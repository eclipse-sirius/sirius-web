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

import { Project } from '../../../pages/Project';
import { Flow } from '../../../usecases/Flow';
import { Explorer } from '../../../workbench/Explorer';

const projectName = 'Cypress - portal';

describe('/projects/:projectId/edit - Portal', () => {
  context('Given a flow project with a robot document', () => {
    let projectId: string = '';
    beforeEach(() =>
      new Flow().createRobotProject(projectName).then((createdProjectData) => {
        projectId = createdProjectData.projectId;
        new Project().visit(projectId);
      })
    );

    afterEach(() => cy.deleteProject(projectId));

    context('When we create a new portal using the contextual menu', () => {
      beforeEach(() => {
        const explorer = new Explorer();
        explorer.expand('robot');
        explorer.getTreeItemByLabel('Robot').should('exist');
        explorer.createRepresentation('Robot', 'Portal', 'Portal');
      });

      it('Then it reveals a newly created portal', () => {
        const explorer = new Explorer();
        explorer.getTreeItemByLabel('Central_Unit').should('exist');
        explorer.getTreeItemByLabel('CaptureSubSystem').should('exist');
        explorer.getTreeItemByLabel('Portal').should('exist');

        explorer.getSelectedTreeItems().should('have.length', 1);
        explorer.getSelectedTreeItems().contains('Portal').should('exist');
      });
    });
  });
});
