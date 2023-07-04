/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
describe('/projects/:projectId/edit - Tree filter bar', () => {
  beforeEach(() => {
    cy.deleteAllProjects();
    cy.createProject('Cypress Project').then((res) => {
      const projectId = res.body.data.createProject.project.id;
      const robot_flow_id = 'c26b6086-b444-3ee6-b8cd-9a4fde5956a7';
      cy.createDocument(projectId, robot_flow_id, 'flow').then(() => {
        cy.visit(`/projects/${projectId}/edit`);
      });
    });
  });

  it('open/close filter bar', () => {
    cy.getByTestId('filterbar-textfield').should('not.exist');
    cy.getByTestId('flow').type('{ctrl+f}');
    cy.getByTestId('filterbar-textfield').should('exist');
    cy.getByTestId('filterbar-close-button').click();
    cy.getByTestId('filterbar-textfield').should('not.exist');
  });

  it('type text in filter bar', () => {
    cy.getByTestId('flow').dblclick();
    cy.getByTestId('Robot').dblclick();
    cy.getByTestId('Central_Unit').dblclick();
    cy.getByTestId('CaptureSubSystem').dblclick();
    cy.getByTestId('Wifi').dblclick();

    cy.getByTestId('flow').type('{ctrl+f}');

    cy.getByTestId('Central_Unit-en-1').should('not.exist');
    cy.getByTestId('Motion_Engine-En-1').should('not.exist');
    cy.getByTestId('Engine-En-0').should('not.exist');

    cy.getByTestId('filterbar-textfield').type('en');

    cy.getByTestId('Central_Unit-en-1').should('exist');
    cy.getByTestId('Motion_Engine-En-1').should('exist');
    cy.getByTestId('Engine-En-0').should('exist');

    cy.getByTestId('filterbar-close-button').click();

    cy.getByTestId('Central_Unit-en-1').should('not.exist');
    cy.getByTestId('Motion_Engine-En-1').should('not.exist');
    cy.getByTestId('Engine-En-0').should('not.exist');
  });

  it('test special characters do not break the filter bar ([, *, ), ^, ...)', () => {
    cy.getByTestId('flow').dblclick();
    cy.getByTestId('Robot').dblclick();
    cy.getByTestId('Central_Unit').dblclick();
    cy.getByTestId('CaptureSubSystem').dblclick();
    cy.getByTestId('Wifi').dblclick();

    cy.getByTestId('flow').type('{ctrl+f}');

    cy.getByTestId('filterbar-textfield').type('[');
    cy.getByTestId('filterbar-filter-button').click();
    cy.getByTestId('flow').should('exist');

    cy.getByTestId('filterbar-textfield').type('^');
    cy.getByTestId('filterbar-filter-button').click();
    cy.getByTestId('flow').should('exist');

    cy.getByTestId('filterbar-textfield').type(')');
    cy.getByTestId('filterbar-filter-button').click();
    cy.getByTestId('flow').should('exist');

    cy.getByTestId('filterbar-textfield').type('*');
    cy.getByTestId('filterbar-filter-button').click();
    cy.getByTestId('flow').should('exist');

    cy.getByTestId('filterbar-close-button').click();
  });

  it('filter button in filter bar', () => {
    cy.getByTestId('flow').dblclick();
    cy.getByTestId('Robot').dblclick();
    cy.getByTestId('Central_Unit').dblclick();
    cy.getByTestId('CaptureSubSystem').dblclick();
    cy.getByTestId('Wifi').dblclick();

    cy.getByTestId('flow').type('{ctrl+f}');

    cy.getByTestId('flow').should('exist');
    cy.getByTestId('Robot').should('exist');
    cy.getByTestId('Central_Unit').should('exist');
    cy.getByTestId('DSP').should('exist');
    cy.getByTestId('Motion_Engine').should('exist');
    cy.getByTestId('active').should('exist');
    cy.getByTestId('CaptureSubSystem').should('exist');
    cy.getByTestId('Radar_Capture').should('exist');
    cy.getByTestId('Back_Camera').should('exist');
    cy.getByTestId('Radar').should('exist');
    cy.getByTestId('high').should('not.exist');
    cy.getByTestId('Engine').should('exist');
    cy.getByTestId('GPU').should('exist');
    cy.getByTestId('active').should('exist');
    cy.getByTestId('Wifi').should('exist');
    cy.getByTestId('standard').should('exist');

    cy.getByTestId('filterbar-textfield').type('en');
    cy.getByTestId('filterbar-filter-button').click();

    cy.getByTestId('flow').should('exist');
    cy.getByTestId('Robot').should('exist');
    cy.getByTestId('Central_Unit').should('exist');
    cy.getByTestId('DSP').should('exist');
    cy.getByTestId('Motion_Engine').should('exist');
    cy.getByTestId('active').should('not.exist');
    cy.getByTestId('CaptureSubSystem').should('exist');
    cy.getByTestId('Radar_Capture').should('exist');
    cy.getByTestId('Back_Camera').should('exist');
    cy.getByTestId('Radar').should('exist');
    cy.getByTestId('high').should('not.exist');
    cy.getByTestId('Engine').should('exist');
    cy.getByTestId('GPU').should('exist');
    cy.getByTestId('active').should('not.exist');
    cy.getByTestId('Wifi').should('not.exist');
    cy.getByTestId('standard').should('not.exist');

    cy.getByTestId('Radar').dblclick();
    cy.getByTestId('Radar').should('not.exist');

    cy.getByTestId('filterbar-filter-button').click();

    cy.getByTestId('flow').should('exist');
    cy.getByTestId('Robot').should('exist');
    cy.getByTestId('Central_Unit').should('exist');
    cy.getByTestId('DSP').should('exist');
    cy.getByTestId('Motion_Engine').should('exist');
    cy.getByTestId('active').should('exist');
    cy.getByTestId('CaptureSubSystem').should('exist');
    cy.getByTestId('Radar_Capture').should('exist');
    cy.getByTestId('Back_Camera').should('exist');
    cy.getByTestId('Radar').should('exist');
    cy.getByTestId('high').should('exist');
    cy.getByTestId('Engine').should('exist');
    cy.getByTestId('GPU').should('exist');
    cy.getByTestId('active').should('exist');
    cy.getByTestId('Wifi').should('exist');
    cy.getByTestId('standard').should('exist');
  });
});
