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
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.web.application.editingcontext.services.DocumentData;
import org.eclipse.sirius.web.application.editingcontext.services.EPackageEntry;
import org.eclipse.sirius.web.application.editingcontext.services.api.IResourceToDocumentService;
import org.eclipse.sirius.web.application.library.dto.PublishLibrariesInput;
import org.eclipse.sirius.web.application.library.services.LibraryMetadataAdapter;
import org.eclipse.sirius.web.application.library.services.api.ILibraryPublicationHandler;
import org.eclipse.sirius.web.application.studio.services.library.api.DependencyGraph;
import org.eclipse.sirius.web.application.studio.services.library.api.IStudioLibraryDependencyCollector;
import org.eclipse.sirius.web.domain.boundedcontexts.library.services.api.ILibrarySearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataCreationService;
import org.eclipse.sirius.web.domain.services.IResult;
import org.eclipse.sirius.web.domain.services.Success;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

/**
 * Handles the publication of libraries from studios.
 *
 * @author gdaniel
 */
@Service
public class StudioLibraryPublicationHandler implements ILibraryPublicationHandler {

    private final IEditingContextSearchService editingContextSearchService;

    private final ISemanticDataCreationService semanticDataCreationService;

    private final IResourceToDocumentService resourceToDocumentService;

    private final IStudioLibraryDependencyCollector studioLibraryDependencyCollector;

    private final IProjectSemanticDataSearchService projectSemanticDataSearchService;

    private final ILibrarySearchService librarySearchService;

    private final IMessageService messageService;

    public StudioLibraryPublicationHandler(IEditingContextSearchService editingContextSearchService, ISemanticDataCreationService semanticDataCreationService, IResourceToDocumentService resourceToDocumentService, IStudioLibraryDependencyCollector studioLibraryDependencyCollector, IProjectSemanticDataSearchService projectSemanticDataSearchService, ILibrarySearchService librarySearchService, IMessageService messageService) {
        this.editingContextSearchService = Objects.requireNonNull(editingContextSearchService);
        this.semanticDataCreationService = Objects.requireNonNull(semanticDataCreationService);
        this.resourceToDocumentService = Objects.requireNonNull(resourceToDocumentService);
        this.studioLibraryDependencyCollector = Objects.requireNonNull(studioLibraryDependencyCollector);
        this.projectSemanticDataSearchService = Objects.requireNonNull(projectSemanticDataSearchService);
        this.librarySearchService = Objects.requireNonNull(librarySearchService);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public boolean canHandle(PublishLibrariesInput input) {
        return Objects.equals(input.publicationKind(), "studio-all");
    }

    @Override
    public IPayload handle(PublishLibrariesInput input) {
        IPayload result = new ErrorPayload(input.id(), this.messageService.unexpectedError());

        Optional<IEMFEditingContext> optionalEditingContext = this.projectSemanticDataSearchService.findByProjectId(AggregateReference.to(input.projectId()))
                .flatMap(projectSemanticData -> this.editingContextSearchService.findById(projectSemanticData.getSemanticData().getId().toString()))
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast);

        if (optionalEditingContext.isPresent()) {
            var editingContext = optionalEditingContext.get();
            ResourceSet resourceSet = editingContext.getDomain().getResourceSet();
            resourceSet.getResourceFactoryRegistry().getProtocolToFactoryMap().put(IEMFEditingContext.RESOURCE_SCHEME, new JSONResourceFactory());

            DependencyGraph<EObject> dependencyGraph = this.studioLibraryDependencyCollector.collectDependencies(resourceSet);

            // Compute the topological ordering to ensure that all the dependencies of a library have been created before it.
            List<EObject> libraryCandidates = dependencyGraph.computeTopologicalOrdering();

            for (EObject libraryCandidate : libraryCandidates) {
                if (!this.isLibrary(libraryCandidate.eResource())) {
                    if (libraryCandidate instanceof Domain || libraryCandidate instanceof RepresentationDescription) {
                        this.getUniqueLibraryName(libraryCandidate, libraryCandidates).ifPresent(libraryName -> {
                            Resource libraryResource = this.getOrCreateLibraryResource(input.projectId(), libraryName, input.version(), resourceSet);
                            libraryResource.getContents().add(libraryCandidate);
                        });
                    } else {
                        Resource sharedComponentsResource = this.getOrCreateLibraryResource(input.projectId(), "shared_components", input.version(), resourceSet);
                        sharedComponentsResource.getContents().add(libraryCandidate);
                    }
                }
            }

            Map<String, SemanticData> createdSemanticData = new HashMap<>();
            for (EObject libraryCandidate : libraryCandidates) {
                String libraryName = this.getResourceName(libraryCandidate.eResource());
                if (!createdSemanticData.containsKey(libraryName)
                        && !this.isLibrary(libraryCandidate.eResource())) {
                    List<AggregateReference<SemanticData, UUID>> dependencies = this.getDependencies(dependencyGraph, libraryCandidate, createdSemanticData);
                    ICause cause = new StudioLibrarySemanticDataCreationRequested(UUID.randomUUID(), input, libraryName);
                    Optional<SemanticData> optionalSemanticData = this.createSemanticData(cause, libraryCandidate.eResource(), dependencies);
                    if (optionalSemanticData.isPresent()) {
                        var semanticData = optionalSemanticData.get();
                        createdSemanticData.put(libraryName, semanticData);
                    }
                }
            }

            result = new SuccessPayload(input.id(), List.of(new Message(createdSemanticData.keySet().size() + " libraries published", MessageLevel.SUCCESS)));
        }
        return result;
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

    private String getResourceName(Resource resource) {
        return resource.eAdapters().stream()
                .filter(ResourceMetadataAdapter.class::isInstance)
                .map(ResourceMetadataAdapter.class::cast)
                .map(ResourceMetadataAdapter::getName)
                .findFirst()
                .orElse(null);
    }

    private boolean isLibrary(Resource resource) {
        return resource.eAdapters().stream().anyMatch(LibraryMetadataAdapter.class::isInstance);
    }

    private Resource getOrCreateLibraryResource(String projectId, String name, String version, ResourceSet resourceSet) {
        String resourceId = projectId + ":" + name + ":" + version;
        URI resourceURI = new JSONResourceFactory().createResourceURI(UUID.nameUUIDFromBytes(resourceId.getBytes()).toString());
        Resource resource = resourceSet.getResource(resourceURI, false);
        if (resource == null) {
            resource = resourceSet.createResource(resourceURI);
            resource.eAdapters().add(new ResourceMetadataAdapter(name));
        }
        return resource;
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
