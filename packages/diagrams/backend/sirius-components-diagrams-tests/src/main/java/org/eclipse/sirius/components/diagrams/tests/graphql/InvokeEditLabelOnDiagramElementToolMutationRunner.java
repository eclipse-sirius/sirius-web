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
package org.eclipse.sirius.components.diagrams.tests.graphql;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.diagrams.dto.EditLabelInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to invoke a label edit tool on a single diagram element with the GraphQL API.
 *
 * @author pcdavid
 */
@Service
public class InvokeEditLabelOnDiagramElementToolMutationRunner  implements IMutationRunner<EditLabelInput> {
    private static final String INVOKE_EDIT_LABEL_ON_DIAGRAM_ELEMENT_TOOL_MUTATION = """
             mutation editLabel($input: EditLabelInput!) {
                editLabel(input: $input) {
                  __typename
                  ... on EditLabelSuccessPayload {
                    diagram {
                      id
                    }
                    messages {
                      body
                      level
                    }
                  }
                  ... on ErrorPayload {
                    messages {
                      body
                      level
                    }
                  }
                }
              }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public InvokeEditLabelOnDiagramElementToolMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(EditLabelInput input) {
        return this.graphQLRequestor.execute(INVOKE_EDIT_LABEL_ON_DIAGRAM_ELEMENT_TOOL_MUTATION, input);
    }
}
