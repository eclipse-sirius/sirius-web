/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
package org.eclipse.sirius.web.graphql.datafetchers.editingcontext;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.graphql.schema.EditingContextTypeProvider;
import org.eclipse.sirius.web.graphql.schema.IdFieldProvider;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to retrieve the id of an editing context.
 * <p>
 * It will be used to fetch the data for the following GraphQL field:
 * </p>
 *
 * <pre>
 * type EditingContext {
 *   id: ID!
 * }
 * </pre>
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = EditingContextTypeProvider.TYPE, field = IdFieldProvider.ID_FIELD)
public class EditingContextIdDataFetcher implements IDataFetcherWithFieldCoordinates<String> {

    @Override
    public String get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getSource();
        return editingContextId;
    }

}
