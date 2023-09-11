/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo and others.
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
import { Theme } from '@material-ui/core/styles';
import {
  ActionHandlerRegistry,
  ApplyLabelEditAction,
  BringToFrontAction,
  EditLabelAction,
  GetSelectionAction,
  GetViewportAction,
  getWindowScroll,
  ILogger,
  IModelFactory,
  InitializeCanvasBoundsAction,
  ModelSource,
  MousePositionTracker,
  MoveAction,
  MoveCommand,
  ReconnectAction,
  SEdge,
  SelectionResult,
  SGraph,
  SModelElement,
  SNode,
  SPort,
  SwitchEditModeAction,
  ViewportResult,
} from 'sprotty';
import {
  Action,
  CenterAction,
  FitToScreenAction,
  Point,
  SelectAction,
  SetViewportAction,
  UpdateModelAction,
} from 'sprotty-protocol';
import {
  Bounds,
  CursorValue,
  GQLDeletionPolicy,
  GQLReconnectKind,
  Menu,
  Palette,
  SingleClickOnTwoDiagramElementsTool,
  Tool,
} from '../representation/DiagramRepresentation.types';
import { IsSiriusModelElementAction, IsSiriusModelElementResult } from './common/isSiriusModelElementRequest';
import { convertDiagram } from './convertDiagram';
import { BorderNode, Diagram, Edge, Node } from './Diagram.types';
import {
  SetActiveConnectorToolsAction,
  ShowContextualMenuAction,
  ShowContextualToolbarAction,
  SiriusSelectAction,
  SiriusUpdateModelAction,
  SourceElement,
  SourceElementAction,
  SprottySelectAction,
} from './DiagramServer.types';
import { ResizeAction, SiriusResizeCommand } from './dragAndDrop/siriusResize';
import { SiriusReconnectAction } from './edgeEdition/model';

/** Action to delete a sprotty element */
export const SPROTTY_DELETE_ACTION = 'sprottyDeleteElement';
/** Action to select a sprotty element */
export const SPROTTY_SELECT_ACTION = 'sprottySelectElement';
/** Action to select an Sirius element */
export const SIRIUS_SELECT_ACTION = 'siriusSelectElement';
/** Action to select an Sirius element */
export const SIRIUS_UPDATE_MODEL_ACTION = 'siriusUpdateModel';
/** Action to set a tool active */
export const ACTIVE_TOOL_ACTION = 'activeTool';
/** Action to set active connector tools */
export const ACTIVE_CONNECTOR_TOOLS_ACTION = 'activeConnectorTools';
/** Action to set the source element */
export const SOURCE_ELEMENT_ACTION = 'sourceElement';
/** Action to show a contextual toolbar */
export const SHOW_CONTEXTUAL_TOOLBAR_ACTION = 'showContextualToolbar';
/** Action to hide a contextual toolbar */
export const HIDE_CONTEXTUAL_TOOLBAR_ACTION = 'hideContextualToolbar';
/** Action to show a contextual menu */
export const SHOW_CONTEXTUAL_MENU_ACTION = 'showContextualMenu';
/** Action to hide a contextual menu */
export const HIDE_CONTEXTUAL_MENU_ACTION = 'hideContextualMenu';
/** Action to zoom in */
export const ZOOM_IN_ACTION = 'zoomIn';
/** Action to zoom OUT */
export const ZOOM_OUT_ACTION = 'zoomOut';
/** Action to zoom to a specifi level*/
export const ZOOM_TO_ACTION = 'zoomTo';

/** Where to open the contextual palette relative to the mouse position */
const popupOffset = {
  x: 24,
  y: -1,
};

/** Default zoom in factor used internally by Sprotty */
const ZOOM_IN_FACTOR = Math.exp(0.5);
/** Default zoom out factor used internally by Sprotty */
const ZOOM_OUT_FACTOR = Math.exp(-0.5);

const INITIAL_ROOT = {
  type: 'NONE',
  id: 'ROOT',
};

interface Root {
  type: string;
  id: string;
}

export class DiagramServer extends ModelSource {
  logger: ILogger;
  mousePositionTracker: MousePositionTracker;
  modelFactory: IModelFactory;
  activeTool: Tool;
  activeConnectorTools: SingleClickOnTwoDiagramElementsTool[];
  editLabel: (labelId: string, newText: string) => void;
  moveElement: (diagramElementId: string, newPositionX: number, newPositionY: number) => void;
  resizeElement: (
    diagramElementId: string,
    newPositionX: number,
    newPositionY: number,
    newWidth: number,
    newHeight: number
  ) => void;
  deleteElements: (diagramElements: SModelElement[], deletionPolicy: GQLDeletionPolicy) => void;

