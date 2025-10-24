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
package org.eclipse.sirius.web.application.views.explorer.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeItemContextMenuEntryProvider;
import org.eclipse.sirius.components.collaborative.trees.dto.ITreeItemContextMenuEntry;
import org.eclipse.sirius.components.collaborative.trees.dto.SingleClickTreeItemContextMenuEntry;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.library.services.LibraryMetadataAdapter;
import org.eclipse.sirius.web.domain.boundedcontexts.library.Library;
import org.eclipse.sirius.web.domain.boundedcontexts.library.services.api.ILibrarySearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataSearchService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Provides the context menu entries for tree items in the explorer.
 *
 * @author gdaniel
 */
@Service
public class ExplorerTreeItemContextMenuEntryProvider implements ITreeItemContextMenuEntryProvider {

    private final IObjectSearchService objectSearchService;

    private final ILibrarySearchService librarySearchService;

    private final ISemanticDataSearchService semanticDataSearchService;

    public ExplorerTreeItemContextMenuEntryProvider(IObjectSearchService objectSearchService, ILibrarySearchService librarySearchService, ISemanticDataSearchService semanticDataSearchService) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.librarySearchService = Objects.requireNonNull(librarySearchService);
        this.semanticDataSearchService = Objects.requireNonNull(semanticDataSearchService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, TreeDescription treeDescription, Tree tree, TreeItem treeItem) {
        return tree.getId().startsWith(ExplorerDescriptionProvider.PREFIX)
                && Objects.equals(tree.getDescriptionId(), ExplorerDescriptionProvider.DESCRIPTION_ID);
    }

    @Override
    public List<ITreeItemContextMenuEntry> getTreeItemContextMenuEntries(IEditingContext editingContext, TreeDescription treeDescription, Tree tree, TreeItem treeItem) {
        List<ITreeItemContextMenuEntry> result = new ArrayList<>();
        this.getUpdateLibraryEntry(editingContext, treeItem).ifPresent(result::add);
        if (treeItem.isHasChildren()) {
            result.add(new SingleClickTreeItemContextMenuEntry("expandAll", "", List.of(), false));
        }
        return result;
    }

    private Optional<ITreeItemContextMenuEntry> getUpdateLibraryEntry(IEditingContext editingContext, TreeItem treeItem) {
        Optional<ITreeItemContextMenuEntry> result = Optional.empty();
        if (editingContext instanceof IEMFEditingContext emfEditingContext) {
            var optionalNotifier = this.objectSearchService.getObject(editingContext, treeItem.getId())
                    .filter(Notifier.class::isInstance)
                    .map(Notifier.class::cast);

            if (optionalNotifier.isEmpty()) {
                optionalNotifier = emfEditingContext.getDomain().getResourceSet().getResources().stream()
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
                if (this.isDirectDependency(emfEditingContext, libraryMetadataAdapter)) {
                    // We do not support the update of a transitive dependency for the moment.
                    result = Optional.of(new SingleClickTreeItemContextMenuEntry("updateLibrary", "Update the library", List.of(), true));
                }
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
