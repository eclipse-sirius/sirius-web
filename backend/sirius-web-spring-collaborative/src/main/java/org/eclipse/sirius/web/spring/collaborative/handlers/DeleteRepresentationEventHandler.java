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
package org.eclipse.sirius.web.spring.collaborative.handlers;

import java.util.Objects;

import org.eclipse.sirius.web.collaborative.api.dto.DeleteRepresentationInput;
import org.eclipse.sirius.web.collaborative.api.dto.DeleteRepresentationSuccessPayload;
import org.eclipse.sirius.web.collaborative.api.services.EventHandlerResponse;
import org.eclipse.sirius.web.collaborative.api.services.IProjectEventHandler;
import org.eclipse.sirius.web.collaborative.api.services.Monitoring;
import org.eclipse.sirius.web.core.api.ErrorPayload;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IInput;
import org.eclipse.sirius.web.representations.IRepresentation;
import org.eclipse.sirius.web.services.api.representations.IRepresentationDescriptionService;
import org.eclipse.sirius.web.services.api.representations.IRepresentationService;
import org.eclipse.sirius.web.services.api.representations.RepresentationDescriptor;
import org.eclipse.sirius.web.spring.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.web.trees.Tree;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

/**
 * Handler used to delete a representation.
 *
 * @author lfasani
 */
@Service
public class DeleteRepresentationEventHandler implements IProjectEventHandler {

    private final IRepresentationService representationService;

    private final ICollaborativeMessageService messageService;

    private final Counter counter;

    public DeleteRepresentationEventHandler(IRepresentationService representationService, ICollaborativeMessageService messageService, MeterRegistry meterRegistry) {
        this.representationService = Objects.requireNonNull(representationService);
        this.messageService = Objects.requireNonNull(messageService);

        // @formatter:off
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
        // @formatter:on
    }

    @Override
    public boolean canHandle(IInput input) {
        return input instanceof DeleteRepresentationInput;
    }

    @Override
    public EventHandlerResponse handle(IEditingContext editingContext, IInput input) {
        this.counter.increment();

        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), DeleteRepresentationInput.class.getSimpleName());
        EventHandlerResponse eventHandlerResponse = new EventHandlerResponse(false, representation -> false, new ErrorPayload(message));
        if (input instanceof DeleteRepresentationInput) {
            DeleteRepresentationInput deleteRepresentationInput = (DeleteRepresentationInput) input;
            var optionalRepresentation = this.representationService.getRepresentation(deleteRepresentationInput.getRepresentationId());

            if (optionalRepresentation.isPresent()) {
                RepresentationDescriptor representationDescriptor = optionalRepresentation.get();
                this.representationService.delete(representationDescriptor.getId());

                eventHandlerResponse = new EventHandlerResponse(false, this::isExplorerTree, new DeleteRepresentationSuccessPayload(representationDescriptor.getId()));
            }
        }

        return eventHandlerResponse;
    }

    private boolean isExplorerTree(IRepresentation representation) {
        return representation instanceof Tree && Objects.equals(((Tree) representation).getDescriptionId(), IRepresentationDescriptionService.EXPLORER_TREE_DESCRIPTION);
    }
}
