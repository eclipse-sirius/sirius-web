/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

describe('Diagram - Graphical-dnd', () => {
  const domainName: string = 'diagramDnD';
  context('Given a view with a simple dnd view', () => {
    context('When we create a new instance project', () => {
      let instanceProjectId: string = '';
      const diagramDescriptionName = `${domainName} - simple dnd view`;
      const diagramTitle = 'Simple dnd';

      beforeEach(() => {
        const studio = new Studio();
        const explorer = new Explorer();
        const details = new Details();
        studio.createProjectFromDomain('Cypress - Studio Instance', domainName, 'Root').then((res) => {
          instanceProjectId = res.projectId;
        });

        explorer.createObject('Root', 'entity1s-Entity1');
        details.getTextField('Name').type('Entity1{enter}');
        explorer.createObject('Entity1', 'entity2sOnEntity1-Entity2');
        details.getTextField('Name').should('have.value', '');
        details.getTextField('Name').type('Entity2{enter}');
        new Explorer().createRepresentation('Root', diagramDescriptionName, diagramTitle);
      });

      afterEach(() => cy.deleteProject(instanceProjectId));

      it('Then when moving node inside its parent no dnd is triggered', () => {
        const diagram = new Diagram();
        const explorer = new Explorer();
        diagram.fitToScreen();

        diagram.dragNode('.react-flow__node:last', { x: 1, y: 1 });
        // eslint-disable-next-line cypress/no-unnecessary-waiting
        cy.wait(500); //wait for tool execution
        explorer.select('Entity1');
        explorer.collapseWithDoubleClick('Entity1');
        explorer.getTreeItemByLabel('Entity2').should('not.exist');
        explorer.getTreeItemByLabel('DropPerformed').should('not.exist');
      });

      it('Then when moving node outside its parent dnd is triggered', () => {
        const diagram = new Diagram();
        const explorer = new Explorer();
        diagram.fitToScreen();

        diagram.dragNode('.react-flow__node:last', { x: 0, y: -200 });
        // eslint-disable-next-line cypress/no-unnecessary-waiting
        cy.wait(500); //wait for tool execution
        explorer.getTreeItemByLabel('dropPerformed').should('exist');
      });
    });
  });
});
