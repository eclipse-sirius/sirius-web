/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
describe('/projects/:projectId/edit - Object Context Menu', () => {
  beforeEach(() => {
    cy.deleteAllProjects();
    cy.createProject('Cypress Project').then((res) => {
      const projectId = res.body.data.createProject.project.id;
      const robot_flow_id = 'c26b6086-b444-3ee6-b8cd-9a4fde5956a7';
      cy.createDocument(projectId, robot_flow_id, 'robot').then((_res) => {
        cy.visit(`/projects/${projectId}/edit`);
      });
    });
  });

  it('can rename an object', () => {
    cy.getByTestId('robot').dblclick();

    cy.getByTestId('Robot-more').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('rename-tree-item').click();
    cy.getByTestId('treeitem-contextmenu').should('not.exist');
    cy.getByTestId('name-edit').get('input').should('have.value', 'Robot');
    cy.getByTestId('name-edit').type('NewRobot{enter}');
    cy.getByTestId('Robot').should('not.exist');
    cy.getByTestId('NewRobot').should('exist');
  });

  it('cannot rename an object with no label feature', () => {
    cy.getByTestId('robot').dblclick();
    cy.getByTestId('Robot').dblclick();
    cy.getByTestId('Wifi').dblclick();

    cy.getByTestId('standard-more').click();

    cy.getByTestId('treeitem-contextmenu').findByTestId('rename-tree-item').should('not.exist');
  });

  it('can delete an object', () => {
    cy.getByTestId('robot').dblclick();

    cy.getByTestId('Robot-more').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('delete').click();

    cy.getByTestId('confirmation-dialog').should('be.visible');
    cy.getByTestId('confirmation-dialog-button-cancel').click();
    cy.getByTestId('confirmation-dialog').should('not.exist');

    cy.getByTestId('treeitem-contextmenu').findByTestId('delete').click();

    cy.getByTestId('confirmation-dialog').should('be.visible');
    cy.getByTestId('confirmation-dialog-button-ok').click();

    cy.getByTestId('Robot').should('not.exist');
    cy.getByTestId('confirmation-dialog').should('not.exist');
  });

  it('can open the new object modal', () => {
    cy.getByTestId('robot').dblclick();
    cy.getByTestId('Robot-more').click();

    cy.getByTestId('treeitem-contextmenu').findByTestId('new-object').click();

    cy.get('.MuiDialog-container').should('be.visible');
  });

  it('can create a new object by clicking on the create button', () => {
    cy.getByTestId('robot').dblclick();
    cy.getByTestId('Robot-more').click();

    cy.getByTestId('treeitem-contextmenu').findByTestId('new-object').click();
    //make sure the data are fetched before selecting
    cy.getByTestId('create-object').should('be.enabled');

    cy.getByTestId('childCreationDescription').click();
    cy.get('[data-value="Power Input"]').click();
    cy.getByTestId('create-object').click();

    cy.getByTestId('explorer://').contains('PowerInput');
  });

  it('can select the created child object', () => {
    cy.getByTestId('robot').dblclick();
    cy.getByTestId('Robot-more').click();

    cy.getByTestId('treeitem-contextmenu').findByTestId('new-object').click();
    //make sure the data are fetched before selecting
    cy.getByTestId('create-object').should('be.enabled');

    cy.getByTestId('childCreationDescription').click();
    cy.get('[data-value="Power Input"]').click();
    cy.getByTestId('create-object').click();

    cy.getByTestId('explorer://').contains('PowerInput');
    cy.getByTestId('selected').contains('PowerInput');
  });

  it('can open the new representation modal', () => {
    cy.getByTestId('robot').dblclick();
    cy.getByTestId('Robot-more').click();

    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();

    cy.get('.MuiDialog-container').should('be.visible');
  });

  it('can create a new representation by typing enter', () => {
    cy.getByTestId('robot').dblclick();
    cy.getByTestId('Robot-more').click();

    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();
    cy.getByTestId('name').clear();
    cy.getByTestId('name').type('diagram{enter}');
    cy.getByTestId('create-representation').click();

    cy.get('.MuiDialog-container').should('not.exist');

    cy.getByTestId('explorer://').contains('diagram');
  });

  it('can create a new representation by clicking on the create button', () => {
    cy.getByTestId('robot').dblclick();
    cy.getByTestId('Robot-more').click();

    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();
    cy.getByTestId('name').clear();
    cy.getByTestId('name').type('diagram');
    cy.getByTestId('create-representation').click();

    cy.get('.MuiDialog-container').should('not.exist');

    cy.getByTestId('explorer://').contains('diagram');
  });

  it('can expand the node in which the representation is created and open the representation', () => {
    cy.getByTestId('robot').dblclick();
    cy.getByTestId('Robot-more').click();

    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();
    cy.getByTestId('name').clear();
    cy.getByTestId('name').type('Topography');
    cy.getByTestId('create-representation').click();

    cy.get('.MuiDialog-container').should('not.exist');

    cy.getByTestId('explorer://').contains('Topography');
    cy.getByTestId('representation-tab-Topography').should('exist');
  });

  it('contains a default name for the representation', () => {
    cy.getByTestId('robot').dblclick();
    cy.getByTestId('Robot-more').click();

    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();
    //make sure the data are fetched before selecting
    cy.getByTestId('create-representation').should('be.enabled');
    cy.getByTestId('representationDescription').click();
    cy.getByTestId('Topography with auto layout').click();
    cy.getByTestId('name').should('have.value', 'Topography with auto layout');

    cy.getByTestId('representationDescription').click();
    //Choose topography representation type
    cy.getByTestId('Topography').click();
    cy.getByTestId('name').should('have.value', 'Topography');

    cy.getByTestId('name').clear().type('newName');
    cy.getByTestId('name').should('have.value', 'newName');

    cy.getByTestId('representationDescription').click();
    //Choose topography with auto layout representation type
    cy.getByTestId('Topography with auto layout').click();
    cy.getByTestId('name').should('have.value', 'newName');
  });

  it('expand all menu item on root object', () => {
    cy.getByTestId('robot').dblclick();
    cy.getByTestId('Robot').should('exist');

    cy.getByTestId('Robot-more').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('expand-all').click();

    cy.getByTestId('robot').should('exist');
    cy.getByTestId('Robot').should('exist');
    cy.getByTestId('Central_Unit').should('exist');
    cy.getByTestId('DSP').should('exist');
    cy.getByTestId('standard').should('exist');
    cy.getByTestId('Motion_Engine').should('exist');
    cy.getByTestId('active').should('exist');
    cy.getByTestId('CaptureSubSystem').should('exist');
    cy.getByTestId('Radar_Capture').should('exist');
    cy.getByTestId('high').should('exist');
    cy.getByTestId('high').should('exist');
    cy.getByTestId('Back_Camera').should('exist');
    cy.getByTestId('standard').should('exist');
    cy.getByTestId('Radar').should('exist');
    cy.getByTestId('high').should('exist');
    cy.getByTestId('Engine').should('exist');
    cy.getByTestId('GPU').should('exist');
    cy.getByTestId('standard').should('exist');
    cy.getByTestId('active').should('exist');
    cy.getByTestId('Wifi').should('exist');
    cy.getByTestId('standard').should('exist');
  });

  it('expand all menu item on intermediate object in tree hierarchy', () => {
    cy.getByTestId('robot').dblclick();
    cy.getByTestId('Robot').dblclick();

    cy.getByTestId('Central_Unit').should('exist');
    cy.getByTestId('Central_Unit-more').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('expand-all').click();

    cy.getByTestId('robot').should('exist');
    cy.getByTestId('Robot').should('exist');
    cy.getByTestId('Central_Unit').should('exist');
    cy.getByTestId('DSP').should('exist');
    cy.getByTestId('standard').should('exist');
    cy.getByTestId('Motion_Engine').should('exist');
    cy.getByTestId('active').should('exist');
    cy.getByTestId('CaptureSubSystem').should('exist');
    cy.getByTestId('Radar_Capture').should('not.exist');
    cy.getByTestId('Back_Camera').should('not.exist');
    cy.getByTestId('Radar').should('not.exist');
    cy.getByTestId('Engine').should('not.exist');
    cy.getByTestId('GPU').should('not.exist');
    // Unable to test this tree item cause it has the same id than another existing one
    // cy.getByTestId('active').should('not.exist');
    cy.getByTestId('Wifi').should('exist');
    // Unable to test this tree item cause it has the same id than another existing one
    // cy.getByTestId('standard').should('not.exist');
  });

  it('expand all menu item on object with no children', () => {
    cy.getByTestId('robot').dblclick();
    cy.getByTestId('Robot').dblclick();
    cy.getByTestId('Wifi').dblclick();
    cy.getByTestId('standard').dblclick();

    cy.getByTestId('standard-more').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('expand-all').should('not.exist');
  });
});
