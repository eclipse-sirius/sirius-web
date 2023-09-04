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
    cy.getByTestId('domain').find('div').first().should('not.have.attr', 'aria-disabled');
    cy.getByTestId('domain').click();
    cy.get('[data-value="http://www.eclipse.org/sirius-web/domain"]').should('exist');
    cy.getByTestId('domain').get('[data-value="http://www.eclipse.org/sirius-web/view"]').should('exist');
    cy.getByTestId('domain').get('[data-value="http://www.obeo.fr/dsl/designer/sample/flow"]').should('exist');
  });

  it('Check the DiagramPalette toolSection creation', () => {
    cy.getByTestId('ViewNewModel').dblclick();
    cy.getByTestId('View').dblclick();
    cy.get('[data-testid$=" Diagram Description"]').dblclick();
    cy.getByTestId('DiagramPalette').should('exist');
    cy.getByTestId('DiagramPalette-more').click();
    cy.getByTestId('new-object').click();
    cy.getByTestId('childCreationDescription').find('input').invoke('val').should('not.be.empty');
    cy.getByTestId('childCreationDescription').click();
    cy.get('[data-value="Diagram Tool Section"]').should('exist').click();
    cy.getByTestId('create-object').click();
    cy.getByTestId('Tool Section').should('exist');
    cy.getByTestId('Tool Section-more').click();
    cy.getByTestId('new-object').click();
    cy.getByTestId('childCreationDescription').find('input').invoke('val').should('not.be.empty');
    cy.getByTestId('childCreationDescription').click();
    cy.get('[data-value="Node Tool"]').should('exist');
    cy.get('[data-value="Edge Tool"]').should('not.exist');
  });

  it('Check the NodePalette toolSection creation', () => {
    cy.getByTestId('ViewNewModel').dblclick();
    cy.getByTestId('View').dblclick();
    cy.get('[data-testid$=" Diagram Description"]').dblclick();
    cy.getByTestId('Entity1 Node').dblclick();
    cy.getByTestId('NodePalette-more').click();
    cy.getByTestId('new-object').click();
    cy.getByTestId('childCreationDescription').find('input').invoke('val').should('not.be.empty');
    cy.getByTestId('childCreationDescription').click();
    cy.get('[data-value="Node Tool Section"]').should('exist').click();
    cy.getByTestId('create-object').click();
    cy.getByTestId('Tool Section').should('exist');
    cy.getByTestId('Tool Section-more').click();
    cy.getByTestId('new-object').click();
    cy.getByTestId('childCreationDescription').find('input').invoke('val').should('not.be.empty');
    cy.getByTestId('childCreationDescription').click();
    cy.get('[data-value="Node Tool"]').should('exist');
    cy.get('[data-value="Edge Tool"]').should('exist');
    cy.get('[data-value="Source Edge End Reconnection Tool"]').should('not.exist');
  });

  it('Check the EdgePalette toolSection creation', () => {
    cy.getByTestId('ViewNewModel').dblclick();
    cy.getByTestId('View').dblclick();
    cy.get('[data-testid$=" Diagram Description"]').dblclick();
    cy.getByTestId('LinkedTo Edge').dblclick();
    cy.getByTestId('EdgePalette-more').click();
    cy.getByTestId('new-object').click();
    cy.getByTestId('childCreationDescription').find('input').invoke('val').should('not.be.empty');
    cy.getByTestId('childCreationDescription').click();
    cy.get('[data-value="Edge Tool Section"]').should('exist').click();
    cy.getByTestId('create-object').click();
    cy.getByTestId('Tool Section').should('exist');
    cy.getByTestId('Tool Section-more').click();
    cy.getByTestId('new-object').click();
    cy.getByTestId('childCreationDescription').find('input').invoke('val').should('not.be.empty');
    cy.getByTestId('childCreationDescription').click();
    cy.get('[data-value="Node Tool"]').should('exist');
    cy.get('[data-value="Edge Tool"]').should('not.exist');
    cy.get('[data-value="Source Edge End Reconnection Tool"]').should('not.exist');
    cy.get('[data-value="Target Edge End Reconnection Tool"]').should('not.exist');
  });

  it('Check the precondition on tools', () => {
    cy.getByTestId('ViewNewModel').dblclick();
    cy.getByTestId('View').dblclick();
    cy.get('[data-testid$=" Diagram Description"]').dblclick();
    cy.getByTestId('DiagramPalette-more').click();
    cy.getByTestId('new-object').click();
    cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('childCreationDescription').click();
    cy.getByTestId('childCreationDescription').get('[data-value="Node Tool"]').should('exist').click();
    cy.getByTestId('create-object').click();
    cy.getByTestId('Precondition Expression').should('exist');
    cy.getByTestId('Precondition Expression').type('aql:self.eAllContents()->size()>0');
    cy.getByTestId('Name').clear();
    cy.getByTestId('Name').type('TestTool');

    cy.get('[title="Back to the homepage"]').click();
    // Check the diagram representation

    cy.getByTestId('create').click();
    cy.getByTestId('name').type('Instance');
    cy.getByTestId('create-project').click();
    cy.getByTestId('empty').click();
    cy.getByTestId('Others...-more').click();
    cy.getByTestId('new-object').click();
    cy.getByTestId('domain').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('domain').find('div').first().should('not.have.attr', 'aria-disabled');
    cy.getByTestId('domain').click();
    cy.getByTestId('domain').get('[data-value^="domain://"]').should('exist').click();
    cy.getByTestId('create-object').click();

    cy.getByTestId('Root-more').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();
    cy.getByTestId('representationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('representationDescription').click();
    cy.get('[data-testid$=" Diagram Description"]').should('exist').click();
    cy.getByTestId('create-representation').click();
    cy.getByTestId('Diagram').should('exist');
    cy.getByTestId('Diagram').click();
    cy.getByTestId('New Entity1 - Tool').should('exist');
    cy.getByTestId('New Entity2 - Tool').should('exist');
    cy.getByTestId('TestTool - Tool').should('not.exist');
    cy.getByTestId('New Entity1 - Tool').click();
    cy.getByTestId('Diagram').click();
    cy.getByTestId('New Entity1 - Tool').should('exist');
    cy.getByTestId('New Entity2 - Tool').should('exist');
    cy.getByTestId('TestTool - Tool').should('exist');
  });
});
