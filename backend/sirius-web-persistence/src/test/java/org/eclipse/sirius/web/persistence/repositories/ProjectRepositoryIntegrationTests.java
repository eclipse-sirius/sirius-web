/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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

import org.eclipse.sirius.web.persistence.entities.AccountEntity;
import org.eclipse.sirius.web.persistence.entities.ProjectEntity;
import org.eclipse.sirius.web.persistence.entities.VisibilityEntity;
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

    private static final String FIRST_OWNER_NAME = "Jyn Erso"; //$NON-NLS-1$

    private static final String SECOND_OWNER_NAME = "Orson Krennic"; //$NON-NLS-1$

    private static final String PASSWORD = "password"; //$NON-NLS-1$

    private static final String ROLE_USER = "user"; //$NON-NLS-1$

    private static final String FIRST_PROJECT_NAME = "Star Durst"; //$NON-NLS-1$

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private IProjectRepository projectRepository;

    @DynamicPropertySource
    public static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl); //$NON-NLS-1$
        registry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword); //$NON-NLS-1$
        registry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername); //$NON-NLS-1$
    }

    @Test
    @Transactional
    public void testInsertAndRetrieveAProject() {
        AccountEntity owner = this.createAndSaveUser(FIRST_OWNER_NAME);

        ProjectEntity project = new ProjectEntity();
        project.setName(FIRST_PROJECT_NAME);
        project.setOwner(owner);

        var savedProject = this.projectRepository.save(project);

        assertThat(savedProject.getId()).isNotNull();
        var optionalProjectFound = this.projectRepository.findById(savedProject.getId());
        assertThat(optionalProjectFound.isPresent()).isTrue();

        optionalProjectFound.ifPresent(projectFound -> {
            assertThat(projectFound.getId()).isNotNull();
            assertThat(projectFound.getName()).isEqualTo(FIRST_PROJECT_NAME);
            assertThat(projectFound.getOwner()).isNotNull();
            assertThat(projectFound.getOwner().getUsername()).isEqualTo(FIRST_OWNER_NAME);
            assertThat(projectFound.getOwner().getRole()).isEqualTo(ROLE_USER);
        });
    }

    @Test
    @Transactional
    public void testInsertTwoProjectWithSameName() {
        AccountEntity owner = this.createAndSaveUser(FIRST_OWNER_NAME);

        ProjectEntity firstProjectEntity = new ProjectEntity();
        firstProjectEntity.setName(FIRST_PROJECT_NAME);
        firstProjectEntity.setOwner(owner);
        ProjectEntity firstSavedProject = this.projectRepository.save(firstProjectEntity);

        ProjectEntity secondProjectEntity = new ProjectEntity();
        secondProjectEntity.setName(FIRST_PROJECT_NAME);
        secondProjectEntity.setOwner(owner);
        ProjectEntity secondSavedProject = this.projectRepository.save(secondProjectEntity);

        assertThat(this.projectRepository.count()).isEqualTo(2);
        assertThat(firstSavedProject.getId()).isNotEqualTo(secondSavedProject.getId());
    }

    @Test
    @Transactional
    public void testDeleteAProject() {
        ProjectEntity project = new ProjectEntity();
        project.setName(FIRST_PROJECT_NAME);
        project.setOwner(this.createAndSaveUser(FIRST_OWNER_NAME));
        project.setVisibility(VisibilityEntity.PRIVATE);

        this.projectRepository.save(project);
        this.projectRepository.delete(project);
        assertThat(this.projectRepository.count()).isEqualTo(0);
    }

    @Test
    @Transactional
    public void testDefaultProjectVisibilityIsPublic() {
        AccountEntity owner = this.createAndSaveUser(FIRST_OWNER_NAME);

        ProjectEntity project = new ProjectEntity();
        project.setName(FIRST_PROJECT_NAME);
        project.setOwner(owner);
        project.setVisibility(VisibilityEntity.PUBLIC);

        var savedProject = this.projectRepository.save(project);

        assertThat(savedProject.getId()).isNotNull();
        var optionalProjectFound = this.projectRepository.findById(savedProject.getId());
        assertThat(optionalProjectFound.isPresent()).isTrue();

        optionalProjectFound.ifPresent(projectFound -> {
            assertThat(projectFound.getId()).isNotNull();
            assertThat(projectFound.getName()).isEqualTo(FIRST_PROJECT_NAME);
            assertThat(projectFound.getVisibility()).isSameAs(VisibilityEntity.PUBLIC);
        });
    }

    @Test
    @Transactional
    public void testCreatePublicProject() {
        ProjectEntity project = new ProjectEntity();
        project.setName(FIRST_PROJECT_NAME);
        project.setOwner(this.createAndSaveUser(FIRST_OWNER_NAME));
        project.setVisibility(VisibilityEntity.PUBLIC);

        var savedProject = this.projectRepository.save(project);

        assertThat(savedProject.getId()).isNotNull();
        var optionalProjectFound = this.projectRepository.findById(savedProject.getId());
        assertThat(optionalProjectFound.isPresent()).isTrue();

        optionalProjectFound.ifPresent(projectFound -> {
            assertThat(projectFound.getId()).isNotNull();
            assertThat(projectFound.getName()).isEqualTo(FIRST_PROJECT_NAME);
            assertThat(projectFound.getVisibility()).isSameAs(VisibilityEntity.PUBLIC);
        });
    }

    @Test
    @Transactional
    public void testIsOwner() {
        AccountEntity firstAccountEntity = this.createAndSaveUser(FIRST_OWNER_NAME);
        AccountEntity secondAccountEntity = this.createAndSaveUser(SECOND_OWNER_NAME);

        ProjectEntity project = new ProjectEntity();
        project.setName(FIRST_PROJECT_NAME);
        project.setOwner(firstAccountEntity);
        project.setVisibility(VisibilityEntity.PUBLIC);

        project = this.projectRepository.save(project);
        assertThat(this.projectRepository.isOwner(firstAccountEntity.getUsername(), project.getId())).isTrue();
        assertThat(this.projectRepository.isOwner(secondAccountEntity.getUsername(), project.getId())).isFalse();
    }

    private AccountEntity createAndSaveUser(String username) {
        AccountEntity owner = new AccountEntity();
        owner.setUsername(username);
        owner.setPassword(PASSWORD);
        owner.setRole(ROLE_USER);
        AccountEntity savedOwner = this.accountRepository.save(owner);
        return savedOwner;
    }
}
