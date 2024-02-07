/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
describe('/projects/:projectId/edit - Diagram', () => {
  beforeEach(() => {
    cy.deleteAllProjects();
    cy.createProject('Cypress Project').then((res) => {
      const projectId = res.body.data.createProject.project.id;
      const robot_flow_id = 'c26b6086-b444-3ee6-b8cd-9a4fde5956a7';
      cy.createDocument(projectId, robot_flow_id, 'robot').then(() => {
        cy.visit(`/projects/${projectId}/edit`);
      });
    });
  });

  it('shows a Topography diagram when clicked in the explorer', () => {
    cy.getByTestId('robot').dblclick();
    cy.getByTestId('Robot').dblclick();
    cy.getByTestId('Robot-more').click();

    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();

    cy.getByTestId('name').clear();
    cy.getByTestId('name').type('Topography1');
    cy.getByTestId('representationDescription').click();
    cy.getByTestId('Topography with auto layout').click();
    cy.getByTestId('create-representation').click();

    cy.getByTestId('rf__wrapper').should('exist');
    cy.get('.react-flow__edgelabel-renderer').children().should('have.length', 7);
    cy.get('.react-flow__nodes').children().should('have.length', 18);
    cy.get('.react-flow__node-freeFormNode').should('have.length', 12);
    cy.get('.react-flow__node-listNode').should('have.length', 2);
    cy.get('.react-flow__node-iconLabelNode').should('have.length', 4);
  });

  it('can share the representation', () => {
    cy.getByTestId('share').should('not.exist');

    cy.getByTestId('robot').dblclick();
    cy.getByTestId('Robot').dblclick();
    cy.getByTestId('Robot-more').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();

    cy.getByTestId('name').clear();
    cy.getByTestId('name').type('Topography1');
    cy.getByTestId('representationDescription').click();
    cy.getByTestId('Topography with auto layout').click();
    cy.getByTestId('create-representation').click();

    cy.getByTestId('share').should('be.enabled');
    cy.getByTestId('rf__wrapper').should('exist');

    cy.getByTestId('share').click();
    cy.url().then(($url) => {
      cy.window().then((win) => {
        win.navigator.clipboard.readText().then((text) => {
          expect(text).to.eq($url);
        });
      });
    });
  });

  it('can switch between representations', () => {
    cy.getByTestId('robot').dblclick();
    cy.getByTestId('Robot').dblclick();
    cy.getByTestId('Robot-more').click();

    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();

    cy.getByTestId('name').clear();
    cy.getByTestId('name').type('Topography1');
    cy.getByTestId('create-representation').click();

    cy.getByTestId('Robot-more').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();

    cy.getByTestId('name').clear();
    cy.getByTestId('name').type('Topography2');
    cy.getByTestId('create-representation').click();

    cy.getByTestId('Topography1').click();

    cy.getByTestId('representation-tab-Topography1').should('have.attr', 'data-testselected', 'true');
    cy.getByTestId('representation-tab-Topography2').should('have.attr', 'data-testselected', 'false');

    cy.getByTestId('Topography2').click();
    cy.getByTestId('representation-tab-Topography1').should('have.attr', 'data-testselected', 'false');
    cy.getByTestId('representation-tab-Topography2').should('have.attr', 'data-testselected', 'true');

    cy.getByTestId('representation-tab-Topography1').click();

    cy.getByTestId('representation-tab-Topography1').should('have.attr', 'data-testselected', 'true');
    cy.getByTestId('representation-tab-Topography2').should('have.attr', 'data-testselected', 'false');
  });

  it('can delete element with DEL key', () => {
    cy.getByTestId('robot').dblclick();
    cy.getByTestId('Robot').dblclick();
    cy.getByTestId('Robot-more').click();

    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();

    cy.getByTestId('name').clear();
    cy.getByTestId('name').type('Topography1');
    cy.getByTestId('representationDescription').click();
    cy.getByTestId('Topography').click();
    cy.getByTestId('create-representation').click();

    cy.getByTestId('rf__wrapper').should('exist');
    cy.getByTestId('rf__wrapper').findByTestId('FreeForm - Wifi').should('exist').click();
    cy.getByTestId('rf__wrapper').findByTestId('FreeForm - Wifi').type('{del}');

    cy.getByTestId('confirmation-dialog').should('be.visible');
    cy.getByTestId('confirmation-dialog-button-cancel').click();
    cy.getByTestId('confirmation-dialog').should('not.exist');

    cy.getByTestId('rf__wrapper').findByTestId('FreeForm - Wifi').type('{del}');

    cy.getByTestId('confirmation-dialog').should('be.visible');
    cy.getByTestId('confirmation-dialog-button-ok').click();

    cy.getByTestId('rf__wrapper').findByTestId('FreeForm - Wifi').should('not.exist');
    cy.getByTestId('confirmation-dialog').should('not.exist');
  });
});
