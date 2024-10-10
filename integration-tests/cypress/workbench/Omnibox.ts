/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

export class Omnibox {
  constructor(readonly projectName: string) {}

  private getOmnibox(): Cypress.Chainable<JQuery<HTMLElement>> {
    return cy.getByTestId('omnibox');
  }

  public display(): Cypress.Chainable<JQuery<HTMLElement>> {
    cy.getByTestId(`navbar-${this.projectName}`).should('exist');
    cy.get('body').type('{ctrl+k}');
    cy.getByTestId('omnibox').should('exist');
    return this.getOmnibox();
  }

  public sendQuery(query: string, hasResult: boolean = true): Cypress.Chainable<JQuery<HTMLElement>> {
    if (query !== '') {
      this.getOmnibox().find('.MuiInputBase-input').type(`${query}`);
    }
    this.getOmnibox().findByTestId('submit-query-button').should('not.be.disabled');
    this.getOmnibox().find('.MuiInputBase-input').type('{enter}');

    this.getOmnibox().find('.MuiList-root').findByTestId('fetch-omnibox-result').should('not.exist');
    if (hasResult) {
      this.getOmnibox().find('.MuiList-root').findByTestId('omnibox-no-result').should('not.exist');
    } else {
      this.getOmnibox().find('.MuiList-root').findByTestId('omnibox-no-result').should('exist');
    }

    this.getOmnibox().findByTestId('submit-query-button').should('be.disabled');

    return this.getOmnibox().find('.MuiList-root');
  }

  public shouldBeClosed(): void {
    cy.getByTestId('omnibox').should('not.exist');
  }
}
