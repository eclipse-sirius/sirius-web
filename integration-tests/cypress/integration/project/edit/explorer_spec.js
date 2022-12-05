/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
 * This program and the accompanying materials
 * are made available under the erms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
function createRepresentationFromTreeItem(treeItemLabel, representationDescriptionName, representationName) {
  cy.getByTestId(treeItemLabel + '-more').click();
  cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();
  cy.getByTestId('name').clear();
  cy.getByTestId('name').type(representationName);
  cy.getByTestId('representationDescription').click();
  cy.getByTestId(representationDescriptionName).click();
  cy.getByTestId('create-representation').click();
}

describe('/projects/:projectId/edit - Explorer', () => {
  beforeEach(() => {
    cy.deleteAllProjects();
    cy.createProject('Cypress Project').then((res) => {
      const projectId = res.body.data.createProject.project.id;
      const robot_flow_id = 'c26b6086-b444-3ee6-b8cd-9a4fde5956a7';
      cy.createDocument(projectId, robot_flow_id, 'robot').then((res) => {
        cy.visit(`/projects/${projectId}/edit`);
      });
    });
  });

  it('contains the documents', () => {
    cy.getByTestId('explorer').contains('robot');
  });

  it('can expand a tree item', () => {
    cy.getByTestId('robot').dblclick();
    cy.getByTestId('explorer').contains('Robot');

    cy.getByTestId('Robot').dblclick();
    cy.getByTestId('explorer').contains('Central_Unit');
    cy.getByTestId('explorer').contains('CaptureSubSystem');
    cy.getByTestId('explorer').contains('Wifi');
  });

  it('can collapse a tree item', () => {
    cy.getByTestId('robot').dblclick();
    cy.getByTestId('Robot').dblclick();
    cy.getByTestId('robot').dblclick();

    cy.getByTestId('Robot').should('not.exist');
  });

  it('can select an object', () => {
    cy.getByTestId('robot').dblclick();
    cy.getByTestId('Robot').dblclick();
    cy.getByTestId('Wifi').click();

    cy.getByTestId('selected').contains('Wifi');
  });

  it('can select a document', () => {
    cy.getByTestId('robot').click();
    cy.getByTestId('selected').contains('robot');
  });

  it('can rename a selected document with simple click (direct edit)', () => {
    cy.getByTestId('robot').click();
    cy.getByTestId('selected').contains('robot');
    cy.getByTestId('robot').click();
    cy.getByTestId('robot').type('renamed-robot{enter}');
    cy.getByTestId('renamed-robot').should('exist');
  });

  it('can rename a selected document by start typing (direct edit)', () => {
    cy.getByTestId('robot').click();
    cy.getByTestId('selected').contains('robot');
    cy.getByTestId('robot').type('renamed-robot{enter}');
    cy.getByTestId('renamed-robot').should('exist');
  });

  it('can cancel a direct edit with Escape', () => {
    cy.getByTestId('robot').click();
    cy.getByTestId('selected').contains('robot');
    cy.getByTestId('robot').click();
    cy.getByTestId('robot').type('renamed-robot{esc}');
    cy.getByTestId('robot').should('exist');
  });

  it('can apply a direct edit with focus lost', () => {
    cy.getByTestId('robot').dblclick();
    cy.getByTestId('Robot').should('exist');
    cy.getByTestId('robot').click();
    cy.getByTestId('robot').type('renamed-robot');
    cy.getByTestId('Robot').click();
    cy.getByTestId('renamed-robot').should('exist');
  });

  it('reveals a newly created diagram', () => {
    // 1. Expand the  root 'Robot' but do not reveal its children (yet)
    cy.getByTestId('robot').dblclick();
    cy.getByTestId('Robot').should('exist');
    cy.getByTestId('Central_Unit').should('not.exist');
    cy.getByTestId('CaptureSubSystem').should('not.exist');
    cy.getByTestId('Topography').should('not.exist');

    // 2. Create 'Topography' diagram on it
    createRepresentationFromTreeItem('Robot', 'Topography with auto layout', 'Topography');

    // 3. CHECK that the 'Robot' is now expanded, and the 'Topography' diagram inside is visible and selected
    cy.getByTestId('Central_Unit').should('exist');
    cy.getByTestId('CaptureSubSystem').should('exist');
    cy.getByTestId('Topography').should('exist');
    cy.getByTestId('selected').findByTestId('Topography').should('exist');
  });

  it('reveals the semantic element selected on a diagram', () => {
    // 1. Expand the root 'Robot' but do not reveal its children (yet)
    cy.getByTestId('robot').dblclick();
    cy.getByTestId('Robot').should('exist');
    cy.getByTestId('Central_Unit').should('not.exist');
    cy.getByTestId('CaptureSubSystem').should('not.exist');
    cy.getByTestId('Topography').should('not.exist');

    // 2. Create 'Topography' diagram on it
    createRepresentationFromTreeItem('Robot', 'Topography with auto layout', 'Topography');

    // 3. On the diagram, click on the 'DSP' node
    cy.getByTestId('DSP').should('not.exist');
    cy.getByTestId('Image - DSP').click();

    // 4. CHECK that the 'DSP' node is now visible and selected in the explorer
    cy.getByTestId('DSP').should('exist');
    cy.getByTestId('selected').findByTestId('DSP').should('exist');
  });

  it('reveals an existing diagram opened using the onboard area', () => {
    // 1. Expand and select root 'Robot' but do not reveal its children (yet)
    cy.getByTestId('robot').dblclick();
    cy.getByTestId('Robot').click();

    // 2. Create 'Topography' diagram on it
    createRepresentationFromTreeItem('Robot', 'Topography with auto layout', 'Topography');

    // 3. Close the new representation and hide it in the explorer
    cy.getByTestId('close-representation-tab-Topography').click();
    cy.getByTestId('robot').click();
    cy.getByTestId('robot').dblclick();
    cy.getByTestId('Topography').should('not.exist');

    // 4. Re-open the representation from the onboard area
    cy.getByTestId('onboard-open-Topography').click();

    // 5. CHECK that is is visible and selected again
    cy.getByTestId('Topography').should('exist');
    cy.getByTestId('selected').findByTestId('Topography').should('exist');
  });
});
