/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
export interface CreatedRepresentationData {
  representationId: string;
}

export class Explorer {
  public getExplorerView(): Cypress.Chainable<JQuery<HTMLElement>> {
    return cy.getByTestId('explorer://');
  }

  public getTreeItemByLabel(treeItemLabel: string): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getExplorerView().get(`[data-treeitemlabel="${treeItemLabel}"]`);
  }

  public getTreeItemContainingLabel(treeItemLabel: string): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getExplorerView().get(`[data-treeitemlabel~="${treeItemLabel}"]`);
  }

  public getTreeItems(): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getExplorerView().find('[data-treeitemid]');
  }

  public getSelectedTreeItems(): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getExplorerView().find('[data-treeitemid][data-testid="selected"]');
  }

  public revealGlobalSelectionInExplorer(): void {
    cy.getByTestId('explorer-reveal-selection-button').click();
  }

  public expandWithDoubleClick(treeItemLabel: string): void {
    this.getTreeItemByLabel(treeItemLabel).should('have.attr', 'data-expanded', 'false');
    this.getTreeItemByLabel(treeItemLabel).dblclick();
  }

  public collapseWithDoubleClick(treeItemLabel: string): void {
    this.getTreeItemByLabel(treeItemLabel).should('have.attr', 'data-expanded', 'true');
    this.getTreeItemByLabel(treeItemLabel).dblclick();
  }

  public createRootObject(documentTreeItemLabel: string, domain: string, entity: string) {
    this.openTreeItemAction(documentTreeItemLabel).getNewObjectButton().click();

    cy.getByTestId('create-object').should('not.be.disabled');

    cy.getByTestId('domain').click();
    cy.getByTestId('domain').get(`[data-value="domain://${domain}"]`).click();

    cy.getByTestId('type').click();
    cy.getByTestId('type').get(`[data-value="${entity}`).should('exist').click();

    cy.getByTestId('create-object').click();
  }

  public createObject(objectTreeItemLabel: string, childCreationDescriptionLabel: string) {
    this.openTreeItemAction(objectTreeItemLabel).getNewObjectButton().click();

    cy.getByTestId('childCreationDescription').children('[role="combobox"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('childCreationDescription').click();
    cy.getByTestId('childCreationDescription')
      .get(`[data-value="${childCreationDescriptionLabel}"]`)
      .should('exist')
      .click();
    cy.getByTestId('create-object').click();
    cy.getByTestId('new-object-modal').should('not.exist');
  }

  public select(treeItemLabel: string, multiSelection: boolean = false): void {
    this.getTreeItemByLabel(treeItemLabel).should('exist');
    this.getTreeItemByLabel(treeItemLabel).click({ ctrlKey: multiSelection });
  }

  public toggle(treeItemLabel: string): void {
    this.getTreeItemByLabel(treeItemLabel).should('exist');
    this.getTreeItemByLabel(treeItemLabel).getByTestId(`${treeItemLabel}-toggle`).click();
  }

  public selectRepresentation(treeItemLabel: string): void {
    this.getTreeItemByLabel(treeItemLabel).should('exist');
    this.getTreeItemByLabel(treeItemLabel).click();
    cy.getByTestId('representation-area').should('exist');
  }

  public createRepresentation(
    treeItemLabel: string,
    representationDescriptionName: string,
    representationLabel: string
  ): Cypress.Chainable<CreatedRepresentationData> {
    this.openTreeItemAction(treeItemLabel).getNewRepresentationButton().click();

    cy.get('[aria-labelledby="dialog-title"]').then((modal) => {
      cy.wrap(modal).findByTestId('name').clear();
      cy.wrap(modal).findByTestId('name').type(representationLabel);
      cy.wrap(modal).findByTestId('representationDescription').click();
      // Make sure we search from inside the select's choices, which is not inside the modal
      cy.get('.MuiPopover-root').findByTestId(representationDescriptionName).click();
      cy.wrap(modal).findByTestId('create-representation').click();
    });

    // Wait for the modal to be closed and the representation actually opened
    cy.get('[aria-labelledby="dialog-title"]').should('not.exist');
    cy.getByTestId(`representation-tab-${representationLabel}`).should('be.visible');

    return cy
      .getByTestId(`representation-tab-${representationLabel}`)
      .should('exist')
      .invoke('attr', 'data-representationid')
      .then((representationId) => {
        if (representationId) {
          return cy.wrap({ representationId });
        } else {
          throw new Error(`Cannot find opened representation tab with label "${representationLabel}"`);
        }
      });
  }

  public rename(treeItemLabel: string, newName: string): void {
    this.openTreeItemAction(treeItemLabel).getRenameItemButton().click();
    cy.getByTestId('name-edit');
    cy.getByTestId('name-edit').get('input').should('have.value', treeItemLabel);
    cy.getByTestId('name-edit').type(`${newName}{enter}`);
    cy.getByTestId('name-edit').should('not.exist');
    this.getTreeItemByLabel(newName).should('exist');
  }

  public delete(treeItemLabel: string): void {
    this.openTreeItemAction(treeItemLabel).getDeleteItemButton().click();
  }

  public dragTreeItem(treeItemLabel: string, dataTransfer: DataTransfer): void {
    this.getTreeItemByLabel(treeItemLabel).trigger('dragstart', { dataTransfer });
  }

  public dopOnTreeItem(treeItemLabel: string, dataTransfer: DataTransfer): void {
    this.getTreeItemByLabel(treeItemLabel).trigger('drop', { dataTransfer });
  }

  public createNewModel(modelName: string, modelType: string): void {
    cy.getByTestId('new-model').should('exist');
    cy.getByTestId('tree-filter-menu-icon').should('exist'); // trick to avoid error if this menu is not render yet
    cy.getByTestId('new-model').click();
    cy.getByTestId('create-new-model').findByTestId('name-input').type(modelName);
    cy.getByTestId('create-new-model').findByTestId('stereotype').click();
    cy.get('li').filter(`:contains("${modelType}")`).click();
    cy.getByTestId('create-new-model').findByTestId('create-document').click();
  }

  public openTreeItemAction(treeItemLabel: string): ExplorerTreeItemActions {
    cy.getByTestId(`${treeItemLabel}-more`).should('be.visible');
    cy.getByTestId(`${treeItemLabel}-more`).click();
    return new ExplorerTreeItemActions();
  }
}

/**
 * All actions may not be available for all items.
 */
class ExplorerTreeItemActions {
  public getTreeItemMenu(): Cypress.Chainable<JQuery<HTMLElement>> {
    return cy.getByTestId('treeitem-contextmenu');
  }

  public getNewObjectButton(): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getTreeItemMenu().findByTestId('new-object');
  }

  public getNewRepresentationButton(): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getTreeItemMenu().findByTestId('new-representation');
  }

  public getRenameItemButton(): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getTreeItemMenu().findByTestId('rename-tree-item');
  }

  public getDeleteItemButton(): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getTreeItemMenu().findByTestId('delete');
  }
}
