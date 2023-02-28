/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
package org.eclipse.sirius.web.graphql.datafetchers.object;

import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * Computes the kind of an object.
 * <p>
 * It will be used to fetch the data for the following GraphQL field:
 * </p>
 *
 * <pre>
 * type Object {
 *   kind: String!
 * }
 * </pre>
 *
 * @author hmarchadour
 */
@QueryDataFetcher(type = "Object", field = "kind")
public class ObjectKindDataFetcher implements IDataFetcherWithFieldCoordinates<String> {

    private final IObjectService objectService;

    public ObjectKindDataFetcher(IObjectService objectService) {
        this.objectService = Objects.requireNonNull(objectService);
    }

    @Override
    public String get(DataFetchingEnvironment environment) throws Exception {
        Object object = environment.getSource();
        return this.objectService.getKind(object);
    }

}
