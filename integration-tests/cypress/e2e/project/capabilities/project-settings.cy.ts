/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import { Project } from '../../../pages/Project';
import { Flow } from '../../../usecases/Flow';

const projectName = 'Cypress - Disabled Settings Project';

describe('Project Settings', () => {
  context('Given a project with a specific name', () => {
    let projectId: string = '';
    beforeEach(() => {
      new Flow().createRobotProject(projectName).then((createProjectData) => {
        projectId = createProjectData.projectId;
      });
    });

    afterEach(() => cy.deleteProject(projectId));

    context('When we try to access the project from the workbench', () => {
      beforeEach(() => new Project().visit(projectId));

      it('Then the settings button should not exist', () => {
        new Project().getProjectNavigationBar(projectName).getSettingsButton().should('not.exist');
      });
    });
  });
});
