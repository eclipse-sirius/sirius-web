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

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.web.application.index.services.api.IIndexEntry;
import org.eclipse.sirius.web.application.index.services.api.IIndexEntryProvider;
import org.eclipse.sirius.web.infrastructure.elasticsearch.services.api.IDefaultIndexUpdateService;
import org.eclipse.sirius.web.infrastructure.elasticsearch.services.api.IIndexCreationService;
import org.eclipse.sirius.web.infrastructure.elasticsearch.services.api.IIndexDeletionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._helpers.bulk.BulkIngester;

/**
 * The default implementation that indexes the content of an editing context.
 *
 * @author gdaniel
 */
@Service
public class DefaultIndexUpdateService implements IDefaultIndexUpdateService {

    private final IIndexCreationService indexCreationService;

    private final IIndexDeletionService indexDeletionService;

    private final IIndexEntryProvider indexEntryProvider;

    private final Optional<ElasticsearchClient> optionalElasticSearchClient;

    private final Logger logger = LoggerFactory.getLogger(DefaultIndexUpdateService.class);

    public DefaultIndexUpdateService(IIndexCreationService indexCreationService, IIndexDeletionService indexDeletionService, IIndexEntryProvider indexEntryProvider,
            Optional<ElasticsearchClient> optionalElasticSearchClient) {
        this.indexCreationService = Objects.requireNonNull(indexCreationService);
        this.indexDeletionService = Objects.requireNonNull(indexDeletionService);
        this.indexEntryProvider = Objects.requireNonNull(indexEntryProvider);
        this.optionalElasticSearchClient = Objects.requireNonNull(optionalElasticSearchClient);
    }

    @Override
    public void updateIndex(IEditingContext editingContext) {
        long start = System.nanoTime();
        if (this.optionalElasticSearchClient.isPresent()) {
            if (editingContext instanceof IEMFEditingContext emfEditingContext) {
                this.clearIndex(editingContext);
                BulkIngester<Void> bulkIngester = BulkIngester.of(bulkIngesterBuilder -> bulkIngesterBuilder
                        .client(this.optionalElasticSearchClient.get())
                );
                for (Resource resourceToIndex : emfEditingContext.getDomain().getResourceSet().getResources()) {
                    StreamSupport.stream(Spliterators.spliteratorUnknownSize(resourceToIndex.getAllContents(), Spliterator.ORDERED), false)
                        .forEach(eObject -> {
                            Optional<IIndexEntry> optionalIndexEntry = this.indexEntryProvider.getIndexEntry(editingContext, eObject);
                            if (optionalIndexEntry.isPresent()) {
                                IIndexEntry indexEntry = optionalIndexEntry.get();
                                bulkIngester.add(bulkOperation -> bulkOperation
                                        .index(indexOperation -> indexOperation
                                                .index(DefaultIndexCreationService.EDITING_CONTEXT_INDEX_NAME_PREFIX + editingContext.getId())
                                                .id(indexEntry.id())
                                                .document(indexEntry)
                                        )
                                );
                            }
                        });
                }
                bulkIngester.close();
            }
        }
        Duration timeToIndexEditingContext = Duration.ofNanos(System.nanoTime() - start);
        this.logger.trace("Indexed editing context {} in {}ms", editingContext.getId(), timeToIndexEditingContext.toMillis());
    }

    private void clearIndex(IEditingContext editingContext) {
        this.indexDeletionService.deleteIndex(editingContext.getId());
        this.indexCreationService.createIndex(editingContext.getId());
    }
}
