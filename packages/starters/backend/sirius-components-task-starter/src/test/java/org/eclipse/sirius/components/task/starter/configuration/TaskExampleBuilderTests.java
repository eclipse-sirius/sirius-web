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
package org.eclipse.sirius.components.task.starter.configuration;


import static org.assertj.core.api.Assertions.assertThat;

import org.eclipse.sirius.components.task.Company;
import org.eclipse.sirius.components.task.TaskPackage;
import org.junit.jupiter.api.Test;

/**
 * Test used to validate the template of task model create by TaskExampleBuilder.
 *
 * @author frouene
 */
class TaskExampleBuilderTests {

    @Test
    public void testExampleContent() {
        var content = new TaskExampleBuilder().getContent();

        assertThat(content).isNotNull();
        assertThat(content.eClass()).isEqualTo(TaskPackage.eINSTANCE.getCompany());
        Company company = (Company) content;
        assertThat(company.getOwnedProjects()).hasSize(4);
        assertThat(company.getOwnedProjects()).anySatisfy(project -> assertThat(project.getName()).isEqualTo("Project Dev"));
        assertThat(company.getOwnedProjects()).anySatisfy(project -> assertThat(project.getName()).isEqualTo("Daily Project Dev"));
        assertThat(company.getOwnedProjects()).anySatisfy(project -> assertThat(project.getName()).isEqualTo("Kanban Project Dev"));
        assertThat(company.getOwnedProjects()).anySatisfy(project -> assertThat(project.getName()).isEqualTo("OKR Project Dev"));
    }
}
