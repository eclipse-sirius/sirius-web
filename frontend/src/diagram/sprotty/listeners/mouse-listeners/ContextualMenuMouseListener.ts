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
import { injectable } from 'inversify';
import { SModelElement, Action } from 'sprotty';
import { MouseListener } from 'diagram/sprotty/listeners/mouse-listeners/MouseListener';
import { SET_CONTEXTUAL_MENU_ACTION } from 'diagram/sprotty/Actions';

@injectable()
export class ContextualMenuMouseListener extends MouseListener {
  mouseDown(target: SModelElement, event: MouseEvent): (Action | Promise<Action>)[] {
    if (event.button === 0) {
      const elementWithTarget = this.findElementWithTarget(target);
      const { activeTool, sourceElement } = this.state;
      if (activeTool && activeTool.__typename === 'MagicCreateEdgeTool' && sourceElement) {
        return [
          <any>{
            kind: SET_CONTEXTUAL_MENU_ACTION,
            element: elementWithTarget,
          },
        ];
      }
    }
    return [];
  }
}
