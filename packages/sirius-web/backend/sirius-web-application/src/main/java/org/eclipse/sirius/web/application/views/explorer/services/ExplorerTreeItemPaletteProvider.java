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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.common.notify.Notifier;
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
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.library.services.LibraryMetadataAdapter;
import org.eclipse.sirius.web.application.messages.ISiriusWebApplicationMessageService;
import org.eclipse.sirius.web.library.domain.Library;
import org.eclipse.sirius.web.library.domain.services.api.ILibrarySearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataSearchService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Provides the palette for the explorer.
 *
 * @author mcharfadi
 */
@Service
public class ExplorerTreeItemPaletteProvider implements ITreeItemPaletteProvider {

    public static final String EXPAND_ALL = "expand-all";

    public static final String NEW_ROOT_OBJECT = "new-root-object";

    public static final String DOWNLOAD_DOCUMENT = "download-document";

    public static final String NEW_OBJECT = "new-object";

    public static final String NEW_REPRESENTATION = "new-representation";

    public static final String DUPLICATE_OBJECT = "duplicate-object";

    public static final String DUPLICATE_REPRESENTATION = "duplicate-representation";

    public static final String UPDATE_LIBRARY = "update-library";

    public static final String REMOVE_LIBRARY = "remove-library";

    private final IObjectSearchService objectSearchService;

    private final IReadOnlyObjectPredicate readOnlyObjectPredicate;

    private final ILibrarySearchService librarySearchService;

    private final ISemanticDataSearchService semanticDataSearchService;

    private final ISiriusWebApplicationMessageService messageService;

