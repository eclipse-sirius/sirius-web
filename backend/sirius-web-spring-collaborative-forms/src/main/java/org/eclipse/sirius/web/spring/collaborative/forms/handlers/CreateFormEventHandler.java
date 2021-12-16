/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.forms.handlers;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.core.api.ErrorPayload;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IInput;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.web.forms.Form;
import org.eclipse.sirius.web.forms.description.FormDescription;
import org.eclipse.sirius.web.representations.IRepresentationDescription;
import org.eclipse.sirius.web.representations.ISemanticRepresentationMetadata;
import org.eclipse.sirius.web.representations.SemanticRepresentationMetadata;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeDescription;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeKind;
import org.eclipse.sirius.web.spring.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.web.spring.collaborative.api.Monitoring;
import org.eclipse.sirius.web.spring.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.web.spring.collaborative.dto.CreateRepresentationSuccessPayload;
import org.eclipse.sirius.web.spring.collaborative.forms.messages.ICollaborativeFormMessageService;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler used to create a new form representation.
 *
 * @author hmarchadour
 */
@Service
public class CreateFormEventHandler implements IEditingContextEventHandler {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IRepresentationPersistenceService representationPersistenceService;

    private final IObjectService objectService;

    private final ICollaborativeFormMessageService messageService;

    private final Counter counter;

    public CreateFormEventHandler(IRepresentationDescriptionSearchService representationDescriptionSearchService, IRepresentationPersistenceService representationPersistenceService,
            IObjectService objectService, ICollaborativeFormMessageService messageService, MeterRegistry meterRegistry) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);
        this.objectService = Objects.requireNonNull(objectService);
        this.messageService = Objects.requireNonNull(messageService);

        // @formatter:off
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
        // @formatter:on
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        if (input instanceof CreateRepresentationInput) {
            CreateRepresentationInput createRepresentationInput = (CreateRepresentationInput) input;
            // @formatter:off
            return this.representationDescriptionSearchService.findById(editingContext, createRepresentationInput.getRepresentationDescriptionId().toString())
                    .filter(FormDescription.class::isInstance)
                    .isPresent();
            // @formatter:on
        }
        return false;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.counter.increment();

        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), CreateRepresentationInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(input.getId(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);

        if (input instanceof CreateRepresentationInput) {
            CreateRepresentationInput createRepresentationInput = (CreateRepresentationInput) input;

            Optional<IRepresentationDescription> optionalRepresentationDescription = this.representationDescriptionSearchService.findById(editingContext,
                    createRepresentationInput.getRepresentationDescriptionId().toString());
            Optional<Object> optionalObject = this.objectService.getObject(editingContext, createRepresentationInput.getObjectId());

            if (optionalRepresentationDescription.isPresent() && optionalObject.isPresent()) {
                IRepresentationDescription representationDescription = optionalRepresentationDescription.get();
                String targetObjectId = this.objectService.getId(optionalObject.get());
                if (representationDescription instanceof FormDescription) {
                    // @formatter:off
                    String formId = UUID.randomUUID().toString();
                    Form form = Form.newForm(formId)
                            .label(createRepresentationInput.getRepresentationName())
                            .targetObjectId(targetObjectId)
                            .pages(List.of()) // We don't store form pages, it will be re-render by the FormProcessor.
                            .build();
                    ISemanticRepresentationMetadata formMetadata = SemanticRepresentationMetadata.newRepresentationMetadata(formId)
                            .descriptionId(representationDescription.getId())
                            .label(form.getLabel())
                            .kind(Form.KIND)
                            .targetObjectId(form.getTargetObjectId())
                            .build();
                    // @formatter:on
                    this.representationPersistenceService.save(editingContext, formMetadata, form);

                    payload = new CreateRepresentationSuccessPayload(input.getId(), formMetadata);
                    changeDescription = new ChangeDescription(ChangeKind.REPRESENTATION_CREATION, editingContext.getId(), input);
                }
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

}
