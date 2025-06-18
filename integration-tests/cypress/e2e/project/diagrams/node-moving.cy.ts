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
import { Project } from '../../../pages/Project';
import { Flow } from '../../../usecases/Flow';
import { Diagram } from '../../../workbench/Diagram';
import { Explorer } from '../../../workbench/Explorer';

describe('Diagram - node moving', () => {
  context('Given a flow project', () => {
    let projectId: string = '';
    beforeEach(() => {
      new Flow().createFlowProject().then((createdProjectData) => {
        projectId = createdProjectData.projectId;
        const project = new Project();
        project.visit(projectId);
        project.disableDeletionConfirmationDialog();
        const explorer = new Explorer();
        explorer.expandWithDoubleClick('Flow');
        explorer.expandWithDoubleClick('NewSystem');
        explorer.delete('CompositeProcessor1');
        explorer.selectRepresentation('Topography');
      });
    });

    afterEach(() => cy.deleteProject(projectId));

    it('Then it is possible to move a node', () => {
      const diagram = new Diagram();
      diagram.getNodes('Topography', 'DataSource1').should('exist');
      diagram.fitToScreen();

      let initialLeft: number, initialTop: number, initialWidth: number, initialHeight: number;

      cy.get('.react-flow__node:first').then(($el) => {
        const rect = $el[0]!.getBoundingClientRect();
        initialLeft = rect.left;
        initialTop = rect.top;
        initialWidth = rect.width;
        initialHeight = rect.height;
      });

      diagram.moveNode('.react-flow__node:first', { x: 50, y: 0 });

      cy.get('.react-flow__node:first').then(($el) => {
        const rect = $el[0]!.getBoundingClientRect();
        expect(rect.left).to.approximately(initialLeft + 50, 2);
        expect(rect.top).to.approximately(initialTop, 2);
        expect(rect.width).to.equal(initialWidth);
        expect(rect.height).to.equal(initialHeight);
      });
    });
  });
});
