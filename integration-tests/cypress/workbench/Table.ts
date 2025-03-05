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

export class Table {
  public getTableRepresentation(): Cypress.Chainable<JQuery<HTMLElement>> {
    return cy.getByTestId('table-representation');
  }

  public typeGlobalSearch(globalSearch: string): void {
    this.getTableRepresentation()
      .find('input[placeholder="Search"]')
      .then(($input) => {
        cy.wrap($input).type(globalSearch);
      });
  }

  public resetGlobalSearchValue(): void {
    this.getTableRepresentation().find('button[aria-label="Clear search"]').click();
  }

  public checkRowCount(expectedRowCount: number): void {
    cy.getByTestId('table-row-header').should('have.length', expectedRowCount);
  }

  public changeRowPerPage(pageSize: number): void {
    cy.getByTestId('cursor-based-pagination-size').click();
    cy.get('ul[role="listbox"]').contains(pageSize).click();
    cy.getByTestId('cursor-based-pagination-size').should('contain', '5');
  }

  public navigateNextPage(): void {
    cy.getByTestId('pagination-next').click();
  }
}
