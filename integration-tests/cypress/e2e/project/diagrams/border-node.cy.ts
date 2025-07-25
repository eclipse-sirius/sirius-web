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

describe('Diagram - border node', () => {
  const domainName: string = 'diagramBorderNode';
  context('Given a view with a simple border node', () => {
    context('When we create a new instance project', () => {
      let instanceProjectId: string = '';
      const diagramDescriptionName = `${domainName} - simple border`;
      const diagramTitle = 'Simple border';

      beforeEach(() => {
        const studio = new Studio();
        const explorer = new Explorer();
        const details = new Details();
        studio.createProjectFromDomain('Cypress - Studio Instance', domainName, 'Root').then((res) => {
          instanceProjectId = res.projectId;
        });
        new Diagram().disableFitView();

        explorer.createObject('Root', 'entity1s-Entity1');
        details.getTextField('Name').type('parent{enter}');
        new Explorer().createRepresentation('Root', diagramDescriptionName, diagramTitle);
        new Diagram().centerViewport();
      });

      afterEach(() => cy.deleteProject(instanceProjectId));

      it('Then during parent resize, the left border node position is updated', () => {
        const diagram = new Diagram();
        diagram.selectNode(diagramTitle, 'parent');
        let initialTransformX: number;

        // Get the initial transform value
        diagram
          .getNodes(diagramTitle, 'border')
          .invoke('attr', 'style')
          .then((style) => {
            // Extract the initial x translation value
            const transformMatch = style?.match(/translate\(([^,]+),/);
            initialTransformX = transformMatch ? parseInt(transformMatch[1] ?? '', 10) : 0;
          });

        // Perform the resize action
        diagram.resizeNodeFeedback('bottom.right', { x: -25, y: 0 });

        // Check the new transform value
        diagram
          .getNodes(diagramTitle, 'border')
          .invoke('attr', 'style')
          .then((style) => {
            // Extract the new x translation value
            const newTransformMatch = style?.match(/translate\(([^,]+),/);
            const newTransformX = newTransformMatch ? parseInt(newTransformMatch[1] ?? '', 10) : 0;

            // Verify that the translation has changed by 25px
            expect(newTransformX).to.eq(initialTransformX - 25);
          });
      });

      it('Then when selected, a border node should not be resizable', () => {
        const diagram = new Diagram();
        diagram.getNodes(diagramTitle, 'border').should('exist');
        diagram.selectNode(diagramTitle, 'border');
        cy.get('react-flow__resize-control nodrag top left handle').should('not.exist');
        cy.get('react-flow__resize-control nodrag top right handle').should('not.exist');
        cy.get('react-flow__resize-control nodrag bottom left handle').should('not.exist');
        cy.get('react-flow__resize-control nodrag bottom right handle').should('not.exist');
      });
    });
  });
});
