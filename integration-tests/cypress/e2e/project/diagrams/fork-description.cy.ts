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
import { Details } from '../../../workbench/Details';
import { Diagram } from '../../../workbench/Diagram';
import { Explorer } from '../../../workbench/Explorer';

describe('Diagram - Fork Representation', () => {
  context('Given a flow project', () => {
    let projectId: string = '';
    let forkedProjectId: string = '';
    beforeEach(() => {
      new Flow().createFlowProject().then((createdProjectData) => {
        projectId = createdProjectData.projectId;
        const project = new Project();
        project.visit(projectId);
        project.disableDeletionConfirmationDialog();
        const explorer = new Explorer();
        explorer.expandWithDoubleClick('Flow');
        explorer.expandWithDoubleClick('NewSystem');
        explorer.selectRepresentation('Topography');
      });
    });

    it('Then we can update a forked decription', () => {
      // Open a Topography diagram and fork the description
      const explorer = new Explorer();
      explorer.openTreeItemAction('Topography');
      cy.getByTestId(`fork-action`).should('exist');
      cy.getByTestId(`fork-action`).click();
      cy.getByTestId(`fork-action-open`).should('exist');
      cy.getByTestId(`fork-action-open`).click();

      // Once redirected to the new studio, change the color of the System node
      cy.getByTestId(`navbar-title`).should('exist');
      explorer.expandWithDoubleClick('FlowView');
      explorer.expandWithDoubleClick('View');
      explorer.expandWithDoubleClick('Topography');
      explorer.expandWithDoubleClick('System Node');
      explorer.expandWithDoubleClick('RectangularNodeStyleDescription');
      const details = new Details();
      details.getReferenceWidget('Background').findByTestId('Background-more').click();
      cy.getByTestId(`Flow_Orange`).should('exist');
      cy.getByTestId(`Flow_Orange`).click();
      cy.getByTestId(`select-value`).should('exist');
      cy.getByTestId(`select-value`).click();

      cy.location().then((location) => {
        var projectId = location.href.split('/').at(4);
        if (!!projectId) {
          forkedProjectId = projectId;
        }
      });

      // Go back to the Topography diagram and check if the forked description is used
      cy.wait(6000);
      const project = new Project();
      project.visit(projectId);
      cy.getByTestId(`navbar-title`).should('exist');
      explorer.expandWithDoubleClick('Flow');
      explorer.expandWithDoubleClick('NewSystem');
      explorer.selectRepresentation('Topography');
      const diagram = new Diagram();
      diagram.getDiagram('Topography').should('exist');
      diagram.getNodes('Topography', 'CompositeProcessor1').should('exist');
      diagram.getNodes('Topography', 'CompositeProcessor1').children().first().should('exist');
      diagram
        .getNodes('Topography', 'CompositeProcessor1')
        .children()
        .first()
        .should('have.css', 'background-color', 'rgb(251, 166, 0)');
    });

    afterEach(() => {
      cy.deleteProject(projectId);
      cy.deleteProject(forkedProjectId);
    });
  });
});
