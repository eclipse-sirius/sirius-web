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
import { Projects } from '../../../pages/Projects';
import { Flow } from '../../../usecases/Flow';

const projectName = 'Cypress - Disabled Download Project';

describe('Project download', () => {
  context('Given a project with a specific name', () => {
    let projectId: string = '';
    beforeEach(() => {
      new Flow().createRobotProject(projectName).then((createProjectData) => {
        projectId = createProjectData.projectId;
      });
    });

    afterEach(() => cy.deleteProject(projectId));

    context('When we try to download the project from the project browser view', () => {
      beforeEach(() => new Projects().visit());

      it('Then the download button should be disabled', () => {
        new Projects().getActionMenu(projectName).getDownloadLink().should('have.class', 'Mui-disabled');
      });
    });

    context('When we try to download the project from the workbench', () => {
      beforeEach(() => new Project().visit(projectId));

      it('Then the download button should be disabled', () => {
        new Project().getProjectNavigationBar(projectName).getDownloadLink().should('have.class', 'Mui-disabled');
      });
    });
  });
});