  invokeTool;
  setContextualPalette: (contextualPalette: Palette | null) => void;
  setContextualMenu: (contextualMenu: Menu | null) => void;
  setActiveTool: (tool: Tool | null) => void;
  onSelectElement: (
    selectedElement: Diagram | Node | BorderNode | Edge | null,
    diagramServer: DiagramServer,
    position: Point,
    event: MouseEvent
  ) => void;
  updateRoutingPointsListener: (routingPoints: Point[], edgeId: string) => void;

  // Used to store the edge source element.
  diagramSource: SourceElement | null;
  currentRoot: Root = INITIAL_ROOT;
  httpOrigin: string;
  theme: Theme;

  firstOpen: boolean = true;
  getCursorOn: (element: any, diagramServer: DiagramServer) => CursorValue;
  reconnectEdge: (
    edgeId: string,
    newEdgeEndId: string,
    reconnectEdgeKind: GQLReconnectKind,
    newEdgeEndPosition: Point
  ) => void;

  override initialize(registry: ActionHandlerRegistry) {
    super.initialize(registry);
    registry.register(ApplyLabelEditAction.KIND, this);
    registry.register(EditLabelAction.KIND, this);
    registry.register(UpdateModelAction.KIND, this);
    registry.register(MoveCommand.KIND, this);
    registry.register(SiriusResizeCommand.KIND, this);
    registry.register(InitializeCanvasBoundsAction.KIND, this);
    registry.register(ReconnectAction.KIND, this);
    registry.register(SIRIUS_UPDATE_MODEL_ACTION, this);
    registry.register(SIRIUS_SELECT_ACTION, this);
    registry.register(SPROTTY_SELECT_ACTION, this);
    registry.register(SPROTTY_DELETE_ACTION, this);
    registry.register(ACTIVE_TOOL_ACTION, this);
    registry.register(ACTIVE_CONNECTOR_TOOLS_ACTION, this);
    registry.register(SOURCE_ELEMENT_ACTION, this);
    registry.register(SHOW_CONTEXTUAL_TOOLBAR_ACTION, this);
    registry.register(HIDE_CONTEXTUAL_TOOLBAR_ACTION, this);
    registry.register(SHOW_CONTEXTUAL_MENU_ACTION, this);
    registry.register(HIDE_CONTEXTUAL_MENU_ACTION, this);
    registry.register(ZOOM_IN_ACTION, this);
    registry.register(ZOOM_OUT_ACTION, this);
    registry.register(ZOOM_TO_ACTION, this);
  }

  commitModel(newRoot) {
    const previousRoot = this.currentRoot;
    this.currentRoot = newRoot;
    return previousRoot;
  }

  handle(action: Action) {
    switch (action.kind) {
      case UpdateModelAction.KIND:
        this.handleUpdateModelAction(action as UpdateModelAction);
        break;
      case ApplyLabelEditAction.KIND:
        this.handleApplyLabelEditAction(action as ApplyLabelEditAction);
        break;
      case EditLabelAction.KIND:
        this.handleEditLabelAction(action as EditLabelAction);
        break;
      case MoveCommand.KIND:
        this.handleMoveAction(action as MoveAction);
        break;
      case SiriusResizeCommand.KIND:
        this.handleResizeAction(action as ResizeAction);
        break;
      case InitializeCanvasBoundsAction.KIND:
        this.handleInitializeCanvasBoundsAction(action as InitializeCanvasBoundsAction);
        break;
      case ReconnectAction.KIND:
        this.handleReconnectAction(action as SiriusReconnectAction);
        break;
      case SIRIUS_UPDATE_MODEL_ACTION:
        this.handleSiriusUpdateModelAction(action as SiriusUpdateModelAction);
        break;
      case SIRIUS_SELECT_ACTION:
        this.handleSiriusSelectAction(action as SiriusSelectAction);
        break;
      case SPROTTY_SELECT_ACTION:
        this.handleSprottySelectAction(action as SprottySelectAction);
        break;
      case SPROTTY_DELETE_ACTION:
        this.handleSiriusDeleteAction(action);
        break;
      case ACTIVE_TOOL_ACTION:
        this.handleActiveToolAction(action);
        break;
      case ACTIVE_CONNECTOR_TOOLS_ACTION:
        this.handleActiveConnectorToolsAction(action as SetActiveConnectorToolsAction);
        break;
      case SOURCE_ELEMENT_ACTION:
        this.handleSourceElementAction(action as SourceElementAction);
        break;
      case SHOW_CONTEXTUAL_TOOLBAR_ACTION:
        this.handleShowContextualToolbarAction(action as ShowContextualToolbarAction);
        break;
      case HIDE_CONTEXTUAL_TOOLBAR_ACTION:
        this.handleHideContextualToolbarAction(action);
        break;
      case SHOW_CONTEXTUAL_MENU_ACTION:
        this.handleShowContextualMenuAction(action as ShowContextualMenuAction);
        break;
      case HIDE_CONTEXTUAL_MENU_ACTION:
        this.handleHideContextualMenuAction(action);
        break;
      case ZOOM_IN_ACTION:
        this.handleZoomInAction(action);
        break;
      case ZOOM_OUT_ACTION:
        this.handleZoomOutAction(action);
        break;
      case ZOOM_TO_ACTION:
        this.handleZoomToAction(action);
        break;
      default:
        this.logger.error(this, 'Invalid action', action);
        break;
    }
  }

