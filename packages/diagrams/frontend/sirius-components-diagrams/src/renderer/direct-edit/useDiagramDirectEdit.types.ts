/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import { DirectEditTrigger } from './DiagramDirectEditContext.types';

export interface UseDiagramDirectEditValue {
  onDirectEdit: (event: React.KeyboardEvent<Element>) => void;
  currentlyEditedLabelId: string | null;
  editingInput: string | null;
  setCurrentlyEditedLabelId: (
    directEditTrigger: DirectEditTrigger,
    currentlyEditedLabelId: string,
    editingInput: string | null
  ) => void;
  resetDirectEdit: () => void;
}
