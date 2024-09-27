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
import { GQLCheckboxCell, GQLLine } from './TableContent.types';

import { gql } from '@apollo/client/core';
import { MRT_Cell, MRT_Row } from 'material-react-table';
import { GQLEditCheckboxCellMutationData, GQLEditCheckboxCellMutationVariables } from './useEditableCheckboxCell.types';

const editCheckboxCellMutation = gql`
  mutation editCheckboxCell($input: EditCheckboxCellInput!) {
    editCheckboxCell(input: $input) {
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

export const useEditableCheckboxCell = (editingContextId: string, representationId: string, tableId: string) => {
  const [mutationEditCheckboxCell, mutationEditCheckboxCellResult] = useMutation<
    GQLEditCheckboxCellMutationData,
    GQLEditCheckboxCellMutationVariables
  >(editCheckboxCellMutation);

  useReporting(mutationEditCheckboxCellResult, (data: GQLEditCheckboxCellMutationData) => data?.editCheckboxCell);

  const editCheckboxCell = (cellId: string, newValue: boolean) => {
    const variables: GQLEditCheckboxCellMutationVariables = {
      input: {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: representationId,
        tableId: tableId,
        cellId,
        newValue,
      },
    };
    mutationEditCheckboxCell({ variables });
  };

  const getMuiEditTextFieldProps = (
    checkboxCell: GQLCheckboxCell,
    cell: MRT_Cell<GQLLine, string>,
    row: MRT_Row<GQLLine>,
    setEditedLines: React.Dispatch<React.SetStateAction<GQLLine[]>>
  ): TextFieldProps => {
    const cellValue = cell.getValue<string>();

    return {
      type: 'text',
      required: true,
      onBlur: (event) => {
        if (event.target.value !== cellValue) {
          setEditedLines((prevEditedLines) => {
            return prevEditedLines.map((line) => {
              if (row.original.id == line.id) {
                const newCells = row.original.cells.map((gqlCell) =>
                  cell.column.id == gqlCell.columnId ? { ...gqlCell, booleanValue: event.target.value } : gqlCell
                );
                return { ...line, cells: newCells };
              }
              return line;
            });
          });

          editCheckboxCell(checkboxCell.id, event.target.value as unknown as boolean);
        }
      },
    };
  };

  return getMuiEditTextFieldProps;
};
