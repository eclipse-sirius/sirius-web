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
import IconButton from '@material-ui/core/IconButton';
import Snackbar from '@material-ui/core/Snackbar';
import { makeStyles, Theme } from '@material-ui/core/styles';
import CloseIcon from '@material-ui/icons/Close';
import React, { useEffect, useState } from 'react';
import { v4 as uuid } from 'uuid';
import { BarChartWidget } from './BarChartWidget';
import { CheckboxWidget } from './CheckboxWidget';
import { FlexboxContainerWidget } from './FlexboxContainerWidget';
import { addWidgetMutation, deleteWidgetMutation, moveWidgetMutation } from './FormDescriptionEditorEventFragment';
import {
  GQLAddWidgetInput,
  GQLAddWidgetMutationData,
  GQLAddWidgetMutationVariables,
  GQLAddWidgetPayload,
  GQLDeleteWidgetInput,
  GQLDeleteWidgetMutationData,
  GQLDeleteWidgetMutationVariables,
  GQLDeleteWidgetPayload,
  GQLErrorPayload,
  GQLFormDescriptionEditorFlexboxContainer,
  GQLFormDescriptionEditorWidget,
  GQLMoveWidgetInput,
  GQLMoveWidgetMutationData,
  GQLMoveWidgetMutationVariables,
  GQLMoveWidgetPayload,
} from './FormDescriptionEditorEventFragment.types';
import { MultiSelectWidget } from './MultiSelectWidget';
import { PieChartWidget } from './PieChartWidget';
import { RadioWidget } from './RadioWidget';
import { SelectWidget } from './SelectWidget';
import { TextAreaWidget } from './TextAreaWidget';
import { TextfieldWidget } from './TextfieldWidget';
import { WidgetEntryProps, WidgetEntryState, WidgetEntryStyleProps } from './WidgetEntry.types';
import { isKind } from './WidgetOperations';
import { ButtonWidget } from './ButtonWidget';

const useWidgetEntryStyles = makeStyles<Theme, WidgetEntryStyleProps>(() => ({
  widget: {
    display: 'flex',
    flexDirection: ({ flexDirection }) => flexDirection,
    flexGrow: ({ flexGrow }) => flexGrow,
  },
  widgetElement: {
    flexGrow: ({ flexGrow }) => flexGrow,
  },
  placeholder: {
    height: ({ flexDirection }) =>
      flexDirection === 'column' || flexDirection === 'column-reverse' ? '10px' : 'inherit',
    width: ({ flexDirection }) => (flexDirection === 'row' || flexDirection === 'row-reverse' ? '10px' : 'inherit'),
  },
  dragOver: {
    border: 'dashed 1px red',
  },
}));

const isErrorPayload = (
  payload: GQLAddWidgetPayload | GQLDeleteWidgetPayload | GQLMoveWidgetPayload
): payload is GQLErrorPayload => payload.__typename === 'ErrorPayload';

