/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
import {
  LOADING__STATE,
  PROJECT_NOT_FOUND__STATE,
  PROJECT_FETCHING_ERROR__STATE,
  PROJECT_LOADED__STATE,
  PROJECT_LOADED_AND_REPRESENTATION_DISPLAYED__STATE,
  HANDLE_FETCHED_PROJECT__ACTION,
  HANDLE_SELECTIONS__ACTION,
  HANDLE_REPRESENTATION_RENAMED__ACTION,
  HANDLE_SUBSCRIBERS_UPDATED__ACTION,
} from '../machine';
import { initialState, reducer } from '../reducer';

const FIRST_OBJECT_ID = 'First Object';
const FIRST_OBJECT = { id: FIRST_OBJECT_ID, label: 'First object', kind: 'Object' };
const SECOND_OBJECT_ID = 'Second Object';
const SECOND_OBJECT = { id: SECOND_OBJECT_ID, label: 'Second object', kind: 'Object' };
const FIRST_DIAGRAM_ID = 'First Diagram';
const FIRST_DIAGRAM = { id: FIRST_DIAGRAM_ID, label: 'First diagram', kind: 'Diagram' };
const SECOND_DIAGRAM_ID = 'Second Diagram';
const SECOND_DIAGRAM = { id: SECOND_DIAGRAM_ID, label: 'Second diagram', kind: 'Diagram' };

const projectLoadedState = {
  viewState: PROJECT_LOADED__STATE,
  project: {
    id: '',
    name: '',
  },
  representations: [],
  displayedRepresentation: undefined,
  selections: [],
  subscribers: [],
  message: undefined,
};

const projectLoadedAndrepresentationDisplayedState = {
  viewState: PROJECT_LOADED_AND_REPRESENTATION_DISPLAYED__STATE,
  project: {
    id: '',
    name: '',
  },
  representations: [FIRST_DIAGRAM],
  displayedRepresentation: FIRST_DIAGRAM,
  selections: [FIRST_DIAGRAM],
  subscribers: [],
  message: undefined,
};

const objectSelectedState = {
  viewState: PROJECT_LOADED__STATE,
  project: {
    id: '',
    name: '',
  },
  representations: [],
  displayedRepresentation: undefined,
  selections: [FIRST_OBJECT],
  subscribers: [],
  message: undefined,
};

