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
package org.eclipse.sirius.web.application.views.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeItemPaletteProvider;
import org.eclipse.sirius.components.collaborative.trees.dto.palette.SingleClickTreeItemTool;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IReadOnlyObjectPredicate;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.palette.dto.Palette;
import org.eclipse.sirius.components.palette.dto.IPaletteEntry;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.web.application.messages.ISiriusWebApplicationMessageService;
import org.eclipse.sirius.web.application.views.explorer.services.ExplorerTreeItemContextMenuEntryProvider;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.springframework.stereotype.Service;

/**
 * Provides the palette tree items in the domain explorer.
 *
 * @author mcharfadi
 */
@Service
public class DomainTreeItemPaletteProvider implements ITreeItemPaletteProvider {

    private final IObjectSearchService objectSearchService;

    private final IReadOnlyObjectPredicate readOnlyObjectPredicate;

    private final ISiriusWebApplicationMessageService messageService;

    public DomainTreeItemPaletteProvider(IObjectSearchService objectSearchService, IReadOnlyObjectPredicate readOnlyObjectPredicate, ISiriusWebApplicationMessageService messageService) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.readOnlyObjectPredicate = Objects.requireNonNull(readOnlyObjectPredicate);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, TreeDescription treeDescription, Tree tree, TreeItem treeItem) {
        return treeDescription.getId().equals(DomainTreeRepresentationDescriptionProvider.DESCRIPTION_ID);
    }

    @Override
    public Palette getPalette(IEditingContext editingContext, TreeDescription treeDescription, Tree tree, TreeItem treeItem) {
        List<IPaletteEntry> paletteEntries = new ArrayList<>();
        if (editingContext instanceof IEMFEditingContext emfEditingContext) {
            paletteEntries.addAll(this.getDocumentTools(emfEditingContext, treeItem));
            paletteEntries.addAll(this.getObjectTools(emfEditingContext, treeItem));
            paletteEntries.addAll(this.getRepresentationTools(emfEditingContext, treeItem));
        }
        if (treeItem.isHasChildren()) {
            paletteEntries.add(new SingleClickTreeItemTool(ExplorerTreeItemContextMenuEntryProvider.EXPAND_ALL, this.messageService.treeToolExpandAll(), List.of(), false, List.of()));
        }
        return new Palette("", List.of(), paletteEntries);
    }

    private List<IPaletteEntry> getDocumentTools(IEMFEditingContext editingContext, TreeItem treeItem) {
        var optionalResource = this.objectSearchService.getObject(editingContext, treeItem.getId())
                .filter(Resource.class::isInstance)
                .map(Resource.class::cast);
        if (optionalResource.isPresent()) {
            var resource = optionalResource.get();

            List<IPaletteEntry> entries = new ArrayList<>();
            if (!this.readOnlyObjectPredicate.test(resource)) {
                entries.add(new SingleClickTreeItemTool(ExplorerTreeItemContextMenuEntryProvider.NEW_ROOT_OBJECT, this.messageService.treeToolNewObject(), List.of(), false, List.of()));
            }
            entries.add(new SingleClickTreeItemTool(ExplorerTreeItemContextMenuEntryProvider.DOWNLOAD_DOCUMENT, this.messageService.treeToolDownload(), List.of(), false, List.of()));
            return entries;
        }
        return List.of();
    }

    private List<IPaletteEntry> getObjectTools(IEMFEditingContext editingContext, TreeItem treeItem) {
        var optionalEObject = this.objectSearchService.getObject(editingContext, treeItem.getId())
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast);
        if (optionalEObject.isPresent()) {
            var object = optionalEObject.get();
            if (!this.readOnlyObjectPredicate.test(object)) {
                return List.of(
                        new SingleClickTreeItemTool(ExplorerTreeItemContextMenuEntryProvider.NEW_OBJECT, this.messageService.treeToolNewObject(), List.of(), false, List.of()),
                        new SingleClickTreeItemTool(ExplorerTreeItemContextMenuEntryProvider.NEW_REPRESENTATION, this.messageService.treeToolNewRepresentation(), List.of(), false, List.of()),
                        new SingleClickTreeItemTool(ExplorerTreeItemContextMenuEntryProvider.DUPLICATE_OBJECT, this.messageService.treeToolDuplicateObject(), List.of(), false, List.of())
                );
            }
        }
        return List.of();
    }

    private List<IPaletteEntry> getRepresentationTools(IEMFEditingContext editingContext, TreeItem treeItem) {
        var optionalRepresentationMetadata = this.objectSearchService.getObject(editingContext, treeItem.getId())
                .filter(RepresentationMetadata.class::isInstance)
                .map(RepresentationMetadata.class::cast);
        if (optionalRepresentationMetadata.isPresent()) {
            return List.of(
                    new SingleClickTreeItemTool(ExplorerTreeItemContextMenuEntryProvider.DUPLICATE_REPRESENTATION, this.messageService.treeToolDuplicateRepresentation(), List.of(), false, List.of())
            );
        }
        return List.of();
    }

}
