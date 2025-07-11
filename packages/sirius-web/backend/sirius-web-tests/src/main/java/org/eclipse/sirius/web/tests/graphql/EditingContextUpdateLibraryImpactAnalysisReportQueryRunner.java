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

import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IQueryRunner;
import org.springframework.stereotype.Service;

/**
 * Used to get an impact analysis report from a library update.
 *
 * @author gdaniel
 */
@Service
public class EditingContextUpdateLibraryImpactAnalysisReportQueryRunner implements IQueryRunner {

    private static final String EDITING_CONTEXT_UPDATE_LIBRARY_IMPACT_ANALYSIS_REPORT_QUERY = """
            query getImpactAnalysisReport($editingContextId: ID!, $libraryId: ID!) {
                viewer {
                    editingContext(editingContextId: $editingContextId) {
                        updateLibraryImpactAnalysisReport(libraryId: $libraryId) {
                            nbElementDeleted
                            nbElementModified
                            nbElementCreated
                            additionalReports
                        }
                    }
                }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public EditingContextUpdateLibraryImpactAnalysisReportQueryRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(Map<String, Object> variables) {
        return this.graphQLRequestor.execute(EDITING_CONTEXT_UPDATE_LIBRARY_IMPACT_ANALYSIS_REPORT_QUERY, variables);
    }

}
