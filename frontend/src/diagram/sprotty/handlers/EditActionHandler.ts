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
import {
  TYPES,
  ILogger,
  IActionDispatcher,
  ApplyLabelEditAction,
  EditLabelAction,
  IActionHandler,
  IActionHandlerInitializer,
  ActionHandlerRegistry,
  Action,
} from 'sprotty';
import { SET_CONTEXTUAL_PALETTE_ACTION } from 'diagram/sprotty/Actions';
import { SIRIUS_TYPES } from 'diagram/sprotty/Types';
import { IMutations } from 'diagram/sprotty/IMutations';

@injectable()
export class EditActionHandler implements IActionHandler, IActionHandlerInitializer {
  @inject(TYPES.ILogger) logger: ILogger;

  @inject(TYPES.IActionDispatcher) actionDispatcher: IActionDispatcher;

  @inject(SIRIUS_TYPES.MUTATIONS) mutations: IMutations;

  initialize(registry: ActionHandlerRegistry): void {
    registry.register(ApplyLabelEditAction.KIND, this);
    registry.register(EditLabelAction.KIND, this);
  }
  handle(action: Action): void {
    switch (action.kind) {
      case ApplyLabelEditAction.KIND:
        this.handleApplyLabelEditAction(action);
        break;
      case EditLabelAction.KIND:
        this.handleEditLabelAction(action);
        break;
      default:
        this.logger.error(this, 'Invalid action', action);
        break;
    }
  }

  handleApplyLabelEditAction(action) {
    const { labelId, text } = action;
    this.mutations.editLabel(labelId, text);
  }

  handleEditLabelAction(action) {
    const { element } = action;
    if (element) {
      const selectedItems = element.index.all().filter((e) => e.selected);
      selectedItems.forEach((item) => {
        const label = item.editableLabel;
        if (label) {
          this.actionDispatcher.dispatch(new EditLabelAction(label.id));
        }
      });
    }
    this.actionDispatcher.dispatch({ kind: SET_CONTEXTUAL_PALETTE_ACTION });
  }
}
