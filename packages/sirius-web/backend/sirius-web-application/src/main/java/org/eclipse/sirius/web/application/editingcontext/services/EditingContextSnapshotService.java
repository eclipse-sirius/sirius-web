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
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextSnapshot;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextSnapshotService;
import org.eclipse.sirius.web.application.editingcontext.services.api.IResourceLoader;
import org.eclipse.sirius.web.application.editingcontext.services.api.IResourceToDocumentService;
import org.eclipse.sirius.web.application.library.services.LibraryMetadataAdapter;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticDataDependency;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataUpdateService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Creates and restores snapshots of editing contexts.
 *
 * @author gdaniel
 */
@Service
public class EditingContextSnapshotService implements IEditingContextSnapshotService {

    private final IResourceToDocumentService resourceToDocumentService;

    private final IResourceLoader resourceLoader;

    private ISemanticDataSearchService semanticDataSearchService;

    private ISemanticDataUpdateService semanticDataUpdateService;

    public EditingContextSnapshotService(IResourceToDocumentService resourceToDocumentService, IResourceLoader resourceLoader, ISemanticDataSearchService semanticDataSearchService, ISemanticDataUpdateService semanticDataUpdateService) {
        this.resourceToDocumentService = Objects.requireNonNull(resourceToDocumentService);
        this.resourceLoader = Objects.requireNonNull(resourceLoader);
        this.semanticDataSearchService = Objects.requireNonNull(semanticDataSearchService);
        this.semanticDataUpdateService = Objects.requireNonNull(semanticDataUpdateService);
    }

    @Override
    public Optional<IEditingContextSnapshot> createSnapshot(IEditingContext editingContext) {
        Optional<IEditingContextSnapshot> result = Optional.empty();
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            ResourceSet resourceSet = siriusWebEditingContext.getDomain().getResourceSet();

            var libraryAdapterResourcesMap = resourceSet.getResources().stream()
                    .filter(resource -> resource.eAdapters().stream()
                            .anyMatch(LibraryMetadataAdapter.class::isInstance))
                    .collect(Collectors.toMap(
                            Resource::getURI,
                            resource -> resource.eAdapters().stream()
                                .filter(LibraryMetadataAdapter.class::isInstance)
                                .findFirst()
                                .orElseThrow(() -> new IllegalStateException("LibraryMetadataAdapter not found"))
                    ));

            List<DocumentData> snapshotDocuments = resourceSet.getResources().stream()
                    .map(resource -> this.resourceToDocumentService.toDocument(resource, false))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();

            List<AggregateReference<SemanticData, UUID>> initialDependencies = new UUIDParser().parse(editingContext.getId())
                    .flatMap(this.semanticDataSearchService::findById)
                    .map(SemanticData::getDependencies)
                    .orElse(List.of())
                    .stream()
                    .map(SemanticDataDependency::dependencySemanticDataId)
                    .toList();

            result = Optional.of(new EditingContextSnapshot(snapshotDocuments, libraryAdapterResourcesMap, initialDependencies));
        }
        return result;
    }

    @Override
    public void restoreSnapshot(IEditingContext editingContext, IEditingContextSnapshot snapshot, ICause cause) {
        if (editingContext instanceof EditingContext siriusWebEditingContext && snapshot instanceof EditingContextSnapshot siriusWebSnapshot) {
            List<AggregateReference<SemanticData, UUID>> dependencies = new UUIDParser().parse(editingContext.getId())
                    .flatMap(this.semanticDataSearchService::findById)
                    .map(SemanticData::getDependencies)
                    .orElse(List.of())
                    .stream()
                    .map(SemanticDataDependency::dependencySemanticDataId)
                    .toList();
            this.semanticDataUpdateService.removeDependencies(cause, AggregateReference.to(UUID.fromString(editingContext.getId())), dependencies);
            this.semanticDataUpdateService.addDependencies(cause, AggregateReference.to(UUID.fromString(editingContext.getId())), siriusWebSnapshot.dependencies());

            ResourceSet resourceSet = siriusWebEditingContext.getDomain().getResourceSet();
            resourceSet.getResources().clear();

            for (var documentSnapshot : siriusWebSnapshot.documents()) {
                var optionalResource = this.resourceLoader.toResource(resourceSet, documentSnapshot.document().getId().toString(), documentSnapshot.document().getName(), documentSnapshot.document().getContent(), false);
                optionalResource.filter(resource -> siriusWebSnapshot.libraryAdapterResourcesMap().containsKey(resource.getURI()))
                    .ifPresent(resource -> resource.eAdapters().add(siriusWebSnapshot.libraryAdapterResourcesMap().get(resource.getURI())));
            }
        }
    }

}
