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
import { memo, useRef } from 'react';
import { makeStyles } from 'tss-react/mui';
import { useTableMutations } from '../../graphql/mutation/useTableMutation';
import { DragState, ResizeRowHandlerProps } from './ResizeRowHandler.types';

const useStyles = makeStyles()(() => ({
  handler: {
    position: 'absolute',
    margin: 0,
    backgroundColor: '#B3BFC5',
    borderColor: '#B3BFC5',
    borderRadius: '2px',
    width: '24px',
    height: '4px',
    bottom: 0,
    left: '15px',
    cursor: 'row-resize',
  },
}));

export const ResizeRowHandler = memo(
  ({ editingContextId, representationId, table, readOnly, row, onRowHeightChanged }: ResizeRowHandlerProps) => {
    const { classes } = useStyles();
    const { resizeRow } = useTableMutations(editingContextId, representationId, table.id);

    const dragState = useRef<DragState>({
      isDragging: false,
      height: 0,
      trElement: undefined,
    });

    const handleMouseDown = (e) => {
      e.preventDefault();

      dragState.current = {
        isDragging: true,
        height: parseInt(window.getComputedStyle(e.target.parentElement.parentElement).height, 10),
        trElement: e.target.parentElement.parentElement,
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
