/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
import { BorderNode, Label, Node } from 'diagram/sprotty/Diagram.types';
import { DiagramServer, HIDE_CONTEXTUAL_TOOLBAR_ACTION, SPROTTY_DELETE_ACTION } from 'diagram/sprotty/DiagramServer';
import { SetActiveConnectorToolsAction, SetActiveToolAction } from 'diagram/sprotty/DiagramServer.types';
import { edgeCreationFeedback } from 'diagram/sprotty/edgeCreationFeedback';
import { EditLabelUIWithInitialContent } from 'diagram/sprotty/EditLabelUIWithInitialContent';
import { GraphFactory } from 'diagram/sprotty/GraphFactory';
import siriusDragAndDropModule from 'diagram/sprotty/siriusDragAndDropModule';
import { DiagramView } from 'diagram/sprotty/views/DiagramView';
import { EdgeView } from 'diagram/sprotty/views/EdgeView';
import { ImageView } from 'diagram/sprotty/views/ImageView';
import { LabelView } from 'diagram/sprotty/views/LabelView';
import { ListItemView } from 'diagram/sprotty/views/ListItemView';
import { ListView } from 'diagram/sprotty/views/ListView';
import { RectangleView } from 'diagram/sprotty/views/RectangleView';
import { Container, ContainerModule, decorate, inject } from 'inversify';
import {
  boundsModule,
  configureActionHandler,
  configureModelElement,
  configureView,
  configureViewerOptions,
  ConsoleLogger,
  defaultModule,
  edgeEditModule,
  edgeLayoutModule,
  EditLabelAction,
  EditLabelActionHandler,
  exportModule,
  fadeModule,
  graphModule,
  hoverModule,
  HtmlRootView,
  KeyListener,
  labelEditModule,
  LogLevel,
  modelSourceModule,
  MouseListener,
  overrideCommandStackOptions,
  overrideViewerOptions,
  PreRenderedView,
  SCompartmentView,
  SEdge,
  selectModule,
  SGraph,
  SLabel,
  SModelElement,
  SRoutingHandleView,
  TYPES,
  updateModule,
  viewportModule,
  ZoomMouseListener,
  zorderModule,
} from 'sprotty';
import { Action, Point, RequestPopupModelAction, SetPopupModelAction, UpdateModelAction } from 'sprotty-protocol';
import { siriusRoutingModule } from './routing/siriusRoutingModule';

const labelEditUiModule = new ContainerModule((bind, _unbind, isBound) => {
  const context = { bind, isBound };
  configureActionHandler(context, EditLabelAction.KIND, EditLabelActionHandler);
  bind(EditLabelUIWithInitialContent).toSelf().inSingletonScope();
  bind(TYPES.IUIExtension).toService(EditLabelUIWithInitialContent);
});

const siriusWebContainerModule = new ContainerModule((bind, unbind, isBound, rebind) => {
  rebind(TYPES.ILogger).to(ConsoleLogger).inSingletonScope();
  rebind(TYPES.LogLevel).toConstantValue(LogLevel.warn);
  rebind(TYPES.IModelFactory).to(GraphFactory).inSingletonScope();
  bind(TYPES.ModelSource).to(DiagramServer).inSingletonScope();

  const context = { bind, unbind, isBound, rebind };
  configureViewerOptions(context, {
    needsClientLayout: true,
    needsServerLayout: true,
  });

  // @ts-ignore
  configureView({ bind, isBound }, 'graph', DiagramView);
  // @ts-ignore
  configureModelElement(context, 'node:rectangle', Node, RectangleView);
  // @ts-ignore
  configureModelElement(context, 'node:image', Node, ImageView);
  // @ts-ignore
  configureModelElement(context, 'node:list', Node, ListView);
  // @ts-ignore
  configureModelElement(context, 'node:list:item', Node, ListItemView);
  // @ts-ignore
  configureModelElement(context, 'port:rectangle', BorderNode, RectangleView);
  // @ts-ignore
  configureModelElement(context, 'port:image', BorderNode, ImageView);
  configureView({ bind, isBound }, 'edge:straight', EdgeView);
  // @ts-ignore
  configureModelElement(context, 'label:inside-center', Label, LabelView);
  // @ts-ignore
  configureModelElement(context, 'label:outside-center', Label, LabelView);
  // @ts-ignore
  configureModelElement(context, 'label:outside', Label, LabelView);
  // @ts-ignore
  configureModelElement(context, 'label:edge-begin', Label, LabelView);
  // @ts-ignore
  configureModelElement(context, 'label:edge-center', Label, LabelView);
  // @ts-ignore
  configureModelElement(context, 'label:edge-end', Label, LabelView);
  // @ts-ignore
  configureView({ bind, isBound }, 'comp:main', SCompartmentView);
  configureView({ bind, isBound }, 'html', HtmlRootView);
  // @ts-ignore
  configureView({ bind, isBound }, 'pre-rendered', PreRenderedView);
  configureView({ bind, isBound }, 'routing-point', SRoutingHandleView);
  configureView({ bind, isBound }, 'volatile-routing-point', SRoutingHandleView);
});

