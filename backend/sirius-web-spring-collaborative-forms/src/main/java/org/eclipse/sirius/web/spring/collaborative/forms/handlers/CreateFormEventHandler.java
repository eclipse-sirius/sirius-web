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
import org.eclipse.sirius.web.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.web.forms.Form;
import org.eclipse.sirius.web.forms.description.FormDescription;
import org.eclipse.sirius.web.representations.IRepresentationDescription;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeDescription;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeKind;
import org.eclipse.sirius.web.spring.collaborative.api.EventHandlerResponse;
import org.eclipse.sirius.web.spring.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.web.spring.collaborative.api.Monitoring;
import org.eclipse.sirius.web.spring.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.web.spring.collaborative.dto.CreateRepresentationSuccessPayload;
import org.eclipse.sirius.web.spring.collaborative.forms.messages.ICollaborativeFormMessageService;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

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
    public boolean canHandle(IInput input) {
        if (input instanceof CreateRepresentationInput) {
            CreateRepresentationInput createRepresentationInput = (CreateRepresentationInput) input;
            // @formatter:off
            return this.representationDescriptionSearchService.findById(createRepresentationInput.getRepresentationDescriptionId())
                    .filter(FormDescription.class::isInstance)
                    .isPresent();
            // @formatter:on
        }
        return false;
    }

    @Override
    public EventHandlerResponse handle(IEditingContext editingContext, IInput input) {
        this.counter.increment();

        if (input instanceof CreateRepresentationInput) {
            CreateRepresentationInput createRepresentationInput = (CreateRepresentationInput) input;

            Optional<IRepresentationDescription> optionalRepresentationDescription = this.representationDescriptionSearchService.findById(createRepresentationInput.getRepresentationDescriptionId());
            Optional<Object> optionalObject = this.objectService.getObject(editingContext, createRepresentationInput.getObjectId());

            if (optionalRepresentationDescription.isPresent() && optionalObject.isPresent()) {
                IRepresentationDescription representationDescription = optionalRepresentationDescription.get();
                String targetObjectId = this.objectService.getId(optionalObject.get());
                if (representationDescription instanceof FormDescription) {
                    // @formatter:off
                    Form form = Form.newForm(UUID.randomUUID())
                            .label(createRepresentationInput.getRepresentationName())
                            .targetObjectId(targetObjectId)
                            .descriptionId(representationDescription.getId())
                            .pages(List.of()) // We don't store form pages, it will be re-render by the FormProcessor.
                            .build();
                    // @formatter:on

                    this.representationPersistenceService.save(editingContext, form);

                    return new EventHandlerResponse(new ChangeDescription(ChangeKind.REPRESENTATION_CREATION, editingContext.getId()), new CreateRepresentationSuccessPayload(input.getId(), form));
                }
            }
        }

        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), CreateRepresentationInput.class.getSimpleName());
        return new EventHandlerResponse(new ChangeDescription(ChangeKind.NOTHING, editingContext.getId()), new ErrorPayload(input.getId(), message));
    }

}
