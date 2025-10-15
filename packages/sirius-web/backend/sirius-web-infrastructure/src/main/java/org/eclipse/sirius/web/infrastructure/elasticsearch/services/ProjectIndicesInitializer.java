/*******************************************************************************
 * Copyright (c) 2025 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.web.infrastructure.elasticsearch.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.ProjectSemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataSearchService;
import org.eclipse.sirius.web.domain.pagination.Window;
import org.eclipse.sirius.web.infrastructure.elasticsearch.services.api.IEditingContextIndexingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;

/**
 * Initializes Sirius Web's indices on startup.
 * <p>
 * This service indexes all the projects that do not have a corresponding index in Elasticsearch. Note that it doesn't
 * inspect the content of the index nor ensure it is aligned with the projects' semantic data.
 * </p>
 *
 * @author gdaniel
 */
@Service
public class ProjectIndicesInitializer implements CommandLineRunner {

    private final IProjectSearchService projectSearchService;

    private final IProjectSemanticDataSearchService projectSemanticDataSearchService;

    private final IEditingContextSearchService editingContextSearchService;

    private final IEditingContextIndexingService editingContextIndexer;

    private final Optional<ElasticsearchClient> optionalElasticSearchClient;

    private final Logger logger = LoggerFactory.getLogger(ProjectIndicesInitializer.class);

    public ProjectIndicesInitializer(IProjectSearchService projectSearchService, IProjectSemanticDataSearchService projectSemanticDataSearchService, IEditingContextSearchService editingContextSearchService, IEditingContextIndexingService editingContextIndexer, Optional<ElasticsearchClient> optionalElasticSearchClient) {
        this.projectSearchService = Objects.requireNonNull(projectSearchService);
        this.projectSemanticDataSearchService = Objects.requireNonNull(projectSemanticDataSearchService);
        this.editingContextSearchService = Objects.requireNonNull(editingContextSearchService);
        this.editingContextIndexer = Objects.requireNonNull(editingContextIndexer);
        this.optionalElasticSearchClient = Objects.requireNonNull(optionalElasticSearchClient);
    }

    @Async
    @Override
    public void run(String... args) throws Exception {
        this.optionalElasticSearchClient.ifPresent(elasticSearchClient -> {
            Window<Project> projects = this.projectSearchService.findAll(ScrollPosition.keyset(), Integer.MAX_VALUE - 1, Map.of());
            try {
                List<String> existingIndices = new ArrayList<>(elasticSearchClient.indices().stats().indices().keySet());

                for (Project project : projects) {
                    if (!existingIndices.remove(ProjectIndexLifecycleManager.PROJECT_INDEX_NAME_PREFIX + project.getId())) {
                        elasticSearchClient.indices().create(createIndexRequest -> createIndexRequest
                                .index(ProjectIndexLifecycleManager.PROJECT_INDEX_NAME_PREFIX + project.getId())
                                .settings(settingsBuilder -> settingsBuilder.mode("lookup"))
                        );
                        this.indexAllEditingContexts(project);
                    }
                }

                // Delete the remaining indices, they are not associated to a project.
                if (!existingIndices.isEmpty()) {
                    elasticSearchClient.indices().delete(deleteIndexRequest -> deleteIndexRequest.index(existingIndices)
                    );
                }
            } catch (IOException exception) {
                this.logger.warn("An error occurred while initializing indices", exception);
            }
        });
    }

    private void indexAllEditingContexts(Project project) {
        List<ProjectSemanticData> allProjectSemanticData = this.projectSemanticDataSearchService.findAllByProjectId(AggregateReference.to(project.getId()));
        for (ProjectSemanticData projectSemanticData : allProjectSemanticData) {
            Optional<IEditingContext> optionalEditingContext = this.editingContextSearchService.findById(projectSemanticData.getSemanticData().getId().toString());
            if (optionalEditingContext.isPresent()) {
                this.editingContextIndexer.index(optionalEditingContext.get());
            }
        }
    }
}
