/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.web.services.explorer;

import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.trees.api.IInitialDirectEditTreeItemLabelProvider;
import org.eclipse.sirius.components.collaborative.trees.dto.InitialDirectEditElementLabelInput;
import org.eclipse.sirius.components.collaborative.trees.dto.InitialDirectEditElementLabelSuccessPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.core.api.labels.StyledString;
import org.springframework.stereotype.Service;

/**
 * Used to compute the initial label to display when the direct edit of a tree item of the explorer is triggered on the frontend.
 *
 * @author mcharfadi
 */
@Service
public class ExplorerInitialDirectEditTreeItemLabelProvider implements IInitialDirectEditTreeItemLabelProvider {

    public static final String EXPLORER_DESCRIPTION_ID = UUID.nameUUIDFromBytes("explorer_tree_description".getBytes()).toString();

    public static final String EXPLORER_DOCUMENT_KIND = "siriusComponents://representation?type=Tree";

    public static final String EXPLORER_NAME = "Explorer";

    @Override
    public boolean canHandle(Tree tree) {
        return tree.getId().startsWith("explorer://");
    }

    @Override
    public IPayload handle(IEditingContext editingContext, Tree tree, InitialDirectEditElementLabelInput input) {
        String initialLabel = tree.getChildren().stream()
                .map(treeItems -> this.searchById(treeItems, input.treeItemId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(TreeItem::getLabel)
                .findFirst()
                .map(StyledString::toString)
                .orElse("");

        return new InitialDirectEditElementLabelSuccessPayload(input.id(), initialLabel);
    }

    private Optional<TreeItem> searchById(TreeItem treeItem, String id) {
        Optional<TreeItem> optionalTreeItem = Optional.empty();
        if (treeItem.getId().equals(id)) {
            optionalTreeItem = Optional.of(treeItem);
        }
        if (optionalTreeItem.isEmpty() && treeItem.isHasChildren()) {
            optionalTreeItem = treeItem.getChildren().stream()
                    .map(treeItems -> this.searchById(treeItems, id))
                    .filter(Optional::isPresent)
                    .map(Optional::get).findFirst();
        }
        return optionalTreeItem;
    }

}
