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
package org.eclipse.sirius.web.application.library.services;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextMigrationParticipantPredicate;
import org.eclipse.sirius.web.application.editingcontext.services.api.IResourceLoader;
import org.eclipse.sirius.web.application.library.services.api.IEditingContextLibraryLoader;
import org.eclipse.sirius.web.domain.boundedcontexts.library.Library;
import org.eclipse.sirius.web.domain.boundedcontexts.library.services.api.ILibrarySearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticDataDependency;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataSearchService;
import org.springframework.stereotype.Service;

/**
 * Loads libraries into the editing context.
 *
 * @author gdaniel
 */
@Service
public class EditingContextLibraryLoader implements IEditingContextLibraryLoader {

    private final ISemanticDataSearchService semanticDataSearchService;

    private final ILibrarySearchService librarySearchService;

    private final IResourceLoader resourceLoader;

    private final List<IEditingContextMigrationParticipantPredicate> migrationParticipantPredicates;

    public EditingContextLibraryLoader(ISemanticDataSearchService semanticDataSearchService, ILibrarySearchService librarySearchService, IResourceLoader resourceLoader, List<IEditingContextMigrationParticipantPredicate> migrationParticipantPredicates) {
        this.semanticDataSearchService = Objects.requireNonNull(semanticDataSearchService);
        this.librarySearchService = Objects.requireNonNull(librarySearchService);
        this.resourceLoader = Objects.requireNonNull(resourceLoader);
        this.migrationParticipantPredicates = Objects.requireNonNull(migrationParticipantPredicates);
    }

    @Override
    public void loadLibraries(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            Set<Library> librariesToLoad = this.getLibrariesToLoad(siriusWebEditingContext);
            for (Library library : librariesToLoad) {
                Optional<SemanticData> librarySemanticData = this.semanticDataSearchService.findById(library.getSemanticData().getId());
                if (librarySemanticData.isPresent()) {
                    librarySemanticData.get().getDocuments().forEach(document -> {
                        URI libraryResourceURI = URI.createURI(LIBRARY_SCHEME + ":///" + document.getId().toString());
                        if (siriusWebEditingContext.getDomain().getResourceSet().getResource(libraryResourceURI, false) == null) {
                            Optional<Resource> resource = this.resourceLoader.toResource(siriusWebEditingContext.getDomain().getResourceSet(), document.getId().toString(), document.getName(), document.getContent(),
                                    this.migrationParticipantPredicates.stream().anyMatch(predicate -> predicate.test(editingContext.getId())));
                            resource.ifPresent(r -> {
                                siriusWebEditingContext.getDomain().getResourceSet().getURIConverter().getURIMap().put(r.getURI(), libraryResourceURI);
                                r.setURI(libraryResourceURI);
                            });
                        }
                    });
                }
            }
        }
    }

    private Set<Library> getLibrariesToLoad(EditingContext editingContext) {
        Set<Library> librariesToLoad = new LinkedHashSet<>();
        List<Library> editingContextLibraries = new UUIDParser().parse(editingContext.getId())
                .flatMap(semanticDataId -> this.semanticDataSearchService.findById(semanticDataId))
                .map(SemanticData::getDependencies)
                .orElse(List.of())
                .stream()
                .map(SemanticDataDependency::libraryId)
                .map(this.librarySearchService::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        for (Library library : editingContextLibraries) {
            librariesToLoad.add(library);
            librariesToLoad.addAll(this.librarySearchService.findAllTransitiveDependencyLibrariesByLibrary(library));
        }
        return librariesToLoad;
    }
}
