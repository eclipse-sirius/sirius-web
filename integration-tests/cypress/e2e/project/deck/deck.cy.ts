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

import { Deck } from '../../../workbench/Deck';
import { Details } from '../../../workbench/Details';
import { Explorer } from '../../../workbench/Explorer';

describe('Verify the Deck Representation', () => {
  let studioProjectId: string = '';
  before(() => {
    // We create the Deck View from the stereotype before executing the tests
    new Deck().initDeckView().then((projectId) => {
      studioProjectId = projectId;
    });
  });
  after(() => {
    // We delete the created studio once all tests have been executed
    cy.deleteProject(studioProjectId);
  });

  context('We first verify the creation of an empty deck representation', () => {
    it('We create the Deck Representation', () => {
      const deck = new Deck();
      deck.createTaskProjectAndDeckRepresentation('Project Dev', 'New Daily Representation');
      new Explorer().getSelectedTreeItems().contains('New Daily Representation').should('exist');
      deck.getDeckRepresentation().should('exist');

      deck.deleteCurrentProject();
    });
  });

  context('We new verify the Deck Creation, Deletion and renaming', () => {
    let taskProjectId: string;
    before(() => {
      new Deck()
        .createTaskProjectAndDeckRepresentation('Daily Project Dev', 'New Daily Representation')
        .then((projectId) => {
          taskProjectId = projectId;
        });
    });

    beforeEach(() => {
      new Deck().openDeckRepresentation(taskProjectId, 'Daily Project Dev', 'New Daily Representation');
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
      new Deck()
        .createTaskProjectAndDeckRepresentation('Daily Project Dev', 'New Daily Representation')
        .then((projectId) => {
          taskProjectId = projectId;
        });
    });

    beforeEach(() => {
      new Deck().openDeckRepresentation(taskProjectId, 'Daily Project Dev', 'New Daily Representation');
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
});
