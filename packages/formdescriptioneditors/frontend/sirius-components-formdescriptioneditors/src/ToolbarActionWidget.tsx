/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
import { useMutation } from '@apollo/client';
import { Selection } from '@eclipse-sirius/sirius-components-core';
import Button from '@material-ui/core/Button';
import { makeStyles, Theme } from '@material-ui/core/styles';
import React, { useEffect, useRef, useState } from 'react';
import { v4 as uuid } from 'uuid';
import { deleteToolbarActionMutation, moveToolbarActionMutation } from './FormDescriptionEditorEventFragment';
import {
  GQLDeleteToolbarActionInput,
  GQLDeleteToolbarActionMutationData,
  GQLDeleteToolbarActionMutationVariables,
  GQLDeleteToolbarActionPayload,
  GQLErrorPayload,
  GQLFormDescriptionEditorToolbarAction,
  GQLMoveToolbarActionInput,
  GQLMoveToolbarActionMutationData,
  GQLMoveToolbarActionMutationVariables,
} from './FormDescriptionEditorEventFragment.types';
import { ToolbarActionProps, ToolbarActionState } from './ToolbarActionWidget.types';

const useStyle = makeStyles<Theme>(() => ({
  style: {
    minWidth: '32px',
    lineHeight: 1.25,
  },
  toolbarAction: {
    display: 'flex',
    flexDirection: 'row',
    flexGrow: 1,
  },
  placeholder: {
    height: 'inherit',
    width: '20px',
  },
  dragOver: {
    border: 'dashed 1px red',
  },
}));

