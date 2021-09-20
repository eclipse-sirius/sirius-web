/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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

import java.util.Map;
import java.util.UUID;

import org.eclipse.sirius.web.core.api.ErrorPayload;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IInput;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeDescription;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeKind;
import org.eclipse.sirius.web.spring.collaborative.api.IQueryService;
import org.eclipse.sirius.web.spring.collaborative.dto.QueryBasedBooleanInput;
import org.eclipse.sirius.web.spring.collaborative.dto.QueryBasedBooleanSuccessPayload;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Tests of the query based boolean event handler.
 *
 * @author fbarbin
 */
public class QueryBasedBooleanEventHandlerTests {
    private static final boolean EXPECTED_RESULT_FALSE = false;

    @Test
    public void testQueryBasedBoolean() {
        // The EMFQueryService implementation is already tested by another class.
        IQueryService queryService = new IQueryService.NoOp() {

            @Override
            public IPayload execute(IEditingContext editingContext, QueryBasedBooleanInput input) {
                return new QueryBasedBooleanSuccessPayload(UUID.randomUUID(), EXPECTED_RESULT_FALSE);
            }
        };

        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        One<IPayload> payloadSink = Sinks.one();

        this.handle(payloadSink, changeDescriptionSink, queryService);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.NOTHING);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(QueryBasedBooleanSuccessPayload.class);
        assertThat(((QueryBasedBooleanSuccessPayload) payload).getResult()).isEqualTo(EXPECTED_RESULT_FALSE);
    }

    @Test
    public void testQueryBasedBooleanFailed() {
        IQueryService queryService = new IQueryService.NoOp() {
            @Override
            public IPayload execute(IEditingContext editingContext, QueryBasedBooleanInput input) {
                return new ErrorPayload(UUID.randomUUID(), "An error occured"); //$NON-NLS-1$
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
        QueryBasedBooleanEventHandler queryBasedBooleanEventHandler = new QueryBasedBooleanEventHandler(new NoOpCollaborativeMessageService(), new SimpleMeterRegistry(), queryService);
        IInput input = new QueryBasedBooleanInput(UUID.randomUUID(), "", Map.of()); //$NON-NLS-1$
        assertThat(queryBasedBooleanEventHandler.canHandle(input)).isTrue();

        IEditingContext editingContext = () -> UUID.randomUUID();
        queryBasedBooleanEventHandler.handle(payloadSink, changeDescriptionSink, editingContext, input);
    }
}
