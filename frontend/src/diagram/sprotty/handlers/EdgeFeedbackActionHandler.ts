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
import { TYPES, ILogger, IActionHandler, IActionHandlerInitializer, ActionHandlerRegistry, Action } from 'sprotty';
import { edgeCreationFeedback } from 'diagram/sprotty/edgeCreationFeedback';
import {
  CREATE_EDGE_FEEDBACK_ACTION,
  UPDATE_EDGE_FEEDBACK_ACTION,
  REMOVE_EDGE_FEEDBACK_ACTION,
} from 'diagram/sprotty/Actions';

@injectable()
export class EdgeFeedbackActionHandler implements IActionHandler, IActionHandlerInitializer {
  @inject(TYPES.ILogger) logger: ILogger;

  initialize(registry: ActionHandlerRegistry): void {
    registry.register(CREATE_EDGE_FEEDBACK_ACTION, this);
    registry.register(UPDATE_EDGE_FEEDBACK_ACTION, this);
    registry.register(REMOVE_EDGE_FEEDBACK_ACTION, this);
  }
  handle(action: Action): void {
    switch (action.kind) {
      case CREATE_EDGE_FEEDBACK_ACTION:
        this.handleCreateEdgeFeedbackAction(action);
        break;
      case UPDATE_EDGE_FEEDBACK_ACTION:
        this.handleUpdateEdgeFeedbackAction(action);
        break;
      case REMOVE_EDGE_FEEDBACK_ACTION:
        this.handleRemoveEdgeFeedbackAction(action);
        break;
      default:
        this.logger.error(this, 'Invalid action', action);
        break;
    }
  }

  handleCreateEdgeFeedbackAction(action) {
    const { x, y } = action;
    edgeCreationFeedback.init(x, y);
  }

  handleUpdateEdgeFeedbackAction(action) {
    const { x, y } = action;
    edgeCreationFeedback.update(x, y);
  }

  handleRemoveEdgeFeedbackAction(action) {
    edgeCreationFeedback.reset();
  }
}
