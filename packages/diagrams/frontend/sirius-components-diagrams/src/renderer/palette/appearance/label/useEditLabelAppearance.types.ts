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
import { GQLErrorPayload, GQLSuccessPayload } from '@eclipse-sirius/sirius-components-core';

export interface UseEditLabelAppearanceValue {
  updateFontSize: (
    editingContextId: string,
    representationId: string,
    diagramElementId: string,
    labelId: string,
    fontSize: number
  ) => void;
  updateItalic: (
    editingContextId: string,
    representationId: string,
    diagramElementId: string,
    labelId: string,
    isItalic: boolean
  ) => void;
  updateBold: (
    editingContextId: string,
    representationId: string,
    diagramElementId: string,
    labelId: string,
    isBold: boolean
  ) => void;
  updateUnderline: (
    editingContextId: string,
    representationId: string,
    diagramElementId: string,
    labelId: string,
    isUnderline: boolean
  ) => void;
  updateStrikeThrough: (
    editingContextId: string,
    representationId: string,
    diagramElementId: string,
    labelId: string,
    isStrikeThrough: boolean
  ) => void;
  updateBorderColor: (
    editingContextId: string,
    representationId: string,
    diagramElementId: string,
    labelId: string,
    borderColor: string
  ) => void;
  updateBorderRadius: (
    editingContextId: string,
    representationId: string,
    diagramElementId: string,
    labelId: string,
    borderRadius: number
  ) => void;
  updateBorderSize: (
    editingContextId: string,
    representationId: string,
    diagramElementId: string,
    labelId: string,
    borderSize: number
  ) => void;
  updateBorderStyle: (
    editingContextId: string,
    representationId: string,
    diagramElementId: string,
    labelId: string,
    borderStyle: string
  ) => void;
  updateColor: (
    editingContextId: string,
    representationId: string,
    diagramElementId: string,
    labelId: string,
    labelColor: string
  ) => void;
  updateBackground: (
    editingContextId: string,
    representationId: string,
    diagramElementId: string,
    labelId: string,
    background: string
  ) => void;
}

export interface GQLEditLabelAppearanceData {
  editLabelAppearance: GQLEditLabelAppearancePayload;
}

export type GQLEditLabelAppearancePayload = GQLErrorPayload | GQLSuccessPayload;

export interface GQLEditLabelAppearanceVariables {
  input: GQLEditLabelAppearanceInput;
}

export interface GQLEditLabelAppearanceInput {
  id: string;
  editingContextId: string;
  representationId: string;
  diagramElementId: string;
  labelId: string;
  appearance: Partial<GQLLabelAppearanceInput>;
}

export interface GQLLabelAppearanceInput {
  fontSize: number;
  italic: boolean;
  bold: boolean;
  underline: boolean;
  strikeThrough: boolean;
  borderColor: string;
  borderRadius: number;
  borderSize: number;
  borderStyle: string;
  color: string;
  background: string;
}
