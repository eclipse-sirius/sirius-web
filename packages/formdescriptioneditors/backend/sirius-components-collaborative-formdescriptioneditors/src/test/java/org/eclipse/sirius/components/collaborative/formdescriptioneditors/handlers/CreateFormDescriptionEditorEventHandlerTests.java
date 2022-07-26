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
package org.eclipse.sirius.components.collaborative.formdescriptioneditors.handlers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationSuccessPayload;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.TestFormDescriptionEditorBuilder;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.IFormDescriptionEditorCreationService;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.messages.ICollaborativeFormDescriptionEditorMessageService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditor;
import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorDescription;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Tests of the create representation event handler.
 *
 * @author arichard
 */
public class CreateFormDescriptionEditorEventHandlerTests {
    @Test
    public void testFormDescriptionEditorCreation() {
        IRepresentationDescriptionSearchService representationDescriptionSearchService = new IRepresentationDescriptionSearchService.NoOp() {
            @Override
            public Optional<IRepresentationDescription> findById(IEditingContext editingContext, String id) {
                // @formatter:off
                FormDescriptionEditorDescription formDescriptionEditorDescription = FormDescriptionEditorDescription.newFormDescriptionEditorDescription(UUID.randomUUID().toString())
                        .label("label") //$NON-NLS-1$
                        .canCreatePredicate(variableManager -> Boolean.TRUE)
                        .targetObjectIdProvider(variableManager -> "targetObjectId") //$NON-NLS-1$
                        .build();
                // @formatter:on

                return Optional.of(formDescriptionEditorDescription);
            }
        };

        AtomicBoolean hasBeenCalled = new AtomicBoolean();
        IFormDescriptionEditorCreationService formDescriptionEditorCreationService = new IFormDescriptionEditorCreationService.NoOp() {
            @Override
            public FormDescriptionEditor create(String label, Object targetObject, FormDescriptionEditorDescription formDescriptionEditorDescription, IEditingContext editingContext) {
                hasBeenCalled.set(true);
                return new TestFormDescriptionEditorBuilder().getFormDescriptionEditor(UUID.randomUUID().toString());
            }

        };

        IObjectService objectService = new IObjectService.NoOp() {
            @Override
            public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
                return Optional.of(new Object());
            }
        };

        CreateFormDescriptionEditorEventHandler handler = new CreateFormDescriptionEditorEventHandler(representationDescriptionSearchService, objectService,
                new ICollaborativeFormDescriptionEditorMessageService.NoOp(), formDescriptionEditorCreationService, new SimpleMeterRegistry());

        var input = new CreateRepresentationInput(UUID.randomUUID(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), "objectId", "representationName"); //$NON-NLS-1$//$NON-NLS-2$
        assertThat(handler.canHandle(null, input)).isTrue();

        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        One<IPayload> payloadSink = Sinks.one();

        IEditingContext editingContext = () -> UUID.randomUUID().toString();
        handler.handle(payloadSink, changeDescriptionSink, editingContext, input);
        assertThat(hasBeenCalled.get()).isTrue();

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.REPRESENTATION_CREATION);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(CreateRepresentationSuccessPayload.class);
    }
}
