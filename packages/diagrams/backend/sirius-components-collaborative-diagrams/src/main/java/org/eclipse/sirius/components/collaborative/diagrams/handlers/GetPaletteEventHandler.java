/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramDescriptionService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IPaletteProvider;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetPaletteInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetPaletteSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.Palette;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler used to get the tool sections.
 *
 * @author arichard
 */
@Service
public class GetPaletteEventHandler implements IDiagramEventHandler {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IDiagramQueryService diagramQueryService;

    private final IDiagramDescriptionService diagramDescriptionService;

    private final IObjectSearchService objectSearchService;

    private final List<IPaletteProvider> toolSectionsProviders;

    private final ICollaborativeMessageService messageService;

    private final Counter counter;

    public GetPaletteEventHandler(IRepresentationDescriptionSearchService representationDescriptionSearchService, IDiagramQueryService diagramQueryService,
            IDiagramDescriptionService diagramDescriptionService, IObjectSearchService objectSearchService, List<IPaletteProvider> toolSectionsProviders, ICollaborativeMessageService messageService,
            MeterRegistry meterRegistry) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.diagramDescriptionService = Objects.requireNonNull(diagramDescriptionService);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.toolSectionsProviders = Objects.requireNonNull(toolSectionsProviders);
        this.messageService = Objects.requireNonNull(messageService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IDiagramInput diagramInput) {
        return diagramInput instanceof GetPaletteInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, DiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), GetPaletteInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(diagramInput.id(), message);
        Palette palette = null;

        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), diagramInput);

        if (diagramInput instanceof GetPaletteInput toolSectionsInput) {
            String diagramElementId = toolSectionsInput.diagramElementId();

            Diagram diagram = diagramContext.diagram();
            var optionalDiagramDescription = this.representationDescriptionSearchService.findById(editingContext, diagram.getDescriptionId())
                    .filter(DiagramDescription.class::isInstance)
                    .map(DiagramDescription.class::cast);
            if (optionalDiagramDescription.isPresent()) {
                DiagramDescription diagramDescription = optionalDiagramDescription.get();
                var optionalToolSectionsProvider = this.toolSectionsProviders.stream().filter(toolSectionProvider -> toolSectionProvider.canHandle(diagramDescription)).findFirst();
                var optionalTargetElement = this.findTargetElement(diagram, diagramElementId, editingContext);
                var optionalDiagramElement = this.findDiagramElement(diagram, diagramElementId);
                var optionalDiagramElementDescription = this.findDiagramElementDescription(diagram, diagramElementId, diagramDescription, optionalDiagramElement.orElse(null));

                if (optionalToolSectionsProvider.isPresent() && optionalTargetElement.isPresent() && optionalDiagramElementDescription.isPresent()) {
                    IPaletteProvider toolSectionsProvider = optionalToolSectionsProvider.get();
                    palette = toolSectionsProvider.handle(editingContext, diagramContext, diagramDescription, optionalDiagramElementDescription.get(), optionalDiagramElement.orElse(null), optionalTargetElement.get());
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
        if (diagram.getId().equals(diagramElementId)) {
            diagramElement = diagram;
        } else {
            var findNodeById = this.diagramQueryService.findNodeById(diagram, diagramElementId);
            if (findNodeById.isPresent()) {
                diagramElement = findNodeById.get();
            } else {
                var findEdgeById = this.diagramQueryService.findEdgeById(diagram, diagramElementId);
                if (findEdgeById.isPresent()) {
                    diagramElement = findEdgeById.get();
                }
            }
        }
        return Optional.ofNullable(diagramElement);
    }

    private Optional<Object> findDiagramElementDescription(Diagram diagram, String diagramElementId, DiagramDescription diagramDescription, Object diagramElement) {
        Object diagramElementDescription = null;

        boolean appliesToRootDiagram = diagram.getId().equals(diagramElementId);
        if (appliesToRootDiagram) {
            diagramElementDescription = diagramDescription;
        } else if (diagramElement instanceof Node) {
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

    private Optional<Object> findTargetElement(Diagram diagram, String diagramElementId, IEditingContext editingContext) {
        String targetObjectId = null;
        boolean appliesToRootDiagram = diagram.getId().equals(diagramElementId);
        if (appliesToRootDiagram) {
            targetObjectId = diagram.getTargetObjectId();
        } else {
            var findNodeById = this.diagramQueryService.findNodeById(diagram, diagramElementId);
            if (findNodeById.isPresent()) {
                Node node = findNodeById.get();
                targetObjectId = node.getTargetObjectId();
            } else {
                var findEdgeById = this.diagramQueryService.findEdgeById(diagram, diagramElementId);
                if (findEdgeById.isPresent()) {
                    Edge edge = findEdgeById.get();
                    targetObjectId = edge.getTargetObjectId();
                }
            }
        }
        if (targetObjectId != null) {
            return this.objectSearchService.getObject(editingContext, targetObjectId);
        }
        return Optional.empty();
    }
}
