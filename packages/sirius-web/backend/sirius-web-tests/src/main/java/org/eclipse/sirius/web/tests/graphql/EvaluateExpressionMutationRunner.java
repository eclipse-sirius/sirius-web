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
package org.eclipse.sirius.web.tests.graphql;

import java.util.Objects;

import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.eclipse.sirius.web.application.views.query.dto.EvaluateExpressionInput;
import org.springframework.stereotype.Service;

/**
 * Used to evaluate expressions.
 *
 * @author sbegaudeau
 */
@Service
public class EvaluateExpressionMutationRunner implements IMutationRunner<EvaluateExpressionInput> {

    private static final String EVALUATE_EXPRESSION_MUTATION = """
            mutation evaluateExpression($input: EvaluateExpressionInput!) {
              evaluateExpression(input: $input) {
                __typename
                ... on EvaluateExpressionSuccessPayload {
                  result {
                    __typename
                    ... on ObjectsExpressionResult {
                      objectsValue: value {
                        id
                        label
                        kind
                      }
                    }
                    ... on ObjectExpressionResult {
                      objectValue: value {
                        id
                        label
                        kind
                      }
                    }
                    ... on BooleanExpressionResult {
                      booleanValue: value
                    }
                    ... on IntExpressionResult {
                      intValue: value
                    }
                    ... on StringsExpressionResult {
                      stringsValue: value
                    }
                    ... on StringExpressionResult {
                      stringValue: value
                    }
                  }
                }
                ... on ErrorPayload {
                  messages {
                    level
                    body
                  }
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public EvaluateExpressionMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLResult run(EvaluateExpressionInput input) {
        return this.graphQLRequestor.execute(EVALUATE_EXPRESSION_MUTATION, input);
    }
}
