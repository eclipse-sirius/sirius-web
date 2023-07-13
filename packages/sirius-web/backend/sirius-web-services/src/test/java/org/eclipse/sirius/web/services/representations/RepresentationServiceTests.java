/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.web.services.representations;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.services.EditingContext;
import org.eclipse.sirius.web.persistence.entities.DocumentEntity;
import org.eclipse.sirius.web.persistence.entities.ProjectEntity;
import org.eclipse.sirius.web.persistence.entities.RepresentationEntity;
import org.eclipse.sirius.web.persistence.repositories.IDocumentRepository;
import org.eclipse.sirius.web.persistence.repositories.IProjectRepository;
import org.eclipse.sirius.web.persistence.repositories.IRepresentationRepository;
import org.eclipse.sirius.web.services.documents.EditingDomainFactory;
import org.eclipse.sirius.web.services.projects.NoOpProjectRepository;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

/**
 * Unit tests of the representation service.
 *
 * @author arichard
 */
public class RepresentationServiceTests {

    private static final String DOCUMENT_NAME = "Obsydians";

    private static final String FIRST_PROJECT_NAME = "Cluster Prism";

    private static final String FIRST_TARGET_OBJECT_ID = "firstTargetObjectId";

    private static final String SECOND_TARGET_OBJECT_ID = "secondTargetObjectId";

    private static final String FIRST_DIAGRAM_LABEL = "First Diagram";

    private static final String SECOND_DIAGRAM_LABEL = "Second Diagram";

    private static final String THIRD_DIAGRAM_LABEL = "Third Diagram";

    private static final String DOCUMENT_CONTENT_PATTERN = "{ \"id\": \"%1$s\" }";

    // @formatter:off
    private static final String DOCUMENT_CONTENT = "{" + System.lineSeparator()
        + "    \"json\": {" + System.lineSeparator()
        + "      \"version\": \"1.0\"," + System.lineSeparator()
        + "    \"encoding\": \"utf-8\"" + System.lineSeparator()
        + "  }," + System.lineSeparator()
        + "  \"content\": [%1$s]" + System.lineSeparator()
        + "}" + System.lineSeparator();
    // @formatter:on

    private RepresentationServiceTestsObjectService objectService = new RepresentationServiceTestsObjectService();

    private IProjectRepository projectRepository = new NoOpProjectRepository() {
        @Override
        public <S extends ProjectEntity> S save(S entity) {
            entity.setId(UUID.randomUUID());
            return entity;
        }
    };

    private IRepresentationRepository representationRepository = new NoOpRepresentationRepository() {
        private List<RepresentationEntity> entities = new ArrayList<>();

        @Override
        public <S extends RepresentationEntity> S save(S representationEntity) {
            this.entities.add(representationEntity);
            return representationEntity;
        }

        @Override
        public List<RepresentationEntity> findAllByProjectId(UUID projectId) {
            return this.entities;
        }

        @Override
        public void deleteById(UUID id) {
            this.entities.removeIf(entity -> Objects.equals(entity.getId(), id));
        }
    };

    private IDocumentRepository documentRepository = new NoOpDocumentRepository() {
        private List<DocumentEntity> entities = new ArrayList<>();

        @Override
        public <S extends DocumentEntity> S save(S entity) {
            this.entities.add(entity);
            return entity;
        }
    };

    private RepresentationService representationService = new RepresentationService(this.objectService, this.projectRepository, this.representationRepository, new ObjectMapper(), new SimpleMeterRegistry());

