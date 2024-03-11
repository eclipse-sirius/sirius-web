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
describe('/projects/:projectId/edit - Robot Diagram', () => {
  const fadeByElementTestId = (elementTestId) => {
    cy.getByTestId(elementTestId).should('have.css', 'opacity', '1');
    cy.getByTestId(elementTestId).first().click({ force: true });
    cy.getByTestId('Fade-element').should('exist').click({ force: true });
    cy.getByTestId(elementTestId).should('have.css', 'opacity', '0.4');
  };

  const hideByElementTestId = (elementTestId) => {
    cy.getByTestId(elementTestId).then((elementBefore) => {
      const countBefore = elementBefore.length ?? 0;
      cy.getByTestId(elementTestId).first().click({ force: true });
      cy.getByTestId('Hide-element').should('exist').click({ force: true });
      cy.getByTestId('Hide-element').should('not.exist');
      if (countBefore === 1) {
        cy.getByTestId(elementTestId).should('not.exist');
      } else {
        cy.getByTestId(elementTestId).then((elementAfter) => {
          cy.expect((elementAfter.length ?? 0) + 1).to.equal(countBefore);
        });
      }
    });
  };

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

  it.skip('test Hide/Fade action is not available on diagram background', () => {
    createFlowReactFlowDiagram();
    cy.getByTestId('rf__wrapper').findByTestId('Label - CaptureSubSystem').should('exist').click('topLeft');

    cy.getByTestId('Hide-element').should('exist');
    cy.getByTestId('Fade-element').should('exist');
    cy.getByTestId('rf__wrapper').click('bottomLeft');
    // NOTE for later: ensure the palette is displayed
    cy.getByTestId('Hide-element').should('not.exist');
    cy.getByTestId('Fade-element').should('not.exist');
  });

  it.skip('can fade any type of nodes', () => {
    createFlowReactFlowDiagram();
    fadeByElementTestId('IconLabel - Temperature: 25');
    fadeByElementTestId('FreeForm - Motion_Engine');
    fadeByElementTestId('FreeForm - Central_Unit');
    fadeByElementTestId('List - Description');
  });

  it.skip('can hide any type of nodes', () => {
    createFlowReactFlowDiagram();
    cy.getByTestId('Label - Temperature: 25').should('have.length', 1);
    cy.getByTestId('Label - Temperature: 28').click();
    cy.getByTestId('form').findByTestId('input-Temperature').should('not.be.disabled');
    cy.getByTestId('form').findByTestId('input-Temperature').should('have.attr', 'value', '28');
    cy.getByTestId('form').findByTestId('Temperature').type('{selectall}').type('25').type('{enter}');
    cy.getByTestId('Label - Temperature: 25').should('have.length', 2);
    hideByElementTestId('Label - Temperature: 25');
    hideByElementTestId('FreeForm - Motion_Engine');
    hideByElementTestId('List - Description');
    hideByElementTestId('FreeForm - Central_Unit');
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

    cy.createChildObject('DiagramPalette', 'Diagram Tool Section');
    cy.getByTestId('Tool Section').click();
    cy.getByTestId('Name').type('{selectAll}section1');
    cy.createChildObject('DiagramPalette', 'Diagram Tool Section');
    cy.getByTestId('Tool Section').click();
    cy.getByTestId('Name').type('{selectAll}section2');
    cy.createChildObject('section1', 'Node Tool');
    cy.getByTestId('Tool').click();
    cy.getByTestId('Name').type('{selectAll}tool1_section1');
    cy.createChildObject('section1', 'Node Tool');
    cy.getByTestId('Tool').click();
    cy.getByTestId('Name').type('{selectAll}tool2_section1');
    cy.createChildObject('section2', 'Node Tool');
    cy.getByTestId('Tool').click();
    cy.getByTestId('Name').type('{selectAll}tool1_section2');
    cy.createChildObject('section2', 'Node Tool');
    cy.getByTestId('Tool').click();
    cy.getByTestId('Name').type('{selectAll}tool2_section2');

    cy.get('[title="Back to the homepage"]').click();

    cy.url().should('eq', Cypress.config().baseUrl + '/projects');
    cy.get('[title="Blank Studio"]').should('be.visible');
    cy.getByTestId('create').click();

    cy.url().should('eq', Cypress.config().baseUrl + '/new/project');
    cy.getByTestId('name').should('be.visible');
    cy.getByTestId('name').type('Instance');
    cy.getByTestId('create-project').click();
    cy.getByTestId('empty').click();
    cy.getByTestId('Others...-more').click();
    cy.getByTestId('new-object').click();
    cy.getByTestId('domain').children('[role="button"]').invoke('text').should('have.length.gt', 1);
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

  it('remembers the last tool invoked from a tool section', () => {
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

    // Open the palette of the "Root" entity.
    cy.getByTestId('rf__wrapper').findByTestId('Label - Root').click();
    cy.getByTestId('Palette').should('exist');
    // It should have the "Text attribute" tool as the default in its first tool section
    cy.getByTestId('Text - Tool').should('exist');
    cy.getByTestId('Boolean - Tool').should('not.exist');

    // Expand the palette and invoke the "Boolean attribute" tool
    cy.getByTestId('expand').click();
    cy.getByTestId('Boolean - Tool').click();
    // Check the attribute has been created
    cy.getByTestId('rf__wrapper').findByTestId('Label - newBoolean').should('exist');

    // Open the palette of the "Root" entity.
    // It should now have the "Boolean attribute" tool as the default in its first tool section
    cy.getByTestId('rf__wrapper').findByTestId('Label - Root').click();
    cy.getByTestId('Palette').should('exist');
    cy.getByTestId('Text - Tool').should('not.exist');
    cy.getByTestId('Boolean - Tool').should('exist');
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
});
