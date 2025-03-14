/*******************************************************************************
 * Copyright (c) 2021, 2025 Obeo.
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
import { Selection } from '@eclipse-sirius/sirius-components-core';

export interface DuplicateObjectModalProps {
  editingContextId: string;
  objectToDuplicateId: string;
  objectToDuplicateKind: string;
  onObjectDuplicated: (selection: Selection) => void;
  onClose: () => void;
}

export interface DuplicateObjectModalState {
  containerSelectedId: string | null;
  containmentFeatureName: string;
  duplicateContent: boolean;
  copyOutgoingReferences: boolean;
  updateIncomingReferences: boolean;
}
