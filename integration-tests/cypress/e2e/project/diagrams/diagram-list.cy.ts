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
import { Explorer } from '../../../workbench/Explorer';
import { Details } from '../../../workbench/Details';

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
        const details = new Details();
        explorer.createObject('Root', 'entity1s-Entity1');
        details.getTextField('Name').type('list{enter}');
        new Explorer().createRepresentation('Root', diagramDescriptionName, diagramTitle);
        // Compartment list without label
        cy.getByTestId('List - undefined').should('exist');
        cy.getByTestId('List - undefined')
          .invoke('css', 'border-width')
          .then((borderWidth) => {
            expect(borderWidth).to.eq('0px 0px 1px');
          });
        // Compartment freeform
        cy.getByTestId('FreeForm - list').should('exist');
        cy.getByTestId('FreeForm - list')
          .invoke('css', 'border-width')
          .then((borderWidth) => {
            expect(borderWidth).to.eq('0px');
          });
      });
    });
  });
});
