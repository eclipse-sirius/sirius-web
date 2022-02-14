/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

import { ContainerModule, decorate, inject } from 'inversify';
import {
  Command,
  CommandExecutionContext,
  CommandReturn,
  configureCommand,
  configureModelElement,
  DeleteElementCommand,
  EmptyGroupView,
  ReconnectAction,
  SDanglingAnchor,
  TYPES,
} from 'sprotty';
import { SiriusSwitchEditModeCommand } from './siriusSwitchEditModeCommand';

/**
 * Replaces the sprotty edge edit module to use our own implementation of a switch edit mode command.
 */
export const siriusEdgeEditModule = new ContainerModule((bind, unbind, isBound, rebind) => {
  const context = { bind, isBound };
  configureCommand(context, SiriusSwitchEditModeCommand);
  configureCommand(context, EmptyReconnectCommand);
  configureCommand(context, DeleteElementCommand);
  configureModelElement(context, 'dangling-anchor', SDanglingAnchor, EmptyGroupView);
});

/**
 * Prevent the reconnect to fail since we activate the edge edition.
 * This class will be removed soon, when we will support the edge reconnection.
 */
class EmptyReconnectCommand extends Command {
  static readonly KIND = ReconnectAction.KIND;

  constructor(protected readonly action: ReconnectAction) {
    super();
  }

  override execute(context: CommandExecutionContext): CommandReturn {
    return context.root;
  }
  undo(context: CommandExecutionContext): CommandReturn {
    return context.root;
  }
  redo(context: CommandExecutionContext): CommandReturn {
    return context.root;
  }
}
decorate(inject(TYPES.Action) as ParameterDecorator, EmptyReconnectCommand, 0);
