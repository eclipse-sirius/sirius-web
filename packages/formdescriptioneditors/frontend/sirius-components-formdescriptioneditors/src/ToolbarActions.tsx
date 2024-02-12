/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
import { Toast } from '@eclipse-sirius/sirius-components-core';
import { GQLToolbarAction } from '@eclipse-sirius/sirius-components-forms';
import IconButton from '@material-ui/core/IconButton';
import { makeStyles, Theme } from '@material-ui/core/styles';
import Tooltip from '@material-ui/core/Tooltip';
import AddIcon from '@material-ui/icons/Add';
import { useEffect, useState } from 'react';
import { addToolbarActionMutation, moveToolbarActionMutation } from './FormDescriptionEditorEventFragment';
import {
  GQLAddToolbarActionInput,
  GQLAddToolbarActionMutationData,
  GQLAddToolbarActionMutationVariables,
  GQLAddToolbarActionPayload,
  GQLErrorPayload,
  GQLMoveToolbarActionInput,
  GQLMoveToolbarActionMutationData,
  GQLMoveToolbarActionMutationVariables,
  GQLMoveToolbarActionPayload,
} from './FormDescriptionEditorEventFragment.types';
import { useFormDescriptionEditor } from './hooks/useFormDescriptionEditor';
import { ToolbarActionsProps } from './ToolbarActions.types';
import { ToolbarActionWidget } from './ToolbarActionWidget';

const useToolbarActionsStyles = makeStyles<Theme>((theme: Theme) => ({
  toolbar: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'flex-end',
  },
  toolbarAction: {
    whiteSpace: 'nowrap',
  },
  toolbarActionDropArea: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: 'whitesmoke',
    border: '1px solid whitesmoke',
    borderRadius: '5px',
    width: '20px',
    height: '30px',
  },
  newToolbarAction: {
    paddingLeft: theme.spacing(1),
  },
  dragOver: {
    borderWidth: '1px',
    borderStyle: 'dashed',
    borderColor: theme.palette.primary.main,
  },
}));

const isErrorPayload = (
  payload: GQLAddToolbarActionPayload | GQLMoveToolbarActionPayload
): payload is GQLErrorPayload => payload.__typename === 'ErrorPayload';

export const ToolbarActions = ({ toolbarActions, containerId }: ToolbarActionsProps) => {
  const { editingContextId, representationId, readOnly } = useFormDescriptionEditor();
  const noop = () => {};
  const classes = useToolbarActionsStyles();

  const [message, setMessage] = useState<string | null>(null);

  const [
    addToolbarAction,
    { loading: addToolbarActionLoading, data: addToolbarActionData, error: addToolbarActionError },
  ] = useMutation<GQLAddToolbarActionMutationData, GQLAddToolbarActionMutationVariables>(addToolbarActionMutation);

  useEffect(() => {
    if (!addToolbarActionLoading) {
      if (addToolbarActionError) {
        setMessage(addToolbarActionError.message);
      }
      if (addToolbarActionData) {
        const { addToolbarAction } = addToolbarActionData;
        if (isErrorPayload(addToolbarAction)) {
          setMessage(addToolbarAction.message);
        }
      }
    }
  }, [addToolbarActionLoading, addToolbarActionData, addToolbarActionError]);

  const [
    moveToolbarAction,
    { loading: moveToolbarActionLoading, data: moveToolbarActionData, error: moveToolbarActionError },
  ] = useMutation<GQLMoveToolbarActionMutationData, GQLMoveToolbarActionMutationVariables>(moveToolbarActionMutation);

  useEffect(() => {
    if (!moveToolbarActionLoading) {
      if (moveToolbarActionError) {
        setMessage(moveToolbarActionError.message);
      }
      if (moveToolbarActionData) {
        const { moveToolbarAction } = moveToolbarActionData;
        if (isErrorPayload(moveToolbarAction)) {
          setMessage(moveToolbarAction.message);
        }
      }
    }
  }, [moveToolbarActionLoading, moveToolbarActionData, moveToolbarActionError]);

  const handleAddToolbarAction: React.MouseEventHandler<HTMLButtonElement> = () => {
    const addToolbarActionInput: GQLAddToolbarActionInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      containerId,
    };
    const addToolbarActionVariables: GQLAddToolbarActionMutationVariables = { input: addToolbarActionInput };
    addToolbarAction({ variables: addToolbarActionVariables });
  };

  const handleDragEnter: React.DragEventHandler<HTMLDivElement> = (event) => {
    event.preventDefault();
    event.currentTarget.classList.add(classes.dragOver);
  };
  const handleDragOver: React.DragEventHandler<HTMLDivElement> = (event) => {
    event.preventDefault();
    event.currentTarget.classList.add(classes.dragOver);
  };
  const handleDragLeave: React.DragEventHandler<HTMLDivElement> = (event) => {
    event.preventDefault();
    event.currentTarget.classList.remove(classes.dragOver);
  };
  const handleDrop: React.DragEventHandler<HTMLDivElement> = (event) => {
    event.preventDefault();
    event.currentTarget.classList.remove(classes.dragOver);

    const id: string = event.dataTransfer.getData('draggedElementId');
    const type: string = event.dataTransfer.getData('draggedElementType');

    if (type !== 'ToolbarActionWidget') {
      return;
    }

    let index = toolbarActions.length;
    if (toolbarActions.find((tba: GQLToolbarAction) => tba.id === id)) {
      index--; // Move at the end of the toolbar
    }

    const moveToolbarActionInput: GQLMoveToolbarActionInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      containerId,
      toolbarActionId: id,
      index,
    };
    const moveToolbarActionVariables: GQLMoveToolbarActionMutationVariables = { input: moveToolbarActionInput };
    moveToolbarAction({ variables: moveToolbarActionVariables });
  };

  return (
    <div className={classes.toolbar}>
      {toolbarActions.map((toolbarAction: GQLToolbarAction) => (
        <div className={classes.toolbarAction} key={toolbarAction.id}>
          <ToolbarActionWidget
            data-testid={toolbarAction.id}
            toolbarActions={toolbarActions}
            containerId={containerId}
            toolbarAction={toolbarAction}
          />
        </div>
      ))}
      <div
        data-testid={`ToolbarActions-DropArea-${containerId}`}
        className={classes.toolbarActionDropArea}
        onDragEnter={readOnly ? noop : handleDragEnter}
        onDragOver={readOnly ? noop : handleDragOver}
        onDragLeave={readOnly ? noop : handleDragLeave}
        onDrop={readOnly ? noop : handleDrop}
      />
      <div className={classes.newToolbarAction}>
        <Tooltip title={'Add new Toolbar Action'} arrow data-testid={`ToolbarActions-NewAction-${containerId}`}>
          <IconButton size="small" aria-label="add" color="primary" onClick={readOnly ? noop : handleAddToolbarAction}>
            <AddIcon />
          </IconButton>
        </Tooltip>
      </div>
      <Toast message={message} open={!!message} onClose={() => setMessage(null)} />
    </div>
  );
};
