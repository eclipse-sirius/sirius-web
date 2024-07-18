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

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.collaborative.trees.api.IExpandAllTreePathProvider;
import org.eclipse.sirius.components.collaborative.trees.dto.ExpandAllTreePathInput;
import org.eclipse.sirius.components.collaborative.trees.dto.ExpandAllTreePathSuccessPayload;
import org.eclipse.sirius.components.collaborative.trees.dto.TreePath;
import org.eclipse.sirius.components.core.api.IContentService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.domain.Entity;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerNavigationService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationDataSearchService;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IExpandAllTreePathProvider} for Sirius Web Tree.
 *
 * @author arichard
 */
@Service
public class ExpandAllTreePathProvider implements IExpandAllTreePathProvider {

    private final IIdentityService identityService;

    private final IContentService contentService;

    private final IExplorerNavigationService explorerNavigationService;

    private final IRepresentationDataSearchService representationDataSearchService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    public ExpandAllTreePathProvider(IIdentityService identityService, IContentService contentService, IExplorerNavigationService explorerNavigationService, IRepresentationDescriptionSearchService representationDescriptionSearchService, IRepresentationDataSearchService representationDataSearchService) {
        this.identityService = Objects.requireNonNull(identityService);
        this.contentService = Objects.requireNonNull(contentService);
        this.explorerNavigationService = Objects.requireNonNull(explorerNavigationService);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.representationDataSearchService = Objects.requireNonNull(representationDataSearchService);
    }

    @Override
    public boolean canHandle(Tree tree) {
        return tree.getDescriptionId().equals(ExplorerDescriptionProvider.DESCRIPTION_ID);
    }

    @Override
    public IPayload handle(IEditingContext editingContext, Tree tree, ExpandAllTreePathInput input) {
        int maxDepth = 0;
        String treeItemId = input.treeItemId();

        Set<String> treeItemIdsToExpand = new LinkedHashSet<>();
        var object = this.getTreeItemObject(editingContext, tree, treeItemId);
        if (object instanceof EObject || treeItemId.startsWith(ExplorerDescriptionProvider.SETTING)) {
            // We need to get the current depth of the tree item
            var itemAncestors = this.explorerNavigationService.getAncestors(editingContext, tree, treeItemId);
            maxDepth = itemAncestors.size();
            maxDepth = this.addAllContents(editingContext, treeItemId, maxDepth, treeItemIdsToExpand, tree);
        } else if (object instanceof Resource resource) {
            // The object may be a document
            var contents = resource.getContents();
            if (!contents.isEmpty()) {
                treeItemIdsToExpand.add(treeItemId);
                for (var rootObject : contents) {
                    var rootObjectId = this.identityService.getId(rootObject);
                    var rootObjectTreePathMaxDepth = 1;
                    rootObjectTreePathMaxDepth = this.addAllContents(editingContext, rootObjectId, rootObjectTreePathMaxDepth, treeItemIdsToExpand, tree);
                    maxDepth = Math.max(maxDepth, rootObjectTreePathMaxDepth);
                }
            }
        }
        return new ExpandAllTreePathSuccessPayload(input.id(), new TreePath(treeItemIdsToExpand.stream().toList(), maxDepth));
    }

    private int addAllContents(IEditingContext editingContext, String treeItemId, int depth, Set<String> treeItemIdsToExpand, Tree tree) {
        var depthConsidered = depth;
        var object = this.getTreeItemObject(editingContext, tree, treeItemId);

        if (treeItemId.startsWith(ExplorerDescriptionProvider.SETTING) && object instanceof List<?> list) {
            treeItemIdsToExpand.add(treeItemId);
            for (var child : list) {
                String childId = this.identityService.getId(child);
                treeItemIdsToExpand.add(childId);
                var childTreePathMaxDepth = depth + 1;
                childTreePathMaxDepth = this.addAllContents(editingContext, childId, childTreePathMaxDepth, treeItemIdsToExpand, tree);
                depthConsidered = Math.max(depthConsidered, childTreePathMaxDepth);
            }
        } else if (object instanceof EObject eObject) {
            if (object instanceof Entity entity) {
                // an Entity has a virtual node for its super types, this node should be a child of the Entity
                var id = ExplorerDescriptionProvider.SETTING + this.identityService.getId(entity) + ExplorerDescriptionProvider.SETTING_ID_SEPARATOR + "superTypes";
                treeItemIdsToExpand.add(id);
                var superTypes = entity.getSuperTypes();
                if (superTypes.size() > 0) {
                    depthConsidered = Math.max(depthConsidered, depth + 2);
                } else {
                    depthConsidered = Math.max(depthConsidered, depth + 1);
                }
            }
            var contents = this.contentService.getContents(eObject);
            if (!contents.isEmpty()) {
                treeItemIdsToExpand.add(treeItemId);

                for (var child : contents) {
                    String childId = this.identityService.getId(child);
                    treeItemIdsToExpand.add(childId);
                    var childTreePathMaxDepth = depth + 1;
                    childTreePathMaxDepth = this.addAllContents(editingContext, childId, childTreePathMaxDepth, treeItemIdsToExpand, tree);
                    depthConsidered = Math.max(depthConsidered, childTreePathMaxDepth);
                }
            } else if (this.representationDataSearchService.existAnyRepresentationForTargetObjectId(treeItemId)) {
                treeItemIdsToExpand.add(treeItemId);
                depthConsidered = Math.max(depthConsidered, depth + 1);
            }
        }

        return depthConsidered;
    }

    private Object getTreeItemObject(IEditingContext editingContext, Tree tree, String treeItemId) {
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
