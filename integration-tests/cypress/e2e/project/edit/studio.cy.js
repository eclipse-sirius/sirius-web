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
describe('/projects/:projectId/edit - Studio', () => {
  beforeEach(() => {
    cy.deleteAllProjects();
    cy.createProjectFromTemplate('studio-template').then((res) => {
      const projectId = res.body.data.createProjectFromTemplate.project.id;
      cy.visit(`/projects/${projectId}/edit`);
    });
  });

  it('Check the label position of an empty edge', () => {
    cy.getByTestId('onboard-open-Domain').dblclick();
    // clear the edge label value
    cy.getByTestId('Label - entity2s [0..*]').click();
    cy.getByTestId('Close - Tool').click();
    cy.getByTestId('Name').type('{selectAll}{backspace}');
    cy.getByTestId('Optional').click();
    cy.getByTestId('Many').click();
    // arrange all
    cy.getByTestId('arrange-all').click();
    // wait for the arrange all to finish
    cy.wait(3000).then(() => {
      // the label should be translated 2 times
      cy.getByTestId('Label - ')
        .should('have.attr', 'transform')
        .then((value) => expect(value).to.match(/translate\(.*, .*\) translate\(.*, .*\)/));
    });
  });

  it('Check the new object domain list', () => {
    cy.getByTestId('DomainNewModel-more').click();
    cy.getByTestId('new-object').click();
    cy.getByTestId('domain').find('input').invoke('val').should('not.be.empty');

    cy.getByTestId('domain').click();
    cy.get('[data-value="http://www.eclipse.org/sirius-web/domain"]').should('exist');
    cy.getByTestId('domain').get('[data-value="http://www.eclipse.org/sirius-web/view"]').should('exist');
    cy.getByTestId('domain').get('[data-value="http://www.obeo.fr/dsl/designer/sample/flow"]').should('exist');
  });
});
