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
package org.eclipse.sirius.web.application.views.explorer.services;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.trees.api.ITreeItemTooltipProvider;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerTooltipService;
import org.springframework.stereotype.Service;

/**
 * Provides tooltips for tree items from the explorer.
 *
 * @author gdaniel
 */
@Service
public class ExplorerTreeItemTooltipProvider implements ITreeItemTooltipProvider {

    private final IObjectSearchService objectSearchService;

    private final IExplorerTooltipService explorerTooltipService;

    public ExplorerTreeItemTooltipProvider(IObjectSearchService objectSearchService, IExplorerTooltipService explorerTooltipService) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.explorerTooltipService = Objects.requireNonNull(explorerTooltipService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, TreeDescription treeDescription, Tree tree, TreeItem treeItem) {
        return tree.getId().startsWith(ExplorerDescriptionProvider.PREFIX)
                && Objects.equals(tree.getDescriptionId(), ExplorerDescriptionProvider.DESCRIPTION_ID);
    }

    @Override
    public String handle(IEditingContext editingContext, TreeDescription treeDescription, Tree tree, TreeItem treeItem) {
        String result = "";

        Optional<Object> optionalObject = this.objectSearchService.getObject(editingContext, treeItem.getId());
        if (optionalObject.isPresent()) {
            Object object = optionalObject.get();
            result = this.explorerTooltipService.getTooltip(object);
        }
        return result;
    }
}
