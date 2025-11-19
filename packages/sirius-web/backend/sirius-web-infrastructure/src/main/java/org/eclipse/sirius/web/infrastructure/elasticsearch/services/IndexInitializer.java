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

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.ProjectSemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataSearchService;
import org.eclipse.sirius.web.domain.pagination.Window;
import org.eclipse.sirius.web.infrastructure.elasticsearch.services.api.IIndexCreationService;
import org.eclipse.sirius.web.infrastructure.elasticsearch.services.api.IIndexUpdateService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.elastic.clients.elasticsearch.ElasticsearchClient;

/**
 * Initializes Sirius Web's indices on startup.
 * <p>
 * This service indexes the projects' editing contexts that do not have a corresponding index in Elasticsearch. Note that it doesn't
 * inspect the content of existing indices nor ensure their content is aligned with the actual data.
 * </p>
 *
 * @author gdaniel
 */
@Service
public class IndexInitializer implements CommandLineRunner {

    private final IProjectSearchService projectSearchService;

    private final IProjectSemanticDataSearchService projectSemanticDataSearchService;

    private final IEditingContextSearchService editingContextSearchService;

    private final IIndexCreationService indexCreationService;

    private final IIndexUpdateService indexUpdateService;

    private final Optional<ElasticsearchClient> optionalElasticSearchClient;

    public IndexInitializer(IProjectSearchService projectSearchService, IProjectSemanticDataSearchService projectSemanticDataSearchService, IEditingContextSearchService editingContextSearchService,
            IIndexCreationService indexCreationService, IIndexUpdateService indexUpdateService, Optional<ElasticsearchClient> optionalElasticSearchClient) {
        this.projectSearchService = Objects.requireNonNull(projectSearchService);
        this.projectSemanticDataSearchService = Objects.requireNonNull(projectSemanticDataSearchService);
        this.editingContextSearchService = Objects.requireNonNull(editingContextSearchService);
        this.indexCreationService = Objects.requireNonNull(indexCreationService);
        this.indexUpdateService = Objects.requireNonNull(indexUpdateService);
        this.optionalElasticSearchClient = Objects.requireNonNull(optionalElasticSearchClient);
    }

    @Async
    @Override
    @Transactional(readOnly = true)
    public void run(String... args) throws Exception {
        this.optionalElasticSearchClient.ifPresent(elasticSearchClient -> {
            Window<Project> projects = this.projectSearchService.findAll(ScrollPosition.keyset(), Integer.MAX_VALUE - 1, Map.of());
            for (Project project : projects) {
                List<ProjectSemanticData> allProjectSemanticData = this.projectSemanticDataSearchService.findAllByProjectId(AggregateReference.to(project.getId()));
                for (ProjectSemanticData projectSemanticData : allProjectSemanticData) {
                    boolean indexCreated = this.indexCreationService.createIndex(projectSemanticData.getSemanticData().getId().toString());
                    if (indexCreated) {
                        this.editingContextSearchService.findById(projectSemanticData.getSemanticData().getId().toString())
                                .ifPresent(this.indexUpdateService::updateIndex);
                    }
                }
            }
        });
    }
}
