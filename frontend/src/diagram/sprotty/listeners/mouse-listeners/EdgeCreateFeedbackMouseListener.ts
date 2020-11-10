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
import { UPDATE_EDGE_FEEDBACK_ACTION, REMOVE_EDGE_FEEDBACK_ACTION } from 'diagram/sprotty/Actions';
export class EdgeCreateFeedbackMouseListener extends MouseListener {
  mouseDown(element, event) {
    const { activeTool } = this.state;
    if (activeTool) {
      if (activeTool.__typename === 'CreateEdgeTool') {
        if (event.button === 0 || event.button === 2) {
          return [
            {
              kind: REMOVE_EDGE_FEEDBACK_ACTION,
            },
          ];
        }
      } else if (activeTool.__typename === 'MagicCreateEdgeTool') {
        if (event.button === 2) {
          return [
            {
              kind: REMOVE_EDGE_FEEDBACK_ACTION,
            },
          ];
        }
      }
    }

    return [];
  }

  mouseMove(element, event) {
    const { activeTool } = this.state;
    if (activeTool && (activeTool.__typename === 'CreateEdgeTool' || activeTool.__typename === 'MagicCreateEdgeTool')) {
      return [
        {
          kind: UPDATE_EDGE_FEEDBACK_ACTION,
          x: event.offsetX,
          y: event.offsetY,
        },
      ];
    }
    return [];
  }
}
