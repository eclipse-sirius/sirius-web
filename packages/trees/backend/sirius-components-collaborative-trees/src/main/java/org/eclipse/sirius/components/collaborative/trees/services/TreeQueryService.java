/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
import java.util.UUID;

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
    public Optional<TreeItem> findTreeItem(Tree tree, UUID treeItemId) {
        // @formatter:off
        return tree.getChildren().stream()
                .map(childTreeItem -> this.findTreeItem(childTreeItem, treeItemId))
                .flatMap(Optional::stream)
                .findFirst();
        // @formatter:on
    }

    private Optional<TreeItem> findTreeItem(TreeItem treeItem, UUID treeItemId) {
        if (treeItem.getId().equals(treeItemId.toString())) {
            return Optional.of(treeItem);
        }
        // @formatter:off
        return treeItem.getChildren().stream()
                .map(childTreeItem -> this.findTreeItem(childTreeItem, treeItemId))
                .flatMap(Optional::stream)
                .findFirst();
        // @formatter:on
    }

}
