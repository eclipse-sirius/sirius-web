/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.views.tree;

import java.util.List;

import org.eclipse.sirius.components.collaborative.trees.api.ITreeItemContextMenuEntryProvider;
import org.eclipse.sirius.components.collaborative.trees.dto.ITreeItemContextMenuEntry;
import org.eclipse.sirius.components.collaborative.trees.dto.SingleClickTreeItemContextMenuEntry;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.springframework.stereotype.Service;

/**
 * Provides the context menu entries for tree items in the domain explorer.
 *
 * @author gdaniel
 */
@Service
public class DomainTreeItemContextMenuEntryProvider implements ITreeItemContextMenuEntryProvider {

    @Override
    public boolean canHandle(IEditingContext editingContext, TreeDescription treeDescription, Tree tree, TreeItem treeItem) {
        return treeDescription.getId().equals(DomainTreeRepresentationDescriptionProvider.DESCRIPTION_ID);
    }

    @Override
    public List<ITreeItemContextMenuEntry> getTreeItemContextMenuEntries(IEditingContext editingContext, TreeDescription treeDescription, Tree tree, TreeItem treeItem) {
        if (treeItem.isHasChildren()) {
            return List.of(new SingleClickTreeItemContextMenuEntry("expandAll", "", List.of()));
        } else {
            return List.of();
        }
    }

}
