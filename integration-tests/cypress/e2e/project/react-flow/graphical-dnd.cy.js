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

describe('/projects/:projectId/edit - graphical DnD', () => {
  beforeEach(() => {
    cy.deleteAllProjects();
    cy.createProjectFromTemplate('studio-template').then((res) => {
      const projectId = res.body.data.createProjectFromTemplate.project.id;
      cy.visit(`/projects/${projectId}/edit`);
    });
  });

  it('Check graphical Drag and Drop', () => {
    cy.getByTestId('DomainNewModel').dblclick();
    cy.get('[title="domain::Domain"]').then(($div) => {
      cy.wrap($div.data().testid).as('domainValue');
    });
    cy.get('@domainValue').then((domainValue) => {
      cy.getByTestId(`${domainValue}`).dblclick();
    });
    cy.createChildObject('Entity2', 'Relation');
    cy.getByTestId('Containment').find('input').check();
    cy.getByTestId('Target Type').click();
    cy.getByTestId('option-Entity1').click();

    cy.getByTestId('ViewNewModel').dblclick();
    cy.getByTestId('View').dblclick();
    cy.get('[data-testid$=" Diagram Description"]').dblclick();
    cy.getByTestId('Entity2 Node').dblclick();
    cy.getByTestId('Reused Child Node Descriptions').click();
    cy.getByTestId('option-Entity1 Node').click();
    cy.createChildObject('NodePalette', 'Drop Node Tool');
    cy.getByTestId('Tool').click();
    cy.getByTestId('Name').type('{selectAll}Drop Entity1 Tool');
    cy.getByTestId('Accepted Node Types').click();
    cy.getByTestId('option-Entity1 Node').click();
    cy.createChildObject('Drop Entity1 Tool', 'Change Context');
    cy.getByTestId('Expression').type('{selectAll}aql:targetElement{enter}');
    cy.createChildObject('aql:targetElement', 'Set Value');
    cy.getByTestId('Feature Name').type('relation');
    cy.getByTestId('Value Expression').type('aql:droppedElement');

    cy.get('[title="Back to the homepage"]').click();
    cy.get('@domainValue').then((domainValue) => {
      cy.createInstanceFromDomainModel(domainValue, true);
    });

    cy.createChildObject('Root', 'Entity1s Entity1');
    cy.getByTestId('Name').type('Entity1{enter}');
    cy.createChildObject('Root', 'Entity2s Entity2');
    cy.getByTestId('Name').type('Entity2{enter}');

    cy.dragAndDropNode('Rectangle - Entity1', 'Rectangle - Entity2');
    cy.isNodeInside('Rectangle - Entity1', 'Rectangle - Entity2');

    cy.createChildObject('Root', 'Entity1s Entity1');
    cy.getByTestId('Name').type('InvalidTarget{enter}');

    cy.dragAndDropNode('Rectangle - Entity1', 'Rectangle - InvalidTarget');
    // The target is invalid, the move is canceled
    cy.isNodeInside('Rectangle - Entity1', 'Rectangle - Entity2');
  });
});
