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
import { Selection, Toast } from '@eclipse-sirius/sirius-components-core';
import {
  GQLButton,
  GQLChartWidget,
  GQLCheckbox,
  GQLFlexboxContainer,
  GQLGroup,
  GQLImage,
  GQLLabelWidget,
  GQLLink,
  GQLList,
  GQLMultiSelect,
  GQLRadio,
  GQLRichText,
  GQLSelect,
  GQLTextarea,
  GQLTextfield,
  GQLWidget,
  PropertySectionContext,
} from '@eclipse-sirius/sirius-components-forms';
import { makeStyles, Theme, withStyles } from '@material-ui/core/styles';
import Tooltip from '@material-ui/core/Tooltip';
import React, { useContext, useEffect, useState } from 'react';
import { BarChartWidget } from './BarChartWidget';
import { ButtonWidget } from './ButtonWidget';
import { CheckboxWidget } from './CheckboxWidget';
import { CustomWidget } from './CustomWidget';
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
  GQLMoveWidgetInput,
  GQLMoveWidgetMutationData,
  GQLMoveWidgetMutationVariables,
  GQLMoveWidgetPayload,
} from './FormDescriptionEditorEventFragment.types';
import { ImageWidget } from './ImageWidget';
import { LabelWidget } from './LabelWidget';
import { LinkWidget } from './LinkWidget';
import { ListWidget } from './ListWidget';
import { MultiSelectWidget } from './MultiSelectWidget';
import { PieChartWidget } from './PieChartWidget';
import { RadioWidget } from './RadioWidget';
import { RichTextWidget } from './RichTextWidget';
import { SelectWidget } from './SelectWidget';
import { TextAreaWidget } from './TextAreaWidget';
import { TextfieldWidget } from './TextfieldWidget';
import { WidgetEntryProps, WidgetEntryState, WidgetEntryStyleProps } from './WidgetEntry.types';
import { isFlexboxContainer, isGroup, isKind } from './WidgetOperations';

const useWidgetEntryStyles = makeStyles<Theme, WidgetEntryStyleProps>((theme) => ({
  widget: {
    display: 'flex',
    flexDirection: ({ flexDirection }) => flexDirection,
    flexGrow: ({ flexGrow }) => flexGrow,
  },
  widgetElement: {
    flexGrow: ({ flexGrow }) => flexGrow,
    border: ({ kind }) => (kind === 'FlexboxContainer' ? '1px solid gray' : '1px solid transparent'),
    '&:hover': {
      borderColor: theme.palette.primary.main,
    },
    '&:has($widgetElement:hover)': {
      borderStyle: 'dashed',
    },
  },
  placeholder: {
    height: ({ flexDirection }) =>
      flexDirection === 'column' || flexDirection === 'column-reverse' ? '10px' : 'inherit',
    width: ({ flexDirection }) => (flexDirection === 'row' || flexDirection === 'row-reverse' ? '10px' : 'inherit'),
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: 'whitesmoke',
    border: '1px solid whitesmoke',
    borderRadius: '5px',
  },
  dragOver: {
    borderWidth: '1px',
    borderStyle: 'dashed',
    borderColor: theme.palette.primary.main,
  },
}));

const WidgetTooltip = withStyles((theme: Theme) => ({
  tooltip: {
    backgroundColor: theme.palette.primary.main,
    margin: '0px',
    borderRadius: '0px',
  },
}))(Tooltip);

const isErrorPayload = (
  payload: GQLAddWidgetPayload | GQLDeleteWidgetPayload | GQLMoveWidgetPayload
): payload is GQLErrorPayload => payload.__typename === 'ErrorPayload';

