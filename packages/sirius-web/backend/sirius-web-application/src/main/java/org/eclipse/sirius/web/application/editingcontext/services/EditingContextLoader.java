/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.components.core.api.IEditingContextRepresentationDescriptionProvider;
import org.eclipse.sirius.components.emf.services.EditingContextCrossReferenceAdapter;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextDependencyLoader;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextLoader;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextMigrationParticipantPredicate;
import org.eclipse.sirius.web.application.editingcontext.services.api.IResourceLoader;
import org.eclipse.sirius.web.application.library.services.LibraryMetadataAdapter;
import org.eclipse.sirius.web.domain.boundedcontexts.library.services.api.ILibrarySearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Used to load an editing context.
 *
 * @author frouene
 */
@Service
public class EditingContextLoader implements IEditingContextLoader {

    private final Logger logger = LoggerFactory.getLogger(EditingContextLoader.class);

    private final IResourceLoader resourceLoader;

    private final IEditingContextDependencyLoader editingContextDependencyLoader;

    private final List<IEditingContextRepresentationDescriptionProvider> representationDescriptionProviders;

    private final List<IEditingContextProcessor> editingContextProcessors;

    private final List<IEditingContextMigrationParticipantPredicate> migrationParticipantPredicates;

    private final ILibrarySearchService librarySearchService;

    public EditingContextLoader(IResourceLoader resourceLoader, IEditingContextDependencyLoader editingContextDependencyLoader, List<IEditingContextRepresentationDescriptionProvider> representationDescriptionProviders, List<IEditingContextProcessor> editingContextProcessors, List<IEditingContextMigrationParticipantPredicate> migrationParticipantPredicates, ILibrarySearchService librarySearchService) {
        this.resourceLoader = Objects.requireNonNull(resourceLoader);
        this.editingContextDependencyLoader = Objects.requireNonNull(editingContextDependencyLoader);
        this.representationDescriptionProviders = Objects.requireNonNull(representationDescriptionProviders);
        this.editingContextProcessors = Objects.requireNonNull(editingContextProcessors);
        this.migrationParticipantPredicates = Objects.requireNonNull(migrationParticipantPredicates);
        this.librarySearchService = Objects.requireNonNull(librarySearchService);
    }

    @Override
    public void load(EditingContext editingContext, SemanticData semanticData) {
        this.editingContextProcessors.forEach(processor -> processor.preProcess(editingContext));

        this.editingContextDependencyLoader.loadDependencies(editingContext);
        this.loadSemanticData(editingContext, semanticData);

        this.representationDescriptionProviders.forEach(representationDescriptionProvider -> {
            var representationDescriptions = representationDescriptionProvider.getRepresentationDescriptions(editingContext);
            representationDescriptions.forEach(representationDescription -> editingContext.getRepresentationDescriptions().put(representationDescription.getId(), representationDescription));
        });

        this.editingContextProcessors.forEach(processor -> processor.postProcess(editingContext));
    }

    private void loadSemanticData(EditingContext editingContext, SemanticData semanticData) {
        ResourceSet resourceSet = editingContext.getDomain().getResourceSet();
        resourceSet.getLoadOptions().put(JsonResource.OPTION_SCHEMA_LOCATION, true);

        var optionalLibrary = this.librarySearchService.findBySemanticData(AggregateReference.to(semanticData.getId()));

        var applyMigrationParticipant = this.migrationParticipantPredicates.stream().anyMatch(predicate -> predicate.test(editingContext.getId()));
        semanticData.getDocuments().forEach(document -> {
            this.resourceLoader.toResource(resourceSet, document.getId().toString(), document.getName(), document.getContent(), applyMigrationParticipant).ifPresent(resource -> {
                if (optionalLibrary.isPresent()) {
                    var library = optionalLibrary.get();
                    resource.eAdapters().add(new LibraryMetadataAdapter(library.getNamespace(), library.getName(), library.getVersion()));
                }
            });
        });

        // The ECrossReferenceAdapter must be set after the resource loading because it needs to resolve proxies in case
        // of inter-resources references
        resourceSet.eAdapters().add(new EditingContextCrossReferenceAdapter());

        this.logger.debug("{} documents loaded for the editing context {}", resourceSet.getResources().size(), editingContext.getId());
    }

}
