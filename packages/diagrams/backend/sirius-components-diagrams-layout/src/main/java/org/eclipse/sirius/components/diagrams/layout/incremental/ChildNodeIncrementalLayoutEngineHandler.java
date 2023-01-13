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
package org.eclipse.sirius.components.diagrams.layout.incremental;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.layout.ISiriusWebLayoutConfigurator;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.NodeLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.ICustomNodeLabelPositionProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.ImageNodeStyleSizeProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used to layout node layout.
 *
 * @author gcoutable
 */
public class ChildNodeIncrementalLayoutEngineHandler {

    private final Logger logger = LoggerFactory.getLogger(ChildNodeIncrementalLayoutEngineHandler.class);

    private final IBorderNodeLayoutEngine borderNodeLayoutEngine;

    private final List<ICustomNodeLabelPositionProvider> customLabelPositionProviders;

    private final ImageNodeStyleSizeProvider imageNodeStyleSizeProvider;

    public ChildNodeIncrementalLayoutEngineHandler(IBorderNodeLayoutEngine borderNodeLayoutEngine, List<ICustomNodeLabelPositionProvider> customLabelPositionProviders,
            ImageNodeStyleSizeProvider imageNodeStyleSizeProvider) {
        this.customLabelPositionProviders = Objects.requireNonNull(customLabelPositionProviders);
        this.borderNodeLayoutEngine = Objects.requireNonNull(borderNodeLayoutEngine);
        this.imageNodeStyleSizeProvider = Objects.requireNonNull(imageNodeStyleSizeProvider);
    }

    public Optional<NodeLayoutData> layout(Optional<IDiagramEvent> diagramEvent, NodeLayoutData nodeLayoutData, ISiriusWebLayoutConfigurator layoutConfigurator, Optional<Double> optionalMaxWidth) {
        LayoutEngineHandlerSwitch layoutEngineHandlerSwitch = new LayoutEngineHandlerSwitch(this.borderNodeLayoutEngine, this.customLabelPositionProviders, this.imageNodeStyleSizeProvider);

        var optionalLayoutEngine = layoutEngineHandlerSwitch.apply(nodeLayoutData.getNodeType());

        if (optionalLayoutEngine.isEmpty()) {
            String pattern = "The node '{}' will not be laid out because its type {} seems to not be handled.";
            this.logger.warn(pattern, nodeLayoutData.getId(), nodeLayoutData.getNodeType());
        }

        return optionalLayoutEngine.map(layoutEngine -> {
            return layoutEngine.layout(diagramEvent, nodeLayoutData, layoutConfigurator, optionalMaxWidth);
        });
    }

    public Optional<Double> getNodeWidth(Optional<IDiagramEvent> diagramEvent, NodeLayoutData nodeLayoutData, ISiriusWebLayoutConfigurator layoutConfigurator) {
        LayoutEngineHandlerSwitch layoutEngineHandlerSwitch = new LayoutEngineHandlerSwitch(this.borderNodeLayoutEngine, this.customLabelPositionProviders, this.imageNodeStyleSizeProvider);

        var optionalLayoutEngine = layoutEngineHandlerSwitch.apply(nodeLayoutData.getNodeType());

        if (optionalLayoutEngine.isEmpty()) {
            String pattern = "We are not able to get the node width of '{}' because its type {} seems to not be handled.";
            this.logger.warn(pattern, nodeLayoutData.getId(), nodeLayoutData.getNodeType());
        }

        return optionalLayoutEngine.map(layoutEngine -> {
            return layoutEngine.getNodeWidth(diagramEvent, nodeLayoutData, layoutConfigurator);
        });
    }
}
