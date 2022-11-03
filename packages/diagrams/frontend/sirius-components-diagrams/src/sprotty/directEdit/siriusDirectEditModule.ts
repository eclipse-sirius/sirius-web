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
import { configureActionHandler, EditLabelAction, EditLabelActionHandler, TYPES } from 'sprotty';
import { EditLabelUIWithInitialContent } from './EditLabelUIWithInitialContent';
import { StartTypingKeyListener } from './siriusStartTypingKeyListener';

export const siriusLabelEditUiModule = new ContainerModule((bind, _unbind, isBound) => {
  const context = { bind, isBound };
  configureActionHandler(context, EditLabelAction.KIND, EditLabelActionHandler);
  bind(TYPES.KeyListener).to(StartTypingKeyListener);
  bind(EditLabelUIWithInitialContent).toSelf().inSingletonScope();
  bind(TYPES.IUIExtension).toService(EditLabelUIWithInitialContent);
});
