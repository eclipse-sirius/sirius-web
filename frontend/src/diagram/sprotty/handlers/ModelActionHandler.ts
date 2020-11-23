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
  ILogger,
  IModelFactory,
  IActionHandler,
  IActionHandlerInitializer,
  ActionHandlerRegistry,
  Action,
  SModelRoot,
} from 'sprotty';
import { SIRIUS_TYPES } from 'diagram/sprotty/Types';
import { ISetState } from 'diagram/sprotty/ISetState';

@injectable()
export class ModelActionHandler implements IActionHandler, IActionHandlerInitializer {
  @inject(TYPES.ILogger) logger: ILogger;
  @inject(TYPES.IActionDispatcher) actionDispatcher: IActionDispatcher;

  @inject(SIRIUS_TYPES.SET_STATE) setState: ISetState;

  @inject(TYPES.IModelFactory) modelFactory: IModelFactory;

  initialize(registry: ActionHandlerRegistry): void {
    registry.register(UpdateModelAction.KIND, this);
  }

  handle(action: Action): void {
    switch (action.kind) {
      case UpdateModelAction.KIND:
        this.handleUpdateModelAction(<UpdateModelAction>action);
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
}
