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
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeItemContextMenuEntryProvider;
import org.eclipse.sirius.components.collaborative.trees.dto.ITreeItemContextMenuEntry;
import org.eclipse.sirius.components.collaborative.trees.dto.SingleClickTreeItemContextMenuEntry;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.library.services.LibraryMetadataAdapter;
import org.eclipse.sirius.web.domain.boundedcontexts.library.Library;
import org.eclipse.sirius.web.domain.boundedcontexts.library.services.api.ILibrarySearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticDataDependency;
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

    private final ISemanticDataSearchService semanticDataSearchService;

    private final ILibrarySearchService librarySearchService;

    public ExplorerTreeItemContextMenuEntryProvider(ISemanticDataSearchService semanticDataSearchService, ILibrarySearchService librarySearchService) {
        this.semanticDataSearchService = Objects.requireNonNull(semanticDataSearchService);
        this.librarySearchService = Objects.requireNonNull(librarySearchService);
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
        return result;
    }

    private Optional<ITreeItemContextMenuEntry> getUpdateLibraryEntry(IEditingContext editingContext, TreeItem treeItem) {
        Optional<ITreeItemContextMenuEntry> result = Optional.empty();
        if (editingContext instanceof IEMFEditingContext emfEditingContext) {
            Optional<Resource> optResource = emfEditingContext.getDomain().getResourceSet().getResources().stream()
                    .filter(resource -> resource.getURI().toString().contains(treeItem.getId()))
                    .findFirst();

            if (optResource.isPresent()) {
                Optional<LibraryMetadataAdapter> libraryMetadata = optResource.get().eAdapters().stream()
                    .filter(LibraryMetadataAdapter.class::isInstance)
                    .map(LibraryMetadataAdapter.class::cast)
                    .findFirst();

                if (libraryMetadata.isPresent()) {
                    if (this.isDirectDependency(libraryMetadata.get(), emfEditingContext)) {
                        // We do not support the update of a transitive dependency for the moment.
                        result = Optional.of(new SingleClickTreeItemContextMenuEntry("updateLibrary", "", List.of()));
                    }
                }
            }
        }
        return result;
    }

    private boolean isDirectDependency(LibraryMetadataAdapter libraryMetadata, IEMFEditingContext emfEditingContext) {
        List<AggregateReference<SemanticData, UUID>> editingContextDependencies = new UUIDParser().parse(emfEditingContext.getId())
            .flatMap(this.semanticDataSearchService::findById)
            .map(SemanticData::getDependencies)
            .orElseGet(() -> List.of())
            .stream()
            .map(SemanticDataDependency::dependencySemanticDataId)
            .toList();
        return this.librarySearchService.findByNamespaceAndNameAndVersion(libraryMetadata.getNamespace(), libraryMetadata.getName(), libraryMetadata.getVersion())
            .map(Library::getSemanticData)
            .filter(editingContextDependencies::contains)
            .isPresent();

    }

}