  handleUpdateModelAction(action: UpdateModelAction) {
    const { newRoot } = action;
    if (newRoot) {
      this.currentRoot = newRoot;
    }
  }

  handleApplyLabelEditAction(action: ApplyLabelEditAction) {
    const { labelId, text } = action;
    this.editLabel(labelId, text);
  }

  handleEditLabelAction(_action: EditLabelAction) {
    this.actionDispatcher.dispatch({ kind: HIDE_CONTEXTUAL_TOOLBAR_ACTION });
  }

  handleMoveAction(action: MoveAction) {
    const { finished, moves } = action;
    if (finished && moves.length > 0) {
      const { elementId, toPosition } = moves[0];
      this.actionDispatcher
        .request<IsSiriusModelElementResult>(IsSiriusModelElementAction.create(elementId))
        .then((isSiriusModelElementResult) => {
          if (isSiriusModelElementResult.isSiriusModelElement) {
            this.moveElement(elementId, toPosition?.x, toPosition?.y);
          }
        });
    }
  }

  handleResizeAction(action: ResizeAction) {
    const { finished, resize } = action;
    if (finished && resize) {
      const { elementId, newPosition, newSize } = resize;
      this.resizeElement(elementId, newPosition?.x, newPosition?.y, newSize.width, newSize.height);
    }
  }

  handleInitializeCanvasBoundsAction(_: InitializeCanvasBoundsAction) {
    if (this.firstOpen && this.currentRoot.id !== INITIAL_ROOT.id) {
      this.actionDispatcher.dispatch(FitToScreenAction.create([], { padding: 20, maxZoom: 1 }));
      this.firstOpen = false;
    }
  }

  handleReconnectAction(action: SiriusReconnectAction) {
    if (action.reconnectKind === 'source') {
      return this.reconnectEdge(action.routableId, action.newSourceId, GQLReconnectKind.SOURCE, action.newEndPosition);
    }

    return this.reconnectEdge(action.routableId, action.newTargetId, GQLReconnectKind.TARGET, action.newEndPosition);
  }

  handleSprottySelectAction(action: SprottySelectAction) {
    const { element } = action;
    if (this.activeConnectorTools?.length > 0) {
      const filteredTools = this.activeConnectorTools.filter((tool) =>
        tool.candidates.some(
          (candidate) =>
            candidate.sources.some((source) => source.id === this.diagramSource.element.descriptionId) &&
            candidate.targets.some((target) => target.id === (element as any).descriptionId)
        )
      );
      if (filteredTools.length < 1) {
        const showContextualMenuAction: ShowContextualMenuAction = {
          kind: 'showContextualMenu',
          element: null,
          tools: [],
          startPosition: null,
          endPosition: null,
        };
        this.actionDispatcher.dispatch(showContextualMenuAction);
      } else if (filteredTools.length === 1) {
        this.invokeTool(
          filteredTools[0],
          this.diagramSource.element.id,
          element.id,
          this.diagramSource.position,
          this.mousePositionTracker.lastPositionOnDiagram
        );
      } else {
        const showContextualMenuAction: ShowContextualMenuAction = {
          kind: 'showContextualMenu',
          element,
          tools: filteredTools,
          startPosition: this.diagramSource.position,
          endPosition: this.mousePositionTracker.lastPositionOnDiagram,
        };
        this.actionDispatcher.dispatch(showContextualMenuAction);
      }
    } else if (this.activeTool) {
      if (this.activeTool.__typename === 'SingleClickOnDiagramElementTool') {
        this.invokeTool(this.activeTool, element.id);
      } else if (this.activeTool.__typename === 'SingleClickOnTwoDiagramElementsTool') {
        this.invokeTool(
          this.activeTool,
          this.diagramSource.element.id,
          element.id,
          this.diagramSource.position,
          this.mousePositionTracker.lastPositionOnDiagram
        );
      }
    } else {
      const showContextualToolbarAction: ShowContextualToolbarAction = {
        kind: SHOW_CONTEXTUAL_TOOLBAR_ACTION,
        element,
        position: this.mousePositionTracker.lastPositionOnDiagram,
      };
      this.actionDispatcher.dispatch(showContextualToolbarAction);
    }
  }

