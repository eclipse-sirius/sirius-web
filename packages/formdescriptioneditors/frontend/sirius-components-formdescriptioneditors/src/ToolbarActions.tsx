/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
import { GQLGroup, GQLToolbarAction, GQLWidget } from '@eclipse-sirius/sirius-components-forms';
import IconButton from '@material-ui/core/IconButton';
import Snackbar from '@material-ui/core/Snackbar';
import { makeStyles, Theme } from '@material-ui/core/styles';
import Tooltip from '@material-ui/core/Tooltip';
import AddIcon from '@material-ui/icons/Add';
import CloseIcon from '@material-ui/icons/Close';
import { useEffect, useState } from 'react';
import { v4 as uuid } from 'uuid';
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
import { ToolbarActionsProps } from './ToolbarActions.types';
import { ToolbarActionWidget } from './ToolbarActionWidget';
import { getAllWidgets, isKind } from './WidgetOperations';

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

export const ToolbarActions = ({
  editingContextId,
  representationId,
  formDescriptionEditor,
  group,
  selection,
  setSelection,
}: ToolbarActionsProps) => {
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
      id: uuid(),
      editingContextId,
      representationId,
      containerId: group.id,
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

    const id: string = event.dataTransfer.getData('text/plain');

    if (isKind(id)) {
      return;
    } else if (id === 'Group') {
      return;
    } else if (getAllWidgets(formDescriptionEditor).find((w: GQLWidget) => w.id === id)) {
      return;
    } else if (formDescriptionEditor.groups.find((g: GQLGroup) => g.id === id)) {
      return;
    }

    let index = group.toolbarActions.length - 1; // Move at the end of the toolbar

    const moveToolbarActionInput: GQLMoveToolbarActionInput = {
      id: uuid(),
      editingContextId,
      representationId,
      containerId: group.id,
      toolbarActionId: id,
      index,
    };
    const moveToolbarActionVariables: GQLMoveToolbarActionMutationVariables = { input: moveToolbarActionInput };
    moveToolbarAction({ variables: moveToolbarActionVariables });
  };

  return (
    <div className={classes.toolbar}>
      {group.toolbarActions.map((toolbarAction: GQLToolbarAction) => (
        <div className={classes.toolbarAction} key={toolbarAction.id}>
          <ToolbarActionWidget
            data-testid={toolbarAction.id}
            editingContextId={editingContextId}
            representationId={representationId}
            formDescriptionEditor={formDescriptionEditor}
            group={group}
            toolbarAction={toolbarAction}
            selection={selection}
            setSelection={setSelection}
          />
        </div>
      ))}
      <div
        data-testid={`Group-ToolbarActions-DropArea-${group.id}`}
        className={classes.toolbarActionDropArea}
        onDragEnter={handleDragEnter}
        onDragOver={handleDragOver}
        onDragLeave={handleDragLeave}
        onDrop={handleDrop}
      />
      <div className={classes.newToolbarAction}>
        <Tooltip title={'Add new Toolbar Action'} arrow data-testid={`Group-ToolbarActions-NewAction-${group.id}`}>
          <IconButton size="small" aria-label="add" color="primary" onClick={handleAddToolbarAction}>
            <AddIcon />
          </IconButton>
        </Tooltip>
      </div>
      <Snackbar
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'right',
        }}
        open={!!message}
        autoHideDuration={3000}
        onClose={() => setMessage(null)}
        message={message}
        action={
          <IconButton size="small" aria-label="close" color="inherit" onClick={() => setMessage(null)}>
            <CloseIcon fontSize="small" />
          </IconButton>
        }
        data-testid="error"
      />
    </div>
  );
};
