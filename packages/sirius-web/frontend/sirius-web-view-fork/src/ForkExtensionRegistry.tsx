/*******************************************************************************
 * Copyright (c) 2024, 2025 CEA LIST and others.
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
import { treeItemContextMenuEntryExtensionPoint } from '@eclipse-sirius/sirius-components-trees';
import { ForkTreeItemContextMenuContribution } from './contributions/ForkTreeItemContextMenuContribution';
const forkRegistry = new ExtensionRegistry();

forkRegistry.addComponent(treeItemContextMenuEntryExtensionPoint, {
  identifier: `siriusWebViewFork_${treeItemContextMenuEntryExtensionPoint.identifier}_fork`,
  Component: ForkTreeItemContextMenuContribution,
});

export { forkRegistry };
