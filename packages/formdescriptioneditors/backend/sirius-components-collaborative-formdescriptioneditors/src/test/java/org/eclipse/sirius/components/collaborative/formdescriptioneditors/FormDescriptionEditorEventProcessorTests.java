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
package org.eclipse.sirius.components.collaborative.formdescriptioneditors;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicyRegistry;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.IFormDescriptionEditorContext;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.IFormDescriptionEditorCreationService;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.dto.FormDescriptionEditorEventInput;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.dto.FormDescriptionEditorRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.representations.SubscriptionManager;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditor;
import org.junit.jupiter.api.Test;

import reactor.test.StepVerifier;

/**
 * Unit tests of the form description editor event processor.
 *
 * @author arichard
 */
public class FormDescriptionEditorEventProcessorTests {

    private static final String FORMDESCRIPTIONEDITOR_ID = UUID.randomUUID().toString();

    private static final String FORMDESCRIPTIONEDITOR_DESCRIPTION_ID = UUID.randomUUID().toString();

    private static final FormDescriptionEditor INITIAL_TEST_FORMDESCRIPTIONEDITOR = FormDescriptionEditor.newFormDescriptionEditor(FORMDESCRIPTIONEDITOR_ID)
            .descriptionId(FORMDESCRIPTIONEDITOR_DESCRIPTION_ID)
            .label(String.valueOf(0))
            .targetObjectId("targetObjectId")
            .pages(List.of())
            .build();

    private final IFormDescriptionEditorCreationService formDescriptionEditorCreationService = new MockFormDescriptionEditorCreationService(INITIAL_TEST_FORMDESCRIPTIONEDITOR);

    private final IFormDescriptionEditorContext formDescriptionEditorContext = new IFormDescriptionEditorContext.NoOp() {
        @Override
        public FormDescriptionEditor getFormDescriptionEditor() {
            return INITIAL_TEST_FORMDESCRIPTIONEDITOR;
        }
    };

    private Predicate<IPayload> getRefreshFormDescriptionEditorEventPayloadPredicate(int count) {
        return representationEventPayload -> {
            if (representationEventPayload instanceof FormDescriptionEditorRefreshedEventPayload) {
                FormDescriptionEditorRefreshedEventPayload payload = (FormDescriptionEditorRefreshedEventPayload) representationEventPayload;
                return payload.formDescriptionEditor() != null && payload.formDescriptionEditor().getLabel().equals(String.valueOf(count));
            }
            return false;
        };
    }

    @Test
    public void testEmitFormDescriptionEditorOnSubscription() {
        IInput input = new FormDescriptionEditorEventInput(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID().toString());
        FormDescriptionEditorEventProcessor formDescriptionEditorEventProcessor = this.createFormDescriptionEditorEventProcessor();

        StepVerifier.create(formDescriptionEditorEventProcessor.getOutputEvents(input))
                .expectNextMatches(this.getRefreshFormDescriptionEditorEventPayloadPredicate(1))
                .thenCancel()
                .verify();
    }


    @Test
    public void testEmitFormDescriptionEditorOnRefresh() {
        FormDescriptionEditorEventInput input = new FormDescriptionEditorEventInput(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID().toString());
        FormDescriptionEditorEventProcessor formDescriptionEditorEventProcessor = this.createFormDescriptionEditorEventProcessor();

        Runnable performRefresh = () -> formDescriptionEditorEventProcessor.refresh(new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, input.formDescriptionEditorId(), input));

        StepVerifier.create(formDescriptionEditorEventProcessor.getOutputEvents(input))
                .expectNextMatches(this.getRefreshFormDescriptionEditorEventPayloadPredicate(1))
                .then(performRefresh)
                .expectNextMatches(this.getRefreshFormDescriptionEditorEventPayloadPredicate(2))
                .thenCancel()
                .verify();
    }

    @Test
    public void testUpdateInitialFormDescriptionEditorForNewSubscription() {
        FormDescriptionEditorEventInput input = new FormDescriptionEditorEventInput(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID().toString());
        FormDescriptionEditorEventProcessor formDescriptionEditorEventProcessor = this.createFormDescriptionEditorEventProcessor();

        Runnable performRefresh = () -> formDescriptionEditorEventProcessor.refresh(new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, input.formDescriptionEditorId(), input));

        StepVerifier.create(formDescriptionEditorEventProcessor.getOutputEvents(input))
                .expectNextMatches(this.getRefreshFormDescriptionEditorEventPayloadPredicate(1))
                .then(performRefresh)
                .expectNextMatches(this.getRefreshFormDescriptionEditorEventPayloadPredicate(2))
                .thenCancel()
                .verify();

        StepVerifier.create(formDescriptionEditorEventProcessor.getOutputEvents(input))
                .expectNextMatches(this.getRefreshFormDescriptionEditorEventPayloadPredicate(2))
                .thenCancel()
                .verify();
    }

    @Test
    public void testCompleteOnDispose() {
        FormDescriptionEditorEventInput input = new FormDescriptionEditorEventInput(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID().toString());
        FormDescriptionEditorEventProcessor formDescriptionEditorEventProcessor = this.createFormDescriptionEditorEventProcessor();

        Runnable disposeFormDescriptionEditorEventProcessor = () -> formDescriptionEditorEventProcessor.dispose();

        StepVerifier.create(formDescriptionEditorEventProcessor.getOutputEvents(input))
                .expectNextMatches(this.getRefreshFormDescriptionEditorEventPayloadPredicate(1))
                .then(disposeFormDescriptionEditorEventProcessor)
                .expectComplete()
                .verify();
    }

    private FormDescriptionEditorEventProcessor createFormDescriptionEditorEventProcessor() {
        var parameters = new FormDescriptionEditorEventProcessorParameters(
                new IEditingContext.NoOp(),
                this.formDescriptionEditorContext,
                List.of(),
                new SubscriptionManager(),
                this.formDescriptionEditorCreationService,
                new IRepresentationDescriptionSearchService.NoOp(),
                new IRepresentationSearchService.NoOp(),
                new IRepresentationRefreshPolicyRegistry.NoOp());
        FormDescriptionEditorEventProcessor formDescriptionEditorEventProcessor = new FormDescriptionEditorEventProcessor(parameters);
        return formDescriptionEditorEventProcessor;
    }
}
