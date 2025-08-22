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
package org.eclipse.sirius.web.papaya.services.library.publishers;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.IDAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.papaya.PapayaFactory;
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.web.application.editingcontext.services.EPackageEntry;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingDomainFactory;
import org.eclipse.sirius.web.application.editingcontext.services.api.IResourceToDocumentService;
import org.eclipse.sirius.web.domain.boundedcontexts.library.Library;
import org.eclipse.sirius.web.domain.boundedcontexts.library.services.api.ILibrarySearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataCreationService;
import org.eclipse.sirius.web.papaya.factories.ReactorFactory;
import org.eclipse.sirius.web.papaya.services.library.PublishPapayaLibraryCommand;
import org.eclipse.sirius.web.papaya.services.library.api.IPapayaLibraryPublisher;
import org.springframework.stereotype.Service;

/**
 * Used to publish the reactor standard library.
 *
 * @author sbegaudeau
 */
@Service
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class ReactorLibraryPublisher implements IPapayaLibraryPublisher {

    private final ILibrarySearchService librarySearchService;

    private final IEditingDomainFactory editingDomainFactory;

    private final IResourceToDocumentService resourceToDocumentService;

    private final ISemanticDataCreationService semanticDataCreationService;

    public ReactorLibraryPublisher(ILibrarySearchService librarySearchService, IEditingDomainFactory editingDomainFactory, IResourceToDocumentService resourceToDocumentService, ISemanticDataCreationService semanticDataCreationService) {
        this.librarySearchService = Objects.requireNonNull(librarySearchService);
        this.editingDomainFactory = Objects.requireNonNull(editingDomainFactory);
        this.resourceToDocumentService = Objects.requireNonNull(resourceToDocumentService);
        this.semanticDataCreationService = Objects.requireNonNull(semanticDataCreationService);
    }

    @Override
    public boolean canPublish(PublishPapayaLibraryCommand command) {
        return command.namespace().equals("papaya") && command.name().equals("reactor");
    }

    @Override
    public void publish(PublishPapayaLibraryCommand command) {
        if (this.librarySearchService.existsByNamespaceAndNameAndVersion(command.namespace(), command.name(), command.version())) {
            return;
        }

        AdapterFactoryEditingDomain editingDomain = this.editingDomainFactory.createEditingDomain();
        var packageRegistry = editingDomain.getResourceSet().getPackageRegistry();
        packageRegistry.put(PapayaPackage.eNS_URI, PapayaPackage.eINSTANCE);

        var optionalJava001Library = this.librarySearchService.findByNamespaceAndNameAndVersion("papaya", "java", "0.0.1");
        var optionalJava002Library = this.librarySearchService.findByNamespaceAndNameAndVersion("papaya", "java", "0.0.2");
        var optionalJava003Library = this.librarySearchService.findByNamespaceAndNameAndVersion("papaya", "java", "0.0.3");
        var optionalReactiveStreams001Library = this.librarySearchService.findByNamespaceAndNameAndVersion("papaya", "reactivestreams", "0.0.1");
        var optionalReactiveStreams002Library = this.librarySearchService.findByNamespaceAndNameAndVersion("papaya", "reactivestreams", "0.0.2");
        var optionalReactiveStreams003Library = this.librarySearchService.findByNamespaceAndNameAndVersion("papaya", "reactivestreams", "0.0.3");

        if (command.version().equals("0.0.1") && optionalJava001Library.isPresent() && optionalReactiveStreams001Library.isPresent()) {
            var java001Library = optionalJava001Library.get();
            var reactiveStreams001Library = optionalReactiveStreams001Library.get();
            this.createReactor(command, editingDomain.getResourceSet(), List.of(java001Library, reactiveStreams001Library));
        } else if (command.version().equals("0.0.2") && optionalJava002Library.isPresent() && optionalReactiveStreams002Library.isPresent()) {
            var java002Library = optionalJava002Library.get();
            var reactiveStreams002Library = optionalReactiveStreams002Library.get();
            this.createReactor(command, editingDomain.getResourceSet(), List.of(java002Library, reactiveStreams002Library));
        } else if (command.version().equals("0.0.3") && optionalJava003Library.isPresent() && optionalReactiveStreams003Library.isPresent()) {
            var java003Library = optionalJava003Library.get();
            var reactiveStreams003Library = optionalReactiveStreams003Library.get();
            this.createReactor(command, editingDomain.getResourceSet(), List.of(java003Library, reactiveStreams003Library));
        }
    }

    private void createReactor(PublishPapayaLibraryCommand command, ResourceSet resourceSet, List<Library> libraries) {
        var reactor = new ReactorFactory().reactor();

        var reactorComponent = PapayaFactory.eINSTANCE.createComponent();
        reactorComponent.setName("reactor");
        reactorComponent.getPackages().add(reactor);

        var reactorProject = PapayaFactory.eINSTANCE.createProject();
        reactorProject.setName("Reactor");
        reactorProject.getElements().add(reactorComponent);

        reactorProject.eAllContents().forEachRemaining(eObject -> {
            String stableIdentifier = command.namespace() + command.name() + EcoreUtil.getURI(eObject).toString();
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(stableIdentifier.getBytes())));
        });
        String stableIdentifier = command.namespace() + command.name() + EcoreUtil.getURI(reactorProject).toString();
        reactorProject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(stableIdentifier.getBytes())));

        var documentId = UUID.nameUUIDFromBytes((command.namespace() + ":" + command.name()).getBytes(StandardCharsets.UTF_8)).toString();
        var resource = new JSONResourceFactory().createResourceFromPath(documentId);
        var resourceMetadataAdapter = new ResourceMetadataAdapter("Reactor", true);
        resource.eAdapters().add(resourceMetadataAdapter);
        resourceSet.getResources().add(resource);

        resource.getContents().add(reactorProject);

        var dependencies = libraries.stream().map(Library::getSemanticData).toList();
        this.resourceToDocumentService.toDocument(resource, false).ifPresent(documentData -> {
            this.semanticDataCreationService.create(command, List.of(documentData.document()), documentData.ePackageEntries().stream().map(EPackageEntry::nsURI).toList(), dependencies);
        });
    }
}
