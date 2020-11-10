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
  UpdateModelAction,
  GetSelectionAction,
  ILogger,
  IModelFactory,
  IActionHandler,
  IActionHandlerInitializer,
  ActionHandlerRegistry,
  Action,
  SModelRoot,
} from 'sprotty';
import { convertDiagram } from 'diagram/sprotty/convertDiagram';
import { SIRIUS_UPDATE_MODEL_ACTION } from 'diagram/sprotty/Actions';
import { SIRIUS_TYPES } from 'diagram/sprotty/Types';
import { ISetState } from 'diagram/sprotty/ISetState';

const INITIAL_ROOT = {
  type: 'NONE',
  id: 'ROOT',
};

@injectable()
export class ModelActionHandler implements IActionHandler, IActionHandlerInitializer {
  @inject(TYPES.ILogger) logger: ILogger;
  @inject(TYPES.IActionDispatcher) actionDispatcher: IActionDispatcher;

  @inject(SIRIUS_TYPES.SET_STATE) setState: ISetState;

  @inject(TYPES.IModelFactory) modelFactory: IModelFactory;

  initialize(registry: ActionHandlerRegistry): void {
    registry.register(UpdateModelAction.KIND, this);
    registry.register(SIRIUS_UPDATE_MODEL_ACTION, this);
  }

  handle(action: Action): void {
    switch (action.kind) {
      case UpdateModelAction.KIND:
        this.handleUpdateModelAction(<UpdateModelAction>action);
        break;
      case SIRIUS_UPDATE_MODEL_ACTION:
        this.handleSiriusUpdateModelAction(action);
        break;
      default:
        this.logger.error(this, 'Invalid action', action);
        break;
    }
  }

  handleUpdateModelAction(action: UpdateModelAction) {
    if (action.newRoot) {
      const sprottyModel: SModelRoot = this.modelFactory.createRoot(action.newRoot);
      this.setState.setCurrentRoot(sprottyModel);
    }
  }

  async handleSiriusUpdateModelAction(action) {
    const { diagram } = action;
    if (diagram) {
      const convertedDiagram = convertDiagram(diagram);
      const sprottyModel: SModelRoot = this.modelFactory.createRoot(convertedDiagram);
      const selectionResult = await this.actionDispatcher.request(GetSelectionAction.create());
      sprottyModel.index
        .all()
        .filter((element) => selectionResult.selectedElementsIDs.indexOf(element.id) >= 0)
        .forEach((element) => (element['selected'] = true));

      const sprottySchema = this.modelFactory.createSchema(sprottyModel);
      this.actionDispatcher.dispatch(new UpdateModelAction(sprottySchema));
    } else {
      this.actionDispatcher.dispatch(new UpdateModelAction(INITIAL_ROOT));
    }
  }
}
