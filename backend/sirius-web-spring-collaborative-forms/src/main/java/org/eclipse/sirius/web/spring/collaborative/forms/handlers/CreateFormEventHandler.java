/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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

import org.eclipse.sirius.web.collaborative.api.dto.CreateRepresentationInput;
import org.eclipse.sirius.web.collaborative.api.dto.CreateRepresentationSuccessPayload;
import org.eclipse.sirius.web.collaborative.api.services.EventHandlerResponse;
import org.eclipse.sirius.web.collaborative.api.services.IProjectEventHandler;
import org.eclipse.sirius.web.collaborative.api.services.Monitoring;
import org.eclipse.sirius.web.forms.Form;
import org.eclipse.sirius.web.forms.description.FormDescription;
import org.eclipse.sirius.web.representations.IRepresentationDescription;
import org.eclipse.sirius.web.services.api.Context;
import org.eclipse.sirius.web.services.api.dto.ErrorPayload;
import org.eclipse.sirius.web.services.api.dto.IProjectInput;
import org.eclipse.sirius.web.services.api.objects.IEditingContext;
import org.eclipse.sirius.web.services.api.objects.IObjectService;
import org.eclipse.sirius.web.services.api.representations.IRepresentationDescriptionService;
import org.eclipse.sirius.web.services.api.representations.IRepresentationService;
import org.eclipse.sirius.web.services.api.representations.RepresentationDescriptor;
import org.eclipse.sirius.web.spring.collaborative.forms.messages.ICollaborativeFormMessageService;
import org.eclipse.sirius.web.trees.Tree;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

/**
 * Handler used to create a new form representation.
 *
 * @author hmarchadour
 */
@Service
public class CreateFormEventHandler implements IProjectEventHandler {

    private final IRepresentationDescriptionService representationDescriptionService;

    private final IRepresentationService representationService;

    private final IObjectService objectService;

    private final ICollaborativeFormMessageService messageService;

    private final Counter counter;

    public CreateFormEventHandler(IRepresentationDescriptionService representationDescriptionService, IRepresentationService representationService, IObjectService objectService,
            ICollaborativeFormMessageService messageService, MeterRegistry meterRegistry) {
        this.representationDescriptionService = Objects.requireNonNull(representationDescriptionService);
        this.representationService = Objects.requireNonNull(representationService);
        this.objectService = Objects.requireNonNull(objectService);
        this.messageService = Objects.requireNonNull(messageService);

        // @formatter:off
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
        // @formatter:on
    }

    @Override
    public boolean canHandle(IProjectInput projectInput) {
        if (projectInput instanceof CreateRepresentationInput) {
            CreateRepresentationInput input = (CreateRepresentationInput) projectInput;
            // @formatter:off
            return this.representationDescriptionService.findRepresentationDescriptionById(input.getRepresentationDescriptionId())
                    .filter(FormDescription.class::isInstance)
                    .isPresent();
            // @formatter:on
        }
        return false;
    }

    @Override
    public EventHandlerResponse handle(IEditingContext editingContext, IProjectInput projectInput, Context context) {
        this.counter.increment();

        if (projectInput instanceof CreateRepresentationInput) {
            CreateRepresentationInput input = (CreateRepresentationInput) projectInput;

            Optional<IRepresentationDescription> optionalRepresentationDescription = this.representationDescriptionService.findRepresentationDescriptionById(input.getRepresentationDescriptionId());
            Optional<Object> optionalObject = this.objectService.getObject(editingContext, input.getObjectId());

            if (optionalRepresentationDescription.isPresent() && optionalObject.isPresent()) {
                IRepresentationDescription representationDescription = optionalRepresentationDescription.get();
                String targetObjectId = this.objectService.getId(optionalObject.get());
                if (representationDescription instanceof FormDescription) {
                    // @formatter:off

                    Form form = Form.newForm(UUID.randomUUID())
                            .label(input.getRepresentationName())
                            .targetObjectId(targetObjectId)
                            .descriptionId(representationDescription.getId())
                            .pages(List.of()) // We don't store form pages, it will be re-render by the FormProcessor.
                            .build();

                    RepresentationDescriptor representationDescriptor = RepresentationDescriptor.newRepresentationDescriptor(form.getId())
                            .projectId(editingContext.getProjectId())
                            .descriptionId(form.getDescriptionId())
                            .targetObjectId(form.getTargetObjectId())
                            .label(form.getLabel())
                            .representation(form)
                            .build();
                    // @formatter:on

                    this.representationService.save(representationDescriptor);

                    return new EventHandlerResponse(false, representation -> representation instanceof Tree, new CreateRepresentationSuccessPayload(form));
                }
            }
        }

        String message = this.messageService.invalidInput(projectInput.getClass().getSimpleName(), CreateRepresentationInput.class.getSimpleName());
        return new EventHandlerResponse(false, representation -> false, new ErrorPayload(message));
    }

}