export const WidgetEntry = ({
  editingContextId,
  representationId,
  formDescriptionEditor,
  page,
  container,
  widget,
  selection,
  setSelection,
  flexDirection,
  flexGrow,
}: WidgetEntryProps) => {
  const classes = useWidgetEntryStyles({ flexDirection, flexGrow, kind: widget.__typename });

  const initialState: WidgetEntryState = { message: null };
  const [state, setState] = useState<WidgetEntryState>(initialState);
  const { message } = state;

  const { propertySectionsRegistry } = useContext(PropertySectionContext);

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
          kind: `siriusComponents://semantic?domain=view&entity=${widget.__typename}Description`,
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
        id: crypto.randomUUID(),
        editingContextId,
        representationId,
        widgetId: widget.id,
      };
      const deleteWidgetVariables: GQLDeleteWidgetMutationVariables = { input: deleteWidgetInput };
      deleteWidget({ variables: deleteWidgetVariables });
      event.stopPropagation();
    }
  };

  const handleDragStart: React.DragEventHandler<HTMLDivElement> = (event: React.DragEvent<HTMLDivElement>) => {
    event.dataTransfer.setData('draggedElementId', widget.id);
    event.dataTransfer.setData('draggedElementType', 'Widget');
    event.stopPropagation();
  };
  const handleDragEnter: React.DragEventHandler<HTMLDivElement> = (event: React.DragEvent<HTMLDivElement>) => {
    event.preventDefault();
    event.currentTarget.classList.add(classes.dragOver);
  };
  const handleDragOver: React.DragEventHandler<HTMLDivElement> = (event: React.DragEvent<HTMLDivElement>) => {
    event.preventDefault();
    event.currentTarget.classList.add(classes.dragOver);
  };
  const handleDragLeave: React.DragEventHandler<HTMLDivElement> = (event: React.DragEvent<HTMLDivElement>) => {
    event.preventDefault();
    event.currentTarget.classList.remove(classes.dragOver);
  };
  const handleDrop: React.DragEventHandler<HTMLDivElement> = (event: React.DragEvent<HTMLDivElement>) => {
    event.preventDefault();
    event.currentTarget.classList.remove(classes.dragOver);
    onDropBefore(event, widget);
  };

  const onDropBefore = (event: React.DragEvent<HTMLDivElement>, widget: GQLWidget) => {
    const id: string = event.dataTransfer.getData('draggedElementId');
    const type: string = event.dataTransfer.getData('draggedElementType');

    if (type !== 'Widget') {
      return;
    }

    let children: GQLWidget[] = null;
    if (isGroup(container)) {
      children = (container as GQLGroup).widgets;
    } else if (isFlexboxContainer(container)) {
      children = (container as GQLFlexboxContainer).children;
    }

    let index: number = children.indexOf(widget);
    if (index <= 0) {
      index = 0;
    }

    if (isKind(id) || propertySectionsRegistry.getWidgetContributions().find((contrib) => contrib.name === id)) {
      const addWidgetInput: GQLAddWidgetInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId,
        containerId: container.id,
        kind: id,
        index,
      };
      const addWidgetVariables: GQLAddWidgetMutationVariables = { input: addWidgetInput };
      addWidget({ variables: addWidgetVariables });
    } else {
      const movedWidgetIndex = children.findIndex((w: GQLWidget) => w.id === id);
      if (movedWidgetIndex > -1 && movedWidgetIndex < index) {
        index--;
      }
      const moveWidgetInput: GQLMoveWidgetInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId,
        containerId: container.id,
        widgetId: id,
        index,
      };
      const moveWidgetVariables: GQLMoveWidgetMutationVariables = { input: moveWidgetInput };
      moveWidget({ variables: moveWidgetVariables });
    }
  };

  let widgetElement: JSX.Element | null = null;
  if (widget.__typename === 'Button') {
    widgetElement = (
      <ButtonWidget
        data-testid={widget.id}
        widget={widget as GQLButton}
        selection={selection}
        setSelection={setSelection}
        onDropBefore={onDropBefore}
      />
    );
  } else if (widget.__typename === 'Checkbox') {
    widgetElement = (
      <CheckboxWidget
        data-testid={widget.id}
        widget={widget as GQLCheckbox}
        selection={selection}
        setSelection={setSelection}
        onDropBefore={onDropBefore}
      />
    );
  } else if (widget.__typename === 'FlexboxContainer') {
    widgetElement = (
      <FlexboxContainerWidget
        data-testid={widget.id}
        editingContextId={editingContextId}
        representationId={representationId}
        formDescriptionEditor={formDescriptionEditor}
        page={page}
        container={container}
        widget={widget as GQLFlexboxContainer}
        selection={selection}
        setSelection={setSelection}
      />
    );
  } else if (widget.__typename === 'Image') {
    widgetElement = (
      <ImageWidget
        data-testid={widget.id}
        widget={widget as GQLImage}
        selection={selection}
        setSelection={setSelection}
        onDropBefore={onDropBefore}
      />
    );
  } else if (widget.__typename === 'LabelWidget') {
    widgetElement = (
      <LabelWidget
        data-testid={widget.id}
        widget={widget as GQLLabelWidget}
        selection={selection}
        setSelection={setSelection}
        onDropBefore={onDropBefore}
      />
    );
  } else if (widget.__typename === 'Link') {
    widgetElement = (
      <LinkWidget
        data-testid={widget.id}
        widget={widget as GQLLink}
        selection={selection}
        setSelection={setSelection}
        onDropBefore={onDropBefore}
      />
    );
  } else if (widget.__typename === 'List') {
    widgetElement = (
      <ListWidget
        data-testid={widget.id}
        widget={widget as GQLList}
        selection={selection}
        setSelection={setSelection}
        onDropBefore={onDropBefore}
      />
    );
  } else if (widget.__typename === 'MultiSelect') {
    widgetElement = (
      <MultiSelectWidget
        data-testid={widget.id}
        widget={widget as GQLMultiSelect}
        selection={selection}
        setSelection={setSelection}
        onDropBefore={onDropBefore}
      />
    );
  } else if (widget.__typename === 'Radio') {
    widgetElement = (
      <RadioWidget
        data-testid={widget.id}
        widget={widget as GQLRadio}
        selection={selection}
        setSelection={setSelection}
        onDropBefore={onDropBefore}
      />
    );
  } else if (widget.__typename === 'RichText') {
    widgetElement = (
      <RichTextWidget
        data-testid={widget.id}
        widget={widget as GQLRichText}
        selection={selection}
        setSelection={setSelection}
        onDropBefore={onDropBefore}
      />
    );
  } else if (widget.__typename === 'Select') {
    widgetElement = (
      <SelectWidget
        data-testid={widget.id}
        widget={widget as GQLSelect}
        selection={selection}
        setSelection={setSelection}
        onDropBefore={onDropBefore}
      />
    );
  } else if (widget.__typename === 'Textarea') {
    widgetElement = (
      <TextAreaWidget
        data-testid={widget.id}
        widget={widget as GQLTextarea}
        selection={selection}
        setSelection={setSelection}
        onDropBefore={onDropBefore}
      />
    );
  } else if (widget.__typename === 'Textfield') {
    widgetElement = (
      <TextfieldWidget
        data-testid={widget.id}
        widget={widget as GQLTextfield}
        selection={selection}
        setSelection={setSelection}
        onDropBefore={onDropBefore}
      />
    );
  } else if (widget.__typename === 'ChartWidget') {
    const chartWidget: GQLChartWidget = widget as GQLChartWidget;
    if (chartWidget.chart.metadata.kind === 'BarChart') {
      widgetElement = (
        <BarChartWidget
          data-testid={widget.id}
          widget={widget as GQLChartWidget}
          selection={selection}
          setSelection={setSelection}
          onDropBefore={onDropBefore}
        />
      );
    } else if (chartWidget.chart.metadata.kind === 'PieChart') {
      widgetElement = (
        <PieChartWidget
          data-testid={widget.id}
          widget={widget as GQLChartWidget}
          selection={selection}
          setSelection={setSelection}
          onDropBefore={onDropBefore}
        />
      );
    }
  } else {
    const PreviewComponent = propertySectionsRegistry.getPreviewComponent(widget);
    if (PreviewComponent) {
      widgetElement = (
        <PreviewComponent
          data-testid={widget.id}
          widget={widget as GQLWidget}
          selection={selection}
          setSelection={setSelection}
          onDropBefore={onDropBefore}
        />
      );
    } else if (propertySectionsRegistry.getComponent(widget)) {
      widgetElement = (
        <CustomWidget
          data-testid={widget.id}
          widget={widget as GQLWidget}
          selection={selection}
          setSelection={setSelection}
          onDropBefore={onDropBefore}
        />
      );
    } else {
      console.error(`Unsupported widget type ${widget.__typename}`);
    }
  }

  return (
    <div className={classes.widget}>
      <div
        data-testid={`WidgetEntry-DropArea-${widget.id}`}
        className={classes.placeholder}
        onDragEnter={handleDragEnter}
        onDragOver={handleDragOver}
        onDragLeave={handleDragLeave}
        onDrop={handleDrop}
      />
      <WidgetTooltip title={widget.__typename} placement="top-end">
        <div
          className={classes.widgetElement}
          onClick={handleClick}
          onKeyDown={handleDelete}
          draggable="true"
          onDragStart={handleDragStart}>
          {widgetElement}
        </div>
      </WidgetTooltip>
      <Toast message={message} open={!!message} onClose={() => setState({ message: null })} />
    </div>
  );
};
