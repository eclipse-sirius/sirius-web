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
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.eclipse.sirius.components.collaborative.trees.api.IExpandAllTreePathProvider;
import org.eclipse.sirius.components.collaborative.trees.dto.ExpandAllTreePathInput;
import org.eclipse.sirius.components.collaborative.trees.dto.ExpandAllTreePathSuccessPayload;
import org.eclipse.sirius.components.collaborative.trees.dto.TreePath;
import org.eclipse.sirius.components.core.api.IContentService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.trees.Tree;
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

    private final IObjectSearchService objectSearchService;

    private final IIdentityService identityService;

    private final IContentService contentService;

    private final IExplorerNavigationService explorerNavigationService;

    private final IRepresentationDataSearchService representationDataSearchService;

    public ExpandAllTreePathProvider(IObjectSearchService objectSearchService, IIdentityService identityService, IContentService contentService, IExplorerNavigationService explorerNavigationService, IRepresentationDataSearchService representationDataSearchService) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.identityService = Objects.requireNonNull(identityService);
        this.contentService = Objects.requireNonNull(contentService);
        this.explorerNavigationService = Objects.requireNonNull(explorerNavigationService);
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
        Set<String> treeItemIdsToExpand = new HashSet<>();
        var object = this.objectSearchService.getObject(editingContext, treeItemId);
        if (object.isPresent()) {
            // We need to get the current depth of the tree item
            var itemAncestors = this.explorerNavigationService.getAncestors(editingContext, treeItemId);
            maxDepth = itemAncestors.size();
            maxDepth = this.addAllContents(editingContext, treeItemId, maxDepth, treeItemIdsToExpand);
        } else {
            // The object may be a document
            var optionalEditingDomain = Optional.of(editingContext).filter(IEMFEditingContext.class::isInstance)
                    .map(IEMFEditingContext.class::cast)
                    .map(IEMFEditingContext::getDomain);

            if (optionalEditingDomain.isPresent()) {
                var optionalResource = optionalEditingDomain.get().getResourceSet().getResources().stream()
                        .filter(resource -> treeItemId.equals(resource.getURI().path().substring(1)))
                        .findFirst();
                if (optionalResource.isPresent()) {
                    var contents = optionalResource.get().getContents();
                    if (!contents.isEmpty()) {
                        treeItemIdsToExpand.add(treeItemId);
                        for (var rootObject : contents) {
                            var rootObjectId = this.identityService.getId(rootObject);
                            var rootObjectTreePathMaxDepth = 1;
                            rootObjectTreePathMaxDepth = this.addAllContents(editingContext, rootObjectId, rootObjectTreePathMaxDepth, treeItemIdsToExpand);
                            maxDepth = Math.max(maxDepth, rootObjectTreePathMaxDepth);
                        }
                    }
                }
            }
        }
        return new ExpandAllTreePathSuccessPayload(input.id(), new TreePath(treeItemIdsToExpand.stream().toList(), maxDepth));
    }

    private int addAllContents(IEditingContext editingContext, String treeItemId, int depth, Set<String> treeItemIdsToExpand) {
        var depthConsidered = depth;
        Optional<Object> optionalObject = this.objectSearchService.getObject(editingContext, treeItemId);
        if (optionalObject.isPresent()) {
            var contents = this.contentService.getContents(optionalObject.get());
            if (!contents.isEmpty()) {
                treeItemIdsToExpand.add(treeItemId);

                for (var child : contents) {
                    String childId = this.identityService.getId(child);
                    treeItemIdsToExpand.add(childId);
                    var childTreePathMaxDepth = depth + 1;
                    childTreePathMaxDepth = this.addAllContents(editingContext, childId, childTreePathMaxDepth, treeItemIdsToExpand);
                    depthConsidered = Math.max(depthConsidered, childTreePathMaxDepth);
                }
            } else if (this.representationDataSearchService.existAnyRepresentationForTargetObjectId(treeItemId)) {
                treeItemIdsToExpand.add(treeItemId);
                depthConsidered = Math.max(depthConsidered, depth + 1);
            }
        }

        return depthConsidered;
    }
}
