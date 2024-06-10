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
package org.eclipse.sirius.web.tests.services.formdescriptioneditors;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.dto.FormDescriptionEditorEventInput;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.dto.FormDescriptionEditorRefreshedEventPayload;
import org.eclipse.sirius.web.tests.services.api.IGivenCommittedTransaction;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedFormDescriptionEditorSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TestTransaction;

import graphql.execution.DataFetcherResult;
import reactor.core.publisher.Flux;

/**
 * Used to create a form description editor and subscribe to it.
 *
 * @author sbegaudeau
 */
@Service
public class GivenCreatedFormDescriptionEditorSubscription implements IGivenCreatedFormDescriptionEditorSubscription {

    private final IGivenCommittedTransaction givenCommittedTransaction;

    private final IGivenCreatedRepresentation givenCreatedRepresentation;

    private final FormDescriptionEditorSubscriptionRunner formDescriptionEditorSubscriptionRunner;

    public GivenCreatedFormDescriptionEditorSubscription(IGivenCommittedTransaction givenCommittedTransaction, IGivenCreatedRepresentation givenCreatedRepresentation, FormDescriptionEditorSubscriptionRunner formDescriptionEditorSubscriptionRunner) {
        this.givenCommittedTransaction = Objects.requireNonNull(givenCommittedTransaction);
        this.givenCreatedRepresentation = Objects.requireNonNull(givenCreatedRepresentation);
        this.formDescriptionEditorSubscriptionRunner = Objects.requireNonNull(formDescriptionEditorSubscriptionRunner);
    }

    @Override
    public Flux<FormDescriptionEditorRefreshedEventPayload> createAndSubscribe(CreateRepresentationInput input) {
        this.givenCommittedTransaction.commit();

        String representationId = this.givenCreatedRepresentation.createRepresentation(input);

        var formDescriptionEditorEventInput = new FormDescriptionEditorEventInput(UUID.randomUUID(), input.editingContextId(), representationId);
        var flux = this.formDescriptionEditorSubscriptionRunner.run(formDescriptionEditorEventInput);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        return flux.filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(FormDescriptionEditorRefreshedEventPayload.class::isInstance)
                .map(FormDescriptionEditorRefreshedEventPayload.class::cast);
    }
}
