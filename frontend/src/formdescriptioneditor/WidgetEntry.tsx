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
import React from 'react';
import { Selection } from 'workbench/Workbench.types';
import { TextfieldWidget } from './TextfieldWidget';
import { WidgetEntryProps } from './WidgetEntry.types';

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

export const WidgetEntry = ({ widget, selection, setSelection, onDropBefore }: WidgetEntryProps) => {
  const classes = useWidgetEntryStyles();

  const handleClick: React.MouseEventHandler<HTMLDivElement> = (event) => {
    const newSelection: Selection = {
      entries: [
        {
          id: widget.id,
          label: widget.label,
          kind: `siriusComponents://graphical?representationType=FormDescriptionEditor&type=${widget.kind}`,
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

  let widgetElement = null;
  if (widget.kind === 'Textfield') {
    widgetElement = (
      <TextfieldWidget widget={widget} selection={selection} setSelection={setSelection} onDropBefore={onDropBefore} />
    );
  }
  return (
    <div className={classes.widget} onClick={handleClick}>
      <div
        className={classes.placeholder}
        onDragEnter={handleDragEnter}
        onDragOver={handleDragOver}
        onDragLeave={handleDragLeave}
        onDrop={handleDrop}
      />
      {widgetElement}
    </div>
  );
};
