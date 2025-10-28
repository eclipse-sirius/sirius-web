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
import org.eclipse.sirius.components.collaborative.omnibox.api.IWorkbenchOmniboxCommandExecutor;
import org.eclipse.sirius.components.collaborative.omnibox.dto.ExecuteWorkbenchOmniboxCommandInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field Mutation#executeWorkbenchOmniboxCommand.
 *
 * @author gdaniel
 */
@MutationDataFetcher(type = "Mutation", field = "executeWorkbenchOmniboxCommand")
public class MutationExecuteWorkbenchOmniboxCommandDataFetcher implements IDataFetcherWithFieldCoordinates<IPayload> {

    private static final String INPUT_ARGUMENT = "input";

    private final ObjectMapper objectMapper;

    private final IWorkbenchOmniboxCommandExecutor workbenchOmniboxCommandExecutor;

    public MutationExecuteWorkbenchOmniboxCommandDataFetcher(ObjectMapper objectMapper, IWorkbenchOmniboxCommandExecutor workbenchOmniboxCommandExecutor) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.workbenchOmniboxCommandExecutor = Objects.requireNonNull(workbenchOmniboxCommandExecutor);
    }

    @Override
    public IPayload get(DataFetchingEnvironment environment) throws Exception {
        Object argument = environment.getArgument(INPUT_ARGUMENT);
        var input = this.objectMapper.convertValue(argument, ExecuteWorkbenchOmniboxCommandInput.class);

        return this.workbenchOmniboxCommandExecutor.execute(input);
    }

}
