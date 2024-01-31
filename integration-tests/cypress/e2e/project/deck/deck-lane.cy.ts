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
describe('Verify the Deck Representation lanes selection and editing', () => {
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

  it('We verify the lane selection.', () => {
    const deck = new Deck();
    const explorer = new Explorer();
    deck.getLane('Monday').should('have.css', 'border-width', '0px');
    explorer.select('daily::Monday');
    deck.getLane('Monday').should('have.css', 'border', '2px solid rgb(190, 26, 120)');
    deck.getLane('Wednesday').click();

    // We verify that when selecting a card, the task is selected too in the model explorer.
    explorer.getSelectedTreeItems();
    explorer.getSelectedTreeItems().should('have.length', 1);
    explorer.getSelectedTreeItems().contains('daily::Wednesday').should('exist');
  });

  it('We verify the lane direct edit.', () => {
    const deck = new Deck();
    deck.getLane('Monday').click('top').find('header').trigger('keydown', { keyCode: 113, which: 113 });
    cy.getByTestId('lane-input-title').get('input').should('have.focus');
  });

  it('We verify the lane editing.', () => {
    const deck = new Deck();
    deck.getLane('Monday').click('top').getByTestId('lane-input-title').click().type('Monday_renamed{enter}');
    new Explorer().getTreeItemByLabel('daily::Monday_renamed').should('exist');
    const details = new Details();
    details.getTextField('Suffix').should('have.value', 'Monday_renamed');
    details.getTextField('Suffix').type('{selectAll}Monday{enter}');
    deck.getLane('Monday').should('exist');
  });

  it('We verify the lane collapsing.', () => {
    const deck = new Deck();
    //We first verify if the collapse works as expected
    deck.getCard('Monday', 'Idea').should('exist');
    deck.getCard('Monday', 'Specification').should('exist');
    deck.collapseLane('Monday').then(() => {
      deck.isCollapse('Monday', true);
      deck.getCard('Monday', 'Idea').should('not.exist');
      deck.getCard('Monday', 'Specification').should('not.exist');
    });

    //We close and reopen the Deck representation to make sure that the collapse state is persisted.
    deck.closeDeckRepresentation('New Daily Representation');
    deck.getDeckRepresentation().should('not.exist');
    new Explorer().getTreeItemByLabel('New Daily Representation').click();

    deck.getCard('Monday', 'Idea').should('not.exist');
    deck.getCard('Monday', 'Specification').should('not.exist');
    deck.expandLane('Monday').then(() => {
      deck.isCollapse('Monday', false);
      deck.getCard('Monday', 'Idea').should('exist');
      deck.getCard('Monday', 'Specification').should('exist');
    });
  });
});
