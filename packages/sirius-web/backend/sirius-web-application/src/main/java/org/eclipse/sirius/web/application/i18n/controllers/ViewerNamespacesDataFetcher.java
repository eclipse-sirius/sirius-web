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
package org.eclipse.sirius.web.application.i18n.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field Viewer#namespaces.
 *
 * <p>
 * It loads the resources i18n/{language}/{namespace}.json to compute namespaces.
 * Thus, adding new JSON files in /i18n/{language}/ will add new namespaces that will be handled by the frontend.
 * </p>
 *
 * @author gcoutable
 */
@QueryDataFetcher(type = "Viewer", field = "namespaces")
public class ViewerNamespacesDataFetcher implements IDataFetcherWithFieldCoordinates<List<String>> {

    @Override
    public List<String> get(DataFetchingEnvironment environment) throws Exception {

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(this.getClass().getClassLoader());
        var resources = Arrays.asList(resolver.getResources("classpath*:/i18n/*/*.json"));
        return resources.stream().map(Resource::getFilename)
                .filter(Objects::nonNull)
                .map(filename -> filename.substring(0, filename.lastIndexOf('.')))
                .toList();
    }
}
