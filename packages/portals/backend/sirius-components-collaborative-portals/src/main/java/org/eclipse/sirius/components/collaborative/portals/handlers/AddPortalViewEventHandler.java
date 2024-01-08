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
package org.eclipse.sirius.components.collaborative.portals.handlers;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.portals.PortalChangeKind;
import org.eclipse.sirius.components.collaborative.portals.api.IPortalEventHandler;
import org.eclipse.sirius.components.collaborative.portals.api.IPortalInput;
import org.eclipse.sirius.components.collaborative.portals.api.PortalContext;
import org.eclipse.sirius.components.collaborative.portals.dto.AddPortalViewInput;
import org.eclipse.sirius.components.collaborative.portals.services.ICollaborativePortalMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * The handler for the addPortalView mutation.
 *
 * @author pcdavid
 */
@Service
public class AddPortalViewEventHandler implements IPortalEventHandler {

    private final IRepresentationSearchService representationSearchService;

    private final ICollaborativePortalMessageService messageService;

    private final Counter counter;

    public AddPortalViewEventHandler(IRepresentationSearchService representationSearchService, ICollaborativePortalMessageService messageService, MeterRegistry meterRegistry) {
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.messageService = Objects.requireNonNull(messageService);
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER).tag(Monitoring.NAME, this.getClass().getSimpleName()).register(meterRegistry);
    }

    @Override
    public boolean canHandle(IPortalInput portalInput) {
        return portalInput instanceof AddPortalViewInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, PortalContext context) {
        this.counter.increment();
        var portalInput = context.getInput();

        String message = this.messageService.invalidInput(portalInput.getClass().getSimpleName(), AddPortalViewInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(portalInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, portalInput.representationId(), portalInput);

        try {
            var portalServices = context.getServices();
            if (context.getInput() instanceof AddPortalViewInput addPortalViewInput) {
                if (this.representationSearchService.findById(context.getEditingContext(), addPortalViewInput.viewRepresentationId(), IRepresentation.class).isEmpty()) {
                    payload = new ErrorPayload(portalInput.id(), "The id passed does not correspond to an existing representation");
                } else if (portalServices.referencesRepresentation(context.getCurrentPortal(), addPortalViewInput.viewRepresentationId())) {
                    payload = new ErrorPayload(portalInput.id(), "The representation is already included in the portal");
                } else {
                    var optionalNewPortal = portalServices.addView(context.getCurrentPortal(), addPortalViewInput.viewRepresentationId(), addPortalViewInput.x(), addPortalViewInput.y(), addPortalViewInput.width(), addPortalViewInput.height());
                    if (optionalNewPortal.isPresent()) {
                        context.setNextPortal(optionalNewPortal.get());
                        payload = new SuccessPayload(addPortalViewInput.id(), List.of());

                        changeDescription = new ChangeDescription(PortalChangeKind.PORTAL_VIEW_ADDITION.name(), optionalNewPortal.get().getId(), context.getInput());
                        changeDescription.getParameters().put(IPortalEventHandler.NEXT_PORTAL_PARAMETER, optionalNewPortal.get());
                    } else {
                        payload = new ErrorPayload(addPortalViewInput.id(), this.messageService.forbiddenLoop());
                    }
                }
            }
        } finally {
            payloadSink.tryEmitValue(payload);
            changeDescriptionSink.tryEmitNext(changeDescription);
        }
    }
}
