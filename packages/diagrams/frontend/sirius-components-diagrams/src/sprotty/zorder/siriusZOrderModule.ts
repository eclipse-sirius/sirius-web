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
import { configureCommand } from 'sprotty';
import { SiriusBringToFrontCommand } from './siriusBringToFrontCommand';

export const siriusZOrderModule = new ContainerModule((bind, _unbind, isBound) => {
  configureCommand({ bind, isBound }, SiriusBringToFrontCommand);
});
