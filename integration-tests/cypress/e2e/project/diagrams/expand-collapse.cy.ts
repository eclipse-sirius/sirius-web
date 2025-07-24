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

import { Studio } from '../../../usecases/Studio';
import { Explorer } from '../../../workbench/Explorer';
import { Details } from '../../../workbench/Details';
import { Diagram } from '../../../workbench/Diagram';

describe('Diagram - expand collapse', () => {
  const domainName: string = 'diagramList';
  context('Given a view with a simple list node', () => {
    context('When we create a new instance project', () => {
      let instanceProjectId: string = '';
      const diagramDescriptionName = `${domainName} - simple list node`;
      const diagramTitle = 'Collapsable node';

      beforeEach(() => {
        const studio = new Studio();
        const explorer = new Explorer();
        const details = new Details();
        studio.createProjectFromDomain('Cypress - Studio Instance', domainName, 'Root').then((res) => {
          instanceProjectId = res.projectId;
        });
        new Diagram().disableFitView();

        explorer.createObject('Root', 'entity1s-Entity1');
        details.getTextField('Name').type('collapsable{enter}');
        new Explorer().createRepresentation('Root', diagramDescriptionName, diagramTitle);
        new Diagram().centerViewport();
      });

      afterEach(() => cy.deleteProject(instanceProjectId));

      it('Then the collapse/expand action hide/show child node', () => {
        const diagram = new Diagram();
        diagram.getNodes(diagramTitle, 'collapsable').should('exist');
        cy.get('.react-flow__node').should('have.length', 5);
        // workaround: the first right-click doesn't seem catch by Cypress, the second one does
        diagram.getNodes(diagramTitle, 'collapsable').should('exist').rightclick('topLeft').rightclick('topLeft');
        diagram.getPalette().should('exist');
        diagram.getPalette().findByTestId('Collapse - Tool').click();
        cy.get('.react-flow__node').should('have.length', 1);
        diagram.getNodes(diagramTitle, 'collapsable').should('exist').rightclick('topLeft').rightclick('topLeft');
        diagram.getPalette().should('exist');
        diagram.getPalette().findByTestId('Expand - Tool').click();
        cy.get('.react-flow__node').should('have.length', 5);
      });
    });
  });
});
