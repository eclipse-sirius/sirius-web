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
import { Studio } from '../../../usecases/Studio';
import { Details } from '../../../workbench/Details';
import { Diagram } from '../../../workbench/Diagram';
import { Explorer } from '../../../workbench/Explorer';

describe('Diagram - list', () => {
  const domainName: string = 'diagramList';
  context('Given a view with a simple list node', () => {
    context('When we create a new instance project', () => {
      let instanceProjectId: string = '';
      const diagramDescriptionName = `${domainName} - simple list node`;
      const diagramTitle = 'Simple list node';

      beforeEach(() => {
        const studio = new Studio();
        studio.createProjectFromDomain('Cypress - Studio Instance', domainName, 'Root').then((res) => {
          instanceProjectId = res.projectId;
        });
      });

      afterEach(() => cy.deleteProject(instanceProjectId));

      it('Then the node is correctly displayed with the proper separator', () => {
        const explorer = new Explorer();
        const diagram = new Diagram();
        const details = new Details();
        explorer.createObject('Root', 'entity1s-Entity1');
        details.getTextField('Name').type('list{enter}');
        new Explorer().createRepresentation('Root', diagramDescriptionName, diagramTitle);
        diagram.fitToScreen();
        // Compartment list without label
        diagram
          .getDiagram(diagramTitle)
          .findByTestId('List - undefined')
          .should('exist')
          .invoke('attr', 'style')
          .then((style) => {
            expect(style).to.contain('border-width: 0px 0px 1px');
          });
        // Compartment freeform
        diagram
          .getDiagram(diagramTitle)
          .findByTestId('FreeForm - list')
          .should('exist')
          .invoke('css', 'border-width')
          .then((borderWidth) => {
            expect(borderWidth).to.eq('0px');
          });
      });
    });
  });
});