  handleSiriusUpdateModelAction(action: SiriusUpdateModelAction) {
    const { diagram, diagramDescription, readOnly } = action;
    if (diagram) {
      const convertedDiagram = convertDiagram(diagram, diagramDescription, this.httpOrigin, readOnly, this.theme);
      const sprottyModel = this.modelFactory.createRoot(convertedDiagram);
      this.actionDispatcher.request<SelectionResult>(GetSelectionAction.create()).then((selectionResult) => {
        (sprottyModel as any).cursor = 'pointer';
        const actionsToDispatch: Action[] = [UpdateModelAction.create(sprottyModel as any)];

        if (selectionResult.selectedElementsIDs.length > 0) {
          // If at least one element is selected dispatch actions that sprotty should have dispatched on a new selection.
          actionsToDispatch.push(
            SelectAction.create({ selectedElementsIDs: selectionResult.selectedElementsIDs }),
            BringToFrontAction.create(selectionResult.selectedElementsIDs)
          );

          // switch to edit mode for each selected edges.
          const selectedEdgesId: string[] = [];
          sprottyModel.children
            .filter((element) => element instanceof SEdge)
            .filter((element) => selectionResult.selectedElementsIDs.indexOf(element.id) >= 0)
            .forEach((selectedEdge) => selectedEdgesId.push(selectedEdge.id));
          if (selectedEdgesId.length > 0) {
            actionsToDispatch.push(SwitchEditModeAction.create({ elementsToActivate: selectedEdgesId }));
          }
        }

        this.actionDispatcher.dispatchAll(actionsToDispatch);
      });
    } else {
      this.actionDispatcher.dispatch(UpdateModelAction.create(INITIAL_ROOT));
    }
  }

  handleSiriusSelectAction(action: SiriusSelectAction) {
    const { selection } = action;
    this.actionDispatcher.request<SelectionResult>(GetSelectionAction.create()).then((selectionResult) => {
      const selectedElementsIDs = [];
      selection.entries
        .filter((entry) => entry.id !== this.currentRoot.id)
        .forEach((entry) => {
          const selectionElement = this.findElement(this.currentRoot, entry.id);
          if (selectionElement) {
            // The React selection and the Sprotty selection does not match. We must update the Sprotty selection
            selectedElementsIDs.push(selectionElement.id);
          }
        });
      const deselectedElementsIDs = [...selectionResult.selectedElementsIDs].filter(
        (id) => !selectedElementsIDs.includes(id)
      );

      const actions: Action[] = [];
      if (selectedElementsIDs.length > 0 || deselectedElementsIDs.length > 0) {
        actions.push(SelectAction.create({ selectedElementsIDs, deselectedElementsIDs }));
      }
      if (selectedElementsIDs.length > 0) {
        actions.push(CenterAction.create(selectedElementsIDs));
        actions.push({ kind: HIDE_CONTEXTUAL_TOOLBAR_ACTION });
      }
      actions.push(
        SwitchEditModeAction.create({
          elementsToActivate: selectedElementsIDs,
          elementsToDeactivate: deselectedElementsIDs,
        })
      );
      this.actionDispatcher.dispatchAll(actions);
    });
  }

  handleSiriusDeleteAction(action) {
    const { element } = action;
    if (element) {
      const selectedItems = element.index.all().filter((e) => e.selected);
      this.deleteElements([...selectedItems], GQLDeletionPolicy.SEMANTIC);
    } else {
      this.logger.log(this, 'Invalid delete action', action);
    }
  }

