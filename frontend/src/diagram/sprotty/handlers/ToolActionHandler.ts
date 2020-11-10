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
import { TYPES, ILogger, IActionDispatcher } from 'sprotty';
import {
  CREATE_EDGE_FEEDBACK_ACTION,
  INVOKE_TOOL_ACTION,
  INVOKE_CONTEXTUAL_TOOL_ACTION,
  REMOVE_EDGE_FEEDBACK_ACTION,
  ACTIVE_TOOL_ACTION,
} from 'diagram/sprotty/Actions';
import { SIRIUS_TYPES } from 'diagram/sprotty/Types';
import { IState } from 'diagram/sprotty/IState';
import { ISetState } from 'diagram/sprotty/ISetState';
import { IMutations } from 'diagram/sprotty/IMutations';

@injectable()
export class ToolActionHandler {
  @inject(TYPES.ILogger) logger: ILogger;
  @inject(SIRIUS_TYPES.STATE) state: IState;

  @inject(SIRIUS_TYPES.SET_STATE) setState: ISetState;

  @inject(SIRIUS_TYPES.MUTATIONS) mutationAPI: IMutations;

  @inject(TYPES.IActionDispatcher) actionDispatcher: IActionDispatcher;

  initialize(registry) {
    registry.register(INVOKE_TOOL_ACTION, this);
    registry.register(INVOKE_CONTEXTUAL_TOOL_ACTION, this);
    registry.register(ACTIVE_TOOL_ACTION, this);
  }
  handle(action) {
    switch (action.kind) {
      case INVOKE_TOOL_ACTION:
        this.handleInvokeToolAction(action);
        break;
      case INVOKE_CONTEXTUAL_TOOL_ACTION:
        this.handleInvokeContextualToolAction(action);
        break;
      case ACTIVE_TOOL_ACTION:
        this.handleActiveToolAction(action);
        break;
      default:
        this.logger.error(this, 'Invalid action', action);
        break;
    }
  }

  handleInvokeToolAction(action) {
    const { activeTool, sourceElement } = this.state;
    const { element } = action;
    if (activeTool.__typename === 'CreateEdgeTool') {
      this.invokeEdgeToolAction(activeTool, sourceElement, element);
    } else {
      this.invokeNodeToolAction(activeTool, element);
    }
  }

  handleInvokeContextualToolAction(action) {
    const { tool } = action;
    if (tool.__typename === 'CreateEdgeTool' || tool.__typename === 'MagicCreateEdgeTool') {
      const { element, x, y } = action;
      this.triggerEdgeFeedbackAction(tool, element, x, y);
    } else if (tool.__typename === 'CreateNodeTool') {
      const { element } = action;
      this.invokeNodeToolAction(tool, element);
    }
  }

  handleActiveToolAction(action) {
    const { activeTool } = action;
    this.setState.setActiveTool(activeTool);
  }

  triggerEdgeFeedbackAction(tool, sourceElement, x, y) {
    this.setState.setActiveTool(tool);
    this.setState.setSourceElement(sourceElement);
    this.actionDispatcher.dispatch(<any>{
      kind: CREATE_EDGE_FEEDBACK_ACTION,
      x,
      y,
    });
  }

  invokeEdgeToolAction(tool, sourceElement, targetElement) {
    this.mutationAPI.invokeEdgeTool(tool, sourceElement, targetElement);
    this.actionDispatcher.dispatchAll([{ kind: REMOVE_EDGE_FEEDBACK_ACTION }]);
  }

  invokeNodeToolAction(tool, element) {
    this.mutationAPI.invokeNodeTool(tool, element);
  }
}
