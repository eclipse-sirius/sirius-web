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

import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.labels.StyledString;
import org.eclipse.sirius.components.core.api.labels.StyledStringFragment;
import org.eclipse.sirius.components.core.api.labels.StyledStringFragmentStyle;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Renderer used to create the tree from its description and some variables.
 *
 * @author hmarchadour
 */
public class TreeRenderer {

    public static final String ANCESTOR_IDS = "ancestorIds";

    public static final String INDEX = "index";

    public static final String EXPANDED = "expanded";

    public static final String ACTIVE_FILTER_IDS = "activeFilterIds";

    private final Logger logger = LoggerFactory.getLogger(TreeRenderer.class);

    private final VariableManager variableManager;

    private final TreeDescription treeDescription;

    public TreeRenderer(VariableManager variableManager, TreeDescription treeDescription) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.treeDescription = Objects.requireNonNull(treeDescription);
    }

    public Tree render() {
        String treeId = this.treeDescription.getIdProvider().apply(this.variableManager);
        String targetObjectId = this.treeDescription.getTargetObjectIdProvider().apply(this.variableManager);

        List<?> rootElements = this.treeDescription.getElementsProvider().apply(this.variableManager);
        List<TreeItem> childrenItems = new ArrayList<>(rootElements.size());

        this.variableManager.put(ANCESTOR_IDS, new ArrayList<>());
        int index = 0;
        for (Object rootElement : rootElements) {
            VariableManager rootElementVariableManager = this.variableManager.createChild();
            rootElementVariableManager.put(VariableManager.SELF, rootElement);
            rootElementVariableManager.put(INDEX, index++);
            childrenItems.add(this.renderTreeItem(rootElementVariableManager));
        }

        return Tree.newTree(treeId)
                .targetObjectId(targetObjectId)
                .descriptionId(this.treeDescription.getId())
                .children(childrenItems)
                .build();
    }

    private TreeItem renderTreeItem(VariableManager treeItemVariableManager) {
        String id = this.treeDescription.getTreeItemIdProvider().apply(treeItemVariableManager);
        String kind = this.treeDescription.getKindProvider().apply(treeItemVariableManager);
        StyledString label = this.treeDescription.getTreeItemLabelProvider().apply(treeItemVariableManager);
        boolean editable = this.treeDescription.getEditableProvider().apply(treeItemVariableManager);
        boolean deletable = this.treeDescription.getDeletableProvider().apply(treeItemVariableManager);
        boolean selectable = this.treeDescription.getSelectableProvider().apply(treeItemVariableManager);
        List<String> iconURLs = this.treeDescription.getIconURLProvider().apply(treeItemVariableManager);

        if (this.loopDetected(treeItemVariableManager, id)) {
            return this.renderWarningTreeItem(id, kind, label, iconURLs, treeItemVariableManager);
        }

        Boolean hasChildren = this.treeDescription.getHasChildrenProvider().apply(treeItemVariableManager);
        List<?> children = this.treeDescription.getChildrenProvider().apply(treeItemVariableManager);
        List<TreeItem> childrenTreeItems = new ArrayList<>(children.size());

        int childIndex = 0;
        boolean expanded = !children.isEmpty();
        for (Object child : children) {
            VariableManager childVariableManager = treeItemVariableManager.createChild();
            List<Object> childAncestors = new ArrayList<Object>(treeItemVariableManager.get(ANCESTOR_IDS, List.class).orElse(List.of()));
            childAncestors.add(id);
            childVariableManager.put(ANCESTOR_IDS, childAncestors);
            childVariableManager.put(VariableManager.SELF, child);
            childVariableManager.put(INDEX, childIndex++);
            childrenTreeItems.add(this.renderTreeItem(childVariableManager));
        }
        return TreeItem
                .newTreeItem(id)
                .kind(kind)
                .label(label)
                .editable(editable)
                .deletable(deletable)
                .selectable(selectable)
                .iconURL(iconURLs)
                .children(childrenTreeItems)
                .hasChildren(hasChildren)
                .expanded(expanded)
                .build();
    }

    private TreeItem renderWarningTreeItem(String id, String kind, StyledString label, List<String> iconURL, VariableManager currentVariableManager) {
        this.logger.warn("Possible infinite loop encountered during tree rendering. Item with id '{}' has already been encountered as ancestors", id);

        List<StyledStringFragment> fragments = new ArrayList<>(label.styledStringFragments());
        fragments.add(0, new StyledStringFragment("<Item rendering loop> ", StyledStringFragmentStyle.newDefaultStyledStringFragmentStyle()
                .foregroundColor("#ff8c00")
                .build()));
        StyledString newLabel = new StyledString(fragments);

        List<Object> idPath = currentVariableManager.get(ANCESTOR_IDS, List.class).orElse(new ArrayList<>());
        idPath.add(id);
        String path =  idPath.stream().map(Object::toString).collect(joining("::")).toString() + "#" + currentVariableManager.get(INDEX, Integer.class);

        String virtualId = "siriuscomponent://treelooprendering?renderedItemId=" + id + "&itemItem=" + UUID.nameUUIDFromBytes(path.getBytes());

        return TreeItem.newTreeItem(virtualId)
                .kind(kind)
                .label(newLabel)
                .editable(false)
                .deletable(false)
                .selectable(false)
                .iconURL(iconURL)
                .children(List.of())
                .hasChildren(false)
                .expanded(false)
                .build();
    }

    /**
     * Checks if the id of the current item has already been used by its ancestors.
     *
     * @param treeItemVariableManager
     *            the VariableManager
     * @param id
     *            the current TreeItem id
     * @return <code>true</code> if a loop is detected
     */
    private boolean loopDetected(VariableManager treeItemVariableManager, String id) {
        // @formatter:off
        return treeItemVariableManager.get(ANCESTOR_IDS, List.class)
                .orElse(List.of()).stream()
                .anyMatch(ancestorId -> Objects.equals(ancestorId, id));
     // @formatter:on
    }
}
