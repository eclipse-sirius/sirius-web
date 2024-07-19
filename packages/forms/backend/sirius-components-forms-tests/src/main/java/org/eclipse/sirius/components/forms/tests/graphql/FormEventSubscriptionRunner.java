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
package org.eclipse.sirius.components.forms.tests.graphql;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.forms.dto.FormEventInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.ISubscriptionRunner;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

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
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public FormEventSubscriptionRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public Flux<Object> run(FormEventInput input) {
        return this.graphQLRequestor.subscribe(FORM_EVENT_SUBSCRIPTION, input);
    }

}
