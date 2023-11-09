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

Cypress.Commands.add('createInstanceFromDomainModel', (domain, withReactFlowRepresentation) => {
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
  cy.getByTestId('domain').get(`[data-value="domain://${domain}"]`).should('exist').click();
  cy.getByTestId('create-object').click();
  if (withReactFlowRepresentation) {
    cy.getByTestId('Root-more').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();
    cy.getByTestId('representationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('representationDescription').click();
    cy.get(`[data-testid="${domain} Diagram Description"]`).should('exist').click();
    cy.getByTestId('name').clear().type('diagram__REACT_FLOW');
    cy.getByTestId('create-representation').click();
  }
});
