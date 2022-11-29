/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.diagrams.layout.incremental;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.elk.graph.ElkGraphElement;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.NodeLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.ICustomNodeLabelPositionProvider;

/**
 * Used to layout node layout.
 *
 * @author gcoutable
 */
public class ChildNodeIncrementalLayoutEngineHandler {

    private final IBorderNodeLayoutEngine borderNodeLayoutEngine;

    private final List<ICustomNodeLabelPositionProvider> customLabelPositionProviders;

    public ChildNodeIncrementalLayoutEngineHandler(IBorderNodeLayoutEngine borderNodeLayoutEngine, List<ICustomNodeLabelPositionProvider> customLabelPositionProviders) {
        this.customLabelPositionProviders = Objects.requireNonNull(customLabelPositionProviders);
        this.borderNodeLayoutEngine = Objects.requireNonNull(borderNodeLayoutEngine);
    }

    public Optional<NodeLayoutData> layout(Optional<IDiagramEvent> diagramEvent, NodeLayoutData nodeLayoutData, Map<String, ElkGraphElement> elementId2ElkElement) {
        LayoutEngineHandlerSwitch layoutEngineHandlerSwitch = new LayoutEngineHandlerSwitch(this.borderNodeLayoutEngine, this.customLabelPositionProviders);

        var optionalLayoutEngine = layoutEngineHandlerSwitch.apply(nodeLayoutData.getNodeType());

        return optionalLayoutEngine.map(layoutEngine -> {
            return layoutEngine.layout(diagramEvent, nodeLayoutData, elementId2ElkElement);
        });
    }

    public Optional<NodeLayoutData> layout(Optional<IDiagramEvent> diagramEvent, NodeLayoutData nodeLayoutData, Map<String, ElkGraphElement> elementId2ElkElement, double nodeWidth) {
        LayoutEngineHandlerSwitch layoutEngineHandlerSwitch = new LayoutEngineHandlerSwitch(this.borderNodeLayoutEngine, this.customLabelPositionProviders);

        var optionalLayoutEngine = layoutEngineHandlerSwitch.apply(nodeLayoutData.getNodeType());

        return optionalLayoutEngine.map(layoutEngine -> {
            return layoutEngine.layout(diagramEvent, nodeLayoutData, elementId2ElkElement, nodeWidth);
        });
    }

    public Optional<Double> getNodeWidth(Optional<IDiagramEvent> diagramEvent, NodeLayoutData nodeLayoutData, Map<String, ElkGraphElement> elementId2ElkElement) {
        LayoutEngineHandlerSwitch layoutEngineHandlerSwitch = new LayoutEngineHandlerSwitch(this.borderNodeLayoutEngine, this.customLabelPositionProviders);

        var optionalLayoutEngine = layoutEngineHandlerSwitch.apply(nodeLayoutData.getNodeType());

        return optionalLayoutEngine.map(layoutEngine -> {
            return layoutEngine.getNodeWidth(diagramEvent, nodeLayoutData, elementId2ElkElement);
        });
    }

    public Optional<Double> getNodeMinimalWidth(NodeLayoutData nodeLayoutData, Map<String, ElkGraphElement> elementId2ElkElement) {
        LayoutEngineHandlerSwitch layoutEngineHandlerSwitch = new LayoutEngineHandlerSwitch(this.borderNodeLayoutEngine, this.customLabelPositionProviders);

        var optionalLayoutEngine = layoutEngineHandlerSwitch.apply(nodeLayoutData.getNodeType());

        return optionalLayoutEngine.map(layoutEngine -> {
            return layoutEngine.getNodeMinimalWidth(nodeLayoutData, elementId2ElkElement);
        });
    }

}
