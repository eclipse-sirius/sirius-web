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
package org.eclipse.sirius.web.tests.services.forms;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.forms.dto.FormEventInput;
import org.eclipse.sirius.components.forms.tests.graphql.FormEventSubscriptionRunner;
import org.eclipse.sirius.components.graphql.tests.api.GraphQLSubscriptionResult;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedFormSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TestTransaction;

/**
 * Used to create a form and subscribe to it.
 *
 * @author sbegaudeau
 */
@Service
public class GivenCreatedFormSubscription implements IGivenCreatedFormSubscription {

    private final IGivenCreatedRepresentation givenCreatedRepresentation;

    private final FormEventSubscriptionRunner formEventSubscriptionRunner;

    public GivenCreatedFormSubscription(IGivenCreatedRepresentation givenCreatedRepresentation, FormEventSubscriptionRunner formEventSubscriptionRunner) {
        this.givenCreatedRepresentation = Objects.requireNonNull(givenCreatedRepresentation);
        this.formEventSubscriptionRunner = Objects.requireNonNull(formEventSubscriptionRunner);
    }

    @Override
    public GraphQLSubscriptionResult createAndSubscribe(CreateRepresentationInput input) {
        String representationId = this.givenCreatedRepresentation.createRepresentation(input);

        var formEventInput = new FormEventInput(UUID.randomUUID(), input.editingContextId(), representationId);
        var result = this.formEventSubscriptionRunner.run(formEventInput);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        return result;
    }
}
