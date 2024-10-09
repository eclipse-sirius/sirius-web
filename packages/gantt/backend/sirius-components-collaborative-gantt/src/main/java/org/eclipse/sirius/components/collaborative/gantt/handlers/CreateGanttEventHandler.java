/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.IRepresentationMetadataPersistenceService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationSuccessPayload;
import org.eclipse.sirius.components.collaborative.gantt.api.IGanttCreationService;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.gantt.Gantt;
import org.eclipse.sirius.components.gantt.description.GanttDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler used to create a new Gantt representation.
 *
 * @author lfasani
 */
@Service
public class CreateGanttEventHandler implements IEditingContextEventHandler {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IRepresentationMetadataPersistenceService representationMetadataPersistenceService;

    private final IRepresentationPersistenceService representationPersistenceService;

    private final IGanttCreationService ganttCreationService;

    private final IObjectService objectService;

    private final ICollaborativeMessageService messageService;

    private final Counter counter;

    public CreateGanttEventHandler(IRepresentationDescriptionSearchService representationDescriptionSearchService, IRepresentationMetadataPersistenceService representationMetadataPersistenceService, IRepresentationPersistenceService representationPersistenceService,
            IGanttCreationService ganttCreationService, IObjectService objectService, ICollaborativeMessageService messageService, MeterRegistry meterRegistry) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.representationMetadataPersistenceService = Objects.requireNonNull(representationMetadataPersistenceService);
        this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);
        this.ganttCreationService = Objects.requireNonNull(ganttCreationService);
        this.objectService = Objects.requireNonNull(objectService);
        this.messageService = Objects.requireNonNull(messageService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        if (input instanceof CreateRepresentationInput createRepresentationInput) {
            return this.representationDescriptionSearchService.findById(editingContext, createRepresentationInput.representationDescriptionId())
                    .filter(GanttDescription.class::isInstance)
                    .isPresent();
        }
        return false;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.counter.increment();

        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), CreateRepresentationInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(input.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);

        if (input instanceof CreateRepresentationInput createRepresentationInput) {
            Optional<GanttDescription> optionalGanttDescription = this.representationDescriptionSearchService.findById(editingContext, createRepresentationInput.representationDescriptionId())
                    .filter(GanttDescription.class::isInstance)
                    .map(GanttDescription.class::cast);
            Optional<Object> optionalObject = this.objectService.getObject(editingContext, createRepresentationInput.objectId());

            if (optionalGanttDescription.isPresent() && optionalObject.isPresent()) {
                GanttDescription ganttDescription = optionalGanttDescription.get();
                Object object = optionalObject.get();

                var variableManager = new VariableManager();
                variableManager.put(VariableManager.SELF, object);
                variableManager.put(GanttDescription.LABEL, createRepresentationInput.representationName());
                String label = ganttDescription.labelProvider().apply(variableManager);

                Gantt gantt = this.ganttCreationService.create(object, ganttDescription, editingContext);
                var representationMetadata = RepresentationMetadata.newRepresentationMetadata(gantt.getId())
                        .kind(gantt.getKind())
                        .label(label)
                        .descriptionId(gantt.descriptionId())
                        .build();

                this.representationMetadataPersistenceService.save(createRepresentationInput, editingContext, representationMetadata, gantt.targetObjectId());
                this.representationPersistenceService.save(createRepresentationInput, editingContext, gantt);

                payload = new CreateRepresentationSuccessPayload(input.id(), representationMetadata);
                changeDescription = new ChangeDescription(ChangeKind.REPRESENTATION_CREATION, editingContext.getId(), input);
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
