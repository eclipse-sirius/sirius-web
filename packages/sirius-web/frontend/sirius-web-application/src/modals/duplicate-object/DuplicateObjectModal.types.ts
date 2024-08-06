/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
  item: any;
  onObjectDuplicated: (object: Selection) => void;
  onClose: () => void;
}

export interface DuplicateObjectModalState {
  containerSelection: Selection;
  containmentFeatureNames: string[];
  containmentFeatureName: string;
  duplicateContent: boolean;
  copyOutgoingReferences: boolean;
  updateIncomingReferences: boolean;
  filterBarText: string;
}
