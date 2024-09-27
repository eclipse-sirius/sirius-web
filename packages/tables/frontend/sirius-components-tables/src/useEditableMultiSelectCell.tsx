/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import { useMutation } from '@apollo/client/react/hooks/useMutation';
import { useReporting } from '@eclipse-sirius/sirius-components-core';
import { TextFieldProps } from '@mui/material/TextField';
import { GQLLine, GQLMultiSelectCell } from './TableContent.types';

import { gql } from '@apollo/client/core';
import Checkbox from '@mui/material/Checkbox';
import MenuItem from '@mui/material/MenuItem';
import { SelectProps } from '@mui/material/Select';
import Typography from '@mui/material/Typography';
import { MRT_Cell, MRT_Row } from 'material-react-table';
import {
  GQLEditMultiSelectCellMutationData,
  GQLEditMultiSelectCellMutationVariables,
} from './useEditableMultiSelectCell.types';

const editMultiSelectCellMutation = gql`
  mutation editMultiSelectCell($input: EditMultiSelectCellInput!) {
    editMultiSelectCell(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

export const useEditableMultiSelectCell = (editingContextId: string, representationId: string, tableId: string) => {
  const [mutationEditMultiSelectCell, mutationEditMultiSelectCellResult] = useMutation<
    GQLEditMultiSelectCellMutationData,
    GQLEditMultiSelectCellMutationVariables
  >(editMultiSelectCellMutation);

  useReporting(
    mutationEditMultiSelectCellResult,
    (data: GQLEditMultiSelectCellMutationData) => data?.editMultiSelectCell
  );

  const editSelectCell = (cellId: string, newValues: string[]) => {
    const variables: GQLEditMultiSelectCellMutationVariables = {
      input: {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: representationId,
        tableId: tableId,
        cellId,
        newValues,
      },
    };
    mutationEditMultiSelectCell({ variables });
  };

  const getMuiEditMultipleSelectProps = (
    multiSelectCell: GQLMultiSelectCell,
    cell: MRT_Cell<GQLLine, string>,
    row: MRT_Row<GQLLine>,
    setEditedLines: React.Dispatch<React.SetStateAction<GQLLine[]>>
  ): TextFieldProps => {
    const cellValue = cell.getValue<string>();

    const selectProps: SelectProps = {
      value: multiSelectCell.values,
      multiple: true,
      renderValue: (selected) =>
        multiSelectCell.options
          .filter((option) => (selected as string[]).includes(option.id))
          .map((option) => option.label)
          .join(', '),
    };

    const children = multiSelectCell.options.map((opt) => {
      return (
        <MenuItem key={opt.id} value={opt.id}>
          <Checkbox checked={multiSelectCell.values.includes(opt.id)} readOnly={false} />
          <Typography>{opt.label}</Typography>
        </MenuItem>
      );
    });

    return {
      type: 'select',
      select: true,
      children: children,
      SelectProps: selectProps,
      required: true,
      onChange: (event) => {
        if (event.target.value !== cellValue) {
          setEditedLines((prevEditedLines) => {
            return prevEditedLines.map((line) => {
              if (row.original.id == line.id) {
                const newCells = row.original.cells.map((gqlCell) =>
                  cell.column.id == gqlCell.columnId ? { ...gqlCell, values: event.target.value } : gqlCell
                );
                return { ...line, cells: newCells };
              }
              return line;
            });
          });
        }
      },
      onBlur: (event) => {
        editSelectCell(multiSelectCell.id, event.target.value as unknown as string[]);
      },
    };
  };

  return getMuiEditMultipleSelectProps;
};
