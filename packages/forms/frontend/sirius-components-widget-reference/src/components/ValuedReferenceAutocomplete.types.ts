/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import { MutationFunction } from '@apollo/client';
import React from 'react';
import {
  GQLClearReferenceMutationData,
  GQLClearReferenceMutationVariables,
  GQLEditReferenceData,
  GQLEditReferenceVariables,
  GQLReferenceValue,
  GQLReferenceWidget,
  GQLRemoveReferenceValueMutationData,
  GQLRemoveReferenceValueMutationVariables,
} from '../ReferenceWidgetFragment.types';

export interface ValuedReferenceAutocompleteProps {
  editingContextId: string;
  formId: string;
  widget: GQLReferenceWidget;
  readOnly: boolean;
  onDragEnter: (event: React.DragEvent<HTMLDivElement>) => void;
  onDragOver: (event: React.DragEvent<HTMLDivElement>) => void;
  onDrop: (event: React.DragEvent<HTMLDivElement>) => void;
  onMoreClick: (event: React.MouseEvent<Element, MouseEvent>) => void;
  onCreateClick: (event: React.MouseEvent<Element, MouseEvent>) => void;
  editReference: MutationFunction<GQLEditReferenceData, GQLEditReferenceVariables>;
  optionClickHandler: (element: GQLReferenceValue) => void;
  clearReference: MutationFunction<GQLClearReferenceMutationData, GQLClearReferenceMutationVariables>;
  removeReferenceValue: MutationFunction<GQLRemoveReferenceValueMutationData, GQLRemoveReferenceValueMutationVariables>;
}
