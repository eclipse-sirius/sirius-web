/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
import { Selection } from 'workbench/Workbench.types';

export interface StereotypeDescription {
  id: string;
  label: string;
  documentName: string;
}

export interface NewDocumentAreaProps {
  editingContextId: string;
  stereotypeDescriptions: StereotypeDescription[];
  setSelection: (selection: Selection) => void;
  readOnly: boolean;
}
