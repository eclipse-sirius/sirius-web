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
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.web.application.project.services.api.IProjectEditingContextService;
import org.eclipse.sirius.web.infrastructure.elasticsearch.services.api.IEditingContextIndexingService;
import org.eclipse.sirius.web.infrastructure.elasticsearch.services.api.IIndexDocumentCreationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._helpers.bulk.BulkIngester;
import co.elastic.clients.util.BinaryData;
import co.elastic.clients.util.ContentType;

/**
 * Indexes the content of an editing context.
 *
 * @author gdaniel
 */
@Service
public class EditingContextIndexingService implements IEditingContextIndexingService {

    private final IProjectEditingContextService projectEditingContextService;

    private final IIdentityService identityService;

    private final IIndexDocumentCreationService indexDocumentCreationService;

    private final Optional<ElasticsearchClient> optionalElasticSearchClient;

    private final Logger logger = LoggerFactory.getLogger(EditingContextIndexingService.class);

    public EditingContextIndexingService(IProjectEditingContextService projectEditingContextService, IIdentityService identityService, IIndexDocumentCreationService indexDocumentCreationService,
            Optional<ElasticsearchClient> optionalElasticSearchClient) {
        this.projectEditingContextService = Objects.requireNonNull(projectEditingContextService);
        this.identityService = Objects.requireNonNull(identityService);
        this.indexDocumentCreationService = Objects.requireNonNull(indexDocumentCreationService);
        this.optionalElasticSearchClient = Objects.requireNonNull(optionalElasticSearchClient);
    }

    @Override
    public void index(IEditingContext editingContext) {
        long start = System.nanoTime();
        if (this.optionalElasticSearchClient.isPresent()) {
            if (editingContext instanceof IEMFEditingContext emfEditingContext) {
                Optional<String> optionalProjectId = this.projectEditingContextService.getProjectId(editingContext.getId());
                if (optionalProjectId.isPresent()) {
                    this.clearIndex(optionalProjectId.get());
                    BulkIngester<Void> bulkIngester = BulkIngester.of(bulkIngesterBuilder -> bulkIngesterBuilder
                            .client(this.optionalElasticSearchClient.get())
                    );
                    for (Resource resourceToIndex : emfEditingContext.getDomain().getResourceSet().getResources()) {
                        StreamSupport.stream(Spliterators.spliteratorUnknownSize(resourceToIndex.getAllContents(), Spliterator.ORDERED), false)
                            .forEach(eObject -> {
                                String objectId = this.identityService.getId(eObject);
                                Optional<byte[]> optionalIndexDocument = this.indexDocumentCreationService.createDocument(eObject);
                                if (optionalIndexDocument.isPresent()) {
                                    BinaryData binaryData = BinaryData.of(optionalIndexDocument.get(), ContentType.APPLICATION_JSON);
                                    bulkIngester.add(bulkOperation -> bulkOperation
                                            .index(indexOperation -> indexOperation
                                                    .index(ProjectIndexLifecycleManager.PROJECT_INDEX_NAME_PREFIX + optionalProjectId.get())
                                                    .id(objectId)
                                                    .document(binaryData)
                                            )
                                    );
                                }
                            });
                    }
                    bulkIngester.close();
                }
            }
        }
        Duration timeToIndexEditingContext = Duration.ofNanos(System.nanoTime() - start);
        this.logger.trace("Indexed editing context {} in {}ms", editingContext.getId(), timeToIndexEditingContext.toMillis());
    }

    private void clearIndex(String projectId) {
        this.optionalElasticSearchClient.ifPresent(elasticSearchClient -> {
            try {
                elasticSearchClient.indices().delete(deleteIndexRequest -> deleteIndexRequest.index(ProjectIndexLifecycleManager.PROJECT_INDEX_NAME_PREFIX + projectId));
                elasticSearchClient.indices().create(createIndexRequest -> createIndexRequest
                        .index(ProjectIndexLifecycleManager.PROJECT_INDEX_NAME_PREFIX + projectId)
                        .settings(settingsBuilder -> settingsBuilder.mode("lookup"))
                );
            } catch (IOException exception) {
                this.logger.warn("An error occurred while clearing the index", exception);
            }
        });
    }
}
