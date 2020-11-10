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
  SelectAction,
  SModelRoot,
  CenterAction,
  GetSelectionAction,
  SGraph,
  ILogger,
} from 'sprotty';
import { SIRIUS_SELECT_ACTION, SPROTTY_SELECT_ACTION } from 'diagram/sprotty/Actions';
import { SIRIUS_TYPES } from 'diagram/sprotty/Types';
import { IMutations } from 'diagram/sprotty/IMutations';
import { IState } from 'diagram/sprotty/IState';

@injectable()
export class SelectActionHandler {
  @inject(TYPES.ILogger) logger: ILogger;
  @inject(TYPES.IActionDispatcher) actionDispatcher: IActionDispatcher;

  @inject(SIRIUS_TYPES.MUTATIONS) mutationAPI: IMutations;

  @inject(SIRIUS_TYPES.STATE) state: IState;

  initialize(registry) {
    registry.register(SIRIUS_SELECT_ACTION, this);
    registry.register(SPROTTY_SELECT_ACTION, this);
  }
  handle(action) {
    switch (action.kind) {
      case SIRIUS_SELECT_ACTION:
        this.handleSiriusSelectAction(action);
        break;
      case SPROTTY_SELECT_ACTION:
        this.handleSprottySelectAction(action);
        break;
      default:
        this.logger.error(this, 'Invalid action', action);
        break;
    }
  }
  async handleSiriusSelectAction(action) {
    const { currentRoot } = this.state;
    if (currentRoot instanceof SModelRoot) {
      const { selection } = action;
      const selectedElementsIDs = [];
      const selectionResult = await this.actionDispatcher.request(GetSelectionAction.create());
      const prevSelectedObjectIds = currentRoot.index
        .all()
        .filter((element) => selectionResult.selectedElementsIDs.indexOf(element.id) >= 0)
        .map((element) => element.id);
      const deselectedElementsIDs = [...prevSelectedObjectIds];
      if (selection?.id !== currentRoot.id) {
        const selectionElement = this.findElement(selection.id);
        if (
          !(selectionElement instanceof SGraph) &&
          selectionElement &&
          prevSelectedObjectIds.indexOf(selectionElement.id) < 0
        ) {
          // The React selection and the Sprotty selection does not match. We must update the Sprotty selection
          selectedElementsIDs.push(selectionElement.id);
        }
      }
      const actions = [];
      if (selectedElementsIDs.length > 0 || deselectedElementsIDs.length > 0) {
        actions.push(new SelectAction(selectedElementsIDs, deselectedElementsIDs));
      }
      if (selectedElementsIDs.length > 0) {
        actions.push(new CenterAction(selectedElementsIDs));
      }
      this.actionDispatcher.dispatchAll(actions);
    }
  }

  handleSprottySelectAction(action) {
    const { element } = action;
    this.mutationAPI.onSelectElement(element);
  }

  findElement(id) {
    const { currentRoot } = this.state;
    if (currentRoot instanceof SModelRoot) {
      const [element] = currentRoot.index
        .all()
        .filter((element) => id === element['targetObjectId'] || id === element.id);
      return element;
    } else {
      return null;
    }
  }
}
