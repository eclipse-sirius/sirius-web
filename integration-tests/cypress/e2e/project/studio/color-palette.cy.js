/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
describe('/projects/:projectId/edit - Color Palette', () => {
  beforeEach(() => {
    cy.deleteAllProjects();
    cy.createProjectFromTemplate('studio-template').then((res) => {
      const projectId = res.body.data.createProjectFromTemplate.project.id;
      cy.visit(`/projects/${projectId}/edit`);
    });
  });

  it('check the default color palette exist and populated', () => {
    cy.getByTestId('ViewNewModel-toggle').click();
    cy.getByTestId('View-toggle').click();
    cy.getByTestId('ColorPalette').should('exist');
    cy.getByTestId('ColorPalette-toggle').click();
    cy.getByTestId('ColorPalette').should('exist');
    cy.getByTestId('color_dark').should('exist');
  });

  it('can add new fixed color to palette color', () => {
    cy.getByTestId('ViewNewModel-toggle').click();
    cy.getByTestId('View-toggle').click();
    cy.getByTestId('ColorPalette-more').click();
    cy.getByTestId('new-object').click();
    cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('childCreationDescription').click().get('[data-value="Fixed Color"]').should('exist').click();
    cy.getByTestId('create-object').click();
    cy.getByTestId('FixedColor').should('exist').click();
    cy.getByTestId('Name').type('color_test{enter}');
    cy.getByTestId('Value').type('#e5f5f8{enter}');
    cy.getByTestId('color_test').should('exist');
  });

  it('can add new color palette to view', () => {
    cy.getByTestId('ViewNewModel-toggle').click();
    cy.getByTestId('View-toggle').click();
    cy.getByTestId('View-more').click();
    cy.getByTestId('new-object').click();
    cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('childCreationDescription').click().get('[data-value="Color Palette"]').should('exist').click();
    cy.getByTestId('create-object').click();
    cy.getByTestId('New Color Palette').should('exist').click();
    cy.getByTestId('Name').clear().type('OtherColorPalette{enter}');
    cy.getByTestId('OtherColorPalette-more').click();
    cy.getByTestId('new-object').click();
    cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('childCreationDescription').click().get('[data-value="Fixed Color"]').should('exist');
  });

  it('can select color from color palette in node style properties', () => {
    cy.getByTestId('ViewNewModel-toggle').click();
    cy.getByTestId('View-toggle').click();
    cy.get('[title="diagram::DiagramDescription"]').dblclick();
    cy.getByTestId('Entity1 Node-toggle').click();
    cy.get('[title="diagram::RectangularNodeStyleDescription"]').click();
    cy.getByTestId('Color').click();
    cy.getByTestId('option-color_dark').should('exist').click();
    cy.getByTestId('Border Color').click();
    cy.getByTestId('option-border_green').should('exist').click();
  });

  it('can select color from color palette in edge style properties', () => {
    cy.getByTestId('ViewNewModel-toggle').click();
    cy.getByTestId('View-toggle').click();
    cy.get('[title="diagram::DiagramDescription"]').dblclick();
    cy.getByTestId('LinkedTo Edge-toggle').click();
    cy.get('[title="diagram::EdgeStyle"]').click();
    cy.getByTestId('Color').click();
    cy.getByTestId('option-color_blue').should('exist').click();
  });
});
