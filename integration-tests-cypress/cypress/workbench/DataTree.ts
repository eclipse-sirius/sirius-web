/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
export class DataTree {
  public getDataTree(): Cypress.Chainable<JQuery<HTMLElement>> {
    return cy.getByTestId('datatree');
  }

  public getTreeItemByLabel(treeItemLabel: string): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getDataTree().get(`[data-datatreeitemlabel="${treeItemLabel}"]`);
  }

  public getTreeItemContainingLabel(treeItemLabel: string): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getDataTree().get(`[data-datatreeitemlabel~="${treeItemLabel}"]`);
  }

  public getTreeItems(): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getDataTree().find('[data-datatreeitemid]');
  }

  public expandWithDoubleClick(treeItemLabel: string): void {
    this.getTreeItemByLabel(treeItemLabel).should('have.attr', 'aria-expanded', 'false');
    this.getTreeItemByLabel(treeItemLabel).dblclick();
  }

  public collapseWithDoubleClick(treeItemLabel: string): void {
    this.getTreeItemByLabel(treeItemLabel).should('have.attr', 'aria-expanded', 'true');
    this.getTreeItemByLabel(treeItemLabel).dblclick();
  }
}
