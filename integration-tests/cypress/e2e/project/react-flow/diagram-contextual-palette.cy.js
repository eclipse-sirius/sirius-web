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

  beforeEach(() => {
    cy.deleteAllProjects();
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
  });

  it('test Hide/Fade action is not available on diagram background', () => {
    cy.getByTestId('Label - CaptureSubSystem').click('topLeft');
    cy.getByTestId('Hide-elements').should('exist');
    cy.getByTestId('Fade-elements').should('exist');
    cy.getByTestId('rf__wrapper').click();
    cy.getByTestId('Hide-elements').should('not.exist');
    cy.getByTestId('Fade-elements').should('not.exist');
  });

  it('can fade any type of nodes', () => {
    fadeByElementTestId('IconLabel - Temperature: 25');
    fadeByElementTestId('Image - Motion_Engine');
    fadeByElementTestId('Rectangle - Central_Unit');
    fadeByElementTestId('List - Description');
  });

  it('can hide any type of nodes', () => {
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
});
