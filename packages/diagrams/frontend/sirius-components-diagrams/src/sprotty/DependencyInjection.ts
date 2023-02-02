/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
import { Container, ContainerModule, decorate, inject } from 'inversify';
import {
  boundsModule,
  configureModelElement,
  configureView,
  configureViewerOptions,
  ConsoleLogger,
  defaultModule,
  edgeLayoutModule,
  exportModule,
  fadeModule,
  graphModule,
  hoverModule,
  HtmlRootView,
  isDeletable,
  KeyListener,
  labelEditModule,
  LogLevel,
  modelSourceModule,
  MouseListener,
  overrideCommandStackOptions,
  overrideViewerOptions,
  PreRenderedView,
  SCompartmentView,
  selectModule,
  SLabel,
  SModelElement,
  SRoutingHandle,
  TYPES,
  updateModule,
  viewportModule,
  ZoomMouseListener,
} from 'sprotty';
import { Action, Point, RequestPopupModelAction, SetPopupModelAction } from 'sprotty-protocol';
import { siriusCommonModule } from './common/siriusCommonModule';
import { BorderNode, Diagram, Edge, Label, Node } from './Diagram.types';
import { DiagramServer, HIDE_CONTEXTUAL_TOOLBAR_ACTION, SPROTTY_DELETE_ACTION } from './DiagramServer';
import { SetActiveConnectorToolsAction, SetActiveToolAction } from './DiagramServer.types';
import { siriusLabelEditUiModule } from './directEdit/siriusDirectEditModule';
import { siriusDragAndDropModule } from './dragAndDrop/siriusDragAndDropModule';
import { edgeCreationFeedback } from './edgeCreationFeedback';
import { siriusEdgeEditModule } from './edgeEdition/siriusEdgeEditModule';
import { GraphFactory } from './GraphFactory';
import { siriusRoutingModule } from './routing/siriusRoutingModule';
import { DiagramView } from './views/DiagramView';
import { EdgeView } from './views/EdgeView';
import { IconLabelView } from './views/IconLabelView';
import { ImageView } from './views/ImageView';
import { LabelView } from './views/LabelView';
import { ParametricSVGImageView } from './views/ParametricSVGImageView';
import { RectangleView } from './views/RectangleView';
import { RoutingHandleView } from './views/RoutingHandleView';
import { VolatileRoutingHandleView } from './views/VolatileRoutingHandleView';
import { siriusZOrderModule } from './zorder/siriusZOrderModule';

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
  configureModelElement(context, 'graph', Diagram, DiagramView);
  // @ts-ignore
  configureModelElement(context, 'node:rectangle', Node, RectangleView);
  // @ts-ignore
  configureModelElement(context, 'node:image', Node, ImageView);
  // @ts-ignore
  configureModelElement(context, 'node:parametric-svg', Node, ParametricSVGImageView);
  // @ts-ignore
  configureModelElement(context, 'node:icon-label', Node, IconLabelView);
  // @ts-ignore
  configureModelElement(context, 'port:rectangle', BorderNode, RectangleView);
  // @ts-ignore
  configureModelElement(context, 'port:image', BorderNode, ImageView);
  // @ts-ignore
  configureModelElement(context, 'edge:straight', Edge, EdgeView);
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
  configureModelElement(context, 'routing-point', SRoutingHandle, RoutingHandleView);
  configureModelElement(context, 'volatile-routing-point', SRoutingHandle, VolatileRoutingHandleView);
});

/**
 * Create the dependency injection container.
 * @param containerId The identifier of the container
 * @param onSelectElement The selection call back
 */
export const createDependencyInjectionContainer = (
  containerId: string,
  httpOrigin: string,
  editingContextId: string
) => {
  const container = new Container();
  container.bind('editingContextId').toConstantValue(editingContextId);
  container.bind('httpOrigin').toConstantValue(httpOrigin);
  container.load(
    defaultModule,
    siriusCommonModule,
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
    siriusEdgeEditModule,
    edgeLayoutModule,
    siriusZOrderModule,
    siriusWebContainerModule,
    labelEditModule,
    siriusLabelEditUiModule
  );

  class DiagramMouseListener extends MouseListener {
    diagramServer: DiagramServer;
    previousCoordinates: Point | null;

    constructor(diagramServer) {
      super();
      this.diagramServer = diagramServer;
    }

    override mouseDown(element: SModelElement, event: MouseEvent): (Action | Promise<Action>)[] {
      this.previousCoordinates = {
        x: event.clientX,
        y: event.clientY,
      };
      return super.mouseDown(element, event);
    }

    override mouseUp(element: SModelElement, event: MouseEvent): (Action | Promise<Action>)[] {
      const actions: Action[] = [];

      if (event.button === 0) {
        if (element instanceof SRoutingHandle) {
          actions.push({ kind: HIDE_CONTEXTUAL_TOOLBAR_ACTION });
        } else if (this.previousCoordinates?.x === event.clientX && this.previousCoordinates?.y === event.clientY) {
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

    override mouseMove(_: SModelElement, event: MouseEvent): (Action | Promise<Action>)[] {
      edgeCreationFeedback.update(event.offsetX, event.offsetY);
      if (event.buttons !== 0) {
        // Hide the palette during scrolling/panning
        return [{ kind: HIDE_CONTEXTUAL_TOOLBAR_ACTION }];
      } else {
        return [];
      }
    }
  }

  const findModelElementWithSemanticTarget = (element: SModelElement): Diagram | Node | BorderNode | Edge | null => {
    let graphicalElement: Diagram | Node | BorderNode | Edge | null = null;
    if (
      element instanceof Diagram ||
      element instanceof Node ||
      element instanceof BorderNode ||
      element instanceof Edge
    ) {
      graphicalElement = element;
    } else if (element instanceof SLabel) {
      graphicalElement = findModelElementWithSemanticTarget(element.parent);
    } else if (element.root instanceof Diagram) {
      graphicalElement = element.root;
    }
    return graphicalElement;
  };

  decorate(inject(TYPES.ModelSource) as ParameterDecorator, DiagramMouseListener, 0);
  container.bind(TYPES.MouseListener).to(DiagramMouseListener).inSingletonScope();

  class DiagramZoomMouseListener extends ZoomMouseListener {
    override wheel(_target, _event) {
      // Hide the palette during zoom
      return [{ kind: HIDE_CONTEXTUAL_TOOLBAR_ACTION }];
    }
  }
  container.bind(TYPES.MouseListener).to(DiagramZoomMouseListener).inSingletonScope();

  const keyListener = new KeyListener();
  keyListener.keyDown = (element, event) => {
    if (event.code === 'Delete' && isDeletable(element)) {
      return [{ kind: SPROTTY_DELETE_ACTION, element }];
    } else if (event.code === 'Escape') {
      return [{ kind: HIDE_CONTEXTUAL_TOOLBAR_ACTION }];
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
