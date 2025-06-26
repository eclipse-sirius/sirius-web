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

export class ImpactAnalysis {
  public getImpactAnalysisDialog(): Cypress.Chainable<JQuery<HTMLElement>> {
    return cy.getByTestId('impact-analysis-dialog');
  }

  public cancelExecution(): void {
    cy.getByTestId('impact-analysis-dialog-button-cancel').click();
  }

  public confirmExecution(): void {
    cy.getByTestId('impact-analysis-dialog-button-ok').click();
  }

  public getNbElementCreated(): Cypress.Chainable<JQuery<HTMLElement>> {
    return cy.getByTestId('impact-analysis-report-nbElementCreated').should('exist');
  }

  public getNbElementDeleted(): Cypress.Chainable<JQuery<HTMLElement>> {
    return cy.getByTestId('impact-analysis-report-nbElementDeleted').should('exist');
  }

  public getNbElementModified(): Cypress.Chainable<JQuery<HTMLElement>> {
    return cy.getByTestId('impact-analysis-report-nbElementModified').should('exist');
  }

  public getAdditionalReportData(index: number): Cypress.Chainable<JQuery<HTMLElement>> {
    return cy.getByTestId(`additionalReportData_${index}`);
  }
}
