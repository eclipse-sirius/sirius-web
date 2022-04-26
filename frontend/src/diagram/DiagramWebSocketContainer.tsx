/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo and others.
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
import { useMutation, useSubscription } from '@apollo/client';
import IconButton from '@material-ui/core/IconButton';
import Snackbar from '@material-ui/core/Snackbar';
import makeStyles from '@material-ui/core/styles/makeStyles';
import Typography from '@material-ui/core/Typography';
import CloseIcon from '@material-ui/icons/Close';
import { useMachine } from '@xstate/react';
import { ServerContext } from 'common/ServerContext';
import {
  GQLDeletionPolicy,
  GQLDiagramEventPayload,
  GQLDiagramEventSubscription,
  GQLDiagramRefreshedEventPayload,
  GQLErrorPayload,
  GQLInvokeSingleClickOnDiagramElementToolData,
  GQLInvokeSingleClickOnDiagramElementToolInput,
  GQLInvokeSingleClickOnDiagramElementToolPayload,
  GQLInvokeSingleClickOnDiagramElementToolSuccessPayload,
  GQLInvokeSingleClickOnDiagramElementToolVariables,
  GQLInvokeSingleClickOnTwoDiagramElementsToolData,
  GQLInvokeSingleClickOnTwoDiagramElementsToolInput,
  GQLInvokeSingleClickOnTwoDiagramElementsToolPayload,
  GQLInvokeSingleClickOnTwoDiagramElementsToolSuccessPayload,
  GQLInvokeSingleClickOnTwoDiagramElementsToolVariables,
  GQLSubscribersUpdatedEventPayload,
  Menu,
  Palette,
  Position,
  Tool,
} from 'diagram/DiagramWebSocketContainer.types';
import {
  CloseSelectionDialogEvent,
  DiagramRefreshedEvent,
  DiagramWebSocketContainerContext,
  DiagramWebSocketContainerEvent,
  diagramWebSocketContainerMachine,
  HandleSelectedObjectInSelectionDialogEvent,
  HideToastEvent,
  InitializeRepresentationEvent,
  ResetSelectedObjectInSelectionDialogEvent,
  ResetToolsEvent,
  SchemaValue,
  SelectedElementEvent,
  SelectionEvent,
  SelectZoomLevelEvent,
  SetActiveConnectorToolsEvent,
  SetActiveToolEvent,
  SetContextualMenuEvent,
  SetContextualPaletteEvent,
  SetDefaultToolEvent,
  ShowSelectionDialogEvent,
  ShowToastEvent,
  SubscribersUpdatedEvent,
  SwithRepresentationEvent,
} from 'diagram/DiagramWebSocketContainerMachine';
import { DropArea } from 'diagram/DropArea';
import {
  arrangeAllOp,
  deleteFromDiagramMutation,
  diagramEventSubscription,
  editLabelMutation as editLabelMutationOp,
  invokeSingleClickOnDiagramElementToolMutation,
  invokeSingleClickOnTwoDiagramElementsToolMutation,
  updateNodeBoundsOp,
  updateNodePositionOp,
} from 'diagram/operations';
import { ContextualMenu } from 'diagram/palette/ContextualMenu';
import { ContextualPalette } from 'diagram/palette/ContextualPalette';
import {
  GQLSingleClickOnDiagramElementTool,
  GQLSingleClickOnTwoDiagramElementsTool,
  GQLToolSection,
} from 'diagram/palette/ContextualPalette.types';
import {
  DiagramServer,
  HIDE_CONTEXTUAL_TOOLBAR_ACTION,
  SOURCE_ELEMENT_ACTION,
  ZOOM_IN_ACTION,
  ZOOM_OUT_ACTION,
} from 'diagram/sprotty/DiagramServer';
import {
  SetActiveConnectorToolsAction,
  SetActiveToolAction,
  SiriusSelectAction,
  SiriusUpdateModelAction,
  SourceElementAction,
  SprottySelectAction,
  ZoomToAction,
} from 'diagram/sprotty/DiagramServer.types';
import { edgeCreationFeedback } from 'diagram/sprotty/edgeCreationFeedback';
import { Toolbar } from 'diagram/Toolbar';
import { atLeastOneSingleClickOnTwoDiagramElementsTool, canInvokeTool } from 'diagram/toolServices';
import React, { useCallback, useContext, useEffect, useRef } from 'react';
import { SelectionDialogWebSocketContainer } from 'selection/SelectionDialogWebSocketContainer';
import { EditLabelAction, HoverFeedbackAction, SEdge, SGraph, SModelElement, SNode, SPort } from 'sprotty';
import { FitToScreenAction } from 'sprotty-protocol';
import { v4 as uuid } from 'uuid';
import { RepresentationComponentProps, Selection, SelectionEntry } from 'workbench/Workbench.types';

const useDiagramWebSocketContainerStyle = makeStyles((theme) => ({
  container: {
    display: 'grid',
    gridTemplateRows: 'min-content 1fr',
    gridTemplateColumns: '1fr',
  },
  contextualPalette: {
    position: 'absolute',
  },
  contextualMenu: {
    position: 'absolute',
  },
  diagramContainer: {
    display: 'grid',
    gridTemplateColumns: '1fr',
    gridTemplateRows: '1fr',
  },
  noDiagram: {
    alignItems: 'center',
    justifyItems: 'center',
  },
  diagramWrapper: {
    display: 'flex',
    alignItems: 'stretch',
  },
  diagram: {
    display: 'flex',
    alignItems: 'stretch',
    flexGrow: 1,
    '& > *': {
      flexGrow: 1,
    },
  },
}));

