/*******************************************************************************
 * Copyright (c) 2024, 2025 CEA LIST.
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
import { gql, useMutation } from '@apollo/client';
import { useReporting } from '@eclipse-sirius/sirius-components-core';
import { memo, useRef } from 'react';
import { makeStyles } from 'tss-react/mui';
import {
  DragState,
  GQLResizeRowData,
  GQLResizeRowInput,
  GQLResizeRowVariables,
  ResizeRowHandlerProps,
} from './ResizeRowHandler.types';

const useStyles = makeStyles()(() => ({
  handler: {
    position: 'absolute',
    margin: 0,
    backgroundColor: '#B3BFC5',
    borderColor: '#B3BFC5',
    borderRadius: '2px',
    width: '80%',
    height: '4px',
    bottom: 0,
    left: '15px',
    cursor: 'row-resize',
    display: 'none',
  },
}));

export const resizeRowMutation = gql`
  mutation resizeTableRow($input: ResizeTableRowInput!) {
    resizeTableRow(input: $input) {
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

export const ResizeRowHandler = memo(
  ({ editingContextId, representationId, table, readOnly, row, onRowHeightChanged }: ResizeRowHandlerProps) => {
    const { classes } = useStyles();

    const [mutationResizeRow, mutationResizeRowResult] = useMutation<GQLResizeRowData, GQLResizeRowVariables>(
      resizeRowMutation
    );
    useReporting(mutationResizeRowResult, (data: GQLResizeRowData) => data.resizeTableRow);

    const resizeRow = (rowId: string, height: number) => {
      const input: GQLResizeRowInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId,
        tableId: table.id,
        rowId,
        height,
      };

      mutationResizeRow({ variables: { input } });
    };

    const dragState = useRef<DragState>({
      isDragging: false,
      height: 0,
    });

    const handleMouseDown = (e) => {
      e.preventDefault();

      dragState.current = {
        isDragging: true,
        height: parseInt(window.getComputedStyle(e.target.parentElement.parentElement).height, 10),
      };

      const handleMouseMove = (e: MouseEvent) => {
        if (dragState.current.isDragging) {
          dragState.current.height += e.movementY;
          onRowHeightChanged(row.id, dragState.current.height);
        }
      };

      const handleMouseUp = (_) => {
        dragState.current.isDragging = false;
        resizeRow(row.id, dragState.current.height);
        document.removeEventListener('mousemove', handleMouseMove);
        document.removeEventListener('mouseup', handleMouseUp);
      };

      document.addEventListener('mousemove', handleMouseMove);
      document.addEventListener('mouseup', handleMouseUp);
    };

    return !readOnly && row.isResizable ? <div className={classes.handler} onMouseDown={handleMouseDown} /> : null;
  }
);