    @Test
    public void testDeleteDanglingRepresentations() {
        ProjectEntity projectEntity = this.createAndSaveProjectEntity();
        AdapterFactoryEditingDomain editingDomain = new EditingDomainFactory().create();
        IEditingContext editingContext = new EditingContext(projectEntity.getId().toString(), editingDomain, Map.of());
        UUID projectId = projectEntity.getId();
        String documentContent = this.createDocumentContent(FIRST_TARGET_OBJECT_ID, SECOND_TARGET_OBJECT_ID);
        this.objectService.addObject(FIRST_TARGET_OBJECT_ID);
        this.objectService.addObject(SECOND_TARGET_OBJECT_ID);
        DocumentEntity documentEntity = this.createAndSaveDocumentEntity(projectEntity, documentContent);

        RepresentationEntity firstRepresentationEntity = this.createRepresentationEntity(projectEntity, FIRST_DIAGRAM_LABEL, FIRST_TARGET_OBJECT_ID);
        RepresentationEntity secondRepresentationEntity = this.createRepresentationEntity(projectEntity, SECOND_DIAGRAM_LABEL, SECOND_TARGET_OBJECT_ID);
        RepresentationEntity thirdRepresentationEntity = this.createRepresentationEntity(projectEntity, THIRD_DIAGRAM_LABEL, SECOND_TARGET_OBJECT_ID);
        this.representationRepository.save(firstRepresentationEntity);
        this.representationRepository.save(secondRepresentationEntity);
        this.representationRepository.save(thirdRepresentationEntity);

        assertThat(this.findRepresentationUUIDsByProject(projectId)).contains(firstRepresentationEntity.getId(), secondRepresentationEntity.getId(), thirdRepresentationEntity.getId());

        this.representationService.deleteDanglingRepresentations(editingContext);
        assertThat(this.findRepresentationUUIDsByProject(projectId)).contains(firstRepresentationEntity.getId(), secondRepresentationEntity.getId(), thirdRepresentationEntity.getId());

        documentEntity.setContent(this.createDocumentContent(FIRST_TARGET_OBJECT_ID));
        this.objectService.removeObject(SECOND_TARGET_OBJECT_ID);

        this.documentRepository.save(documentEntity);
        this.representationService.deleteDanglingRepresentations(editingContext);

        List<UUID> representationUUIDs = this.findRepresentationUUIDsByProject(projectId);
        assertThat(representationUUIDs).hasSizeLessThanOrEqualTo(1);
        assertThat(representationUUIDs).contains(firstRepresentationEntity.getId());
        assertThat(representationUUIDs).doesNotContain(secondRepresentationEntity.getId(), thirdRepresentationEntity.getId());

    }

    private ProjectEntity createAndSaveProjectEntity() {
        ProjectEntity project = new ProjectEntity();
        project.setName(FIRST_PROJECT_NAME);
        ProjectEntity savedProject = this.projectRepository.save(project);
        return savedProject;
    }

    private DocumentEntity createAndSaveDocumentEntity(ProjectEntity projectEntity, String documentContent) {
        DocumentEntity documentEntity = new DocumentEntity();
        documentEntity.setName(DOCUMENT_NAME);
        documentEntity.setProject(projectEntity);
        documentEntity.setContent(documentContent);

        return this.documentRepository.save(documentEntity);
    }

    private String createDocumentContent(String... targetObjectIds) {
        //@formatter:off
        String documentContentContent = List.of(targetObjectIds).stream()
                .map(targetObjectId -> String.format(DOCUMENT_CONTENT_PATTERN, targetObjectId))
                .collect(Collectors.joining(","));
        //@formatter:on

        return String.format(DOCUMENT_CONTENT, documentContentContent);
    }

    private RepresentationEntity createRepresentationEntity(ProjectEntity projectEntity, String label, String targetObjectId) {
        RepresentationEntity representationEntity = new RepresentationEntity();
        representationEntity.setId(UUID.randomUUID());
        representationEntity.setLabel(label);
        representationEntity.setProject(projectEntity);
        representationEntity.setTargetObjectId(targetObjectId);
        representationEntity.setKind("siriusComponents://representation?type=Diagram");
        representationEntity.setDescriptionId(UUID.randomUUID().toString());
        representationEntity.setContent("{ \"nodes\": [], \"edges\": []}");
        return representationEntity;
    }

    private List<UUID> findRepresentationUUIDsByProject(UUID projectId) {
        // @formatter:off
        return this.representationRepository.findAllByProjectId(projectId).stream()
                .map(RepresentationEntity::getId)
                .toList();
        // @formatter:on
    }
}
