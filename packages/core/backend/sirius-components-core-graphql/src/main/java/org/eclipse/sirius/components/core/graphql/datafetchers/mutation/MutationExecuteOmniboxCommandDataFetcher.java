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
package org.eclipse.sirius.components.core.graphql.datafetchers.mutation;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.MutationDataFetcher;
import org.eclipse.sirius.components.collaborative.omnibox.api.IOmniboxCommandExecutor;
import org.eclipse.sirius.components.collaborative.omnibox.dto.ExecuteOmniboxCommandInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field Mutation#executeOmniboxCommand.
 *
 * @author gdaniel
 */
@MutationDataFetcher(type = "Mutation", field = "executeOmniboxCommand")
public class MutationExecuteOmniboxCommandDataFetcher implements IDataFetcherWithFieldCoordinates<IPayload> {

    private static final String INPUT_ARGUMENT = "input";

    private final ObjectMapper objectMapper;

    private final IOmniboxCommandExecutor omniboxCommandExecutor;

    public MutationExecuteOmniboxCommandDataFetcher(ObjectMapper objectMapper, IOmniboxCommandExecutor omniboxCommandExecutor) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.omniboxCommandExecutor = Objects.requireNonNull(omniboxCommandExecutor);
    }

    @Override
    public IPayload get(DataFetchingEnvironment environment) throws Exception {
        Object argument = environment.getArgument(INPUT_ARGUMENT);
        var input = this.objectMapper.convertValue(argument, ExecuteOmniboxCommandInput.class);

        return this.omniboxCommandExecutor.execute(input);
    }

}
