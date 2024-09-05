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
package org.eclipse.sirius.components.collaborative.trees.handlers;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.eclipse.sirius.components.collaborative.trees.dto.ExpandAllTreePathInput;
import org.eclipse.sirius.components.collaborative.trees.dto.ExpandAllTreePathSuccessPayload;
import org.eclipse.sirius.components.collaborative.trees.dto.TreePath;
import org.eclipse.sirius.components.collaborative.trees.services.api.ITreeNavigationService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.components.trees.renderer.TreeRenderer;
import org.springframework.stereotype.Service;

/**
 * This class is used as a fallback to handle an ExpandAllTreePath event that no handlers can handle.
 *
 * @author Jerome Gout
 */
@Service
public class DefaultExpandAllTreePathHandler {
    private final ITreeNavigationService treeNavigationService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    public DefaultExpandAllTreePathHandler(ITreeNavigationService treeNavigationService, IRepresentationDescriptionSearchService representationDescriptionSearchService) {
        this.treeNavigationService = Objects.requireNonNull(treeNavigationService);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
    }

    public IPayload handle(IEditingContext editingContext, Tree tree, ExpandAllTreePathInput input) {
        int maxDepth = 0;
        String treeItemId = input.treeItemId();

        Set<String> treeItemIdsToExpand = new LinkedHashSet<>();
        // We need to get the current depth of the tree item
        var itemAncestors = this.treeNavigationService.getAncestors(editingContext, tree, treeItemId);
        maxDepth = itemAncestors.size();
        maxDepth = this.addAllContents(editingContext, treeItemId, maxDepth, treeItemIdsToExpand, tree);
        return new ExpandAllTreePathSuccessPayload(input.id(), new TreePath(treeItemIdsToExpand.stream().toList(), maxDepth));
    }

    private int addAllContents(IEditingContext editingContext, String treeItemId, int depth, Set<String> treeItemIdsToExpand, Tree tree) {
        var depthConsidered = depth;
        var optionalObject = this.getTreeItemObject(editingContext, tree, treeItemId);
        treeItemIdsToExpand.add(treeItemId);
        if (optionalObject.isPresent() && this.hasChildren(editingContext, tree, optionalObject.get())) {
            List<?> children = this.getChildren(editingContext, tree, optionalObject.get(), treeItemId);
            for (var child: children) {
                var optionalChildId = this.getTreeItemId(editingContext, tree, child);
                if (optionalChildId.isPresent()) {
                    var childTreePathMaxDepth = depth + 1;
                    childTreePathMaxDepth = this.addAllContents(editingContext, optionalChildId.get(), childTreePathMaxDepth, treeItemIdsToExpand, tree);
                    depthConsidered = Math.max(depthConsidered, childTreePathMaxDepth);
                }
            }
        } else {
            depthConsidered = Math.max(depthConsidered, depth + 1);
        }
        return depthConsidered;
    }

    private boolean hasChildren(IEditingContext editingContext, Tree tree, Object object) {
        boolean result = false;
        var optionalTreeDescription = this.getTreeDescription(editingContext, tree.getDescriptionId());
        if (optionalTreeDescription.isPresent()) {
            var variableManager = new VariableManager();
            variableManager.put(VariableManager.SELF, object);
            result = optionalTreeDescription.get().getHasChildrenProvider().apply(variableManager);
        }
        return result;
    }

    private List<?> getChildren(IEditingContext editingContext, Tree tree, Object object, String treeItemId) {
        List<?> result = List.of();
        var optionalTreeDescription = this.getTreeDescription(editingContext, tree.getDescriptionId());

        if (optionalTreeDescription.isPresent()) {
            var variableManager = new VariableManager();
            variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
            variableManager.put(VariableManager.SELF, object);
            variableManager.put(TreeRenderer.EXPANDED, List.of(treeItemId)); // getChildren only returns children if self is expanded
            result = optionalTreeDescription.get().getChildrenProvider().apply(variableManager);
        }
        return result;
    }

    private Optional<String> getTreeItemId(IEditingContext editingContext, Tree tree, Object object) {
        Optional<String> result = Optional.empty();
        var optionalTreeDescription = this.getTreeDescription(editingContext, tree.getDescriptionId());
        if (optionalTreeDescription.isPresent()) {
            var variableManager = new VariableManager();
            variableManager.put(VariableManager.SELF, object);
            result = Optional.of(optionalTreeDescription.get().getTreeItemIdProvider().apply(variableManager));
        }
        return result;
    }

    private Optional<Object> getTreeItemObject(IEditingContext editingContext, Tree tree, String id) {
        var optionalTreeDescription = this.getTreeDescription(editingContext, tree.getDescriptionId());

        if (optionalTreeDescription.isPresent()) {
            var variableManager = new VariableManager();
            variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
            variableManager.put(TreeDescription.ID, id);
            return Optional.ofNullable(optionalTreeDescription.get().getTreeItemObjectProvider().apply(variableManager));
        }
        return Optional.empty();
    }

    private Optional<TreeDescription> getTreeDescription(IEditingContext editingContext, String descriptionId) {
        return this.representationDescriptionSearchService.findById(editingContext, descriptionId)
                .filter(TreeDescription.class::isInstance)
                .map(TreeDescription.class::cast);
    }
}
