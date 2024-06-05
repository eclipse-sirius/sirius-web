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
import { Explorer } from '../../../workbench/Explorer';
import { Diagram } from '../../../workbench/Diagram';

describe('arrange all - Diagram', () => {
  context('Given a flow project', () => {
    let projectId: string = '';
    beforeEach(() => {
      new Flow().createFlowProject().then((createdProjectData) => {
        projectId = createdProjectData.projectId;
        const project = new Project();
        project.visit(projectId);
        project.disableDeletionConfirmationDialog();
        const explorer = new Explorer();
        explorer.expand('FlowNewModel');
        explorer.expand('NewSystem');
        explorer.select('Topography');
      });
    });

    afterEach(() => cy.deleteProject(projectId));

    it('Check arrange all do not marked node as resizedByUser', () => {
      const diagram = new Diagram();
      const explorer = new Explorer();
      explorer.expand('CompositeProcessor1');
      explorer.rename('Processor1', 'CPU');
      diagram.getNodes('Topography', 'CPU').should('exist');
      diagram.arrangeAll();
      let nodeHeight: number;
      diagram.getNodes('Topography', 'CompositeProcessor1').then(($node) => {
        nodeHeight = $node.height() ?? 0;
      });
      explorer.delete('CPU');
      diagram.getNodes('Topography', 'CPU').should('not.exist');
      //CompositeProcessor should be resized to its empty size
      diagram.getNodes('Topography', 'CompositeProcessor1').then(($node) => {
        expect(nodeHeight).to.be.greaterThan(Math.trunc($node.height() ?? 0));
      });
    });

    it('Check loading indicator is present during arrange action', () => {
      cy.getByTestId('arrange-all').click();
      cy.getByTestId('arrange-all-circular-loading').should('exist');
    });
  });
});
