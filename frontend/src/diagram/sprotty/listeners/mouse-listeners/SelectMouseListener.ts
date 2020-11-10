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
import { SPROTTY_SELECT_ACTION } from 'diagram/sprotty/Actions';
export class SelectMouseListener extends MouseListener {
  mouseDown(element, event) {
    if (event.button === 0) {
      const elementWithTarget = this.findElementWithTarget(element);
      return [{ kind: SPROTTY_SELECT_ACTION, element: elementWithTarget }];
    }
    return [];
  }
}
