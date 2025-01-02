/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
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
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
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

    private static final int MAX_EXPAND_DEPTH_INCREASE = 100;

    private final ITreeNavigationService treeNavigationService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IURLParser urlParser;

    public DefaultExpandAllTreePathHandler(ITreeNavigationService treeNavigationService, IRepresentationDescriptionSearchService representationDescriptionSearchService, IURLParser urlParser) {
        this.treeNavigationService = Objects.requireNonNull(treeNavigationService);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.urlParser = Objects.requireNonNull(urlParser);
    }

    public IPayload handle(IEditingContext editingContext, Tree tree, ExpandAllTreePathInput input) {
        int maxDepth = 0;
        String treeItemId = input.treeItemId();
        Map<String, List<String>> parameters = this.urlParser.getParameterValues(tree.getId());

        Set<String> treeItemIdsToExpand = new LinkedHashSet<>();

        // We need to get the current depth of the tree item
        var itemAncestors = this.treeNavigationService.getAncestors(editingContext, tree, treeItemId);
        maxDepth = itemAncestors.size();
        var optionalTreeDescription = this.getTreeDescription(editingContext, tree.getDescriptionId());
        if (optionalTreeDescription.isPresent()) {
            List<String> activeFilterIds = Optional.ofNullable(parameters.get(TreeRenderer.ACTIVE_FILTER_IDS))
                    .map(activeFilterIdsParams -> activeFilterIdsParams.get(0))
                    .map(this.urlParser::getParameterEntries)
                    .orElse(List.of());
            int index = this.computeIndexOf(treeItemId, tree.getChildren());
            var variableManager = new VariableManager();
            variableManager.put(TreeRenderer.INDEX, index);
            variableManager.put(TreeRenderer.ANCESTOR_IDS, itemAncestors);
            variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
            variableManager.put(TreeDescription.ID, treeItemId);
            variableManager.put(TreeRenderer.ACTIVE_FILTER_IDS, activeFilterIds);
            maxDepth = this.addAllContents(optionalTreeDescription.get(), treeItemId, maxDepth, treeItemIdsToExpand, maxDepth, variableManager);
        }
        return new ExpandAllTreePathSuccessPayload(input.id(), new TreePath(treeItemIdsToExpand.stream().toList(), maxDepth));
    }

    private int computeIndexOf(String treeItemId, List<TreeItem> children) {
        int index = -1;
        for (int currentIndex = 0; currentIndex < children.size() && index < 0; currentIndex++) {
            TreeItem currentItem = children.get(currentIndex);
            if (treeItemId.equals(currentItem.getId())) {
                index = currentIndex;
            } else {
                index = this.computeIndexOf(treeItemId, currentItem.getChildren());
            }
        }
        return index;
    }

    private int addAllContents(TreeDescription treeDescription, String treeItemId, int depth, Set<String> treeItemIdsToExpand, int startingDepth, VariableManager variableManager) {
        var depthConsidered = depth;
        if (depthConsidered - startingDepth < MAX_EXPAND_DEPTH_INCREASE) {

            var optionalObject = this.getTreeItemObject(treeDescription, variableManager);
            treeItemIdsToExpand.add(treeItemId);
            variableManager.put(TreeRenderer.EXPANDED, treeItemIdsToExpand.stream().toList());
            if (optionalObject.isPresent()) {
                variableManager.put(VariableManager.SELF, optionalObject.get());
                if (this.hasChildren(treeDescription, variableManager)) {

                    List<?> children = this.getChildren(treeDescription, variableManager);
                    int index = 0;
                    for (var child : children) {
                        VariableManager childVariableManager = variableManager.createChild();
                        childVariableManager.put(TreeRenderer.INDEX, index++);
                        List<String> ancestors = new ArrayList<String>(childVariableManager.get(TreeRenderer.ANCESTOR_IDS, List.class).orElse(List.of()));
                        ancestors.add(treeItemId);
                        childVariableManager.put(TreeRenderer.ANCESTOR_IDS, ancestors);
                        var optionalChildId = this.getTreeItemId(treeDescription, child);
                        if (optionalChildId.isPresent()) {
                            childVariableManager.put(TreeDescription.ID, optionalChildId.get());
                            var childTreePathMaxDepth = depth + 1;
                            childTreePathMaxDepth = this.addAllContents(treeDescription, optionalChildId.get(), childTreePathMaxDepth, treeItemIdsToExpand, startingDepth, childVariableManager);
                            depthConsidered = Math.max(depthConsidered, childTreePathMaxDepth);
                        }
                    }
                }
            } else {
                depthConsidered = Math.max(depthConsidered, depth + 1);
            }
        }
        return depthConsidered;
    }

    private boolean hasChildren(TreeDescription treeDescription, VariableManager variableManager) {
        return treeDescription.getHasChildrenProvider().apply(variableManager);
    }

    private List<?> getChildren(TreeDescription treeDescription, VariableManager variableManager) {
        return treeDescription.getChildrenProvider().apply(variableManager);
    }

    private Optional<String> getTreeItemId(TreeDescription treeDescription, Object object) {
        var variableManager = new VariableManager();
        variableManager.put(VariableManager.SELF, object);
        return Optional.of(treeDescription.getTreeItemIdProvider().apply(variableManager));
    }

    private Optional<Object> getTreeItemObject(TreeDescription treeDescription, VariableManager variableManager) {
        return Optional.ofNullable(treeDescription.getTreeItemObjectProvider().apply(variableManager));
    }

    private Optional<TreeDescription> getTreeDescription(IEditingContext editingContext, String descriptionId) {
        return this.representationDescriptionSearchService.findById(editingContext, descriptionId)
                .filter(TreeDescription.class::isInstance)
                .map(TreeDescription.class::cast);
    }
}
