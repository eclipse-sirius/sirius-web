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

import org.eclipse.sirius.web.core.api.IEditService;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeDescription;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeKind;
import org.eclipse.sirius.web.spring.collaborative.dto.CreateRootObjectInput;
import org.eclipse.sirius.web.spring.collaborative.dto.CreateRootObjectSuccessPayload;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Tests of the create root object event handler.
 *
 * @author lfasani
 */
public class CreateRootObjectEventHandlerTests {
    @Test
    public void testCreateChild() {
        Object object = new Object();
        IEditService editService = new IEditService.NoOp() {
            @Override
            public Optional<Object> createRootObject(IEditingContext editingContext, UUID documentId, String domainId, String rootObjectCreationDescriptionId) {
                return Optional.of(object);
            }
        };

        CreateRootObjectEventHandler handler = new CreateRootObjectEventHandler(editService, new NoOpCollaborativeMessageService(), new SimpleMeterRegistry());
        var input = new CreateRootObjectInput(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), "domainId", "rootObjectCreationDescriptionId"); //$NON-NLS-1$//$NON-NLS-2$
        assertThat(handler.canHandle(input)).isTrue();

        IEditingContext editingContext = () -> UUID.randomUUID();

        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        One<IPayload> payloadSink = Sinks.one();

        handler.handle(payloadSink, changeDescriptionSink, editingContext, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.SEMANTIC_CHANGE);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(CreateRootObjectSuccessPayload.class);
        assertThat(((CreateRootObjectSuccessPayload) payload).getObject()).isEqualTo(object);
    }
}
