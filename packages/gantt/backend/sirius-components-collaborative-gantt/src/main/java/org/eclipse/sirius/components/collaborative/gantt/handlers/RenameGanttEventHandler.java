/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.dto.RenameRepresentationSuccessPayload;
import org.eclipse.sirius.components.collaborative.gantt.api.IGanttEventHandler;
import org.eclipse.sirius.components.collaborative.gantt.api.IGanttInput;
import org.eclipse.sirius.components.collaborative.gantt.dto.input.RenameGanttInput;
import org.eclipse.sirius.components.collaborative.gantt.message.ICollaborativeGanttMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.gantt.Gantt;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler used to rename a gantt.
 *
 * @author lfasani
 */
@Service
public class RenameGanttEventHandler implements IGanttEventHandler {

    private final IRepresentationSearchService representationSearchService;

    private final IRepresentationPersistenceService representationPersistenceService;

    private final ICollaborativeGanttMessageService messageService;

    private final Counter counter;

    public RenameGanttEventHandler(IRepresentationSearchService representationSearchService, IRepresentationPersistenceService representationPersistenceService,
            ICollaborativeGanttMessageService messageService, MeterRegistry meterRegistry) {
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);
        this.messageService = Objects.requireNonNull(messageService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IGanttInput ganttInput) {
        return ganttInput instanceof RenameGanttInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, Gantt gantt, IGanttInput ganttInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(ganttInput.getClass().getSimpleName(), RenameGanttInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(ganttInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, ganttInput.representationId(), ganttInput);

        if (ganttInput instanceof RenameGanttInput) {
            RenameGanttInput renameRepresentationInput = (RenameGanttInput) ganttInput;
            String representationId = renameRepresentationInput.representationId();
            String newLabel = renameRepresentationInput.newLabel();
            Optional<Gantt> optionalDiagram = this.representationSearchService.findById(editingContext, representationId, Gantt.class);
            if (optionalDiagram.isPresent()) {
                Gantt currentGantt = optionalDiagram.get();

                Gantt renamedGantt = Gantt.newGantt(currentGantt).label(newLabel).build();
                this.representationPersistenceService.save(renameRepresentationInput, editingContext, renamedGantt);

                payload = new RenameRepresentationSuccessPayload(ganttInput.id(), renamedGantt);
                changeDescription = new ChangeDescription(ChangeKind.REPRESENTATION_RENAMING, renameRepresentationInput.representationId(), ganttInput);
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
