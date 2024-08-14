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
package org.eclipse.sirius.components.collaborative.selection.services;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import org.eclipse.sirius.components.collaborative.selection.services.api.ISelectionDialogNavigationService;
import org.eclipse.sirius.components.collaborative.trees.api.IExpandAllTreePathProvider;
import org.eclipse.sirius.components.collaborative.trees.dto.ExpandAllTreePathInput;
import org.eclipse.sirius.components.collaborative.trees.dto.ExpandAllTreePathSuccessPayload;
import org.eclipse.sirius.components.collaborative.trees.dto.TreePath;
import org.eclipse.sirius.components.core.api.IContentService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IExpandAllTreePathProvider} for the Selection Dialog Tree.
 *
 * @author frouene
 * @author fbarbin
 */
@Service
public class SelectionDialogExpandAllTreePathProvider implements IExpandAllTreePathProvider {

    private final IIdentityService identityService;

    private final IContentService contentService;

    private final ISelectionDialogNavigationService selectionDialogNavigationService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    public SelectionDialogExpandAllTreePathProvider(IIdentityService identityService, IContentService contentService, ISelectionDialogNavigationService selectionDialogNavigationService, IRepresentationDescriptionSearchService representationDescriptionSearchService) {
        this.identityService = Objects.requireNonNull(identityService);
        this.contentService = Objects.requireNonNull(contentService);
        this.selectionDialogNavigationService = Objects.requireNonNull(selectionDialogNavigationService);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
    }

    @Override
    public boolean canHandle(Tree tree) {
        return  tree.getDescriptionId().startsWith(ISelectionDialogNavigationService.SELECTION_DIALOG_TREE_DESCRIPTION_KIND);
    }

    @Override
    public IPayload handle(IEditingContext editingContext, Tree tree, ExpandAllTreePathInput input) {
        int maxDepth = 0;
        String treeItemId = input.treeItemId();
        Set<String> treeItemIdsToExpand = new LinkedHashSet<>();
        var object = this.getTreeItemObject(editingContext, treeItemId, tree);
        if (object != null) {
            // We need to get the current depth of the tree item
            var itemAncestors = this.selectionDialogNavigationService.getAncestors(editingContext, treeItemId, tree);
            maxDepth = itemAncestors.size();
            maxDepth = this.addAllContents(editingContext, treeItemId, maxDepth, treeItemIdsToExpand, tree);
        }
        return new ExpandAllTreePathSuccessPayload(input.id(), new TreePath(treeItemIdsToExpand.stream().toList(), maxDepth));
    }

    private int addAllContents(IEditingContext editingContext, String treeItemId, int depth, Set<String> treeItemIdsToExpand, Tree tree) {
        var depthConsidered = depth;
        var object = this.getTreeItemObject(editingContext, treeItemId, tree);
        if (object != null) {
            var contents = this.contentService.getContents(object);
            if (!contents.isEmpty()) {
                treeItemIdsToExpand.add(treeItemId);

                for (var child : contents) {
                    String childId = this.identityService.getId(child);
                    treeItemIdsToExpand.add(childId);
                    var childTreePathMaxDepth = depth + 1;
                    childTreePathMaxDepth = this.addAllContents(editingContext, childId, childTreePathMaxDepth, treeItemIdsToExpand, tree);
                    depthConsidered = Math.max(depthConsidered, childTreePathMaxDepth);
                }
            }
        }

        return depthConsidered;
    }

    private Object getTreeItemObject(IEditingContext editingContext, String treeItemId, Tree tree) {
        var optionalTreeDescription = this.representationDescriptionSearchService.findById(editingContext, tree.getDescriptionId())
                .filter(TreeDescription.class::isInstance)
                .map(TreeDescription.class::cast);

        if (optionalTreeDescription.isPresent()) {
            var variableManager = new VariableManager();
            variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
            variableManager.put(TreeDescription.ID, treeItemId);
            return optionalTreeDescription.get().getTreeItemObjectProvider().apply(variableManager);
        }
        return null;
    }
}
