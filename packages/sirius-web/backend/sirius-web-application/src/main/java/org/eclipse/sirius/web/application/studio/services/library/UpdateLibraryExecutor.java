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
package org.eclipse.sirius.web.application.studio.services.library;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextDependencyLoader;
import org.eclipse.sirius.web.application.library.services.LibraryMetadataAdapter;
import org.eclipse.sirius.web.application.studio.services.library.api.IUpdateLibraryExecutor;
import org.eclipse.sirius.web.domain.boundedcontexts.library.Library;
import org.eclipse.sirius.web.domain.boundedcontexts.library.services.api.ILibrarySearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticDataDependency;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataUpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Used to execute a library update.
 *
 * @author gdaniel
 */
@Service
public class UpdateLibraryExecutor implements IUpdateLibraryExecutor {

    private final Logger logger = LoggerFactory.getLogger(UpdateLibraryExecutor.class);

    private final ILibrarySearchService librarySearchService;

    private final ISemanticDataSearchService semanticDataSearchService;

    private final ISemanticDataUpdateService semanticDataUpdateService;

    private final IEditingContextDependencyLoader editingContextDependencyLoader;

    public UpdateLibraryExecutor(ILibrarySearchService librarySearchService, ISemanticDataSearchService semanticDataSearchService, ISemanticDataUpdateService semanticDataUpdateService, IEditingContextDependencyLoader editingContextDependencyLoader) {
        this.librarySearchService = Objects.requireNonNull(librarySearchService);
        this.semanticDataSearchService = Objects.requireNonNull(semanticDataSearchService);
        this.semanticDataUpdateService = Objects.requireNonNull(semanticDataUpdateService);
        this.editingContextDependencyLoader = Objects.requireNonNull(editingContextDependencyLoader);
    }

    @Override
    public IStatus updateLibrary(IEditingContext editingContext, UUID libraryId, ICause cause) {
        IStatus result = new Failure(List.of());
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            Optional<Library> optionalNewLibrary = this.librarySearchService.findById(libraryId);
            if (optionalNewLibrary.isPresent()) {
                Library newLibrary = optionalNewLibrary.get();

                // Find the version of the library already in the dependencies.
                Optional<Library> optionalOldLibrary = this.getLibraryDependencyWithDifferentVersion(siriusWebEditingContext, newLibrary);
                if (optionalOldLibrary.isPresent()) {
                    Library oldLibrary = optionalOldLibrary.get();
                    List<Resource> oldLibraryResources = this.getAllResourcesFromLibraryRecursiveDependencies(siriusWebEditingContext, oldLibrary);

                    for (Resource oldLibraryResource : oldLibraryResources) {
                        oldLibraryResource.unload();
                        siriusWebEditingContext.getDomain().getResourceSet().getResources().remove(oldLibraryResource);
                    }

                    this.addLibraryDependency(cause, siriusWebEditingContext, newLibrary);
                    this.removeLibraryDependency(cause, siriusWebEditingContext, oldLibrary);

                    this.editingContextDependencyLoader.loadDependencies(siriusWebEditingContext);

                    result = new Success(List.of(new Message("Library " + oldLibrary.getName() + " updated to version " + newLibrary.getVersion(), MessageLevel.SUCCESS)));
                } else {
                    result = new Failure(List.of(new Message("Cannot update Library " + newLibrary.getName() + ": the library is not a direct dependency", MessageLevel.ERROR)));
                }
            }
        }
        return result;
    }

    private Optional<Library> getLibraryDependencyWithDifferentVersion(EditingContext editingContext, Library library) {
        return new UUIDParser().parse(editingContext.getId())
                .flatMap(this.semanticDataSearchService::findById)
                .map(SemanticData::getDependencies)
                .orElse(List.of())
                .stream()
                .map(SemanticDataDependency::dependencySemanticDataId)
                .map(this.librarySearchService::findBySemanticData)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(libraryDependency -> Objects.equals(libraryDependency.getNamespace(), library.getNamespace())
                        && Objects.equals(libraryDependency.getName(), library.getName())
                        && !Objects.equals(libraryDependency.getVersion(), library.getVersion()))
                .findFirst();
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

    private void addLibraryDependency(ICause cause, EditingContext editingContext, Library library) {
        Optional<SemanticData> optionalSemanticData = new UUIDParser().parse(editingContext.getId())
                .flatMap(this.semanticDataSearchService::findById);
        if (optionalSemanticData.isPresent()) {
            if (optionalSemanticData.get().getDependencies().stream().anyMatch(dependency -> dependency.dependencySemanticDataId().equals(library.getSemanticData()))) {
                this.logger.warn("Cannot add the dependency to library " + library.getNamespace() + ":" + library.getName() + ":" + library.getVersion() + ": the dependency already exists");
            } else {
                this.semanticDataUpdateService.addDependencies(cause, AggregateReference.to(optionalSemanticData.get().getId()), List.of(library.getSemanticData()));
            }
        }
    }

    private void removeLibraryDependency(ICause cause, EditingContext editingContext, Library library) {
        new UUIDParser().parse(editingContext.getId())
            .flatMap(this.semanticDataSearchService::findById)
            .ifPresent(semanticData -> {
                this.semanticDataUpdateService.removeDependencies(cause, AggregateReference.to(semanticData.getId()), List.of(library.getSemanticData()));
            });
    }
}
