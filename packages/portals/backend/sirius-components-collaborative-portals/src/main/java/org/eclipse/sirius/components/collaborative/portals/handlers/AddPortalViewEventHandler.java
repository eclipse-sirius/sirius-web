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
package org.eclipse.sirius.components.collaborative.portals.handlers;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.portals.api.IPortalEventHandler;
import org.eclipse.sirius.components.collaborative.portals.api.IPortalInput;
import org.eclipse.sirius.components.collaborative.portals.api.PortalContext;
import org.eclipse.sirius.components.collaborative.portals.dto.AddPortalViewInput;
import org.eclipse.sirius.components.collaborative.portals.services.ICollaborativePortalMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.portals.Portal;
import org.eclipse.sirius.components.portals.PortalView;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * The handled for the addPortalView mutation.
 *
 * @author pcdavid
 */
@Service
public class AddPortalViewEventHandler implements IPortalEventHandler {

    private final ICollaborativePortalMessageService messageService;

    private final Counter counter;

    public AddPortalViewEventHandler(ICollaborativePortalMessageService messageService, MeterRegistry meterRegistry) {
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
            if (context.getInput() instanceof AddPortalViewInput addPortalViewInput) {
                var newViews = new ArrayList<>(context.getCurrentPortal().getViews());
                var newPortalViewId = this.computeNewPortalViewId(context.getCurrentPortal(), addPortalViewInput);
                newViews.add(PortalView.newPortalView(newPortalViewId).representationId(addPortalViewInput.viewRepresentationId()).build());
                var newPortal = Portal.newPortal(context.getCurrentPortal()).views(newViews).build();
                context.setNextPortal(newPortal);
            }
        } finally {
            payloadSink.tryEmitValue(payload);
            changeDescriptionSink.tryEmitNext(changeDescription);
        }
    }

    private String computeNewPortalViewId(Portal parentPortal, AddPortalViewInput addPortalViewInput) {
        String rawId = parentPortal.getId() + addPortalViewInput.viewRepresentationId();
        return UUID.nameUUIDFromBytes(rawId.getBytes()).toString();
    }

}
