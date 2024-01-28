/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
 * This program and the accompanying materials
 * are made available under the erms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/

import { Project } from '../../pages/Project';
import { Flow } from '../../usecases/Flow';
import { Details } from '../../workbench/Details';
import { Diagram } from '../../workbench/Diagram';
import { Explorer } from '../../workbench/Explorer';
import { Workbench } from '../../workbench/Workbench';

const projectName = 'Cypress - Representations lifecycle';

/**
 * This test suite will be used to validate the proper lifecycle of our application.
 *
 * For that, we will open and close in various ways several representations and we will evaluate if we have
 * the proper number of representations and if they behave properly.
 */
describe('Representations lifecycle', () => {
  context('Given a flow project with a robot document', () => {
    let projectId: string = '';
    beforeEach(() => {
      new Flow().createRobotProject(projectName).then((createdProjectData) => {
        projectId = createdProjectData.projectId;
        new Project().visit(projectId);
      });
    });

    afterEach(() => cy.deleteProject(projectId));

    it('Then we can open a tree, a diagram and then a form', () => {
      const explorer = new Explorer();
      explorer.expand('robot');
      explorer.createRepresentation('Robot', 'Topography', 'diagram');

      new Diagram().getNodes('diagram', 'Wifi').should('exist');
      explorer.getTreeItemByLabel('Robot').click();
    });

    it('Then we can open a tree then a form and switch objects', () => {
      const explorer = new Explorer();
      const details = new Details();

      explorer.expand('robot');
      explorer.getTreeItemByLabel('Robot').click();
      details.getDetailsView().contains('Robot').should('exist');

      explorer.expand('Robot');
      explorer.getTreeItemByLabel('Central_Unit').click();
      details.getDetailsView().contains('Central_Unit').should('exist');

      explorer.getTreeItemByLabel('CaptureSubSystem').click();
      details.getDetailsView().contains('CaptureSubSystem').should('exist');

      explorer.getTreeItemByLabel('Wifi').click();
      details.getDetailsView().contains('Wifi').should('exist');
    });

    it('Then we can open a diagram and switch diagrams', () => {
      const explorer = new Explorer();
      explorer.expand('robot');
      explorer.createRepresentation('Robot', 'Topography', 'Topography1');
      new Diagram().getNodes('Topography1', 'Wifi').should('exist');

      explorer.createRepresentation('Robot', 'Topography', 'Topography2');
      new Diagram().getNodes('Topography2', 'Wifi').should('exist');

      const workbench = new Workbench();
      workbench.showTab('Topography1');

      explorer.delete('Topography2');

      workbench.showTab('Topography2');
      cy.contains('The representation is not available anymore').should('exist');

      workbench.showTab('Topography1');
      new Diagram().getNodes('Topography1', 'Wifi').should('exist');
    });
  });
});