const representationAndObjectSelectedState = {
  viewState: PROJECT_LOADED_AND_REPRESENTATION_DISPLAYED__STATE,
  project: {
    id: '',
    name: '',
  },
  representations: [FIRST_DIAGRAM],
  displayedRepresentation: FIRST_DIAGRAM,
  selections: [FIRST_OBJECT],
  subscribers: [],
  message: undefined,
};
describe('EditProjectView - reducer', () => {
  it('navigates to the empty project loaded state', () => {
    expect(initialState).toStrictEqual({
      viewState: LOADING__STATE,
      project: undefined,
      representations: [],
      selections: [],
      displayedRepresentation: undefined,
      subscribers: [],
      message: undefined,
    });
  });

  it('navigates to the project not found state after loading a non-existing project', () => {
    const response = {
      data: {
        viewer: {},
      },
    };

    const prevState = initialState;
    const action = { type: HANDLE_FETCHED_PROJECT__ACTION, response };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: PROJECT_NOT_FOUND__STATE,
      message: 'The project requested does not exist',
    });
  });

  it('navigates to the project fetching error state after an unexpected error during the loading', () => {
    const prevState = initialState;
    const response = {
      errors: [
        {
          message: 'Unexpected error',
          location: [
            {
              line: 11,
              column: 8,
            },
          ],
          extensions: {
            classification: 'DataFetchingError',
          },
        },
      ],
    };

    const action = { type: HANDLE_FETCHED_PROJECT__ACTION, response };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: PROJECT_FETCHING_ERROR__STATE,
      message: 'An error has occurred while fetching the project. Please contact your administrator.',
    });
  });

  it('navigates to the project loaded state after loading the project', () => {
    const prevState = initialState;
    const response = {
      data: {
        viewer: {
          project: {
            id: '',
            name: '',
          },
        },
      },
    };

    const action = { type: HANDLE_FETCHED_PROJECT__ACTION, response };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: PROJECT_LOADED__STATE,
      project: response.data.viewer.project,
      representations: [],
      displayedRepresentation: undefined,
      selections: [],
      subscribers: [],
      message: undefined,
    });
  });

  it('navigates to the object selected state after selecting an object', () => {
    const prevState = projectLoadedState;
    const action = { type: HANDLE_SELECTIONS__ACTION, selections: [FIRST_OBJECT] };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: PROJECT_LOADED__STATE,
      project: projectLoadedState.project,
      representations: [],
      displayedRepresentation: undefined,
      selections: action.selections,
      subscribers: [],
      message: undefined,
    });
  });

  it('navigates to the project loaded state after renaming a representation', () => {
    const prevState = projectLoadedState;
    const action = {
      type: HANDLE_REPRESENTATION_RENAMED__ACTION,
      projectEvent: { representationId: FIRST_DIAGRAM_ID, newLabel: '' },
    };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: PROJECT_LOADED__STATE,
      project: projectLoadedState.project,
      representations: [],
      displayedRepresentation: undefined,
      selections: [],
      subscribers: [],
      message: undefined,
    });
  });

  it('navigates to the representation displayed state after selecting an object', () => {
    const prevState = projectLoadedState;
    const action = {
      type: HANDLE_SELECTIONS__ACTION,
      selections: [FIRST_DIAGRAM],
    };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: PROJECT_LOADED_AND_REPRESENTATION_DISPLAYED__STATE,
      project: projectLoadedState.project,
      representations: action.selections,
      displayedRepresentation: FIRST_DIAGRAM,
      selections: action.selections,
      subscribers: [],
      message: undefined,
    });
  });

  it('stays to the project loaded state after renaming a representation', () => {
    const prevState = projectLoadedState;
    const action = {
      type: HANDLE_REPRESENTATION_RENAMED__ACTION,
      projectEvent: { representationId: FIRST_DIAGRAM_ID, newLabel: '' },
    };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: projectLoadedState.viewState,
      project: projectLoadedState.project,
      representations: projectLoadedState.representations,
      displayedRepresentation: projectLoadedState.displayedRepresentation,
      selections: projectLoadedState.selections,
      subscribers: [],
      message: undefined,
    });
  });

  it('stays to the project loaded and representation displayed state after renaming a representation', () => {
    const prevState = projectLoadedAndrepresentationDisplayedState;
    const action = {
      type: HANDLE_REPRESENTATION_RENAMED__ACTION,
      projectEvent: { representationId: FIRST_DIAGRAM_ID, newLabel: '' },
    };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: projectLoadedAndrepresentationDisplayedState.viewState,
      project: projectLoadedAndrepresentationDisplayedState.project,
      representations: projectLoadedAndrepresentationDisplayedState.representations,
      displayedRepresentation: projectLoadedAndrepresentationDisplayedState.displayedRepresentation,
      selections: projectLoadedAndrepresentationDisplayedState.selections,
      subscribers: [],
      message: undefined,
    });
  });

  it('navigates to the representation and object selected state after selecting a representation', () => {
    const prevState = objectSelectedState;
    const action = {
      type: HANDLE_SELECTIONS__ACTION,
      selections: [SECOND_DIAGRAM],
    };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: PROJECT_LOADED_AND_REPRESENTATION_DISPLAYED__STATE,
      project: objectSelectedState.project,
      representations: action.selections,
      displayedRepresentation: SECOND_DIAGRAM,
      selections: action.selections,
      subscribers: [],
      message: undefined,
    });
  });

  it('navigates to the representation and object selected state after selecting an object', () => {
    const prevState = projectLoadedAndrepresentationDisplayedState;
    const action = { type: HANDLE_SELECTIONS__ACTION, selections: [SECOND_OBJECT] };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: PROJECT_LOADED_AND_REPRESENTATION_DISPLAYED__STATE,
      project: projectLoadedAndrepresentationDisplayedState.project,
      representations: projectLoadedAndrepresentationDisplayedState.representations,
      displayedRepresentation: projectLoadedAndrepresentationDisplayedState.displayedRepresentation,
      selections: action.selections,
      subscribers: [],
      message: undefined,
    });
  });

  it('navigates back to the representation and object selected state after selecting another representation', () => {
    const prevState = representationAndObjectSelectedState;
    const action = {
      type: HANDLE_SELECTIONS__ACTION,
      selections: [SECOND_DIAGRAM],
    };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: PROJECT_LOADED_AND_REPRESENTATION_DISPLAYED__STATE,
      project: representationAndObjectSelectedState.project,
      representations: [...representationAndObjectSelectedState.representations, ...action.selections],
      displayedRepresentation: SECOND_DIAGRAM,
      selections: action.selections,
      subscribers: [],
      message: undefined,
    });
  });

  it('navigates back to the representation and object selected state after selecting another object', () => {
    const prevState = representationAndObjectSelectedState;
    const action = { type: HANDLE_SELECTIONS__ACTION, selections: [SECOND_OBJECT] };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: PROJECT_LOADED_AND_REPRESENTATION_DISPLAYED__STATE,
      project: representationAndObjectSelectedState.project,
      representations: representationAndObjectSelectedState.representations,
      displayedRepresentation: representationAndObjectSelectedState.displayedRepresentation,
      selections: action.selections,
      subscribers: [],
      message: undefined,
    });
  });

  it('navigates back to the project loaded and representation displayed state after renaming another representation', () => {
    const prevState = projectLoadedAndrepresentationDisplayedState;
    const action = {
      type: HANDLE_REPRESENTATION_RENAMED__ACTION,
      projectEvent: { representationId: SECOND_DIAGRAM_ID, newLabel: '' },
    };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: PROJECT_LOADED_AND_REPRESENTATION_DISPLAYED__STATE,
      project: projectLoadedAndrepresentationDisplayedState.project,
      representations: projectLoadedAndrepresentationDisplayedState.representations,
      displayedRepresentation: projectLoadedAndrepresentationDisplayedState.displayedRepresentation,
      selections: projectLoadedAndrepresentationDisplayedState.selections,
      subscribers: [],
      message: undefined,
    });
  });

  it('updates the list of subscribers', () => {
    const prevState = representationAndObjectSelectedState;
    const subscribers = [{ username: 'jdoe' }];
    const action = { type: HANDLE_SUBSCRIBERS_UPDATED__ACTION, subscribers };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: PROJECT_LOADED_AND_REPRESENTATION_DISPLAYED__STATE,
      project: representationAndObjectSelectedState.project,
      representations: representationAndObjectSelectedState.representations,
      displayedRepresentation: representationAndObjectSelectedState.displayedRepresentation,
      selections: representationAndObjectSelectedState.selections,
      subscribers,
      message: undefined,
    });
  });
});
