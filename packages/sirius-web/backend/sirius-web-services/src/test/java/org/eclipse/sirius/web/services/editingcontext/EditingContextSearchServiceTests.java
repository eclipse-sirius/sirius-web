/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
package org.eclipse.sirius.web.services.editingcontext;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.emf.services.EditingContext;
import org.eclipse.sirius.components.emf.services.IEditingContextEPackageService;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.web.persistence.entities.DocumentEntity;
import org.eclipse.sirius.web.persistence.entities.ProjectEntity;
import org.eclipse.sirius.web.persistence.repositories.IDocumentRepository;
import org.eclipse.sirius.web.persistence.repositories.IProjectRepository;
import org.eclipse.sirius.web.services.documents.DocumentMetadataAdapter;
import org.eclipse.sirius.web.services.editingcontext.api.IDynamicDialogDescriptionServices;
import org.eclipse.sirius.web.services.editingcontext.api.IDynamicRepresentationDescriptionService;
import org.eclipse.sirius.web.services.editingcontext.api.IEditingDomainFactoryService;
import org.eclipse.sirius.web.services.projects.api.EditingContextMetadata;
import org.eclipse.sirius.web.services.projects.api.IEditingContextMetadataProvider;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

/**
 * Unit tests of the editing context search service.
 *
 * @author sbegaudeau
 */
public class EditingContextSearchServiceTests {

    // @formatter:off
    private static final String CONTENT = """
        {
          "json": {
            "version": "1.0",
            "encoding": "utf-8"
          },
          "ns": {
            "ecore": "http://www.eclipse.org/emf/2002/Ecore"
          },
          "content": [
            {
              "eClass": "ecore:EPackage",
              "data": {
                "name": "ecore",
                "nsURI": "http://www.eclipse.org/emf/2002/Ecore",
                "nsPrefix": "ecore",
                "eClassifiers": [
                  {
                    "eClass": "ecore:EClass",
                    "data": {
                      "name": "AClass"
                    }
                  }
                ]
              }
            }
          ]
        }
        """;
    // @formatter:on

    @Test
    public void testEditingContextWithNoDocuments() {
        IProjectRepository projectRepository = new NoOpProjectRepository();
        IDocumentRepository documentRepository = new NoOpDocumentRepository();
        ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory();
        EPackage.Registry ePackageRegistry = new EPackageRegistryImpl();
        ePackageRegistry.put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);

        String projectId = UUID.randomUUID().toString();

        IEditingContextEPackageService editingContextEPackageService = editingContextId -> List.of();

        var editingContextMetadata = new EditingContextMetadata(List.of());
        IEditingContextMetadataProvider editingContextMetadataProvider = editingContextId -> editingContextMetadata;

        IEditingDomainFactoryService editingDomainFactoryService = new EditingDomainFactoryService(editingContextEPackageService, editingContextMetadataProvider, composedAdapterFactory,
                ePackageRegistry, Optional.empty());
        IEditingContextSearchService editingContextSearchService = new EditingContextSearchService(projectRepository, documentRepository, editingDomainFactoryService, List.of(),
                new IDynamicRepresentationDescriptionService.NoOp(), new IDynamicDialogDescriptionServices.NoOp(), new SimpleMeterRegistry());
        IEditingContext editingContext = editingContextSearchService.findById(projectId).get();

        assertThat(editingContext).isInstanceOf(EditingContext.class);
        EditingDomain editingDomain = ((EditingContext) editingContext).getDomain();
        assertThat(editingDomain.getResourceSet().getResources()).hasSize(0);
    }

    @Test
    public void testEditingContextWithDocuments() {
        UUID projectId = UUID.randomUUID();

        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId(projectId);
        projectEntity.setName("");

        DocumentEntity firstDocumentEntity = new DocumentEntity();
        firstDocumentEntity.setId(UUID.randomUUID());
        firstDocumentEntity.setName("First Document");
        firstDocumentEntity.setProject(projectEntity);
        firstDocumentEntity.setContent(CONTENT);

        DocumentEntity secondDocumentEntity = new DocumentEntity();
        secondDocumentEntity.setId(UUID.randomUUID());
        secondDocumentEntity.setName("Second Document");
        secondDocumentEntity.setProject(projectEntity);
        secondDocumentEntity.setContent(CONTENT);

        IProjectRepository projectRepository = new NoOpProjectRepository();
        IDocumentRepository documentRepository = new NoOpDocumentRepository() {
            @Override
            public List<DocumentEntity> findAllByProjectId(UUID projectId) {
                return List.of(firstDocumentEntity, secondDocumentEntity);
            }
        };

        ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory();
        EPackage.Registry ePackageRegistry = new EPackageRegistryImpl();
        ePackageRegistry.put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);

        IEditingContextEPackageService editingContextEPackageService = editingContextId -> List.of();

        var editingContextMetadata = new EditingContextMetadata(List.of());
        IEditingContextMetadataProvider editingContextMetadataProvider = editingContextId -> editingContextMetadata;

        IEditingDomainFactoryService editingDomainFactoryService = new EditingDomainFactoryService(editingContextEPackageService, editingContextMetadataProvider, composedAdapterFactory,
                ePackageRegistry, Optional.empty());
        IEditingContextSearchService editingContextSearchService = new EditingContextSearchService(projectRepository, documentRepository, editingDomainFactoryService, List.of(),
                new IDynamicRepresentationDescriptionService.NoOp(), new IDynamicDialogDescriptionServices.NoOp(), new SimpleMeterRegistry());
        IEditingContext editingContext = editingContextSearchService.findById(projectId.toString()).get();

        assertThat(editingContext).isInstanceOf(EditingContext.class);
        EditingDomain editingDomain = ((EditingContext) editingContext).getDomain();

        assertThat(editingDomain.getResourceSet().getResources()).hasSize(2);
        Resource firstResource = editingDomain.getResourceSet().getResource(new JSONResourceFactory().createResourceURI(firstDocumentEntity.getId().toString()), true);
        this.assertProperResourceLoading(firstResource, firstDocumentEntity);

        Resource secondResource = editingDomain.getResourceSet().getResource(new JSONResourceFactory().createResourceURI(secondDocumentEntity.getId().toString()), true);
        this.assertProperResourceLoading(secondResource, secondDocumentEntity);
    }

    private void assertProperResourceLoading(Resource resource, DocumentEntity documentEntity) {
        assertThat(resource).isNotNull();
        assertThat(resource.eAdapters()).hasSize(2);
        // @formatter:off
        var optionalDocumentMetadataAdapter = resource.eAdapters().stream()
                .filter(DocumentMetadataAdapter.class::isInstance)
                .map(DocumentMetadataAdapter.class::cast)
                .findFirst();
        // @formatter:on
        assertThat(optionalDocumentMetadataAdapter).isPresent();
        assertThat(optionalDocumentMetadataAdapter.get().getName()).isEqualTo(documentEntity.getName());

        // @formatter:off
        var optionalCrossReferencerAdapter = resource.eAdapters().stream()
                .filter(ECrossReferenceAdapter.class::isInstance)
                .map(ECrossReferenceAdapter.class::cast)
                .findFirst();
        // @formatter:on
        assertThat(optionalCrossReferencerAdapter).isPresent();
    }
}
