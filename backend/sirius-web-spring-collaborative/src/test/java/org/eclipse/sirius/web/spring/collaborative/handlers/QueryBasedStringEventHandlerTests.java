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
import org.eclipse.sirius.web.spring.collaborative.api.EventHandlerResponse;
import org.eclipse.sirius.web.spring.collaborative.api.IQueryService;
import org.eclipse.sirius.web.spring.collaborative.dto.QueryBasedStringInput;
import org.eclipse.sirius.web.spring.collaborative.dto.QueryBasedStringSuccessPayload;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

/**
 * Tests of the query based String event handler.
 *
 * @author fbarbin
 */
public class QueryBasedStringEventHandlerTests {
    private static final String EXPECTED_RESULT = "result"; //$NON-NLS-1$

    @Test
    public void testQueryBasedString() {
        // The EMFQueryService implementation is already tested by another class.
        IQueryService queryService = new IQueryService.NoOp() {

            @Override
            public IPayload execute(IEditingContext editingContext, QueryBasedStringInput input) {
                return new QueryBasedStringSuccessPayload(UUID.randomUUID(), EXPECTED_RESULT);
            }
        };

        EventHandlerResponse response = this.handle(queryService);
        assertThat(response.getPayload()).isInstanceOf(QueryBasedStringSuccessPayload.class);
        assertThat(((QueryBasedStringSuccessPayload) response.getPayload()).getResult()).isEqualTo(EXPECTED_RESULT);
    }

    @Test
    public void testQueryBasedStringFailed() {
        IQueryService queryService = new IQueryService.NoOp() {
            @Override
            public IPayload execute(IEditingContext editingContext, QueryBasedStringInput input) {
                return new ErrorPayload(UUID.randomUUID(), "An error occured"); //$NON-NLS-1$
            }
        };
        EventHandlerResponse response = this.handle(queryService);
        assertThat(response.getPayload()).isInstanceOf(ErrorPayload.class);
    }

    private EventHandlerResponse handle(IQueryService queryService) {
        QueryBasedStringEventHandler queryBasedStringEventHandler = new QueryBasedStringEventHandler(new NoOpCollaborativeMessageService(), new SimpleMeterRegistry(), queryService);
        IInput input = new QueryBasedStringInput(UUID.randomUUID(), "", Map.of()); //$NON-NLS-1$
        assertThat(queryBasedStringEventHandler.canHandle(input)).isTrue();

        IEditingContext editingContext = () -> UUID.randomUUID();
        EventHandlerResponse response = queryBasedStringEventHandler.handle(editingContext, input);
        return response;
    }
}
