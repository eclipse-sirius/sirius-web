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
  machine,
  LOADING__STATE,
  PROJECT_NOT_FOUND__STATE,
  PROJECT_FETCHING_ERROR__STATE,
  PROJECT_LOADED__STATE,
  PROJECT_LOADED_AND_REPRESENTATION_DISPLAYED__STATE,
  HANDLE_FETCHED_PROJECT__ACTION,
  HANDLE_SELECTION__ACTION,
  HANDLE_REPRESENTATION_RENAMED__ACTION,
  HANDLE_SUBSCRIBERS_UPDATED__ACTION,
} from './machine';

export const initialState = {
  viewState: LOADING__STATE,
  project: undefined,
  representations: [],
  selection: undefined,
  displayedRepresentation: undefined,
  subscribers: [],
  message: undefined,
};

export const reducer = (prevState, action) => {
  const supportedActions = machine[prevState.viewState];
  if (!supportedActions[action.type]) {
    console.error(`The state ${prevState.viewState} does not support the action ${action.type}`);
  }

  let state = prevState;
  switch (action.type) {
    case HANDLE_FETCHED_PROJECT__ACTION:
      state = handleFetchedProjectAction(prevState, action);
      break;
    case HANDLE_SELECTION__ACTION:
      state = handleSelectionAction(prevState, action);
      break;
    case HANDLE_REPRESENTATION_RENAMED__ACTION:
      state = handleRepresentationRenamedAction(prevState, action);
      break;
    case HANDLE_SUBSCRIBERS_UPDATED__ACTION:
      state = handleSubscribersUpdated(prevState, action);
      break;
    default:
      state = prevState;
  }

  const newSupportedStates = supportedActions[action.type];
  if (!newSupportedStates || newSupportedStates.indexOf(state.viewState) === -1) {
    console.error(`The state ${state.viewState} should not be accessible with the action ${action.type}`);
  }
  return state;
};

const handleFetchedProjectAction = (prevState, action) => {
  const { representations, selection, displayedRepresentation, subscribers, message } = prevState;
  const { response } = action;

  let state = undefined;
  if (response.errors) {
    const message = 'An error has occurred while fetching the project. Please contact your administrator.';
    state = { viewState: PROJECT_FETCHING_ERROR__STATE, message };
  } else if (response.data.viewer.project) {
    const { project } = response.data.viewer;
    state = {
      viewState: PROJECT_LOADED__STATE,
      project,
      representations,
      selection,
      displayedRepresentation,
      subscribers,
      message,
    };
  } else {
    const message = 'The project requested does not exist';
    state = { viewState: PROJECT_NOT_FOUND__STATE, message };
  }

  return state;
};

const handleSelectionAction = (prevState, action) => {
  const { selection } = action;
  const { viewState, project, representations, displayedRepresentation, subscribers, message } = prevState;

  let newRepresentations;
  let newDisplayedRepresentation;
  let newViewState = null;

  if (selection?.kind === 'Diagram' || selection?.kind === 'Form') {
    newViewState = PROJECT_LOADED_AND_REPRESENTATION_DISPLAYED__STATE;
    newDisplayedRepresentation = selection;
    newRepresentations = [...representations];
    const selectedRepresentation = representations.find((representation) => selection.id === representation.id);
    if (!selectedRepresentation) {
      newRepresentations = [...representations, selection];
    } else {
      newRepresentations = representations;
    }
    newViewState = PROJECT_LOADED_AND_REPRESENTATION_DISPLAYED__STATE;
  } else {
    // Keep existing representations & displayedRepresentation
    newRepresentations = representations;
    newDisplayedRepresentation = displayedRepresentation;
    if (viewState === PROJECT_LOADED__STATE || viewState === PROJECT_LOADED_AND_REPRESENTATION_DISPLAYED__STATE) {
      newViewState = viewState;
    } else {
      console.error(
        'Invalid state, the viewState must be PROJECT_LOADED__STATE or PROJECT_LOADED_AND_REPRESENTATION_DISPLAYED__STATE'
      );
    }
  }

  return {
    viewState: newViewState,
    project,
    representations: newRepresentations,
    displayedRepresentation: newDisplayedRepresentation,
    selection,
    subscribers,
    message,
  };
};

const handleRepresentationRenamedAction = (prevState, action) => {
  const { viewState, project, representations, displayedRepresentation, selection, subscribers, message } = prevState;
  const { projectEvent } = action;
  const { representationId, newLabel } = projectEvent;
  let representationToRename = representations.find((r) => r.id === representationId);
  if (representationToRename) {
    representationToRename.label = newLabel;
  }

  return {
    viewState,
    project,
    representations,
    displayedRepresentation,
    selection,
    subscribers,
    message,
  };
};

const handleSubscribersUpdated = (prevState, action) => {
  const { subscribers } = action;
  const { viewState, project, representations, selection, displayedRepresentation, message } = prevState;

  return {
    viewState,
    project,
    representations,
    selection,
    displayedRepresentation,
    subscribers,
    message,
  };
};
