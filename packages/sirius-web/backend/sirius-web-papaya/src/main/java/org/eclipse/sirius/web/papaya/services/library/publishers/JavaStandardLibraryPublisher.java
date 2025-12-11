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
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.IDAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.papaya.PapayaFactory;
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.web.application.editingcontext.services.EPackageEntry;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingDomainFactory;
import org.eclipse.sirius.web.application.editingcontext.services.api.IResourceToDocumentService;
import org.eclipse.sirius.web.domain.boundedcontexts.library.services.api.ILibrarySearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataCreationService;
import org.eclipse.sirius.web.papaya.factories.JavaIoFactory;
import org.eclipse.sirius.web.papaya.factories.JavaLangFactory;
import org.eclipse.sirius.web.papaya.factories.JavaTextFactory;
import org.eclipse.sirius.web.papaya.factories.JavaTimeFactory;
import org.eclipse.sirius.web.papaya.factories.JavaUtilFactory;
import org.eclipse.sirius.web.papaya.services.library.PublishPapayaLibraryCommand;
import org.eclipse.sirius.web.papaya.services.library.api.IPapayaLibraryPublisher;
import org.springframework.stereotype.Service;

/**
 * Used to publish the Java standard library.
 *
 * @author sbegaudeau
 */
@Service
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class JavaStandardLibraryPublisher implements IPapayaLibraryPublisher {

    private final ILibrarySearchService librarySearchService;

    private final IEditingDomainFactory editingDomainFactory;

    private final IResourceToDocumentService resourceToDocumentService;

    private final ISemanticDataCreationService semanticDataCreationService;

    public JavaStandardLibraryPublisher(ILibrarySearchService librarySearchService, IEditingDomainFactory editingDomainFactory, IResourceToDocumentService resourceToDocumentService, ISemanticDataCreationService semanticDataCreationService) {
        this.librarySearchService = Objects.requireNonNull(librarySearchService);
        this.editingDomainFactory = Objects.requireNonNull(editingDomainFactory);
        this.resourceToDocumentService = Objects.requireNonNull(resourceToDocumentService);
        this.semanticDataCreationService = Objects.requireNonNull(semanticDataCreationService);
    }

    @Override
    public boolean canPublish(PublishPapayaLibraryCommand command) {
        return command.namespace().equals("papaya") && command.name().equals("java");
    }

    @Override
    public void publish(PublishPapayaLibraryCommand command) {
        if (this.librarySearchService.existsByNamespaceAndNameAndVersion(command.namespace(), command.name(), command.version())) {
            return;
        }

        AdapterFactoryEditingDomain editingDomain = this.editingDomainFactory.createEditingDomain();
        var packageRegistry = editingDomain.getResourceSet().getPackageRegistry();
        packageRegistry.put(PapayaPackage.eNS_URI, PapayaPackage.eINSTANCE);

        if (command.version().equals("0.0.1")) {
            this.createJava001(command, editingDomain.getResourceSet());
        } else if (command.version().equals("0.0.2")) {
            this.createJava002(command, editingDomain.getResourceSet());
        } else if (command.version().equals("0.0.3")) {
            this.createJava003(command, editingDomain.getResourceSet());
        }
        if (editingDomain.getAdapterFactory() instanceof IDisposable disposable) {
            disposable.dispose();
        }
    }

    private void createJava001(PublishPapayaLibraryCommand command, ResourceSet resourceSet) {
        var javaLang = new JavaLangFactory().javaLang();

        var javaBaseComponent = PapayaFactory.eINSTANCE.createComponent();
        javaBaseComponent.setName("java.base");
        javaBaseComponent.getPackages().add(javaLang);

        var javaProject = PapayaFactory.eINSTANCE.createProject();
        javaProject.setName("Java Standard Library");
        javaProject.getElements().add(javaBaseComponent);

        javaProject.eAllContents().forEachRemaining(eObject -> {
            String stableIdentifier = command.namespace() + command.name() + EcoreUtil.getURI(eObject).toString();
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(stableIdentifier.getBytes())));
        });
        String stableIdentifier = command.namespace() + command.name() + EcoreUtil.getURI(javaProject).toString();
        javaProject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(stableIdentifier.getBytes())));

        var documentId = UUID.nameUUIDFromBytes((command.namespace() + ":" + command.name()).getBytes(StandardCharsets.UTF_8)).toString();
        var resource = new JSONResourceFactory().createResourceFromPath(documentId);
        var resourceMetadataAdapter = new ResourceMetadataAdapter("Java", true);
        resource.eAdapters().add(resourceMetadataAdapter);
        resourceSet.getResources().add(resource);

        resource.getContents().add(javaProject);

        this.resourceToDocumentService.toDocument(resource, false).ifPresent(documentData -> {
            this.semanticDataCreationService.create(command, List.of(documentData.document()), documentData.ePackageEntries().stream().map(EPackageEntry::nsURI).toList(), List.of());
        });
    }

    private void createJava002(PublishPapayaLibraryCommand command, ResourceSet resourceSet) {
        var javaLang = new JavaLangFactory().javaLang();
        var javaText = new JavaTextFactory().javaText();

        var javaBaseComponent = PapayaFactory.eINSTANCE.createComponent();
        javaBaseComponent.setName("java.base");
        javaBaseComponent.getPackages().add(javaLang);
        javaBaseComponent.getPackages().add(javaText);

        var javaProject = PapayaFactory.eINSTANCE.createProject();
        javaProject.setName("Java Standard Library");
        javaProject.getElements().add(javaBaseComponent);

        javaProject.eAllContents().forEachRemaining(eObject -> {
            String stableIdentifier = command.namespace() + command.name() + EcoreUtil.getURI(eObject).toString();
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(stableIdentifier.getBytes())));
        });
        String stableIdentifier = command.namespace() + command.name() + EcoreUtil.getURI(javaProject).toString();
        javaProject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(stableIdentifier.getBytes())));

        var documentId = UUID.nameUUIDFromBytes((command.namespace() + ":" + command.name()).getBytes(StandardCharsets.UTF_8)).toString();
        var resource = new JSONResourceFactory().createResourceFromPath(documentId);
        var resourceMetadataAdapter = new ResourceMetadataAdapter("Java", true);
        resource.eAdapters().add(resourceMetadataAdapter);
        resourceSet.getResources().add(resource);

        resource.getContents().add(javaProject);

        this.resourceToDocumentService.toDocument(resource, false).ifPresent(documentData -> {
            this.semanticDataCreationService.create(command, List.of(documentData.document()), documentData.ePackageEntries().stream().map(EPackageEntry::nsURI).toList(), List.of());
        });
    }

    private void createJava003(PublishPapayaLibraryCommand command, ResourceSet resourceSet) {
        var javaLang = new JavaLangFactory().javaLang();
        var javaText = new JavaTextFactory().javaText();
        var javaIo = new JavaIoFactory().javaIo();
        var javaTime = new JavaTimeFactory().javaTime();
        var javaUtil = new JavaUtilFactory().javaUtil();

        var javaBaseComponent = PapayaFactory.eINSTANCE.createComponent();
        javaBaseComponent.setName("java.base");
        javaBaseComponent.getPackages().add(javaLang);
        javaBaseComponent.getPackages().add(javaText);
        javaBaseComponent.getPackages().add(javaIo);
        javaBaseComponent.getPackages().add(javaTime);
        javaBaseComponent.getPackages().add(javaUtil);

        var javaProject = PapayaFactory.eINSTANCE.createProject();
        javaProject.setName("Java Standard Library");
        javaProject.getElements().add(javaBaseComponent);

        javaProject.eAllContents().forEachRemaining(eObject -> {
            String stableIdentifier = command.namespace() + command.name() + EcoreUtil.getURI(eObject).toString();
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(stableIdentifier.getBytes())));
        });
        String stableIdentifier = command.namespace() + command.name() + EcoreUtil.getURI(javaProject).toString();
        javaProject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(stableIdentifier.getBytes())));

        var documentId = UUID.nameUUIDFromBytes((command.namespace() + ":" + command.name()).getBytes(StandardCharsets.UTF_8)).toString();
        var resource = new JSONResourceFactory().createResourceFromPath(documentId);
        var resourceMetadataAdapter = new ResourceMetadataAdapter("Java", true);
        resource.eAdapters().add(resourceMetadataAdapter);
        resourceSet.getResources().add(resource);

        resource.getContents().add(javaProject);

        this.resourceToDocumentService.toDocument(resource, false).ifPresent(documentData -> {
            this.semanticDataCreationService.create(command, List.of(documentData.document()), documentData.ePackageEntries().stream().map(EPackageEntry::nsURI).toList(), List.of());
        });
    }
}
