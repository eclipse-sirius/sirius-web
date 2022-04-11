/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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

export interface EditingContextAction {
  id: string;
  label: string;
}

export interface NewDocumentAreaProps {
  editingContextId: string;
  stereotypeDescriptions: StereotypeDescription[];
  editingContextActions: EditingContextAction[];
  setSelection: (selection: Selection) => void;
  readOnly: boolean;
}

export interface GQLCreateDocumentMutationInput {
  id: string;
  editingContextId: string;
  name: string;
  stereotypeDescriptionId: string;
}

export interface GQLCreateDocumentMutationVariables {
  input: GQLCreateDocumentMutationInput;
}

export interface GQLInvokeEditingContextActionInput {
  id: string;
  editingContextId: string;
  actionId: string;
}

export interface GQLInvokeEditingContextActionVariables {
  input: GQLInvokeEditingContextActionInput;
}