  handleActiveToolAction(action) {
    const { tool } = action;
    this.activeTool = tool;
  }

  handleActiveConnectorToolsAction(action: SetActiveConnectorToolsAction) {
    const { tools } = action;
    this.activeConnectorTools = tools;
  }

  handleSourceElementAction(action: SourceElementAction) {
    const { sourceElement } = action;
    this.diagramSource = sourceElement;
  }

  handleShowContextualToolbarAction(action: ShowContextualToolbarAction) {
    const { element, position: palettePosition } = action;
    if (
      !!element &&
      ((element as any).kind === 'siriusComponents://representation?type=Diagram' || (element as any).parent)
    ) {
      this.actionDispatcher.request<ViewportResult>(GetViewportAction.create()).then((viewportResult) => {
        const { viewport, canvasBounds } = viewportResult;
        const { scroll, zoom } = viewport;
        if (palettePosition) {
          const bounds: Bounds = {
            x: (palettePosition.x - scroll.x) * zoom + canvasBounds.x + popupOffset.x,
            y: (palettePosition.y - scroll.y) * zoom + canvasBounds.y + popupOffset.y,
            width: -1,
            height: -1,
          };

          let edgeStartPosition = { x: 0, y: 0 };
          if (element instanceof SNode || element instanceof SPort) {
            edgeStartPosition = {
              x: (palettePosition.x - scroll.x) * zoom,
              y: (palettePosition.y - scroll.y) * zoom,
            };
          }
          const contextualPalette: Palette = {
            palettePosition,
            canvasBounds: bounds,
            edgeStartPosition,
            element,
            renameable: !(element instanceof SGraph),
            deletable: !(element instanceof SGraph),
          };
          this.setContextualPalette(contextualPalette);
        }
      });
    } else {
      const contextualPalette: Palette = null;
      this.setContextualPalette(contextualPalette);
    }
  }

  handleHideContextualToolbarAction(_: Action) {
    const contextualPalette = null;
    this.setContextualPalette(contextualPalette);
  }

  /**
   * Convert a given browser clientX/clientY couple in the current sprotty diagram coordinate system.
   */
  async convertInSprottyCoordinate(clientX, clientY) {
    const { viewport, canvasBounds } = await this.actionDispatcher.request<ViewportResult>(GetViewportAction.create());
    const { scroll, zoom } = viewport;
    return {
      x: (clientX - canvasBounds.x) / zoom + scroll.x,
      y: (clientY - canvasBounds.y) / zoom + scroll.y,
    };
  }

  /**
   * Use to display a contextual menu when more than one edge tool can apply
   * @param action
   */
  handleShowContextualMenuAction(action: ShowContextualMenuAction) {
    const { tools, startPosition, endPosition } = action;
    const element: any = action.element;
    if (element && (element.kind === 'siriusComponents://representation?type=Diagram' || element.parent)) {
      this.actionDispatcher.request<ViewportResult>(GetViewportAction.create()).then((viewportResult) => {
        const { viewport, canvasBounds } = viewportResult;
        const { scroll, zoom } = viewport;
        const lastPositionOnDiagram = this.mousePositionTracker.lastPositionOnDiagram;
        if (lastPositionOnDiagram) {
          const bounds = {
            x: (lastPositionOnDiagram.x - scroll.x) * zoom + canvasBounds.x + popupOffset.x,
            y: (lastPositionOnDiagram.y - scroll.y) * zoom + canvasBounds.y + popupOffset.y,
            width: -1,
            height: -1,
          };
          const contextualMenu: Menu = {
            canvasBounds: bounds,
            sourceElement: this.diagramSource.element,
            targetElement: element,
            tools,
            startPosition,
            endPosition,
          };
          this.setContextualMenu(contextualMenu);
        }
      });
    } else {
      const contextualMenu = null;
      this.setContextualMenu(contextualMenu);
    }
  }

  handleHideContextualMenuAction(_: Action) {
    const contextualMenu = null;
    this.setContextualMenu(contextualMenu);
  }

  handleZoomInAction(_: Action) {
    this.doZoom(ZOOM_IN_FACTOR);
  }

  handleZoomOutAction(_: Action) {
    this.doZoom(ZOOM_OUT_FACTOR);
  }

  handleZoomToAction(action) {
    this.doZoomLevel(action.level);
  }

