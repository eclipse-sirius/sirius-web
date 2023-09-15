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

  it('zoom reset to 100% when switched representations', () => {
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
    cy.getByTestId('representationDescription').click();
    cy.getByTestId('Topography with auto layout').click();
    cy.getByTestId('create-representation').click();

    cy.getByTestId('zoom-level').find('input').should('have.value', '1');
    cy.getByTestId('zoom-level').click();
    cy.get('.MuiList-root').type('{uparrow}{enter}');
    cy.getByTestId('zoom-level').find('input').should('have.value', '1.25');

    cy.getByTestId('representation-tab-Topography1').click();
    cy.getByTestId('zoom-level').find('input').should('have.value', '1');

    cy.getByTestId('representation-tab-Topography2').click();
    cy.getByTestId('zoom-level').find('input').should('have.value', '1');
  });

  it('zoom reset to 100% when clicked on tree item', () => {
    cy.getByTestId('robot').dblclick();
    cy.getByTestId('Robot').dblclick();
    cy.getByTestId('Robot-more').click();

    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();

    cy.getByTestId('name').clear();
    cy.getByTestId('name').type('Topography1');
    cy.getByTestId('representationDescription').click();
    cy.getByTestId('Topography with auto layout').click();
    cy.getByTestId('create-representation').click();

    cy.getByTestId('zoom-level').find('input').should('have.value', '1');
    cy.getByTestId('zoom-level').click();
    cy.get('.MuiList-root').type('{uparrow}{enter}');
    cy.getByTestId('zoom-level').find('input').should('have.value', '1.25');

    cy.getByTestId('Central_Unit').click();
    cy.getByTestId('zoom-level').find('input').should('have.value', '1');
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

    cy.get('#diagram>svg text.sprotty-label').should('have.length', 23);
    cy.get('#diagram>svg foreignObject.sprotty-label').should('have.length', 2);
    cy.get('#diagram>svg g.sprotty-edge').should('have.length', 7);
    cy.get('#diagram>svg image').should('have.length', 10);
    cy.get('#diagram rect').should('have.length', 18);

    cy.get('#diagram>svg>g>g>rect').should('have.length', 3);
    cy.get('#diagram>svg>g>g>image').should('have.length', 1);
    cy.get('#diagram>svg>g>g.sprotty-edge').should('have.length', 7);

    cy.get('#diagram>svg>g>g>g rect').should('have.length', 15);
    cy.get('#diagram>svg>g>g>g image').should('have.length', 9);
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
    cy.get('#diagram>svg.sprotty-graph').should('have.length', 1);

    cy.getByTestId('share').click();
    cy.url().then(($url) => {
      cy.task('getClipboard').should('eq', $url);
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

  it('Icons are displayed', () => {
    cy.getByTestId('robot').dblclick();
    cy.getByTestId('Robot').dblclick();
    cy.getByTestId('Robot-more').click();

    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();

    cy.getByTestId('name').clear();
    cy.getByTestId('name').type('Topography1');
    cy.getByTestId('representationDescription').click();
    cy.getByTestId('Topography with auto layout').click();
    cy.getByTestId('create-representation').click();

    cy.get('#diagram>svg>g>g>rect').siblings('g').children('image').should('have.length', 9);
  });

  it('can create views by Drag and Drop on an unsynchronized diagram', () => {
    cy.getByTestId('robot').dblclick();
    cy.getByTestId('Robot').dblclick();
    cy.getByTestId('Robot-more').click();

    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();

    cy.getByTestId('name').clear();
    cy.getByTestId('name').type('Topography1');
    cy.getByTestId('representationDescription').click();
    cy.getByTestId('Topography unsynchronized').click();
    cy.getByTestId('create-representation').click();

    // Drop Central_Unit on the diagram's background
    const dataTransfer1 = new DataTransfer();
    cy.getByTestId('Central_Unit').trigger('dragstart', { dataTransfer: dataTransfer1 });
    cy.getByTestId('Diagram').trigger('drop', { dataTransfer: dataTransfer1 });
    cy.wait(500); // Wait for representation to refresh
    cy.getByTestId('Rectangle - Central_Unit').should('exist');

    // Drop Motion_Engine inside Central_Unit
    const dataTransfer2 = new DataTransfer();
    cy.getByTestId('Central_Unit').dblclick();
    cy.getByTestId('Motion_Engine').trigger('dragstart', { dataTransfer: dataTransfer2 });
    cy.getByTestId('Rectangle - Central_Unit').trigger('drop', { dataTransfer: dataTransfer2 });
    cy.wait(500); // Wait for representation to refresh
    cy.getByTestId('Image - Motion_Engine').should('exist');
  });

  it('can create views by Drag and Drop on an unsynchronized diagram when a representation is selected', () => {
    cy.getByTestId('robot').dblclick();
    cy.getByTestId('Robot').dblclick();
    cy.getByTestId('Robot-more').click();

    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();

    cy.getByTestId('name').clear();
    cy.getByTestId('name').type('Topography1');
    cy.getByTestId('representationDescription').click();
    cy.getByTestId('Topography unsynchronized').click();
    cy.getByTestId('create-representation').click();

    cy.getByTestId('Topography1').click();

    const dataTransfer = new DataTransfer();
    cy.getByTestId('Central_Unit').trigger('dragstart', { dataTransfer });
    cy.getByTestId('Diagram').trigger('drop', { dataTransfer });
    cy.wait(500); // Wait for representation to refresh
    cy.getByTestId('Rectangle - Central_Unit').should('exist');
  });
});
