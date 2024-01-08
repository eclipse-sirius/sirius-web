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
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.portals.PortalChangeKind;
import org.eclipse.sirius.components.collaborative.portals.api.IPortalEventHandler;
import org.eclipse.sirius.components.collaborative.portals.api.IPortalInput;
import org.eclipse.sirius.components.collaborative.portals.api.PortalContext;
import org.eclipse.sirius.components.collaborative.portals.dto.LayoutPortalInput;
import org.eclipse.sirius.components.collaborative.portals.dto.PortalViewLayoutDataInput;
import org.eclipse.sirius.components.collaborative.portals.services.ICollaborativePortalMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.portals.PortalViewLayoutData;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * The handler for the layoutPortal mutation.
 *
 * @author pcdavid
 */
@Service
public class LayoutPortalEventHandler implements IPortalEventHandler {

    private final ICollaborativePortalMessageService messageService;

    private final Counter counter;

    public LayoutPortalEventHandler(ICollaborativePortalMessageService messageService, MeterRegistry meterRegistry) {
        this.messageService = Objects.requireNonNull(messageService);
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER).tag(Monitoring.NAME, this.getClass().getSimpleName()).register(meterRegistry);
    }

    @Override
    public boolean canHandle(IPortalInput portalInput) {
        return portalInput instanceof LayoutPortalInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, PortalContext context) {
        this.counter.increment();
        var portalInput = context.getInput();

        String message = this.messageService.invalidInput(portalInput.getClass().getSimpleName(), LayoutPortalInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(portalInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, portalInput.representationId(), portalInput);

        try {
            if (context.getInput() instanceof LayoutPortalInput layoutPortalInput) {
                List<PortalViewLayoutData> layoutData = layoutPortalInput.layoutData().stream().map(this::convert).toList();
                var newPortal = context.getServices().layout(context.getCurrentPortal(), layoutData);
                context.setNextPortal(newPortal);
                payload = new SuccessPayload(layoutPortalInput.id(), List.of());
                changeDescription = new ChangeDescription(PortalChangeKind.PORTAL_LAYOUT_CHANGE.name(), newPortal.getId(), context.getInput());
                changeDescription.getParameters().put(IPortalEventHandler.NEXT_PORTAL_PARAMETER, newPortal);
            }
        } finally {
            payloadSink.tryEmitValue(payload);
            changeDescriptionSink.tryEmitNext(changeDescription);
        }
    }

    private PortalViewLayoutData convert(PortalViewLayoutDataInput input) {
        return PortalViewLayoutData.newPortalViewLayoutData(input.portalViewId())
                .x(input.x())
                .y(input.y())
                .width(input.width())
                .height(input.height())
                .build();
    }

}
