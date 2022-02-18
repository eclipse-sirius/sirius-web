/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.collaborative.forms;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.forms.api.FormCreationParameters;
import org.eclipse.sirius.components.collaborative.forms.dto.FormEventInput;
import org.eclipse.sirius.components.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.representations.RepresentationRefreshPolicyRegistry;
import org.eclipse.sirius.components.collaborative.representations.SubscriptionManager;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.junit.jupiter.api.Test;

import reactor.test.StepVerifier;

/**
 * Unit tests of the form event processor.
 *
 * @author sbegaudeau
 */
public class FormEventProcessorTests {

    private static final String FORM_ID = UUID.randomUUID().toString();

    private FormDescription getFormDescription() {
        // @formatter:off
        return FormDescription.newFormDescription(UUID.randomUUID())
                .targetObjectIdProvider(targetObjectIdProvider -> "targetObjectId") //$NON-NLS-1$
                .canCreatePredicate(variableManager -> true)
                .idProvider(variableManager -> UUID.randomUUID().toString())
                .label("formLabel") //$NON-NLS-1$
                .labelProvider(variableManager -> "label") //$NON-NLS-1$
                .groupDescriptions(List.of())
                .pageDescriptions(List.of())
                .build();
        // @formatter:on
    }

    private Predicate<IPayload> getRefreshFormEventPayloadPredicate() {
        return representationEventPayload -> {
            if (representationEventPayload instanceof FormRefreshedEventPayload) {
                FormRefreshedEventPayload payload = (FormRefreshedEventPayload) representationEventPayload;
                return payload.getForm() != null;
            }
            return false;
        };
    }

    @Test
    public void testEmitFormOnSubscription() {
        IInput input = new FormEventInput(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID().toString());

        // @formatter:off
        FormCreationParameters formCreationParameters = FormCreationParameters.newFormCreationParameters(FORM_ID)
                .formDescription(this.getFormDescription())
                .editingContext(new IEditingContext.NoOp())
                .objects(List.of(new Object()))
                .build();
        // @formatter:on

        FormEventProcessor formEventProcessor = new FormEventProcessor(formCreationParameters, List.of(), new SubscriptionManager(), new WidgetSubscriptionManager(),
                new RepresentationRefreshPolicyRegistry());

        // @formatter:off
        StepVerifier.create(formEventProcessor.getOutputEvents(input))
                .expectNextMatches(this.getRefreshFormEventPayloadPredicate())
                .thenCancel()
                .verify();
        // @formatter:on
    }

    @Test
    public void testEmitFormOnRefresh() {
        FormEventInput input = new FormEventInput(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID().toString());

        // @formatter:off
        FormCreationParameters formCreationParameters = FormCreationParameters.newFormCreationParameters(FORM_ID)
                .formDescription(this.getFormDescription())
                .editingContext(new IEditingContext.NoOp())
                .objects(List.of(new Object()))
                .build();
        // @formatter:on

        FormEventProcessor formEventProcessor = new FormEventProcessor(formCreationParameters, List.of(), new SubscriptionManager(), new WidgetSubscriptionManager(),
                new RepresentationRefreshPolicyRegistry());

        Runnable performRefresh = () -> formEventProcessor.refresh(new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, input.getFormId(), input));

        // @formatter:off
        StepVerifier.create(formEventProcessor.getOutputEvents(input))
                .expectNextMatches(this.getRefreshFormEventPayloadPredicate())
                .then(performRefresh)
                .expectNextMatches(this.getRefreshFormEventPayloadPredicate())
                .thenCancel()
                .verify();
        // @formatter:on
    }

    @Test
    public void testCompleteOnDispose() {
        FormEventInput input = new FormEventInput(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID().toString());

        // @formatter:off
        FormCreationParameters formCreationParameters = FormCreationParameters.newFormCreationParameters(FORM_ID)
                .formDescription(this.getFormDescription())
                .editingContext(new IEditingContext.NoOp())
                .objects(List.of(new Object()))
                .build();
        // @formatter:on

        FormEventProcessor formEventProcessor = new FormEventProcessor(formCreationParameters, List.of(), new SubscriptionManager(), new WidgetSubscriptionManager(),
                new RepresentationRefreshPolicyRegistry());

        Runnable disposeFormEventProcessor = () -> formEventProcessor.dispose();

        // @formatter:off
        StepVerifier.create(formEventProcessor.getOutputEvents(input))
                .expectNextMatches(this.getRefreshFormEventPayloadPredicate())
                .then(disposeFormEventProcessor)
                .expectComplete()
                .verify();
        // @formatter:on
    }
}
