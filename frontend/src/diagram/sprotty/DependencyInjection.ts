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
import { Container, ContainerModule } from 'inversify';
import {
  boundsModule,
  configureModelElement,
  configureView,
  configureViewerOptions,
  ConsoleLogger,
  defaultModule,
  edgeLayoutModule,
  editLabelFeature,
  exportModule,
  fadeModule,
  graphModule,
  HtmlRootView,
  labelEditModule,
  labelEditUiModule,
  LogLevel,
  modelSourceModule,
  moveModule,
  overrideViewerOptions,
  PreRenderedView,
  routingModule,
  SCompartmentView,
  selectModule,
  SLabel,
  SNode,
  SRoutingHandleView,
  TYPES,
  updateModule,
  viewportModule,
  withEditLabelFeature,
  zorderModule,
  SetPopupModelAction,
  RequestPopupModelAction,
  overrideCommandStackOptions,
  configureCommand,
  HoverFeedbackCommand,
  SwitchEditModeCommand,
  ReconnectCommand,
  SDanglingAnchor,
  EmptyGroupView,
} from 'sprotty';

import { GraphFactory } from 'diagram/sprotty/GraphFactory';
import { DiagramView } from 'diagram/sprotty/views/DiagramView';
import { EdgeView } from 'diagram/sprotty/views/EdgeView';
import { ImageView } from 'diagram/sprotty/views/ImageView';
import { LabelView } from 'diagram/sprotty/views/LabelView';
import { RectangleView } from 'diagram/sprotty/views/RectangleView';
import { DiagramModelSource } from 'diagram/sprotty/DiagramModelSource';
import { EditActionHandler } from 'diagram/sprotty/handlers/EditActionHandler';
import { DeleteActionHandler } from 'diagram/sprotty/handlers/DeleteActionHandler';
import { ModelActionHandler } from 'diagram/sprotty/handlers/ModelActionHandler';
import { SelectActionHandler } from 'diagram/sprotty/handlers/SelectActionHandler';
import { ZoomActionHandler } from 'diagram/sprotty/handlers/ZoomActionHandler';
import { EdgeFeedbackActionHandler } from 'diagram/sprotty/handlers/EdgeFeedbackActionHandler';
import { ContextualPaletteActionHandler } from 'diagram/sprotty/handlers/ContextualPaletteActionHandler';
import { ContextualMenuActionHandler } from 'diagram/sprotty/handlers/ContextualMenuActionHandler';
import { ToolActionHandler } from 'diagram/sprotty/handlers/ToolActionHandler';
import { HoverActionHandler } from 'diagram/sprotty/handlers/HoverActionHandler';

import { ContextualPaletteMouseListener } from 'diagram/sprotty/listeners/mouse-listeners/ContextualPaletteMouseListener';
import { ContextualMenuMouseListener } from 'diagram/sprotty/listeners/mouse-listeners/ContextualMenuMouseListener';
import { CursorMouseListener } from 'diagram/sprotty/listeners/mouse-listeners/CursorMouseListener';
import { EdgeCreateFeedbackMouseListener } from 'diagram/sprotty/listeners/mouse-listeners/EdgeCreateFeedbackMouseListener';
import { HoverMouseListener } from 'diagram/sprotty/listeners/mouse-listeners/HoverMouseListener';
import { ToolMouseListener } from 'diagram/sprotty/listeners/mouse-listeners/ToolMouseListener';

import { ContextualPaletteKeyListener } from 'diagram/sprotty/listeners/key-listeners/ContextualPaletteKeyListener';
import { DeleteKeyListener } from 'diagram/sprotty/listeners/key-listeners/DeleteKeyListener';
import { EditKeyListener } from 'diagram/sprotty/listeners/key-listeners/EditKeyListener';

import { SIRIUS_TYPES } from 'diagram/sprotty/Types';

/**
 * Action handlers
 */
const handlerClasses = [
  DeleteActionHandler,
  EditActionHandler,
  ModelActionHandler,
  SelectActionHandler,
  ZoomActionHandler,
  EdgeFeedbackActionHandler,
  ContextualPaletteActionHandler,
  ContextualMenuActionHandler,
  ToolActionHandler,
  HoverActionHandler,
];

/**
 * Listeners
 */
