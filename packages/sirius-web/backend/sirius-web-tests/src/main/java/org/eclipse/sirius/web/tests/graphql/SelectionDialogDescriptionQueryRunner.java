/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IQueryRunner;
import org.springframework.stereotype.Service;

/**
 * Used to retrieve the description of the selection dialog.
 *
 * @author gcoutable
 */
@Service
public class SelectionDialogDescriptionQueryRunner implements IQueryRunner {

    private static final String GET_SELECTION_DIALOG_DESCRIPTION = """
            query getSelectionDialogDescription($editingContextId: ID!, $representationId: ID!, $variables: [SelectionDialogVariable!]!) {
              viewer {
                editingContext(editingContextId: $editingContextId) {
                  representation(representationId: $representationId) {
                    description {
                      ... on SelectionDescription {
                        dialog(variables: $variables) {
                          titles {
                            defaultTitle
                            noSelectionTitle
                            withSelectionTitle
                          }
                          description
                          noSelectionAction {
                            label
                            description
                          }
                          withSelectionAction {
                            label
                            description
                          }
                          statusMessages {
                            noSelectionActionStatusMessage
                            selectionRequiredWithoutSelectionStatusMessage
                          }
                          confirmButtonLabels {
                            noSelectionConfirmButtonLabel
                            selectionRequiredWithoutSelectionConfirmButtonLabel
                            selectionRequiredWithSelectionConfirmButtonLabel
                          }
                        }
                        treeDescription {
                          id
                        }
                        multiple
                        optional
                      }
                    }
                  }
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public SelectionDialogDescriptionQueryRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLResult run(Map<String, Object> variables) {
        return this.graphQLRequestor.execute(GET_SELECTION_DIALOG_DESCRIPTION, variables);
    }
}
