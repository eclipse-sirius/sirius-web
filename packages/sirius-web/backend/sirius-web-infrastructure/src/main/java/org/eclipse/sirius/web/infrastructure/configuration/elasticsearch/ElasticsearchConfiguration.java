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
package org.eclipse.sirius.web.infrastructure.configuration.elasticsearch;

import java.util.Optional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchClient;
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.transport.ElasticsearchTransport;

/**
 * Spring Configuration used to create everything necessary to interact with Elasticsearch.
 * <p>
 * This class makes the integration of Elasticsearch fully optional: Elasticsearch beans will be created only if the
 * application's configuration contains the following properties:
 * <ul>
 * <li>{@code spring.elasticsearch.username}</li>
 * <li>{@code spring.elasticsearch.password}</li>
 * </ul>
 * Note that {@code spring.elasticsearch.uris} is not required and its default value is {@code localhost:9200}.
 * </p>
 * <p>
 * Client code should always assume Elasticsearch beans can be absent, and fallback accordingly.
 * </p>
 *
 * @author gdaniel
 */
@Configuration
public class ElasticsearchConfiguration {

    @Bean
    public ElasticsearchClient elasticSearchClient(ElasticsearchTransport transport, ElasticsearchProperties properties) {
        ElasticsearchClient elasticSearchClient = null;
        if (properties.getUsername() != null && properties.getPassword() != null) {
            // We need to explicitly set the client to null if there is no username or password, otherwise
            // it gets created and will fail when attempting to perform an operation.
            elasticSearchClient = new ElasticsearchClient(transport);
        }
        return elasticSearchClient;
    }

    @Bean
    @ConditionalOnMissingBean(value = ElasticsearchOperations.class, name = "elasticsearchTemplate")
    @ConditionalOnBean(ElasticsearchClient.class)
    public ElasticsearchTemplate elasticsearchTemplate(Optional<ElasticsearchClient> optionalElasticsearchClient, ElasticsearchConverter converter) {
        ElasticsearchTemplate elasticSearchTemplate = null;
        if (optionalElasticsearchClient.isPresent()) {
            elasticSearchTemplate = new ElasticsearchTemplate(optionalElasticsearchClient.get(), converter);
        }
        return elasticSearchTemplate;
    }

    @Bean
    @ConditionalOnMissingBean(value = ReactiveElasticsearchOperations.class, name = "reactiveElasticsearchTemplate")
    @ConditionalOnBean(ReactiveElasticsearchClient.class)
    public ReactiveElasticsearchTemplate reactiveElasticsearchTemplate(Optional<ReactiveElasticsearchClient> optionalReactiveElasticsearchClient,
            ElasticsearchConverter converter) {
        ReactiveElasticsearchTemplate reactiveElasticSearchTemplate = null;
        if (optionalReactiveElasticsearchClient.isPresent()) {
            reactiveElasticSearchTemplate = new ReactiveElasticsearchTemplate(optionalReactiveElasticsearchClient.get(), converter);
        }
        return reactiveElasticSearchTemplate;
    }

}
