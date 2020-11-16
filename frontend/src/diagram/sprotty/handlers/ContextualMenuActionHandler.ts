/***********************************************************************************************
 * Copyright (c) 2019, 2020 Obeo. All Rights Reserved.
 * This software and the attached documentation are the exclusive ownership
 * of its authors and was conceded to the profit of Obeo SARL.
 * This software and the attached documentation are protected under the rights
 * of intellectual ownership, including the section "Titre II  Droits des auteurs (Articles L121-1 L123-12)"
 * By installing this software, you acknowledge being aware of this rights and
 * accept them, and as a consequence you must:
 * - be in possession of a valid license of use conceded by Obeo only.
 * - agree that you have read, understood, and will comply with the license terms and conditions.
 * - agree not to do anything that could conflict with intellectual ownership owned by Obeo or its beneficiaries
 * or the authors of this software
 *
 * Should you not agree with these terms, you must stop to use this software and give it back to its legitimate owner.
 ***********************************************************************************************/
import { inject, injectable } from 'inversify';
import {
  GetViewportAction,
  SNode,
  getAbsoluteBounds,
  TYPES,
  IActionDispatcher,
  MousePositionTracker,
  IActionHandler,
  IActionHandlerInitializer,
  SModelElement,
} from 'sprotty';
import { SET_CONTEXTUAL_MENU_ACTION } from 'diagram/sprotty/Actions';

import { SIRIUS_TYPES } from 'diagram/sprotty/Types';
import { ISetState } from 'diagram/sprotty/ISetState';
import { IState } from 'diagram/sprotty/IState';

/** Where to open the contextual menu relative to the mouse position */
const popupOffset = {
  x: 24,
  y: -1,
};
@injectable()
export class ContextualMenuActionHandler implements IActionHandler, IActionHandlerInitializer {
  @inject(TYPES.IActionDispatcher) actionDispatcher: IActionDispatcher;

  @inject(SIRIUS_TYPES.SET_STATE) setState: ISetState;

  @inject(SIRIUS_TYPES.STATE) state: IState;

  @inject(MousePositionTracker) mousePositionTracker: MousePositionTracker;

  initialize(registry) {
    registry.register(SET_CONTEXTUAL_MENU_ACTION, this);
  }
  handle(action) {
    this.handleSetContextualMenuAction(action);
  }
  async handleSetContextualMenuAction(action) {
    const { element } = action;

    if (element instanceof SModelElement) {
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
        const { sourceElement } = this.state;
        const contextualMenu = {
          canvasBounds: bounds,
          origin,
          sourceElement,
          targetElement: element,
        };
        this.setState.setContextualMenu(contextualMenu);
      } else {
        const contextualMenu = undefined;
        this.setState.setContextualMenu(contextualMenu);
      }
    }
  }
}
