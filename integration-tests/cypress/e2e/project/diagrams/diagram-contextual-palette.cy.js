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

describe.skip('/projects/:projectId/edit - Robot Diagram', () => {
  const createFlowReactFlowDiagram = () => {
    cy.createProject('Cypress Project').then((res) => {
      const projectId = res.body.data.createProject.project.id;
      const robot_flow_id = 'c26b6086-b444-3ee6-b8cd-9a4fde5956a7';
      cy.createDocument(projectId, robot_flow_id, 'flow').then(() => {
        cy.visit(`/projects/${projectId}/edit`);
      });
    });

    cy.getByTestId('flow').dblclick();
    cy.getByTestId('Robot').dblclick();
    cy.getByTestId('Robot-more').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();

    cy.getByTestId('name').clear();
    cy.getByTestId('name').type('Topography');
    cy.getByTestId('representationDescription').click();
    cy.getByTestId('Topography with auto layout').click();
    cy.getByTestId('create-representation').click();
    cy.getByTestId('Topography').click();
  };

  beforeEach(() => {
    cy.deleteAllProjects();
  });

  it('can not open multi tool section in the same time', () => {
    cy.createProjectFromTemplate('studio-template').then((res) => {
      const projectId = res.body.data.createProjectFromTemplate.project.id;
      cy.visit(`/projects/${projectId}/edit`);
    });
    cy.getByTestId('ViewNewModel').dblclick();
    cy.getByTestId('View').dblclick();
    cy.get('[data-testid$=" Diagram Description"]').dblclick();
    cy.get('[data-testid$=" Diagram Description-more"]').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('rename-tree-item').click();
    cy.getByTestId('name-edit').type('Diagram Description{enter}');

    cy.createChildObject('Palette', 'toolSections-DiagramToolSection');
    cy.getByTestId('Tool Section').click();
    cy.getByTestId('Name').type('{selectAll}section1');
    cy.createChildObject('Palette', 'toolSections-DiagramToolSection');
    cy.getByTestId('Tool Section').click();
    cy.getByTestId('Name').type('{selectAll}section2');
    cy.createChildObject('section1', 'nodeTools-NodeTool');
    cy.getByTestId('Tool').click();
    cy.getByTestId('Name').type('{selectAll}tool1_section1');
    cy.createChildObject('section1', 'nodeTools-NodeTool');
    cy.getByTestId('Tool').click();
    cy.getByTestId('Name').type('{selectAll}tool2_section1');
    cy.createChildObject('section2', 'nodeTools-NodeTool');
    cy.getByTestId('Tool').click();
    cy.getByTestId('Name').type('{selectAll}tool1_section2');
    cy.createChildObject('section2', 'nodeTools-NodeTool');
    cy.getByTestId('Tool').click();
    cy.getByTestId('Name').type('{selectAll}tool2_section2');

    cy.get('[aria-label="Back to the homepage"]').click();

    cy.url().should('eq', Cypress.config().baseUrl + '/projects');
    cy.get('[aria-label="Blank Studio"]').should('be.visible');
    cy.getByTestId('create').click();

    cy.url().should('eq', Cypress.config().baseUrl + '/new/project');
    cy.getByTestId('name').should('be.visible');
    cy.getByTestId('name').type('Instance');
    cy.getByTestId('create-project').click();
    cy.getByTestId('empty').click();
    cy.getByTestId('Others...-more').click();
    cy.getByTestId('new-object').click();
    cy.getByTestId('domain').children('[role="combobox"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('domain').find('div').first().should('not.have.attr', 'aria-disabled');
    cy.getByTestId('domain').click();
    cy.getByTestId('domain').get('[data-value^="domain://"]').should('exist').click();
    cy.getByTestId('create-object').click();

    cy.getByTestId('Root-more').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();
    cy.getByTestId('name').clear();
    cy.getByTestId('name').type('my new diagram');
    cy.getByTestId('representationDescription').click();
    cy.getByTestId('Diagram Description').click();
    cy.getByTestId('create-representation').click();

    cy.getByTestId('rf__wrapper')
      .click('center')
      .then(() => {
        cy.getByTestId('tool1_section1 - Tool').should('exist');
        cy.getByTestId('tool1_section2 - Tool').should('exist');
        cy.getByTestId('tool2_section1 - Tool').should('not.exist');
        cy.getByTestId('tool2_section2 - Tool').should('not.exist');
        cy.getByTestId('expand').should('have.length', 2);
        cy.getByTestId('expand').eq(0).click();
        cy.getByTestId('tool2_section1 - Tool').should('exist');
        cy.getByTestId('tool2_section2 - Tool').should('not.exist');
        cy.getByTestId('expand').eq(1).click();
        cy.getByTestId('tool2_section1 - Tool').should('not.exist');
        cy.getByTestId('tool2_section2 - Tool').should('exist');
        cy.getByTestId('expand').eq(1).click();
        cy.getByTestId('tool2_section1 - Tool').should('not.exist');
        cy.getByTestId('tool2_section2 - Tool').should('not.exist');
      });
  });

  it('closes the diagram palette on Esc', () => {
    // Create a studio project with a Domain diagram
    cy.createProjectFromTemplate('studio-template').then((res) => {
      const projectId = res.body.data.createProjectFromTemplate.project.id;
      cy.visit(`/projects/${projectId}/edit`);
    });

    // Rename the diagram
    cy.getByTestId('onboard-open-Domain').click();
    cy.getByTestId('Domain').click();
    cy.getByTestId('Domain-more').click();
    cy.getByTestId('rename-tree-item').click();
    cy.getByTestId('name-edit').type('Domain{enter}');

    cy.getByTestId('rf__wrapper').click(100, 100);
    cy.getByTestId('Palette').should('exist');
    cy.get('body').type('{esc}');
    cy.getByTestId('Palette').should('not.exist');
  });

  it('closes the element palette on Esc', () => {
    // Create a studio project with a Domain diagram
    cy.createProjectFromTemplate('studio-template').then((res) => {
      const projectId = res.body.data.createProjectFromTemplate.project.id;
      cy.visit(`/projects/${projectId}/edit`);
    });

    // Rename the diagram
    cy.getByTestId('onboard-open-Domain').click();
    cy.getByTestId('Domain').click();
    cy.getByTestId('Domain-more').click();
    cy.getByTestId('rename-tree-item').click();
    cy.getByTestId('name-edit').type('Domain{enter}');

    cy.getByTestId('rf__wrapper').findByTestId('Label - Root').click();
    cy.getByTestId('Palette').should('exist');
    cy.get('body').type('{esc}');
    cy.getByTestId('Palette').should('not.exist');
  });

  it('semantic delete tool with confirmation dialog', () => {
    createFlowReactFlowDiagram();

    // trick used to interact with the palette
    cy.getByTestId('fit-to-screen').click();
    cy.wait(4000);

    cy.getByTestId('rf__wrapper').findByTestId('FreeForm - Wifi').should('exist').click();
    cy.getByTestId('Delete from model - Tool').should('exist').click();

    cy.getByTestId('confirmation-dialog').should('be.visible');
    cy.getByTestId('confirmation-dialog-button-cancel').click();
    cy.getByTestId('confirmation-dialog').should('not.exist');

    cy.getByTestId('Delete from model - Tool').should('exist').click();

    cy.getByTestId('confirmation-dialog').should('be.visible');
    cy.getByTestId('confirmation-dialog-button-ok').click();

    cy.getByTestId('rf__wrapper').findByTestId('FreeForm - Wifi').should('not.exist');
    cy.getByTestId('confirmation-dialog').should('not.exist');
  });

  it('diagram palette is closed once element palette is opened', () => {
    createFlowReactFlowDiagram();

    // trick used to interact with the palette
    cy.getByTestId('fit-to-screen').click();
    cy.wait(4000);

    cy.getByTestId('rf__wrapper').click(100, 100);
    cy.getByTestId('Palette').should('exist').should('have.length', 1);
    cy.getByTestId('Palette').findByTestId('Composite Processor - Tool').should('exist');

    cy.getByTestId('rf__wrapper').findByTestId('FreeForm - Wifi').should('exist').click();
    cy.getByTestId('Palette').should('exist').should('have.length', 1);
    cy.getByTestId('Palette').findByTestId('Edit - Tool').should('exist');
  });
});
