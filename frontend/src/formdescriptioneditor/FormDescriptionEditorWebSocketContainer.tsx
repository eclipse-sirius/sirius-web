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
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import TextFieldsIcon from '@material-ui/icons/TextFields';
import { useMachine } from '@xstate/react';
import { Kind, Widget } from 'formdescriptioneditor/FormDescriptionEditorWebSocketContainer.types';
import {
  FormDescriptionEditorWebSocketContainerContext,
  FormDescriptionEditorWebSocketContainerEvent,
  formDescriptionEditorWebSocketContainerMachine,
  InitializeRepresentationEvent,
  SchemaValue,
  UpdateWidgetsEvent,
} from 'formdescriptioneditor/FormDescriptionEditorWebSocketContainerMachine';
import { WidgetEntry } from 'formdescriptioneditor/WidgetEntry';
import React, { useEffect } from 'react';
import { v4 as uuid } from 'uuid';
import { RepresentationComponentProps } from 'workbench/Workbench.types';

const useFormDescriptionEditorStyles = makeStyles((theme) => ({
  formDescriptionEditor: {
    display: 'flex',
    flexDirection: 'column',

    width: '100%',
  },
  header: {
    padding: '4px 8px 4px 8px',
  },
  main: {
    display: 'flex',
    flexDirection: 'row',
    height: '100%',
    borderTop: '1px solid grey',
  },
  widgets: {
    display: 'flex',
    flexDirection: 'column',
    borderRight: '1px solid grey',
    padding: '4px 8px 4px 8px',
  },
  widgetKind: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    marginTop: '8px',
    marginBottom: '8px',
    cursor: 'move',
  },
  preview: {
    width: '100%',
    padding: '4px 8px 4px 8px',
  },
  body: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'stretch',
  },
  bottomDropArea: {
    height: '100px',
  },
  dragOver: {
    border: 'dashed 1px red',
  },
}));

const isKind = (value: string): value is Kind => {
  return value === 'Textfield';
};

export const FormDescriptionEditorWebSocketContainer = ({ selection, setSelection }: RepresentationComponentProps) => {
  const classes = useFormDescriptionEditorStyles();

  const [{ value, context }, dispatch] = useMachine<
    FormDescriptionEditorWebSocketContainerContext,
    FormDescriptionEditorWebSocketContainerEvent
  >(formDescriptionEditorWebSocketContainerMachine);
  const { formDescriptionEditorWebSocketContainer } = value as SchemaValue;
  const { widgets } = context;

  useEffect(() => {
    if (formDescriptionEditorWebSocketContainer === 'loading') {
      const initializeRepresentationEvent: InitializeRepresentationEvent = {
        type: 'INITIALIZE',
        widgets,
      };
      dispatch(initializeRepresentationEvent);
    }
  }, [formDescriptionEditorWebSocketContainer, dispatch, widgets]);

  const handleDragStart: React.DragEventHandler<HTMLDivElement> = (event) => {
    event.dataTransfer.setData('text/plain', event.currentTarget.id);
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

    const id = event.dataTransfer.getData('text/plain');
    const newWidget: Widget = {
      id: uuid(),
      label: id,
      kind: isKind(id) ? id : 'Textfield',
    };
    const updatedWidgets = widgets;
    updatedWidgets.push(newWidget);
    const updateWidgetsEvent: UpdateWidgetsEvent = {
      type: 'UPDATE_WIDGETS',
      widgets: updatedWidgets,
    };
    dispatch(updateWidgetsEvent);
  };
  const handleDropBefore = (event: React.DragEvent<HTMLDivElement>, widget: Widget) => {
    const id = event.dataTransfer.getData('text/plain');
    const newWidget: Widget = {
      id: uuid(),
      label: id,
      kind: isKind(id) ? id : 'Textfield',
    };
    const updatedWidgets = widgets;
    let index = updatedWidgets.indexOf(widget);
    if (index === -1) {
      updatedWidgets.push(newWidget);
    } else {
      updatedWidgets.splice(index, 0, newWidget);
    }

    const updateWidgetsEvent: UpdateWidgetsEvent = {
      type: 'UPDATE_WIDGETS',
      widgets: updatedWidgets,
    };
    dispatch(updateWidgetsEvent);
  };

  return (
    <div className={classes.formDescriptionEditor}>
      <div className={classes.header}>
        <Typography>Form</Typography>
      </div>
      <div className={classes.main}>
        <div className={classes.widgets}>
          <Typography gutterBottom>Widgets</Typography>
          <div
            id="Textfield"
            data-testid="FormDescriptionEditor-Textfield"
            draggable="true"
            className={classes.widgetKind}
            onDragStart={handleDragStart}
          >
            <TextFieldsIcon />
            <Typography variant="caption" gutterBottom>
              Textfield
            </Typography>
          </div>
        </div>
        <div className={classes.preview}>
          <Typography>Preview</Typography>
          <div className={classes.body}>
            {widgets.map((widget) => (
              <WidgetEntry
                key={widget.id}
                widget={widget}
                selection={selection}
                setSelection={setSelection}
                onDropBefore={handleDropBefore}
              />
            ))}
            <div
              data-testid="FormDescriptionEditor-DropArea"
              className={classes.bottomDropArea}
              onDragEnter={handleDragEnter}
              onDragOver={handleDragOver}
              onDragLeave={handleDragLeave}
              onDrop={handleDrop}
            />
          </div>
        </div>
      </div>
    </div>
  );
};
