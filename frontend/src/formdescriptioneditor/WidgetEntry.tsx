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
import { makeStyles } from '@material-ui/core/styles';
import CloseIcon from '@material-ui/icons/Close';
import React, { useEffect, useState } from 'react';
import { v4 as uuid } from 'uuid';
import { Selection } from 'workbench/Workbench.types';
import { addWidgetMutation } from './FormDescriptionEditorEventFragment';
import {
  GQLAddWidgetInput,
  GQLAddWidgetMutationData,
  GQLAddWidgetMutationVariables,
  GQLAddWidgetPayload,
  GQLErrorPayload,
  GQLFormDescriptionEditorWidget,
} from './FormDescriptionEditorEventFragment.types';
import { Kind } from './FormDescriptionEditorWebSocketContainer.types';
import { TextfieldWidget } from './TextfieldWidget';
import { WidgetEntryProps, WidgetEntryState } from './WidgetEntry.types';

const useWidgetEntryStyles = makeStyles((theme) => ({
  widget: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'stretch',
  },
  placeholder: {
    height: '8px',
  },
  dragOver: {
    border: 'dashed 1px red',
  },
}));

const isErrorPayload = (payload: GQLAddWidgetPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

const isKind = (value: string): value is Kind => {
  return value === 'Textfield';
};

export const WidgetEntry = ({
  editingContextId,
  representationId,
  formDescriptionEditor,
  widget,
  selection,
  setSelection,
}: WidgetEntryProps) => {
  const classes = useWidgetEntryStyles();

  const initialState: WidgetEntryState = { message: null };
  const [state, setState] = useState<WidgetEntryState>(initialState);
  const { message } = state;

  const [addWidget, { loading: addWidgetLoading, data: addWidgetData, error: addWidgetError }] = useMutation<
    GQLAddWidgetMutationData,
    GQLAddWidgetMutationVariables
  >(addWidgetMutation);

  useEffect(() => {
    if (!addWidgetLoading) {
      if (addWidgetError) {
        setState((prevState) => {
          const newState = { ...prevState };
          newState.message = addWidgetError.message;
          return newState;
        });
      }
      if (addWidgetData) {
        const { addWidget } = addWidgetData;
        if (isErrorPayload(addWidget)) {
          setState((prevState) => {
            const newState = { ...prevState };
            newState.message = addWidget.message;
            return newState;
          });
        }
      }
    }
  }, [addWidgetLoading, addWidgetData, addWidgetError]);

  const handleClick: React.MouseEventHandler<HTMLDivElement> = (event) => {
    const newSelection: Selection = {
      entries: [
        {
          id: widget.id,
          label: widget.label,
          kind: `siriusComponents://semantic?domain=view&entity=${widget.kind}Description`,
        },
      ],
    };
    setSelection(newSelection);
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
    event.currentTarget.classList.remove(classes.dragOver);
  };
  const handleDrop: React.DragEventHandler<HTMLDivElement> = (event) => {
    event.currentTarget.classList.remove(classes.dragOver);
    onDropBefore(event, widget);
  };

  const onDropBefore = (event: React.DragEvent<HTMLDivElement>, widget: GQLFormDescriptionEditorWidget) => {
    const id: string = event.dataTransfer.getData('text/plain');

    const existingWidgets: GQLFormDescriptionEditorWidget[] = formDescriptionEditor.widgets;
    let index: number = existingWidgets.indexOf(widget);
    if (index <= 0) {
      index = 0;
    }

    const addWidgetInput: GQLAddWidgetInput = {
      id: uuid(),
      editingContextId,
      representationId,
      kind: isKind(id) ? id : 'Textfield',
      index,
    };
    const addWidgetVariables: GQLAddWidgetMutationVariables = { input: addWidgetInput };
    addWidget({ variables: addWidgetVariables });
  };

  let widgetElement = null;
  if (widget.kind === 'Textfield') {
    widgetElement = (
      <TextfieldWidget
        data-testid={widget.id}
        widget={widget}
        selection={selection}
        setSelection={setSelection}
        onDropBefore={onDropBefore}
      />
    );
  }
  return (
    <div className={classes.widget} onClick={handleClick}>
      <div
        data-testid="WidgetEntry-DropArea"
        className={classes.placeholder}
        onDragEnter={handleDragEnter}
        onDragOver={handleDragOver}
        onDragLeave={handleDragLeave}
        onDrop={handleDrop}
      />
      {widgetElement}
      <Snackbar
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'right',
        }}
        open={!!message}
        autoHideDuration={3000}
        onClose={() => setState({ message: null })}
        message={message}
        action={
          <IconButton size="small" aria-label="close" color="inherit" onClick={() => setState({ message: null })}>
            <CloseIcon fontSize="small" />
          </IconButton>
        }
        data-testid="error"
      />
    </div>
  );
};
