/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
describe('/projects/:projectId/edit - Diagram', () => {
  beforeEach(() => {
    cy.deleteAllProjects();
    cy.createProject('Cypress Project').then((res) => {
      const projectId = res.body.data.createProject.project.id;
      cy.wrap(projectId).as('projectId');
      // Robot flow document id is c26b6086-b444-3ee6-b8cd-9a4fde5956a7
      cy.createDocument(projectId, 'c26b6086-b444-3ee6-b8cd-9a4fde5956a7', 'flow').then(() => {
        cy.visit(`/projects/${projectId}/edit`);
      });
    });

    cy.getByTestId('flow').dblclick();
    cy.getByTestId('Robot').dblclick();
    cy.getByTestId('Robot-more').click();
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-representation').click();

    cy.getByTestId('name').clear().type('Topography with auto layout');
    cy.getByTestId('representationDescription').click();
    cy.getByTestId('Topography with auto layout').click();
    cy.getByTestId('create-representation').click();

    cy.getByTestId('representation-tab-Topography with auto layout').should('exist');
    cy.getByTestId('representation-tab-Topography with auto layout').then((tab) => {
      const representationId = tab[0].dataset.representationid;
      cy.wrap(representationId).as('representationId');
    });
  });

  it('test selection dialog - show/hide', () => {
    cy.getByTestId('Rectangle - CaptureSubSystem').click('topLeft');
    cy.getByTestId('PopupToolbar').should('exist');

    cy.getByTestId('PopupToolbar').findByTestId('expand').first().click();
    cy.getByTestId('PopupToolbar').findByTestId('Processor with Connection - Tool').click();
    cy.getByTestId('selection-dialog').should('exist');

    cy.getByTestId('selection-dialog').click('topLeft');
    cy.getByTestId('selection-dialog').should('not.exist');
  });

  it('test selection dialog - select item and click finish', () => {
    cy.getByTestId('Rectangle - CaptureSubSystem').click('topLeft');
    cy.getByTestId('PopupToolbar').should('exist');

    cy.getByTestId('PopupToolbar').findByTestId('expand').first().click();
    cy.getByTestId('PopupToolbar').findByTestId('Processor with Connection - Tool').click();
    cy.getByTestId('selection-dialog').should('exist');

    cy.getByTestId('selection-dialog').findByTestId('DSP').click();
    cy.getByTestId('selection-dialog').findByTestId('finish-action').click();
    cy.getByTestId('selection-dialog').should('not.exist');
    cy.getByTestId('Image - Processor4').should('exist');
    cy.getByTestId('Edge - 6').should('exist');
  });

  it('test selection dialog - items list update', function () {
    const projectId = this.projectId;
    const representationId = this.representationId;

    cy.getByTestId('Image - DSP').invoke('attr', 'data-nodeid').should('exist');
    cy.getByTestId('Image - DSP')
      .invoke('attr', 'data-nodeid')
      .then((nodeId) => {
        cy.getByTestId('Rectangle - CaptureSubSystem').click('topLeft');
        cy.getByTestId('PopupToolbar').should('exist');
        cy.getByTestId('PopupToolbar').findByTestId('expand').first().click();
        cy.getByTestId('PopupToolbar').findByTestId('Processor with Connection - Tool').click();
        cy.getByTestId('selection-dialog').should('exist');
        cy.getByTestId('selection-dialog').findByTestId('DSP').should('exist');
        cy.deleteNodeFromDiagram(projectId, representationId, nodeId);
        cy.getByTestId('selection-dialog').findByTestId('DSP').should('not.exist');
        cy.getByTestId('selection-dialog').click('topLeft');
        cy.getByTestId('selection-dialog').should('not.exist');
      });
  });

  it('test selection dialog - delete element on which selection dialog is called', function () {
    const projectId = this.projectId;
    const representationId = this.representationId;

    cy.getByTestId('Rectangle - CaptureSubSystem').invoke('attr', 'data-nodeid').should('exist');
    cy.getByTestId('Rectangle - CaptureSubSystem')
      .invoke('attr', 'data-nodeid')
      .then((nodeId) => {
        cy.getByTestId('Rectangle - CaptureSubSystem').click('topLeft');
        cy.getByTestId('PopupToolbar').should('exist');
        cy.getByTestId('PopupToolbar').findByTestId('expand').first().click();
        cy.getByTestId('PopupToolbar').findByTestId('Processor with Connection - Tool').click();
        cy.getByTestId('selection-dialog').should('exist');
        cy.getByTestId('selection-dialog').findByTestId('DSP').should('exist');
        cy.deleteNodeFromDiagram(projectId, representationId, nodeId);
        cy.getByTestId('selection-dialog').should('not.exist');
      });
  });
});
