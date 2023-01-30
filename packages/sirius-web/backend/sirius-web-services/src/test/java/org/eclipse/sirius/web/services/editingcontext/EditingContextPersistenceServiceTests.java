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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextPersistenceService;
import org.eclipse.sirius.components.emf.services.EditingContext;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.persistence.entities.AccountEntity;
import org.eclipse.sirius.web.persistence.entities.DocumentEntity;
import org.eclipse.sirius.web.persistence.entities.ProjectEntity;
import org.eclipse.sirius.web.persistence.repositories.IDocumentRepository;
import org.eclipse.sirius.web.services.documents.DocumentMetadataAdapter;
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
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(id.toString());
        resource.eAdapters().add(new DocumentMetadataAdapter(name));

        EClass eClass = EcoreFactory.eINSTANCE.createEClass();
        eClass.setName("Concept");
        resource.getContents().add(eClass);

        AdapterFactoryEditingDomain editingDomain = new EditingDomainFactory().create();
        editingDomain.getResourceSet().getResources().add(resource);

        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId(projectId);
        projectEntity.setName("");
        AccountEntity owner = new AccountEntity();
        owner.setId(UUID.randomUUID());
        owner.setUsername("jdoe");
        projectEntity.setOwner(owner);

        DocumentEntity existingEntity = new DocumentEntity();
        existingEntity.setId(id);
        existingEntity.setProject(projectEntity);
        existingEntity.setName(name);
        existingEntity.setContent("");

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
        IEditingContextPersistenceService editingContextPersistenceService = new EditingContextPersistenceService(documentRepository, new NoOpApplicationEventPublisher(), new SimpleMeterRegistry());
        assertThat(entities).hasSize(0);

        IEditingContext editingContext = new EditingContext(UUID.randomUUID().toString(), editingDomain, Map.of());

        editingContextPersistenceService.persist(editingContext);
        assertThat(entities).hasSize(1);

        DocumentEntity documentEntity = entities.get(0);
        assertThat(documentEntity.getId()).isEqualTo(id);
        assertThat(documentEntity.getName()).isEqualTo(name);
        assertThat(documentEntity.getProject().getId()).isEqualTo(projectId);
    }
}
