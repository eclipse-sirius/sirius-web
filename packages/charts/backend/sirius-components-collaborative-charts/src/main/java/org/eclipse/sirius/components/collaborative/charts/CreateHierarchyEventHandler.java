/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.charts;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.charts.hierarchy.Hierarchy;
import org.eclipse.sirius.components.charts.hierarchy.descriptions.HierarchyDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.IRepresentationMetadataPersistenceService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.charts.messages.ICollaborativeChartsMessageService;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationSuccessPayload;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler used to create a new hierarchy representation.
 *
 * @author sbegaudeau
 */
@Service
public class CreateHierarchyEventHandler implements IEditingContextEventHandler {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IRepresentationMetadataPersistenceService representationMetadataPersistenceService;

    private final IRepresentationPersistenceService representationPersistenceService;

    private final IObjectService objectService;

    private final ICollaborativeChartsMessageService messageService;

    private final HierarchyCreationService hierarchyCreationService;

    private final Counter counter;

    public CreateHierarchyEventHandler(IRepresentationDescriptionSearchService representationDescriptionSearchService, IRepresentationMetadataPersistenceService representationMetadataPersistenceService,
            IRepresentationPersistenceService representationPersistenceService, IObjectService objectService, ICollaborativeChartsMessageService messageService,
            HierarchyCreationService hierarchyCreationService, MeterRegistry meterRegistry) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);
        this.representationMetadataPersistenceService = Objects.requireNonNull(representationMetadataPersistenceService);
        this.objectService = Objects.requireNonNull(objectService);
        this.messageService = Objects.requireNonNull(messageService);
        this.hierarchyCreationService = Objects.requireNonNull(hierarchyCreationService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        if (input instanceof CreateRepresentationInput createRepresentationInput) {
            return this.representationDescriptionSearchService.findById(editingContext, createRepresentationInput.representationDescriptionId())
                    .filter(HierarchyDescription.class::isInstance)
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
            Optional<HierarchyDescription> optionalHierarchyDescription = this.representationDescriptionSearchService.findById(editingContext, createRepresentationInput.representationDescriptionId())
                    .filter(HierarchyDescription.class::isInstance)
                    .map(HierarchyDescription.class::cast);

            Optional<Object> optionalObject = this.objectService.getObject(editingContext, createRepresentationInput.objectId());

            if (optionalHierarchyDescription.isPresent() && optionalObject.isPresent()) {
                Object object = optionalObject.get();
                HierarchyDescription representationDescription = optionalHierarchyDescription.get();

                var variableManager = new VariableManager();
                variableManager.put(VariableManager.SELF, object);
                variableManager.put(HierarchyDescription.LABEL, createRepresentationInput.representationName());
                String label = representationDescription.getLabelProvider().apply(variableManager);

                Hierarchy hierarchy = this.hierarchyCreationService.create(object, representationDescription, editingContext);
                var representationMetadata = RepresentationMetadata.newRepresentationMetadata(hierarchy.getId())
                        .kind(hierarchy.getKind())
                        .label(label)
                        .descriptionId(hierarchy.getDescriptionId())
                        .build();

                this.representationMetadataPersistenceService.save(createRepresentationInput, editingContext, representationMetadata, hierarchy.getTargetObjectId());
                this.representationPersistenceService.save(createRepresentationInput, editingContext, hierarchy);

                payload = new CreateRepresentationSuccessPayload(input.id(), representationMetadata);
                changeDescription = new ChangeDescription(ChangeKind.REPRESENTATION_CREATION, editingContext.getId(), input);
            }
        }

        changeDescriptionSink.tryEmitNext(changeDescription);
        payloadSink.tryEmitValue(payload);
    }

}
