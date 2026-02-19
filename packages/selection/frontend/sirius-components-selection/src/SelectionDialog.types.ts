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
import { GQLTree } from '@eclipse-sirius/sirius-components-trees';

export interface SelectionDialogProps {
  editingContextId: string;
  selectionRepresentationId: string;
  targetObjectId: string;
  onClose: () => void;
  onFinish: (selectedObjectId: string) => void;
}

export interface SelectionDialogState {
  selectedObjectIds: string[];
  singleTreeItemSelected: GQLTree | null;
  noSelectionOptionSelected: boolean;
}
