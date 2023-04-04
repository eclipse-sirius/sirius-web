/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramDescriptionService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IToolSectionsProvider;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetToolSectionSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetToolSectionsInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolSection;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler used to get the tool sections.
 *
 * @author arichard
 */
@Service
public class GetToolSectionsEventHandler implements IDiagramEventHandler {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IDiagramQueryService diagramQueryService;

    private final IDiagramDescriptionService diagramDescriptionService;

    private final IObjectService objectService;

    private final List<IToolSectionsProvider> toolSectionsProviders;

    private final ICollaborativeMessageService messageService;

    private final Counter counter;

    public GetToolSectionsEventHandler(IRepresentationDescriptionSearchService representationDescriptionSearchService, IDiagramQueryService diagramQueryService,
            IDiagramDescriptionService diagramDescriptionService, IObjectService objectService, List<IToolSectionsProvider> toolSectionsProviders, ICollaborativeMessageService messageService,
            MeterRegistry meterRegistry) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.diagramDescriptionService = Objects.requireNonNull(diagramDescriptionService);
        this.objectService = Objects.requireNonNull(objectService);
        this.toolSectionsProviders = Objects.requireNonNull(toolSectionsProviders);
        this.messageService = Objects.requireNonNull(messageService);

        // @formatter:off
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
        // @formatter:on
    }

    @Override
    public boolean canHandle(IDiagramInput diagramInput) {
        return diagramInput instanceof GetToolSectionsInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IDiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        List<ToolSection> toolSections = new ArrayList<>();
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), diagramInput);

        if (diagramInput instanceof GetToolSectionsInput) {
            GetToolSectionsInput toolSectionsInput = (GetToolSectionsInput) diagramInput;
            String diagramElementId = toolSectionsInput.diagramElementId();

            Diagram diagram = diagramContext.getDiagram();
            // @formatter:off
            var optionalDiagramDescription = this.representationDescriptionSearchService.findById(editingContext, diagram.getDescriptionId())
                    .filter(DiagramDescription.class::isInstance)
                    .map(DiagramDescription.class::cast);
            // @formatter:on
            if (optionalDiagramDescription.isPresent()) {
                DiagramDescription diagramDescription = optionalDiagramDescription.get();
                var optionalToolSectionsProvider = this.toolSectionsProviders.stream().filter(toolSectionProvider -> toolSectionProvider.canHandle(diagramDescription)).findFirst();
                var optionalTargetElement = this.findTargetElement(diagram, diagramElementId, editingContext);
                var optionalDiagramElement = this.findDiagramElement(diagram, diagramElementId);
                var optionalDiagramElementDescription = this.findDiagramElementDescription(diagram, diagramElementId, diagramDescription, optionalDiagramElement.orElse(null));

                if (optionalToolSectionsProvider.isPresent() && optionalTargetElement.isPresent() && optionalDiagramElementDescription.isPresent()) {
                    IToolSectionsProvider toolSectionsProvider = optionalToolSectionsProvider.get();
                    toolSections = toolSectionsProvider.handle(optionalTargetElement.get(), optionalDiagramElement.orElse(null), optionalDiagramElementDescription.get(),
                            diagramDescription);
                }
            }
        }
        payloadSink.tryEmitValue(new GetToolSectionSuccessPayload(diagramInput.id(), toolSections));
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private Optional<Object> findDiagramElement(Diagram diagram, String diagramElementId) {
        Object diagramElement = null;
        if (diagram.getId().equals(diagramElementId)) {
            diagramElement = diagram;
        } else {
            var findNodeById = this.diagramQueryService.findNodeById(diagram, diagramElementId);
            if (findNodeById.isPresent()) {
                Node node = findNodeById.get();
                diagramElement = node;
            } else {
                var findEdgeById = this.diagramQueryService.findEdgeById(diagram, diagramElementId);
                if (findEdgeById.isPresent()) {
                    Edge edge = findEdgeById.get();
                    diagramElement = edge;
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
            return this.objectService.getObject(editingContext, targetObjectId);
        }
        return Optional.empty();
    }
}
