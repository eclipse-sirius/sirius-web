/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.eclipse.sirius.components.collaborative.trees.api.ITreePathProvider;
import org.eclipse.sirius.components.collaborative.trees.dto.TreePath;
import org.eclipse.sirius.components.collaborative.trees.dto.TreePathInput;
import org.eclipse.sirius.components.collaborative.trees.dto.TreePathSuccessPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerNavigationService;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link ITreePathProvider} for the Sirius Web Explorer.
 *
 * @author pcdavid
 */
@Service
public class ExplorerTreePathProvider implements ITreePathProvider {

    private final IExplorerNavigationService explorerNavigationService;

    public ExplorerTreePathProvider(IExplorerNavigationService explorerNavigationService) {
        this.explorerNavigationService = Objects.requireNonNull(explorerNavigationService);
    }

    @Override
    public boolean canHandle(Tree tree) {
        return tree.getDescriptionId().equals(ExplorerDescriptionProvider.DESCRIPTION_ID);
    }

    @Override
    public IPayload handle(IEditingContext editingContext, Tree tree, TreePathInput input) {
        int maxDepth = 0;
        Set<String> allAncestors = new HashSet<>();
        for (String selectionEntryId : input.selectionEntryIds()) {
            List<String> itemAncestors = this.explorerNavigationService.getAncestors(editingContext, selectionEntryId);
            allAncestors.addAll(itemAncestors);
            maxDepth = Math.max(maxDepth, itemAncestors.size());
        }
        return new TreePathSuccessPayload(input.id(), new TreePath(allAncestors.stream().toList(), maxDepth));
    }
}
