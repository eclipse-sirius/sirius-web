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
package org.eclipse.sirius.components.forms.tests.graphql;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.forms.dto.FormEventInput;
import org.eclipse.sirius.components.graphql.tests.api.GraphQLSubscriptionResult;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.ISubscriptionRunner;
import org.springframework.stereotype.Service;

import graphql.execution.DataFetcherResult;

/**
 * Used to get the form event subscription with the GraphQL API.
 *
 * @author gdaniel
 */
@Service
public class FormEventSubscriptionRunner implements ISubscriptionRunner<FormEventInput> {

    private static final String FORM_EVENT_SUBSCRIPTION = """
            subscription formEvent($input: FormEventInput!) {
              formEvent(input: $input) {
                __typename
                ... on FormRefreshedEventPayload {
                  form {
                    id
                  }
                }
                ... on FormCapabilitiesRefreshedEventPayload {
                  capabilities {
                    canEdit
                  }
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public FormEventSubscriptionRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLSubscriptionResult run(FormEventInput input) {
        var rawResult = this.graphQLRequestor.subscribe(FORM_EVENT_SUBSCRIPTION, input);
        var flux = rawResult.flux()
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData);
        return new GraphQLSubscriptionResult(flux, rawResult.errors(), rawResult.extensions());
    }

}
