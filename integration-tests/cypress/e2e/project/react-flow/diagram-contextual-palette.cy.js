/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
    cy.getByTestId('Fade-elements').should('exist').click({ force: true });
    cy.getByTestId(elementTestId).should('have.css', 'opacity', '0.4');
  };

  const hideByElementTestId = (elementTestId) => {
    cy.getByTestId(elementTestId).then((elementBefore) => {
      const countBefore = elementBefore.length ?? 0;
      cy.getByTestId(elementTestId).first().click({ force: true });
      cy.getByTestId('Hide-elements').should('exist').click({ force: true });
      cy.getByTestId('Hide-elements').should('not.exist');
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
    cy.getByTestId('name').type('Topography__REACT_FLOW');
    cy.getByTestId('representationDescription').click();
    cy.getByTestId('Topography with auto layout').click();
    cy.getByTestId('create-representation').click();
    cy.getByTestId('Topography__REACT_FLOW').click();
  };

  beforeEach(() => {
    cy.deleteAllProjects();
  });

  it('test Hide/Fade action is not available on diagram background', () => {
    createFlowReactFlowDiagram();
    cy.getByTestId('rf__wrapper').findByTestId('Label - CaptureSubSystem').should('exist').click('topLeft');
    cy.getByTestId('Hide-elements').should('exist');
    cy.getByTestId('Fade-elements').should('exist');
    cy.getByTestId('rf__wrapper').click('bottomLeft');
    // NOTE for later: ensure the palette is displayed
    cy.getByTestId('Hide-elements').should('not.exist');
    cy.getByTestId('Fade-elements').should('not.exist');
  });

  it('can fade any type of nodes', () => {
    createFlowReactFlowDiagram();
    fadeByElementTestId('IconLabel - Temperature: 25');
    fadeByElementTestId('Image - Motion_Engine');
    fadeByElementTestId('Rectangle - Central_Unit');
    fadeByElementTestId('List - Description');
  });

  it('can hide any type of nodes', () => {
    createFlowReactFlowDiagram();
    cy.getByTestId('Label - Temperature: 25').should('have.length', 1);
    cy.getByTestId('Label - Temperature: 28').click();
    cy.getByTestId('form').findByTestId('input-Temperature').should('not.be.disabled');
    cy.getByTestId('form').findByTestId('input-Temperature').should('have.attr', 'value', '28');
    cy.getByTestId('form').findByTestId('Temperature').type('{selectall}').type('25').type('{enter}');
    cy.getByTestId('Label - Temperature: 25').should('have.length', 2);
    hideByElementTestId('Label - Temperature: 25');
    hideByElementTestId('Image - Motion_Engine');
    hideByElementTestId('List - Description');
    hideByElementTestId('Rectangle - Central_Unit');
  });

  it('can not open multi tool section in the same time', () => {
    cy.createProjectFromTemplate('studio-template').then((res) => {
      const projectId = res.body.data.createProjectFromTemplate.project.id;
      cy.visit(`/projects/${projectId}/edit`);
    });
    cy.getByTestId('ViewNewModel').dblclick();
    cy.getByTestId('View').dblclick();
    cy.get('[data-testid$=" Diagram Description"]').dblclick();
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
    cy.getByTestId('name').type('__REACT_FLOW');
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
});
