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

const projectName = 'Cypress - explorer-dnd';

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

    context('When we drop tree item in the explorer', () => {
      it('Then the object are moved', () => {
        const explorer = new Explorer();
        explorer.expand('robot');
        explorer.expand('System');
        explorer.expand('Central_Unit');
        explorer.getTreeItemByLabel('Radar').should('not.exist');

        explorer.expand('CompositeProcessor');
        const dataTransfer = new DataTransfer();
        explorer.dragTreeItem('Radar', dataTransfer);
        explorer.dopOnTreeItem('Central_Unit', dataTransfer);
        explorer.collapse('CompositeProcessor');
        
        explorer.getTreeItemByLabel('Radar').should('exist');
      });
    });
  });
});
