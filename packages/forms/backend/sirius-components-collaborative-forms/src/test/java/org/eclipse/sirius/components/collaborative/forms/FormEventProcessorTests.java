/*******************************************************************************
 * Copyright (c) 2022, 2026 Obeo.
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

import org.eclipse.sirius.components.collaborative.forms.api.IFormPostProcessor;
import org.eclipse.sirius.components.collaborative.forms.dto.FormCapabilitiesRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.forms.dto.FormEventInput;
import org.eclipse.sirius.components.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.forms.services.api.IFormCapabilitiesService;
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
        return FormDescription.newFormDescription(UUID.randomUUID().toString())
                .targetObjectIdProvider(targetObjectIdProvider -> "targetObjectId")
                .canCreatePredicate(variableManager -> true)
                .idProvider(variableManager -> UUID.randomUUID().toString())
                .label("formLabel")
                .labelProvider(variableManager -> "label")
                .pageDescriptions(List.of())
                .iconURLsProvider(variableManager -> List.of())
                .build();
    }

    private Predicate<IPayload> getRefreshFormCapabilitiesEventPayloadPredicate() {
        return representationEventPayload -> {
            if (representationEventPayload instanceof FormCapabilitiesRefreshedEventPayload payload) {
                return payload.capabilities() != null;
            }
            return false;
        };
    }

    private Predicate<IPayload> getRefreshFormEventPayloadPredicate() {
        return representationEventPayload -> {
            if (representationEventPayload instanceof FormRefreshedEventPayload payload) {
                return payload.form() != null;
            }
            return false;
        };
    }

    @Test
    public void testEmitFormOnSubscription() {
        IInput input = new FormEventInput(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID().toString());
        FormEventProcessor formEventProcessor = this.createFormEventProcessor();

        StepVerifier.create(formEventProcessor.getOutputEvents(input))
                .expectNextMatches(this.getRefreshFormCapabilitiesEventPayloadPredicate())
                .expectNextMatches(this.getRefreshFormEventPayloadPredicate())
                .thenCancel()
                .verify();
    }

    @Test
    public void testEmitFormOnUpdate() {
        FormEventInput input = new FormEventInput(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID().toString());
        FormEventProcessor formEventProcessor = this.createFormEventProcessor();

        Runnable performUpdate = () -> formEventProcessor.update(input, (org.eclipse.sirius.components.forms.Form) formEventProcessor.getRepresentation());

        StepVerifier.create(formEventProcessor.getOutputEvents(input))
                .expectNextMatches(this.getRefreshFormCapabilitiesEventPayloadPredicate())
                .expectNextMatches(this.getRefreshFormEventPayloadPredicate())
                .then(performUpdate)
                .expectNextMatches(this.getRefreshFormEventPayloadPredicate())
                .thenCancel()
                .verify();
    }

    @Test
    public void testCompleteOnDispose() {
        FormEventInput input = new FormEventInput(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID().toString());
        FormEventProcessor formEventProcessor = this.createFormEventProcessor();

        Runnable disposeFormEventProcessor = formEventProcessor::dispose;

        StepVerifier.create(formEventProcessor.getOutputEvents(input))
                .expectNextMatches(this.getRefreshFormCapabilitiesEventPayloadPredicate())
                .expectNextMatches(this.getRefreshFormEventPayloadPredicate())
                .then(disposeFormEventProcessor)
                .expectComplete()
                .verify();
    }

    private FormEventProcessor createFormEventProcessor() {
        IEditingContext editingContext = new IEditingContext.NoOp();

        var formDescription = this.getFormDescription();
        var object = new Object();
        var formContext = new FormContext(FORM_ID, null, formDescription, object, List.of());
        var form = new FormCreationService(List.of(), java.util.Optional.of(new IFormPostProcessor.NoOp()))
                .create(editingContext, formDescription, object, formContext);
        FormEventProcessor formEventProcessor = new FormEventProcessor(editingContext, formContext.withForm(form), List.of(), List.of(), new SubscriptionManager(),
                new org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService.NoOp(), new IFormCapabilitiesService.NoOp());
        return formEventProcessor;
    }
}
