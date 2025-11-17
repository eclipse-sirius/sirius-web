/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import { Project } from '../../pages/Project';
import { Flow } from '../../usecases/Flow';
import { Diagram } from '../../workbench/Diagram';
import { Explorer } from '../../workbench/Explorer';
import { Omnibox } from '../../workbench/Omnibox';

const makeValidSearch = (search: string, expectedHistory: number, omnibox: Omnibox, explorer: Explorer) => {
  omnibox.display();
  if (expectedHistory == 0) {
    omnibox.activateSearch();
    cy.get('[role="button"][data-testid*="search"]').should('not.exist');
  } else {
    omnibox.activateSearch().get('[role="button"][data-testid*="Search"]').should('have.length', expectedHistory);
  }

  omnibox.sendQuery(search);
  cy.getByTestId('omnibox').should('exist').findByTestId(search).click();
  omnibox.shouldBeClosed();
  explorer.revealGlobalSelectionInExplorer();
  explorer.getSelectedTreeItems().contains(search).should('exist');
};

const makeInvalidSearch = (search: string, expectedHistory: number, omnibox: Omnibox) => {
  omnibox.display();
  if (expectedHistory == 0) {
    omnibox.activateSearch();
    cy.get('[role="button"][data-testid*="search"]').should('not.exist');
  } else {
    omnibox.activateSearch().get('[role="button"][data-testid*="Search"]').should('have.length', expectedHistory);
  }
  omnibox.sendQuery(search, false);
  cy.get('body').type('{esc}');
};

const projectName = 'Cypress - Omnibox';
describe('Project - Omnibox', () => {
  context('Given a Robot flow project', () => {
    let projectId: string = '';
    beforeEach(() => {
      new Flow().createRobotProject(projectName).then((createdProjectData) => {
        projectId = createdProjectData.projectId;
        new Project().visit(projectId);
      });
    });

    afterEach(() => cy.deleteProject(projectId));

    it('Then the omnibox can be displayed and used to select an element in the workbench', () => {
      const explorer = new Explorer();

      explorer.expandWithDoubleClick('robot');
      explorer.createRepresentation('System', 'Topography', 'Topography');

      const omnibox = new Omnibox();
      omnibox.display();
      omnibox.sendQuery('').findByTestId('Search').click();
      omnibox.sendQuery('').findByTestId('DSP').click();
      omnibox.shouldBeClosed();

      explorer.getSelectedTreeItems().contains('DSP').should('exist');

      const diagram = new Diagram();
      diagram.getSelectedNodes('Topography', 'DSP').should('be.visible');

      cy.getByTestId('page-tab-DSP').should('be.visible');
    });

    it('Then selecting an element from the omnibox right after project opening reveals it in the Explorer', () => {
      const explorer = new Explorer();

      const omnibox = new Omnibox();
      omnibox.display();
      omnibox.sendQuery('').findByTestId('Search').click();
      omnibox.sendQuery('').findByTestId('DSP').click();
      omnibox.shouldBeClosed();

      explorer.getSelectedTreeItems().contains('DSP').should('exist');
    });

    it('Then the search command of omnibox remembers the user previous searches', () => {
      const explorer = new Explorer();
      const omnibox = new Omnibox();

      // Make 1 search
      makeValidSearch('Central_Unit', 0, omnibox, explorer);
      // Check there is one previous search in history
      makeValidSearch('Wifi', 1, omnibox, explorer);
      // Re-run previous search on 'Central_Unit' using history
      const box = omnibox.display();
      omnibox.activateSearch();
      cy.get('[role="button"][data-testid*="Search"]').should('have.length', 2);
      box.findByTestId("Search 'Central_Unit'").click();
      cy.getByTestId('omnibox').findByTestId('Central_Unit').click();
      omnibox.shouldBeClosed();
      explorer.revealGlobalSelectionInExplorer();
      explorer.getSelectedTreeItems().contains('Central_Unit').should('exist');
      // Making the same search do not keep twice the result in history
      makeValidSearch('Central_Unit', 2, omnibox, explorer);
      // Makes 9 more searches to check that we only keep 10 result
      makeValidSearch('System', 2, omnibox, explorer);
      makeValidSearch('Motion_Engine', 3, omnibox, explorer);
      makeInvalidSearch('invalidSearch', 4, omnibox);
      makeInvalidSearch('invalidSearch2', 5, omnibox);
      makeValidSearch('Radar_Capture', 6, omnibox, explorer);
      makeValidSearch('Back_Camera', 7, omnibox, explorer);
      makeValidSearch('Radar', 8, omnibox, explorer);
      makeValidSearch('Engine', 9, omnibox, explorer);
      makeValidSearch('GPU', 10, omnibox, explorer);
      makeValidSearch('standard', 10, omnibox, explorer);
    });
  });
});
