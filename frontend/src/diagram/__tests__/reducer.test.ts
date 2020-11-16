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
import { initialState, reducer, INITIAL_ROOT } from '../reducer';
import { DiagramModelSource } from 'diagram/sprotty/DiagramModelSource';
import {
  EMPTY__STATE,
  LOADING__STATE,
  READY__STATE,
  COMPLETE__STATE,
  HANDLE_DATA__ACTION,
  HANDLE_CONNECTION_ERROR__ACTION,
  HANDLE_ERROR__ACTION,
  HANDLE_COMPLETE__ACTION,
  SET_ACTIVE_TOOL__ACTION,
  SWITCH_REPRESENTATION__ACTION,
  SELECTION__ACTION,
  SELECTED_ELEMENT__ACTION,
  SELECT_ZOOM_LEVEL__ACTION,
  SET_TOOL_SECTIONS__ACTION,
} from '../machine';

const modelSource = new DiagramModelSource();

const getReadyState = () => ({
  viewState: READY__STATE,
  displayedRepresentationId: 'displayedRepresentationId',
  modelSource,
  diagram: undefined,
  toolSections: [],
  contextualPalette: undefined,
  contextualMenu: undefined,
  diagramInternalState: {
    currentRoot: INITIAL_ROOT,
    activeTool: undefined,
    sourceElement: undefined,
  },
  activeTool: undefined,
  latestSelection: undefined,
  newSelection: undefined,
  zoomLevel: '1',
  subscribers: [],
  message: undefined,
  errorMessage: undefined,
});

const nodeTool = { id: 'myNodeToolId', label: 'My Node Tool', type: 'CreateNodeTool' };
const edgeTool = { id: 'myEdgeToolId', label: 'My Edge Tool', type: 'CreateEdgeTool' };

const errorMessage = 'An error has occured while retrieving the content from the server';

const completeMessage = {
  type: 'complete',
};

const subscribersUpdatedEventPayloadMessage = {
  type: 'data',
  id: '51',
  data: {
    diagramEvent: {
      __typename: 'SubscribersUpdatedEventPayload',
      subscribers: [{ username: 'jdoe' }],
    },
  },
};

const selectedElement1 = { id: 'objectId', kind: 'graph', label: 'Object' };
selectedElement1['root'] = selectedElement1;
const selection1 = { id: 'objectId', kind: 'Diagram', label: 'Object' };
const selection2 = { id: 'objectId2', kind: 'Object2', label: 'Object2' };

