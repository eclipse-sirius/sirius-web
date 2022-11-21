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
import { Selection, ServerContext, ServerContextValue } from '@eclipse-sirius/sirius-components-core';
import { ButtonStyleProps, getTextDecorationLineValue, GQLButton } from '@eclipse-sirius/sirius-components-forms';
import Button from '@material-ui/core/Button';
import IconButton from '@material-ui/core/IconButton';
import Snackbar from '@material-ui/core/Snackbar';
import { makeStyles, Theme } from '@material-ui/core/styles';
import CloseIcon from '@material-ui/icons/Close';
import React, { useContext, useEffect, useRef, useState } from 'react';
import { v4 as uuid } from 'uuid';
import { deleteToolbarActionMutation, moveToolbarActionMutation } from './FormDescriptionEditorEventFragment';
import {
  GQLDeleteToolbarActionInput,
  GQLDeleteToolbarActionMutationData,
  GQLDeleteToolbarActionMutationVariables,
  GQLDeleteToolbarActionPayload,
  GQLErrorPayload,
  GQLMoveToolbarActionInput,
  GQLMoveToolbarActionMutationData,
  GQLMoveToolbarActionMutationVariables,
} from './FormDescriptionEditorEventFragment.types';
import { ToolbarActionProps, ToolbarActionState } from './ToolbarActionWidget.types';

