/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.components.trees.renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.core.api.labels.StyledString;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.trees.description.TreeDescription;


/**
 * Renderer used to create the tree from its description and some variables.
 *
 * @author hmarchadour
 */
public class TreeRenderer {

    public static final String EXPANDED = "expanded";

    private final VariableManager variableManager;

    private final TreeDescription treeDescription;

    public TreeRenderer(VariableManager variableManager, TreeDescription treeDescription) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.treeDescription = Objects.requireNonNull(treeDescription);
    }

    public Tree render() {
        String treeId = this.treeDescription.getIdProvider().apply(this.variableManager);
        String label = this.treeDescription.getLabelProvider().apply(this.variableManager).toString();

        List<?> rootElements = this.treeDescription.getElementsProvider().apply(this.variableManager);
        List<TreeItem> childrenItems = new ArrayList<>(rootElements.size());
        for (Object rootElement : rootElements) {
            VariableManager rootElementVariableManager = this.variableManager.createChild();
            rootElementVariableManager.put(VariableManager.SELF, rootElement);
            childrenItems.add(this.renderTreeItem(rootElementVariableManager));
        }

        return Tree.newTree(treeId)
                .descriptionId(this.treeDescription.getId())
                .label(label)
                .children(childrenItems)
                .build();
    }

    private TreeItem renderTreeItem(VariableManager treeItemVariableManager) {
        String id = this.treeDescription.getTreeItemIdProvider().apply(treeItemVariableManager);
        String kind = this.treeDescription.getKindProvider().apply(treeItemVariableManager);
        StyledString label = this.treeDescription.getLabelProvider().apply(treeItemVariableManager);
        boolean editable = this.treeDescription.getEditableProvider().apply(treeItemVariableManager);
        boolean deletable = this.treeDescription.getDeletableProvider().apply(treeItemVariableManager);
        boolean selectable = this.treeDescription.getSelectableProvider().apply(treeItemVariableManager);
        List<String> iconURL = this.treeDescription.getIconURLProvider().apply(treeItemVariableManager);
        Boolean hasChildren = this.treeDescription.getHasChildrenProvider().apply(treeItemVariableManager);

        List<?> children = this.treeDescription.getChildrenProvider().apply(treeItemVariableManager);
        List<TreeItem> childrenTreeItems = new ArrayList<>(children.size());

        boolean expanded = !children.isEmpty();
        for (Object child : children) {
            VariableManager childVariableManager = treeItemVariableManager.createChild();
            childVariableManager.put(VariableManager.SELF, child);
            childrenTreeItems.add(this.renderTreeItem(childVariableManager));
        }
        return TreeItem.newTreeItem(id)
                .kind(kind)
                .label(label)
                .editable(editable)
                .deletable(deletable)
                .selectable(selectable)
                .iconURL(iconURL)
                .children(childrenTreeItems)
                .hasChildren(hasChildren)
                .expanded(expanded)
                .build();
    }
}
