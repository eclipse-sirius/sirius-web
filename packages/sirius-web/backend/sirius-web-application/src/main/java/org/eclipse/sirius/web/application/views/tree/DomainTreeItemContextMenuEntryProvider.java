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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeItemContextMenuEntryProvider;
import org.eclipse.sirius.components.collaborative.trees.dto.ITreeItemContextMenuEntry;
import org.eclipse.sirius.components.collaborative.trees.dto.SingleClickTreeItemContextMenuEntry;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IReadOnlyObjectPredicate;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.web.application.views.explorer.services.ExplorerTreeItemContextMenuEntryProvider;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.springframework.stereotype.Service;

/**
 * Provides the context menu entries for tree items in the domain explorer.
 *
 * @author gdaniel
 */
@Service
public class DomainTreeItemContextMenuEntryProvider implements ITreeItemContextMenuEntryProvider {

    private final IObjectSearchService objectSearchService;

    private final IReadOnlyObjectPredicate readOnlyObjectPredicate;

    public DomainTreeItemContextMenuEntryProvider(IObjectSearchService objectSearchService, IReadOnlyObjectPredicate readOnlyObjectPredicate) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.readOnlyObjectPredicate = Objects.requireNonNull(readOnlyObjectPredicate);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, TreeDescription treeDescription, Tree tree, TreeItem treeItem) {
        return treeDescription.getId().equals(DomainTreeRepresentationDescriptionProvider.DESCRIPTION_ID);
    }

    @Override
    public List<ITreeItemContextMenuEntry> getTreeItemContextMenuEntries(IEditingContext editingContext, TreeDescription treeDescription, Tree tree, TreeItem treeItem) {
        List<ITreeItemContextMenuEntry> result = new ArrayList<>();
        if (editingContext instanceof IEMFEditingContext emfEditingContext) {
            result.addAll(this.getDocumentContextMenuEntries(emfEditingContext, treeItem));
            result.addAll(this.getObjectContextMenuEntries(emfEditingContext, treeItem));
            result.addAll(this.getRepresentationContextMenuEntries(emfEditingContext, treeItem));
        }
        if (treeItem.isHasChildren()) {
            result.add(new SingleClickTreeItemContextMenuEntry(ExplorerTreeItemContextMenuEntryProvider.EXPAND_ALL, "", List.of(), false));
        }
        return result;
    }

    private List<ITreeItemContextMenuEntry> getDocumentContextMenuEntries(IEMFEditingContext editingContext, TreeItem treeItem) {
        var optionalResource = this.objectSearchService.getObject(editingContext, treeItem.getId())
                .filter(Resource.class::isInstance)
                .map(Resource.class::cast);
        if (optionalResource.isPresent()) {
            var resource = optionalResource.get();

            List<ITreeItemContextMenuEntry> entries = new ArrayList<>();
            if (!this.readOnlyObjectPredicate.test(resource)) {
                entries.add(new SingleClickTreeItemContextMenuEntry(ExplorerTreeItemContextMenuEntryProvider.NEW_ROOT_OBJECT, "", List.of(), false));
            }
            entries.add(new SingleClickTreeItemContextMenuEntry(ExplorerTreeItemContextMenuEntryProvider.DOWNLOAD_DOCUMENT, "", List.of(), false));
            return entries;
        }
        return List.of();
    }

    private List<ITreeItemContextMenuEntry> getObjectContextMenuEntries(IEMFEditingContext editingContext, TreeItem treeItem) {
        var optionalEObject = this.objectSearchService.getObject(editingContext, treeItem.getId())
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast);
        if (optionalEObject.isPresent()) {
            var object = optionalEObject.get();
            if (!this.readOnlyObjectPredicate.test(object)) {
                return List.of(
                        new SingleClickTreeItemContextMenuEntry(ExplorerTreeItemContextMenuEntryProvider.NEW_OBJECT, "", List.of(), false),
                        new SingleClickTreeItemContextMenuEntry(ExplorerTreeItemContextMenuEntryProvider.NEW_REPRESENTATION, "", List.of(), false),
                        new SingleClickTreeItemContextMenuEntry(ExplorerTreeItemContextMenuEntryProvider.DUPLICATE_OBJECT, "", List.of(), false)
                );
            }
        }
        return List.of();
    }

    private List<ITreeItemContextMenuEntry> getRepresentationContextMenuEntries(IEMFEditingContext editingContext, TreeItem treeItem) {
        var optionalRepresentationMetadata = this.objectSearchService.getObject(editingContext, treeItem.getId())
                .filter(RepresentationMetadata.class::isInstance)
                .map(RepresentationMetadata.class::cast);
        if (optionalRepresentationMetadata.isPresent()) {
            return List.of(
                    new SingleClickTreeItemContextMenuEntry(ExplorerTreeItemContextMenuEntryProvider.DUPLICATE_REPRESENTATION, "", List.of(), false)
            );
        }
        return List.of();
    }

}
