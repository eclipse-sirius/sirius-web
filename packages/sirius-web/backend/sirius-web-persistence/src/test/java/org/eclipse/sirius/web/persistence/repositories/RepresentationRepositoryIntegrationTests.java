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
package org.eclipse.sirius.web.persistence.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

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

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * Integration tests of the representation repository.
 *
 * @author sbegaudeau
 */
@Testcontainers
@SpringBootTest
@ContextConfiguration(classes = PersistenceTestConfiguration.class)
public class RepresentationRepositoryIntegrationTests extends AbstractIntegrationTests {

    private static final String FIRST_PROJECT_NAME = "Cluster Prism";

    private static final String SECOND_PROJECT_NAME = "War Mantle";

    private static final String FIRST_DIAGRAM_LABEL = "First Diagram";

    private static final String SECOND_DIAGRAM_LABEL = "Second Diagram";

    private static final String THIRD_DIAGRAM_LABEL = "Third Diagram";

    private static final String FOURTH_DIAGRAM_LABEL = "Fourth Diagram";

    private static final String FIRST_TARGET_OBJECT_ID = "firstTargetObjectId";

    private static final String SECOND_TARGET_OBJECT_ID = "secondTargetObjectId";

    @Autowired
    private IProjectRepository projectRepository;

    @Autowired
    private IRepresentationRepository representationRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @DynamicPropertySource
    public static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword);
        registry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername);
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
        ProjectEntity firstProject = new ProjectEntity();
        firstProject.setName(FIRST_PROJECT_NAME);
        ProjectEntity savedFirstProject = this.projectRepository.save(firstProject);

        ProjectEntity secondProject = new ProjectEntity();
        secondProject.setName(SECOND_PROJECT_NAME);
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
        ProjectEntity project = new ProjectEntity();
        project.setName(FIRST_PROJECT_NAME);
        ProjectEntity savedProject = this.projectRepository.save(project);
        return savedProject;
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

    @Test
    @Transactional
    public void testCascadingOnDeleteProject() {
        ProjectEntity project = this.createAndSaveProjectEntity();
        RepresentationEntity firstRepresentationEntity = this.createRepresentationEntity(project, FIRST_DIAGRAM_LABEL, FIRST_TARGET_OBJECT_ID);
        this.representationRepository.save(firstRepresentationEntity);

        this.projectRepository.delete(project);
        assertThat(this.representationRepository.count()).isEqualTo(0);

    }
}
