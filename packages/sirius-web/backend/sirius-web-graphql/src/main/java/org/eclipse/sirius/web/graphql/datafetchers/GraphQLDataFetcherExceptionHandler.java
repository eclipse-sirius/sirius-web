/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.web.graphql.datafetchers;

import java.util.concurrent.CompletableFuture;

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
    public CompletableFuture<DataFetcherExceptionHandlerResult> handleException(DataFetcherExceptionHandlerParameters handlerParameters) {
        this.logger.warn(handlerParameters.getException().getMessage(), handlerParameters.getException());

        GraphQLError error = new ExceptionWhileDataFetching(handlerParameters.getPath(), handlerParameters.getException(), handlerParameters.getSourceLocation());

        var result = DataFetcherExceptionHandlerResult.newResult()
                .error(error)
                .build();
        return CompletableFuture.completedFuture(result);
    }

}
