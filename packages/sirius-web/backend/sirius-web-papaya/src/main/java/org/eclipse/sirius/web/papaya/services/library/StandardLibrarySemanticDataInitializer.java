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
package org.eclipse.sirius.web.papaya.services.library;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.web.application.editingcontext.services.EPackageEntry;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingDomainFactory;
import org.eclipse.sirius.web.application.editingcontext.services.api.IResourceToDocumentService;
import org.eclipse.sirius.web.domain.boundedcontexts.library.services.api.ILibrarySearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataCreationService;
import org.eclipse.sirius.web.papaya.factories.JavaLangFactory;
import org.eclipse.sirius.web.papaya.factories.ReactiveStreamsFactory;
import org.eclipse.sirius.web.papaya.services.library.api.IStandardLibrarySemanticDataInitializer;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Used to initialize the semantic data of a Papaya standard library.
 *
 * @author sbegaudeau
 */
@Service
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class StandardLibrarySemanticDataInitializer implements IStandardLibrarySemanticDataInitializer {

    private final ILibrarySearchService librarySearchService;

    private final IEditingDomainFactory editingDomainFactory;

    private final IResourceToDocumentService resourceToDocumentService;

    private final ISemanticDataCreationService semanticDataCreationService;

    public StandardLibrarySemanticDataInitializer(ILibrarySearchService librarySearchService, IEditingDomainFactory editingDomainFactory, IResourceToDocumentService resourceToDocumentService, ISemanticDataCreationService semanticDataCreationService) {
        this.librarySearchService = Objects.requireNonNull(librarySearchService);
        this.editingDomainFactory = Objects.requireNonNull(editingDomainFactory);
        this.resourceToDocumentService = Objects.requireNonNull(resourceToDocumentService);
        this.semanticDataCreationService = Objects.requireNonNull(semanticDataCreationService);
    }

    @Transactional
    @Override
    public void initializeStandardLibrary(InitializeStandardLibraryEvent event) {
        if (this.librarySearchService.existsByNamespaceAndNameAndVersion(event.namespace(), event.name(), event.version())) {
            return;
        }

        AdapterFactoryEditingDomain editingDomain = this.editingDomainFactory.createEditingDomain();
        var packageRegistry = editingDomain.getResourceSet().getPackageRegistry();
        packageRegistry.put(PapayaPackage.eNS_URI, PapayaPackage.eINSTANCE);

        var documentId = UUID.nameUUIDFromBytes((event.namespace() + ":" + event.name() + ":" + event.version()).getBytes(StandardCharsets.UTF_8)).toString();
        var resource = new JSONResourceFactory().createResourceFromPath(documentId);
        var resourceMetadataAdapter = new ResourceMetadataAdapter(event.name());
        resource.eAdapters().add(resourceMetadataAdapter);
        editingDomain.getResourceSet().getResources().add(resource);

        List<AggregateReference<SemanticData, UUID>> dependencies = new ArrayList<>();

        if (event.namespace().equals("papaya") && event.name().equals("java")) {
            var javaLangPackage = new JavaLangFactory().javaLang();
            resource.getContents().add(javaLangPackage);
        } else if (event.namespace().equals("papaya") && event.name().equals("reactivestreams")) {
            var optionalJavaStandardLibrary = this.librarySearchService.findByNamespaceAndNameAndVersion("papaya", "java", "17.0.0");
            if (optionalJavaStandardLibrary.isPresent()) {
                var orgReactiveStreamsPackage = new ReactiveStreamsFactory().orgReactiveStreams();
                resource.getContents().add(orgReactiveStreamsPackage);

                var javaStandardLibrary = optionalJavaStandardLibrary.get();
                dependencies.add(javaStandardLibrary.getSemanticData());
            }
        }

        this.resourceToDocumentService.toDocument(resource, false).ifPresent(documentData -> {
            this.semanticDataCreationService.create(event, List.of(documentData.document()), documentData.ePackageEntries().stream().map(EPackageEntry::nsURI).toList(), dependencies);
        });
    }
}
