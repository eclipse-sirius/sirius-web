/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.infrastructure.graphql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;

/**
 * Used to handle any exception coming from a data fetcher.
 *
 * @author sbegaudeau
 */
public class GraphQLDataFetcherExceptionHandler implements DataFetcherExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GraphQLDataFetcherExceptionHandler.class);

    @Override
    public DataFetcherExceptionHandlerResult onException(DataFetcherExceptionHandlerParameters handlerParameters) {
        this.logger.warn(handlerParameters.getException().getMessage(), handlerParameters.getException());

        GraphQLError error = this.getGraphQLError(handlerParameters);

        return DataFetcherExceptionHandlerResult.newResult()
                .error(error)
                .build();
    }

    private GraphQLError getGraphQLError(DataFetcherExceptionHandlerParameters handlerParameters) {
        return new ExceptionWhileDataFetching(handlerParameters.getPath(), handlerParameters.getException(), handlerParameters.getSourceLocation());
    }

}