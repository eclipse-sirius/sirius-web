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

import { MRT_Row, MRT_TableInstance } from 'material-react-table';
import { GQLLine } from '../table/TableContent.types';

export interface RowContextMenuProps {
  editingContextId: string;
  representationId: string;
  tableId: string;
  row: MRT_Row<GQLLine>;
  table: MRT_TableInstance<GQLLine>;
  readOnly: boolean;
}

export interface RowContextMenuEntry {
  __typename: string;
  id: string;
  label: string;
  iconURLs: string[];
}
