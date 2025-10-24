/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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

import { RenameProjectDialog } from './RenameProject';

export class Project {
  public visit(
    projectId: string,
    representationId?: string,
    options?: Partial<Cypress.VisitOptions>
  ): Cypress.Chainable<Cypress.AUTWindow> {
    return cy.visit(`/projects/${projectId}/edit${representationId ? '/' + representationId : ''}`, {
      ...options,
      onBeforeLoad(win) {
        cy.spy(win.console, 'debug').as('consoleDebug');
      },
    });
  }
  public disableDeletionConfirmationDialog(): void {
    cy.window().then((win) => {
      win.localStorage.setItem('sirius-confirmation-dialog-disabled', JSON.stringify(true));
    });
  }
  public enableDeletionConfirmationDialog(): void {
    cy.window().then((win) => {
      win.localStorage.setItem('sirius-confirmation-dialog-disabled', JSON.stringify(false));
    });
  }

  public rename(newProjectName: string): void {
    this.isLoaded();
    const renameDialog = this.getProjectNavigationBar().getRenameDialog();
    renameDialog.clearValue();
    renameDialog.getRenameTextField().type(newProjectName);
    renameDialog.validate(true);
    renameDialog.getRenameButton().click();
  }

  public getProjectNavigationBar(): ProjectNavigationBar {
    this.isLoaded();
    cy.getByTestId(`navbar-title`).should('be.visible');
    cy.getByTestId('navigation-bar').findByTestId('more').click();
    return new ProjectNavigationBar();
  }

  private isLoaded(): void {
    cy.get('@consoleDebug').should('be.calledWith', 'query getProjectAndRepresentation: response received');
    cy.get('@consoleDebug').should('be.calledWith', 'query getOnboardData: response received');
    cy.get('@consoleDebug').should('be.calledWith', 'query getRepresentationMetadata: response received');
    cy.get('@consoleDebug').should('be.calledWith', 'query getAllTreeFilters: response received');
    cy.get('@consoleDebug').should('be.calledWith', 'query getAllExplorerDescriptions: response received');
  }
}

class ProjectNavigationBar {
  public getDownloadLink(): Cypress.Chainable<JQuery<HTMLElement>> {
    return cy.getByTestId('download-link');
  }

  public getRenameButton(): Cypress.Chainable<JQuery<HTMLElement>> {
    return cy.getByTestId('rename');
  }

  public getRenameDialog(): RenameProjectDialog {
    this.getRenameButton().should('exist').click();
    return new RenameProjectDialog();
  }

  public getShareButton(): Cypress.Chainable<JQuery<HTMLElement>> {
    return cy.getByTestId('share');
  }

  public getShareDialog(): ShareProjectDialog {
    this.getShareButton().should('exist').click();
    return new ShareProjectDialog();
  }

  public getDeleteButton(): Cypress.Chainable<JQuery<HTMLElement>> {
    return cy.getByTestId('delete');
  }

  public getSettingsButton(): Cypress.Chainable<JQuery<HTMLElement>> {
    return cy.getByTestId('project-settings-link');
  }
}

class ShareProjectDialog {
  public getSharePathTextField(): Cypress.Chainable<JQuery<HTMLElement>> {
    return cy.getByTestId('share-path');
  }
}
