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
export class Form {
  public getForm(): Cypress.Chainable<JQuery<HTMLElement>> {
    return cy.get(`[data-representation-kind="form"]`);
  }

  public getWidget(widgetLabel: string): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getForm().findByTestId(widgetLabel);
  }

  public getWidgetElement(widgetLabel: string, elementLabel: string): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getWidget(widgetLabel).findByTestId(elementLabel);
  }
}
