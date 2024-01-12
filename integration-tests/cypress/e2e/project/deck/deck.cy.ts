/*******************************************************************************
 * Copyright (c) 2024  Obeo.
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

import { Project } from '../../../pages/Project';
import { Projects } from '../../../pages/Projects';
import { isCreateProjectFromTemplateSuccessPayload } from '../../../support/server/createProjectFromTemplateCommand';
import { Deck } from '../../../workbench/Deck';
import { Explorer } from '../../../workbench/Explorer';
import { Details } from '../../../workbench/Details';

describe('Verify the Deck Representation', () => {
  let studioProjectId: string = '';
  before(() => {
    // We create the Deck View from the stereotype before executing the tests
    new Projects().visit();
    cy.createProjectFromTemplate('blank-studio-template').then((res) => {
      const payload = res.body.data.createProjectFromTemplate;
      if (isCreateProjectFromTemplateSuccessPayload(payload)) {
        studioProjectId = payload.project.id;

        new Project().visit(studioProjectId);
        cy.getByTestId('new-model').click();
        cy.getByTestId('name-input').type('Deck View');
        cy.getByTestId('stereotype').click();
        cy.get('li').filter(':contains("Deck View")').click();
        cy.getByTestId('create-document').click();
      }
    });
  });
  after(() => {
    // We delete the created studio once all tests have been executed
    cy.deleteProject(studioProjectId);
  });

  context('We first verify the creation of an empty deck representation', () => {
    it('We create the Deck Representation', () => {
      createTaskProjectAndDeckRepresentation('Project Dev', 'New Daily Representation');
      new Explorer().getSelectedTreeItems().contains('New Daily Representation').should('exist');
      new Deck().getDeckRepresentation().should('exist');

      deleteCurrentProject();
    });
  });

  context('We new verify the Deck Creation, Deletion and renaming', () => {
    let taskProjectId: string;
    before(() => {
      createTaskProjectAndDeckRepresentation('Daily Project Dev', 'New Daily Representation').then(() => {
        cy.url().then(($url) => {
          const prefix = Cypress.config().baseUrl + '/projects/';
          taskProjectId = $url.substring(prefix.length, $url.indexOf('/', prefix.length + 1));
        });
      });
    });

    beforeEach(() => {
      openDeckRepresentation(taskProjectId, 'Daily Project Dev', 'New Daily Representation');
    });

    after(() => {
      cy.deleteProject(taskProjectId);
    });

    it('We verify the Deck Representation on the Daily project', () => {
      new Explorer().getSelectedTreeItems().contains('New Daily Representation').should('exist');

      const deck = new Deck();
      //We verify that all lanes are present.
      const days: string[] = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];
      days.forEach((day) => {
        deck.getLane(day).should('exist');
      });

      //We verify that all cards are present in the expected lane.
      deck.getCard('Monday', 'Idea').should('exist');
      deck.getCard('Monday', 'Specification').should('exist');
      deck.getCard('Tuesday', 'Development').should('exist');
      deck.getCard('Friday', 'Release').should('exist');
    });

    it('We rename the Deck Representation on the Daily project', () => {
      const explorer = new Explorer();
      explorer.getTreeItemByLabel('New Daily Representation').should('exist');
      new Deck().getDeckRepresentation().should('exist');
      explorer.rename('New Daily Representation', 'renamed');
      explorer.getTreeItemByLabel('New Daily Representation').should('not.exist');
      explorer.getTreeItemByLabel('renamed').should('exist');

      cy.getByTestId('representation-tab-renamed').should('exist');
      explorer.rename('renamed', 'New Daily Representation');
    });

    it('We remove the Deck Representation on the Daily project', () => {
      const explorer = new Explorer();
      explorer.getTreeItemByLabel('New Daily Representation').should('exist');
      new Deck().getDeckRepresentation().should('exist');
      explorer.delete('New Daily Representation');
      explorer.getTreeItemByLabel('New Daily Representation').should('not.exist');
      cy.getByTestId('representation-area').find('h5').should('have.text', 'The Deck does not exist anymore');
    });
  });

  context('We now verify the Deck representation refresh', () => {
    let taskProjectId: string;
    before(() => {
      createTaskProjectAndDeckRepresentation('Daily Project Dev', 'New Daily Representation').then(() => {
        cy.url().then(($url) => {
          const prefix = Cypress.config().baseUrl + '/projects/';
          taskProjectId = $url.substring(prefix.length, $url.indexOf('/', prefix.length + 1));
        });
      });
    });

    beforeEach(() => {
      openDeckRepresentation(taskProjectId, 'Daily Project Dev', 'New Daily Representation');
    });

    after(() => {
      cy.deleteProject(taskProjectId);
    });
    it('We create a new daily tag to verify that the deck is properly refreshed', () => {
      const explorer = new Explorer();
      explorer.createObject('Daily Project Dev', 'Tag');
      explorer.select('::');
      const details = new Details();
      details.getTextField('Prefix').type('daily{enter}');
      details.getTextField('Suffix').type('Today{enter}');
      new Deck().getLane('Today').should('exist');
    });

    it('We set the task description and verify that the card is refreshed.', () => {
      const deck = new Deck();
      const explorer = new Explorer();
      deck.getCard('Monday', 'Idea').should('exist');
      explorer.getTreeItemByLabel('Idea').click();
      cy.getByTestId('input-Description').type('The Description{enter}');
      deck.getCard('Monday', 'Idea').contains('The Description');
    });

    it('We change a task tag to verify that the deck is properly refreshed (the card should be removed from the lane)', () => {
      const deck = new Deck();

      deck.getCard('Monday', 'Idea').should('exist');
      new Explorer().select('Idea');
      new Details().deleteReferenceWidgetOption('Tags', 'daily::Monday');
      cy.getByTestId('card-Idea').should('not.exist');
    });

    it('We delete a task and verify that the card is removed from the lane.', () => {
      const deck = new Deck();
      deck.getCard('Tuesday', 'Development').should('exist');
      new Explorer().delete('Development');
      cy.getByTestId('card-Development').should('not.exist');
    });
  });

  context('We now verify the Deck representation cards selection and editing', () => {
    let taskProjectId: string;
    before(() => {
      createTaskProjectAndDeckRepresentation('Daily Project Dev', 'New Daily Representation').then(() => {
        cy.url().then(($url) => {
          const prefix = Cypress.config().baseUrl + '/projects/';
          taskProjectId = $url.substring(prefix.length, $url.indexOf('/', prefix.length + 1));
        });
      });
    });

    beforeEach(() => {
      openDeckRepresentation(taskProjectId, 'Daily Project Dev', 'New Daily Representation');
    });

    after(() => {
      cy.deleteProject(taskProjectId);
    });
    it('We verify the card selection.', () => {
      const deck = new Deck();
      const explorer = new Explorer();
      deck.getCard('Monday', 'Idea').should('have.css', 'border', '');
      explorer.select('Idea');
      deck.getCard('Monday', 'Idea').should('have.css', 'border', '2px solid rgb(190, 26, 120)');
      deck.getCard('Monday', 'Specification').click();

      // We verify that when selecting a card, the task is selected too in the model explorer.
      explorer.getSelectedTreeItems();
      explorer.getSelectedTreeItems().should('have.length', 1);
      explorer.getSelectedTreeItems().contains('Specification').should('exist');
    });

    it('We verify the card direct edit.', () => {
      const deck = new Deck();
      deck.getCard('Monday', 'Specification').click().trigger('keydown', { keyCode: 113, which: 113 }); // F2 key code

      //After having pressed F2, the focus should be on the title input field.
      cy.getByTestId('card-input-title').get('input').should('have.focus');

      //After having pressed Tab, the focus should be on the title input field.
      deck.getCard('Monday', 'Specification').trigger('keydown', { keyCode: 9, which: 9 }); // Tab key code
      cy.getByTestId('card-input-label').get('input').should('have.focus');
      deck.getCard('Monday', 'Specification').trigger('keydown', { keyCode: 9, which: 9 }); // Tab key code
      cy.getByTestId('card-input-details').get('input').should('have.focus');
    });

    it('We verify the card editing.', () => {
      const deck = new Deck();
      deck.getCard('Monday', 'Specification').click().trigger('keydown', { keyCode: 113, which: 113 }); // F2 key code

      //We rename the title
      cy.getByTestId('card-input-title').type('Specification_renamed{enter}');
      new Explorer().getTreeItemByLabel('Specification_renamed').should('exist');

      //We edit the description
      deck.getCard('Monday', 'Specification_renamed').click();
      cy.getByTestId('card-input-details').type('a description{enter}');

      cy.getByTestId('input-Description').should('have.value', 'a description');
    });

    it('We verify the card creation.', () => {
      const deck = new Deck();
      const explorer = new Explorer();
      //We create a new Card
      deck.getLane('Monday').find('a:contains("Click to add card")').click();
      deck.getLane('Monday').find('div[placeholder="title"]').click().type('myNewCard');
      deck.getLane('Monday').find('div[placeholder="description"]').click().type('my Card description');
      deck.getLane('Monday').get('button:contains("Add card")').click();

      //We verify that the new task has been created
      explorer.getTreeItemByLabel('myNewCard').should('exist');
      explorer.select('myNewCard');

      //We check the values in the properties view.
      cy.getByTestId('input-Name').should('have.value', 'myNewCard');
      cy.getByTestId('input-Description').should('have.value', 'my Card description');

      //We check that the new card is visible in the deck representation.
      deck.getCard('Monday', 'myNewCard').contains('my Card description');
    });

    it('We verify the card deletion.', () => {
      const deck = new Deck();
      const explorer = new Explorer();
      explorer.getTreeItemByLabel('Development').should('exist');
      deck.getCard('Tuesday', 'Development').click();

      // We use the delete button
      deck.getCard('Tuesday', 'Development').find('button[aria-label="deleteCard"]').click();

      //The Task should be removed from the model explorer and the deck representation.
      cy.getByTestId('card-Development').should('not.exist');
      explorer.getTreeItemByLabel('Development').should('not.exist');
    });
  });
});

const createTaskProjectAndDeckRepresentation = (rootElementName: string, deckRepresentationName: string) => {
  // We create the Task project from the template before each tests.
  new Projects().visit();
  return cy.createProjectFromTemplate('task-template').then((res) => {
    const payload = res.body.data.createProjectFromTemplate;
    if (isCreateProjectFromTemplateSuccessPayload(payload)) {
      const taskProjectId = payload.project.id;
      new Project().visit(taskProjectId);
      createDailyDeckRepresentation(rootElementName, deckRepresentationName);
    }
  });
};

const createDailyDeckRepresentation = (rootElementName: string, deckRepresentationName) => {
  const explorer = new Explorer();
  explorer.getTreeItemByLabel('Task Model').should('exist');
  explorer.expand('Task Model');
  explorer.expand('Company');
  explorer.createRepresentation(rootElementName, 'Deck Daily Representation', deckRepresentationName);
};

const deleteCurrentProject = () => {
  cy.url().then(($url) => {
    const prefix = Cypress.config().baseUrl + '/projects/';
    const projectId = $url.substring(prefix.length, $url.indexOf('/', prefix.length + 1));
    cy.deleteProject(projectId);
  });
};

function openDeckRepresentation(taskProjectId: string, projectName: string, representationName: string) {
  new Projects().visit();
  new Project().visit(taskProjectId);
  const explorer = new Explorer();
  explorer.expand('Task Model');
  explorer.expand('Company');
  explorer.expand(projectName);
  new Explorer().getTreeItemByLabel(representationName).click();
}