/**
 * Create the dependency injection container.
 * @param containerId The identifier of the container
 * @param onSelectElement The selection call back
 */
export const createDependencyInjectionContainer = (containerId: string, getCursorOn) => {
  const container = new Container();
  container.load(
    defaultModule,
    boundsModule,
    selectModule,
    siriusDragAndDropModule,
    viewportModule,
    fadeModule,
    exportModule,
    hoverModule,
    graphModule,
    updateModule,
    modelSourceModule,
    siriusRoutingModule,
    edgeEditModule,
    edgeLayoutModule,
    zorderModule,
    siriusWebContainerModule,
    labelEditModule,
    labelEditUiModule
  );

  const findElementWithTarget = (element) => {
    if (element.targetObjectId) {
      return element;
    } else if (element.parent) {
      return findElementWithTarget(element.parent);
    }
    // Otherwise, use the diagram as element with target.
    return element.root;
  };

  class DiagramMouseListener extends MouseListener {
    diagramServer: DiagramServer;
    previousCoordinates: Point | null;

    constructor(diagramServer) {
      super();
      this.diagramServer = diagramServer;
    }

    mouseDown(element: SModelElement, event: MouseEvent): (Action | Promise<Action>)[] {
      this.previousCoordinates = {
        x: event.clientX,
        y: event.clientY,
      };
      return super.mouseDown(element, event);
    }

    mouseUp(element: SModelElement, event: MouseEvent): (Action | Promise<Action>)[] {
      if (event.button === 0) {
        if (this.previousCoordinates?.x === event.clientX && this.previousCoordinates?.y === event.clientY) {
          const elementWithTarget = findModelElementWithSemanticTarget(element);
          this.diagramServer.onSelectElement(
            elementWithTarget,
            this.diagramServer,
            {
              x: event.offsetX,
              y: event.offsetY,
            },
            event
          );
        }
      } else if (event.button === 2) {
        edgeCreationFeedback.reset();
        this.diagramServer.setActiveTool(null);

        const setActiveToolAction: SetActiveToolAction = { kind: 'activeTool', tool: null };
        const setActiveConnectorToolsAction: SetActiveConnectorToolsAction = {
          kind: 'activeConnectorTools',
          tools: [],
        };
        return [setActiveToolAction, setActiveConnectorToolsAction];
      }
      return [];
    }

    mouseMove(_: SModelElement, event: MouseEvent): (Action | Promise<Action>)[] {
      edgeCreationFeedback.update(event.offsetX, event.offsetY);
      if (event.buttons !== 0) {
        // Hide the palette during scrolling/panning
        return [{ kind: HIDE_CONTEXTUAL_TOOLBAR_ACTION }];
      } else {
        return [];
      }
    }
  }

  const findModelElementWithSemanticTarget = (element: SModelElement): SGraph | Node | BorderNode | SEdge | null => {
    let graphicalElement: SGraph | Node | BorderNode | SEdge | null = null;
    if (
      element instanceof SGraph ||
      element instanceof Node ||
      element instanceof BorderNode ||
      element instanceof SEdge
    ) {
      graphicalElement = element;
    } else if (element instanceof SLabel) {
      graphicalElement = findModelElementWithSemanticTarget(element.parent);
    } else if (element.root instanceof SGraph) {
      graphicalElement = element.root;
    }
    return graphicalElement;
  };

  decorate(inject(TYPES.ModelSource) as ParameterDecorator, DiagramMouseListener, 0);
  container.bind(TYPES.MouseListener).to(DiagramMouseListener).inSingletonScope();

  class DiagramZoomMouseListener extends ZoomMouseListener {
    wheel(target, event) {
      // Hide the palette during zoom
      return [{ kind: HIDE_CONTEXTUAL_TOOLBAR_ACTION }];
    }
  }
  container.bind(TYPES.MouseListener).to(DiagramZoomMouseListener).inSingletonScope();

  class CursorMouseListener extends MouseListener {
    diagramServer: any;
    constructor(diagramServer) {
      super();
      this.diagramServer = diagramServer;
    }

    mouseMove(element, event) {
      const root = element.root;
      const elementWithTarget = findElementWithTarget(element);
      const expectedCursor = getCursorOn(elementWithTarget, this.diagramServer);
      if (root.cursor !== expectedCursor) {
        root.cursor = expectedCursor;
        return [
          {
            kind: UpdateModelAction.KIND,
            input: root,
          },
        ];
      }

      return [];
    }
  }
  decorate(inject(TYPES.ModelSource) as ParameterDecorator, CursorMouseListener, 0);

  container.bind(TYPES.MouseListener).to(CursorMouseListener).inSingletonScope();

  // The list of characters that will enable the direct edit mechanism.
  const directEditActivationValidCharacters = /[\w&é§èàùçÔØÁÛÊË"«»’”„´$¥€£\\¿?!=+-,;:%/{}[\]–#@*.]/;

  const keyListener = new KeyListener();
  keyListener.keyDown = (element, event) => {
    if (event.code === 'Delete') {
      return [{ kind: SPROTTY_DELETE_ACTION, element }];
    } else if (event.code === 'F2') {
      return [{ kind: EditLabelAction.KIND, element }];
    } else if (event.code === 'Escape') {
      return [{ kind: HIDE_CONTEXTUAL_TOOLBAR_ACTION }];
    } else {
      /*If a modifier key is hit alone, do nothing*/
      if ((event.altKey || event.shiftKey) && event.getModifierState(event.key)) {
        return [];
      }
      const validFirstInputChar =
        !event.metaKey &&
        !event.ctrlKey &&
        event.key.length === 1 &&
        directEditActivationValidCharacters.test(event.key);
      if (validFirstInputChar) {
        return [{ kind: EditLabelAction.KIND, element, initialText: event.key, preSelect: false }];
      }
    }
    return [];
  };
  container.bind(TYPES.KeyListener).toConstantValue(keyListener);

  overrideViewerOptions(container, {
    baseDiv: containerId,
    hiddenDiv: containerId + '-hidden',
  });
  overrideCommandStackOptions(container, {
    defaultDuration: 500,
  });
  /**
   * Workaround to disable Sprotty standard popup feature.
   * We handle the contextual toolbar on selection (not on hover).
   */
  const actionDispatcher = container.get(TYPES.IActionDispatcher) as any;
  const dispatchFn = actionDispatcher.dispatch;
  // Override the dispatch function to ignore Sprotty SetPopupModelAction & RequestPopupModelAction.
  actionDispatcher.dispatch = (action) => {
    if (
      !action.fromSirius &&
      (action.kind === SetPopupModelAction.KIND || action.kind === RequestPopupModelAction.KIND)
    ) {
      return undefined;
    }
    return dispatchFn.call(actionDispatcher, action);
  };
  return container;
};
