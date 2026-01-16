/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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

import org.eclipse.sirius.web.application.index.services.api.IIndexEntry;
import org.eclipse.sirius.web.infrastructure.elasticsearch.services.api.IDefaultIndexCreationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;

/**
 * The default implementation that creates the index for an editing context.
 *
 * @author gdaniel
 */
@Service
public class DefaultIndexCreationService implements IDefaultIndexCreationService {

    public static final String EDITING_CONTEXT_INDEX_NAME_PREFIX = "editing-context-";

    private final Optional<ElasticsearchClient> optionalElasticSearchClient;

    private final Logger logger = LoggerFactory.getLogger(DefaultIndexCreationService.class);

    public DefaultIndexCreationService(Optional<ElasticsearchClient> optionalElasticSearchClient) {
        this.optionalElasticSearchClient = Objects.requireNonNull(optionalElasticSearchClient);
    }

    @Override
    public boolean createIndex(String editingContextId) {
        boolean indexCreated = false;
        if (this.optionalElasticSearchClient.isPresent()) {
            ElasticsearchClient elasticSearchClient = this.optionalElasticSearchClient.get();
            try {
                if (!elasticSearchClient.indices().exists(existsRequest -> existsRequest.index(EDITING_CONTEXT_INDEX_NAME_PREFIX + editingContextId)).value()) {
                    elasticSearchClient.indices().create(createIndexRequest -> createIndexRequest
                            .index(EDITING_CONTEXT_INDEX_NAME_PREFIX + editingContextId)
                            .settings(settingsBuilder -> settingsBuilder.mode("lookup"))
                            .mappings(mappingsBuilder -> mappingsBuilder
                                    // Do not index iconURLs, editingContextId, or entryType, this is technical information we don't want to search for.
                                    .properties(IIndexEntry.ICON_URLS_FIELD, propertyBuilder ->
                                            propertyBuilder.text(textPropertyBuilder ->
                                                    textPropertyBuilder.index(false)))
                                    .properties(IIndexEntry.EDITING_CONTEXT_ID_FIELD, propertyBuilder ->
                                            propertyBuilder.text(textPropertyBuilder ->
                                                    textPropertyBuilder.index(false)))
                                    .properties(IIndexEntry.INDEX_ENTRY_TYPE_FIELD, propertyBuilder ->
                                            propertyBuilder.text(textPropertyBuilder ->
                                                    textPropertyBuilder.index(false)))));
                    indexCreated = true;
                }
            } catch (IOException | ElasticsearchException exception) {
                this.logger.warn("An error occurred while creating the index", exception);
            }
        }
        return indexCreated;
    }
}
