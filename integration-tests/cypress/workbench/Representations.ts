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

export class Representations {
  public showRepresentationsView(): void {
    cy.getByTestId('viewselector-Representations').click();
  }

  public getRepresentationsView(): Cypress.Chainable<JQuery<HTMLElement>> {
    return cy.getByTestId('view-Representations');
  }

  public hasTreeItems(expectedItems: string[]): void {
    this.getRepresentationsView().then((result) => {
      cy.wrap(result)
        .find('ul[role="tree"]')
        .then((result) => {
          expectedItems.forEach((itemText) => {
            cy.wrap(result).should('contain.text', itemText);
          });
        });
    });
  }
}
