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
package org.eclipse.sirius.components.collaborative.handlers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextActionHandler;
import org.eclipse.sirius.components.collaborative.dto.InvokeEditingContextActionInput;
import org.eclipse.sirius.components.collaborative.dto.InvokeEditingContextActionSuccessPayload;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Used to test the execution of an editing context action.
 *
 * @author sbegaudeau
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class InvokeEditingContextActionEventHandlerTests {
    @Test
    public void testInvokeEditingContextAction() {
        AtomicBoolean hasBeenCalled = new AtomicBoolean();

        var handler = new IEditingContextActionHandler() {

            @Override
            public IStatus handle(IEditingContext editingContext, String actionId) {
                hasBeenCalled.set(true);
                return new Success(ChangeKind.SEMANTIC_CHANGE, Map.of());
            }

            @Override
            public boolean canHandle(IEditingContext editingContext, String actionId) {
                return true;
            }
        };

        var eventHandler = new InvokeEditingContextActionEventHandler(List.of(handler), new ICollaborativeMessageService.NoOp(), new SimpleMeterRegistry());

        var input = new InvokeEditingContextActionInput(UUID.randomUUID(), "editingContextId", "actionId"); //$NON-NLS-1$//$NON-NLS-2$
        IEditingContext editingContext = () -> "editingContextId"; //$NON-NLS-1$

        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        One<IPayload> payloadSink = Sinks.one();

        assertThat(eventHandler.canHandle(editingContext, input)).isTrue();
        eventHandler.handle(payloadSink, changeDescriptionSink, editingContext, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.SEMANTIC_CHANGE);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(InvokeEditingContextActionSuccessPayload.class);
        assertThat(hasBeenCalled.get()).isTrue();
    }

    @Test
    public void testErrorNoHandlerFound() {
        var eventHandler = new InvokeEditingContextActionEventHandler(List.of(), new ICollaborativeMessageService.NoOp(), new SimpleMeterRegistry());

        var input = new InvokeEditingContextActionInput(UUID.randomUUID(), "editingContextId", "actionId"); //$NON-NLS-1$//$NON-NLS-2$
        IEditingContext editingContext = () -> "editingContextId"; //$NON-NLS-1$

        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        One<IPayload> payloadSink = Sinks.one();

        assertThat(eventHandler.canHandle(editingContext, input)).isTrue();
        eventHandler.handle(payloadSink, changeDescriptionSink, editingContext, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.NOTHING);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(ErrorPayload.class);

        ErrorPayload errorPayload = (ErrorPayload) payload;
        assertThat(errorPayload.getMessage()).isEqualTo("No handler could be found for action with id actionId"); //$NON-NLS-1$
    }
}
