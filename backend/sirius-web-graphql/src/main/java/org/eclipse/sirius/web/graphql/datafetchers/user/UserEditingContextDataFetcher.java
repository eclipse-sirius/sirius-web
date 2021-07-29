/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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

import java.util.UUID;

import org.eclipse.sirius.web.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.web.graphql.schema.ViewerTypeProvider;
import org.eclipse.sirius.web.spring.graphql.api.IDataFetcherWithFieldCoordinates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to retrieve an editing context for a user.
 * <p>
 * It will be used to fetch the data for the following GraphQL field:
 * </p>
 *
 * <pre>
 * type User {
 *   editingContext(editingContextId: ID!): EditingContext
 * }
 * </pre>
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = ViewerTypeProvider.USER_TYPE, field = ViewerTypeProvider.EDITING_CONTEXT_FIELD)
public class UserEditingContextDataFetcher implements IDataFetcherWithFieldCoordinates<UUID> {

    private final Logger logger = LoggerFactory.getLogger(UserEditingContextDataFetcher.class);

    @Override
    public UUID get(DataFetchingEnvironment environment) throws Exception {
        String editingContextIdArgument = environment.getArgument(ViewerTypeProvider.EDITING_CONTEXT_ID_ARGUMENT);
        try {
            UUID editingContextId = UUID.fromString(editingContextIdArgument);
            return editingContextId;
        } catch (IllegalArgumentException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }
        return null;
    }

}
