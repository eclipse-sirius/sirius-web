/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
import { WorkbenchHandle } from '@eclipse-sirius/sirius-components-core';
import { GQLOmniboxCommand } from '@eclipse-sirius/sirius-components-omnibox';

export interface WorkbenchOmniboxProps {
  editingContextId: string;
  workbenchHandle: WorkbenchHandle;
  children: React.ReactNode;
}

export interface WorkbenchOmniboxState {
  open: boolean;
  commands: GQLOmniboxCommand[] | null;
}
