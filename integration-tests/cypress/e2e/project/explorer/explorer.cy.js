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

describe('/project/explorer - Explorer', () => {
  const flow = new Flow();

  beforeEach(() => flow.createRobotProject(projectName).then(() => new Project().visit(flow.projectId)));

  afterEach(() => cy.deleteProject(flow.projectId));

  it('contains the documents', () => {
    new Explorer().getExplorerView().contains('robot');
  });

  it('can expand a tree item', () => {
    const explorer = new Explorer();
    explorer.expand('robot');
    explorer.getTreeItemByLabel('Robot').should('exist');

    explorer.expand('Robot');
    explorer.getTreeItemByLabel('Central_Unit').should('exist');
    explorer.getTreeItemByLabel('CaptureSubSystem').should('exist');
    explorer.getTreeItemByLabel('Wifi').should('exist');
  });

  it('can collapse a tree item', () => {
    const explorer = new Explorer();
    explorer.expand('robot');
    explorer.expand('Robot');
    explorer.collapse('robot');

    explorer.getTreeItemByLabel('Robot').should('not.exist');
  });

  it('can select an object', () => {
    const explorer = new Explorer();
    explorer.expand('robot');
    explorer.expand('Robot');
    explorer.getTreeItemByLabel('Wifi').click();

    explorer.getSelectedTreeItems().should('have.length', 1);
    explorer.getSelectedTreeItems().contains('Wifi').should('exist');
  });

  it('can select a document', () => {
    const explorer = new Explorer();
    explorer.getTreeItemByLabel('robot').click();
    explorer.getSelectedTreeItems().should('have.length', 1);
    explorer.getSelectedTreeItems().contains('robot').should('exist');
  });

  it('can rename a selected document with simple click (direct edit)', () => {
    const explorer = new Explorer();
    explorer.getTreeItemByLabel('robot').click();
    explorer.getTreeItemByLabel('robot').type('renamed-robot{enter}');
    explorer.getTreeItemByLabel('robot').should('exist');
  });

  it('can rename a selected document by start typing (direct edit)', () => {
    const explorer = new Explorer();
    explorer.getTreeItemByLabel('robot').click();
    explorer.getTreeItemByLabel('robot').type('renamed-robot{enter}');
    explorer.getTreeItemByLabel('renamed-robot').should('exist');
  });

  it('can cancel a direct edit with Escape', () => {
    const explorer = new Explorer();
    explorer.getTreeItemByLabel('robot').click();
    explorer.getTreeItemByLabel('robot').type('renamed-robot{esc}');

    cy.getByTestId('name-edit').should('not.exist');
    explorer.getTreeItemByLabel('robot').should('exist');
  });

  it('can apply a direct edit with focus lost', () => {
    const explorer = new Explorer();
    explorer.expand('robot');
    explorer.getTreeItemByLabel('robot').click();

    explorer.getTreeItemByLabel('robot').type('renamed-robot');
    explorer.getTreeItemByLabel('Robot').click();
    explorer.getTreeItemByLabel('renamed-robot').should('exist');
  });

  it('reveals a newly created diagram', () => {
    // 1. Expand the  root 'Robot' but do not reveal its children (yet)
    const explorer = new Explorer();
    explorer.expand('robot');
    explorer.getTreeItemByLabel('Robot').should('exist');
    explorer.getTreeItemByLabel('Central_Unit').should('not.exist');
    explorer.getTreeItemByLabel('CaptureSubSystem').should('not.exist');
    explorer.getTreeItemByLabel('Topography').should('not.exist');

    // 2. Create the representation
    explorer.createRepresentation('Robot', 'Topography with auto layout', 'Topography');

    // 3. CHECK that the 'Robot' is now expanded, and the 'Topography' diagram inside is visible and selected
    explorer.getTreeItemByLabel('Central_Unit').should('exist');
    explorer.getTreeItemByLabel('CaptureSubSystem').should('exist');
    explorer.getTreeItemByLabel('Topography').should('exist');

    explorer.getSelectedTreeItems().should('have.length', 1);
    explorer.getSelectedTreeItems().contains('Topography').should('exist');
  });

  it('reveals the semantic element selected on a diagram', () => {
    // 1. Expand the root 'Robot' but do not reveal its children (yet)
    const explorer = new Explorer();
    explorer.expand('robot');
    explorer.getTreeItemByLabel('Robot').should('exist');
    explorer.getTreeItemByLabel('Central_Unit').should('not.exist');
    explorer.getTreeItemByLabel('CaptureSubSystem').should('not.exist');
    explorer.getTreeItemByLabel('Topography').should('not.exist');

    // 2. Create 'Topography' diagram on it
    explorer.createRepresentation('Robot', 'Topography with auto layout', 'Topography');

    // 3. On the diagram, click on the 'DSP' node
    const diagram = new Diagram();
    diagram.getDiagram('Topography').should('exist');
    diagram.getNodes('Topography', 'DSP').click();

    // 4. CHECK that 'DSP' is now selected in the explorer
    explorer.getSelectedTreeItems().should('have.length', 1);
    explorer.getSelectedTreeItems().contains('DSP').should('exist');
  });

  it('reveals an existing diagram opened using the onboard area', () => {
    // 1. Expand and select root 'Robot' but do not reveal its children (yet)
    const explorer = new Explorer();
    explorer.expand('robot');

    // 2. Create 'Topography' diagram on it
    explorer.createRepresentation('Robot', 'Topography with auto layout', 'Topography');

    // 3. Close the new representation and hide it in the explorer
    const workbench = new Workbench();
    workbench.closeRepresentation('Topography');
    explorer.collapse('robot');

    explorer.getTreeItemByLabel('Topography').should('not.exist');

    // 4. Re-open the representation from the onboard area
    workbench.openRepresentation('Topography');

    // 5. CHECK that is is visible and selected again
    explorer.getTreeItemByLabel('Topography').should('exist');
    explorer.getSelectedTreeItems().contains('Topography').should('exist');
  });

  it('documents are alphabetically ordered (case insensitive order)', () => {
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
