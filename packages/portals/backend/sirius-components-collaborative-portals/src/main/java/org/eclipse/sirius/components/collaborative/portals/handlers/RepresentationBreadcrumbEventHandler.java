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

import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.portals.api.IPortalEventHandler;
import org.eclipse.sirius.components.collaborative.portals.api.IPortalInput;
import org.eclipse.sirius.components.collaborative.portals.api.PortalContext;
import org.eclipse.sirius.components.collaborative.portals.dto.RepresentationBreadcrumbsSuccessPayload;
import org.eclipse.sirius.components.collaborative.portals.dto.RepresentationBreadcumbsInput;
import org.eclipse.sirius.components.collaborative.portals.services.ICollaborativePortalMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.representations.ISemanticRepresentation;
import org.eclipse.sirius.components.representations.WorkbenchSelectionEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * The event handler used to find the breadcrumbs leading to a representation displayed inside a portal.
 *
 * @author pcdavid
 */
@Service
public class RepresentationBreadcrumbEventHandler implements IPortalEventHandler {

    private final Logger logger = LoggerFactory.getLogger(RepresentationBreadcrumbEventHandler.class);

    private final IRepresentationSearchService representationSearchService;

    private final IObjectService objectService;

    private final ICollaborativePortalMessageService messageService;

    private final Counter counter;

    public RepresentationBreadcrumbEventHandler(IRepresentationSearchService representationSearchService, IObjectService objectService, ICollaborativePortalMessageService messageService,
            MeterRegistry meterRegistry) {
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.objectService = Objects.requireNonNull(objectService);
        this.messageService = Objects.requireNonNull(messageService);
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER).tag(Monitoring.NAME, this.getClass().getSimpleName()).register(meterRegistry);
    }

    @Override
    public boolean canHandle(IPortalInput portalInput) {
        return portalInput instanceof RepresentationBreadcumbsInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, PortalContext context) {
        this.logger.info("Entering RepresentationBreadcrumbEventHandler");
        this.counter.increment();
        var portalInput = context.getInput();

        String message = this.messageService.invalidInput(portalInput.getClass().getSimpleName(), RepresentationBreadcumbsInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(portalInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, portalInput.representationId(), portalInput);
        try {
            if (context.getInput() instanceof RepresentationBreadcumbsInput breadcrumbsInput) {
                LinkedList<WorkbenchSelectionEntry> path = new LinkedList<>();
                Optional<ISemanticRepresentation> optionalRepresentation = this.representationSearchService.findById(context.getEditingContext(), breadcrumbsInput.viewRepresentationId(), ISemanticRepresentation.class);
                if (optionalRepresentation.isPresent()) {
                    var representation = optionalRepresentation.get();
                    path.addFirst(new WorkbenchSelectionEntry(representation.getId(), optionalRepresentation.get().getLabel(), optionalRepresentation.get().getKind()));
                    Optional<Object> optionalCurrentObject = this.objectService.getObject(context.getEditingContext(), representation.getTargetObjectId());
                    while (optionalCurrentObject.isPresent()) {
                        Object currentObject = optionalCurrentObject.get();
                        String id = this.objectService.getId(currentObject);
                        if (id == null) {
                            break;
                        }
                        String label = this.objectService.getLabel(currentObject);
                        String kind = this.objectService.getKind(currentObject);
                        path.addFirst(new WorkbenchSelectionEntry(id, label, kind));
                        optionalCurrentObject = optionalCurrentObject
                                .filter(EObject.class::isInstance)
                                .map(EObject.class::cast)
                                .map(eObject -> Optional.<Object>ofNullable(eObject.eContainer()).orElse(eObject.eResource()));
                    }
                }
                payload = new RepresentationBreadcrumbsSuccessPayload(context.getInput().id(), path);
            }
        } finally {
            payloadSink.tryEmitValue(payload);
            changeDescriptionSink.tryEmitNext(changeDescription);
            this.logger.info("Leaving RepresentationBreadcrumbEventHandler");
        }
    }
}
