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

export class Workbench {
  openRepresentation(representationLabel) {
    cy.getByTestId(`onboard-open-${representationLabel}`).click();
  }

  closeRepresentation(representationLabel) {
    cy.getByTestId(`close-representation-tab-${representationLabel}`).click();
  }

  performAction(actionLabel) {
    cy.get('[data-testid="onboard-area"]')
      .find('[data-testid="actions"]')
      .contains(new RegExp('^' + actionLabel + '$', 'g'))
      .click();
  }
}
