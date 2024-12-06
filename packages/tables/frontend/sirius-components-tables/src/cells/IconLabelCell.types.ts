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
import { GQLIconLabelCell } from '../table/TableContent.types';

export interface IconLabelCellProps {
  editingContextId: string;
  representationId: string;
  tableId: string;
  cell: GQLIconLabelCell;
}
