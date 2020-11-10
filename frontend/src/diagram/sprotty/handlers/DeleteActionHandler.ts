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
  IActionDispatcher,
  IActionHandler,
  IActionHandlerInitializer,
  ActionHandlerRegistry,
  Action,
  SModelRoot,
  DeleteElementAction,
} from 'sprotty';
import { SET_CONTEXTUAL_PALETTE_ACTION } from 'diagram/sprotty/Actions';
import { SIRIUS_TYPES } from 'diagram/sprotty/Types';
import { IMutations } from 'diagram/sprotty/IMutations';
import { IState } from 'diagram/sprotty/IState';

@injectable()
export class DeleteActionHandler implements IActionHandler, IActionHandlerInitializer {
  @inject(TYPES.IActionDispatcher) actionDispatcher: IActionDispatcher;

  @inject(SIRIUS_TYPES.MUTATIONS) mutations: IMutations;

  @inject(SIRIUS_TYPES.STATE) state: IState;

  initialize(registry: ActionHandlerRegistry): void {
    registry.register(DeleteElementAction.KIND, this);
  }
  handle(action: Action): void {
    if (action instanceof DeleteElementAction) {
      this.deleteSelectedElements(action);
    }
  }

  async deleteSelectedElements(action: DeleteElementAction) {
    const { currentRoot } = this.state;
    if (currentRoot instanceof SModelRoot) {
      action.elementIds;
      const selectedObjects = currentRoot.index.all().filter((element) => action.elementIds.indexOf(element.id) >= 0);

      this.mutations.deleteElements([...selectedObjects]);
      this.actionDispatcher.dispatch({ kind: SET_CONTEXTUAL_PALETTE_ACTION });
    }
  }
}
