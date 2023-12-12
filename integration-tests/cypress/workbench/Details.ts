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

export class Details {
  public getDetailsView(): Cypress.Chainable<JQuery<HTMLElement>> {
    return cy.getByTestId('view-Details');
  }

  public getTextField(label: string): Cypress.Chainable<JQuery<HTMLInputElement | HTMLTextAreaElement>> {
    return this.getDetailsView().find(`[data-testid="input-${label}"]`);
  }
}
