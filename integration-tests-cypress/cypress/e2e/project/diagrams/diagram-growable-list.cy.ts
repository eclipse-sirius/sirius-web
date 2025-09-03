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
import { Details } from '../../../workbench/Details';
import { Diagram } from '../../../workbench/Diagram';
import { Explorer } from '../../../workbench/Explorer';

describe('Diagram - growable list', () => {
  const domainName: string = 'diagramGrowableList';
  context('Given a view with multiple growbale list nodes', () => {
    context('When we create a new instance project', () => {
      let instanceProjectId: string = '';
      const diagramDescriptionName = `${domainName} - multiple growable list nodes`;
      const diagramTitle = 'Multiple growable list nodes';

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
      });

      afterEach(() => cy.deleteProject(instanceProjectId));

      it('Then child node are correctly initialized to respect growable nodes', () => {
        const diagram = new Diagram();
        diagram
          .getNodes(diagramTitle, 'List 1')
          .should('exist')
          .invoke('css', 'height')
          .then((nodeHeight) => {
            expect(parseFloat(nodeHeight.toString())).to.approximately(176, 5);
          });
        diagram
          .getNodes(diagramTitle, 'SubList A')
          .should('exist')
          .invoke('css', 'height')
          .then((nodeHeight) => {
            expect(parseFloat(nodeHeight.toString())).to.approximately(70, 5);
          });
        diagram
          .getNodes(diagramTitle, 'SubList B')
          .should('exist')
          .invoke('css', 'height')
          .then((nodeHeight) => {
            expect(parseFloat(nodeHeight.toString())).to.approximately(70, 5);
          });
        diagram
          .getNodes(diagramTitle, 'List 2')
          .should('exist')
          .invoke('css', 'height')
          .then((nodeHeight) => {
            expect(parseFloat(nodeHeight.toString())).to.approximately(70, 5);
          });
        diagram
          .getNodes(diagramTitle, 'List 3')
          .should('exist')
          .invoke('css', 'height')
          .then((nodeHeight) => {
            expect(parseFloat(nodeHeight.toString())).to.approximately(164, 5);
          });
      });
    });
  });
});
