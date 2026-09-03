/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import Typography from '@mui/material/Typography';
import React, { useEffect } from 'react';
import { makeStyles } from 'tss-react/mui';
import { addGroupMutation, moveGroupMutation } from './FormDescriptionEditorEventFragment';
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
import { Group } from './Group';
import { PageProps } from './Page.types';
import { useFormDescriptionEditor } from './hooks/useFormDescriptionEditor';

const isErrorPayload = (payload: GQLAddPagePayload | GQLMovePagePayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

const usePageStyles = makeStyles()((theme) => ({
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

export const Page = ({ page }: PageProps) => {
  const { editingContextId, representationId, readOnly } = useFormDescriptionEditor();
  const noop = () => {};
  const { classes } = usePageStyles();

  const { addErrorMessage, addMessages } = useMultiToast();

  const [addGroup, { loading: addGroupLoading, data: addGroupData, error: addGroupError }] = useMutation<
    GQLAddGroupMutationData,
    GQLAddGroupMutationVariables
  >(addGroupMutation);

  useEffect(() => {
    if (!addGroupLoading) {
      if (addGroupError) {
        addErrorMessage(addGroupError.message);
      }
      if (addGroupData) {
        const { addGroup } = addGroupData;
        if (isErrorPayload(addGroup)) {
          addMessages(addGroup.messages);
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
        addErrorMessage(moveGroupError.message);
      }
      if (moveGroupData) {
        const { moveGroup } = moveGroupData;
        if (isErrorPayload(moveGroup)) {
          addMessages(moveGroup.messages);
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
          return <Group key={group.id} page={page} group={group} />;
        })}
      </div>
      <div
        data-testid="Page-DropArea"
        className={classes.bottomDropArea}
        onDragEnter={readOnly ? noop : handleDragEnter}
        onDragOver={readOnly ? noop : handleDragOver}
        onDragLeave={readOnly ? noop : handleDragLeave}
        onDrop={readOnly ? noop : handleDrop}>
        <Typography variant="body1">{'Drag and drop a group here'}</Typography>
      </div>
    </div>
  );
};
