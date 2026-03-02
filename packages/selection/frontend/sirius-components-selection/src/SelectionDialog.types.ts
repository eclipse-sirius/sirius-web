/*******************************************************************************
 * Copyright (c) 2021, 2026 Obeo.
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

import { DiagramDialogComponentProps } from '@eclipse-sirius/sirius-components-diagrams';

export interface SelectionDialogProps extends DiagramDialogComponentProps {}

export interface SelectionDialogState {
  selectedTreeItemIds: string[];
  selectionDialogOption: SelectionDialogOptions;
}

export type SelectionDialogOptions = 'INITIAL' | 'NO_SELECTION' | 'WITH_SELECTION';
