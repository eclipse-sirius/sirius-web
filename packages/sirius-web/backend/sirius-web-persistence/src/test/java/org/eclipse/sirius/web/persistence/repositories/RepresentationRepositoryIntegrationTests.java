/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.web.persistence.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.eclipse.sirius.web.persistence.entities.AccountEntity;
import org.eclipse.sirius.web.persistence.entities.DocumentEntity;
import org.eclipse.sirius.web.persistence.entities.ProjectEntity;
import org.eclipse.sirius.web.persistence.entities.RepresentationEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Integration tests of the representation repository.
 *
 * @author sbegaudeau
 */
@Testcontainers
@SpringBootTest
@ContextConfiguration(classes = PersistenceTestConfiguration.class)
public class RepresentationRepositoryIntegrationTests extends AbstractIntegrationTests {

    private static final String DOCUMENT_NAME = "Obsydians"; //$NON-NLS-1$

    private static final String OWNER_NAME = "Jyn Erso"; //$NON-NLS-1$

    private static final String ROLE_USER = "user"; //$NON-NLS-1$

    private static final String FIRST_PROJECT_NAME = "Cluster Prism"; //$NON-NLS-1$

    private static final String SECOND_PROJECT_NAME = "War Mantle"; //$NON-NLS-1$

    private static final String FIRST_DIAGRAM_LABEL = "First Diagram"; //$NON-NLS-1$

    private static final String SECOND_DIAGRAM_LABEL = "Second Diagram"; //$NON-NLS-1$

    private static final String THIRD_DIAGRAM_LABEL = "Third Diagram"; //$NON-NLS-1$

    private static final String FOURTH_DIAGRAM_LABEL = "Fourth Diagram"; //$NON-NLS-1$

    private static final String FIRST_TARGET_OBJECT_ID = "firstTargetObjectId"; //$NON-NLS-1$

    private static final String SECOND_TARGET_OBJECT_ID = "secondTargetObjectId"; //$NON-NLS-1$

    private static final String DOCUMENT_CONTENT_PATTERN = "{ \"id\": \"%1$s\" }"; //$NON-NLS-1$

    // @formatter:off
    private static final String DOCUMENT_CONTENT = "{" + System.lineSeparator() //$NON-NLS-1$
    + "    \"json\": {" + System.lineSeparator() //$NON-NLS-1$
    + "      \"version\": \"1.0\"," + System.lineSeparator() //$NON-NLS-1$
    + "    \"encoding\": \"utf-8\"" + System.lineSeparator() //$NON-NLS-1$
    + "  }," + System.lineSeparator() //$NON-NLS-1$
    + "  \"content\": [%1$s]" + System.lineSeparator() //$NON-NLS-1$
    + "}" + System.lineSeparator(); //$NON-NLS-1$
    // @formatter:on

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private IProjectRepository projectRepository;

    @Autowired
    private IRepresentationRepository representationRepository;

