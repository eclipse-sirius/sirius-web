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

export class Explorer {
  public getExplorerView(): Cypress.Chainable<JQuery<HTMLElement>> {
    return cy.getByTestId('explorer://');
  }

  public getTreeItemByLabel(treeItemLabel: string): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getExplorerView().get(`[data-treeitemlabel="${treeItemLabel}"]`);
  }

  public getTreeItems(): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getExplorerView().find('[data-treeitemid]');
  }

  public getSelectedTreeItems(): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getExplorerView().find('[data-treeitemid][data-testid="selected"]');
  }

  public expand(treeItemLabel: string): void {
    this.getTreeItemByLabel(treeItemLabel).should('have.attr', 'data-expanded', 'false');
    this.getTreeItemByLabel(treeItemLabel).dblclick();
  }

  public collapse(treeItemLabel: string): void {
    this.getTreeItemByLabel(treeItemLabel).should('have.attr', 'data-expanded', 'true');
    this.getTreeItemByLabel(treeItemLabel).dblclick();
  }

  public createRootObject(documentTreeItemLabel: string, domain: string, entity: string) {
    this.getTreeItemByLabel(documentTreeItemLabel).find('button').click();
    cy.getByTestId('new-object').click();

    cy.getByTestId('create-object').should('not.be.disabled');

    cy.getByTestId('domain').click();
    cy.getByTestId('domain').get(`[data-value="domain://${domain}"]`).click();

    cy.getByTestId('type').click();
    cy.getByTestId('type').get(`[data-value="${entity}`).should('exist').click();

    cy.getByTestId('create-object').click();
  }

  public createObject(objectTreeItemLabel: string, childCreationDescriptionLabel: string) {
    this.getTreeItemByLabel(objectTreeItemLabel).find('button').click();
    cy.getByTestId('new-object').click();

    cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('childCreationDescription').click();
    cy.getByTestId('childCreationDescription')
      .get(`[data-value="${childCreationDescriptionLabel}"]`)
      .should('exist')
      .click();
    cy.getByTestId('create-object').click();
  }

  public createRepresentation(
    treeItemLabel: string,
    representationDescriptionName: string,
    representationLabel: string
  ): void {
    this.getTreeItemByLabel(treeItemLabel).find('button').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();
    cy.getByTestId('name').clear();
    cy.getByTestId('name').type(representationLabel);
    cy.getByTestId('representationDescription').click();
    cy.getByTestId(representationDescriptionName).click();
    cy.getByTestId('create-representation').click();
  }

  public rename(treeItemLabel: string, newName: string): void {
    this.getTreeItemByLabel(treeItemLabel).find('button').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('rename-tree-item').click();
    cy.getByTestId('name-edit');
    cy.getByTestId('name-edit').get('input').should('have.value', treeItemLabel);
    cy.getByTestId('name-edit').type(`${newName}{enter}`);
  }

  public delete(treeItemLabel: string): void {
    this.getTreeItemByLabel(treeItemLabel).find('button').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('delete').click();
  }
}
