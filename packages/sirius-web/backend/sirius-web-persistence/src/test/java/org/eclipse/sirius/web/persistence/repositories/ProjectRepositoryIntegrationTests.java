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

import org.eclipse.sirius.web.persistence.entities.ProjectEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Integration tests of the project repository.
 *
 * @author sbegaudeau
 */
@Testcontainers
@SpringBootTest
@ContextConfiguration(classes = PersistenceTestConfiguration.class)
public class ProjectRepositoryIntegrationTests extends AbstractIntegrationTests {
    private static final String FIRST_PROJECT_NAME = "Star Durst";

    @Autowired
    private IProjectRepository projectRepository;

    @DynamicPropertySource
    public static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword);
        registry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername);
    }

    @Test
    @Transactional
    public void testInsertAndRetrieveAProject() {
        ProjectEntity project = new ProjectEntity();
        project.setName(FIRST_PROJECT_NAME);

        var savedProject = this.projectRepository.save(project);

        assertThat(savedProject.getId()).isNotNull();
        var optionalProjectFound = this.projectRepository.findById(savedProject.getId());
        assertThat(optionalProjectFound.isPresent()).isTrue();

        optionalProjectFound.ifPresent(projectFound -> {
            assertThat(projectFound.getId()).isNotNull();
            assertThat(projectFound.getName()).isEqualTo(FIRST_PROJECT_NAME);
        });
    }

    @Test
    @Transactional
    public void testInsertTwoProjectWithSameName() {
        ProjectEntity firstProjectEntity = new ProjectEntity();
        firstProjectEntity.setName(FIRST_PROJECT_NAME);
        ProjectEntity firstSavedProject = this.projectRepository.save(firstProjectEntity);

        ProjectEntity secondProjectEntity = new ProjectEntity();
        secondProjectEntity.setName(FIRST_PROJECT_NAME);
        ProjectEntity secondSavedProject = this.projectRepository.save(secondProjectEntity);

        assertThat(this.projectRepository.count()).isEqualTo(2);
        assertThat(firstSavedProject.getId()).isNotEqualTo(secondSavedProject.getId());
    }

    @Test
    @Transactional
    public void testDeleteAProject() {
        ProjectEntity project = new ProjectEntity();
        project.setName(FIRST_PROJECT_NAME);

        this.projectRepository.save(project);
        this.projectRepository.delete(project);
        assertThat(this.projectRepository.count()).isEqualTo(0);
    }

    @Test
    @Transactional
    public void testCreatePublicProject() {
        ProjectEntity project = new ProjectEntity();
        project.setName(FIRST_PROJECT_NAME);

        var savedProject = this.projectRepository.save(project);

        assertThat(savedProject.getId()).isNotNull();
        var optionalProjectFound = this.projectRepository.findById(savedProject.getId());
        assertThat(optionalProjectFound.isPresent()).isTrue();

        optionalProjectFound.ifPresent(projectFound -> {
            assertThat(projectFound.getId()).isNotNull();
            assertThat(projectFound.getName()).isEqualTo(FIRST_PROJECT_NAME);
        });
    }

}