    @Autowired
    private IDocumentRepository documentRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @DynamicPropertySource
    public static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl); //$NON-NLS-1$
        registry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword); //$NON-NLS-1$
        registry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername); //$NON-NLS-1$
    }

    @Test
    @Transactional
    public void testInsertAndRetrieveARepresentation() {
        ProjectEntity savedProject = this.createAndSaveProjectEntity();

        RepresentationEntity representationEntity = this.createRepresentationEntity(savedProject, FIRST_DIAGRAM_LABEL, FIRST_TARGET_OBJECT_ID);
        RepresentationEntity savedRepresentation = this.representationRepository.save(representationEntity);

        assertThat(savedRepresentation.getId()).isNotNull();
        var optionalRepresentationFound = this.representationRepository.findById(savedRepresentation.getId());
        assertThat(optionalRepresentationFound.isPresent()).isTrue();

        optionalRepresentationFound.ifPresent(representationFound -> {
            assertThat(representationFound.getLabel()).isEqualTo(representationEntity.getLabel());
            assertThat(representationFound.getTargetObjectId()).isEqualTo(representationEntity.getTargetObjectId());
            assertThat(representationFound.getContent()).isEqualTo(representationEntity.getContent());
            assertThat(representationFound.getKind()).isEqualTo(representationEntity.getKind());
        });
    }

    @Test
    @Transactional
    public void testFindByIdAndProjectId() {
        AccountEntity owner = new AccountEntity();
        owner.setUsername(OWNER_NAME);
        owner.setPassword(OWNER_NAME);
        owner.setRole(ROLE_USER);
        AccountEntity savedOwner = this.accountRepository.save(owner);

        ProjectEntity firstProject = new ProjectEntity();
        firstProject.setName(FIRST_PROJECT_NAME);
        firstProject.setOwner(savedOwner);
        ProjectEntity savedFirstProject = this.projectRepository.save(firstProject);

        ProjectEntity secondProject = new ProjectEntity();
        secondProject.setName(SECOND_PROJECT_NAME);
        secondProject.setOwner(savedOwner);
        ProjectEntity savedSecondProject = this.projectRepository.save(secondProject);

        RepresentationEntity firstRepresentationEntity = this.createRepresentationEntity(savedFirstProject, FIRST_DIAGRAM_LABEL, FIRST_TARGET_OBJECT_ID);
        this.representationRepository.save(firstRepresentationEntity);
        RepresentationEntity secondRepresentationEntity = this.createRepresentationEntity(savedSecondProject, SECOND_DIAGRAM_LABEL, FIRST_TARGET_OBJECT_ID);
        this.representationRepository.save(secondRepresentationEntity);

        var optionalFirstRepresentationEntity = this.representationRepository.findByIdAndProjectId(firstRepresentationEntity.getId(), firstProject.getId());
        var optionalNotFoundFirstRepresentationEntity = this.representationRepository.findByIdAndProjectId(firstRepresentationEntity.getId(), secondProject.getId());
        var optionalSecondRepresentationEntity = this.representationRepository.findByIdAndProjectId(secondRepresentationEntity.getId(), secondProject.getId());
        var optionalNotFoundSecondRepresentationEntity = this.representationRepository.findByIdAndProjectId(secondRepresentationEntity.getId(), firstProject.getId());

        assertThat(optionalFirstRepresentationEntity).isPresent();
        assertThat(optionalSecondRepresentationEntity).isPresent();
        assertThat(optionalNotFoundFirstRepresentationEntity).isEmpty();
        assertThat(optionalNotFoundSecondRepresentationEntity).isEmpty();
    }

    @Test
    @Transactional
    public void testFindAllByProjectId() {
        ProjectEntity savedProject = this.createAndSaveProjectEntity();

        RepresentationEntity firstRepresentationEntity = this.createRepresentationEntity(savedProject, FIRST_DIAGRAM_LABEL, FIRST_TARGET_OBJECT_ID);
        RepresentationEntity secondRepresentationEntity = this.createRepresentationEntity(savedProject, SECOND_DIAGRAM_LABEL, FIRST_TARGET_OBJECT_ID);

        this.representationRepository.save(firstRepresentationEntity);
        this.representationRepository.save(secondRepresentationEntity);

        List<RepresentationEntity> representationEntities = this.representationRepository.findAllByProjectId(savedProject.getId());
        assertThat(representationEntities).hasSize(2);

    }

    @Test
    @Transactional
    public void testFindAllByTargetObjectId() {
        ProjectEntity savedProject = this.createAndSaveProjectEntity();

        RepresentationEntity firstRepresentationEntity = this.createRepresentationEntity(savedProject, FIRST_DIAGRAM_LABEL, FIRST_TARGET_OBJECT_ID);
        RepresentationEntity secondRepresentationEntity = this.createRepresentationEntity(savedProject, SECOND_DIAGRAM_LABEL, FIRST_TARGET_OBJECT_ID);
        RepresentationEntity thirdRepresentationEntity = this.createRepresentationEntity(savedProject, THIRD_DIAGRAM_LABEL, FIRST_TARGET_OBJECT_ID);
        RepresentationEntity fourthRepresentationEntity = this.createRepresentationEntity(savedProject, FOURTH_DIAGRAM_LABEL, SECOND_TARGET_OBJECT_ID);

        this.representationRepository.save(firstRepresentationEntity);
        this.representationRepository.save(secondRepresentationEntity);
        this.representationRepository.save(thirdRepresentationEntity);
        this.representationRepository.save(fourthRepresentationEntity);

        List<RepresentationEntity> representationEntitiesForFirstObject = this.representationRepository.findAllByTargetObjectId(FIRST_TARGET_OBJECT_ID);
        assertThat(representationEntitiesForFirstObject).hasSize(3);

        List<RepresentationEntity> representationEntitiesForSecondObject = this.representationRepository.findAllByTargetObjectId(SECOND_TARGET_OBJECT_ID);
        assertThat(representationEntitiesForSecondObject).hasSize(1);
    }

    @Test
    @Transactional
    public void testHasRepresentations() {
        ProjectEntity savedProject = this.createAndSaveProjectEntity();

        RepresentationEntity firstRepresentationEntity = this.createRepresentationEntity(savedProject, FIRST_DIAGRAM_LABEL, FIRST_TARGET_OBJECT_ID);
        RepresentationEntity secondRepresentationEntity = this.createRepresentationEntity(savedProject, SECOND_DIAGRAM_LABEL, FIRST_TARGET_OBJECT_ID);
        RepresentationEntity thirdRepresentationEntity = this.createRepresentationEntity(savedProject, THIRD_DIAGRAM_LABEL, FIRST_TARGET_OBJECT_ID);

        this.representationRepository.save(firstRepresentationEntity);
        this.representationRepository.save(secondRepresentationEntity);
        this.representationRepository.save(thirdRepresentationEntity);

        boolean firstObjectHasRepresentations = this.representationRepository.hasRepresentations(FIRST_TARGET_OBJECT_ID);
        assertThat(firstObjectHasRepresentations).isTrue();

        boolean secondObjectHasRepresentations = this.representationRepository.hasRepresentations(SECOND_TARGET_OBJECT_ID);
        assertThat(secondObjectHasRepresentations).isFalse();
    }

    @Test
    @Transactional
    public void testDeleteRepresentations() {
        ProjectEntity savedProject = this.createAndSaveProjectEntity();

        RepresentationEntity firstRepresentationEntity = this.createRepresentationEntity(savedProject, FIRST_DIAGRAM_LABEL, FIRST_TARGET_OBJECT_ID);
        RepresentationEntity secondRepresentationEntity = this.createRepresentationEntity(savedProject, SECOND_DIAGRAM_LABEL, FIRST_TARGET_OBJECT_ID);
        RepresentationEntity thirdRepresentationEntity = this.createRepresentationEntity(savedProject, THIRD_DIAGRAM_LABEL, FIRST_TARGET_OBJECT_ID);

        firstRepresentationEntity = this.representationRepository.save(firstRepresentationEntity);
        this.representationRepository.save(secondRepresentationEntity);
        this.representationRepository.save(thirdRepresentationEntity);
        assertThat(this.representationRepository.count()).isEqualTo(3);

        this.representationRepository.deleteById(firstRepresentationEntity.getId());
        assertThat(this.representationRepository.count()).isEqualTo(2);

        this.representationRepository.deleteAll();
        assertThat(this.representationRepository.count()).isEqualTo(0);
    }

    private ProjectEntity createAndSaveProjectEntity() {
        AccountEntity owner = new AccountEntity();
        owner.setUsername(OWNER_NAME);
        owner.setPassword(OWNER_NAME);
        owner.setRole(ROLE_USER);
        AccountEntity savedOwner = this.accountRepository.save(owner);

        ProjectEntity project = new ProjectEntity();
        project.setName(FIRST_PROJECT_NAME);
        project.setOwner(savedOwner);
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
                .collect(Collectors.joining(",")); //$NON-NLS-1$
        //@formatter:on

        return String.format(DOCUMENT_CONTENT, documentContentContent);
    }

    private RepresentationEntity createRepresentationEntity(ProjectEntity projectEntity, String label, String targetObjectId) {
        RepresentationEntity representationEntity = new RepresentationEntity();
        representationEntity.setId(UUID.randomUUID());
        representationEntity.setLabel(label);
        representationEntity.setProject(projectEntity);
        representationEntity.setTargetObjectId(targetObjectId);
        representationEntity.setKind("siriusComponents://representation?type=Diagram"); //$NON-NLS-1$
        representationEntity.setDescriptionId(UUID.randomUUID().toString());
        representationEntity.setContent("{ \"nodes\": [], \"edges\": []}"); //$NON-NLS-1$
        return representationEntity;
    }

    @Test
    @Transactional
    public void testCascadingOnDeleteProject() {
        ProjectEntity project = this.createAndSaveProjectEntity();
        RepresentationEntity firstRepresentationEntity = this.createRepresentationEntity(project, FIRST_DIAGRAM_LABEL, FIRST_TARGET_OBJECT_ID);
        this.representationRepository.save(firstRepresentationEntity);

        this.projectRepository.delete(project);
        assertThat(this.representationRepository.count()).isEqualTo(0);

    }

    @Test
    @Transactional
    public void testDeleteDanglingRepresentations() {
        ProjectEntity projectEntity = this.createAndSaveProjectEntity();
        UUID projectId = projectEntity.getId();
        String documentContent = this.createDocumentContent(FIRST_TARGET_OBJECT_ID, SECOND_TARGET_OBJECT_ID);
        DocumentEntity documentEntity = this.createAndSaveDocumentEntity(projectEntity, documentContent);

        RepresentationEntity firstRepresentationEntity = this.createRepresentationEntity(projectEntity, FIRST_DIAGRAM_LABEL, FIRST_TARGET_OBJECT_ID);
        RepresentationEntity secondRepresentationEntity = this.createRepresentationEntity(projectEntity, SECOND_DIAGRAM_LABEL, SECOND_TARGET_OBJECT_ID);
        RepresentationEntity thirdRepresentationEntity = this.createRepresentationEntity(projectEntity, THIRD_DIAGRAM_LABEL, SECOND_TARGET_OBJECT_ID);
        this.representationRepository.save(firstRepresentationEntity);
        this.representationRepository.save(secondRepresentationEntity);
        this.representationRepository.save(thirdRepresentationEntity);

        assertThat(this.findRepresentationUUIDsByProject(projectId)).contains(firstRepresentationEntity.getId(), secondRepresentationEntity.getId(), thirdRepresentationEntity.getId());

        this.representationRepository.deleteDanglingRepresentations(projectId);
        assertThat(this.findRepresentationUUIDsByProject(projectId)).contains(firstRepresentationEntity.getId(), secondRepresentationEntity.getId(), thirdRepresentationEntity.getId());

        documentEntity.setContent(this.createDocumentContent(FIRST_TARGET_OBJECT_ID));
        this.documentRepository.save(documentEntity);
        this.representationRepository.deleteDanglingRepresentations(projectEntity.getId());

        List<UUID> representationUUIDs = this.findRepresentationUUIDsByProject(projectId);
        assertThat(representationUUIDs).hasSizeLessThanOrEqualTo(1);
        assertThat(representationUUIDs).contains(firstRepresentationEntity.getId());
        assertThat(representationUUIDs).doesNotContain(secondRepresentationEntity.getId(), thirdRepresentationEntity.getId());

    }

    private List<UUID> findRepresentationUUIDsByProject(UUID projectId) {
        // @formatter:off
        return this.representationRepository.findAllByProjectId(projectId).stream()
                .map(RepresentationEntity::getId)
                .collect(Collectors.toList());
        // @formatter:on
    }

}
