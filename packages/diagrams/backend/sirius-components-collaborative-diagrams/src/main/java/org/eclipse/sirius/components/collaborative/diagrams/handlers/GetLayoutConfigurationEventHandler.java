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

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.ILayoutConfigurationProvider;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetLayoutConfigurationInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetLayoutConfigurationSuccessPayload;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

import java.util.List;
import java.util.Objects;

/**
 * Handler used to get the layout configurations.
 *
 * @author ocailleau
 */

@Service
public class GetLayoutConfigurationEventHandler implements IDiagramEventHandler {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final List<ILayoutConfigurationProvider> layoutConfigurationProviders;

    private final ICollaborativeMessageService messageService;

    private final Counter counter;

    public GetLayoutConfigurationEventHandler(IRepresentationDescriptionSearchService representationDescriptionSearchService,
                                              List<ILayoutConfigurationProvider> layoutProviders, ICollaborativeMessageService messageService, MeterRegistry meterRegistry) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.layoutConfigurationProviders = Objects.requireNonNull(layoutProviders);
        this.messageService = Objects.requireNonNull(messageService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IDiagramInput diagramInput) {
        return diagramInput instanceof GetLayoutConfigurationInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, DiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), GetLayoutConfigurationInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(diagramInput.id(), message);

        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), diagramInput);

        if (diagramInput instanceof GetLayoutConfigurationInput) {
            Diagram diagram = diagramContext.diagram();
            var optionalDiagramDescription = this.representationDescriptionSearchService.findById(editingContext, diagram.getDescriptionId())
                    .filter(DiagramDescription.class::isInstance)
                    .map(DiagramDescription.class::cast);
            if (optionalDiagramDescription.isPresent()) {
                DiagramDescription diagramDescription = optionalDiagramDescription.get();
                var layoutConfigurations = this.layoutConfigurationProviders.stream()
                        .filter(layoutProvider -> layoutProvider.canHandle(editingContext, diagramContext, diagramDescription))
                        .flatMap(layoutProvider -> layoutProvider.getLayoutConfiguration(editingContext, diagramContext, diagramDescription).stream())
                        .toList();
                payload = new GetLayoutConfigurationSuccessPayload(diagramInput.id(), layoutConfigurations);
            }
        }
        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