const useStyles = makeStyles<Theme, ButtonStyleProps>((theme) => ({
  style: {
    minWidth: '32px',
    lineHeight: 1.25,
    backgroundColor: ({ backgroundColor }) => (backgroundColor ? backgroundColor : theme.palette.primary.light),
    color: ({ foregroundColor }) => (foregroundColor ? foregroundColor : 'white'),
    fontSize: ({ fontSize }) => (fontSize ? fontSize : 'inherit'),
    fontStyle: ({ italic }) => (italic ? 'italic' : 'inherit'),
    fontWeight: ({ bold }) => (bold ? 'bold' : 'inherit'),
    textDecorationLine: ({ underline, strikeThrough }) => getTextDecorationLineValue(underline, strikeThrough),
    '&:hover': {
      backgroundColor: ({ backgroundColor }) => (backgroundColor ? backgroundColor : theme.palette.primary.main),
      color: ({ foregroundColor }) => (foregroundColor ? foregroundColor : 'white'),
      fontSize: ({ fontSize }) => (fontSize ? fontSize : 'inherit'),
      fontStyle: ({ italic }) => (italic ? 'italic' : 'inherit'),
      fontWeight: ({ bold }) => (bold ? 'bold' : 'inherit'),
      textDecorationLine: ({ underline, strikeThrough }) => getTextDecorationLineValue(underline, strikeThrough),
    },
  },
  selected: {
    minWidth: '32px',
    lineHeight: 1.25,
    backgroundColor: theme.palette.secondary.light,
    color: 'white',
    fontSize: ({ fontSize }) => (fontSize ? fontSize : 'inherit'),
    fontStyle: ({ italic }) => (italic ? 'italic' : 'inherit'),
    fontWeight: ({ bold }) => (bold ? 'bold' : 'inherit'),
    textDecorationLine: ({ underline, strikeThrough }) => getTextDecorationLineValue(underline, strikeThrough),
    '&:hover': {
      backgroundColor: theme.palette.secondary.main,
      color: 'white',
      fontSize: ({ fontSize }) => (fontSize ? fontSize : 'inherit'),
      fontStyle: ({ italic }) => (italic ? 'italic' : 'inherit'),
      fontWeight: ({ bold }) => (bold ? 'bold' : 'inherit'),
      textDecorationLine: ({ underline, strikeThrough }) => getTextDecorationLineValue(underline, strikeThrough),
    },
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
  icon: {
    marginRight: ({ iconOnly }) => (iconOnly ? theme.spacing(0) : theme.spacing(2)),
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
  const props: ButtonStyleProps = {
    backgroundColor: toolbarAction.style?.backgroundColor ?? null,
    foregroundColor: toolbarAction.style?.foregroundColor ?? null,
    fontSize: toolbarAction.style?.fontSize ?? null,
    italic: toolbarAction.style?.italic ?? null,
    bold: toolbarAction.style?.bold ?? null,
    underline: toolbarAction.style?.underline ?? null,
    strikeThrough: toolbarAction.style?.strikeThrough ?? null,
    iconOnly: toolbarAction.buttonLabel ? false : true,
  };
  const classes = useStyles(props);

  const { httpOrigin }: ServerContextValue = useContext(ServerContext);

  const initialState: ToolbarActionState = {
    imageURL: toolbarAction.imageURL,
    validImage: false,
    message: null,
    selected: false,
  };
  const [state, setState] = useState<ToolbarActionState>(initialState);

  const onErrorLoadingImage = () => {
    setState((prevState) => {
      return {
        ...prevState,
        validImage: false,
      };
    });
  };

  useEffect(() => {
    let newURL: string = null;
    let validURL = true;
    if (!toolbarAction.imageURL) {
      validURL = false;
    } else if (toolbarAction.imageURL.startsWith('http://') || toolbarAction.imageURL.startsWith('https://')) {
      newURL = toolbarAction.imageURL;
    } else {
      newURL = httpOrigin + toolbarAction.imageURL;
    }
    setState((prevState) => {
      return {
        ...prevState,
        imageURL: newURL,
        validImage: validURL,
      };
    });
  }, [toolbarAction.imageURL]);

  const ref = useRef<HTMLInputElement | null>(null);

  useEffect(() => {
    if (ref.current && selection.entries.find((entry) => entry.id === toolbarAction.id)) {
      ref.current.focus();
      setState((prevState) => {
        return {
          ...prevState,
          selected: true,
        };
      });
    } else {
      setState((prevState) => {
        return {
          ...prevState,
          selected: false,
        };
      });
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
        setState((prevState) => {
          return {
            ...prevState,
            message: deleteToolbarActionError.message,
          };
        });
      }
      if (deleteToolbarActionData) {
        const { deleteToolbarAction } = deleteToolbarActionData;
        if (isErrorPayload(deleteToolbarAction)) {
          setState((prevState) => {
            return {
              ...prevState,
              message: deleteToolbarAction.message,
            };
          });
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
        setState((prevState) => {
          return {
            ...prevState,
            message: moveToolbarActionError.message,
          };
        });
      }
      if (moveToolbarActionData) {
        const { moveToolbarAction } = moveToolbarActionData;
        if (isErrorPayload(moveToolbarAction)) {
          setState((prevState) => {
            return {
              ...prevState,
              message: moveToolbarAction.message,
            };
          });
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

  const onDropBefore = (event: React.DragEvent<HTMLDivElement>, toolbarAction: GQLButton) => {
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
          kind: `siriusComponents://semantic?domain=view&entity=${toolbarAction.__typename}Description`,
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
        classes={state.selected ? { root: classes.selected } : { root: classes.style }}
        variant="contained"
        onFocus={() =>
          setState((prevState) => {
            return {
              ...prevState,
              selected: true,
            };
          })
        }
        onBlur={() =>
          setState((prevState) => {
            return {
              ...prevState,
              selected: false,
            };
          })
        }
        ref={ref}>
        {state.validImage && state.imageURL ? (
          <img
            className={classes.icon}
            width="16"
            height="16"
            alt={toolbarAction.label}
            src={state.imageURL}
            onError={onErrorLoadingImage}
          />
        ) : null}
        Lorem
      </Button>
      <Snackbar
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'right',
        }}
        open={!!state.message}
        autoHideDuration={3000}
        onClose={() =>
          setState((prevState) => {
            return {
              ...prevState,
              message: null,
            };
          })
        }
        message={state.message}
        action={
          <IconButton
            size="small"
            aria-label="close"
            color="inherit"
            onClick={() =>
              setState((prevState) => {
                return {
                  ...prevState,
                  message: null,
                };
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
