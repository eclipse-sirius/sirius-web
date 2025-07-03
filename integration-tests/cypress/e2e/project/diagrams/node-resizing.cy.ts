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

describe('Diagram - node resizing', () => {
  context('Given a flow project', () => {
    let projectId: string = '';
    beforeEach(() => {
      new Flow().createFlowProject().then((createdProjectData) => {
        projectId = createdProjectData.projectId;
        const project = new Project();
        project.visit(projectId);
        new Diagram().disableFitView();
        project.disableDeletionConfirmationDialog();
        const explorer = new Explorer();
        explorer.expandWithDoubleClick('Flow');
        explorer.expandWithDoubleClick('NewSystem');
        explorer.delete('DataSource1');
        explorer.expandWithDoubleClick('CompositeProcessor1');
        explorer.delete('Processor1');
        explorer.selectRepresentation('Topography');
      });
    });

    afterEach(() => cy.deleteProject(projectId));

    it('Then is possible to resize a node', () => {
      const diagram = new Diagram();
      diagram.getNodes('Topography', 'CompositeProcessor1').should('exist');
      diagram.selectNode('Topography', 'CompositeProcessor1');

      let initialLeft: number, initialTop: number, initialWidth: number, initialHeight: number;

      cy.get('.react-flow__node:first').then(($el) => {
        const rect = $el[0]?.getBoundingClientRect();
        initialLeft = rect?.left ?? 0;
        initialTop = rect?.top ?? 0;
        initialWidth = rect?.width ?? 0;
        initialHeight = rect?.height ?? 0;
      });

      diagram.resizeNode('bottom.right', { x: 25, y: 25 });

      cy.get('.react-flow__node:first').then(($el) => {
        const rect = $el[0]?.getBoundingClientRect();
        expect(rect?.left).to.equal(initialLeft);
        expect(rect?.top).to.equal(initialTop);
        expect(rect?.width).to.approximately(initialWidth + 25, 5);
        expect(rect?.height).to.approximately(initialHeight + 25, 5);
      });
    });
  });
});
