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

import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.layout.ISiriusWebLayoutConfigurator;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.NodeLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.ICustomNodeLabelPositionProvider;

/**
 * Used to layout node children along the strategy to layout children.
 *
 * @author gcoutable
 */
public class ChildLayoutStrategyEngineHandler {

    private final IBorderNodeLayoutEngine borderNodeLayoutEngine;

    private final List<ICustomNodeLabelPositionProvider> customLabelPositionProviders;

    public ChildLayoutStrategyEngineHandler(IBorderNodeLayoutEngine borderNodeLayoutEngine, List<ICustomNodeLabelPositionProvider> customLabelPositionProviders) {
        this.customLabelPositionProviders = Objects.requireNonNull(customLabelPositionProviders);
        this.borderNodeLayoutEngine = Objects.requireNonNull(borderNodeLayoutEngine);
    }

    Optional<Size> layoutChildren(Optional<IDiagramEvent> optionalDiagramEvent, NodeLayoutData node, ISiriusWebLayoutConfigurator layoutConfigurator) {
        LayoutStrategyEngineHandlerSwitch layoutStrategyEngineHandlerSwitch = new LayoutStrategyEngineHandlerSwitch(this.borderNodeLayoutEngine, this.customLabelPositionProviders);

        var optionalLayoutStrategyEngine = layoutStrategyEngineHandlerSwitch.apply(node.getChildrenLayoutStrategy());
        return optionalLayoutStrategyEngine.map(layoutStrategyEngine -> {
            return layoutStrategyEngine.layoutChildren(optionalDiagramEvent, node, layoutConfigurator);
        });
    }

    Optional<Size> layoutChildren(Optional<IDiagramEvent> optionalDiagramEvent, NodeLayoutData node, ISiriusWebLayoutConfigurator layoutConfigurator, double width) {
        LayoutStrategyEngineHandlerSwitch layoutStrategyEngineHandlerSwitch = new LayoutStrategyEngineHandlerSwitch(this.borderNodeLayoutEngine, this.customLabelPositionProviders);

        var optionalLayoutStrategyEngine = layoutStrategyEngineHandlerSwitch.apply(node.getChildrenLayoutStrategy());
        return optionalLayoutStrategyEngine.map(layoutStrategyEngine -> {
            return layoutStrategyEngine.layoutChildren(optionalDiagramEvent, node, layoutConfigurator, width);
        });
    }

}
