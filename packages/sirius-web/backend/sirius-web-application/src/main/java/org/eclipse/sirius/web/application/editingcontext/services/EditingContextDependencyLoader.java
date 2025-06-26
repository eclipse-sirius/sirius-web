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
package org.eclipse.sirius.web.application.editingcontext.services;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextDependencyLoader;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextMigrationParticipantPredicate;
import org.eclipse.sirius.web.application.editingcontext.services.api.IResourceLoader;
import org.eclipse.sirius.web.application.library.services.LibraryMetadataAdapter;
import org.eclipse.sirius.web.domain.boundedcontexts.library.services.api.ILibrarySearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.Document;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataSearchService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Loads dependencies into the editing context.
 *
 * @author gdaniel
 */
@Service
public class EditingContextDependencyLoader implements IEditingContextDependencyLoader {

    private final IResourceLoader resourceLoader;

    private final List<IEditingContextMigrationParticipantPredicate> migrationParticipantPredicates;

    private final ILibrarySearchService librarySearchService;

    public EditingContextDependencyLoader(ISemanticDataSearchService semanticDataSearchService, IResourceLoader resourceLoader,
            List<IEditingContextMigrationParticipantPredicate> migrationParticipantPredicates, ILibrarySearchService librarySearchService) {
        this.resourceLoader = Objects.requireNonNull(resourceLoader);
        this.migrationParticipantPredicates = Objects.requireNonNull(migrationParticipantPredicates);
        this.librarySearchService = Objects.requireNonNull(librarySearchService);
    }

    @Override
    public void loadDependencies(IEditingContext editingContext, List<SemanticData> dependenciesSemanticData) {
        if (editingContext instanceof IEMFEditingContext emfEditingContext) {
            for (SemanticData semanticData : dependenciesSemanticData) {
                var optionalLibrary = this.librarySearchService.findBySemanticData(AggregateReference.to(semanticData.getId()));
                semanticData.getDocuments().stream()
                        .filter(document -> !this.isAlreadyLoaded(emfEditingContext, document))
                        .forEach(document -> {
                            var applyMigrationParticipants = this.migrationParticipantPredicates.stream().anyMatch(predicate -> predicate.test(editingContext.getId()));
                            this.resourceLoader.toResource(emfEditingContext.getDomain().getResourceSet(), document.getId().toString(), document.getName(), document.getContent(), applyMigrationParticipants).ifPresent(resource -> {
                                if (optionalLibrary.isPresent()) {
                                    var library = optionalLibrary.get();
                                    resource.eAdapters().add(new LibraryMetadataAdapter(library.getNamespace(), library.getName(), library.getVersion()));
                                }
                            });
                        });
            }
        }
    }

    /**
     * Used to ensure that we are not loading twice a resource with the same identity.
     * <p>
     * This could easily happen while trying to load two dependencies that both share a dependency to another one. We
     * don't want to load the common dependency twice.
     * </p>
     *
     * @param editingContext
     *            The editing context
     * @param document
     *            The document being loaded
     * @return true if the document is matching an existing resource of the resource set, false otherwise
     */
    private boolean isAlreadyLoaded(IEMFEditingContext editingContext, Document document) {
        return editingContext.getDomain().getResourceSet().getResources().stream()
                .anyMatch(resource -> resource.getURI().path().substring(1).equals(document.getId().toString()));
    }
}
