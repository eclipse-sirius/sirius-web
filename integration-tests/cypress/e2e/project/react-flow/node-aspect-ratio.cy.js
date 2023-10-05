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
describe('/projects/:projectId/edit - Node aspect ratio', () => {
  beforeEach(() => {
    cy.deleteAllProjects();
    cy.createProjectFromTemplate('studio-template').then((res) => {
      const projectId = res.body.data.createProjectFromTemplate.project.id;
      cy.visit(`/projects/${projectId}/edit`);
    });
  });
  it('Check node default size is used for node creation', () => {
    cy.getByTestId('ViewNewModel').dblclick();
    cy.getByTestId('View').dblclick();
    cy.get('[data-testid$=" Diagram Description"]').dblclick();
    cy.getByTestId('Entity1 Node').click();
    cy.getByTestId('Default Width Expression').type('200');
    cy.getByTestId('Default Height Expression').type('200');
    cy.getByTestId('Keep Aspect Ratio').find('input').check();

    cy.get('[title="Back to the homepage"]').click();

    // Create the domain instance
    cy.url().should('eq', Cypress.config().baseUrl + '/projects');
    cy.get('[title="Blank Studio"]').should('be.visible');
    cy.getByTestId('create').click();

    cy.url().should('eq', Cypress.config().baseUrl + '/new/project');
    cy.getByTestId('name').should('be.visible');
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
    cy.getByTestId('name').clear().type('diagram__REACT_FLOW');
    cy.getByTestId('create-representation').click();

    cy.getByTestId('Root-more').click();
    cy.getByTestId('new-object').click();
    cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('childCreationDescription').click();
    cy.getByTestId('childCreationDescription').get('[data-value="Entity1s Entity1"]').should('exist').click();
    cy.getByTestId('create-object').click();

    cy.wait(400); // wait for automatique zoom to apply
    cy.get('.react-flow__viewport')
      .invoke('attr', 'style')
      .then((styleValue) => {
        let scale = 1;
        const match = /scale\(([^)]+)\)/.exec(styleValue);
        if (match) {
          scale = match[1];
        }
        cy.getByTestId('Rectangle - ')
          .invoke('css', 'width')
          .then((nodeWidth) => {
            expect(parseInt(nodeWidth) / scale).to.approximately(200, 1);
          });
        cy.getByTestId('Rectangle - ')
          .invoke('css', 'height')
          .then((nodeHeight) => expect(parseInt(nodeHeight) / scale).to.approximately(200, 1));
      });

    cy.getByTestId('Entity1').click();
    cy.getByTestId('Name').type('Larger label but should keep aspect ratio{enter}');
    cy.getByTestId('Larger label but should keep aspect ratio').click();
    cy.getByTestId('Rectangle - Larger label but should keep aspect ratio').then(($node) => {
      const nodeWidth = $node.width();
      const nodeHeight = $node.height();
      expect(Math.trunc(nodeWidth)).to.eq(Math.trunc(nodeHeight));
    });
  });

  it('Check node keep aspect ratio with large sub node', () => {
    cy.getByTestId('DomainNewModel').dblclick();

    cy.get('[title="domain::Domain"]').then(($div) => {
      cy.wrap($div.data().testid).as('domainValue');
    });
    cy.get('@domainValue').then((domainValue) => {
      cy.getByTestId(`${domainValue}-more`).click();
    });

    cy.getByTestId('new-object').click();
    cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('childCreationDescription').click();
    cy.getByTestId('childCreationDescription').get('[data-value="Entity"]').should('exist').click();
    cy.getByTestId('create-object').click();

    cy.getByTestId('Name').find('input').should('have.value', 'NewEntity');
    cy.getByTestId('Name').find('input').clear().type('SubNode');

    cy.getByTestId('Entity1-more').click();
    cy.getByTestId('new-object').click();
    cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('childCreationDescription').click();
    cy.getByTestId('childCreationDescription').get('[data-value="Relation"]').should('exist').click();
    cy.getByTestId('create-object').click();

    cy.getByTestId('Containment').find('input').check();
    cy.getByTestId('Target Type').click();
    cy.getByTestId('option-SubNode').should('exist');
    cy.getByTestId('option-SubNode').click();

    cy.getByTestId('ViewNewModel').dblclick();
    cy.getByTestId('View').dblclick();
    cy.get('[data-testid$=" Diagram Description"]').dblclick();

    cy.get('[data-testid$=" Diagram Description-more"]').click();
    cy.getByTestId('new-object').click();
    cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('childCreationDescription').click();
    cy.getByTestId('childCreationDescription').get('[data-value="Node Description"]').should('exist').click();
    cy.getByTestId('create-object').click();
    cy.getByTestId('input-Domain Type').should('have.text', '');
    cy.get('@domainValue').then((domainValue) => {
      cy.getByTestId('Domain Type').type(`${domainValue}::SubNode`);
    });
    cy.getByTestId('Default Width Expression').type('400');
    cy.getByTestId('Default Height Expression').type('20');

    cy.getByTestId('Entity1 Node').click();
    cy.getByTestId('Default Width Expression').type('200');
    cy.getByTestId('Default Height Expression').type('200');
    cy.getByTestId('Keep Aspect Ratio').find('input').check();
    cy.getByTestId('Reused Child Node Descriptions').click();
    cy.getByTestId('option-Node').should('exist');
    cy.getByTestId('option-Node').click();

    cy.get('[title="Back to the homepage"]').click();

    // Create the domain instance
    cy.url().should('eq', Cypress.config().baseUrl + '/projects');
    cy.get('[title="Blank Studio"]').should('be.visible');
    cy.getByTestId('create').click();

    cy.url().should('eq', Cypress.config().baseUrl + '/new/project');
    cy.getByTestId('name').should('be.visible');
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
    cy.getByTestId('name').clear().type('diagram__REACT_FLOW');
    cy.getByTestId('create-representation').click();

    cy.getByTestId('Root-more').click();
    cy.getByTestId('new-object').click();
    cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('childCreationDescription').click();
    cy.getByTestId('childCreationDescription').get('[data-value="Entity1s Entity1"]').should('exist').click();
    cy.getByTestId('create-object').click();

    cy.getByTestId('Entity1-more').click();
    cy.getByTestId('new-object').click();
    cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('childCreationDescription').click();
    cy.getByTestId('childCreationDescription').get('[data-value="Relation Sub Node"]').should('exist').click();
    cy.getByTestId('create-object').click();

    cy.getByTestId('Rectangle - ').then(($node) => {
      const nodeWidth = $node.width();
      const nodeHeight = $node.height();
      expect(Math.trunc(nodeWidth)).to.eq(Math.trunc(nodeHeight));
    });
  });

  it('Check node keep aspect ratio with higher sub node', () => {
    cy.getByTestId('DomainNewModel').dblclick();

    cy.get('[title="domain::Domain"]').then(($div) => {
      cy.wrap($div.data().testid).as('domainValue');
    });
    cy.get('@domainValue').then((domainValue) => {
      cy.getByTestId(`${domainValue}-more`).click();
    });

    cy.getByTestId('new-object').click();
    cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('childCreationDescription').click();
    cy.getByTestId('childCreationDescription').get('[data-value="Entity"]').should('exist').click();
    cy.getByTestId('create-object').click();

    cy.getByTestId('Name').find('input').should('have.value', 'NewEntity');
    cy.getByTestId('Name').find('input').clear().type('SubNode');

    cy.getByTestId('Entity1-more').click();
    cy.getByTestId('new-object').click();
    cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('childCreationDescription').click();
    cy.getByTestId('childCreationDescription').get('[data-value="Relation"]').should('exist').click();
    cy.getByTestId('create-object').click();

    cy.getByTestId('Containment').find('input').check();
    cy.getByTestId('Target Type').click();
    cy.getByTestId('option-SubNode').should('exist');
    cy.getByTestId('option-SubNode').click();

    cy.getByTestId('ViewNewModel').dblclick();
    cy.getByTestId('View').dblclick();
    cy.get('[data-testid$=" Diagram Description"]').dblclick();

    cy.get('[data-testid$=" Diagram Description-more"]').click();
    cy.getByTestId('new-object').click();
    cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('childCreationDescription').click();
    cy.getByTestId('childCreationDescription').get('[data-value="Node Description"]').should('exist').click();
    cy.getByTestId('create-object').click();
    cy.getByTestId('input-Domain Type').should('have.text', '');
    cy.get('@domainValue').then((domainValue) => {
      cy.getByTestId('Domain Type').type(`${domainValue}::SubNode`);
    });
    cy.getByTestId('Default Width Expression').type('20');
    cy.getByTestId('Default Height Expression').type('400');

    cy.getByTestId('Entity1 Node').click();
    cy.getByTestId('input-Default Width Expression').should('have.text', '');
    cy.getByTestId('Default Width Expression').type('200');
    cy.getByTestId('Default Height Expression').type('200');
    cy.getByTestId('Keep Aspect Ratio').find('input').check();
    cy.getByTestId('Reused Child Node Descriptions').click();
    cy.getByTestId('option-Node').should('exist');
    cy.getByTestId('option-Node').click();

    cy.get('[title="Back to the homepage"]').click();

    // Create the domain instance
    cy.url().should('eq', Cypress.config().baseUrl + '/projects');
    cy.get('[title="Blank Studio"]').should('be.visible');
    cy.getByTestId('create').click();

    cy.url().should('eq', Cypress.config().baseUrl + '/new/project');
    cy.getByTestId('name').should('be.visible');
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
    cy.getByTestId('name').clear().type('diagram__REACT_FLOW');
    cy.getByTestId('create-representation').click();

    cy.getByTestId('Root-more').click();
    cy.getByTestId('new-object').click();
    cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('childCreationDescription').click();
    cy.getByTestId('childCreationDescription').get('[data-value="Entity1s Entity1"]').should('exist').click();
    cy.getByTestId('create-object').click();

    cy.getByTestId('Entity1-more').click();
    cy.getByTestId('new-object').click();
    cy.getByTestId('childCreationDescription').children('[role="button"]').invoke('text').should('have.length.gt', 1);
    cy.getByTestId('childCreationDescription').click();
    cy.getByTestId('childCreationDescription').get('[data-value="Relation Sub Node"]').should('exist').click();
    cy.getByTestId('create-object').click();

    cy.getByTestId('Rectangle - ').then(($node) => {
      const nodeWidth = $node.width();
      const nodeHeight = $node.height();
      expect(Math.trunc(nodeWidth)).to.eq(Math.trunc(nodeHeight));
    });
  });
});
