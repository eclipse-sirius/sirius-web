/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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

const projectName = 'Cypress - explorer';

describe('Explorer', () => {
  context('Given a flow project with a robot document', () => {
    let projectId: string = '';
    beforeEach(() =>
      new Flow().createRobotProject(projectName).then((createdProjectData) => {
        projectId = createdProjectData.projectId;
        new Project().visit(projectId);
      })
    );

    afterEach(() => cy.deleteProject(projectId));

    context('When we interact with the explorer', () => {
      it.skip('Then we can navigate through the explorer using keyboard arrows', () => {
        const explorer = new Explorer();
        explorer.expand('robot');
        explorer.getTreeItemByLabel('System').should('exist');

        explorer.expand('Robot');
        explorer.getTreeItemByLabel('Central_Unit').should('exist');
        explorer.getTreeItemByLabel('CaptureSubSystem').should('exist');
        explorer.getTreeItemByLabel('Wifi').should('exist');
      });
    });
  });
});
