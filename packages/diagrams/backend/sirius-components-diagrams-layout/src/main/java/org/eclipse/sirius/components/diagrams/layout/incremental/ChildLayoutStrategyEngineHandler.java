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

import org.eclipse.sirius.components.diagrams.layout.ISiriusWebLayoutConfigurator;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.ChildrenAreaLaidOutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.ICustomNodeLabelPositionProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.ImageNodeStyleSizeProvider;

/**
 * Used to layout node children along the strategy to layout children.
 *
 * @author gcoutable
 */
public class ChildLayoutStrategyEngineHandler {

    private final IBorderNodeLayoutEngine borderNodeLayoutEngine;

    private final List<ICustomNodeLabelPositionProvider> customLabelPositionProviders;

    private final ImageNodeStyleSizeProvider imageNodeStyleSizeProvider;

    public ChildLayoutStrategyEngineHandler(IBorderNodeLayoutEngine borderNodeLayoutEngine, List<ICustomNodeLabelPositionProvider> customLabelPositionProviders,
            ImageNodeStyleSizeProvider imageNodeStyleSizeProvider) {
        this.customLabelPositionProviders = Objects.requireNonNull(customLabelPositionProviders);
        this.borderNodeLayoutEngine = Objects.requireNonNull(borderNodeLayoutEngine);
        this.imageNodeStyleSizeProvider = Objects.requireNonNull(imageNodeStyleSizeProvider);
    }

    Optional<ChildrenAreaLaidOutData> layoutChildrenArea(ChildrenAreaLayoutContext childrenAreaLayoutContext, ISiriusWebLayoutConfigurator layoutConfigurator) {
        LayoutStrategyEngineHandlerSwitch layoutStrategyEngineHandlerSwitch = new LayoutStrategyEngineHandlerSwitch(this.borderNodeLayoutEngine, this.customLabelPositionProviders,
                this.imageNodeStyleSizeProvider);

        // @formatter:off
        return layoutStrategyEngineHandlerSwitch.apply(childrenAreaLayoutContext.getChildrenLayoutStrategy())
                .map(layoutStrategyEngine -> {
                    return layoutStrategyEngine.layoutChildrenArea(childrenAreaLayoutContext, layoutConfigurator);
                });
        // @formatter:on
    }

}
