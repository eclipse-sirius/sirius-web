/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

export class Explorer {
  getExplorerView() {
    return cy.getByTestId('explorer://');
  }

  getTreeItemByLabel(treeItemLabel) {
    return this.getExplorerView().contains('[data-treeitemid]', treeItemLabel);
  }

  getTreeItems() {
    return this.getExplorerView().find('[data-treeitemid]');
  }

  getSelectedTreeItems() {
    return this.getExplorerView().find('[data-treeitemid][data-testid="selected"]');
  }

  expand(treeItemLabel) {
    this.getTreeItemByLabel(treeItemLabel).should('have.attr', 'data-expanded', 'false');
    this.getTreeItemByLabel(treeItemLabel).dblclick();
  }

  collapse(treeItemLabel) {
    this.getTreeItemByLabel(treeItemLabel).should('have.attr', 'data-expanded', 'true');
    this.getTreeItemByLabel(treeItemLabel).dblclick();
  }

  createRepresentation(treeItemLabel, representationDescriptionName, representationLabel) {
    this.getTreeItemByLabel(treeItemLabel).find('button').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();
    cy.getByTestId('name').clear();
    cy.getByTestId('name').type(representationLabel);
    cy.getByTestId('representationDescription').click();
    cy.getByTestId(representationDescriptionName).click();
    cy.getByTestId('create-representation').click();
  }

  rename(treeItemLabel, newName) {
    this.getTreeItemByLabel(treeItemLabel).find('button').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('rename-tree-item').click();
    cy.getByTestId('name-edit').should('exist');
    cy.getByTestId('name-edit').get('input').should('have.value', treeItemLabel);
    cy.getByTestId('name-edit').type(`${newName}{enter}`);
  }
}
