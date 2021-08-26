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

import java.util.UUID;

import org.eclipse.sirius.web.core.api.ErrorPayload;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IInput;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.spring.collaborative.api.EventHandlerResponse;
import org.eclipse.sirius.web.spring.collaborative.api.IQueryService;
import org.eclipse.sirius.web.spring.collaborative.dto.QueryBasedIntInput;
import org.eclipse.sirius.web.spring.collaborative.dto.QueryBasedIntSuccessPayload;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

/**
 * Tests of the query based int event handler.
 *
 * @author fbarbin
 */
public class QueryBasedIntEventHandlerTests {
    private static final int EXPECTED_RESULT_10 = 10;

    @Test
    public void testQueryBasedInt() {
        IQueryService queryService = new IQueryService() {

            @Override
            public IPayload execute(IEditingContext editingContext, QueryBasedIntInput input) {
                return new QueryBasedIntSuccessPayload(UUID.randomUUID(), EXPECTED_RESULT_10);
            }
        };

        EventHandlerResponse response = this.handle(queryService);
        assertThat(response.getPayload()).isInstanceOf(QueryBasedIntSuccessPayload.class);
        assertThat(((QueryBasedIntSuccessPayload) response.getPayload()).getResult()).isEqualTo(EXPECTED_RESULT_10);
    }

    @Test
    public void testQueryBasedIntFailed() {
        IQueryService queryService = new IQueryService() {
            @Override
            public IPayload execute(IEditingContext editingContext, QueryBasedIntInput input) {
                return new ErrorPayload(UUID.randomUUID(), "An error occured"); //$NON-NLS-1$
            }
        };
        EventHandlerResponse response = this.handle(queryService);
        assertThat(response.getPayload()).isInstanceOf(ErrorPayload.class);
    }

    private EventHandlerResponse handle(IQueryService queryService) {
        QueryBasedIntEventHandler queryBasedIntEventHandler = new QueryBasedIntEventHandler(new NoOpCollaborativeMessageService(), new SimpleMeterRegistry(), queryService);
        IInput input = new QueryBasedIntInput(UUID.randomUUID(), "aql:self"); //$NON-NLS-1$
        assertThat(queryBasedIntEventHandler.canHandle(input)).isTrue();

        IEditingContext editingContext = () -> UUID.randomUUID();
        EventHandlerResponse response = queryBasedIntEventHandler.handle(editingContext, input);
        return response;
    }
}
