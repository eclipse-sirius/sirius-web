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

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.dto.RenameRepresentationSuccessPayload;
import org.eclipse.sirius.components.collaborative.portals.api.IPortalEventHandler;
import org.eclipse.sirius.components.collaborative.portals.api.IPortalInput;
import org.eclipse.sirius.components.collaborative.portals.api.PortalContext;
import org.eclipse.sirius.components.collaborative.portals.dto.RenamePortalInput;
import org.eclipse.sirius.components.collaborative.portals.services.ICollaborativePortalMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.portals.Portal;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler used to rename a portal.
 *
 * @author pcdavid
 */
@Service
public class RenamePortalEventHandler implements IPortalEventHandler {
    private final IRepresentationPersistenceService representationPersistenceService;

    private final ICollaborativePortalMessageService messageService;

    private final Counter counter;

    public RenamePortalEventHandler(IRepresentationPersistenceService representationPersistenceService, ICollaborativePortalMessageService messageService, MeterRegistry meterRegistry) {
        this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);
        this.messageService = Objects.requireNonNull(messageService);
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER).tag(Monitoring.NAME, this.getClass().getSimpleName()).register(meterRegistry);
    }

    @Override
    public boolean canHandle(IPortalInput portalInput) {
        return portalInput instanceof RenamePortalInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, PortalContext context) {
        this.counter.increment();
        var portalInput = context.getInput();

        String message = this.messageService.invalidInput(portalInput.getClass().getSimpleName(), RenamePortalInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(portalInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, portalInput.representationId(), portalInput);

        try {
            if (portalInput instanceof RenamePortalInput renameRepresentationInput) {
                String newLabel = renameRepresentationInput.newLabel();
                var renamedPortal = Portal.newPortal(context.getCurrentPortal()).label(newLabel).build();
                this.representationPersistenceService.save(renameRepresentationInput, context.getEditingContext(), renamedPortal);
                context.setNextPortal(renamedPortal);

                payload = new RenameRepresentationSuccessPayload(portalInput.id(), renamedPortal);
                changeDescription = new ChangeDescription(ChangeKind.REPRESENTATION_RENAMING, renameRepresentationInput.representationId(), portalInput);
            }
        } finally {
            payloadSink.tryEmitValue(payload);
            changeDescriptionSink.tryEmitNext(changeDescription);
        }
    }

}
