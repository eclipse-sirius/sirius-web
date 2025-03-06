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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.web.application.editingcontext.services.DocumentData;
import org.eclipse.sirius.web.application.editingcontext.services.EPackageEntry;
import org.eclipse.sirius.web.application.editingcontext.services.api.IResourceToDocumentService;
import org.eclipse.sirius.web.application.library.dto.PublishLibrariesInput;
import org.eclipse.sirius.web.application.library.services.LibraryMetadataAdapter;
import org.eclipse.sirius.web.application.studio.services.library.api.DependencyGraph;
import org.eclipse.sirius.web.application.studio.services.library.api.IStudioLibrarySemanticDataCreationService;
import org.eclipse.sirius.web.domain.boundedcontexts.library.services.api.ILibrarySearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataCreationService;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.services.Success;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Creates the semantic data containing the studio libraries of a given dependency graph.
 *
 * @author gdaniel
 */
@Service
public class StudioLibrarySemanticDataCreationService implements IStudioLibrarySemanticDataCreationService {

    private final IIdentityService identityService;

    private final IResourceToDocumentService resourceToDocumentService;

    private final ISemanticDataCreationService semanticDataCreationService;

    private final ILibrarySearchService librarySearchService;

    public StudioLibrarySemanticDataCreationService(IIdentityService identityService, IResourceToDocumentService resourceToDocumentService, ISemanticDataCreationService semanticDataCreationService, ILibrarySearchService librarySearchService) {
        this.identityService = Objects.requireNonNull(identityService);
        this.resourceToDocumentService = Objects.requireNonNull(resourceToDocumentService);
        this.semanticDataCreationService = Objects.requireNonNull(semanticDataCreationService);
        this.librarySearchService = Objects.requireNonNull(librarySearchService);

    }

    @Override
    public Collection<SemanticData> createSemanticData(PublishLibrariesInput input, DependencyGraph<EObject> dependencyGraph, ResourceSet resourceSet) {
        // Compute the topological ordering to ensure that all the dependencies of a library have been created before it.
        List<EObject> libraryCandidates = dependencyGraph.computeTopologicalOrdering();
        for (EObject libraryCandidate : libraryCandidates) {
            if (!libraryCandidate.eResource().eAdapters().stream().anyMatch(LibraryMetadataAdapter.class::isInstance)) {
                if (libraryCandidate instanceof Domain || libraryCandidate instanceof RepresentationDescription) {
                    this.getUniqueLibraryName(libraryCandidate, libraryCandidates).ifPresent(libraryName -> {
                        // Use the identifier of the top-level element as a stable resource identifier.
                        String resourceId = this.identityService.getId(libraryCandidate);
                        Resource libraryResource = this.getOrCreateLibraryResource(libraryName, resourceId, resourceSet);
                        libraryResource.getContents().add(libraryCandidate);
                    });
                } else {
                    // The shared component contains all its elements in its root, and its content may vary between version,
                    // it is more stable to use a fixed name as the resource identifier.
                    String resourceId = input.projectId() + ":" + "shared_components";
                    Resource sharedComponentsResource = this.getOrCreateLibraryResource("shared_components", resourceId, resourceSet);
                    sharedComponentsResource.getContents().add(libraryCandidate);
                }
            }
        }

        Map<String, SemanticData> createdSemanticData = new HashMap<>();
        for (EObject libraryCandidate : libraryCandidates) {
            String libraryName = this.getResourceName(libraryCandidate.eResource());
            if (!createdSemanticData.containsKey(libraryName)
                    && !libraryCandidate.eResource().eAdapters().stream().anyMatch(LibraryMetadataAdapter.class::isInstance)) {
                List<AggregateReference<SemanticData, UUID>> dependencies = this.getDependencies(dependencyGraph, libraryCandidate, createdSemanticData);
                ICause cause = new StudioLibrarySemanticDataCreationRequested(UUID.randomUUID(), input, libraryName);
                Optional<SemanticData> optionalSemanticData = this.createSemanticData(cause, libraryCandidate.eResource(), dependencies);
                if (optionalSemanticData.isPresent()) {
                    var semanticData = optionalSemanticData.get();
                    createdSemanticData.put(libraryName, semanticData);
                }
            }
        }
        return createdSemanticData.values();
    }

