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
import { TYPES, ILogger, IActionDispatcher, SetViewportAction, getWindowScroll, GetViewportAction } from 'sprotty';
import { ZOOM_IN_ACTION, ZOOM_OUT_ACTION, ZOOM_TO_ACTION } from 'diagram/sprotty/Actions';
import { SIRIUS_TYPES } from 'diagram/sprotty/Types';
import { IState } from 'diagram/sprotty/IState';

/** Default zoom in factor used internally by Sprotty */
const ZOOM_IN_FACTOR = Math.exp(0.5);
/** Default zoom out factor used internally by Sprotty */
const ZOOM_OUT_FACTOR = Math.exp(-0.5);

@injectable()
export class ZoomActionHandler {
  @inject(TYPES.IActionDispatcher) actionDispatcher: IActionDispatcher;

  @inject(TYPES.ILogger) logger: ILogger;

  @inject(SIRIUS_TYPES.STATE) state: IState;

  initialize(registry) {
    registry.register(ZOOM_IN_ACTION, this);
    registry.register(ZOOM_OUT_ACTION, this);
    registry.register(ZOOM_TO_ACTION, this);
  }
  handle(action) {
    switch (action.kind) {
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

  async handleZoomInAction(action) {
    await this.doZoom(ZOOM_IN_FACTOR);
  }

  async handleZoomOutAction(action) {
    await this.doZoom(ZOOM_OUT_FACTOR);
  }

  async handleZoomToAction(action) {
    await this.doZoomLevel(action.level);
  }

  async doZoom(zoomFactor) {
    const root = await this.actionDispatcher.request(GetViewportAction.create());
    const { viewport } = root;
    await this.doZoomLevel(viewport.zoom * zoomFactor);
  }

  async doZoomLevel(zoomLevel) {
    const root = await this.actionDispatcher.request(GetViewportAction.create());
    const { viewport, canvasBounds } = root;
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
    const { currentRoot } = this.state;
    await this.actionDispatcher.dispatch(new SetViewportAction(currentRoot.id, newViewport, true));
  }
}
