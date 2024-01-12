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

export class Deck {
  public getDeckRepresentation(): Cypress.Chainable<JQuery<HTMLElement>> {
    return cy.getByTestId('deck-representation');
  }

  public getLane(laneLabel: string): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getDeckRepresentation().findByTestId(`lane-${laneLabel}`);
  }

  public getCard(laneLabel: string, cardLabel): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getLane(laneLabel).findByTestId(`card-${cardLabel}`);
  }
}
