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

export class UploadProject {
  public visit(): Cypress.Chainable<Cypress.AUTWindow> {
    return cy.visit('/upload/project');
  }

  public getFileInput(): Cypress.Chainable<JQuery<HTMLElement>> {
    return cy.getByTestId('file');
  }

  public getUploadProjectButton(): Cypress.Chainable<JQuery<HTMLElement>> {
    return cy.getByTestId('upload-project');
  }
}