export const WidgetEntry = ({
  editingContextId,
  representationId,
  containerId,
  siblings,
  widget,
  selection,
  setSelection,
  flexDirection,
  flexGrow,
}: WidgetEntryProps) => {
  const classes = useWidgetEntryStyles({ flexDirection, flexGrow });

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

  const [deleteWidget, { loading: deleteWidgetLoading, data: deleteWidgetData, error: deleteWidgetError }] =
    useMutation<GQLDeleteWidgetMutationData, GQLDeleteWidgetMutationVariables>(deleteWidgetMutation);

  useEffect(() => {
    if (!deleteWidgetLoading) {
      if (deleteWidgetError) {
        setState((prevState) => {
          return { ...prevState, message: deleteWidgetError.message };
        });
      }
      if (deleteWidgetData) {
        const { deleteWidget } = deleteWidgetData;
        if (isErrorPayload(deleteWidget)) {
          setState((prevState) => {
            return { ...prevState, message: deleteWidget.message };
          });
        }
      }
    }
  }, [deleteWidgetLoading, deleteWidgetData, deleteWidgetError]);

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
    event.stopPropagation();
  };

  const handleDelete: React.KeyboardEventHandler<HTMLDivElement> = (event) => {
    event.preventDefault();
    if (event.key === 'Delete') {
      const deleteWidgetInput: GQLDeleteWidgetInput = {
        id: uuid(),
        editingContextId,
        representationId,
        widgetId: widget.id,
      };
      const deleteWidgetVariables: GQLDeleteWidgetMutationVariables = { input: deleteWidgetInput };
      deleteWidget({ variables: deleteWidgetVariables });
      event.stopPropagation();
    }
  };

  const handleDragStart: React.DragEventHandler<HTMLDivElement> = (event) => {
    event.dataTransfer.setData('text/plain', widget.id);
    event.stopPropagation();
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
    onDropBefore(event, widget);
  };

  const onDropBefore = (event: React.DragEvent<HTMLDivElement>, widget: GQLFormDescriptionEditorWidget) => {
    const id: string = event.dataTransfer.getData('text/plain');

    let index: number = siblings.indexOf(widget);
    if (index <= 0) {
      index = 0;
    }

    if (isKind(id)) {
      const addWidgetInput: GQLAddWidgetInput = {
        id: uuid(),
        editingContextId,
        representationId,
        containerId,
        kind: id,
        index,
      };
      const addWidgetVariables: GQLAddWidgetMutationVariables = { input: addWidgetInput };
      addWidget({ variables: addWidgetVariables });
    } else {
      const movedWidgetIndex = siblings.findIndex((w) => w.id === id);
      if (movedWidgetIndex > -1 && movedWidgetIndex < index) {
        index--;
      }
      const moveWidgetInput: GQLMoveWidgetInput = {
        id: uuid(),
        editingContextId,
        representationId,
        containerId,
        widgetId: id,
        index,
      };
      const moveWidgetVariables: GQLMoveWidgetMutationVariables = { input: moveWidgetInput };
      moveWidget({ variables: moveWidgetVariables });
    }
  };

  let widgetElement: JSX.Element | null = null;
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
  } else if (widget.kind === 'TextArea') {
    widgetElement = (
      <TextAreaWidget
        data-testid={widget.id}
        widget={widget}
        selection={selection}
        setSelection={setSelection}
        onDropBefore={onDropBefore}
      />
    );
  } else if (widget.kind === 'Checkbox') {
    widgetElement = (
      <CheckboxWidget
        data-testid={widget.id}
        widget={widget}
        selection={selection}
        setSelection={setSelection}
        onDropBefore={onDropBefore}
      />
    );
  } else if (widget.kind === 'Radio') {
    widgetElement = (
      <RadioWidget
        data-testid={widget.id}
        widget={widget}
        selection={selection}
        setSelection={setSelection}
        onDropBefore={onDropBefore}
      />
    );
  } else if (widget.kind === 'Select') {
    widgetElement = (
      <SelectWidget
        data-testid={widget.id}
        widget={widget}
        selection={selection}
        setSelection={setSelection}
        onDropBefore={onDropBefore}
      />
    );
  } else if (widget.kind === 'MultiSelect') {
    widgetElement = (
      <MultiSelectWidget
        data-testid={widget.id}
        widget={widget}
        selection={selection}
        setSelection={setSelection}
        onDropBefore={onDropBefore}
      />
    );
  } else if (widget.kind === 'Button') {
    widgetElement = (
      <ButtonWidget
        data-testid={widget.id}
        widget={widget}
        selection={selection}
        setSelection={setSelection}
        onDropBefore={onDropBefore}
      />
    );
  } else if (widget.kind === 'BarChart') {
    widgetElement = (
      <BarChartWidget
        data-testid={widget.id}
        widget={widget}
        selection={selection}
        setSelection={setSelection}
        onDropBefore={onDropBefore}
      />
    );
  } else if (widget.kind === 'PieChart') {
    widgetElement = (
      <PieChartWidget
        data-testid={widget.id}
        widget={widget}
        selection={selection}
        setSelection={setSelection}
        onDropBefore={onDropBefore}
      />
    );
  } else if (widget.kind === 'FlexboxContainer') {
    widgetElement = (
      <FlexboxContainerWidget
        data-testid={widget.id}
        editingContextId={editingContextId}
        representationId={representationId}
        widget={widget as GQLFormDescriptionEditorFlexboxContainer}
        selection={selection}
        setSelection={setSelection}
      />
    );
  }
  return (
    <div
      className={classes.widget}
      onClick={handleClick}
      onKeyDown={handleDelete}
      draggable="true"
      onDragStart={handleDragStart}
    >
      <div
        data-testid="WidgetEntry-DropArea"
        className={classes.placeholder}
        onDragEnter={handleDragEnter}
        onDragOver={handleDragOver}
        onDragLeave={handleDragLeave}
        onDrop={handleDrop}
      />
      <div className={classes.widgetElement}>{widgetElement}</div>
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
