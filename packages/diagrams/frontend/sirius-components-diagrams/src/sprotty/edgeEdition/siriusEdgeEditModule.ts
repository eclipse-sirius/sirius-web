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

import { ContainerModule } from 'inversify';
import {
  configureCommand,
  configureModelElement,
  DeleteElementCommand,
  EmptyGroupView,
  ReconnectCommand,
} from 'sprotty';
import { SiriusDanglingAnchor } from '../routing/siriusDanglingAnchor';
import { SiriusSwitchEditModeCommand } from './siriusSwitchEditModeCommand';

/**
 * Replaces the sprotty edge edit module to use our own implementation of a switch edit mode command.
 */
export const siriusEdgeEditModule = new ContainerModule((bind, _unbind, isBound) => {
  const context = { bind, isBound };
  configureCommand(context, SiriusSwitchEditModeCommand);
  configureCommand(context, ReconnectCommand);
  configureCommand(context, DeleteElementCommand);
  configureModelElement(context, 'dangling-anchor', SiriusDanglingAnchor, EmptyGroupView);
});
