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
import { MouseListener } from 'diagram/sprotty/listeners/mouse-listeners/MouseListener';
import { HOVER_ACTION } from 'diagram/sprotty/Actions';
export class HoverMouseListener extends MouseListener {
  mouseOver(element, event) {
    const elementWithTarget = this.findElementWithTarget(element);
    const { id } = elementWithTarget;
    return [{ kind: HOVER_ACTION, id }];
  }

  mouseLeave(element, event) {
    const elementWithTarget = this.findElementWithTarget(element);
    if (elementWithTarget?.kind === 'Diagram') {
      return [{ kind: HOVER_ACTION }];
    }
    return [];
  }
}
