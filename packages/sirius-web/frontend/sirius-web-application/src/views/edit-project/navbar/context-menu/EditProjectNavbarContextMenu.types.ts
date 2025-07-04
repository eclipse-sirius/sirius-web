/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

export interface EditProjectNavbarContextMenuProps {
  anchorEl: HTMLElement;
  onClose: () => void;
  workbenchHandle: WorkbenchHandle;
}

export interface EditProjectNavbarContextMenuState {
  redirectUrl: string | null;
}

export interface EditProjectNavbarMenuContainerProps {
  children?: React.ReactNode;
}

export interface EditProjectNavbarMenuEntryProps {
  projectId: string;
  onCloseContextMenu: () => void;
}
