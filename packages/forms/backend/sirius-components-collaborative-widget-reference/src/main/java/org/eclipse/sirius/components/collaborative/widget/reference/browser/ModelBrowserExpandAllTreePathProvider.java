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
package org.eclipse.sirius.components.collaborative.widget.reference.browser;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.collaborative.trees.api.IExpandAllTreePathProvider;
import org.eclipse.sirius.components.collaborative.trees.dto.ExpandAllTreePathInput;
import org.eclipse.sirius.components.collaborative.trees.dto.ExpandAllTreePathSuccessPayload;
import org.eclipse.sirius.components.collaborative.trees.dto.TreePath;
import org.eclipse.sirius.components.collaborative.widget.reference.browser.api.IModelBrowserNavigationService;
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
 * Implementation of {@link IExpandAllTreePathProvider} for Widget Reference Tree.
 *
 * @author frouene
 */
@Service
public class ModelBrowserExpandAllTreePathProvider implements IExpandAllTreePathProvider {

    private final IIdentityService identityService;

    private final IContentService contentService;

    private final IModelBrowserNavigationService modelBrowserNavigationService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    public ModelBrowserExpandAllTreePathProvider(IIdentityService identityService, IContentService contentService, IModelBrowserNavigationService modelBrowserNavigationService, IRepresentationDescriptionSearchService representationDescriptionSearchService) {
        this.identityService = Objects.requireNonNull(identityService);
        this.contentService = Objects.requireNonNull(contentService);
        this.modelBrowserNavigationService = Objects.requireNonNull(modelBrowserNavigationService);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
    }

    @Override
    public boolean canHandle(Tree tree) {
        return tree.getDescriptionId().equals(ModelBrowsersDescriptionProvider.CONTAINER_DESCRIPTION_ID)
                || tree.getDescriptionId().equals(ModelBrowsersDescriptionProvider.REFERENCE_DESCRIPTION_ID);
    }

    @Override
    public IPayload handle(IEditingContext editingContext, Tree tree, ExpandAllTreePathInput input) {
        int maxDepth = 0;
        String treeItemId = input.treeItemId();
        Set<String> treeItemIdsToExpand = new LinkedHashSet<>();
        var object = this.getTreeItemObject(editingContext, treeItemId, tree);
        if (object instanceof EObject) {
            // We need to get the current depth of the tree item
            var itemAncestors = this.modelBrowserNavigationService.getAncestors(editingContext, treeItemId, tree);
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
        var object = this.getTreeItemObject(editingContext, treeItemId, tree);
        if (object instanceof EObject eObject) {
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
