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
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.layout.ISiriusWebLayoutConfigurator;
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

    public Optional<NodeLayoutData> layout(Optional<IDiagramEvent> diagramEvent, NodeLayoutData nodeLayoutData, ISiriusWebLayoutConfigurator layoutConfigurator) {
        LayoutEngineHandlerSwitch layoutEngineHandlerSwitch = new LayoutEngineHandlerSwitch(this.borderNodeLayoutEngine, this.customLabelPositionProviders);

        var optionalLayoutEngine = layoutEngineHandlerSwitch.apply(nodeLayoutData.getNodeType());

        return optionalLayoutEngine.map(layoutEngine -> {
            return layoutEngine.layout(diagramEvent, nodeLayoutData, layoutConfigurator);
        });
    }

    public Optional<NodeLayoutData> layout(Optional<IDiagramEvent> diagramEvent, NodeLayoutData nodeLayoutData, ISiriusWebLayoutConfigurator layoutConfigurator, double nodeWidth) {
        LayoutEngineHandlerSwitch layoutEngineHandlerSwitch = new LayoutEngineHandlerSwitch(this.borderNodeLayoutEngine, this.customLabelPositionProviders);

        var optionalLayoutEngine = layoutEngineHandlerSwitch.apply(nodeLayoutData.getNodeType());

        return optionalLayoutEngine.map(layoutEngine -> {
            return layoutEngine.layout(diagramEvent, nodeLayoutData, layoutConfigurator, nodeWidth);
        });
    }

    public Optional<Double> getNodeWidth(Optional<IDiagramEvent> diagramEvent, NodeLayoutData nodeLayoutData, ISiriusWebLayoutConfigurator layoutConfigurator) {
        LayoutEngineHandlerSwitch layoutEngineHandlerSwitch = new LayoutEngineHandlerSwitch(this.borderNodeLayoutEngine, this.customLabelPositionProviders);

        var optionalLayoutEngine = layoutEngineHandlerSwitch.apply(nodeLayoutData.getNodeType());

        return optionalLayoutEngine.map(layoutEngine -> {
            return layoutEngine.getNodeWidth(diagramEvent, nodeLayoutData, layoutConfigurator);
        });
    }

    public Optional<Double> getNodeMinimalWidth(NodeLayoutData nodeLayoutData, ISiriusWebLayoutConfigurator layoutConfigurator) {
        LayoutEngineHandlerSwitch layoutEngineHandlerSwitch = new LayoutEngineHandlerSwitch(this.borderNodeLayoutEngine, this.customLabelPositionProviders);

        var optionalLayoutEngine = layoutEngineHandlerSwitch.apply(nodeLayoutData.getNodeType());

        return optionalLayoutEngine.map(layoutEngine -> {
            return layoutEngine.getNodeMinimalWidth(nodeLayoutData, layoutConfigurator);
        });
    }

}
