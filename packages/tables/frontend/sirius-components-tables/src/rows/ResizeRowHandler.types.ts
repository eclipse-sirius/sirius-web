/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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
import { GQLLine, GQLTable } from '../table/TableContent.types';

export interface ResizeRowHandlerProps {
  editingContextId: string;
  representationId: string;
  table: GQLTable;
  readOnly: boolean;
  row: GQLLine;
}

export interface DragState {
  isDragging: boolean;
  height: number;
  trElement: HTMLElement | undefined;
}

export interface GQLResizeColumnInput {
  id: string;
  editingContextId: string;
  representationId: string;
  tableId: string;
  columnId: string;
  width: number;
}

export interface GQLResizeRowInput {
  id: string;
  editingContextId: string;
  representationId: string;
  tableId: string;
  rowId: string;
  height: number;
}

export interface GQLResizeRowVariables {
  input: GQLResizeRowInput;
}

export interface GQLResizeRowData {
  resizeTableRow: GQLResizeTableRowPayload;
}

export type GQLResizeTableRowPayload = GQLErrorPayload | GQLSuccessPayload;
