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

export class RenameProjectDialog {
  public getInnerRenameTextField(): Cypress.Chainable<JQuery<HTMLElement>> {
    return cy.getByTestId('rename-project-dialog').findByTestId('inner-rename-textfield');
  }

  public getRenameTextField(): Cypress.Chainable<JQuery<HTMLElement>> {
    return cy.getByTestId('rename-project-dialog').findByTestId('rename-textfield');
  }

  public getRenameButton(): Cypress.Chainable<JQuery<HTMLElement>> {
    return cy.getByTestId('rename-project-dialog').findByTestId('rename-project');
  }

  public clearValue(): void {
    this.getInnerRenameTextField().clear();
  }

  public validate(shouldBeValid: boolean): void {
    if (shouldBeValid) {
      this.getRenameTextField().find('p').should('not.have.class', 'Mui-error');
      this.getRenameButton().should('be.enabled');
    } else {
      this.getRenameTextField().find('p').should('have.class', 'Mui-error');
      this.getRenameButton().should('be.disabled');
    }
  }
}
