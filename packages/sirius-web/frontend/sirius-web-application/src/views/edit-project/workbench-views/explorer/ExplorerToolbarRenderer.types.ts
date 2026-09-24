/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { TreeDescriptionMetadata } from './TreeDescriptionsMenu.types';

export interface ExplorerToolbarRendererProps {
  editingContextId: string;
  readOnly: boolean;
  explorerDescriptions: TreeDescriptionMetadata[];
}
