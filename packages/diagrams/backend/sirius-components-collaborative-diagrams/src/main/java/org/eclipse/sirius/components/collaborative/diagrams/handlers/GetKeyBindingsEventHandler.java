/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IKeyBindingsProvider;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetDropNodeCompatibilityInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetKeyBindingsInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetKeyBindingsSuccessPayload;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Event handler for the "get key bindings" query.
 *
 * @author pcdavid
 */
@Service
public class GetKeyBindingsEventHandler implements IDiagramEventHandler {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final List<IKeyBindingsProvider> keyBindingsProviders;

    private final ICollaborativeMessageService messageService;

    private final Counter counter;

    public GetKeyBindingsEventHandler(IRepresentationDescriptionSearchService representationDescriptionSearchService, List<IKeyBindingsProvider> keyBindingsProviders,
            ICollaborativeMessageService messageService, MeterRegistry meterRegistry) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.keyBindingsProviders = Objects.requireNonNull(keyBindingsProviders);
        this.messageService = Objects.requireNonNull(messageService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IDiagramInput diagramInput) {
        return diagramInput instanceof GetKeyBindingsInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, DiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), diagramInput);
        IPayload payload = null;

        if (diagramInput instanceof GetKeyBindingsInput) {
            Diagram diagram = diagramContext.diagram();
            var diagramDescription = this.representationDescriptionSearchService.findById(editingContext, diagram.getDescriptionId())
                    .filter(DiagramDescription.class::isInstance)
                    .map(DiagramDescription.class::cast);

            if (diagramDescription.isPresent()) {
                List<String> result = new ArrayList<>();
                this.keyBindingsProviders.stream()
                        .filter(provider -> provider.canHandle(editingContext, diagramDescription.get()))
                        .forEach(provider -> result.addAll(provider.getKeyBindings(editingContext, diagramDescription.get())));

                payload = new GetKeyBindingsSuccessPayload(diagramInput.id(), result);
            }
        } else {
            String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), GetDropNodeCompatibilityInput.class.getSimpleName());
            payload = new ErrorPayload(diagramInput.id(), message);
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
