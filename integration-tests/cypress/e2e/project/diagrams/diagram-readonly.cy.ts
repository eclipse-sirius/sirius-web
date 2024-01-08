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
import { Diagram } from '../../../workbench/Diagram';
import { Explorer } from '../../../workbench/Explorer';
import { Portal } from '../../../workbench/Portal';

const projectName = 'Cypress - readonly diagram with portal';

describe('Diagram read-only', () => {
  const diagramTitle = 'diagram';
  context('Given a flow project with a robot document and a Topography diagram', () => {
    let projectId: string = '';
    beforeEach(() => {
      new Flow().createRobotProject(projectName).then((createdProjectData) => {
        projectId = createdProjectData.projectId;
        new Project().visit(projectId);
      });
      const explorer = new Explorer();
      explorer.expand('robot');
      explorer.expand('Robot');

      explorer.createRepresentation('Robot', 'Topography', diagramTitle);

      // Wait for the diagram to be render
      new Diagram().getNodes(diagramTitle, 'Wifi').should('exist');
    });

    afterEach(() => cy.deleteProject(projectId));

    context('When we add a topography diagram in a new portal', () => {
      beforeEach(() => {
        const explorer = new Explorer();
        explorer.getTreeItemByLabel('Robot').should('exist');
        explorer.createRepresentation('Robot', 'Portal', 'Portal');
        const portal = new Portal();
        portal.addRepresentationFromExplorer('Portal', diagramTitle);

        // Check that the diagram has been added
        portal.getFrame(diagramTitle).then((res) => {
          const frame = cy.wrap(res);
          frame.should('be.visible');
          frame
            .findByTestId('representation-frame-header')
            .find('[aria-label="remove"]')
            .should('be.visible')
            .should('be.enabled');
          frame.get('.react-resizable-handle').should('be.visible');
        });
      });

      it('Then the diagram is readonly', () => {
        const diagram = new Diagram();
        cy.getByTestId('arrange-all').should('exist').should('be.disabled');
        cy.getByTestId('reveal-hidden-elements').should('exist').should('be.disabled');
        cy.getByTestId('reveal-faded-elements').should('exist').should('be.disabled');
        cy.getByTestId('unpin-all-elements').should('exist').should('be.disabled');
        diagram.getPalette().should('not.exist');
        diagram.getNodes('diagram', 'Motion_Engine').should('exist').click('bottom');
        diagram.getSelectedNodes('diagram', 'Motion_Engine').should('exist');
        diagram.getPalette().should('not.exist');
      });
    });
  });
});
