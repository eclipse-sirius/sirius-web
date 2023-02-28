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

import java.util.ArrayList;
import java.util.Map;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.representations.VariableManager;

import graphql.schema.DataFetchingEnvironment;

/**
 * Computes the expression to get an object.
 * <p>
 * It will be used to fetch the data for the following GraphQL field:
 * </p>
 *
 * <pre>
 * type Object {
 *   expressionBasedObject(expression: String!): Object
 * }
 * </pre>
 *
 * @author hmarchadour
 */
@QueryDataFetcher(type = "Object", field = "expressionBasedObject")
public class ObjectExpressionBasedObjectDataFetcher implements IDataFetcherWithFieldCoordinates<Object> {

    private static final String EXPRESSION_ARGUMENT = "expression";

    @Override
    public Object get(DataFetchingEnvironment environment) throws Exception {
        Object object = environment.getSource();
        String expression = environment.getArgument(EXPRESSION_ARGUMENT);

        AQLInterpreter interpreter = new AQLInterpreter(new ArrayList<>(), new ArrayList<>());
        Result result = interpreter.evaluateExpression(Map.of(VariableManager.SELF, object), expression);
        return result.asObject().orElse(null);
    }

}
