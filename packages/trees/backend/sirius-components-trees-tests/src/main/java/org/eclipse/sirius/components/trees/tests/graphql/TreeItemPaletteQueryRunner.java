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
package org.eclipse.sirius.components.trees.tests.graphql;

import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IQueryRunner;
import org.springframework.stereotype.Service;

/**
 * Used to retrieve the palette.
 *
 * @author mcharfadi
 */
@Service
public class TreeItemPaletteQueryRunner implements IQueryRunner {

    public static final String PALETTE_QUERY = """
            query getFetchTreeItemContextMenuEntryDataQuery(
              $editingContextId: ID!
              $representationId: ID!
              $treeItemId: ID!
            ) {
              viewer {
                editingContext(editingContextId: $editingContextId) {
                  representation(representationId: $representationId) {
                    description {
                      ... on TreeDescription {
                        palette(treeItemId: $treeItemId) {
                          quickAccessTools {
                            ...ToolFields
                          }
                          paletteEntries {
                            ...ToolFields
                            ... on ToolSection {
                              id
                              label
                              iconURL
                              tools {
                                ...ToolFields
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }

            fragment ToolFields on Tool {
              __typename
              id
              label
              iconURL
              ... on SingleClickTreeItemTool {
                withImpactAnalysis
                keyBindings {
                  isCtrl
                  isMeta
                  isAlt
                  key
                }
              }
              ... on FetchTreeItemTool {
                keyBindings {
                  isCtrl
                  isMeta
                  isAlt
                  key
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public TreeItemPaletteQueryRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLResult run(Map<String, Object> variables) {
        return this.graphQLRequestor.execute(PALETTE_QUERY, variables);
    }
}
