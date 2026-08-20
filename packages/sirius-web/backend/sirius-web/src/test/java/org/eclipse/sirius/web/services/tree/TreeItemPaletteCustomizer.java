/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.web.services.tree;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.sirius.components.collaborative.diagrams.dto.SingleClickOnDiagramElementTool;
import org.eclipse.sirius.components.collaborative.trees.palette.api.ITreeItemPaletteCustomizer;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.palette.dto.IPaletteEntry;
import org.eclipse.sirius.components.palette.dto.ITool;
import org.eclipse.sirius.components.palette.dto.Palette;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.springframework.stereotype.Service;

/**
 * Used to transform the tree item palette for the tests.
 *
 * @author sbegaudeau
 */
@Service
public class TreeItemPaletteCustomizer implements ITreeItemPaletteCustomizer {

    public static final String CUSTOMIZED_TREE_TOOL_ID = "customized-tree-tool-id";

    public static final String CUSTOMIZED_TREE_TOOL_LABEL = "Customized tree tool";


    @Override
    public boolean canHandle(IEditingContext editingContext, TreeDescription treeDescription, Tree tree, TreeItem treeItem) {
        return true;
    }

    @Override
    public Palette customize(IEditingContext editingContext, TreeDescription treeDescription, Tree tree, TreeItem treeItem, Palette palette) {
        List<ITool> quickAccessTools = new ArrayList<>();
        quickAccessTools.addAll(palette.quickAccessTools());

        var customizedTool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool(CUSTOMIZED_TREE_TOOL_ID)
                .label(CUSTOMIZED_TREE_TOOL_LABEL)
                .iconURL(List.of())
                .targetDescriptions(List.of())
                .keyBindings(List.of())
                .build();
        quickAccessTools.add(customizedTool);

        List<IPaletteEntry> paletteEntries = new ArrayList<>();
        paletteEntries.addAll(palette.paletteEntries());

        return Palette.newPalette(palette.id())
                .quickAccessTools(quickAccessTools)
                .paletteEntries(paletteEntries)
                .build();
    }
}
