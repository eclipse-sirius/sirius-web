/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.components.graphql.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import graphql.schema.PropertyDataFetcher;

/**
 * Used to clear the GraphQL cache while using the development profile when the server starts.
 *
 * @author sbegaudeau
 */
@Component
@Profile("dev")
public class GraphQLCacheClearer implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(GraphQLCacheClearer.class);

    @Override
    public void run(String... args) throws Exception {
        this.logger.info("Clearing the GraphQL cache: PropertyDataFetcher#clearReflectionCache()");

        PropertyDataFetcher.clearReflectionCache();
    }

}
