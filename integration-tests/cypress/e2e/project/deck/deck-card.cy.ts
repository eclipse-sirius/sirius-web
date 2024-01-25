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
import { Explorer } from '../../../workbench/Explorer';
describe('Verify the Deck Representation cards selection and editing', () => {
  let studioProjectId: string = '';
  let taskProjectId: string;
  before(() => {
    // We create the Deck View from the stereotype before executing the tests
    new Deck().initDeckView().then((projectId) => {
      studioProjectId = projectId;
    });

    new Deck()
      .createTaskProjectAndDeckRepresentation('Daily Project Dev', 'New Daily Representation')
      .then((projectId) => {
        taskProjectId = projectId;
      });
  });
  after(() => {
    // We delete the created studio once all tests have been executed
    cy.deleteProject(studioProjectId);
    cy.deleteProject(taskProjectId);
  });

  beforeEach(() => {
    new Deck().openDeckRepresentation(taskProjectId, 'Daily Project Dev', 'New Daily Representation');
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
