/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.persistence.entities.DocumentEntity;
import org.eclipse.sirius.web.persistence.entities.ProjectEntity;
import org.eclipse.sirius.web.persistence.repositories.IDocumentRepository;
import org.eclipse.sirius.web.persistence.repositories.IProjectRepository;
import org.eclipse.sirius.web.services.documents.EditingDomainFactory;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

/**
 * Unit tests of the editing context persistence service.
 *
 * @author sbegaudeau
 */
public class EditingContextPersistenceServiceTests {
    @Test
    public void testDocumentPersistence() {
        UUID projectId = UUID.randomUUID();
        String name = "New Document";
        UUID id = UUID.randomUUID();
        AdapterFactoryEditingDomain editingDomain = new EditingDomainFactory().create();

        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId(projectId);
        projectEntity.setName("");

        var existingEntity = this.createFakeDocument(name, id, projectEntity, editingDomain);

        List<DocumentEntity> entities = new ArrayList<>();
        IDocumentRepository documentRepository = new NoOpDocumentRepository() {
            @Override
            public <S extends DocumentEntity> S save(S entity) {
                entities.add(entity);
                return entity;
            }

            @Override
            public Optional<DocumentEntity> findById(UUID id) {
                return Optional.of(existingEntity);
            }
        };
        IProjectRepository projectRepository = new NoOpProjectRepository();

        var editingContextPersistenceService = new EditingContextPersistenceService(documentRepository, projectRepository, new NoOpApplicationEventPublisher(), new SimpleMeterRegistry());
        assertThat(entities).hasSize(0);

        var editingContext = new EditingContext(UUID.randomUUID().toString(), editingDomain, Map.of(), List.of());

        editingContextPersistenceService.persist(new ICause.NoOp(), editingContext);
        assertThat(entities).hasSize(1);

        DocumentEntity documentEntity = entities.get(0);
        assertThat(documentEntity.getId()).isEqualTo(id);
        assertThat(documentEntity.getName()).isEqualTo(name);
        assertThat(documentEntity.getProject().getId()).isEqualTo(projectId);
    }


    @Test
    public void testUnkownDocumentPersistence() {

        UUID projectId = UUID.randomUUID();
        String name = "New Document";
        UUID id = UUID.randomUUID();
        AdapterFactoryEditingDomain editingDomain = new EditingDomainFactory().create();

        ProjectEntity existingProject = new ProjectEntity();
        existingProject.setId(projectId);
        existingProject.setName("Existing project");


        this.createFakeDocument(name, id, existingProject, editingDomain);

        List<DocumentEntity> entities = new ArrayList<>();
        IDocumentRepository documentRepository = new NoOpDocumentRepository() {
            @Override
            public <S extends DocumentEntity> S save(S entity) {
                entities.add(entity);
                return entity;
            }

        };


        IProjectRepository projectRepository = new NoOpProjectRepository() {
            @Override
            public Optional<ProjectEntity> findById(UUID id) {
                return Optional.of(existingProject);
            }
        };

        var editingContextPersistenceService = new EditingContextPersistenceService(documentRepository, projectRepository, new NoOpApplicationEventPublisher(), new SimpleMeterRegistry());

        assertThat(entities).hasSize(0);

        var editingContext = new EditingContext(UUID.randomUUID().toString(), editingDomain, Map.of(), List.of());

        editingContextPersistenceService.persist(new ICause.NoOp(), editingContext);
        assertThat(entities).hasSize(1);

        DocumentEntity documentEntity = entities.get(0);
        assertThat(documentEntity.getId()).isEqualTo(id);
        assertThat(documentEntity.getName()).isEqualTo(name);
        assertThat(documentEntity.getProject().getId()).isEqualTo(projectId);

    }

    private DocumentEntity createFakeDocument(String documentName, UUID documentId, ProjectEntity projectEntity, AdapterFactoryEditingDomain editingDomain) {
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(documentId.toString());
        resource.eAdapters().add(new ResourceMetadataAdapter(documentName));

        EClass eClass = EcoreFactory.eINSTANCE.createEClass();
        eClass.setName("Concept");
        resource.getContents().add(eClass);

        editingDomain.getResourceSet().getResources().add(resource);

        DocumentEntity existingEntity = new DocumentEntity();
        existingEntity.setId(documentId);
        existingEntity.setProject(projectEntity);
        existingEntity.setName(documentName);
        existingEntity.setContent("");

        return existingEntity;
    }
}
