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

import java.util.Objects;

import org.eclipse.sirius.web.core.api.ErrorPayload;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IInput;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeDescription;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeKind;
import org.eclipse.sirius.web.spring.collaborative.api.EventHandlerResponse;
import org.eclipse.sirius.web.spring.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.web.spring.collaborative.api.IQueryService;
import org.eclipse.sirius.web.spring.collaborative.api.Monitoring;
import org.eclipse.sirius.web.spring.collaborative.dto.QueryBasedIntInput;
import org.eclipse.sirius.web.spring.collaborative.messages.ICollaborativeMessageService;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

/**
 * Handler used to execute query based int.
 *
 * @author fbarbin
 */
@Service
public class QueryBasedIntEventHandler implements IEditingContextEventHandler {

    private final IQueryService queryService;

    private final ICollaborativeMessageService messageService;

    private final Counter counter;

    public QueryBasedIntEventHandler(ICollaborativeMessageService messageService, MeterRegistry meterRegistry, IQueryService queryService) {
        this.messageService = Objects.requireNonNull(messageService);
        this.queryService = Objects.requireNonNull(queryService);

        // @formatter:off
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
        // @formatter:on
    }

    @Override
    public boolean canHandle(IInput input) {
        return input instanceof QueryBasedIntInput;
    }

    @Override
    public EventHandlerResponse handle(IEditingContext editingContext, IInput input) {
        this.counter.increment();
        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), QueryBasedIntInput.class.getSimpleName());
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId());
        if (input instanceof QueryBasedIntInput) {
            QueryBasedIntInput queryBasedIntInput = (QueryBasedIntInput) input;
            IPayload payload = this.queryService.execute(editingContext, queryBasedIntInput);
            return new EventHandlerResponse(changeDescription, payload);
        }
        ErrorPayload errorPayload = new ErrorPayload(input.getId(), message);
        return new EventHandlerResponse(changeDescription, errorPayload);
    }
}
