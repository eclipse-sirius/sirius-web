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
import { inject, injectable } from 'inversify';
import {
  TYPES,
  IActionDispatcher,
  GetViewportAction,
  SNode,
  SGraph,
  SEdge,
  getAbsoluteBounds,
  MousePositionTracker,
  IActionHandler,
  IActionHandlerInitializer,
  ActionHandlerRegistry,
  Action,
} from 'sprotty';
import { SET_CONTEXTUAL_PALETTE_ACTION } from 'diagram/sprotty/Actions';
import { SIRIUS_TYPES } from 'diagram/sprotty/Types';
import { ISetState } from 'diagram/sprotty/ISetState';

/** Where to open the contextual palette relative to the mouse position */
const popupOffset = {
  x: 24,
  y: -1,
};

@injectable()
export class ContextualPaletteActionHandler implements IActionHandler, IActionHandlerInitializer {
  @inject(TYPES.IActionDispatcher) actionDispatcher: IActionDispatcher;

  @inject(SIRIUS_TYPES.SET_STATE) setState: ISetState;

  @inject(MousePositionTracker) mousePositionTracker: MousePositionTracker;

  initialize(registry: ActionHandlerRegistry): void {
    registry.register(SET_CONTEXTUAL_PALETTE_ACTION, this);
  }
  handle(action: Action): void {
    this.handleSetContextualPaletteAction(action);
  }
  async handleSetContextualPaletteAction(action) {
    const { element } = action;
    if (element && element.kind !== 'Diagram' && element.parent) {
      const root = await this.actionDispatcher.request(GetViewportAction.create());
      const { viewport, canvasBounds } = root;
      const { scroll, zoom } = viewport;
      const lastPositionOnDiagram = this.mousePositionTracker.lastPositionOnDiagram;
      if (lastPositionOnDiagram) {
        const bounds = {
          x: (lastPositionOnDiagram.x - scroll.x) * zoom + canvasBounds.x + popupOffset.x,
          y: (lastPositionOnDiagram.y - scroll.y) * zoom + canvasBounds.y + popupOffset.y,
          width: -1,
          height: -1,
        };

        const absoluteBounds = getAbsoluteBounds(element);
        let origin = { x: 0, y: 0 };
        if (element instanceof SNode) {
          origin = {
            x: absoluteBounds.x + (element.size.width / 2) * zoom,
            y: absoluteBounds.y + (element.size.height / 2) * zoom,
          };
        }

        const contextualPalette = {
          canvasBounds: bounds,
          origin,
          element: element,
          renameable: !(element instanceof SGraph) && !(element instanceof SEdge),
          deletable: !(element instanceof SGraph),
        };
        this.setState.setContextualPalette(contextualPalette);
      }
    } else {
      const contextualPalette = undefined;
      this.setState.setContextualPalette(contextualPalette);
    }
  }
}
