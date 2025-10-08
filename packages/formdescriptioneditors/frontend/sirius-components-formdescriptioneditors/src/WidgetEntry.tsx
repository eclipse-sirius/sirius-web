/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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
import {
  Selection,
  Toast,
  useData,
  useDeletionConfirmationDialog,
  useSelection,
} from '@eclipse-sirius/sirius-components-core';
import {
  GQLButton,
  GQLChartWidget,
  GQLCheckbox,
  GQLContainer,
  GQLDateTime,
  GQLFlexDirection,
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
  GQLSlider,
  GQLSplitButton,
  GQLTextarea,
  GQLTextfield,
  GQLTree,
  GQLWidget,
  widgetContributionExtensionPoint,
} from '@eclipse-sirius/sirius-components-forms';
import Tooltip from '@mui/material/Tooltip';
import { Theme } from '@mui/material/styles';
import React, { useEffect, useState } from 'react';
import { makeStyles, withStyles } from 'tss-react/mui';
import { BarChartWidget } from './BarChartWidget';
import { ButtonWidget } from './ButtonWidget';
import { CheckboxWidget } from './CheckboxWidget';
import { CustomWidget } from './CustomWidget';
import { DateTimeWidget } from './DateTimeWidget';
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
import { SliderWidget } from './SliderWidget';
import { SplitButtonWidget } from './SplitButtonWidget';
import { TextAreaWidget } from './TextAreaWidget';
import { TextfieldWidget } from './TextfieldWidget';
import { TreeWidget } from './TreeWidget';
import { WidgetEntryProps, WidgetEntryState } from './WidgetEntry.types';
import { isFlexboxContainer, isGroup, isKind } from './WidgetOperations';
import { useFormDescriptionEditor } from './hooks/useFormDescriptionEditor';

const useWidgetEntryStyles = makeStyles<
  { flexDirection: GQLFlexDirection; flexGrow: number; kind: any },
  'widgetElement'
