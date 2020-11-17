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
  SelectAllAction,
  SModelRoot,
  SNode,
  SEdge,
  CenterAction,
  GetSelectionAction,
  ILogger,
} from 'sprotty';
import { SIRIUS_SELECT_ACTION, SET_CONTEXTUAL_PALETTE_ACTION } from 'diagram/sprotty/Actions';
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
    registry.register(SelectAction.KIND, this);
    registry.register(SelectAllAction.KIND, this);
  }
  handle(action) {
    switch (action.kind) {
      case SelectAction.KIND:
        this.handleSelectAction(action);
        break;
      case SelectAllAction.KIND:
        this.handleSelectAllAction(action);
        break;
      case SIRIUS_SELECT_ACTION:
        this.handleSiriusSelectAction(action);
        break;
      default:
        this.logger.error(this, 'Invalid action', action);
        break;
    }
  }
  handleSelectAllAction(action) {
    const { select } = action;
    const { currentRoot } = this.state;
    if (select && currentRoot instanceof SModelRoot) {
      const elements = currentRoot.index
        .all()
        .filter((element) => element instanceof SNode || element instanceof SEdge);
      this.mutationAPI.onSelectElements([...elements]);
    } else {
      this.mutationAPI.onSelectElements([]);
    }
    this.actionDispatcher.dispatch({ kind: SET_CONTEXTUAL_PALETTE_ACTION });
  }
  async handleSiriusSelectAction(action) {
    const { currentRoot } = this.state;
    if (currentRoot instanceof SModelRoot) {
      const { selections } = action;
      const newTargetIds = selections.map((selection) => selection.id);

      const selectionResult = await this.actionDispatcher.request(GetSelectionAction.create());

      const prevSelectedElements = currentRoot.index
        .all()
        .filter((element) => selectionResult.selectedElementsIDs.indexOf(element.id) >= 0);

      const selectedElementsIDs = Array.from(
        currentRoot.index
          .all()
          .filter((element) => newTargetIds.indexOf(element['targetObjectId']) >= 0)
          .filter((element) => element instanceof SNode || element instanceof SEdge)
          .map((element) => element.id)
      );

      const deselectedElementsIDs = Array.from(
        prevSelectedElements
          .filter((prevSelectedElement) => selectedElementsIDs.indexOf(prevSelectedElement.id) < 0)
          .map((element) => element.id)
      );

      const actions = [];
      if (selectedElementsIDs.length > 0 || deselectedElementsIDs.length > 0) {
        const selectAction = new SelectAction(selectedElementsIDs, deselectedElementsIDs);
        actions.push(selectAction);
      }
      if (selectedElementsIDs.length > 0) {
        actions.push(new CenterAction(selectedElementsIDs));
      }
      this.actionDispatcher.dispatchAll(actions);
    }
  }

  async handleSelectAction(action) {
    const { currentRoot } = this.state;
    if (currentRoot instanceof SModelRoot) {
      const selectionResult = await this.actionDispatcher.request(GetSelectionAction.create());
      const selectedElements = Array.from(
        currentRoot.index.all().filter((element) => selectionResult.selectedElementsIDs.indexOf(element.id) >= 0)
      );
      this.mutationAPI.onSelectElements([...selectedElements]);
    }
  }
}
