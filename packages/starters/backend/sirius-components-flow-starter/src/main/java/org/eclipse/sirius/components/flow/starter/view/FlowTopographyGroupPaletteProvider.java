/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
package org.eclipse.sirius.components.flow.starter.view;

import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramBuilders;
import org.eclipse.sirius.components.view.diagram.GroupPalette;

/**
 * Used to create the group palette for Flow Topography.
 *
 * @author mcharfadi
 */
public class FlowTopographyGroupPaletteProvider {

    private final DiagramBuilders diagramBuilderHelper = new DiagramBuilders();

    private final FlowViewBuilder flowViewBuilder = new FlowViewBuilder();

    public GroupPalette createPalette(IViewDiagramElementFinder cache) {
        return this.diagramBuilderHelper.newGroupPalette()
                .toolSections(this.flowViewBuilder.createGroupHideRevealNodeToolSection())
                .build();
    }

}
