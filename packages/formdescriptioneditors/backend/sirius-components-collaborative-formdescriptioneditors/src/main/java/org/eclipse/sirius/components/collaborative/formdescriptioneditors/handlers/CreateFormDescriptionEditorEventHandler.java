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
package org.eclipse.sirius.components.collaborative.formdescriptioneditors.handlers;

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
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.IFormDescriptionEditorCreationService;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.messages.ICollaborativeFormDescriptionEditorMessageService;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditor;
import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorDescription;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler used to create a new form description editor representation.
 *
 * @author arichard
 */
@Service
public class CreateFormDescriptionEditorEventHandler implements IEditingContextEventHandler {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IRepresentationMetadataPersistenceService representationMetadataPersistenceService;

    private final IRepresentationPersistenceService representationPersistenceService;

    private final IObjectService objectService;

    private final ICollaborativeFormDescriptionEditorMessageService messageService;

    private final IFormDescriptionEditorCreationService formDescriptionEditorCreationService;

    private final Counter counter;

    public CreateFormDescriptionEditorEventHandler(IRepresentationDescriptionSearchService representationDescriptionSearchService,
            IRepresentationMetadataPersistenceService representationMetadataPersistenceService, IRepresentationPersistenceService representationPersistenceService, IObjectService objectService,
            ICollaborativeFormDescriptionEditorMessageService messageService, IFormDescriptionEditorCreationService formDescriptionEditorCreationService, MeterRegistry meterRegistry) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.representationMetadataPersistenceService = Objects.requireNonNull(representationMetadataPersistenceService);
        this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);
        this.objectService = Objects.requireNonNull(objectService);
        this.messageService = Objects.requireNonNull(messageService);
        this.formDescriptionEditorCreationService = Objects.requireNonNull(formDescriptionEditorCreationService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        if (input instanceof CreateRepresentationInput createRepresentationInput) {
            return this.representationDescriptionSearchService.findById(editingContext, createRepresentationInput.representationDescriptionId())
                    .filter(FormDescriptionEditorDescription.class::isInstance)
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

            Optional<FormDescriptionEditorDescription> optionalFormDescriptionEditorDescription = this.representationDescriptionSearchService.findById(editingContext, createRepresentationInput.representationDescriptionId())
                    .filter(FormDescriptionEditorDescription.class::isInstance)
                    .map(FormDescriptionEditorDescription.class::cast);

            Optional<Object> optionalObject = this.objectService.getObject(editingContext, createRepresentationInput.objectId());

            if (optionalFormDescriptionEditorDescription.isPresent() && optionalObject.isPresent()) {
                FormDescriptionEditorDescription representationDescription = optionalFormDescriptionEditorDescription.get();
                Object object = optionalObject.get();

                String label = createRepresentationInput.representationName();

                FormDescriptionEditor formDescriptionEditor = this.formDescriptionEditorCreationService.create(createRepresentationInput, object, representationDescription,
                        editingContext);
                var representationMetadata = new RepresentationMetadata(formDescriptionEditor.getId(), formDescriptionEditor.getKind(), label,
                        formDescriptionEditor.getDescriptionId());
                this.representationMetadataPersistenceService.save(createRepresentationInput, editingContext, representationMetadata, formDescriptionEditor.getTargetObjectId());
                this.representationPersistenceService.save(createRepresentationInput, editingContext, formDescriptionEditor);

                payload = new CreateRepresentationSuccessPayload(input.id(), representationMetadata);
                changeDescription = new ChangeDescription(ChangeKind.REPRESENTATION_CREATION, editingContext.getId(), input);
            }
        }

        changeDescriptionSink.tryEmitNext(changeDescription);
        payloadSink.tryEmitValue(payload);
    }

}
