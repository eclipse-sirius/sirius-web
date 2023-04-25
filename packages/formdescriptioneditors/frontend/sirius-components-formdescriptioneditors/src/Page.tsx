/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import CloseIcon from '@material-ui/icons/Close';
import IconButton from '@material-ui/core/IconButton';
import Snackbar from '@material-ui/core/Snackbar';
import { Group } from './Group';
import { PageProps, PageState } from './Page.types';
import {
  GQLAddGroupInput,
  GQLAddGroupMutationData,
  GQLAddGroupMutationVariables,
  GQLAddPagePayload,
  GQLErrorPayload,
  GQLMoveGroupInput,
  GQLMoveGroupMutationData,
  GQLMoveGroupMutationVariables,
  GQLMovePagePayload,
} from './FormDescriptionEditorEventFragment.types';
import { addGroupMutation, moveGroupMutation } from './FormDescriptionEditorEventFragment';
import React, { useEffect, useRef, useState } from 'react';

const isErrorPayload = (payload: GQLAddPagePayload | GQLMovePagePayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

const usePageStyles = makeStyles((theme) => ({
  page: {
    display: 'flex',
    flexDirection: 'column',
    '& > *': {
      marginBottom: theme.spacing(2),
    },
    overflowY: 'auto',
  },
  preview: {
    width: '100%',
    padding: '4px 8px 4px 8px',
    overflowY: 'auto',
  },
  bottomDropArea: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: 'whitesmoke',
    borderRadius: '10px',
    color: 'gray',
    height: '60px',
  },
  dragOver: {
    borderWidth: '1px',
    borderStyle: 'dashed',
    borderColor: theme.palette.primary.main,
  },
}));

export const Page = ({
  editingContextId,
  representationId,
  formDescriptionEditor,
  page,
  selection,
  setSelection,
}: PageProps) => {
  const classes = usePageStyles();

  const initialState: PageState = { message: null, selected: false };
  const [state, setState] = useState<PageState>(initialState);
  const { message } = state;

  const ref = useRef<HTMLInputElement | null>(null);

  useEffect(() => {
    if (ref.current && selection.entries.find((entry) => entry.id === page.id)) {
      ref.current.focus();
      setState((prevState) => {
        return { ...prevState, selected: true };
      });
    } else {
      setState((prevState) => {
        return { ...prevState, selected: false };
      });
    }
  }, [selection, page]);

  const [addGroup, { loading: addGroupLoading, data: addGroupData, error: addGroupError }] = useMutation<
    GQLAddGroupMutationData,
    GQLAddGroupMutationVariables
  >(addGroupMutation);

  useEffect(() => {
    if (!addGroupLoading) {
      if (addGroupError) {
        setState((prevState) => {
          return { ...prevState, message: addGroupError.message };
        });
      }
      if (addGroupData) {
        const { addGroup } = addGroupData;
        if (isErrorPayload(addGroup)) {
          setState((prevState) => {
            return { ...prevState, message: addGroup.message };
          });
        }
      }
    }
  }, [addGroupLoading, addGroupData, addGroupError]);

  const [moveGroup, { loading: moveGroupLoading, data: moveGroupData, error: moveGroupError }] = useMutation<
    GQLMoveGroupMutationData,
    GQLMoveGroupMutationVariables
  >(moveGroupMutation);

  useEffect(() => {
    if (!moveGroupLoading) {
      if (moveGroupError) {
        setState((prevState) => {
          return { ...prevState, message: moveGroupError.message };
        });
      }
      if (moveGroupData) {
        const { moveGroup } = moveGroupData;
        if (isErrorPayload(moveGroup)) {
          setState((prevState) => {
            return { ...prevState, message: moveGroup.message };
          });
        }
      }
    }
  }, [moveGroupLoading, moveGroupData, moveGroupError]);

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

    if (type !== 'Group') {
      return;
    }

    let index = page.groups.length;

    if (id === 'Group') {
      const addGroupInput: GQLAddGroupInput = {
        id: crypto.randomUUID(),
        editingContextId,
        pageId: page.id,
        representationId,
        index,
      };
      const addGroupVariables: GQLAddGroupMutationVariables = { input: addGroupInput };
      addGroup({ variables: addGroupVariables });
    } else if (page.groups.find((g) => g.id === id)) {
      index--;
      const moveGroupInput: GQLMoveGroupInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId,
        pageId: page.id,
        groupId: id,
        index,
      };
      const moveGroupVariables: GQLMoveGroupMutationVariables = { input: moveGroupInput };
      moveGroup({ variables: moveGroupVariables });
    }
  };

  return (
    <div className={classes.preview}>
      <div className={classes.page}>
        {page.groups.map((group) => {
          return (
            <Group
              key={group.id}
              editingContextId={editingContextId}
              representationId={representationId}
              formDescriptionEditor={formDescriptionEditor}
              page={page}
              group={group}
              selection={selection}
              setSelection={setSelection}
            />
          );
        })}
      </div>
      <div
        data-testid="Page-DropArea"
        className={classes.bottomDropArea}
        onDragEnter={handleDragEnter}
        onDragOver={handleDragOver}
        onDragLeave={handleDragLeave}
        onDrop={handleDrop}>
        <Typography variant="body1">{'Drag and drop a group here'}</Typography>
      </div>
      <Snackbar
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'right',
        }}
        open={!!message}
        autoHideDuration={3000}
        onClose={() =>
          setState((prevState) => {
            return { ...prevState, message: null };
          })
        }
        message={message}
        action={
          <IconButton
            size="small"
            aria-label="close"
            color="inherit"
            onClick={() =>
              setState((prevState) => {
                return { ...prevState, message: null };
              })
            }>
            <CloseIcon fontSize="small" />
          </IconButton>
        }
        data-testid="error"
      />
    </div>
  );
};
