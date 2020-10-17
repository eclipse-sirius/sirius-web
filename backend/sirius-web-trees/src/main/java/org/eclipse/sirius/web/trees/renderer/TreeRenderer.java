/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.trees.renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.trees.Tree;
import org.eclipse.sirius.web.trees.TreeItem;
import org.eclipse.sirius.web.trees.description.TreeDescription;

/**
 * Renderer used to create the tree from its description and some variables.
 *
 * @author hmarchadour
 */
public class TreeRenderer {

    public static final String EXPANDED = "expanded"; //$NON-NLS-1$

    private VariableManager variableManager;

    private TreeDescription treeDescription;

    public TreeRenderer(VariableManager variableManager, TreeDescription treeDescription) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.treeDescription = Objects.requireNonNull(treeDescription);
    }

    public Tree render() {
        UUID treeId = this.treeDescription.getIdProvider().apply(this.variableManager);
        String label = this.treeDescription.getLabelProvider().apply(this.variableManager);

        List<TreeItem> childrenItems = new ArrayList<>();
        List<Object> rootElements = this.treeDescription.getElementsProvider().apply(this.variableManager);
        for (Object rootElement : rootElements) {
            VariableManager rootElementVariableManager = this.variableManager.createChild();
            rootElementVariableManager.put(VariableManager.SELF, rootElement);
            childrenItems.add(this.renderTreeItem(rootElementVariableManager));
        }

        // @formatter:off
        return Tree.newTree(treeId)
                .label(label)
                .children(childrenItems)
                .build();
        // @formatter:on
    }

    private TreeItem renderTreeItem(VariableManager treeItemVariableManager) {
        List<TreeItem> childrenTreeItems = new ArrayList<>();

        String id = this.treeDescription.getTreeItemIdProvider().apply(treeItemVariableManager);
        String kind = this.treeDescription.getKindProvider().apply(treeItemVariableManager);
        String label = this.treeDescription.getLabelProvider().apply(treeItemVariableManager);
        boolean editable = this.treeDescription.getEditableProvider().apply(treeItemVariableManager);
        String imageURL = this.treeDescription.getImageURLProvider().apply(treeItemVariableManager);
        Boolean hasChildren = this.treeDescription.getHasChildrenProvider().apply(treeItemVariableManager);

        List<Object> children = this.treeDescription.getChildrenProvider().apply(treeItemVariableManager);
        boolean expanded = !children.isEmpty();
        for (Object child : children) {
            VariableManager childVariableManager = treeItemVariableManager.createChild();
            childVariableManager.put(VariableManager.SELF, child);
            childrenTreeItems.add(this.renderTreeItem(childVariableManager));
        }

        // @formatter:off
        return TreeItem.newTreeItem(id)
                .kind(kind)
                .label(label)
                .editable(editable)
                .imageURL(imageURL)
                .children(childrenTreeItems)
                .hasChildren(hasChildren)
                .expanded(expanded)
                .build();
        // @formatter:on
    }
}
