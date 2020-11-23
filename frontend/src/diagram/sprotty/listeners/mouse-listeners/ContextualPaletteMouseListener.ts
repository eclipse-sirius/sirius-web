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
import { injectable } from 'inversify';
import { SModelElement, Action } from 'sprotty';
import { MouseListener } from 'diagram/sprotty/listeners/mouse-listeners/MouseListener';
import { SET_CONTEXTUAL_PALETTE_ACTION } from 'diagram/sprotty/Actions';

@injectable()
export class ContextualPaletteMouseListener extends MouseListener {
  potentialOnClick = false;
  mouseDown(target: SModelElement, event: MouseEvent): (Action | Promise<Action>)[] {
    if (event.button === 0 && !event.ctrlKey && !event.metaKey) {
      this.potentialOnClick = true;
    } else if (event.button === 2 || (event.button === 0 && (event.ctrlKey || event.metaKey))) {
      this.potentialOnClick = false;
      return [
        {
          kind: SET_CONTEXTUAL_PALETTE_ACTION,
        },
      ];
    }
    return [];
  }

  mouseMove(target: SModelElement, event: MouseEvent): (Action | Promise<Action>)[] {
    if (event.button === 0 && this.potentialOnClick) {
      this.potentialOnClick = false;
      return [
        {
          kind: SET_CONTEXTUAL_PALETTE_ACTION,
        },
      ];
    }
    return [];
  }

  mouseUp(target: SModelElement, event: MouseEvent): (Action | Promise<Action>)[] {
    if (event.button === 0 && this.potentialOnClick) {
      this.potentialOnClick = false;
      const elementWithTarget = this.findElementWithTarget(target);
      const { activeTool, dirty } = this.state;
      if (!activeTool && !dirty) {
        return [
          <any>{
            kind: SET_CONTEXTUAL_PALETTE_ACTION,
            element: elementWithTarget,
          },
        ];
      }
    }
    return [];
  }
}
