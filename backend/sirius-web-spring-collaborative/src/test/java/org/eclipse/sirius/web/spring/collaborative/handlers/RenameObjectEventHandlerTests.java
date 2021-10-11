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
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.sirius.web.core.api.IEditService;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeDescription;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeKind;
import org.eclipse.sirius.web.spring.collaborative.dto.RenameObjectInput;
import org.eclipse.sirius.web.spring.collaborative.dto.RenameObjectSuccessPayload;
import org.eclipse.sirius.web.spring.collaborative.messages.ICollaborativeMessageService;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Unit tests of the rename object event handler.
 *
 * @author arichard
 */
public class RenameObjectEventHandlerTests {
    @Test
    public void testRenameObject() {
        IObjectService objectService = new IObjectService.NoOp() {
            @Override
            public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
                return Optional.of(new Object());
            }

            @Override
            public Optional<String> getLabelField(Object object) {
                return Optional.of("label"); //$NON-NLS-1$
            }
        };

        AtomicBoolean hasBeenCalled = new AtomicBoolean();
        IEditService editService = new IEditService.NoOp() {
            @Override
            public void editLabel(Object object, String labelField, String newValue) {
                hasBeenCalled.set(true);
            }
        };

        RenameObjectEventHandler handler = new RenameObjectEventHandler(new ICollaborativeMessageService.NoOp(), objectService, editService, new SimpleMeterRegistry());
        var input = new RenameObjectInput(UUID.randomUUID(), UUID.randomUUID(), "objectId", "newName"); //$NON-NLS-1$ //$NON-NLS-2$
        IEditingContext editingContext = () -> UUID.randomUUID();

        assertThat(handler.canHandle(editingContext, input)).isTrue();

        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        One<IPayload> payloadSink = Sinks.one();

        handler.handle(payloadSink, changeDescriptionSink, editingContext, input);
        assertThat(hasBeenCalled.get()).isTrue();

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.SEMANTIC_CHANGE);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(RenameObjectSuccessPayload.class);
    }
}
