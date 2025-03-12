/*******************************************************************************
 * Copyright (c) 2024, 2025 CEA List.
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

export interface RowFiltersButtonProps {
  readOnly: boolean;
  rowFilters: RowFilter[];
  onRowFiltersChange: (rowFilters: RowFilter[]) => void;
}

export interface RowFilter {
  id: string;
  label: string;
  state: boolean;
}

export interface RowFiltersMenuState {
  anchorEl: HTMLElement | null;
}
