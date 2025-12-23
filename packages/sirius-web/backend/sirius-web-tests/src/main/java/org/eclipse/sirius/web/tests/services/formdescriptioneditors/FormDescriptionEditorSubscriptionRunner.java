/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.web.tests.services.formdescriptioneditors;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.formdescriptioneditors.dto.FormDescriptionEditorEventInput;
import org.eclipse.sirius.components.graphql.tests.api.GraphQLSubscriptionResult;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.ISubscriptionRunner;
import org.springframework.stereotype.Service;

import graphql.execution.DataFetcherResult;

/**
 * Used to get the form description editor event subscription with the GraphQL API.
 *
 * @author sbegaudeau
 */
@Service
public class FormDescriptionEditorSubscriptionRunner implements ISubscriptionRunner<FormDescriptionEditorEventInput> {

    private static final String FORM_DESCRIPTION_EDITOR_EVENT_SUBSCRIPTION = """
            subscription formDescriptionEditorEvent($input: FormDescriptionEditorEventInput!) {
              formDescriptionEditorEvent(input: $input) {
                __typename
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public FormDescriptionEditorSubscriptionRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLSubscriptionResult run(FormDescriptionEditorEventInput input) {
        var rawResult = this.graphQLRequestor.subscribe(FORM_DESCRIPTION_EDITOR_EVENT_SUBSCRIPTION, input);
        var flux = rawResult.flux()
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData);
        return new GraphQLSubscriptionResult(flux, rawResult.errors(), rawResult.extensions());
    }
}
