/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.trees.services;

import java.util.Optional;

import org.eclipse.sirius.components.collaborative.trees.services.api.ITreeQueryService;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.springframework.stereotype.Service;

/**
 * Used to query a tree.
 *
 * @author sbegaudeau
 */
@Service
public class TreeQueryService implements ITreeQueryService {

    @Override
    public Optional<TreeItem> findTreeItem(Tree tree, String treeItemId) {
        return tree.getChildren().stream()
                .map(childTreeItem -> this.findTreeItem(childTreeItem, treeItemId))
                .flatMap(Optional::stream)
                .findFirst();
    }

    private Optional<TreeItem> findTreeItem(TreeItem treeItem, String treeItemId) {
        if (treeItem.getId().equals(treeItemId)) {
            return Optional.of(treeItem);
        }
        return treeItem.getChildren().stream()
                .map(childTreeItem -> this.findTreeItem(childTreeItem, treeItemId))
                .flatMap(Optional::stream)
                .findFirst();
    }

}
