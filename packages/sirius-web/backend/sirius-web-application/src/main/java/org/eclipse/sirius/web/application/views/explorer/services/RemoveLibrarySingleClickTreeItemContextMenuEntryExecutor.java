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

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.trees.api.ISingleClickTreeItemContextMenuEntryExecutor;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeInput;
import org.eclipse.sirius.components.collaborative.trees.dto.InvokeSingleClickTreeItemContextMenuEntryInput;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.editingcontext.services.api.IProxyRemovalService;
import org.eclipse.sirius.web.application.library.services.LibraryMetadataAdapter;
import org.eclipse.sirius.web.domain.boundedcontexts.library.Library;
import org.eclipse.sirius.web.domain.boundedcontexts.library.services.api.ILibrarySearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataUpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Executes the "Remove library" contextual menu action.
 *
 * @author gdaniel
 */
@Service
public class RemoveLibrarySingleClickTreeItemContextMenuEntryExecutor implements ISingleClickTreeItemContextMenuEntryExecutor {

    private final Logger logger = LoggerFactory.getLogger(RemoveLibrarySingleClickTreeItemContextMenuEntryExecutor.class);

    private final IObjectSearchService objectSearchService;

    private final ILibrarySearchService librarySearchService;

    private final ISemanticDataSearchService semanticDataSearchService;

    private final ISemanticDataUpdateService semanticDataUpdateService;

    private final IProxyRemovalService proxyRemovalService;

    public RemoveLibrarySingleClickTreeItemContextMenuEntryExecutor(IObjectSearchService objectSearchService, ILibrarySearchService librarySearchService,
            ISemanticDataSearchService semanticDataSearchService, ISemanticDataUpdateService semanticDataUpdateService, IProxyRemovalService proxyRemovalService) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.librarySearchService = Objects.requireNonNull(librarySearchService);
        this.semanticDataSearchService = Objects.requireNonNull(semanticDataSearchService);
        this.semanticDataUpdateService = Objects.requireNonNull(semanticDataUpdateService);
        this.proxyRemovalService = Objects.requireNonNull(proxyRemovalService);
    }

    @Override
    public boolean canExecute(TreeDescription treeDescription) {
        return Objects.equals(treeDescription.getId(), ExplorerDescriptionProvider.DESCRIPTION_ID);
    }

    @Override
    public IStatus execute(IEditingContext editingContext, TreeDescription treeDescription, Tree tree, TreeItem treeItem, String treeItemMenuContextEntryId, ITreeInput treeInput) {
        IStatus result = new Failure("Something went wrong while handling the action");
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            Optional<Library> optionalLibrary = this.getLibrary(editingContext, treeItem.getId());
            if (optionalLibrary.isPresent()) {
                Library library = optionalLibrary.get();
                List<Resource> libraryResources = this.getAllResourcesFromLibraryRecursiveDependencies(siriusWebEditingContext, library);
                for (Resource libraryResource : libraryResources) {
                    libraryResource.unload();
                    siriusWebEditingContext.getDomain().getResourceSet().getResources().remove(libraryResource);
                }

                if (treeInput instanceof InvokeSingleClickTreeItemContextMenuEntryInput invokeSingleClickTreeItemContextMenuEntryInput
                        && Objects.equals(invokeSingleClickTreeItemContextMenuEntryInput.menuEntryId(), ExplorerTreeItemContextMenuEntryProvider.REMOVE_LIBRARY)) {
                    // Do not update the semantic data bounded context if we are not performing an actual library
                    // removal (this is for example the case when performing an impact analysis).
                    this.semanticDataUpdateService.removeDependencies(treeInput, AggregateReference.to(UUID.fromString(editingContext.getId())), List.of(optionalLibrary.get().getSemanticData()));
                }

                long start = System.nanoTime();
                this.proxyRemovalService.removeUnresolvedProxies(siriusWebEditingContext);
                Duration timeToRemoveProxies = Duration.ofNanos(System.nanoTime() - start);
                this.logger.trace("Removed proxies in {}ms", timeToRemoveProxies.toMillis());

                result = new Success(ChangeKind.SEMANTIC_CHANGE, Map.of());
            }
        }
        return result;
    }

    private Optional<Library> getLibrary(IEditingContext editingContext, String objectId) {
        Optional<Library> optionalLibrary = Optional.empty();
        Optional<Resource> optionalResource = this.objectSearchService.getObject(editingContext, objectId)
                .filter(Resource.class::isInstance)
                .map(Resource.class::cast);
        if (optionalResource.isPresent()) {
            Resource resource = optionalResource.get();
            Optional<LibraryMetadataAdapter> optionalLibraryMetadataAdapter = resource.eAdapters().stream()
                    .filter(LibraryMetadataAdapter.class::isInstance)
                    .map(LibraryMetadataAdapter.class::cast)
                    .findFirst();
            if (optionalLibraryMetadataAdapter.isPresent()) {
                optionalLibrary = this.librarySearchService.findByNamespaceAndNameAndVersion(optionalLibraryMetadataAdapter.get().getNamespace(), optionalLibraryMetadataAdapter.get().getName(),
                        optionalLibraryMetadataAdapter.get().getVersion());
            }
        }
        return optionalLibrary;
    }

    private List<Resource> getAllResourcesFromLibraryRecursiveDependencies(EditingContext editingContext, Library library) {
        Set<Library> libraries = new LinkedHashSet<>();
        libraries.add(library);
        this.semanticDataSearchService.findAllDependenciesRecursivelyById(library.getSemanticData().getId()).stream()
                .map(SemanticData::getId)
                .map(AggregateReference::<SemanticData, UUID>to)
                .map(this.librarySearchService::findBySemanticData)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(libraries::add);
        List<Resource> libraryResources = new ArrayList<>();
        for (Resource resource : editingContext.getDomain().getResourceSet().getResources()) {
            resource.eAdapters().stream()
                .filter(LibraryMetadataAdapter.class::isInstance)
                .map(LibraryMetadataAdapter.class::cast)
                .findFirst()
                .ifPresent(libraryMetadata -> {
                    if (libraries.stream().anyMatch(libraryDependency ->
                        Objects.equals(libraryMetadata.getNamespace(), libraryDependency.getNamespace())
                            && Objects.equals(libraryMetadata.getName(), libraryDependency.getName())
                            && Objects.equals(libraryMetadata.getVersion(), libraryDependency.getVersion())
                    )) {
                        libraryResources.add(resource);
                    }
                });
        }
        return libraryResources;
    }
}