describe('DiagramWebSocketContainer - reducer', () => {
  it('has a proper initial state', () => {
    expect(initialState).toStrictEqual({
      viewState: EMPTY__STATE,
      displayedRepresentationId: undefined,
      modelSource: undefined,
      diagram: undefined,
      toolSections: [],
      contextualPalette: undefined,
      contextualMenu: undefined,
      newSelection: undefined,
      diagramInternalState: undefined,
      latestSelection: undefined,
      activeTool: undefined,
      zoomLevel: undefined,
      subscribers: [],
      message: undefined,
      errorMessage: undefined,
    });
  });

  it('navigates to the complete state if a connection error has been received', () => {
    const prevState = getReadyState();
    const message = {
      type: 'connection_error',
    };
    const action = { type: HANDLE_CONNECTION_ERROR__ACTION, message };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: COMPLETE__STATE,
      displayedRepresentationId: prevState.displayedRepresentationId,
      modelSource: undefined,
      diagram: undefined,
      toolSections: [],
      contextualPalette: undefined,
      contextualMenu: undefined,
      newSelection: undefined,
      diagramInternalState: undefined,
      latestSelection: undefined,
      activeTool: undefined,
      zoomLevel: undefined,
      subscribers: [],
      message: 'An error has occured while retrieving the content from the server',
      errorMessage: undefined,
    });
  });

  it('navigates to the error state if an error has been received', () => {
    const prevState = getReadyState();
    const message = errorMessage;
    const action = { type: HANDLE_ERROR__ACTION, message };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: COMPLETE__STATE,
      displayedRepresentationId: prevState.displayedRepresentationId,
      modelSource: undefined,
      diagram: undefined,
      toolSections: [],
      contextualPalette: undefined,
      contextualMenu: undefined,
      newSelection: undefined,
      diagramInternalState: undefined,
      latestSelection: undefined,
      activeTool: undefined,
      zoomLevel: undefined,
      subscribers: [],
      message,
      errorMessage: undefined,
    });
  });

  it('updates the list of subscribers if it should be updated', () => {
    const prevState = getReadyState();
    const message = subscribersUpdatedEventPayloadMessage;
    const action = { type: HANDLE_DATA__ACTION, message };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: READY__STATE,
      displayedRepresentationId: prevState.displayedRepresentationId,
      modelSource: prevState.modelSource,
      diagram: prevState.diagram,
      toolSections: prevState.toolSections,
      contextualPalette: prevState.contextualPalette,
      contextualMenu: prevState.contextualMenu,
      newSelection: undefined,
      diagramInternalState: prevState.diagramInternalState,
      latestSelection: undefined,
      activeTool: undefined,
      zoomLevel: prevState.zoomLevel,
      subscribers: message.data.diagramEvent.subscribers,
      message: undefined,
      errorMessage: undefined,
    });
  });

  it('updates the message if an error has been received while a diagram was displayed', () => {
    const prevState = getReadyState();
    const message = errorMessage;
    const action = { type: HANDLE_ERROR__ACTION, message };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: COMPLETE__STATE,
      displayedRepresentationId: prevState.displayedRepresentationId,
      modelSource: undefined,
      diagram: undefined,
      toolSections: [],
      contextualPalette: undefined,
      contextualMenu: undefined,
      newSelection: undefined,
      diagramInternalState: undefined,
      latestSelection: undefined,
      activeTool: undefined,
      zoomLevel: undefined,
      subscribers: prevState.subscribers,
      message,
      errorMessage: undefined,
    });
  });

  it('updates selected node tool', () => {
    const prevState = getReadyState();
    const action = { type: SET_ACTIVE_TOOL__ACTION, activeTool: nodeTool };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: READY__STATE,
      displayedRepresentationId: prevState.displayedRepresentationId,
      modelSource: prevState.modelSource,
      diagram: prevState.diagram,
      toolSections: prevState.toolSections,
      activeTool: nodeTool,
      contextualPalette: undefined,
      contextualMenu: undefined,
      newSelection: undefined,
      diagramInternalState: {
        currentRoot: INITIAL_ROOT,
        activeTool: nodeTool,
        sourceElement: undefined,
      },
      latestSelection: undefined,
      zoomLevel: prevState.zoomLevel,
      subscribers: prevState.subscribers,
      message: undefined,
      errorMessage: undefined,
    });
  });

  it('updates selected edge tool', () => {
    const prevState = getReadyState();
    const action = { type: SET_ACTIVE_TOOL__ACTION, activeTool: edgeTool };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: READY__STATE,
      displayedRepresentationId: prevState.displayedRepresentationId,
      modelSource: prevState.modelSource,
      diagram: prevState.diagram,
      toolSections: prevState.toolSections,
      activeTool: edgeTool,
      contextualPalette: undefined,
      contextualMenu: undefined,
      newSelection: undefined,
      diagramInternalState: {
        currentRoot: INITIAL_ROOT,
        activeTool: edgeTool,
        sourceElement: undefined,
      },
      latestSelection: undefined,
      zoomLevel: prevState.zoomLevel,
      subscribers: prevState.subscribers,
      message: undefined,
      errorMessage: undefined,
    });
  });

  it('navigates to the complete state if a complete event has been received', () => {
    const prevState = getReadyState();
    const message = completeMessage;
    const action = { type: HANDLE_COMPLETE__ACTION, message };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: COMPLETE__STATE,
      displayedRepresentationId: prevState.displayedRepresentationId,
      modelSource: undefined,
      diagram: undefined,
      toolSections: [],
      activeTool: undefined,
      contextualPalette: undefined,
      contextualMenu: undefined,
      newSelection: undefined,
      diagramInternalState: undefined,
      latestSelection: undefined,
      zoomLevel: undefined,
      subscribers: [],
      message: 'The diagram does not exist',
      errorMessage: undefined,
    });
  });

  it('Switch to another diagram', () => {
    const prevState = getReadyState();
    const action = { type: SWITCH_REPRESENTATION__ACTION, representationId: 'newDisplayedRepresentationId' };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: LOADING__STATE,
      displayedRepresentationId: 'newDisplayedRepresentationId',
      modelSource: undefined,
      diagram: undefined,
      toolSections: [],
      activeTool: undefined,
      contextualPalette: undefined,
      contextualMenu: undefined,
      newSelection: undefined,
      diagramInternalState: undefined,
      latestSelection: undefined,
      zoomLevel: '1',
      subscribers: prevState.subscribers,
      message: undefined,
      errorMessage: undefined,
    });
  });

  it('Set a selection', () => {
    const prevState = getReadyState();
    const action = { type: SELECTION__ACTION, selection: selection1 };
    const state = reducer(prevState, action);
    expect(state).toStrictEqual({
      viewState: READY__STATE,
      displayedRepresentationId: prevState.displayedRepresentationId,
      modelSource: prevState.modelSource,
      diagram: prevState.diagram,
      toolSections: prevState.toolSections,
      latestSelection: selection1,
      contextualPalette: undefined,
      contextualMenu: undefined,
      newSelection: selection1,
      diagramInternalState: {
        currentRoot: INITIAL_ROOT,
        activeTool: prevState.activeTool,
        sourceElement: undefined,
      },
      activeTool: prevState.activeTool,
      zoomLevel: prevState.zoomLevel,
      subscribers: prevState.subscribers,
      message: undefined,
      errorMessage: undefined,
    });
  });

  it('Set a selection then set a selected element', () => {
    const prevState = getReadyState();
    const action = { type: SELECTED_ELEMENT__ACTION, selection: selection1 };
    const state = reducer(prevState, action);

    const action2 = { type: SELECTION__ACTION, selection: selection2 };
    const state2 = reducer(state, action2);

    expect(state2).toStrictEqual({
      viewState: READY__STATE,
      displayedRepresentationId: prevState.displayedRepresentationId,
      modelSource: prevState.modelSource,
      diagram: prevState.diagram,
      toolSections: prevState.toolSections,
      latestSelection: selection2,
      contextualPalette: undefined,
      contextualMenu: undefined,
      newSelection: selection2,
      diagramInternalState: {
        currentRoot: INITIAL_ROOT,
        activeTool: prevState.activeTool,
        sourceElement: undefined,
      },
      activeTool: prevState.activeTool,
      zoomLevel: prevState.zoomLevel,
      subscribers: prevState.subscribers,
      message: undefined,
      errorMessage: undefined,
    });
  });

  it('Set a selected element', () => {
    const prevState = getReadyState();
    const action = { type: SELECTED_ELEMENT__ACTION, selection: selection1 };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: READY__STATE,
      displayedRepresentationId: prevState.displayedRepresentationId,
      modelSource: prevState.modelSource,
      diagram: prevState.diagram,
      toolSections: prevState.toolSections,
      latestSelection: selection1,
      contextualPalette: undefined,
      contextualMenu: undefined,
      newSelection: prevState.newSelection,
      diagramInternalState: {
        currentRoot: INITIAL_ROOT,
        activeTool: prevState.activeTool,
        sourceElement: undefined,
      },
      activeTool: prevState.activeTool,
      zoomLevel: prevState.zoomLevel,
      subscribers: prevState.subscribers,
      message: undefined,
      errorMessage: undefined,
    });
  });

  it('Set a selected element and then set a selection', () => {
    const prevState = getReadyState();
    const action = { type: SELECTION__ACTION, selection: selection2 };
    const state = reducer(prevState, action);

    const action2 = { type: SELECTED_ELEMENT__ACTION, selection: selection1 };
    const state2 = reducer(state, action2);

    expect(state2).toStrictEqual({
      viewState: READY__STATE,
      displayedRepresentationId: prevState.displayedRepresentationId,
      modelSource: prevState.modelSource,
      diagram: prevState.diagram,
      toolSections: prevState.toolSections,
      latestSelection: selection1,
      contextualPalette: undefined,
      contextualMenu: undefined,
      newSelection: selection2,
      diagramInternalState: {
        currentRoot: INITIAL_ROOT,
        activeTool: prevState.activeTool,
        sourceElement: undefined,
      },
      activeTool: prevState.activeTool,
      zoomLevel: prevState.zoomLevel,
      subscribers: prevState.subscribers,
      message: undefined,
      errorMessage: undefined,
    });
  });

  it('updates the zoom level', () => {
    const prevState = getReadyState();
    const action = { type: SELECT_ZOOM_LEVEL__ACTION, level: '2' };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: READY__STATE,
      displayedRepresentationId: prevState.displayedRepresentationId,
      modelSource: prevState.modelSource,
      diagram: prevState.diagram,
      toolSections: prevState.toolSections,
      activeTool: undefined,
      newSelection: undefined,
      diagramInternalState: {
        currentRoot: INITIAL_ROOT,
        activeTool: undefined,
        sourceElement: undefined,
      },
      latestSelection: undefined,
      contextualPalette: undefined,
      contextualMenu: undefined,
      zoomLevel: '2',
      subscribers: prevState.subscribers,
      message: undefined,
      errorMessage: undefined,
    });
  });

  it('computes a default tool for non-empty sections', () => {
    const prevState = getReadyState();
    const action = {
      type: SET_TOOL_SECTIONS__ACTION,
      toolSections: [
        {
          id: 'section0',
          tools: [],
        },
        {
          id: 'section1',
          tools: [{ id: 'singleTool' }],
          defaultTool: { id: 'singleTool' },
        },
        {
          id: 'section2',
          tools: [{ id: 'firstTool' }, { id: 'secondTool' }],
        },
      ],
    };
    const state = reducer(prevState, action);
    expect(state).toStrictEqual({
      viewState: READY__STATE,
      displayedRepresentationId: prevState.displayedRepresentationId,
      modelSource: prevState.modelSource,
      diagram: prevState.diagram,
      toolSections: [
        {
          id: 'section0',
          tools: [],
        },
        {
          id: 'section1',
          tools: [{ id: 'singleTool' }],
          defaultTool: { id: 'singleTool' },
        },
        {
          id: 'section2',
          tools: [{ id: 'firstTool' }, { id: 'secondTool' }],
          defaultTool: { id: 'firstTool' },
        },
      ],
      activeTool: prevState.activeTool,
      newSelection: prevState.newSelection,
      diagramInternalState: {
        activeTool: undefined,
        currentRoot: {
          id: 'ROOT',
          type: 'NONE',
        },
        sourceElement: undefined,
      },
      latestSelection: prevState.latestSelection,
      contextualPalette: prevState.contextualPalette,
      contextualMenu: prevState.contextualMenu,
      zoomLevel: prevState.zoomLevel,
      subscribers: prevState.subscribers,
      message: undefined,
      errorMessage: undefined,
    });
  });
});
