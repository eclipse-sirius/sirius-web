/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.application.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Nature;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectUpdateService;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

/**
 * Used to test the manipulation of project nature.
 *
 * @author sbegaudeau
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectNatureTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IProjectSearchService projectSearchService;

    @Autowired
    private IProjectUpdateService projectUpdateService;

    @Test
    @DisplayName("Given a project, when a nature is added, then the new nature is visible")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenProjectWhenNatureIsAddedThenTheNewNatureIsVisible() {
        var optionalProject = this.projectSearchService.findById(TestIdentifiers.SYSML_SAMPLE_PROJECT);
        assertThat(optionalProject)
                .isPresent()
                .get()
                .satisfies(project -> assertThat(project.getNatures()).hasSize(1));

        var projectId = optionalProject.map(Project::getId).orElseThrow(IllegalStateException::new);
        this.projectUpdateService.addNature(projectId, "new nature");

        optionalProject = this.projectSearchService.findById(TestIdentifiers.SYSML_SAMPLE_PROJECT);
        assertThat(optionalProject)
                .isPresent()
                .get()
                .satisfies(project -> assertThat(project.getNatures()).hasSize(2));
    }

    @Test
    @DisplayName("Given a project, when a nature is removed, then the nature is not visible anymore")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenProjectWhenNatureIsRemovedThenTheNatureIsNotVisibleAnymore() {
        var optionalProject = this.projectSearchService.findById(TestIdentifiers.SYSML_SAMPLE_PROJECT);
        assertThat(optionalProject)
                .isPresent()
                .get()
                .satisfies(project -> assertThat(project.getNatures()).hasSize(1));

        var projectId = optionalProject.map(Project::getId).orElseThrow(IllegalStateException::new);
        var existingNature = optionalProject.flatMap(project -> project.getNatures().stream().map(Nature::name).findFirst())
                .orElseThrow(IllegalStateException::new);
        this.projectUpdateService.removeNature(projectId, existingNature);

        optionalProject = this.projectSearchService.findById(TestIdentifiers.SYSML_SAMPLE_PROJECT);
        assertThat(optionalProject)
                .isPresent()
                .get()
                .satisfies(project -> assertThat(project.getNatures()).hasSize(0));
    }
}
