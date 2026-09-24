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
package org.eclipse.sirius.components.collaborative.trees;

import java.util.Locale;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.trees.api.ITreeFilter;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.springframework.stereotype.Service;

/**
 * Filters the rendered content of a tree.
 *
 * @author mcharfadi
 */
@Service
public class TreeFilter implements ITreeFilter {

    @Override
    public Tree filter(Tree tree, String searchedValue) {
        if (searchedValue.isEmpty()) {
            return tree;
        }

        String normalizedSearchedValue = searchedValue.toLowerCase(Locale.ROOT);
        var children = tree.getChildren().stream()
                .map(treeItem -> this.filter(treeItem, normalizedSearchedValue))
                .flatMap(Optional::stream)
                .toList();

        return Tree.newTree(tree.getId())
                .descriptionId(tree.getDescriptionId())
                .targetObjectId(tree.getTargetObjectId())
                .children(children)
                .build();
    }

    private Optional<TreeItem> filter(TreeItem treeItem, String normalizedSearchedValue) {
        boolean matches = treeItem.getLabel().toString().toLowerCase(Locale.ROOT).contains(normalizedSearchedValue);
        var children = treeItem.getChildren().stream()
                .map(child -> this.filter(child, normalizedSearchedValue))
                .flatMap(Optional::stream)
                .toList();

        if (matches || !children.isEmpty()) {
            return Optional.of(TreeItem.newTreeItem(treeItem.getId())
                    .kind(treeItem.getKind())
                    .label(treeItem.getLabel())
                    .editable(treeItem.isEditable())
                    .deletable(treeItem.isDeletable())
                    .selectable(treeItem.isSelectable())
                    .iconURL(treeItem.getIconURL())
                    .children(children)
                    .hasChildren(!children.isEmpty())
                    .expanded(!children.isEmpty())
                    .build());
        }
        return Optional.empty();
    }
}
