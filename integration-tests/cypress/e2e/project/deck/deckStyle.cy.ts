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
describe('Verify the Deck Representation lanes selection and editing', () => {
  let studioProjectId: string = '';
  let taskProjectId: string;
  before(() => {
    // We create the Deck View from the stereotype before executing the tests
    new Deck().initDeckView().then((projectId) => {
      studioProjectId = projectId;
      addStyles();
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

  it('We verify the deck style.', () => {
    cy.getByTestId('deck-representation').should('have.css', 'background-color', 'rgb(255, 112, 67)');
    const deck = new Deck();
    // We check the lane background color
    deck.getLane('Monday').should('have.css', 'background-color', 'rgb(255, 236, 179)');
    deck.getLane('Tuesday').should('have.css', 'background-color', 'rgb(255, 160, 0)');

    //We check the Card background color
    deck.getCard('Monday', 'Idea').should('have.css', 'background-color', 'rgb(215, 204, 200)');
    deck.getCard('Monday', 'Specification').should('have.css', 'background-color', 'rgb(93, 64, 55)');

    // We check the lane title conditional style
    cy.getByTestId('lane-Monday-title').should('have.css', 'color', 'rgb(187, 222, 251)');
    cy.getByTestId('lane-Monday-title').should('have.css', 'font-size', '16px');
    cy.getByTestId('lane-Monday-title').should('have.css', 'font-style', 'normal');
    //400 means 'normal'
    cy.getByTestId('lane-Monday-title').should('have.css', 'font-weight', '400');
    cy.getByTestId('lane-Monday-title').should('have.css', 'text-decoration', 'none solid rgb(187, 222, 251)');

    // We check the lane title style
    cy.getByTestId('lane-Tuesday-title').should('have.css', 'color', 'rgb(25, 118, 210)');
    cy.getByTestId('lane-Tuesday-title').should('have.css', 'font-size', '20px');
    cy.getByTestId('lane-Tuesday-title').should('have.css', 'font-style', 'italic');
    //700 means 'bold'
    cy.getByTestId('lane-Tuesday-title').should('have.css', 'font-weight', '700');
    cy.getByTestId('lane-Tuesday-title').should(
      'have.css',
      'text-decoration',
      'underline line-through solid rgb(25, 118, 210)'
    );

    // We check the card title conditional style
    cy.getByTestId('card-Idea-title').should('have.css', 'color', 'rgb(178, 235, 242)');
    cy.getByTestId('card-Idea-title').should('have.css', 'font-size', '16px');
    cy.getByTestId('card-Idea-title').should('have.css', 'font-style', 'normal');
    //400 means 'normal'
    cy.getByTestId('card-Idea-title').should('have.css', 'font-weight', '400');
    cy.getByTestId('card-Idea-title').should('have.css', 'text-decoration', 'none solid rgb(178, 235, 242)');

    // We check the card title style
    cy.getByTestId('card-Specification-title').should('have.css', 'color', 'rgb(0, 151, 167)');
    cy.getByTestId('card-Specification-title').should('have.css', 'font-size', '22px');
    cy.getByTestId('card-Specification-title').should('have.css', 'font-style', 'italic');
    //700 means 'bold'
    cy.getByTestId('card-Specification-title').should('have.css', 'font-weight', '700');
    cy.getByTestId('card-Specification-title').should(
      'have.css',
      'text-decoration',
      'underline line-through solid rgb(0, 151, 167)'
    );

    const explorer = new Explorer();
    explorer.rename('Daily Project Dev', 'RDaily Project Dev');
    cy.getByTestId('deck-representation').should('have.css', 'background-color', 'rgb(255, 167, 38)');
  });
});
const addStyles = () => {
  const explorer = new Explorer();
  const deck = new Deck();

  // We first create a Style for the Deck Description
  explorer.getTreeItemByLabel('Deck Task Sample View').should('exist');
  explorer.expand('Deck Task Sample View');
  explorer.expand('View');
  explorer.expand('Deck Daily Representation');
  deck.createDeckRepresentationtyle('Deck Daily Representation', 'deepOrange 400');
  deck.createDeckRepresentationtyle('Deck Daily Representation', 'orange 400', "aql:self.name.startsWith('R')");

  // We now create a Style for the Lane Description
  explorer.expand('Lane Description');
  const laneStyle = {
    backgroundColor: 'amber 700',
    color: 'blue 700',
    italic: true,
    bold: true,
    underline: true,
    strikeThrough: true,
    fontSize: 20,
  };
  deck.createDeckElementStyle('Lane Description', laneStyle);

  const conditionalLaneStyle = {
    condition: "aql:self.suffix.startsWith('M')",
    backgroundColor: 'amber 100',
    color: 'blue 100',
    italic: false,
    bold: false,
    underline: false,
    strikeThrough: false,
    fontSize: 16,
  };
  deck.createDeckElementStyle('Lane Description', conditionalLaneStyle);

  // And finally for the Card Descrpition
  const cardStyle = {
    backgroundColor: 'brown 700',
    color: 'cyan 700',
    italic: true,
    bold: true,
    underline: true,
    strikeThrough: true,
    fontSize: 22,
  };
  deck.createDeckElementStyle('Card Description', cardStyle);
  const conditionalCardStyle = {
    condition: "aql:self.name.startsWith('I')",
    backgroundColor: 'brown 100',
    color: 'cyan 100',
    italic: false,
    bold: false,
    underline: false,
    strikeThrough: false,
    fontSize: 16,
  };
  deck.createDeckElementStyle('Card Description', conditionalCardStyle);
};
