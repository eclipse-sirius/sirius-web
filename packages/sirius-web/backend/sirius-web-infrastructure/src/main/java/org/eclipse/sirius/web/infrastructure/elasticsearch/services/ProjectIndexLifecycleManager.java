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
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.web.application.project.dto.CreateProjectFromTemplateInput;
import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectDeletedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.events.SemanticDataUpdatedEvent;
import org.eclipse.sirius.web.infrastructure.elasticsearch.services.api.IEditingContextIndexingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import co.elastic.clients.elasticsearch.ElasticsearchClient;

/**
 * Manages the lifecycle of project indices.
 *
 * @author gdaniel
 */
@Service
public class ProjectIndexLifecycleManager {

    public static final String PROJECT_INDEX_NAME_PREFIX = "project-";

    private final Optional<ElasticsearchClient> optionalElasticSearchClient;

    private final IEditingContextSearchService editingContextSearchService;

    private final IEditingContextIndexingService editingContextIndexer;

    private final Logger logger = LoggerFactory.getLogger(ProjectIndexLifecycleManager.class);

    public ProjectIndexLifecycleManager(Optional<ElasticsearchClient> optionalElasticSearchClient, IEditingContextSearchService editingContextSearchService,
            IEditingContextIndexingService editingContextIndexer) {
        this.optionalElasticSearchClient = Objects.requireNonNull(optionalElasticSearchClient);
        this.editingContextSearchService = Objects.requireNonNull(editingContextSearchService);
        this.editingContextIndexer = Objects.requireNonNull(editingContextIndexer);
    }

    @TransactionalEventListener
    public void onProjectCreatedEvent(ProjectCreatedEvent projectCreatedEvent) {
        this.optionalElasticSearchClient.ifPresent(elasticSearchClient -> {
            String createdProjectId = projectCreatedEvent.project().getId();
            try {
                if (!elasticSearchClient.indices().exists(existsRequest -> existsRequest.index(PROJECT_INDEX_NAME_PREFIX + createdProjectId)).value()) {
                    elasticSearchClient.indices().create(createIndexRequest -> createIndexRequest.index(PROJECT_INDEX_NAME_PREFIX + createdProjectId));
                }
            } catch (IOException exception) {
                this.logger.warn("An error occurred while creating the index", exception);
            }
        });
    }

    @TransactionalEventListener
    public void onProjectDeletedEvent(ProjectDeletedEvent projectDeletedEvent) {
        this.optionalElasticSearchClient.ifPresent(elasticSearchClient -> {
            String deletedProjectId = projectDeletedEvent.project().getId();
            try {
                elasticSearchClient.indices().delete(deleteIndexRequest -> deleteIndexRequest.index(PROJECT_INDEX_NAME_PREFIX + deletedProjectId));
            } catch (IOException exception) {
                this.logger.warn("An error occurred whild deleting the index", exception);
            }
        });
    }

    @TransactionalEventListener
    public void onSemanticDataUpdatedEvent(SemanticDataUpdatedEvent semanticDataUpdatedEvent) {
        // Project created from a template contain initial semantic data that is not captured by the change recorder of
        // the editing context.
        // We need to manually index it to ensure the index is aligned with the content of the project.
        if (semanticDataUpdatedEvent.causedBy() instanceof CreateProjectFromTemplateInput) {
            Optional<IEditingContext> optionalEditingContext = this.editingContextSearchService.findById(semanticDataUpdatedEvent.semanticData().getId().toString());
            if (optionalEditingContext.isPresent()) {
                this.editingContextIndexer.index(optionalEditingContext.get());
            }
        }
    }

}
