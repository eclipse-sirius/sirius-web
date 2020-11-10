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
import { UpdateModelAction } from 'sprotty';
import { MouseListener } from 'diagram/sprotty/listeners/mouse-listeners/MouseListener';
import { canInvokeTool } from 'diagram/toolServices';
export class CursorMouseListener extends MouseListener {
  mouseMove(element, event) {
    const { activeTool, sourceElement } = this.state;
    const root = element.root;
    if (activeTool) {
      const elementWithTarget = this.findElementWithTarget(element);
      const cursorAllowed = canInvokeTool(activeTool, sourceElement, elementWithTarget);
      let expectedCursor;
      if (cursorAllowed) {
        expectedCursor = 'copy';
      } else {
        expectedCursor = 'not-allowed';
      }
      if (root.cursor !== expectedCursor) {
        root.cursor = expectedCursor;
        return [
          {
            kind: UpdateModelAction.KIND,
            input: root,
          },
        ];
      }
    } else {
      if (root.cursor) {
        root.cursor = undefined;
        return [
          {
            kind: UpdateModelAction.KIND,
            input: root,
          },
        ];
      }
    }
    return [];
  }
}