const mouseListeners = [
  CursorMouseListener,
  EdgeCreateFeedbackMouseListener,
  ToolMouseListener,
  ContextualPaletteMouseListener,
  ContextualMenuMouseListener,
  HoverMouseListener,
];
const keyListeners = [EditKeyListener, DeleteKeyListener, ContextualPaletteKeyListener];

const siriusWebContainerModule = new ContainerModule((bind, unbind, isBound, rebind) => {
  rebind(TYPES.ILogger).to(ConsoleLogger).inSingletonScope();
  rebind(TYPES.LogLevel).toConstantValue(LogLevel.warn);
  rebind(TYPES.IModelFactory).to(GraphFactory).inSingletonScope();
  bind(TYPES.ModelSource).to(DiagramModelSource).inSingletonScope();

  mouseListeners.forEach((listenerClass) => {
    bind(TYPES.MouseListener).to(listenerClass).inSingletonScope();
  });
  keyListeners.forEach((listenerClass) => {
    bind(TYPES.KeyListener).to(listenerClass).inSingletonScope();
  });

  bind(SIRIUS_TYPES.MUTATIONS).toConstantValue({});
  bind(SIRIUS_TYPES.STATE).toConstantValue({});
  bind(SIRIUS_TYPES.SET_STATE).toConstantValue({});

  const context = { bind, unbind, isBound, rebind };

  configureCommand(context, HoverFeedbackCommand);

  configureViewerOptions(context, {
    needsClientLayout: true,
    needsServerLayout: true,
  });

  // @ts-ignore
  configureView({ bind, isBound }, 'graph', DiagramView);
  // @ts-ignore
  configureModelElement(context, 'node:rectangle', SNode, RectangleView, {
    enable: [withEditLabelFeature],
  });
  // @ts-ignore
  configureModelElement(context, 'node:image', SNode, ImageView, {
    enable: [withEditLabelFeature],
  });
  // @ts-ignore
  configureView({ bind, isBound }, 'port:square', RectangleView);
  configureView({ bind, isBound }, 'edge:straight', EdgeView);
  // @ts-ignore
  configureView({ bind, isBound }, 'label:inside-left', LabelView);
  // @ts-ignore
  configureModelElement(context, 'label:inside-center', SLabel, LabelView, {
    enable: [editLabelFeature],
  });
  // @ts-ignore
  configureView({ bind, isBound }, 'label:inside-right', LabelView);
  // @ts-ignore
  configureView({ bind, isBound }, 'label:outside-left', LabelView);
  // @ts-ignore
  configureView({ bind, isBound }, 'label:outside-center', LabelView);
  // @ts-ignore
  configureView({ bind, isBound }, 'label:outside-right', LabelView);
  // @ts-ignore
  configureModelElement(context, 'label:text', SLabel, LabelView, {
    enable: [editLabelFeature],
  });
  configureView({ bind, isBound }, 'comp:main', SCompartmentView);
  configureView({ bind, isBound }, 'html', HtmlRootView);
  // @ts-ignore
  configureView({ bind, isBound }, 'pre-rendered', PreRenderedView);
  configureView({ bind, isBound }, 'routing-point', SRoutingHandleView);
  configureView({ bind, isBound }, 'volatile-routing-point', SRoutingHandleView);

  handlerClasses.forEach((handlerClass) => {
    bind(TYPES.IActionHandlerInitializer).to(handlerClass).inSingletonScope();
  });
});

/**
 * Create the dependency injection container.
 * @param containerId The identifier of the container
 */
export const createDependencyInjectionContainer = (containerId) => {
  const container = new Container();

  const edgeEditModule = new ContainerModule((bind, _unbind, isBound) => {
    const context = { bind, isBound };
    configureCommand(context, SwitchEditModeCommand);
    configureCommand(context, ReconnectCommand);
    configureModelElement(context, 'dangling-anchor', SDanglingAnchor, EmptyGroupView);
  });
  container.load(
    defaultModule,
    boundsModule,
    selectModule,
    moveModule,
    viewportModule,
    fadeModule,
    exportModule,
    graphModule,
    updateModule,
    modelSourceModule,
    routingModule,
    edgeEditModule,
    edgeLayoutModule,
    zorderModule,
    siriusWebContainerModule,
    labelEditModule,
    labelEditUiModule
  );

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
