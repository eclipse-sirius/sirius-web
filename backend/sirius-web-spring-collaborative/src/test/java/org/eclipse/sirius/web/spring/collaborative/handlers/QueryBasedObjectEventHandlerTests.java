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
import org.eclipse.sirius.web.spring.collaborative.dto.QueryBasedObjectInput;
import org.eclipse.sirius.web.spring.collaborative.dto.QueryBasedObjectSuccessPayload;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

/**
 * Tests of the query based Object event handler.
 *
 * @author fbarbin
 */
public class QueryBasedObjectEventHandlerTests {
    private static final Object EXPECTED_RESULT = new Object();

    @Test
    public void testQueryBasedObject() {
        // The EMFQueryService implementation is already tested by another class.
        IQueryService queryService = new IQueryService.NoOp() {

            @Override
            public IPayload execute(IEditingContext editingContext, QueryBasedObjectInput input) {
                return new QueryBasedObjectSuccessPayload(UUID.randomUUID(), EXPECTED_RESULT);
            }
        };

        EventHandlerResponse response = this.handle(queryService);
        assertThat(response.getPayload()).isInstanceOf(QueryBasedObjectSuccessPayload.class);
        assertThat(((QueryBasedObjectSuccessPayload) response.getPayload()).getResult()).isEqualTo(EXPECTED_RESULT);
    }

    @Test
    public void testQueryBasedObjectFailed() {
        IQueryService queryService = new IQueryService.NoOp() {
            @Override
            public IPayload execute(IEditingContext editingContext, QueryBasedObjectInput input) {
                return new ErrorPayload(UUID.randomUUID(), "An error occured"); //$NON-NLS-1$
            }
        };
        EventHandlerResponse response = this.handle(queryService);
        assertThat(response.getPayload()).isInstanceOf(ErrorPayload.class);
    }

    private EventHandlerResponse handle(IQueryService queryService) {
        QueryBasedObjectEventHandler queryBasedObjectEventHandler = new QueryBasedObjectEventHandler(new NoOpCollaborativeMessageService(), new SimpleMeterRegistry(), queryService);
        IInput input = new QueryBasedObjectInput(UUID.randomUUID(), "", Map.of()); //$NON-NLS-1$
        assertThat(queryBasedObjectEventHandler.canHandle(input)).isTrue();

        IEditingContext editingContext = () -> UUID.randomUUID();
        EventHandlerResponse response = queryBasedObjectEventHandler.handle(editingContext, input);
        return response;
    }
}
