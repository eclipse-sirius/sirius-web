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

import { Explorer } from './Explorer';

export class Portal {
  public getPortal(): Cypress.Chainable<JQuery<HTMLElement>> {
    return cy.get('[data-representation-kind="portal"]');
  }

  public getDropArea(): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getPortal().findByTestId('portal-drop-area');
  }

  public getFrame(frameTitle: string): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getPortal().findByTestId(`representation-frame-${frameTitle}`);
  }

  public getToolbar(): Cypress.Chainable<JQuery<HTMLElement>> {
    return this.getPortal().findByTestId('portal-toolbar');
  }

  public addRepresentationFromExplorer(portalName: string, representationName: string): void {
    const explorer = new Explorer();
    explorer.getTreeItemByLabel(portalName).click();
    this.getDropArea().should('exist');

    const dataTransfer = new DataTransfer();
    explorer.getTreeItemByLabel(representationName).trigger('dragstart', { dataTransfer });
    this.getDropArea().trigger('dragenter', 'center', { dataTransfer });
    this.getDropArea().trigger('dragover', 'center', { dataTransfer });
    this.getDropArea().trigger('drop', 'center', { dataTransfer });
  }
}
