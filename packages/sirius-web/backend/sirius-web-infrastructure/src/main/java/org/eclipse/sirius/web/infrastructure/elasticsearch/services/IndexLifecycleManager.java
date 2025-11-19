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
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.project.events.ProjectDeletedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.ProjectSemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.events.SemanticDataCreatedEvent;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.events.SemanticDataUpdatedEvent;
import org.eclipse.sirius.web.infrastructure.elasticsearch.services.api.IIndexCreationService;
import org.eclipse.sirius.web.infrastructure.elasticsearch.services.api.IIndexDeletionService;
import org.eclipse.sirius.web.infrastructure.elasticsearch.services.api.IIndexUpdateService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import co.elastic.clients.elasticsearch.ElasticsearchClient;

/**
 * Manages the lifecycle of project indices.
 *
 * @author gdaniel
 */
@Service
public class IndexLifecycleManager {

    private final Optional<ElasticsearchClient> optionalElasticSearchClient;

    private final IEditingContextSearchService editingContextSearchService;

    private final IProjectSemanticDataSearchService projectSemanticDataSearchService;

    private final IIndexCreationService indexCreationService;

    private final IIndexDeletionService indexDeletionService;

    private final IIndexUpdateService indexUpdateService;

    public IndexLifecycleManager(Optional<ElasticsearchClient> optionalElasticSearchClient, IEditingContextSearchService editingContextSearchService, IProjectSemanticDataSearchService projectSemanticDataSearchService, IIndexCreationService indexCreationService, IIndexDeletionService indexDeletionService, IIndexUpdateService indexUpdateService) {
        this.optionalElasticSearchClient = Objects.requireNonNull(optionalElasticSearchClient);
        this.editingContextSearchService = Objects.requireNonNull(editingContextSearchService);
        this.projectSemanticDataSearchService = Objects.requireNonNull(projectSemanticDataSearchService);
        this.indexCreationService = Objects.requireNonNull(indexCreationService);
        this.indexDeletionService = Objects.requireNonNull(indexDeletionService);
        this.indexUpdateService = Objects.requireNonNull(indexUpdateService);
    }

    @Async
    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public void onProjectDeletedEvent(ProjectDeletedEvent projectDeletedEvent) {
        this.optionalElasticSearchClient.ifPresent(elasticSearchClient -> {
            List<ProjectSemanticData> projectSemanticData = this.projectSemanticDataSearchService.findAllByProjectId(AggregateReference.to(projectDeletedEvent.project().getId()));
            for (ProjectSemanticData semanticData : projectSemanticData) {
                this.indexDeletionService.deleteIndex(semanticData.getId().toString());
            }
        });
    }

    @Async
    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public void onSemanticDataCreatedEvent(SemanticDataCreatedEvent semanticDataCreatedEvent) {
        if (this.optionalElasticSearchClient.isPresent()) {
            boolean indexCreated = this.indexCreationService.createIndex(semanticDataCreatedEvent.semanticData().getId().toString());
            if (indexCreated) {
                Optional<IEditingContext> optionalEditingContext = this.editingContextSearchService.findById(semanticDataCreatedEvent.semanticData().getId().toString());
                if (optionalEditingContext.isPresent()) {
                    this.indexUpdateService.updateIndex(optionalEditingContext.get());
                }
            }
        }
    }

    @Async
    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public void onSemanticDataUpdatedEvent(SemanticDataUpdatedEvent semanticDataUpdatedEvent) {
        if (this.optionalElasticSearchClient.isPresent()) {
            Optional<IEditingContext> optionalEditingContext = this.editingContextSearchService.findById(semanticDataUpdatedEvent.semanticData().getId().toString());
            if (optionalEditingContext.isPresent()) {
                this.indexUpdateService.updateIndex(optionalEditingContext.get());
            }
        }
    }

}