    private Optional<String> getUniqueLibraryName(EObject eObject, List<EObject> libraryCandidates) {
        Optional<String> optionalLibraryName = this.getLibraryName(eObject);
        if (optionalLibraryName.isPresent()) {
            String libraryName = optionalLibraryName.get();
            List<EObject> librariesWithSameName = libraryCandidates.stream()
                    .filter(libraryCandidate -> this.getLibraryName(libraryCandidate).map(name -> Objects.equals(name, libraryName)).orElse(false))
                    .toList();
            if (librariesWithSameName.size() > 1) {
                optionalLibraryName = Optional.of(libraryName + librariesWithSameName.indexOf(eObject));
            }
        }
        return optionalLibraryName;
    }

    private Optional<String> getLibraryName(EObject eObject) {
        Optional<String> result = Optional.empty();
        if (eObject instanceof RepresentationDescription representationDescription) {
            result = Optional.ofNullable(representationDescription.getName());
        } else if (eObject instanceof Domain domain) {
            result = Optional.ofNullable(domain.getName());
        }
        return result;
    }

    private Resource getOrCreateLibraryResource(String libraryName, String resourceId, ResourceSet resourceSet) {
        URI resourceURI = new JSONResourceFactory().createResourceURI(UUID.nameUUIDFromBytes(resourceId.getBytes()).toString());
        Resource resource = resourceSet.getResource(resourceURI, false);
        if (resource == null) {
            resource = resourceSet.createResource(resourceURI);
            resource.eAdapters().add(new ResourceMetadataAdapter(libraryName));
        }
        return resource;
    }

    private Optional<SemanticData> createSemanticData(ICause event, Resource resource, List<AggregateReference<SemanticData, UUID>> dependencies) {
        Optional<SemanticData> result = Optional.empty();
        Optional<DocumentData> optionalDocumentData = this.resourceToDocumentService.toDocument(resource, false);
        if (optionalDocumentData.isPresent()) {
            DocumentData documentData = optionalDocumentData.get();
            var documents = documentData.ePackageEntries().stream()
                    .map(EPackageEntry::nsURI)
                    .toList();

            IResult<SemanticData> semanticData = this.semanticDataCreationService.create(event, List.of(documentData.document()), documents, dependencies);
            if (semanticData instanceof Success<SemanticData> success) {
                result = Optional.ofNullable(success.data());
            }
        }
        return result;
    }

    private String getResourceName(Resource resource) {
        return resource.eAdapters().stream()
                .filter(ResourceMetadataAdapter.class::isInstance)
                .map(ResourceMetadataAdapter.class::cast)
                .map(ResourceMetadataAdapter::getName)
                .findFirst()
                .orElse(null);
    }

    private List<AggregateReference<SemanticData, UUID>> getDependencies(DependencyGraph<EObject> dependencyGraph, EObject libraryCandidate, Map<String, SemanticData> createdSemanticData) {
        List<AggregateReference<SemanticData, UUID>> dependencies = new ArrayList<>();
        for (EObject dependencyCandidate : dependencyGraph.getDependencies(libraryCandidate)) {
            Optional<LibraryMetadataAdapter> optLibraryMetadata = dependencyCandidate.eResource().eAdapters().stream()
                .filter(LibraryMetadataAdapter.class::isInstance)
                .map(LibraryMetadataAdapter.class::cast)
                .findFirst();
            if (optLibraryMetadata.isPresent()) {
                LibraryMetadataAdapter libraryMetadata = optLibraryMetadata.get();
                this.librarySearchService.findByNamespaceAndNameAndVersion(libraryMetadata.getNamespace(), libraryMetadata.getName(), libraryMetadata.getVersion())
                        .ifPresent(library -> dependencies.add(library.getSemanticData()));
            } else {
                SemanticData existingSemanticData = createdSemanticData.get(this.getResourceName(dependencyCandidate.eResource()));
                if (existingSemanticData != null) {
                    dependencies.add(AggregateReference.to(existingSemanticData.getId()));
                }
            }
        }
        return dependencies.stream()
                .distinct()
                .toList();
    }

}
