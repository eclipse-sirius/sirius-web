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

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.FormDescriptionEditorContext;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.TestFormDescriptionEditorBuilder;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.IFormDescriptionEditorContext;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.dto.AddToolbarActionInput;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.dto.AddToolbarActionSuccessPayload;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.messages.ICollaborativeFormDescriptionEditorMessageService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.view.FormDescription;
import org.eclipse.sirius.components.view.ViewFactory;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Tests of the add toolbar action event handler.
 *
 * @author arichard
 */
public class AddToolbarActionEventHandlerTests {
    @Test
    public void testAddToolbarAction() {
        FormDescription formDescription = ViewFactory.eINSTANCE.createFormDescription();
        var objectService = new IObjectService.NoOp() {
            @Override
            public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
                return Optional.of(formDescription);
            }
        };
        var handler = new AddToolbarActionEventHandler(objectService, new ICollaborativeFormDescriptionEditorMessageService.NoOp(), new SimpleMeterRegistry());
        var input = new AddToolbarActionInput(UUID.randomUUID(), "editingContextId", "representationId", "containerId"); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$

        assertThat(handler.canHandle(input)).isTrue();

        One<IPayload> payloadSink = Sinks.one();
        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        IFormDescriptionEditorContext formDescriptionEditorContext = new FormDescriptionEditorContext(new TestFormDescriptionEditorBuilder().getFormDescriptionEditor(UUID.randomUUID().toString()));

        handler.handle(payloadSink, changeDescriptionSink, new IEditingContext.NoOp(), formDescriptionEditorContext, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.SEMANTIC_CHANGE);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(AddToolbarActionSuccessPayload.class);
        assertThat(formDescription.getToolbarActions()).hasSize(1);
    }
}
