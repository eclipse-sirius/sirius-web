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
import { Project } from '../../../pages/Project';
import { Flow } from '../../../usecases/Flow';
import { Diagram } from '../../../workbench/Diagram';
import { Explorer } from '../../../workbench/Explorer';

describe('Diagram - edges', () => {
  context('Given a flow project', () => {
    let projectId: string = '';
    beforeEach(() => {
      new Flow().createFlowProject().then((createdProjectData) => {
        projectId = createdProjectData.projectId;
        const project = new Project();
        project.visit(projectId);
        new Diagram().disableFitView();
        const explorer = new Explorer();
        explorer.expandWithDoubleClick('Flow');
        explorer.expandWithDoubleClick('NewSystem');
        explorer.selectRepresentation('Topography');
      });
    });

    afterEach(() => cy.deleteProject(projectId));

    it('Check bend points are available when selecting an edge', () => {
      const diagram = new Diagram();
      const explorer = new Explorer();
      explorer.expandWithDoubleClick('DataSource1');
      diagram.getNodes('Topography', 'DataSource1').should('exist');
      explorer.select('standard');
      diagram.pullWorkbenchSelection();
      cy.getByTestId('bend-point-0').should('exist');
      cy.getByTestId('bend-point-1').should('exist');
      cy.getByTestId('temporary-moving-line-1').should('exist');
    });

    it('Check no new bend point is created when simply clicking on a bendpoint', () => {
      const diagram = new Diagram();
      const explorer = new Explorer();
      explorer.expandWithDoubleClick('DataSource1');
      diagram.getNodes('Topography', 'DataSource1').should('exist');
      explorer.select('standard');
      diagram.pullWorkbenchSelection();
      cy.getByTestId('bend-point-2').should('exist');
      cy.getByTestId('bend-point-2').click();
      cy.getByTestId('bend-point-4').should('not.exist');
    });
  });
});
