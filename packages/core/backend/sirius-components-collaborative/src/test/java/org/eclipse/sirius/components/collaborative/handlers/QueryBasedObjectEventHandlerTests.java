/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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

import java.util.Map;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IQueryService;
import org.eclipse.sirius.components.collaborative.dto.QueryBasedObjectInput;
import org.eclipse.sirius.components.collaborative.dto.QueryBasedObjectSuccessPayload;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Tests of the query based Object event handler.
 *
 * @author fbarbin
 */
public class QueryBasedObjectEventHandlerTests {
    private static final Object EXPECTED_RESULT = new Object();

    @Test
    public void testQueryBasedObject() {
        IQueryService queryService = new IQueryService.NoOp() {

            @Override
            public IPayload execute(IEditingContext editingContext, QueryBasedObjectInput input) {
                return new QueryBasedObjectSuccessPayload(UUID.randomUUID(), EXPECTED_RESULT);
            }
        };

        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        One<IPayload> payloadSink = Sinks.one();

        this.handle(payloadSink, changeDescriptionSink, queryService);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.NOTHING);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(QueryBasedObjectSuccessPayload.class);
        assertThat(((QueryBasedObjectSuccessPayload) payload).getResult()).isEqualTo(EXPECTED_RESULT);
    }

    @Test
    public void testQueryBasedObjectFailed() {
        IQueryService queryService = new IQueryService.NoOp() {
            @Override
            public IPayload execute(IEditingContext editingContext, QueryBasedObjectInput input) {
                return new ErrorPayload(UUID.randomUUID(), "An error occured");
            }
        };
        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        One<IPayload> payloadSink = Sinks.one();

        this.handle(payloadSink, changeDescriptionSink, queryService);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.NOTHING);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(ErrorPayload.class);
    }

    private void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IQueryService queryService) {
        QueryBasedObjectEventHandler queryBasedObjectEventHandler = new QueryBasedObjectEventHandler(new ICollaborativeMessageService.NoOp(), new SimpleMeterRegistry(), queryService);
        IInput input = new QueryBasedObjectInput(UUID.randomUUID(), "", Map.of());
        assertThat(queryBasedObjectEventHandler.canHandle(new IEditingContext.NoOp(), input)).isTrue();

        IEditingContext editingContext = () -> UUID.randomUUID().toString();
        queryBasedObjectEventHandler.handle(payloadSink, changeDescriptionSink, editingContext, input);
    }
}
