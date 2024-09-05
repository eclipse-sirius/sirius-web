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
package org.eclipse.sirius.components.collaborative.selection.handlers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.collaborative.selection.dto.SelectionDialogTreeEventInput;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.junit.jupiter.api.Test;

import reactor.core.publisher.Sinks;

/**
 * Tests the selection description message event handler.
 *
 * @author frouene
 */
public class SelectionDescriptionMessageEventHandlerTests {

    @Test
    public void testSelectionDescriptionMessageEventHandlerWrongInput() {
        var collaborativeMessageService = new ICollaborativeMessageService.NoOp() {
            @Override
            public String invalidInput(String expectedInputTypeName, String receivedInputTypeName) {
                return "invalid input";
            }
        };

        var handler = new SelectionDescriptionMessageEventHandler(collaborativeMessageService, new IObjectService.NoOp());

        Sinks.One<IPayload> payloadSink = Sinks.one();
        Sinks.Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();

        var wrongInput = new SelectionDialogTreeEventInput(UUID.randomUUID(), "editingContextId", "representationId");

        handler.handle(payloadSink, changeDescriptionSink, new IEditingContext.NoOp(), wrongInput);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(ErrorPayload.class);
        assert payload != null;
        assertThat(((ErrorPayload) payload).message()).isEqualTo("invalid input");
    }

}
