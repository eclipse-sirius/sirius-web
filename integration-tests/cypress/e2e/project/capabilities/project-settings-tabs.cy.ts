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

import { ProjectSettings } from '../../../pages/ProjectSettings';
import { Flow } from '../../../usecases/Flow';

const projectName = 'Cypress - Disabled Settings Tabs Project';

describe('Project Settings Tabs', () => {
  context('Given a project with a specific name', () => {
    let projectId: string = '';
    beforeEach(() => {
      new Flow().createRobotProject(projectName).then((createProjectData) => {
        projectId = createProjectData.projectId;
      });
    });

    afterEach(() => cy.deleteProject(projectId));

    context('When we try to access the project settings tabs from the workbench', () => {
      beforeEach(() => new ProjectSettings().visit(projectId));

      it('Then the settings tabs should not exist', () => {
        cy.getByTestId('upload-image').should('not.exist');
        cy.contains('No setting pages found').should('be.visible');
      });
    });
  });
});