const isDiagramRefreshedEventPayload = (payload: GQLDiagramEventPayload): payload is GQLDiagramRefreshedEventPayload =>
  payload.__typename === 'DiagramRefreshedEventPayload';
const isSubscribersUpdatedEventPayload = (
  payload: GQLDiagramEventPayload
): payload is GQLSubscribersUpdatedEventPayload => payload.__typename === 'SubscribersUpdatedEventPayload';
const isErrorPayload = (payload: GQLDiagramEventPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
const isInvokeSingleClickOnDiagramElementToolSuccessPayload = (
  payload: GQLInvokeSingleClickOnDiagramElementToolPayload
): payload is GQLInvokeSingleClickOnDiagramElementToolSuccessPayload =>
  payload.__typename === 'InvokeSingleClickOnDiagramElementToolSuccessPayload';
const isInvokeSingleClickOnTwoDiagramElementsToolSuccessPayload = (
  payload: GQLInvokeSingleClickOnTwoDiagramElementsToolPayload
): payload is GQLInvokeSingleClickOnTwoDiagramElementsToolSuccessPayload =>
  payload.__typename === 'InvokeSingleClickOnTwoDiagramElementsToolSuccessPayload';

/**
 * Here be dragons!
 *
 * If you want to modify this component, you need to understand this documentation first and foremost.
 * Any change to this component which involve a change to the reducer, its machine or one of the useEffect
 * functions will REQUIRE an update to this documentation. The lifecycle of this component is not perfect
 * and it can still be dramatically improved. Yet, we have currently a stable situation which can be
 * documented.
 *
 * This component can be in one of four states:
 * - EMPTY__STATE
 * - LOADING__STATE
 * - READY__STATE
 * - COMPLETE__STATE
 *
 * Almost a dozen variables are involved to determine whether or not we should transition from one state
 * to another. The EMPTY__STATE is used to represent a state where this component is blank and not
 * connected to any GraphQL subscription. In this state, the component is not even connected to a specific
 * representation. This state is temporary and will not persist for long since immediatly after starting
 * from this state, we will transition to the LOADING__STATE. This first state is thus used in order to
 * trigger the initial transition of the state machine in a similar fashion as some future transitions.
 *
 * From the LOADING__STATE, the component will have only one goal, to perform the initialization of the
 * diagram server. Once initialized the component will move to the READY__STATE. In the READY__STATE, we
 * will start the GraphQL subscription and listen to diagram events coming from the server. In this state,
 * the various callbacks defined in this component and used by our Sprotty code can be used safely.
 *
 * If we receive a connection error while starting the GraphQL subscription or if we encounter a GraphQL-WS
 * error or complete message, we will move to the COMPLETE__STATE. In this state, we know that the diagram
 * being displayed cannot be displayed anymore. The diagram has simply been deleted by someone (the current
 * user or another user) or the diagram has never existed (if someone has entered an invalid URL for example).
 * In this case, we will carefully remove the Sprotty diagram from the component (see more about that in a
 * large comment below). Once in the COMPLETE__STATE, we will only be able to perform one transition, switching
 * to another representation. Such transition will make us switch to the LOADING__STATE to wait for the
 * initialization of a new diagram server instance.
 *
 * Here are some use cases showing how the component should react:
 *
 * 1/ Manipulating diagrams
 *
 * In the default use case, a user will open a diagram, view it and iteract with it. For that, we will
 * start in the EMPTY__STATE, once a first empty rendering has been done and the React ref has been
 * initialized, we will move to the initialization of the diagram server. When the diagram server has
 * been initialized, we will start the GraphQL subscription.
 *
 * When we will receive some data, the user will finally be able to see a diagram on screen and interact
 * with it. Most of the work of a user will be done in this READY__STATE. From there, we have a working
 * diagram server ready to interact with Sprotty.
 *
 * +-----------------+                                +-------------------+                                        +-----------------+
 * |                 |                                |                   |                                        |                 |
 * |   EMPTY STATE   +---[SWITCHING REPRESENTATION]-->|   LOADING STATE   +---[INITIALIZING THE DIAGRAM SERVER]--->|   READY STATE   |
 * |                 |                                |                   |                                        |                 |
 * +-----------------+                                +-------------------+                                        +-----------------+
 *
 * 2/ Switching between diagrams
 *
 * When we will switch from one diagram to another using either our tabs, the explorer or by changing the
 * URL. First, we will have a diagram loaded by the user (see the previous section) so here we will start
 * with the READY__STATE. Switching to another diagram will make us initialize everything once again from
 * scratch. This new initialization step is required in order to have the proper representationId in the
 * closure of our callbacks used by Sprotty to perform our mutations. While this could be simplified,
 * keep in mind that switching to another diagram requires initializing the diagram server.
 *
 * +-----------------+                                 +-------------------+                                        +-----------------+
 * |                 |                                 |                   |                                        |                 |
 * |   READY STATE   +---[SWITCHING REPRESENTATION]--->|   LOADING STATE   +---[INITIALIZING THE DIAGRAM SERVER]--->|   READY STATE   |
 * |                 |                                 |                   |                                        |                 |
 * +-----------------+                                 +-------------------+                                        +-----------------+
 *
 * Switching from one diagram to another or switching from no diagram to the first selected diagram are
 * thus performed in a similar way.
 *
 * 3/ Reacting to the diagram deletion
 *
 * When a diagram is deleted, the backend will send us a complete message indicating that we will never
 * ever receive anymore diagram refresh events for the diagram. Once that message has been received, we
 * will switch to the COMPLETE__STATE and remove the diagram from the interface.
 *
 * +-----------------+                                   +--------------------+
 * |                 |                                   |                    |
 * |   READY STATE   +---[RECEIVE A COMPLETE MESSAGE]--->|   COMPLETE STATE   |
 * |                 |                                   |                    |
 * +-----------------+                                   +--------------------+
 *
 * The same behavior would occur in case of an error or a connection error since those are errors we cannot
 * recover from.
 *
 * 4/ Switching to another diagram
 *
 * When we are working on a proper diagram and switching to a diagram which does not exist anymore or
 * when we are trying to open a deleted diagram, we will start from either the READY__STATE in case we
 * were viewing an existing diagram or from the COMPLETE__STATE if we were viewing a deleted or non-existent
 * diagram. In those situations, we will start by going back to the LOADING__STATE to wait for the diagram
 * server initialization.
 *
 * Once initialized, the diagram server will receive a complete message indicating that we will never ever
 * receive any diagram refresh event for the given representationId and thus that the diagram does not exist.
 * The server cannot distinguish a representation which has never existed and a representation which has
 * been deleted.
 *
 * The only way to properly deactivate Sprotty is both to remove the diagram server and to remove the base
 * div used to display the diagram. This div can only be removed by React thanks to a carefully arranged
 * organization. This behavior is documented later in this component.
 *
 * +-----------------+
 * |                 |
 * |   READY STATE   +------+
 * |                 |      |                                +-------------------+                                        +-----------------+
 * +-----------------+      |                                |                   |                                        |                 |
 *                          +--[SWITCHING REPRESENTATION]--->|   LOADING STATE   +---[INITIALIZING THE DIAGRAM SERVER]--->|   READY STATE   |
 * +--------------------+   |                                |                   |                                        |                 |
 * |                    |   |                                +-------------------+                                        +-----------------+
 * |   COMPLETE STATE   +---+
 * |                    |
 * +--------------------+
 *
 * Then after having reach the READY__STATE, we will receive a complete message in case we are trying to
 * view a diagram which does not exist or which has been deleted. Deleting a diagram by ourserlves, trying
 * to view a diagram deleted by another person or trying to view a diagram which has never existed are
 * exactly the same thing.
 *
 * 5/ Switching from a deleted diagram to another diagram
 *
 * If we are viewing a deleted diagram, we will have reach the COMPLETE__STATE. In order to display another
 * diagram, we will need to initialize the Sprotty base div once again. For that, we will need to display
 * it and connect it to React with useRef() once again. In order to perform that, we will need to move back
 * to the LOADING__STATE once again.
 *
 * +--------------------+                                 +-------------------+
 * |                    |                                 |                   |
 * |   COMPLETE STATE   +---[SWITCHING REPRESENTATION]--->|   LOADING STATE   |
 * |                    |                                 |                   |
 * +--------------------+                                 +-------------------+
 *
 * Once the diagram server has been initialized, we will either start receiving proper diagram data and thus
 * switch to the diagram loaded state or we will receive a complete message and switch back to the complete
 * state since we would have switched to another deleted diagram.
 *
 *
 * As you may have understood it, the lifecycle of this component has evolved organically due to the complexity
 * of managing multiple useEffect hooks, user interactions and Websocket messages. The current organization is
 * not perfect but some proper states are starting to emerge from this. We have something like six potentially
 * asynchronous processes to schedule properly with close to a dozen variables which should be in a proper state
 * in each situation. This is complex but it is getting better updates after updates for the moment.
 *
 * @author sbegaudeau
 */
export const DiagramWebSocketContainer = ({
  editingContextId,
  representationId,
  readOnly,
  selection,
  setSelection,
}: RepresentationComponentProps) => {
  const diagramDomElement = useRef(null);
  const { httpOrigin } = useContext(ServerContext);
  const classes = useDiagramWebSocketContainerStyle();
  const [{ value, context }, dispatch] = useMachine<DiagramWebSocketContainerContext, DiagramWebSocketContainerEvent>(
    diagramWebSocketContainerMachine
  );
  const { toast, diagramWebSocketContainer, selectionDialog } = value as SchemaValue;
  const {
    id,
    displayedRepresentationId,
    diagramServer,
    diagram,
    toolSections,
    contextualPalette,
    contextualMenu,
    activeTool,
    activeConnectorTools,
    newSelection,
    zoomLevel,
    subscribers,
    message,
    selectedObjectId,
  } = context;

  const [
    deleteElementsMutation,
    { loading: deleteFromDiagramLoading, data: deleteFromDiagramData, error: deleteFromDiagramError },
  ] = useMutation(deleteFromDiagramMutation);
  const [
    invokeSingleClickOnDiagramElementTool,
    {
      loading: invokeSingleClickOnDiagramElementToolLoading,
      data: invokeSingleClickOnDiagramElementToolData,
      error: invokeSingleClickOnDiagramElementToolError,
    },
  ] = useMutation<GQLInvokeSingleClickOnDiagramElementToolData, GQLInvokeSingleClickOnDiagramElementToolVariables>(
    invokeSingleClickOnDiagramElementToolMutation
  );
  const [
    invokeSingleClickOnTwoDiagramElementsTool,
    {
      loading: invokeSingleClickOnTwoDiagramElementsToolLoading,
      data: invokeSingleClickOnTwoDiagramElementsToolData,
      error: invokeSingleClickOnTwoDiagramElementsToolError,
    },
  ] = useMutation<
    GQLInvokeSingleClickOnTwoDiagramElementsToolData,
    GQLInvokeSingleClickOnTwoDiagramElementsToolVariables
  >(invokeSingleClickOnTwoDiagramElementsToolMutation);
  const [editLabelMutation, { loading: editLabelLoading, data: editLabelData, error: editLabelError }] =
    useMutation(editLabelMutationOp);
  const [
    updateNodePositionMutation,
    { loading: updateNodePositionLoading, data: updateNodePositionData, error: updateNodePositionError },
  ] = useMutation(updateNodePositionOp);
  const [
    updateNodeBoundsMutation,
    { loading: updateNodeBoundsLoading, data: updateNodeBoundsData, error: updateNodeBoundsError },
  ] = useMutation(updateNodeBoundsOp);
  const [arrangeAllMutation, { loading: arrangeAllLoading, data: arrangeAllData, error: arrangeAllError }] =
    useMutation(arrangeAllOp);

  /**
   * Dispatch the diagram to the diagramServer if our state indicate that diagram has changed.
   */
  useEffect(() => {
    if (diagramServer) {
      const action: SiriusUpdateModelAction = { kind: 'siriusUpdateModel', diagram, readOnly };
      diagramServer.actionDispatcher.dispatch(action);
    }
  }, [diagram, diagramServer, readOnly]);

  /**
   * Dispatch the activeTool to the diagramServer if our state indicate that activeTool has changed.
   */
  useEffect(() => {
    if (diagramServer) {
      const action: SetActiveToolAction = { kind: 'activeTool', tool: activeTool };
      diagramServer.actionDispatcher.dispatch(action);
    }
  }, [activeTool, diagramServer, dispatch]);

  /**
   * Dispatch the activeConnectorTool to the diagramServer if our state indicate that activeConnectorTool has changed.
   */
  useEffect(() => {
    if (diagramServer) {
      const action: SetActiveConnectorToolsAction = { kind: 'activeConnectorTools', tools: activeConnectorTools };
      diagramServer.actionDispatcher.dispatch(action);
    }
  }, [activeConnectorTools, diagramServer, dispatch]);

  /**
   * Dispatch the selection if our props indicate that selection has changed.
   */
  useEffect(() => {
    if (diagramWebSocketContainer === 'ready') {
      const selectionEvent: SelectionEvent = { type: 'SELECTION', selection };
      dispatch(selectionEvent);
    }
  }, [selection, diagramServer, diagramWebSocketContainer, dispatch]);

  /**
   * Dispatch the new selection to the diagramServer if our state indicate that new selection has changed.
   */
  useEffect(() => {
    if (diagramServer && newSelection) {
      const action: SiriusSelectAction = { kind: 'siriusSelectElement', selection: newSelection };
      diagramServer.actionDispatcher.dispatch(action);
    }
  }, [newSelection, diagramServer, dispatch]);

  /**
   * Switch to another diagram if our props indicate that we should display a different diagram
   * then the one currently displayed. This will be used to start displaying a diagram from nothing
   * and to reset everything while switching from one diagram to another.
   *
   * This will bring us to the loading state in which the diagram server will have to be reinitialized.
   */
  useEffect(() => {
    if (displayedRepresentationId !== representationId) {
      const switchRepresentationEvent: SwithRepresentationEvent = { type: 'SWITCH_REPRESENTATION', representationId };
      dispatch(switchRepresentationEvent);
    }
  }, [displayedRepresentationId, representationId, dispatch]);

  const resetTools = useCallback(() => {
    edgeCreationFeedback.reset();
    const resetToolsAction: ResetToolsEvent = { type: 'RESET_TOOLS' };
    dispatch(resetToolsAction);
  }, [dispatch]);

  const deleteElements = useCallback(
    (diagramElements: SModelElement[], deletionPolicy: GQLDeletionPolicy): void => {
      const edgeIds = diagramElements.filter((diagramElement) => diagramElement instanceof SEdge).map((elt) => elt.id);
      const nodeIds = diagramElements
        .filter((diagramElement) => diagramElement instanceof SNode || diagramElement instanceof SPort)
        .map((elt) => elt.id);

      const input = {
        id: uuid(),
        editingContextId,
        representationId,
        nodeIds,
        edgeIds,
        deletionPolicy,
      };
      deleteElementsMutation({ variables: { input } });
      resetTools();
    },
    [editingContextId, representationId, deleteElementsMutation, resetTools]
  );

  const invokeTool = useCallback(
    (tool, ...params) => {
      if (tool) {
        const { id: toolId } = tool;
        if (tool.__typename === 'SingleClickOnTwoDiagramElementsTool') {
          const [diagramSourceElementId, diagramTargetElementId, sourcePosition, targetPosition] = params;
          const sourcePositionX = sourcePosition?.x;
          const sourcePositionY = sourcePosition?.y;
          const targetPositionX = targetPosition?.x;
          const targetPositionY = targetPosition?.y;

          const input: GQLInvokeSingleClickOnTwoDiagramElementsToolInput = {
            id: uuid(),
            editingContextId,
            representationId,
            diagramSourceElementId,
            diagramTargetElementId,
            toolId,
            sourcePositionX,
            sourcePositionY,
            targetPositionX,
            targetPositionY,
          };
          invokeSingleClickOnTwoDiagramElementsTool({ variables: { input } });

          edgeCreationFeedback.reset();
        } else {
          const [diagramElementId, startingPosition] = params;
          let startingPositionX = startingPosition ? startingPosition.x : 0;
          let startingPositionY = startingPosition ? startingPosition.y : 0;
          const input: GQLInvokeSingleClickOnDiagramElementToolInput = {
            id: uuid(),
            editingContextId,
            representationId,
            diagramElementId,
            toolId,
            startingPositionX,
            startingPositionY,
            selectedObjectId,
          };
          invokeSingleClickOnDiagramElementTool({ variables: { input } });
        }
        resetTools();
      }
    },
    [
      editingContextId,
      representationId,
      selectedObjectId,
      resetTools,
      invokeSingleClickOnDiagramElementTool,
      invokeSingleClickOnTwoDiagramElementsTool,
    ]
  );

  const moveElement = useCallback(
    (diagramElementId, newPositionX, newPositionY) => {
      const input = {
        id: uuid(),
        editingContextId,
        representationId,
        diagramElementId,
        newPositionX,
        newPositionY,
      };
      updateNodePositionMutation({ variables: { input } });
    },
    [editingContextId, representationId, updateNodePositionMutation]
  );

  const resizeElement = useCallback(
    (diagramElementId, newPositionX, newPositionY, newWidth, newHeight) => {
      const input = {
        id: uuid(),
        editingContextId,
        representationId,
        diagramElementId,
        newPositionX,
        newPositionY,
        newWidth,
        newHeight,
      };
      updateNodeBoundsMutation({ variables: { input } });
    },
    [editingContextId, representationId, updateNodeBoundsMutation]
  );

  const invokeHover = (id: string, mouseIsHover: boolean) => {
    if (diagramServer) {
      diagramServer.actionDispatcher.dispatch(
        HoverFeedbackAction.create({ mouseoverElement: id, mouseIsOver: mouseIsHover })
      );
    }
  };
  /**
   * Initialize the diagram server used by Sprotty in order to perform the diagram edition. This
   * initialization will be done each time we are in the loading state.
   */
  useEffect(() => {
    const onSelectElement = (
      selectedElement: SModelElement,
      diagramServer: DiagramServer,
      position: Position,
      event: MouseEvent
    ) => {
      const newSelection: Selection = { entries: [] };

      if (selectedElement instanceof SGraph) {
        const { id, label, kind } = selectedElement as any; // (as any) to be removed when the proper type will be available
        newSelection.entries.push({ id, label, kind });
      } else if (
        selectedElement instanceof SNode ||
        selectedElement instanceof SPort ||
        selectedElement instanceof SEdge
      ) {
        const { targetObjectId, targetObjectKind, targetObjectLabel } = selectedElement as any; // (as any) to be removed when the proper type will be available

        const semanticSelectionEntry: SelectionEntry = {
          id: targetObjectId,
          label: targetObjectLabel,
          kind: targetObjectKind,
        };

        newSelection.entries.push(semanticSelectionEntry);
      }

      setSelection(newSelection);

      const selectedElementEvent: SelectedElementEvent = { type: 'SELECTED_ELEMENT', selection: newSelection };
      dispatch(selectedElementEvent);

      /**
       * Dispatch the selected element to the diagramServer if our state indicate that selected element has changed.
       * We can't use useEffet hook here, because SPROTTY_SELECT_ACTION must be send to the diagramServer even
       * if the same element is selected several times (useEffect hook only reacts if the selected element is not the same).
       * We can also not use diagramServer from the reducer state here, because it is undefined when onSelectElement() is called.
       */
      const selectSprottyAction: SprottySelectAction = {
        kind: 'sprottySelectElement',
        element: selectedElement,
      };
      diagramServer.actionDispatcher.dispatch(selectSprottyAction);
    };

    const getCursorOn = (element, diagramServer: DiagramServer) => {
      let cursor = 'pointer';
      if (diagramServer.diagramSource) {
        if (diagramServer.activeConnectorTools.length > 0) {
          const cursorAllowed = atLeastOneSingleClickOnTwoDiagramElementsTool(
            diagramServer.activeConnectorTools,
            diagramServer.diagramSource,
            element
          );
          if (cursorAllowed) {
            cursor = 'copy';
          } else {
            cursor = 'not-allowed';
          }
        } else if (diagramServer.activeTool) {
          const cursorAllowed = canInvokeTool(diagramServer.activeTool, diagramServer.diagramSource, element);
          if (cursorAllowed) {
            cursor = 'copy';
          } else {
            cursor = 'not-allowed';
          }
        }
      }
      return cursor;
    };
    const setActiveTool = (tool: Tool | null) => {
      const setActiveToolEvent: SetActiveToolEvent = { type: 'SET_ACTIVE_TOOL', activeTool: tool };
      dispatch(setActiveToolEvent);
    };
    const editLabel = (labelId, newText) => {
      const input = {
        id: uuid(),
        editingContextId,
        representationId,
        labelId,
        newText,
      };
      editLabelMutation({ variables: { input } });
    };
    const setContextualPalette = (contextualPalette: Palette) => {
      if (!readOnly) {
        const setContextualPaletteEvent: SetContextualPaletteEvent = {
          type: 'SET_CONTEXTUAL_PALETTE',
          contextualPalette,
        };
        dispatch(setContextualPaletteEvent);
      }
    };
    const setContextualMenu = (contextualMenu: Menu) => {
      if (!readOnly) {
        const setContextualMenuEvent: SetContextualMenuEvent = {
          type: 'SET_CONTEXTUAL_MENU',
          contextualMenu,
        };
        dispatch(setContextualMenuEvent);
      }
    };

    if (diagramWebSocketContainer === 'loading' && diagramDomElement.current) {
      const initializeRepresentationEvent: InitializeRepresentationEvent = {
        type: 'INITIALIZE',
        diagramDomElement,
        deleteElements,
        invokeTool,
        moveElement,
        resizeElement,
        editLabel,
        onSelectElement,
        getCursorOn,
        setActiveTool,
        toolSections,
        setContextualPalette,
        setContextualMenu,
        httpOrigin,
      };
      dispatch(initializeRepresentationEvent);
    }
  }, [
    diagramWebSocketContainer,
    displayedRepresentationId,
    diagramServer,
    setSelection,
    deleteElements,
    invokeTool,
    moveElement,
    resizeElement,
    editLabelMutation,
    toolSections,
    selection,
    editingContextId,
    representationId,
    httpOrigin,
    dispatch,
    readOnly,
  ]);

  useEffect(() => {
    if (selectedObjectId && activeTool && contextualPalette) {
      invokeTool(activeTool, contextualPalette.element.id, contextualPalette.palettePosition);
      const sourceElementAction: SourceElementAction = { kind: SOURCE_ELEMENT_ACTION, sourceElement: null };
      diagramServer.actionDispatcher.dispatch(sourceElementAction);
      const resetSelectedObjectInSelectionDialogEvent: ResetSelectedObjectInSelectionDialogEvent = {
        type: 'RESET_SELECTED_OBJECT_IN_SELECTION_DIALOG',
      };
      dispatch(resetSelectedObjectInSelectionDialogEvent);
    }
  }, [activeTool, diagramServer, invokeTool, dispatch, selectedObjectId, contextualPalette]);

  const { error } = useSubscription<GQLDiagramEventSubscription>(diagramEventSubscription, {
    variables: {
      input: {
        id,
        editingContextId,
        diagramId: representationId,
      },
    },
    fetchPolicy: 'no-cache',
    skip: diagramWebSocketContainer !== 'ready',
    onSubscriptionData: ({ subscriptionData }) => {
      if (subscriptionData?.data) {
        const { diagramEvent } = subscriptionData.data;
        if (isDiagramRefreshedEventPayload(diagramEvent)) {
          const diagramRefreshedEvent: DiagramRefreshedEvent = {
            type: 'HANDLE_DIAGRAM_REFRESHED',
            diagram: diagramEvent.diagram,
          };
          dispatch(diagramRefreshedEvent);
        } else if (isSubscribersUpdatedEventPayload(diagramEvent)) {
          const subscribersUpdatedEvent: SubscribersUpdatedEvent = {
            type: 'HANDLE_SUBSCRIBERS_UPDATED',
            subscribers: diagramEvent.subscribers,
          };
          dispatch(subscribersUpdatedEvent);
        } else if (isErrorPayload(diagramEvent)) {
          const showToastEvent: ShowToastEvent = { type: 'SHOW_TOAST', message: diagramEvent.message };
          dispatch(showToastEvent);
        }
      }
    },
    onSubscriptionComplete: () => {
      dispatch({ type: 'HANDLE_COMPLETE' });
    },
  });

  useEffect(() => {
    if (error) {
      const message = 'An error has occurred while trying to retrieve the diagram';
      const showToastEvent: ShowToastEvent = { type: 'SHOW_TOAST', message };
      dispatch(showToastEvent);
      dispatch({ type: 'HANDLE_COMPLETE' });
    }
  }, [error, dispatch]);

  const onZoomIn = () => {
    if (diagramServer) {
      diagramServer.actionDispatcher.dispatch({ kind: ZOOM_IN_ACTION });
    }
  };

  const onZoomOut = () => {
    if (diagramServer) {
      diagramServer.actionDispatcher.dispatch({ kind: ZOOM_OUT_ACTION });
    }
  };

  const onFitToScreen = () => {
    if (diagramServer) {
      diagramServer.actionDispatcher.dispatch(FitToScreenAction.create([], { padding: 20 }));
    }
  };

  const onArrangeAll = () => {
    const input = {
      id: uuid(),
      editingContextId,
      representationId,
    };
    arrangeAllMutation({ variables: { input } });
  };

  const setZoomLevel = (level) => {
    if (diagramServer) {
      const action: ZoomToAction = { kind: 'zoomTo', level };
      diagramServer.actionDispatcher.dispatch(action);
      const selectZoomLevelEvent: SelectZoomLevelEvent = { type: 'SELECT_ZOOM_LEVEL', level };
      dispatch(selectZoomLevelEvent);
    }
  };

  const convertInSprottyCoordinate = async (clientX, clientY) => {
    if (diagramServer) {
      return await diagramServer.convertInSprottyCoordinate(clientX, clientY);
    } else {
      return {
        x: 0,
        y: 0,
      };
    }
  };

  const handleError = useCallback(
    (loading, data, error) => {
      if (!loading) {
        if (error) {
          const showToastEvent: ShowToastEvent = {
            type: 'SHOW_TOAST',
            message: 'An error has occurred while executing this action, please contact the server administrator',
          };
          dispatch(showToastEvent);
        }
        if (data) {
          const keys = Object.keys(data);
          if (keys.length > 0) {
            const firstKey = keys[0];
            const firstField = data[firstKey];
            if (firstField.__typename === 'ErrorPayload') {
              const { message } = firstField;
              const showToastEvent: ShowToastEvent = {
                type: 'SHOW_TOAST',
                message,
              };
              dispatch(showToastEvent);
            }
          }
        }
      }
    },
    [dispatch]
  );

  useEffect(() => {
    handleError(updateNodePositionLoading, updateNodePositionData, updateNodePositionError);
  }, [updateNodePositionLoading, updateNodePositionData, updateNodePositionError, handleError]);
  useEffect(() => {
    handleError(updateNodeBoundsLoading, updateNodeBoundsData, updateNodeBoundsError);
  }, [updateNodeBoundsLoading, updateNodeBoundsData, updateNodeBoundsError, handleError]);
  useEffect(() => {
    handleError(editLabelLoading, editLabelData, editLabelError);
  }, [editLabelLoading, editLabelData, editLabelError, handleError]);
  useEffect(() => {
    handleError(deleteFromDiagramLoading, deleteFromDiagramData, deleteFromDiagramError);
  }, [deleteFromDiagramLoading, deleteFromDiagramData, deleteFromDiagramError, handleError]);
  useEffect(() => {
    handleError(
      invokeSingleClickOnDiagramElementToolLoading,
      invokeSingleClickOnDiagramElementToolData,
      invokeSingleClickOnDiagramElementToolError
    );
    if (!invokeSingleClickOnDiagramElementToolLoading) {
      resetTools();
    }
    if (invokeSingleClickOnDiagramElementToolData) {
      const { invokeSingleClickOnDiagramElementTool } = invokeSingleClickOnDiagramElementToolData;
      if (isInvokeSingleClickOnDiagramElementToolSuccessPayload(invokeSingleClickOnDiagramElementTool)) {
        const { newSelection } = invokeSingleClickOnDiagramElementTool;
        if (newSelection?.entries.length ?? 0 > 0) {
          setSelection(newSelection);
        }
      }
    }
  }, [
    invokeSingleClickOnDiagramElementToolLoading,
    invokeSingleClickOnDiagramElementToolData,
    invokeSingleClickOnDiagramElementToolError,
    handleError,
    resetTools,
    setSelection,
    selection,
  ]);
  useEffect(() => {
    handleError(
      invokeSingleClickOnTwoDiagramElementsToolLoading,
      invokeSingleClickOnTwoDiagramElementsToolData,
      invokeSingleClickOnTwoDiagramElementsToolError
    );
    if (invokeSingleClickOnTwoDiagramElementsToolData) {
      const { invokeSingleClickOnTwoDiagramElementsTool } = invokeSingleClickOnTwoDiagramElementsToolData;
      if (isInvokeSingleClickOnTwoDiagramElementsToolSuccessPayload(invokeSingleClickOnTwoDiagramElementsTool)) {
        const { newSelection } = invokeSingleClickOnTwoDiagramElementsTool;
        if (newSelection?.entries.length ?? 0 > 0) {
          setSelection(newSelection);
        }
      }
    }
  }, [
    invokeSingleClickOnTwoDiagramElementsToolLoading,
    invokeSingleClickOnTwoDiagramElementsToolData,
    invokeSingleClickOnTwoDiagramElementsToolError,
    handleError,
    setSelection,
    selection,
  ]);
  useEffect(() => {
    handleError(arrangeAllLoading, arrangeAllData, arrangeAllError);
  }, [arrangeAllLoading, arrangeAllData, arrangeAllError, handleError]);
  /**
   * Gather up, it's time for a story.
   *
   * Once upon a time, the following code was victim of a painful bug. The first version of the bug caused
   * some content to remain even after we told React to remove it. The issue was coming from the fact we
   * were giving control of one of our divs to Sprotty, the div with "ref={diagramElement}". Sprotty seems
   * to heavily modify this div, including it seems destroying it and recreating it. This is not unsurprising
   * since we told it that it is under its control.
   *
   * Now when we need to remove a diagram, because it does not exist anymore, we need to remove this content.
   * An issue arise because we told React to remove the div in question but React has kept a reference to
   * the original version of the div that it has rendered. Now Sprotty has replaced that div with a new one
   * and thus when React tries to delete the content it tries to remove a div from a parent div but the div
   * in question has been long gone from its parent. As a result, a DOMException occurred, warning us that
   * "the node to be removed is not a child of this node".
   *
   * In order to fix this issue, two intermediary divs have been introduced with the id "diagram-container"
   * and "diagram-wrapper". The goal of diagram container is to act as the stable React parent div from which
   * we will remove some content. The diagram wapper will act as the stable child div which will be removed
   * when the content is not available anymore. React does not seems to care that under this div there are
   * some content which is not under its control anymore. Telling React to delete the diagram wrapper will
   * make it drop the whole DOM subtree without looking precisely at its content. It's faster for React and
   * better for us since it allows us to fix our issue.
   *
   * And thus, those various divs lived long and happily ever after...
   *
   * TLDR: Do not touch the div structure below without a deep understanding of the React reconciliation algorithm!
   */
  let contextualPaletteContent;
  if (!readOnly && contextualPalette) {
    const { element, palettePosition, canvasBounds, edgeStartPosition, renameable, deletable } = contextualPalette;
    const { x, y } = edgeStartPosition;
    const style = {
      left: canvasBounds.x + 'px',
      top: canvasBounds.y + 'px',
    };
    let invokeLabelEditFromContextualPalette;
    if (renameable) {
      invokeLabelEditFromContextualPalette = () =>
        diagramServer.actionDispatcher.dispatchAll([
          { kind: HIDE_CONTEXTUAL_TOOLBAR_ACTION },
          EditLabelAction.create(element.editableLabel.id),
        ]);
    }
    let invokeDeleteFromContextualPalette;
    if (deletable) {
      invokeDeleteFromContextualPalette = (deletionPolicy: GQLDeletionPolicy) =>
        deleteElements([element], deletionPolicy);
    }
    const invokeToolFromContextualPalette = (tool) => {
      if (tool.__typename === 'SingleClickOnTwoDiagramElementsTool') {
        const setActiveToolEvent: SetActiveToolEvent = { type: 'SET_ACTIVE_TOOL', activeTool: tool };
        dispatch(setActiveToolEvent);
        const setContextualPaletteEvent: SetContextualPaletteEvent = {
          type: 'SET_CONTEXTUAL_PALETTE',
          contextualPalette: null,
        };
        dispatch(setContextualPaletteEvent);
        edgeCreationFeedback.init(x, y);

        const action: SourceElementAction = {
          kind: 'sourceElement',
          sourceElement: { element, position: { ...palettePosition } },
        };
        diagramServer.actionDispatcher.dispatch(action);
      } else if (tool.__typename === 'SingleClickOnDiagramElementTool') {
        if (tool.selectionDescriptionId) {
          const showSelectionDialogEvent: ShowSelectionDialogEvent = {
            type: 'SHOW_SELECTION_DIALOG',
            activeTool: tool,
          };
          dispatch(showSelectionDialogEvent);
        } else {
          invokeTool(tool, element.id, palettePosition);
          const sourceElementAction: SourceElementAction = { kind: SOURCE_ELEMENT_ACTION, sourceElement: null };
          diagramServer.actionDispatcher.dispatch(sourceElementAction);
        }
      }
      const setDefaultToolEvent: SetDefaultToolEvent = { type: 'SET_DEFAULT_TOOL', defaultTool: tool };
      dispatch(setDefaultToolEvent);
    };
    const invokeConnectorToolFromContextualPalette = (toolSections: GQLToolSection[]) => {
      resetTools();
      const tools = [];
      toolSections.forEach((toolSection) => {
        const filteredTools = toolSection.tools
          .filter((tool) => tool.__typename === 'SingleClickOnTwoDiagramElementsTool')
          .map((tool) => tool as GQLSingleClickOnTwoDiagramElementsTool)
          .filter((tool) =>
            tool.candidates.some((candidate) => candidate.sources.some((source) => source.id === element.descriptionId))
          );
        tools.push(...filteredTools);
      });
      const setActiveConnectorToolsEvent: SetActiveConnectorToolsEvent = {
        type: 'SET_ACTIVE_CONNECTOR_TOOLS',
        tools,
      };
      dispatch(setActiveConnectorToolsEvent);
      edgeCreationFeedback.init(x, y);

      const action: SourceElementAction = {
        kind: 'sourceElement',
        sourceElement: { element, position: { ...palettePosition } },
      };
      diagramServer.actionDispatcher.dispatch(action);
    };
    contextualPaletteContent = (
      <div className={classes.contextualPalette} style={style}>
        <ContextualPalette
          editingContextId={editingContextId}
          representationId={representationId}
          diagramElement={element}
          invokeTool={invokeToolFromContextualPalette}
          invokeConnectorTool={invokeConnectorToolFromContextualPalette}
          invokeLabelEdit={invokeLabelEditFromContextualPalette}
          invokeDelete={invokeDeleteFromContextualPalette}
          invokeClose={resetTools}
        />
      </div>
    );
  }
  let contextualMenuContent;
  if (!readOnly && contextualMenu) {
    const { sourceElement, targetElement, canvasBounds, tools, startPosition, endPosition } = contextualMenu;
    const style = {
      left: canvasBounds.x + 'px',
      top: canvasBounds.y + 'px',
    };
    if (tools && tools.length > 1 && !!startPosition && !!endPosition) {
      const invokeToolFromContextualMenu = (tool) => {
        invokeTool(tool, sourceElement.id, targetElement.id, startPosition, endPosition);
      };
      contextualMenuContent = (
        <div className={classes.contextualMenu} style={style}>
          <ContextualMenu
            tools={tools}
            invokeTool={invokeToolFromContextualMenu}
            invokeClose={resetTools}
          ></ContextualMenu>
        </div>
      );
    }
  }

  let content = (
    <div id="diagram-container" className={classes.diagramContainer}>
      <DropArea
        editingContextId={editingContextId}
        representationId={representationId}
        invokeHover={invokeHover}
        convertInSprottyCoordinate={convertInSprottyCoordinate}
      >
        <div id="diagram-wrapper" className={classes.diagramWrapper}>
          <div ref={diagramDomElement} id="diagram" className={classes.diagram} />
          {contextualPaletteContent}
          {contextualMenuContent}
        </div>
      </DropArea>
    </div>
  );

  if (diagramWebSocketContainer === 'complete') {
    content = (
      <div id="diagram-container" className={classes.diagramContainer + ' ' + classes.noDiagram}>
        <Typography variant="h5" align="center" data-testid="diagram-complete-message">
          The diagram does not exist
        </Typography>
      </div>
    );
  }

  let selectModelElementDialog;
  if (selectionDialog === 'visible') {
    selectModelElementDialog = (
      <SelectionDialogWebSocketContainer
        editingContextId={editingContextId}
        selectionRepresentationId={(activeTool as GQLSingleClickOnDiagramElementTool).selectionDescriptionId}
        targetObjectId={contextualPalette?.element.targetObjectId}
        onClose={() => {
          dispatch({ type: 'CLOSE_SELECTION_DIALOG' } as CloseSelectionDialogEvent);
        }}
        onFinish={(selectedObjectId) => {
          dispatch({
            type: 'HANDLE_SELECTED_OBJECT_IN_SELECTION_DIALOG',
            selectedObjectId,
          } as HandleSelectedObjectInSelectionDialogEvent);
        }}
      />
    );
  }
  return (
    <div className={classes.container}>
      <Toolbar
        onZoomIn={onZoomIn}
        onZoomOut={onZoomOut}
        onFitToScreen={onFitToScreen}
        onArrangeAll={onArrangeAll}
        setZoomLevel={setZoomLevel}
        autoLayout={diagram?.autoLayout}
        zoomLevel={zoomLevel}
        subscribers={subscribers}
      />
      {content}
      {selectModelElementDialog}
      <Snackbar
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'right',
        }}
        open={toast === 'visible'}
        autoHideDuration={3000}
        onClose={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
        message={message}
        action={
          <IconButton
            size="small"
            aria-label="close"
            color="inherit"
            onClick={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
          >
            <CloseIcon fontSize="small" />
          </IconButton>
        }
        data-testid="error"
      />
    </div>
  );
};
