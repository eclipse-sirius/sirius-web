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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.web.application.index.services.api.IIndexEntry;
import org.eclipse.sirius.web.application.index.services.api.IIndexQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;

/**
 * Performs queries on Elasticsearch indices.
 *
 * @author gdaniel
 */
@Service
public class IndexQueryService implements IIndexQueryService {

    private final Logger logger = LoggerFactory.getLogger(IndexQueryService.class);

    private final Optional<ElasticsearchClient> optionalElasticSearchClient;

    public IndexQueryService(Optional<ElasticsearchClient> optionalElasticsearchClient) {
        this.optionalElasticSearchClient = Objects.requireNonNull(optionalElasticsearchClient);
    }

    @Override
    public boolean isAvailable() {
        return this.optionalElasticSearchClient.isPresent();
    }

    @Override
    public List<IIndexEntry> search(String query) {
        List<IIndexEntry> result = new ArrayList<>();
        long start = System.nanoTime();
        if (this.optionalElasticSearchClient.isPresent()) {
            ElasticsearchClient elasticSearchClient = this.optionalElasticSearchClient.get();
            try {
                SearchResponse<IIndexEntry> response = elasticSearchClient.search(searchRequest -> searchRequest
                        .index(ProjectIndexLifecycleManager.PROJECT_INDEX_NAME_PREFIX + "*")
                        // Maximum size supported by Elasticsearch
                        .size(10000)
                        .query(queryBuilder -> queryBuilder
                                .queryString(queryStringBuilder -> queryStringBuilder
                                        .query(query)
                                        .allowLeadingWildcard(true)))
                        , IIndexEntry.class);
                result = response.hits().hits().stream().map(Hit::source).toList();
            } catch (IOException exception) {
                this.logger.warn("An error occurred while querying indices", exception);
            }
        }
        Duration timeToPerformQuery = Duration.ofNanos(System.nanoTime() - start);
        this.logger.trace("Query performed in {} ms", timeToPerformQuery.toMillis());
        return result;
    }

}
