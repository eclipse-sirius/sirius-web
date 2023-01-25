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
package org.eclipse.sirius.components.view.emf.diagram;

import java.util.List;
import java.util.Optional;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.sirius.components.diagrams.description.EdgeLabelKind;
import org.eclipse.sirius.components.diagrams.events.ReconnectEdgeKind;
import org.eclipse.sirius.components.view.DeleteTool;
import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.DiagramElementDescription;
import org.eclipse.sirius.components.view.DiagramPalette;
import org.eclipse.sirius.components.view.DropTool;
import org.eclipse.sirius.components.view.EdgeDescription;
import org.eclipse.sirius.components.view.EdgePalette;
import org.eclipse.sirius.components.view.EdgeReconnectionTool;
import org.eclipse.sirius.components.view.EdgeTool;
import org.eclipse.sirius.components.view.LabelEditTool;
import org.eclipse.sirius.components.view.NodeDescription;
import org.eclipse.sirius.components.view.NodePalette;
import org.eclipse.sirius.components.view.NodeTool;
import org.eclipse.sirius.components.view.SourceEdgeEndReconnectionTool;
import org.eclipse.sirius.components.view.TargetEdgeEndReconnectionTool;

/**
 * Helper to locate tools inside View model elements.
 *
 * @author pcdavid
 */
public class ToolFinder {
    public Optional<DropTool> findDropTool(DiagramDescription diagramDescription) {
        return Optional.ofNullable(diagramDescription).map(DiagramDescription::getPalette).map(DiagramPalette::getDropTool);
    }

    public Optional<DeleteTool> findDeleteTool(DiagramElementDescription diagramElementDescription) {
        Optional<DeleteTool> result = Optional.empty();
        if (diagramElementDescription instanceof NodeDescription nodeDescription) {
            result = Optional.ofNullable(nodeDescription).map(NodeDescription::getPalette).map(NodePalette::getDeleteTool);
        } else if (diagramElementDescription instanceof EdgeDescription edgeDescription) {
            result = Optional.ofNullable(edgeDescription).map(EdgeDescription::getPalette).map(EdgePalette::getDeleteTool);
        }
        return result;
    }

    public Optional<LabelEditTool> findNodeLabelEditTool(NodeDescription nodeDescription) {
        return Optional.ofNullable(nodeDescription).map(NodeDescription::getPalette).map(NodePalette::getLabelEditTool);
    }

    public Optional<LabelEditTool> findLabelEditTool(EdgeDescription edgeDescription, EdgeLabelKind labelKind) {
        return Optional.ofNullable(edgeDescription).map(EdgeDescription::getPalette).map(switch (labelKind) {
            case BEGIN_LABEL -> EdgePalette::getBeginLabelEditTool;
            case CENTER_LABEL -> EdgePalette::getCenterLabelEditTool;
            case END_LABEL -> EdgePalette::getEndLabelEditTool;
        });
    }

    public List<NodeTool> findNodeTools(DiagramDescription diagramDescription) {
        // @formatter:off
        return Optional.ofNullable(diagramDescription)
                .map(DiagramDescription::getPalette)
                .map(DiagramPalette::getNodeTools)
                .orElse(new BasicEList<>());
        // @formatter:on
    }

    public List<NodeTool> findNodeTools(DiagramElementDescription diagramElementDescription) {
        EList<NodeTool> result = new BasicEList<>();
        if (diagramElementDescription instanceof NodeDescription nodeDescription) {
            result = Optional.ofNullable(nodeDescription).map(NodeDescription::getPalette).map(NodePalette::getNodeTools).orElse(new BasicEList<>());
        } else if (diagramElementDescription instanceof EdgeDescription edgeDescription) {
            result = Optional.ofNullable(edgeDescription).map(EdgeDescription::getPalette).map(EdgePalette::getNodeTools).orElse(new BasicEList<>());
        }
        return result;
    }

    public List<EdgeTool> findEdgeTools(NodeDescription nodeDescription) {
        return Optional.ofNullable(nodeDescription).map(NodeDescription::getPalette).map(NodePalette::getEdgeTools).orElse(new BasicEList<>());
    }

    public List<EdgeReconnectionTool> findReconnectionTools(EdgeDescription edgeDescription, ReconnectEdgeKind reconnectEdgeKind) {
        List<EdgeReconnectionTool> edgeReconnectionTools;
        var optionalTools = Optional.ofNullable(edgeDescription.getPalette()).map(EdgePalette::getEdgeReconnectionTools);
        switch (reconnectEdgeKind) {
            case SOURCE:
                edgeReconnectionTools = optionalTools.filter(SourceEdgeEndReconnectionTool.class::isInstance).orElse(new BasicEList<>());
                break;
            case TARGET:
                edgeReconnectionTools = optionalTools.filter(TargetEdgeEndReconnectionTool.class::isInstance).orElse(new BasicEList<>());
                break;
            default:
                edgeReconnectionTools = List.of();
                break;
        }
        return edgeReconnectionTools;
    }
}
