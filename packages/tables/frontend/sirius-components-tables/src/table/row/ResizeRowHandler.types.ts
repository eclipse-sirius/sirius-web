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
import { GQLLine, GQLTable } from '../TableContent.types';

export interface ResizeRowHandlerProps {
  editingContextId: string;
  representationId: string;
  table: GQLTable;
  readOnly: boolean;
  row: GQLLine;
  onRowHeightChanged: (rowId: string, height: number) => void;
}

export interface DragState {
  isDragging: boolean;
  height: number;
  trElement: HTMLElement | undefined;
}
