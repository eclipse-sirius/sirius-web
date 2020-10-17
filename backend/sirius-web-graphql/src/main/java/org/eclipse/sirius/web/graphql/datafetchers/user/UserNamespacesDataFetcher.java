/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.graphql.datafetchers.user;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.web.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.web.graphql.schema.ViewerTypeProvider;
import org.eclipse.sirius.web.services.api.objects.IEditService;
import org.eclipse.sirius.web.services.api.objects.Namespace;
import org.eclipse.sirius.web.spring.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to retrieve a document with a given documentId.
 * <p>
 * It will be used to fetch the data for the following GraphQL field:
 * </p>
 *
 * <pre>
 * type Viewer {
 *   namespaces: Namespace
 * }
 * </pre>
 *
 * @author lfasani
 */
@QueryDataFetcher(type = ViewerTypeProvider.USER_TYPE, field = ViewerTypeProvider.NAMESPACES_FIELD)
public class UserNamespacesDataFetcher implements IDataFetcherWithFieldCoordinates<List<Namespace>> {

    private final IEditService editService;

    public UserNamespacesDataFetcher(IEditService editService) {
        this.editService = Objects.requireNonNull(editService);
    }

    @Override
    public List<Namespace> get(DataFetchingEnvironment environment) throws Exception {
        return this.editService.getNamespaces();
    }
}
