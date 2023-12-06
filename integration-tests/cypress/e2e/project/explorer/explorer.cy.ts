/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
import { Workbench } from '../../../workbench/Workbench';

const projectName = 'Cypress - explorer';

describe('Explorer', () => {
  context('Given a flow project with a robot document', () => {
    let projectId: string = '';
    beforeEach(() =>
      new Flow().createRobotProject(projectName).then((createdProjectData) => {
        projectId = createdProjectData.projectId;
        new Project().visit(createdProjectData.projectId);
      })
    );

    afterEach(() => cy.deleteProject(projectId));

    context('When we interact with the explorer', () => {
      it('Then it contains the documents', () => {
        new Explorer().getExplorerView().contains('robot');
      });

      it('Then we can expand a tree item', () => {
        const explorer = new Explorer();
        explorer.expand('robot');
        explorer.getTreeItemByLabel('Robot').should('exist');

        explorer.expand('Robot');
        explorer.getTreeItemByLabel('Central_Unit').should('exist');
        explorer.getTreeItemByLabel('CaptureSubSystem').should('exist');
        explorer.getTreeItemByLabel('Wifi').should('exist');
      });

      it('Then we can collapse a tree item', () => {
        const explorer = new Explorer();
        explorer.expand('robot');
        explorer.expand('Robot');
        explorer.collapse('robot');

        explorer.getTreeItemByLabel('Robot').should('not.exist');
      });

      it('Then we can select an object', () => {
        const explorer = new Explorer();
        explorer.expand('robot');
        explorer.expand('Robot');
        explorer.getTreeItemByLabel('Wifi').click();

        explorer.getSelectedTreeItems().should('have.length', 1);
        explorer.getSelectedTreeItems().contains('Wifi').should('exist');
      });

      it('Then we can select a document', () => {
        const explorer = new Explorer();
        explorer.getTreeItemByLabel('robot').click();
        explorer.getSelectedTreeItems().should('have.length', 1);
        explorer.getSelectedTreeItems().contains('robot').should('exist');
      });

      it('Then we can rename a selected document with simple click (direct edit)', () => {
        const explorer = new Explorer();
        explorer.getTreeItemByLabel('robot').click();
        explorer.getTreeItemByLabel('robot').type('renamed-robot{enter}');
        explorer.getTreeItemByLabel('robot').should('exist');
      });

      it('Then we can rename a selected document by start typing (direct edit)', () => {
        const explorer = new Explorer();
        explorer.getTreeItemByLabel('robot').click();
        explorer.getTreeItemByLabel('robot').type('renamed-robot{enter}');
        explorer.getTreeItemByLabel('renamed-robot').should('exist');
      });

      it('Then we can cancel a direct edit with Escape', () => {
        const explorer = new Explorer();
        explorer.getTreeItemByLabel('robot').click();
        explorer.getTreeItemByLabel('robot').type('renamed-robot{esc}');

        cy.getByTestId('name-edit').should('not.exist');
        explorer.getTreeItemByLabel('robot').should('exist');
      });

      it('The we can apply a direct edit with focus lost', () => {
        const explorer = new Explorer();
        explorer.expand('robot');
        explorer.getTreeItemByLabel('robot').click();

        explorer.getTreeItemByLabel('robot').type('renamed-robot');
        explorer.getTreeItemByLabel('Robot').click();
        explorer.getTreeItemByLabel('renamed-robot').should('exist');
      });

      it('Then documents are alphabetically ordered (case insensitive order)', () => {
        // Create Flow Model
        const workbench = new Workbench();
        workbench.performAction('Flow');

        // Check documents order
        const explorer = new Explorer();
        explorer.getTreeItems().first().should('contain', 'Flow');
        explorer.getTreeItems().last().should('contain', 'robot');

        // Rename Flow Model to sFlow
        explorer.getTreeItemByLabel('Flow').click();
        explorer.rename('Flow', 'sFlow');
        explorer.getTreeItemByLabel('sFlow').should('exist');

        // Check documents order
        explorer.getTreeItems().first().should('contain', 'robot');
        explorer.getTreeItems().last().should('contain', 'sFlow');

        // Rename sFlow Model to ROBOT
        explorer.getTreeItemByLabel('sFlow').click();
        explorer.rename('sFlow', 'ROBOT');
        explorer.getTreeItemByLabel('ROBOT').should('exist');

        // Check documents order
        explorer.getTreeItems().first().should('contain', 'robot');
        explorer.getTreeItems().last().should('contain', 'ROBOT');
      });
    });

    context('When we create a new diagram using the contextual menu', () => {
      beforeEach(() => {
        const explorer = new Explorer();
        explorer.expand('robot');
        explorer.getTreeItemByLabel('Robot').should('exist');
        explorer.createRepresentation('Robot', 'Topography with auto layout', 'Topography');
      });

      it('Then it reveals a newly created diagram', () => {
        const explorer = new Explorer();
        explorer.getTreeItemByLabel('Central_Unit').should('exist');
        explorer.getTreeItemByLabel('CaptureSubSystem').should('exist');
        explorer.getTreeItemByLabel('Topography').should('exist');

        explorer.getSelectedTreeItems().should('have.length', 1);
        explorer.getSelectedTreeItems().contains('Topography').should('exist');
      });

      it('Then it reveals the semantic element selected on a diagram', () => {
        const diagram = new Diagram();
        diagram.getDiagram('Topography').should('exist');
        diagram.getNodes('Topography', 'DSP').click();

        const explorer = new Explorer();
        explorer.getSelectedTreeItems().should('have.length', 1);
        explorer.getSelectedTreeItems().contains('DSP').should('exist');
      });

      it('Then it reveals an existing diagram opened using the onboard area', () => {
        const workbench = new Workbench();
        workbench.closeRepresentation('Topography');

        const explorer = new Explorer();
        explorer.collapse('robot');
        explorer.getTreeItemByLabel('Topography').should('not.exist');

        workbench.openRepresentation('Topography');

        explorer.getTreeItemByLabel('Topography').should('exist');
        explorer.getSelectedTreeItems().contains('Topography').should('exist');
      });
    });
  });
});