    public ExplorerTreeItemPaletteProvider(IObjectSearchService objectSearchService, IReadOnlyObjectPredicate readOnlyObjectPredicate, ILibrarySearchService librarySearchService, ISemanticDataSearchService semanticDataSearchService, ISiriusWebApplicationMessageService messageService) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.readOnlyObjectPredicate = Objects.requireNonNull(readOnlyObjectPredicate);
        this.librarySearchService = Objects.requireNonNull(librarySearchService);
        this.semanticDataSearchService = Objects.requireNonNull(semanticDataSearchService);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, TreeDescription treeDescription, Tree tree, TreeItem treeItem) {
        return tree.getId().startsWith(ExplorerDescriptionProvider.PREFIX)
                && Objects.equals(tree.getDescriptionId(), ExplorerDescriptionProvider.DESCRIPTION_ID);
    }

    @Override
    public Palette getPalette(IEditingContext editingContext, TreeDescription treeDescription, Tree tree, TreeItem treeItem) {
        List<IPaletteEntry> paletteEntries = new ArrayList<>();
        if (editingContext instanceof IEMFEditingContext emfEditingContext) {
            paletteEntries.addAll(this.getDocumentContextMenuEntries(emfEditingContext, treeItem));
            paletteEntries.addAll(this.getObjectContextMenuEntries(emfEditingContext, treeItem));
            paletteEntries.addAll(this.getRepresentationContextMenuEntries(emfEditingContext, treeItem));
            paletteEntries.addAll(this.getLibraryRelatedEntries(emfEditingContext, treeItem));
        }
        if (treeItem.isHasChildren()) {
            paletteEntries.add(new SingleClickTreeItemTool(EXPAND_ALL, this.messageService.treeToolExpandAll(), List.of(), false, List.of()));
        }
        return new Palette("", List.of(), paletteEntries);
    }

    private List<IPaletteEntry> getDocumentContextMenuEntries(IEMFEditingContext editingContext, TreeItem treeItem) {
        var optionalResource = this.objectSearchService.getObject(editingContext, treeItem.getId())
                .filter(Resource.class::isInstance)
                .map(Resource.class::cast);
        if (optionalResource.isPresent()) {
            var resource = optionalResource.get();

            List<IPaletteEntry> entries = new ArrayList<>();
            if (!this.readOnlyObjectPredicate.test(resource)) {
                entries.add(new SingleClickTreeItemTool(NEW_ROOT_OBJECT, this.messageService.treeToolNewObject(), List.of(), false, List.of()));
            }
            entries.add(new SingleClickTreeItemTool(DOWNLOAD_DOCUMENT, this.messageService.treeToolDownload(), List.of(), false, List.of()));
            return entries;
        }
        return List.of();
    }

    private List<IPaletteEntry> getObjectContextMenuEntries(IEMFEditingContext editingContext, TreeItem treeItem) {
        var optionalEObject = this.objectSearchService.getObject(editingContext, treeItem.getId())
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast);
        if (optionalEObject.isPresent()) {
            var object = optionalEObject.get();
            if (!this.readOnlyObjectPredicate.test(object)) {
                return List.of(
                        new SingleClickTreeItemTool(NEW_OBJECT, this.messageService.treeToolNewObject(), List.of(), false, List.of()),
                        new SingleClickTreeItemTool(NEW_REPRESENTATION, this.messageService.treeToolNewRepresentation(), List.of(), false, List.of()),
                        new SingleClickTreeItemTool(DUPLICATE_OBJECT, this.messageService.treeToolDuplicateObject(), List.of(), false, List.of())
                );
            }
        }
        return List.of();
    }

    private List<IPaletteEntry> getRepresentationContextMenuEntries(IEMFEditingContext editingContext, TreeItem treeItem) {
        var optionalRepresentationMetadata = this.objectSearchService.getObject(editingContext, treeItem.getId())
                .filter(RepresentationMetadata.class::isInstance)
                .map(RepresentationMetadata.class::cast);
        if (optionalRepresentationMetadata.isPresent()) {
            return List.of(
                    new SingleClickTreeItemTool(DUPLICATE_REPRESENTATION, this.messageService.treeToolDuplicateRepresentation(), List.of(), false, List.of())
            );
        }
        return List.of();
    }

    private List<IPaletteEntry> getLibraryRelatedEntries(IEMFEditingContext editingContext, TreeItem treeItem) {
        List<IPaletteEntry> result = new ArrayList<>();
        var optionalNotifier = this.objectSearchService.getObject(editingContext, treeItem.getId())
                .filter(Notifier.class::isInstance)
                .map(Notifier.class::cast);

        if (optionalNotifier.isEmpty()) {
            optionalNotifier = editingContext.getDomain().getResourceSet().getResources().stream()
                    .filter(resource -> resource.getURI().toString().contains(treeItem.getId()))
                    .map(Notifier.class::cast)
                    .findFirst();
        }

        var optionalLibraryMetadataAdapter = optionalNotifier.stream()
                .map(Notifier::eAdapters)
                .flatMap(Collection::stream)
                .filter(LibraryMetadataAdapter.class::isInstance)
                .map(LibraryMetadataAdapter.class::cast)
                .findFirst();

        if (optionalLibraryMetadataAdapter.isPresent()) {
            var libraryMetadataAdapter = optionalLibraryMetadataAdapter.get();
            if (this.isDirectDependency(editingContext, libraryMetadataAdapter)) {
                // We do not support the update or removal of a transitive dependency for the moment.
                result.add(new SingleClickTreeItemTool(UPDATE_LIBRARY, this.messageService.treeToolUpdateLibrary(), List.of(), true, List.of()));
                result.add(new SingleClickTreeItemTool(REMOVE_LIBRARY, this.messageService.treeToolRemoveLibrary(), List.of("/icons/remove_library.svg"), true, List.of()));
            }
        }
        return result;
    }

    private boolean isDirectDependency(IEMFEditingContext emfEditingContext, LibraryMetadataAdapter libraryMetadataAdapter) {
        var editingContextDependencies = new UUIDParser().parse(emfEditingContext.getId())
                .map(this.semanticDataSearchService::findAllDependenciesIdById)
                .stream()
                .flatMap(Collection::stream)
                .map(AggregateReference::getId)
                .toList();
        return this.librarySearchService.findByNamespaceAndNameAndVersion(libraryMetadataAdapter.getNamespace(), libraryMetadataAdapter.getName(), libraryMetadataAdapter.getVersion())
                .map(Library::getSemanticData)
                .map(AggregateReference::getId)
                .map(editingContextDependencies::contains)
                .orElse(false);
    }

}
