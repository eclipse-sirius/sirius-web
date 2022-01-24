/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.handlers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.core.api.ErrorPayload;
import org.eclipse.sirius.web.core.api.IEditService;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeDescription;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeKind;
import org.eclipse.sirius.web.spring.collaborative.dto.CreateChildInput;
import org.eclipse.sirius.web.spring.collaborative.dto.CreateChildSuccessPayload;
import org.eclipse.sirius.web.spring.collaborative.messages.ICollaborativeMessageService;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Tests of the create child event handler.
 *
 * @author sbegaudeau
 */
public class CreateChildEventHandlerTests {

    @Test
    public void testCreateChild() {
        Object createdObject = new Object();
        IEditService editService = new IEditService.NoOp() {
            @Override
            public Optional<Object> createChild(IEditingContext editingContext, Object object, String childCreationDescriptionId) {
                return Optional.of(createdObject);
            }
        };

        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        One<IPayload> payloadSink = Sinks.one();

        this.handle(payloadSink, changeDescriptionSink, editService);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.SEMANTIC_CHANGE);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(CreateChildSuccessPayload.class);
        assertThat(((CreateChildSuccessPayload) payload).getObject()).isEqualTo(createdObject);
    }

    @Test
    public void testCreateChildFailed() {
        IEditService editService = new IEditService.NoOp() {
            @Override
            public Optional<Object> createChild(IEditingContext editingContext, Object object, String childCreationDescriptionId) {
                return Optional.empty();
            }
        };

        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        One<IPayload> payloadSink = Sinks.one();

        this.handle(payloadSink, changeDescriptionSink, editService);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.NOTHING);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(ErrorPayload.class);
    }

    void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditService editService) {
        IObjectService objectService = new IObjectService.NoOp() {
            @Override
            public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
                return Optional.of(new Object());
            }
        };

        CreateChildEventHandler handler = new CreateChildEventHandler(objectService, editService, new ICollaborativeMessageService.NoOp(), new SimpleMeterRegistry());
        var input = new CreateChildInput(UUID.randomUUID(), UUID.randomUUID().toString(), "parentObjectId", "childCreationDescriptionId"); //$NON-NLS-1$//$NON-NLS-2$

        IEditingContext editingContext = () -> UUID.randomUUID().toString();
        assertThat(handler.canHandle(editingContext, input)).isTrue();
        handler.handle(payloadSink, changeDescriptionSink, editingContext, input);
    }
}
