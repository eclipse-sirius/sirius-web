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

import org.eclipse.sirius.web.infrastructure.elasticsearch.services.api.IDefaultIndexDeletionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;

/**
 * The default implementation that deletes the index for an editing context.
 *
 * @author gdaniel
 */
@Service
public class DefaultIndexDeletionService implements IDefaultIndexDeletionService {

    private final Optional<ElasticsearchClient> optionalElasticSearchClient;

    private final Logger logger = LoggerFactory.getLogger(DefaultIndexDeletionService.class);

    public DefaultIndexDeletionService(Optional<ElasticsearchClient> optionalElasticSearchClient) {
        this.optionalElasticSearchClient = Objects.requireNonNull(optionalElasticSearchClient);
    }

    @Override
    public boolean deleteIndex(String editingContextId) {
        boolean indexDeleted = false;
        if (this.optionalElasticSearchClient.isPresent()) {
            ElasticsearchClient elasticSearchClient = this.optionalElasticSearchClient.get();
            try {
                if (elasticSearchClient.indices().exists(existsRequest -> existsRequest.index(DefaultIndexCreationService.EDITING_CONTEXT_INDEX_NAME_PREFIX + editingContextId)).value()) {
                    elasticSearchClient.indices().delete(deleteIndexRequest -> deleteIndexRequest.index(DefaultIndexCreationService.EDITING_CONTEXT_INDEX_NAME_PREFIX + editingContextId));
                    indexDeleted = true;
                }
            } catch (IOException | ElasticsearchException exception) {
                this.logger.warn("An error occurred while deleting the index", exception);
            }
        }
        return indexDeleted;
    }
}
