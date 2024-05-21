/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.gantt.handlers;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.gantt.GanttChangeKind;
import org.eclipse.sirius.components.collaborative.gantt.api.IGanttContext;
import org.eclipse.sirius.components.collaborative.gantt.api.IGanttEventHandler;
import org.eclipse.sirius.components.collaborative.gantt.api.IGanttInput;
import org.eclipse.sirius.components.collaborative.gantt.api.IGanttTaskService;
import org.eclipse.sirius.components.collaborative.gantt.dto.input.ChangeGanttColumnInput;
import org.eclipse.sirius.components.collaborative.gantt.message.ICollaborativeGanttMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handle "Change Gantt column" event.
 *
 * @author lfasani
 */
@Service
public class ChangeGanttColumnEventHandler implements IGanttEventHandler {

    private final IGanttTaskService ganttTaskService;

    private final ICollaborativeGanttMessageService messageService;

    private final Counter counter;

    public ChangeGanttColumnEventHandler(IGanttTaskService ganttLaneService, ICollaborativeGanttMessageService messageService, MeterRegistry meterRegistry) {
        this.messageService = Objects.requireNonNull(messageService);
        this.ganttTaskService = Objects.requireNonNull(ganttLaneService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IGanttInput ganttInput) {
        return ganttInput instanceof ChangeGanttColumnInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IGanttContext ganttContext, IGanttInput ganttInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(ganttInput.getClass().getSimpleName(), ChangeGanttColumnInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(ganttInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, ganttInput.representationId(), ganttInput);

        if (ganttInput instanceof ChangeGanttColumnInput input) {
            payload = this.ganttTaskService.changeGanttColumn(input, editingContext, ganttContext);
            changeDescription = new ChangeDescription(GanttChangeKind.GANTT_REPRESENTATION_UPDATE, ganttInput.representationId(), ganttInput);
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
