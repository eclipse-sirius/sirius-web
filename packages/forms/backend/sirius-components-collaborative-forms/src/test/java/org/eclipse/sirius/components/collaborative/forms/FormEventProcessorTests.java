/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.forms.api.FormCreationParameters;
import org.eclipse.sirius.components.collaborative.forms.api.IFormPostProcessor;
import org.eclipse.sirius.components.collaborative.forms.configuration.FormEventProcessorConfiguration;
import org.eclipse.sirius.components.collaborative.forms.dto.FormEventInput;
import org.eclipse.sirius.components.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.representations.RepresentationRefreshPolicyRegistry;
import org.eclipse.sirius.components.collaborative.representations.SubscriptionManager;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IObjectService;
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
                .build();
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
                .expectNextMatches(this.getRefreshFormEventPayloadPredicate())
                .thenCancel()
                .verify();
    }

    @Test
    public void testEmitFormOnRefresh() {
        FormEventInput input = new FormEventInput(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID().toString());
        FormEventProcessor formEventProcessor = this.createFormEventProcessor();

        Runnable performRefresh = () -> formEventProcessor.refresh(new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, input.formId(), input));

        StepVerifier.create(formEventProcessor.getOutputEvents(input))
                .expectNextMatches(this.getRefreshFormEventPayloadPredicate())
                .then(performRefresh)
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
                .expectNextMatches(this.getRefreshFormEventPayloadPredicate())
                .then(disposeFormEventProcessor)
                .expectComplete()
                .verify();
    }

    private FormEventProcessor createFormEventProcessor() {
        IEditingContext editingContext = new IEditingContext.NoOp();

        FormCreationParameters formCreationParameters = FormCreationParameters.newFormCreationParameters(FORM_ID)
                .formDescription(this.getFormDescription())
                .editingContext(editingContext)
                .object(new Object())
                .selection(List.of())
                .build();

        FormEventProcessor formEventProcessor = new FormEventProcessor(
                new FormEventProcessorConfiguration(editingContext, new IObjectService.NoOp(), formCreationParameters, List.of(), List.of()),
                new SubscriptionManager(),
                new WidgetSubscriptionManager(),
                new IRepresentationSearchService.NoOp(),
                new RepresentationRefreshPolicyRegistry(List.of()),
                new IFormPostProcessor.NoOp());
        return formEventProcessor;
    }
}
