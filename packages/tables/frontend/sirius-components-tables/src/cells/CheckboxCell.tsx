/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import Checkbox from '@mui/material/Checkbox';
import { CheckboxCellProps } from './CheckboxCell.types';
import { useEditCheckboxCell } from './useEditCheckboxCell';

export const CheckboxCell = ({ editingContextId, representationId, tableId, cell, disabled }: CheckboxCellProps) => {
  const { editCheckboxCell, loading } = useEditCheckboxCell(editingContextId, representationId, tableId, cell.id);

  const handleChange = (_event: React.ChangeEvent<HTMLInputElement>, checked: boolean) => {
    editCheckboxCell(checked);
  };

  return <Checkbox checked={cell.booleanValue} onChange={handleChange} disabled={disabled || loading} />;
};
