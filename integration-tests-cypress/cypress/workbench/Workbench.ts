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

type WorkbenchSide = 'left' | 'right';
type PanelState = 'expanded' | 'collapsed';
type RepresentationEditor = {
  representationLabel: string;
};

export class Workbench {
  public openRepresentation(representationLabel: string): void {
    cy.getByTestId(`onboard-open-${representationLabel}`).click();
  }

  public closeRepresentation(representationLabel: string): void {
    cy.getByTestId(`close-representation-tab-${representationLabel}`).click();
  }

  public performAction(actionLabel: string): void {
    cy.get('[data-testid="onboard-area"]')
      .find('[data-testid="actions"]')
      .contains(new RegExp('^' + actionLabel + '$', 'g'))
      .click();
  }

  public showTab(label: string): void {
    cy.getByTestId(`representation-tab-${label}`).click();
  }

  public closeTab(label: string): void {
    cy.getByTestId(`close-representation-tab-${label}`).click();
  }

  public checkPanelContent(side: WorkbenchSide, viewTitles: string[]) {
    viewTitles.forEach((viewTitle) => {
      cy.getByTestId(`site-${side}`).findByTestId(`view-${viewTitle}`);
    });
  }

  public selectPanelView(side: WorkbenchSide, viewTitle: string) {
    cy.getByTestId(`sidebar-${side}`).findByTestId(`viewselector-${viewTitle}`).should('exist').click();
  }

  private capitalize(val: string) {
    return val.charAt(0).toUpperCase() + val.slice(1);
  }

  public isIconHighlighted(side: WorkbenchSide, viewTitle: string, selected: boolean = true) {
    cy.getByTestId(`sidebar-${side}`)
      .findByTestId(`viewselector-${viewTitle}`)
      .invoke('attr', 'class')
      .then((classList) =>
        classList
          ?.split(' ')
          .some((className) =>
            selected
              ? className.endsWith(`-viewSelectorIconSelected${this.capitalize(side)}`)
              : className.endsWith(`viewSelectorIcon${this.capitalize(side)}`)
          )
      );
  }

  public checkPanelState(side: WorkbenchSide, state: PanelState) {
    if (state === 'expanded') {
      cy.get(`#${side}`).should(($panel) => {
        expect($panel).attr('data-panel-size').not.equal('0.0');
      });
    } else {
      cy.get(`#${side}`).should('have.attr', 'data-panel-size', '0.0');
    }
  }

  public checkRepresentationEditorTabs(expectedRepresentationEditors: RepresentationEditor[]) {
    if (expectedRepresentationEditors.length == 0) {
      cy.getByTestId(`onboard-area`).should('exist');
    } else {
      cy.getByTestId(`onboard-area`).should('not.exist');

      expectedRepresentationEditors.forEach((expectedRepresentationEditor) => {
        cy.getByTestId(`representation-tab-${expectedRepresentationEditor.representationLabel}`).should('exist');
      });
    }
  }
}
