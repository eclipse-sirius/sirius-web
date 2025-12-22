/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
package org.eclipse.sirius.components.view.emf.diagram;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.diagrams.description.EdgeLabelKind;
import org.eclipse.sirius.components.diagrams.events.ReconnectEdgeKind;
import org.eclipse.sirius.components.view.diagram.DeleteTool;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramElementDescription;
import org.eclipse.sirius.components.view.diagram.DiagramPalette;
import org.eclipse.sirius.components.view.diagram.DiagramToolSection;
import org.eclipse.sirius.components.view.diagram.DropNodeTool;
import org.eclipse.sirius.components.view.diagram.DropTool;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.EdgePalette;
import org.eclipse.sirius.components.view.diagram.EdgeReconnectionTool;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.EdgeToolSection;
import org.eclipse.sirius.components.view.diagram.LabelEditTool;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodePalette;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.NodeToolSection;
import org.eclipse.sirius.components.view.diagram.SourceEdgeEndReconnectionTool;
import org.eclipse.sirius.components.view.diagram.TargetEdgeEndReconnectionTool;

/**
 * Helper to locate tools inside View model elements.
 *
 * @author pcdavid
 */
public class ToolFinder {

    private final Function<EObject, UUID> idProvider = (eObject) -> {
        // DiagramElementDescription should have a proper id.
        return UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes());
    };

    public Optional<DropTool> findDropTool(DiagramDescription diagramDescription) {
        return Optional.ofNullable(diagramDescription).map(DiagramDescription::getPalette).map(DiagramPalette::getDropTool);
    }

    public Optional<DeleteTool> findDeleteTool(DiagramElementDescription diagramElementDescription) {
        Optional<DeleteTool> result = Optional.empty();
        if (diagramElementDescription instanceof NodeDescription nodeDescription) {
            result = Optional.of(nodeDescription).map(NodeDescription::getPalette).map(NodePalette::getDeleteTool);
        } else if (diagramElementDescription instanceof EdgeDescription edgeDescription) {
            result = Optional.of(edgeDescription).map(EdgeDescription::getPalette).map(EdgePalette::getDeleteTool);
        }
        return result;
    }

    public Optional<DropNodeTool> findDropNodeTool(NodeDescription nodeDescription) {
        return Optional.ofNullable(nodeDescription).map(NodeDescription::getPalette).map(NodePalette::getDropNodeTool);
    }

    public Optional<DropNodeTool> findDropNodeTool(DiagramDescription diagramDescription) {
        return Optional.ofNullable(diagramDescription).map(DiagramDescription::getPalette).map(DiagramPalette::getDropNodeTool);
    }

    public Optional<LabelEditTool> findNodeLabelEditTool(NodeDescription nodeDescription) {
        return Optional.ofNullable(nodeDescription).map(NodeDescription::getPalette).map(NodePalette::getLabelEditTool);
    }

    public Optional<LabelEditTool> findEdgeLabelEditTool(EdgeDescription nodeDescription) {
        // for the moment, direct edit can only be triggered on the center label
        return Optional.ofNullable(nodeDescription).map(EdgeDescription::getPalette).map(EdgePalette::getCenterLabelEditTool);
    }

    public Optional<LabelEditTool> findLabelEditTool(EdgeDescription edgeDescription, EdgeLabelKind labelKind) {
        return Optional.ofNullable(edgeDescription).map(EdgeDescription::getPalette).map(switch (labelKind) {
            case BEGIN_LABEL -> EdgePalette::getBeginLabelEditTool;
            case CENTER_LABEL -> EdgePalette::getCenterLabelEditTool;
            case END_LABEL -> EdgePalette::getEndLabelEditTool;
        });
    }

    public List<NodeTool> findNodeTools(DiagramDescription diagramDescription) {
        return Optional.ofNullable(diagramDescription)
                .map(DiagramDescription::getPalette)
                .map(DiagramPalette::getNodeTools)
                .orElse(new BasicEList<>());
    }

    public List<NodeTool> findNodeTools(DiagramElementDescription diagramElementDescription) {
        List<NodeTool> result = new BasicEList<>();
        if (diagramElementDescription instanceof NodeDescription nodeDescription) {
            result = Optional.of(nodeDescription).map(NodeDescription::getPalette)
                    .map(NodePalette::getNodeTools)
                    .orElse(new BasicEList<>());
        } else if (diagramElementDescription instanceof EdgeDescription edgeDescription) {
            result = Optional.of(edgeDescription).map(EdgeDescription::getPalette)
                    .map(EdgePalette::getNodeTools)
                    .orElse(new BasicEList<>());
        }
        return result;
    }

    public List<NodeTool> findQuickAccessNodeTools(DiagramElementDescription diagramElementDescription) {
        List<NodeTool> result = new BasicEList<>();
        if (diagramElementDescription instanceof NodeDescription nodeDescription) {
            result = Optional.of(nodeDescription).map(NodeDescription::getPalette)
                    .map(NodePalette::getQuickAccessTools)
                    .orElse(new BasicEList<>());
        } else if (diagramElementDescription instanceof EdgeDescription edgeDescription) {
            result = Optional.of(edgeDescription).map(EdgeDescription::getPalette)
                    .map(EdgePalette::getQuickAccessTools)
                    .orElse(new BasicEList<>());
        }
        return result;
    }

    public List<NodeTool> findQuickAccessDiagramTools(DiagramDescription diagramDescription) {
        return Optional.ofNullable(diagramDescription)
                .map(DiagramDescription::getPalette)
                .map(DiagramPalette::getQuickAccessTools)
                .orElse(new BasicEList<>());
    }

    public List<EdgeTool> findEdgeTools(DiagramElementDescription elementDescription) {
        List<EdgeTool> edgeTools = new ArrayList<>();
        if (elementDescription instanceof NodeDescription nodeDescription && nodeDescription.getPalette() != null) {
            edgeTools.addAll(nodeDescription.getPalette().getEdgeTools());
            edgeTools.addAll(nodeDescription.getPalette().getToolSections().stream()
                    .flatMap(toolSection -> toolSection.getEdgeTools().stream()).toList());
        } else if (elementDescription instanceof EdgeDescription edgeDescription && edgeDescription.getPalette() != null) {
            edgeTools.addAll(edgeDescription.getPalette().getEdgeTools());
        }
        return edgeTools;
    }

    public List<NodeTool> findQuickAccessEdgeTools(EdgeDescription edgeDescription) {
        return Optional.ofNullable(edgeDescription)
                .map(EdgeDescription::getPalette)
                .map(EdgePalette::getQuickAccessTools)
                .orElse(new BasicEList<>());
    }

    public List<DiagramToolSection> findToolSections(DiagramDescription diagramDescription) {
        return Optional.ofNullable(diagramDescription)
                .map(DiagramDescription::getPalette)
                .map(DiagramPalette::getToolSections)
                .orElse(new BasicEList<>());
    }

    public List<NodeToolSection> findToolSections(NodeDescription diagramDescription) {
        return Optional.ofNullable(diagramDescription)
                .map(NodeDescription::getPalette)
                .map(NodePalette::getToolSections)
                .orElse(new BasicEList<>());
    }

    public List<EdgeToolSection> findToolSections(EdgeDescription diagramDescription) {
        return Optional.ofNullable(diagramDescription)
                .map(EdgeDescription::getPalette)
                .map(EdgePalette::getToolSections)
                .orElse(new BasicEList<>());
    }

    public List<EdgeReconnectionTool> findReconnectionTools(EdgeDescription edgeDescription, ReconnectEdgeKind reconnectEdgeKind) {
        List<EdgeReconnectionTool> edgeReconnectionTools = List.of();
        var optionalTools = Optional.ofNullable(edgeDescription.getPalette()).map(EdgePalette::getEdgeReconnectionTools);
        if (optionalTools.isPresent()) {
            switch (reconnectEdgeKind) {
                case SOURCE:
                    edgeReconnectionTools = optionalTools.get().stream().filter(SourceEdgeEndReconnectionTool.class::isInstance).toList();
                    break;
                case TARGET:
                    edgeReconnectionTools = optionalTools.get().stream().filter(TargetEdgeEndReconnectionTool.class::isInstance).toList();
                    break;
                default:
                    break;
            }
        }
        return edgeReconnectionTools;
    }

    public Optional<NodeTool> getNodeToolByIdFromNodeDescription(NodeDescription viewNodeDescription, String toolId) {
        if (viewNodeDescription.getPalette() != null) {
            return viewNodeDescription.getPalette().getToolSections().stream()
                .flatMap(nodeToolSection -> nodeToolSection.getNodeTools().stream())
                .filter(tool -> this.idProvider.apply(tool).toString().equals(toolId))
                .findFirst()
                .or(() -> viewNodeDescription.getPalette().getQuickAccessTools().stream()
                        .filter(tool -> this.idProvider.apply(tool).toString().equals(toolId))
                        .findFirst())
                .or(() -> viewNodeDescription.getPalette().getNodeTools().stream()
                        .filter(tool -> this.idProvider.apply(tool).toString().equals(toolId))
                        .findFirst());
        }
        return Optional.empty();
    }

    public Optional<NodeTool> getNodeToolByIdFromEdgeDescription(EdgeDescription viewEdgeDescription, String toolId) {
        if (viewEdgeDescription.getPalette() != null) {
            return viewEdgeDescription.getPalette().getToolSections().stream()
                .flatMap(edgeToolSection -> edgeToolSection.getNodeTools().stream())
                .filter(tool -> this.idProvider.apply(tool).toString().equals(toolId))
                .findFirst()
                .or(() -> viewEdgeDescription.getPalette().getQuickAccessTools().stream()
                        .filter(tool -> this.idProvider.apply(tool).toString().equals(toolId))
                        .findFirst())
                .or(() -> viewEdgeDescription.getPalette().getNodeTools().stream()
                        .filter(tool -> this.idProvider.apply(tool).toString().equals(toolId))
                        .findFirst());
        }
        return Optional.empty();
    }

    public Optional<NodeTool> getNodeToolByIdFromDiagramDescription(org.eclipse.sirius.components.view.diagram.DiagramDescription diagramDescription, String toolId) {
        if (diagramDescription.getPalette() != null) {
            return diagramDescription.getPalette().getToolSections().stream()
                .flatMap(diagramToolSection -> diagramToolSection.getNodeTools().stream())
                .filter(tool -> this.idProvider.apply(tool).toString().equals(toolId))
                .findFirst()
                .or(() -> diagramDescription.getPalette().getQuickAccessTools().stream()
                        .filter(tool -> this.idProvider.apply(tool).toString().equals(toolId))
                        .findFirst())
                .or(() -> diagramDescription.getPalette().getNodeTools().stream()
                        .filter(tool -> this.idProvider.apply(tool).toString().equals(toolId))
                        .findFirst());
        }
        return Optional.empty();
    }

    public Optional<EdgeTool> getEdgeToolByIdFromNodeDescription(NodeDescription viewNodeDescription, String toolId) {
        if (viewNodeDescription.getPalette() != null) {
            var optionalRootEdgeTool = viewNodeDescription.getPalette().getEdgeTools().stream()
                    .filter(tool -> this.idProvider.apply(tool).toString().equals(toolId))
                    .findFirst();

            Supplier<Optional<EdgeTool>> findEdgeToolInToolSection = () -> viewNodeDescription.getPalette().getToolSections().stream()
                    .flatMap(toolSection -> toolSection.getEdgeTools().stream())
                    .filter(tool -> this.idProvider.apply(tool).toString().equals(toolId))
                    .findFirst();

            return optionalRootEdgeTool.or(findEdgeToolInToolSection);
        }
        return Optional.empty();
    }

    public Optional<EdgeTool> getEdgeToolByIdFromEdgeDescription(EdgeDescription viewEdgeDescription, String toolId) {
        if (viewEdgeDescription.getPalette() != null) {
            return viewEdgeDescription.getPalette().getEdgeTools().stream()
                .filter(tool -> this.idProvider.apply(tool).toString().equals(toolId))
                .findFirst();
        }
        return Optional.empty();
    }

    public Optional<NodeTool> getGroupNodeToolByIdFromDiagramDescription(org.eclipse.sirius.components.view.diagram.DiagramDescription diagramDescription, String toolId) {
        if (diagramDescription.getGroupPalette() != null) {
            return diagramDescription.getGroupPalette().getNodeTools().stream()
                    .filter(tool -> this.idProvider.apply(tool).toString().equals(toolId))
                    .findFirst()
                    .or(() -> diagramDescription.getGroupPalette().getQuickAccessTools().stream()
                            .filter(tool -> this.idProvider.apply(tool).toString().equals(toolId))
                            .findFirst())
                    .or(() -> diagramDescription.getGroupPalette().getToolSections().stream()
                            .filter(org.eclipse.sirius.components.view.diagram.NodeToolSection.class::isInstance)
                            .map(org.eclipse.sirius.components.view.diagram.NodeToolSection.class::cast)
                            .flatMap(toolSection -> toolSection.getNodeTools().stream())
                            .filter(tool -> this.idProvider.apply(tool).toString().equals(toolId))
                            .findFirst());
        }
        return Optional.empty();
    }
}
