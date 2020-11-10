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
import { INVOKE_TOOL_ACTION, ACTIVE_TOOL_ACTION } from 'diagram/sprotty/Actions';
export class ToolMouseListener extends MouseListener {
  mouseDown(element, event) {
    const result = [];
    if (event.button === 0) {
      const elementWithTarget = this.findElementWithTarget(element);
      const { activeTool, sourceElement } = this.state;
      if (activeTool) {
        if (activeTool.__typename === 'CreateEdgeTool') {
          if (sourceElement) {
            // Can trigger the tool.
            result.push({
              kind: INVOKE_TOOL_ACTION,
              element: elementWithTarget,
            });
          }
        } else if (activeTool.__typename === 'CreateNodeTool') {
          result.push({
            kind: INVOKE_TOOL_ACTION,
            element: elementWithTarget,
          });
        }
      }
    } else if (event.button === 2) {
      const { activeTool } = this.state;
      if (activeTool) {
        result.push({
          kind: ACTIVE_TOOL_ACTION,
          activeTool: undefined,
        });
      }
    }
    return result;
  }
}
