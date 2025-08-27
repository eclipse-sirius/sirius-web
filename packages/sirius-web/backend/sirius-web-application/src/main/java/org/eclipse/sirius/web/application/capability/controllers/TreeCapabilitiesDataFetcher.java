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
package org.eclipse.sirius.web.application.capability.controllers;

import graphql.schema.DataFetchingEnvironment;
import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

/**
 * Data fetcher for the field Tree#capabilities.
 *
 * @author mcharfadi
 */
@QueryDataFetcher(type = "Tree", field = "capabilities")
public class TreeCapabilitiesDataFetcher implements IDataFetcherWithFieldCoordinates<Object> {

    @Override
    public Object get(DataFetchingEnvironment environment) throws Exception {
        return new Object();
    }
}