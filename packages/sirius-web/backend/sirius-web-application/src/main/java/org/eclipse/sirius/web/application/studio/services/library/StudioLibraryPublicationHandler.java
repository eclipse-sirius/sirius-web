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
import org.eclipse.sirius.components.core.api.IEditingContext;
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
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.editingcontext.services.DocumentData;
import org.eclipse.sirius.web.application.editingcontext.services.EPackageEntry;
import org.eclipse.sirius.web.application.editingcontext.services.api.IResourceToDocumentService;
import org.eclipse.sirius.web.application.library.dto.PublishLibrariesInput;
import org.eclipse.sirius.web.application.library.services.DependencyGraph;
import org.eclipse.sirius.web.application.library.services.api.ILibraryPublicationHandler;
import org.eclipse.sirius.web.application.studio.services.library.api.IStudioLibraryDependencyCollector;
import org.eclipse.sirius.web.domain.boundedcontexts.library.Library;
import org.eclipse.sirius.web.domain.boundedcontexts.library.services.api.ILibraryCreationService;
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

    private ISemanticDataCreationService semanticDataCreationService;

    private IResourceToDocumentService resourceToDocumentService;

    private IStudioLibraryDependencyCollector studioLibraryDependencyCollector;

    private ILibraryCreationService libraryCreationService;

    private IProjectSemanticDataSearchService projectSemanticDataSearchService;

    private IMessageService messageService;

    public StudioLibraryPublicationHandler(IEditingContextSearchService editingContextSearchService, ISemanticDataCreationService semanticDataCreationService, IResourceToDocumentService resourceToDocumentService, IStudioLibraryDependencyCollector studioLibraryDependencyCollector, ILibraryCreationService libraryCreationService, IProjectSemanticDataSearchService projectSemanticDataSearchService, IMessageService messageService) {
        this.editingContextSearchService = Objects.requireNonNull(editingContextSearchService);
        this.semanticDataCreationService = Objects.requireNonNull(semanticDataCreationService);
        this.resourceToDocumentService = Objects.requireNonNull(resourceToDocumentService);
        this.studioLibraryDependencyCollector = Objects.requireNonNull(studioLibraryDependencyCollector);
        this.libraryCreationService = Objects.requireNonNull(libraryCreationService);
        this.projectSemanticDataSearchService = Objects.requireNonNull(projectSemanticDataSearchService);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public boolean canHandle(PublishLibrariesInput input) {
        return Objects.equals(input.publicationKind(), "studio-all");
    }

    @Override
    public IPayload handle(PublishLibrariesInput input) {
        IPayload result = new ErrorPayload(input.id(), this.messageService.unexpectedError());
        Optional<IEditingContext> optionalEditingContext = this.projectSemanticDataSearchService.findByProjectId(AggregateReference.to(input.projectId()))
                .flatMap(projectSemanticData -> this.editingContextSearchService.findById(projectSemanticData.getSemanticData().getId().toString()));

        if (optionalEditingContext.isPresent() && optionalEditingContext.get() instanceof EditingContext editingContext) {
            ResourceSet rSet = editingContext.getDomain().getResourceSet();
            rSet.getResourceFactoryRegistry().getProtocolToFactoryMap().put(IEMFEditingContext.RESOURCE_SCHEME, new JSONResourceFactory());

            DependencyGraph<EObject> dependencyGraph = this.studioLibraryDependencyCollector.collectDependencies(rSet);

            // Compute the topological ordering to ensure that all the dependencies of a library have been created before it.
            List<EObject> libraryCandidates = dependencyGraph.computeTopologicalOrdering();

            for (EObject libraryCandidate : libraryCandidates) {
                if (libraryCandidate instanceof Domain || libraryCandidate instanceof RepresentationDescription) {
                    Optional<String> optionalLibraryName = this.getUniqueLibraryName(libraryCandidate, libraryCandidates);
                    if (optionalLibraryName.isPresent()) {
                        Resource libraryResource = this.getOrCreateLibraryResource(input.projectId(), optionalLibraryName.get(), input.version(), rSet);
                        libraryResource.getContents().add(libraryCandidate);
                    }
                } else {
                    Resource sharedComponentsResource = this.getOrCreateLibraryResource(input.projectId(), "shared_components", input.version(), rSet);
                    sharedComponentsResource.getContents().add(libraryCandidate);
                }
            }


            Map<String, Library> createdLibraries = new HashMap<>();
            for (EObject libraryCandidate : libraryCandidates) {
                if (!createdLibraries.containsKey(this.getResourceName(libraryCandidate.eResource()))) {
                    Optional<SemanticData> optionalSemanticData = this.toSemanticData(input, libraryCandidate.eResource());
                    if (optionalSemanticData.isPresent()) {
                        String libraryName = this.getResourceName(libraryCandidate.eResource());
                        Library library = Library.newLibrary()
                                .namespace(input.projectId())
                                .name(libraryName)
                                .semanticData(AggregateReference.to(optionalSemanticData.get().getId()))
                                .dependencies(dependencyGraph.getDependencies(libraryCandidate).stream()
                                        .map(dependency -> createdLibraries.get(this.getResourceName(dependency.eResource())).getId())
                                        .distinct()
                                        .map(AggregateReference::<Library, UUID>to)
                                        .toList())
                                .version(input.version())
                                .description(input.description())
                                .build(input);
                        this.libraryCreationService.createLibrary(library);
                        createdLibraries.put(libraryName, library);
                    }
                }
            }

            result = new SuccessPayload(input.id(), List.of(new Message(createdLibraries.keySet().size() + " libraries published", MessageLevel.SUCCESS)));
        }
        return result;
    }


    private Optional<SemanticData> toSemanticData(ICause event, Resource resource) {
        Optional<SemanticData> result = Optional.empty();
        Optional<DocumentData> optionalDocumentData = this.resourceToDocumentService.toDocument(resource, false);
        if (optionalDocumentData.isPresent()) {
            DocumentData documentData = optionalDocumentData.get();
            IResult<SemanticData> semanticData = this.semanticDataCreationService.create(event, List.of(documentData.document()), documentData.ePackageEntries().stream().map(EPackageEntry::nsURI).toList());
            if (semanticData instanceof Success<SemanticData> success) {
                result = Optional.ofNullable(success.data());
            }
        }
        return result;
    }

    private Optional<String> getUniqueLibraryName(EObject eObject, List<EObject> libraryCandidates) {
        Optional<String> optLibraryName = this.getLibraryName(eObject);
        if (optLibraryName.isPresent()) {
            String libraryName = optLibraryName.get();
            List<EObject> librariesWithSameName = libraryCandidates.stream()
                    .filter(libraryCandidate -> this.getLibraryName(libraryCandidate).map(name -> Objects.equals(name, libraryName)).orElse(false))
                    .toList();
            if (librariesWithSameName.size() > 1) {
                optLibraryName = Optional.of(libraryName + librariesWithSameName.indexOf(eObject));
            }
        }
        return optLibraryName;
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

    private Resource getOrCreateLibraryResource(String projectId, String name, String version, ResourceSet rSet) {
        String resourceId = projectId + ":" + name + ":" + version;
        URI resourceURI = new JSONResourceFactory().createResourceURI(UUID.nameUUIDFromBytes(resourceId.getBytes()).toString());
        Resource resource = rSet.getResource(resourceURI, false);
        if (resource == null) {
            resource = rSet.createResource(resourceURI);
            resource.eAdapters().add(new ResourceMetadataAdapter(name));
        }
        return resource;
    }
}