  doZoom(zoomFactor) {
    this.setContextualPalette(null);
    this.actionDispatcher.request<ViewportResult>(GetViewportAction.create()).then((viewportResult) => {
      const { viewport } = viewportResult;
      this.doZoomLevel(viewport.zoom * zoomFactor);
    });
  }

  doZoomLevel(zoomLevel) {
    this.actionDispatcher.request<ViewportResult>(GetViewportAction.create()).then((viewportResult) => {
      const { viewport, canvasBounds } = viewportResult;
      const windowScroll = getWindowScroll();
      const clientX = canvasBounds.x + canvasBounds.width / 2;
      const clientY = canvasBounds.y + canvasBounds.height / 2;

      const viewportOffset = {
        x: clientX + windowScroll.x - canvasBounds.x,
        y: clientY + windowScroll.y - canvasBounds.y,
      };
      const offsetFactor = 1.0 / zoomLevel - 1.0 / viewport.zoom;
      const newViewport = {
        zoom: zoomLevel,
        scroll: {
          x: viewport.scroll.x - offsetFactor * viewportOffset.x,
          y: viewport.scroll.y - offsetFactor * viewportOffset.y,
        },
      };
      this.actionDispatcher.dispatch(SetViewportAction.create(this.currentRoot.id, newViewport, { animate: true }));
    });
  }

  findElementWithTarget(element) {
    if (element?.targetObjectId) {
      return element;
    } else if (element.targetObjectId) {
      return this.findElementWithTarget(element.parent);
    }
    // Otherwise, use the diagram as element with target.
    return this.currentRoot;
  }

  findElement(element, id: string) {
    if (element.id === id || element.targetObjectId === id) {
      return element;
    } else if (element.children) {
      for (const child of element.children) {
        const foundElement = this.findElement(child, id);
        if (foundElement) {
          return foundElement;
        }
      }
    }
    return null;
  }

  setLogger(logger: ILogger) {
    this.logger = logger;
  }
  setMousePositionTracker(mousePositionTracker: MousePositionTracker) {
    this.mousePositionTracker = mousePositionTracker;
  }
  setModelFactory(modelFactory: IModelFactory) {
    this.modelFactory = modelFactory;
  }

  setEditLabelListener(editLabel: (labelId: string, newText: string) => void) {
    this.editLabel = editLabel;
  }

  setMoveElementListener(moveElement: (diagramElementId: string, newPositionX: number, newPositionY: number) => void) {
    this.moveElement = moveElement;
  }
  setResizeElementListener(
    resizeElement: (
      diagramElementId: string,
      newPositionX: number,
      newPositionY: number,
      newWidth: number,
      newHeight: number
    ) => void
  ) {
    this.resizeElement = resizeElement;
  }

  setDeleteElementsListener(
    deleteElements: (diagramElements: SModelElement[], deletionPolicy: GQLDeletionPolicy) => void
  ) {
    this.deleteElements = deleteElements;
  }

  setReconnectEdgeListener(
    reconnectEdge: (
      edgeId: string,
      newEdgeEndId: string,
      reconnectEdgeKind: GQLReconnectKind,
      newEdgeEndPosition: Point
    ) => void
  ) {
    this.reconnectEdge = reconnectEdge;
  }

  setInvokeToolListener(invokeTool) {
    this.invokeTool = invokeTool;
  }

  setContextualPaletteListener(setContextualPalette: (contextualPalette: Palette | null) => void) {
    this.setContextualPalette = setContextualPalette;
  }

  setContextualMenuListener(setContextualMenu: (contextualMenu: Menu | null) => void) {
    this.setContextualMenu = setContextualMenu;
  }

  setHttpOrigin(httpOrigin: string) {
    this.httpOrigin = httpOrigin;
  }

  setTheme(theme: Theme) {
    this.theme = theme;
  }

  setActiveToolListener(setActiveTool) {
    this.setActiveTool = setActiveTool;
  }

  setOnSelectElementListener(
    onSelectElement: (
      selectedElement: Diagram | Node | BorderNode | Edge | null,
      diagramServer: DiagramServer,
      position: Point,
      event: MouseEvent
    ) => void
  ) {
    this.onSelectElement = onSelectElement;
  }

  setUpdateRoutingPointsListener(updateRoutingPointsListener: (routingPoints: Point[], edgeId: string) => void) {
    this.updateRoutingPointsListener = updateRoutingPointsListener;
  }

  setGetCursorOnListener(getCursorOn: (element, diagramServer: DiagramServer) => CursorValue) {
    this.getCursorOn = getCursorOn;
  }
}
