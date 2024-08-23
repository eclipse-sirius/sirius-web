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

import { Explorer } from '../../../workbench/Explorer';

describe.skip('/projects/:projectId/edit - FormDescriptionEditor', () => {
  beforeEach(() => {
    cy.deleteAllProjects();
    cy.createProjectFromTemplate('studio-template').then((res) => {
      const projectId = res.body.data.createProjectFromTemplate.project.id;
      const view_document_id = 'ea57f74d-bc7b-3a7a-81e0-8aef4ee85770';
      cy.createDocument(projectId, view_document_id, 'ViewDocument').then(() => {
        cy.visit(`/projects/${projectId}/edit`);
      });
    });
    cy.getByTestId('ViewDocument').dblclick();
    cy.getByTestId('View').dblclick();
    cy.getByTestId('View-more').should('be.enabled').click();
    // create the form description
    cy.getByTestId('treeitem-contextmenu').findByTestId('new-object').click();
    //make sure the data are fetched before selecting
    cy.getByTestId('create-object').should('be.enabled');
    cy.getByTestId('childCreationDescription').click();
    cy.get('[data-value="descriptions-FormDescription"]').click();
    cy.getByTestId('create-object').click();
    // create the form description editor
    cy.getByTestId('New Form Description').click();

    const explorer = new Explorer();
    explorer.createRepresentation('New Form Description', 'FormDescriptionEditor', 'FormDescriptionEditor');
  });

  it('try to move a toolbar action into another empty group', () => {
    // create another group
    const dataTransfer = new DataTransfer();
    cy.getByTestId('FormDescriptionEditor-Group').trigger('dragstart', { dataTransfer });
    cy.getByTestId('Page-DropArea').trigger('drop', { dataTransfer });
    // create a toolbar action in the first group
    cy.get('[data-testid^="ToolbarActions-NewAction-"]').eq(1).click();
    // move the toolbar action from the first group to the second one
    cy.getByTestId('ToolbarAction').trigger('dragstart', { dataTransfer });
    cy.get('[data-testid^="ToolbarActions-DropArea-"]').eq(2).trigger('drop', { dataTransfer });
  });

  it('try to add group and widget to a page', () => {
    const dataTransfer = new DataTransfer();
    cy.getByTestId('FormDescriptionEditor-BarChart').trigger('dragstart', { dataTransfer });
    cy.get('[data-testid^="Group-Widgets-DropArea-"]').eq(0).trigger('drop', { dataTransfer });
    cy.getByTestId('BarChart').should('exist');
  });
});