const isErrorPayload = (payload: GQLDeleteToolbarActionPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const ToolbarActionWidget = ({
  editingContextId,
  representationId,
  siblings,
  toolbarAction,
  selection,
  setSelection,
}: ToolbarActionProps) => {
  const classes = useStyle();

  const initialState: ToolbarActionState = { message: null, selected: false };
  const [state, setState] = useState<ToolbarActionState>(initialState);

  const ref = useRef<HTMLInputElement | null>(null);

  useEffect(() => {
    if (ref.current && selection.entries.find((entry) => entry.id === toolbarAction.id)) {
      ref.current.focus();
      setState({ message: state.message, selected: true });
    } else {
      setState({ message: state.message, selected: false });
    }
  }, [selection, toolbarAction]);

  const [
    deleteToolbarAction,
    { loading: deleteToolbarActionLoading, data: deleteToolbarActionData, error: deleteToolbarActionError },
  ] = useMutation<GQLDeleteToolbarActionMutationData, GQLDeleteToolbarActionMutationVariables>(
    deleteToolbarActionMutation
  );

  useEffect(() => {
    if (!deleteToolbarActionLoading) {
      if (deleteToolbarActionError) {
        setState({ message: deleteToolbarActionError.message, selected: state.selected });
      }
      if (deleteToolbarActionData) {
        const { deleteToolbarAction } = deleteToolbarActionData;
        if (isErrorPayload(deleteToolbarAction)) {
          setState({ message: deleteToolbarAction.message, selected: state.selected });
        }
      }
    }
  }, [deleteToolbarActionLoading, deleteToolbarActionData, deleteToolbarActionError]);

  const [
    moveToolbarAction,
    { loading: moveToolbarActionLoading, data: moveToolbarActionData, error: moveToolbarActionError },
  ] = useMutation<GQLMoveToolbarActionMutationData, GQLMoveToolbarActionMutationVariables>(moveToolbarActionMutation);

  useEffect(() => {
    if (!moveToolbarActionLoading) {
      if (moveToolbarActionError) {
        setState({ message: moveToolbarActionError.message, selected: state.selected });
      }
      if (moveToolbarActionData) {
        const { moveToolbarAction } = moveToolbarActionData;
        if (isErrorPayload(moveToolbarAction)) {
          setState({ message: moveToolbarAction.message, selected: state.selected });
        }
      }
    }
  }, [moveToolbarActionLoading, moveToolbarActionData, moveToolbarActionError]);

  const handleDragStart: React.DragEventHandler<HTMLDivElement> = (event: React.DragEvent<Element>) => {
    event.dataTransfer.setData('text/plain', toolbarAction.id);
    event.stopPropagation();
  };
  const handleDragEnter: React.DragEventHandler<HTMLDivElement> = (event: React.DragEvent<Element>) => {
    event.preventDefault();
    event.currentTarget.classList.add(classes.dragOver);
  };
  const handleDragOver: React.DragEventHandler<HTMLDivElement> = (event: React.DragEvent<Element>) => {
    event.preventDefault();
    event.currentTarget.classList.add(classes.dragOver);
  };
  const handleDragLeave: React.DragEventHandler<HTMLDivElement> = (event: React.DragEvent<Element>) => {
    event.preventDefault();
    event.currentTarget.classList.remove(classes.dragOver);
  };
  const handleDrop: React.DragEventHandler<HTMLDivElement> = (event: React.DragEvent<HTMLDivElement>) => {
    event.preventDefault();
    event.currentTarget.classList.remove(classes.dragOver);
    onDropBefore(event, toolbarAction);
  };

  const onDropBefore = (
    event: React.DragEvent<HTMLDivElement>,
    toolbarAction: GQLFormDescriptionEditorToolbarAction
  ) => {
    const id: string = event.dataTransfer.getData('text/plain');

    let index: number = siblings.indexOf(toolbarAction);
    if (index <= 0) {
      index = 0;
    }

    const movedToolbarActionIndex = siblings.findIndex((tba) => tba.id === id);
    if (movedToolbarActionIndex > -1 && movedToolbarActionIndex < index) {
      index--;
    }
    const moveToolbarActionInput: GQLMoveToolbarActionInput = {
      id: uuid(),
      editingContextId,
      representationId,
      containerId: null,
      toolbarActionId: id,
      index,
    };
    const moveToolbarActionVariables: GQLMoveToolbarActionMutationVariables = { input: moveToolbarActionInput };
    moveToolbarAction({ variables: moveToolbarActionVariables });
  };

  const handleClick: React.MouseEventHandler<HTMLDivElement> = (
    event: React.MouseEvent<HTMLDivElement, MouseEvent>
  ) => {
    const newSelection: Selection = {
      entries: [
        {
          id: toolbarAction.id,
          label: toolbarAction.label,
          kind: `siriusComponents://semantic?domain=view&entity=${toolbarAction.kind}Description`,
        },
      ],
    };
    setSelection(newSelection);
    event.stopPropagation();
  };

  const handleDelete: React.KeyboardEventHandler<HTMLDivElement> = (event: React.KeyboardEvent<HTMLDivElement>) => {
    event.preventDefault();
    if (event.key === 'Delete') {
      const deleteToolbarActionInput: GQLDeleteToolbarActionInput = {
        id: uuid(),
        editingContextId,
        representationId,
        toolbarActionId: toolbarAction.id,
      };
      const deleteToolbarActionVariables: GQLDeleteToolbarActionMutationVariables = { input: deleteToolbarActionInput };
      deleteToolbarAction({ variables: deleteToolbarActionVariables });
      event.stopPropagation();
    }
  };

  return (
    <div
      className={classes.toolbarAction}
      onClick={handleClick}
      onKeyDown={handleDelete}
      draggable="true"
      onDragStart={handleDragStart}>
      <div
        data-testid={`ToolbarAction-DropArea-${toolbarAction.id}`}
        className={classes.placeholder}
        onDragEnter={handleDragEnter}
        onDragOver={handleDragOver}
        onDragLeave={handleDragLeave}
        onDrop={handleDrop}
      />
      <Button
        data-testid={toolbarAction.label}
        classes={{ root: classes.style }}
        variant="contained"
        color={state.selected ? 'secondary' : 'primary'}
        onFocus={() => setState({ message: state.message, selected: true })}
        onBlur={() => setState({ message: state.message, selected: false })}
        ref={ref}>
        Lorem
      </Button>
    </div>
  );
};
