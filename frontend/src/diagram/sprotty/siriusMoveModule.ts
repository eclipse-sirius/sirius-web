/*******************************************************************************
 * Copyright (c) 2021 THALES GLOBAL SERVICES.
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
import { configureCommand, LocationPostprocessor, TYPES } from 'sprotty';
import { SiriusMoveCommand, SiriusMoveMouseListener } from './siriusMove';

const siriusMoveModule = new ContainerModule((bind, _unbind, isBound) => {
  bind(TYPES.MouseListener).to(SiriusMoveMouseListener);
  configureCommand({ bind, isBound }, SiriusMoveCommand);
  bind(LocationPostprocessor).toSelf().inSingletonScope();
  bind(TYPES.IVNodePostprocessor).toService(LocationPostprocessor);
  bind(TYPES.HiddenVNodePostprocessor).toService(LocationPostprocessor);
});

export default siriusMoveModule;
