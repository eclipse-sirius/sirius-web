/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Service;

import graphql.execution.ExecutionStepInfo;
import graphql.schema.DataFetchingEnvironment;

/**
 * The IObjectQueryBasedDataFetcherService implementation.
 *
 * @author fbarbin
 */
@Service
public class ObjectQueryBasedDataFetcherService implements IObjectQueryBasedDataFetcherService {
    private static final String VARIABLE_ARGUMENT = "variableName";

    @Override
    public Map<String, Object> computeNewVariables(DataFetchingEnvironment environment) {
        Object source = environment.getSource();
        ObjectLocalContext localContext = environment.getLocalContext();
        Map<String, Object> variables = localContext.getVariables();

        String parentVariable = this.getParentVariable(environment);

        Map<String, Object> newVariables = new HashMap<>(variables);
        newVariables.put(parentVariable, source);
        return newVariables;
    }

    private String getParentVariable(DataFetchingEnvironment environment) {
        ExecutionStepInfo parent = environment.getExecutionStepInfo().getParent();
        return Optional.ofNullable(parent.getArgument(VARIABLE_ARGUMENT))
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .orElse(VariableManager.SELF);
    }
}
