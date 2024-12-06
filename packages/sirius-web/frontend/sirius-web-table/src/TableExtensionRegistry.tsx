/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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

import { ExtensionRegistry } from '@eclipse-sirius/sirius-components-core';
import { settingButtonMenuEntryExtensionPoint } from '@eclipse-sirius/sirius-components-tables';
import { ForkViewMenuAction } from './contributions/ForkViewMenuAction';

const tableRegistry = new ExtensionRegistry();

tableRegistry.addComponent(settingButtonMenuEntryExtensionPoint, {
  identifier: `siriusWebTable_${settingButtonMenuEntryExtensionPoint.identifier}_fork`,
  Component: ForkViewMenuAction,
});

export { tableRegistry };
