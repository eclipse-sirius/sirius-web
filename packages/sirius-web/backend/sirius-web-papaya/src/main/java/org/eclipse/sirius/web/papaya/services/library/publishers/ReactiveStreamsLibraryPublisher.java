/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
package org.eclipse.sirius.web.papaya.services.library.publishers;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.IDAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.papaya.PapayaFactory;
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.web.application.editingcontext.services.EPackageEntry;
import org.eclipse.sirius.web.application.editingcontext.services.api.IResourceToDocumentService;
import org.eclipse.sirius.web.domain.boundedcontexts.library.Library;
import org.eclipse.sirius.web.domain.boundedcontexts.library.services.api.ILibrarySearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataCreationService;
import org.eclipse.sirius.web.papaya.factories.ReactiveStreamsFactory;
import org.eclipse.sirius.web.papaya.services.library.PublishPapayaLibraryCommand;
import org.eclipse.sirius.web.papaya.services.library.api.IPapayaLibraryPublisher;
import org.springframework.stereotype.Service;

/**
 * Used to publish the reactive streams library.
 *
 * @author sbegaudeau
 */
@Service
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class ReactiveStreamsLibraryPublisher implements IPapayaLibraryPublisher {

    private final ILibrarySearchService librarySearchService;

    private final IResourceToDocumentService resourceToDocumentService;

    private final ISemanticDataCreationService semanticDataCreationService;

    public ReactiveStreamsLibraryPublisher(ILibrarySearchService librarySearchService, IResourceToDocumentService resourceToDocumentService, ISemanticDataCreationService semanticDataCreationService) {
        this.librarySearchService = Objects.requireNonNull(librarySearchService);
        this.resourceToDocumentService = Objects.requireNonNull(resourceToDocumentService);
        this.semanticDataCreationService = Objects.requireNonNull(semanticDataCreationService);
    }

    @Override
    public boolean canPublish(PublishPapayaLibraryCommand command) {
        return command.namespace().equals("papaya") && command.name().equals("reactivestreams");
    }

    @Override
    public void publish(PublishPapayaLibraryCommand command) {
        if (this.librarySearchService.existsByNamespaceAndNameAndVersion(command.namespace(), command.name(), command.version())) {
            return;
        }

        var resourceSet = new ResourceSetImpl();
        resourceSet.getPackageRegistry().put(PapayaPackage.eNS_URI, PapayaPackage.eINSTANCE);

        var documentId = UUID.nameUUIDFromBytes((command.namespace() + ":" + command.name() + ":" + command.version()).getBytes(StandardCharsets.UTF_8)).toString();
        var resource = new JSONResourceFactory().createResourceFromPath(documentId);
        var resourceMetadataAdapter = new ResourceMetadataAdapter(command.name());
        resource.eAdapters().add(resourceMetadataAdapter);
        resourceSet.getResources().add(resource);

        var optionalJava001Library = this.librarySearchService.findByNamespaceAndNameAndVersion("papaya", "java", "0.0.1");
        var optionalJava002Library = this.librarySearchService.findByNamespaceAndNameAndVersion("papaya", "java", "0.0.2");
        var optionalJava003Library = this.librarySearchService.findByNamespaceAndNameAndVersion("papaya", "java", "0.0.3");

        if (command.version().equals("0.0.1") && optionalJava001Library.isPresent()) {
            var java001Library = optionalJava001Library.get();
            this.createReactiveStreams(command, resourceSet, java001Library);
        } else if (command.version().equals("0.0.2") && optionalJava002Library.isPresent()) {
            var java002Library = optionalJava002Library.get();
            this.createReactiveStreams(command, resourceSet, java002Library);
        } else if (command.version().equals("0.0.3") && optionalJava003Library.isPresent()) {
            var java003Library = optionalJava003Library.get();
            this.createReactiveStreams(command, resourceSet, java003Library);
        }
    }

    private void createReactiveStreams(PublishPapayaLibraryCommand command, ResourceSet resourceSet, Library library) {
        var orgReactiveStreams = new ReactiveStreamsFactory().orgReactiveStreams();

        var reactiveStreamsComponent = PapayaFactory.eINSTANCE.createComponent();
        reactiveStreamsComponent.setName("reactivestreams");
        reactiveStreamsComponent.getPackages().add(orgReactiveStreams);

        var reactiveStreamsProject = PapayaFactory.eINSTANCE.createProject();
        reactiveStreamsProject.setName("Reactive Streams");
        reactiveStreamsProject.getElements().add(reactiveStreamsComponent);

        reactiveStreamsProject.eAllContents().forEachRemaining(eObject -> {
            String stableIdentifier = command.namespace() + command.name() + EcoreUtil.getURI(eObject).toString();
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(stableIdentifier.getBytes())));
        });
        String stableIdentifier = command.namespace() + command.name() + EcoreUtil.getURI(reactiveStreamsProject).toString();
        reactiveStreamsProject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(stableIdentifier.getBytes())));

        var documentId = UUID.nameUUIDFromBytes((command.namespace() + ":" + command.name()).getBytes(StandardCharsets.UTF_8)).toString();
        var resource = new JSONResourceFactory().createResourceFromPath(documentId);
        var resourceMetadataAdapter = new ResourceMetadataAdapter("Reactive Streams", true);
        resource.eAdapters().add(resourceMetadataAdapter);
        resourceSet.getResources().add(resource);

        resource.getContents().add(reactiveStreamsProject);

        this.resourceToDocumentService.toDocument(resource, false).ifPresent(documentData -> {
            this.semanticDataCreationService.create(command, List.of(documentData.document()), documentData.ePackageEntries().stream().map(EPackageEntry::nsURI).toList(), List.of(library.getSemanticData()));
        });
    }
}
