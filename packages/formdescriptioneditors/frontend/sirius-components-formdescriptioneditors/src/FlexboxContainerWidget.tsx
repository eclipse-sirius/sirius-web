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
import IconButton from '@material-ui/core/IconButton';
import Snackbar from '@material-ui/core/Snackbar';
import { makeStyles, Theme } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import CloseIcon from '@material-ui/icons/Close';
import React, { useEffect, useRef, useState } from 'react';
import { v4 as uuid } from 'uuid';
import { FlexboxContainerWidgetState, FlexboxContainerWidgetStyleProps } from './FlexboxContainerWidget.types';
import { addWidgetMutation, moveWidgetMutation } from './FormDescriptionEditorEventFragment';
import {
  GQLAddWidgetInput,
  GQLAddWidgetMutationData,
  GQLAddWidgetMutationVariables,
  GQLAddWidgetPayload,
  GQLErrorPayload,
  GQLMoveWidgetInput,
  GQLMoveWidgetMutationData,
  GQLMoveWidgetMutationVariables,
  GQLMoveWidgetPayload,
} from './FormDescriptionEditorEventFragment.types';
import { WidgetEntry } from './WidgetEntry';
import { FlexboxContainerWidgetProps } from './WidgetEntry.types';
import { isKind } from './WidgetOperations';

const isErrorPayload = (payload: GQLAddWidgetPayload | GQLMoveWidgetPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

const useStyles = makeStyles<Theme, FlexboxContainerWidgetStyleProps>((theme) => ({
  selected: {
    color: theme.palette.primary.main,
  },
  widget: {
    border: 'solid 1px lightgrey',
    padding: '0px 0px 0px 4px',
    width: '100%',
  },
  container: {
    display: 'flex',
    flexWrap: ({ flexWrap }) => flexWrap,
    flexDirection: ({ flexDirection }) => flexDirection,
    '& > *': {
      marginBottom: theme.spacing(0),
    },
  },
  bottomDropArea: {
    height: '30px',
    width: '100%',
  },
  dragOver: {
    border: 'dashed 1px red',
  },
}));

export const FlexboxContainerWidget = ({
  editingContextId,
  representationId,
  toolbarActions,
  widget,
  selection,
  setSelection,
}: FlexboxContainerWidgetProps) => {
  const classes = useStyles({
    flexDirection: widget.flexDirection,
    flexWrap: widget.flexWrap,
  });

  const initialState: FlexboxContainerWidgetState = { message: null, selected: false };
  const [state, setState] = useState<FlexboxContainerWidgetState>(initialState);
  const { message, selected } = state;

  const [addWidget, { loading: addWidgetLoading, data: addWidgetData, error: addWidgetError }] = useMutation<
    GQLAddWidgetMutationData,
    GQLAddWidgetMutationVariables
  >(addWidgetMutation);

  useEffect(() => {
    if (!addWidgetLoading) {
      if (addWidgetError) {
        setState((prevState) => {
          return { ...prevState, message: addWidgetError.message };
        });
      }
      if (addWidgetData) {
        const { addWidget } = addWidgetData;
        if (isErrorPayload(addWidget)) {
          setState((prevState) => {
            return { ...prevState, message: addWidget.message };
          });
        }
      }
    }
  }, [addWidgetLoading, addWidgetData, addWidgetError]);

  const ref = useRef<HTMLDivElement | null>(null);

  useEffect(() => {
    if (ref.current && selection.entries.find((entry) => entry.id === widget.id)) {
      ref.current.focus();
      setState((prevState) => {
        return { ...prevState, selected: true };
      });
    } else {
      setState((prevState) => {
        return { ...prevState, selected: false };
      });
    }
  }, [selection, widget]);

  const [moveWidget, { loading: moveWidgetLoading, data: moveWidgetData, error: moveWidgetError }] = useMutation<
    GQLMoveWidgetMutationData,
    GQLMoveWidgetMutationVariables
  >(moveWidgetMutation);

  useEffect(() => {
    if (!moveWidgetLoading) {
      if (moveWidgetError) {
        setState((prevState) => {
          return { ...prevState, message: moveWidgetError.message };
        });
      }
      if (moveWidgetData) {
        const { moveWidget } = moveWidgetData;
        if (isErrorPayload(moveWidget)) {
          setState((prevState) => {
            return { ...prevState, message: moveWidget.message };
          });
        }
      }
    }
  }, [moveWidgetLoading, moveWidgetData, moveWidgetError]);

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
    let index = widget.children.length;

    if (isKind(id)) {
      const addWidgetInput: GQLAddWidgetInput = {
        id: uuid(),
        editingContextId,
        representationId,
        containerId: widget.id,
        kind: id,
        index,
      };
      const addWidgetVariables: GQLAddWidgetMutationVariables = { input: addWidgetInput };
      addWidget({ variables: addWidgetVariables });
    } else {
      if (widget.children.find((w) => w.id === id)) {
        index--;
      }
      const moveWidgetInput: GQLMoveWidgetInput = {
        id: uuid(),
        editingContextId,
        representationId,
        containerId: widget.id,
        widgetId: id,
        index,
      };
      const moveWidgetVariables: GQLMoveWidgetMutationVariables = { input: moveWidgetInput };
      moveWidget({ variables: moveWidgetVariables });
    }
  };

  let children = widget.children.map((childWidget) => {
    return (
      <WidgetEntry
        key={childWidget.id}
        editingContextId={editingContextId}
        representationId={representationId}
        containerId={widget.id}
        toolbarActions={toolbarActions}
        siblings={widget.children}
        widget={childWidget}
        selection={selection}
        setSelection={setSelection}
        flexDirection={widget.flexDirection}
        flexGrow={widget.flexGrow}
      />
    );
  });

  return (
    <div className={classes.widget} tabIndex={0} ref={ref}>
      <Typography
        variant="subtitle2"
        className={selected ? classes.selected : ''}
        onFocus={() =>
          setState((prevState) => {
            return { ...prevState, selected: true };
          })
        }
        onBlur={() =>
          setState((prevState) => {
            return { ...prevState, selected: false };
          })
        }>
        {widget.label}
      </Typography>
      <div className={classes.container}>{children}</div>
      <div
        data-testid={`${widget.id}-DropArea`}
        className={classes.bottomDropArea}
        onDragEnter={handleDragEnter}
        onDragOver={handleDragOver}
        onDragLeave={handleDragLeave}
        onDrop={handleDrop}
      />
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
