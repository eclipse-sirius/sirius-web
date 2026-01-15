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
import org.eclipse.sirius.components.collaborative.diagrams.api.IConnectorPaletteProvider;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramDescriptionService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetConnectorPaletteInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetPaletteSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.Palette;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Handler used to get the connector palette.
 *
 * @author mcharfadi
 */
@Service
public class GetConnectorPaletteEventHandler implements IDiagramEventHandler {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IDiagramQueryService diagramQueryService;

    private final IDiagramDescriptionService diagramDescriptionService;

    private final List<IConnectorPaletteProvider> paletteProviders;

    private final ICollaborativeMessageService messageService;

    private final Counter counter;

    public GetConnectorPaletteEventHandler(IRepresentationDescriptionSearchService representationDescriptionSearchService, IDiagramQueryService diagramQueryService, IDiagramDescriptionService diagramDescriptionService, List<IConnectorPaletteProvider> paletteProviders,
                                           ICollaborativeMessageService messageService, MeterRegistry meterRegistry) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.diagramQueryService = diagramQueryService;
        this.diagramDescriptionService = diagramDescriptionService;
        this.paletteProviders = Objects.requireNonNull(paletteProviders);
        this.messageService = Objects.requireNonNull(messageService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IDiagramInput diagramInput) {
        return diagramInput instanceof GetConnectorPaletteInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, DiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), GetConnectorPaletteInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(diagramInput.id(), message);
        Palette palette = null;

        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), diagramInput);

        if (diagramInput instanceof GetConnectorPaletteInput toolSectionsInput) {
            String sourceDiagramElementId = toolSectionsInput.sourceDiagramElementId();
            String targetDiagramElementId = toolSectionsInput.targetDiagramElementId();

            Diagram diagram = diagramContext.diagram();
            var optionalDiagramDescription = this.representationDescriptionSearchService.findById(editingContext, diagram.getDescriptionId())
                    .filter(DiagramDescription.class::isInstance)
                    .map(DiagramDescription.class::cast);



            if (optionalDiagramDescription.isPresent()) {
                DiagramDescription diagramDescription = optionalDiagramDescription.get();

                var optionalSourceDiagramElement = this.findDiagramElement(diagram, sourceDiagramElementId);
                var optionalSourceDiagramElementDescription = this.findDiagramElementDescription(diagramDescription, optionalSourceDiagramElement.orElse(null));

                var optionalTargetDiagramElement = this.findDiagramElement(diagram, targetDiagramElementId);
                var optionalTargetDiagramElementDescription = this.findDiagramElementDescription(diagramDescription, optionalTargetDiagramElement.orElse(null));

                var optionalPaletteProvider = this.paletteProviders.stream().filter(paletteProvider -> paletteProvider.canHandle(diagramDescription)).findFirst();

                if (optionalPaletteProvider.isPresent()) {
                    IConnectorPaletteProvider paletteProvider = optionalPaletteProvider.get();
                    palette = paletteProvider.handle(editingContext, diagramContext, diagramDescription,
                            optionalSourceDiagramElement.get(), optionalTargetDiagramElement.get(),
                            optionalSourceDiagramElementDescription.get(), optionalTargetDiagramElementDescription.get());
                }
            }
        }
        if (palette != null) {
            payload = new GetPaletteSuccessPayload(diagramInput.id(), palette);
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private Optional<Object> findDiagramElement(Diagram diagram, String diagramElementId) {
        Object diagramElement = null;
        var findNodeById = this.diagramQueryService.findNodeById(diagram, diagramElementId);
        if (findNodeById.isPresent()) {
            diagramElement = findNodeById.get();
        } else {
            var findEdgeById = this.diagramQueryService.findEdgeById(diagram, diagramElementId);
            if (findEdgeById.isPresent()) {
                diagramElement = findEdgeById.get();
            }
        }
        return Optional.ofNullable(diagramElement);
    }

    private Optional<Object> findDiagramElementDescription(DiagramDescription diagramDescription, Object diagramElement) {
        Object diagramElementDescription = null;

        if (diagramElement instanceof Node) {
            String descriptionId = ((Node) diagramElement).getDescriptionId();
            var optionalNodeDescription = this.diagramDescriptionService.findNodeDescriptionById(diagramDescription, descriptionId);
            if (optionalNodeDescription.isPresent()) {
                diagramElementDescription = optionalNodeDescription.get();
            }
        } else if (diagramElement instanceof Edge) {
            String descriptionId = ((Edge) diagramElement).getDescriptionId();
            var optionalEdgeDescription = this.diagramDescriptionService.findEdgeDescriptionById(diagramDescription, descriptionId);
            if (optionalEdgeDescription.isPresent()) {
                diagramElementDescription = optionalEdgeDescription.get();
            }
        }
        return Optional.ofNullable(diagramElementDescription);
    }
}