>()((theme, { flexDirection, flexGrow, kind: _kind }, classes) => ({
  widget: {
    display: 'flex',
    flexDirection: flexDirection,
    flexGrow: flexGrow,
  },
  widgetElement: {
    flexGrow: flexGrow,
    border: '1px solid transparent',
    '&:hover': {
      borderColor: theme.palette.primary.main,
    },
    [`&:has(.${classes.widgetElement}:hover)`]: {
      borderStyle: 'dashed',
    },
  },
  placeholder: {
    height: flexDirection === 'column' || flexDirection === 'column-reverse' ? '10px' : 'inherit',
    width: flexDirection === 'row' || flexDirection === 'row-reverse' ? '10px' : 'inherit',
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

const WidgetTooltip = withStyles(Tooltip, (theme: Theme) => ({
  tooltip: {
    backgroundColor: theme.palette.primary.main,
    margin: '0px',
    borderRadius: '0px',
  },
}));

const isErrorPayload = (
  payload: GQLAddWidgetPayload | GQLDeleteWidgetPayload | GQLMoveWidgetPayload
): payload is GQLErrorPayload => payload.__typename === 'ErrorPayload';

export const WidgetEntry = ({ page, container, widget, flexDirection, flexGrow }: WidgetEntryProps) => {
  const { editingContextId, representationId, readOnly } = useFormDescriptionEditor();
  const noop = () => {};
  const { classes } = useWidgetEntryStyles({ flexDirection, flexGrow, kind: widget.__typename });

  const initialState: WidgetEntryState = { message: null };
  const [state, setState] = useState<WidgetEntryState>(initialState);
  const { message } = state;

  const { data: widgetContributions } = useData(widgetContributionExtensionPoint);

  const { setSelection } = useSelection();
  const { showDeletionConfirmation } = useDeletionConfirmationDialog();

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
        },
      ],
    };
    setSelection(newSelection);
    event.stopPropagation();
  };

  const handleDelete: React.KeyboardEventHandler<HTMLDivElement> = (event) => {
    event.preventDefault();
    if (event.code === 'Delete') {
      const deleteWidgetInput: GQLDeleteWidgetInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId,
        widgetId: widget.id,
      };
      const deleteWidgetVariables: GQLDeleteWidgetMutationVariables = { input: deleteWidgetInput };
      showDeletionConfirmation(() => {
        deleteWidget({ variables: deleteWidgetVariables });
      });
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

    let children: GQLWidget[] = [];
    if (isGroup(container)) {
      children = (container as GQLGroup).widgets;
    } else if (isFlexboxContainer(container)) {
      children = (container as GQLFlexboxContainer).children;
    }

    let index: number = children.indexOf(widget);
    if (index <= 0) {
      index = 0;
    }
    if (isKind(id) || widgetContributions.find((widgetContribution) => widgetContribution.name === id)) {
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
        onDropBefore={readOnly ? noop : onDropBefore}
      />
    );
  } else if (widget.__typename === 'SplitButton') {
    widgetElement = (
      <SplitButtonWidget
        data-testid={widget.id}
        editingContextId={editingContextId}
        representationId={representationId}
        widget={widget as GQLSplitButton}
        onDropBefore={readOnly ? noop : onDropBefore}
      />
    );
  } else if (widget.__typename === 'Checkbox') {
    widgetElement = (
      <CheckboxWidget
        data-testid={widget.id}
        widget={widget as GQLCheckbox}
        onDropBefore={readOnly ? noop : onDropBefore}
      />
    );
  } else if (widget.__typename === 'DateTime') {
    widgetElement = (
      <DateTimeWidget
        data-testid={widget.id}
        widget={widget as GQLDateTime}
        onDropBefore={readOnly ? noop : onDropBefore}
      />
    );
  } else if (widget.__typename === 'FlexboxContainer') {
    widgetElement = (
      <FlexboxContainerWidget
        data-testid={widget.id}
        page={page}
        container={container}
        widget={widget as GQLFlexboxContainer}
      />
    );
  } else if (widget.__typename === 'FormDescriptionEditorIf') {
    const ifPreview: GQLFlexboxContainer = {
      ...widget,
      flexDirection: 'column',
      flexWrap: 'nowrap',
      flexGrow: 1,
      borderStyle: null,
      children: (widget as GQLContainer).children,
    };
    widgetElement = (
      <FlexboxContainerWidget data-testid={widget.id} page={page} container={container} widget={ifPreview} />
    );
  } else if (widget.__typename === 'FormDescriptionEditorFor') {
    const forPreview: GQLFlexboxContainer = {
      ...widget,
      flexDirection: 'column',
      flexWrap: 'nowrap',
      flexGrow: 1,
      borderStyle: null,
      children: (widget as GQLContainer).children,
    };
    widgetElement = (
      <FlexboxContainerWidget data-testid={widget.id} page={page} container={container} widget={forPreview} />
    );
  } else if (widget.__typename === 'Image') {
    widgetElement = (
      <ImageWidget data-testid={widget.id} widget={widget as GQLImage} onDropBefore={readOnly ? noop : onDropBefore} />
    );
  } else if (widget.__typename === 'LabelWidget') {
    widgetElement = (
      <LabelWidget
        data-testid={widget.id}
        widget={widget as GQLLabelWidget}
        onDropBefore={readOnly ? noop : onDropBefore}
      />
    );
  } else if (widget.__typename === 'Link') {
    widgetElement = (
      <LinkWidget data-testid={widget.id} widget={widget as GQLLink} onDropBefore={readOnly ? noop : onDropBefore} />
    );
  } else if (widget.__typename === 'List') {
    widgetElement = (
      <ListWidget data-testid={widget.id} widget={widget as GQLList} onDropBefore={readOnly ? noop : onDropBefore} />
    );
  } else if (widget.__typename === 'MultiSelect') {
    widgetElement = (
      <MultiSelectWidget
        data-testid={widget.id}
        widget={widget as GQLMultiSelect}
        onDropBefore={readOnly ? noop : onDropBefore}
      />
    );
  } else if (widget.__typename === 'Radio') {
    widgetElement = (
      <RadioWidget data-testid={widget.id} widget={widget as GQLRadio} onDropBefore={readOnly ? noop : onDropBefore} />
    );
  } else if (widget.__typename === 'RichText') {
    widgetElement = (
      <RichTextWidget
        data-testid={widget.id}
        widget={widget as GQLRichText}
        onDropBefore={readOnly ? noop : onDropBefore}
      />
    );
  } else if (widget.__typename === 'Select') {
    widgetElement = (
      <SelectWidget
        data-testid={widget.id}
        widget={widget as GQLSelect}
        onDropBefore={readOnly ? noop : onDropBefore}
      />
    );
  } else if (widget.__typename === 'Slider') {
    widgetElement = (
      <SliderWidget
        data-testid={widget.id}
        widget={widget as GQLSlider}
        onDropBefore={readOnly ? noop : onDropBefore}
      />
    );
  } else if (widget.__typename === 'Textarea') {
    widgetElement = (
      <TextAreaWidget
        data-testid={widget.id}
        widget={widget as GQLTextarea}
        onDropBefore={readOnly ? noop : onDropBefore}
      />
    );
  } else if (widget.__typename === 'Textfield') {
    widgetElement = (
      <TextfieldWidget
        data-testid={widget.id}
        widget={widget as GQLTextfield}
        onDropBefore={readOnly ? noop : onDropBefore}
      />
    );
  } else if (widget.__typename === 'TreeWidget') {
    widgetElement = <TreeWidget data-testid={widget.id} widget={widget as GQLTree} onDropBefore={onDropBefore} />;
  } else if (widget.__typename === 'ChartWidget') {
    const chartWidget: GQLChartWidget = widget as GQLChartWidget;
    if (chartWidget.chart.__typename === 'BarChart') {
      widgetElement = (
        <BarChartWidget
          data-testid={widget.id}
          widget={widget as GQLChartWidget}
          onDropBefore={readOnly ? noop : onDropBefore}
        />
      );
    } else if (chartWidget.chart.__typename === 'PieChart') {
      widgetElement = (
        <PieChartWidget
          data-testid={widget.id}
          widget={widget as GQLChartWidget}
          onDropBefore={readOnly ? noop : onDropBefore}
        />
      );
    }
  } else {
    const widgetContribution = widgetContributions.find(
      (widgetContribution) => widgetContribution.name === widget.__typename
    );
    if (widgetContribution) {
      if (widgetContribution.previewComponent) {
        const PreviewComponent = widgetContribution.previewComponent;
        widgetElement = (
          <PreviewComponent data-testid={widget.id} widget={widget} onDropBefore={readOnly ? noop : onDropBefore} />
        );
      } else if (widgetContribution.component(widget)) {
        widgetElement = (
          <CustomWidget data-testid={widget.id} widget={widget} onDropBefore={readOnly ? noop : onDropBefore} />
        );
      }
    } else {
      console.error(`Unsupported widget type ${widget.__typename}`);
    }
  }

  return (
    <div className={classes.widget}>
      <div
        data-testid={`WidgetEntry-DropArea-${widget.id}`}
        className={classes.placeholder}
        onDragEnter={readOnly ? noop : handleDragEnter}
        onDragOver={readOnly ? noop : handleDragOver}
        onDragLeave={readOnly ? noop : handleDragLeave}
        onDrop={readOnly ? noop : handleDrop}
      />
      <WidgetTooltip title={widget.__typename} placement="top-end">
        <div
          className={classes.widgetElement}
          onClick={readOnly ? noop : handleClick}
          tabIndex={0}
          onKeyDown={readOnly ? noop : handleDelete}
          draggable={!readOnly}
          onDragStart={readOnly ? noop : handleDragStart}>
          {widgetElement}
        </div>
      </WidgetTooltip>
      {message ? <Toast open message={message} onClose={() => setState({ message: null })} /> : null}
    </div>
  );
};
