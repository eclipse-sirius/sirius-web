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
import { GQLLine, GQLSelectCell } from './TableContent.types';

import { gql } from '@apollo/client/core';
import MenuItem from '@mui/material/MenuItem';
import { SelectProps } from '@mui/material/Select';
import Typography from '@mui/material/Typography';
import { MRT_Cell, MRT_Row } from 'material-react-table';
import { GQLEditSelectCellMutationData, GQLEditSelectCellMutationVariables } from './useEditableSelectCell.types';

const editSelectCellMutation = gql`
  mutation editSelectCell($input: EditSelectCellInput!) {
    editSelectCell(input: $input) {
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

export const useEditableSelectCell = (editingContextId: string, representationId: string, tableId: string) => {
  const [mutationEditSelectCell, mutationEditSelectCellResult] = useMutation<
    GQLEditSelectCellMutationData,
    GQLEditSelectCellMutationVariables
  >(editSelectCellMutation);

  useReporting(mutationEditSelectCellResult, (data: GQLEditSelectCellMutationData) => data?.editSelectCell);

  const editSelectCell = (cellId: string, newValue: string) => {
    const variables: GQLEditSelectCellMutationVariables = {
      input: {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: representationId,
        tableId: tableId,
        cellId,
        newValue,
      },
    };
    mutationEditSelectCell({ variables });
  };

  const muiEditSelectProps = (
    selectCell: GQLSelectCell,
    cell: MRT_Cell<GQLLine, string>,
    row: MRT_Row<GQLLine>,
    setEditedLines: React.Dispatch<React.SetStateAction<GQLLine[]>>
  ): TextFieldProps => {
    const cellValue = cell.getValue<string>();

    const selectProps: SelectProps = {
      value: selectCell.value,
      renderValue: (selected) => selectCell.options.find((opt) => selected === opt.id)?.label,
    };

    const children = selectCell.options.map((opt) => {
      return (
        <MenuItem key={opt.id} value={opt.id}>
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
                  cell.column.id == gqlCell.columnId ? { ...gqlCell, value: event.target.value } : gqlCell
                );
                return { ...line, cells: newCells };
              }
              return line;
            });
          });
        }
      },
      onBlur: (event) => {
        editSelectCell(selectCell.id, event.target.value);
      },
    };
  };

  return muiEditSelectProps;
};
