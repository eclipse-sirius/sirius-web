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
  getCSSColor,
  useData,
  useDeletionConfirmationDialog,
  useSelection,
} from '@eclipse-sirius/sirius-components-core';
import { GQLWidget, widgetContributionExtensionPoint } from '@eclipse-sirius/sirius-components-forms';
import { GroupStyleProps } from '@eclipse-sirius/sirius-components-forms/src/groups/Group.types';
import ToggleButton from '@mui/material/ToggleButton';
import ToggleButtonGroup from '@mui/material/ToggleButtonGroup';
import Tooltip from '@mui/material/Tooltip';
import Typography from '@mui/material/Typography';
import { Theme } from '@mui/material/styles';
import React, { useEffect, useRef, useState } from 'react';
import { makeStyles, withStyles } from 'tss-react/mui';
import {
  addGroupMutation,
  addWidgetMutation,
  deleteGroupMutation,
  moveGroupMutation,
  moveWidgetMutation,
} from './FormDescriptionEditorEventFragment';
import {
  GQLAddGroupInput,
  GQLAddGroupMutationData,
  GQLAddGroupMutationVariables,
  GQLAddWidgetInput,
  GQLAddWidgetMutationData,
  GQLAddWidgetMutationVariables,
  GQLAddWidgetPayload,
  GQLDeleteGroupInput,
  GQLDeleteGroupMutationData,
  GQLDeleteGroupMutationVariables,
  GQLDeleteGroupPayload,
  GQLErrorPayload,
  GQLMoveGroupInput,
  GQLMoveGroupMutationData,
  GQLMoveGroupMutationVariables,
  GQLMoveGroupPayload,
  GQLMoveWidgetInput,
  GQLMoveWidgetMutationData,
  GQLMoveWidgetMutationVariables,
  GQLMoveWidgetPayload,
} from './FormDescriptionEditorEventFragment.types';
import { GroupProps, GroupState } from './Group.types';
import { ToolbarActions } from './ToolbarActions';
import { WidgetEntry } from './WidgetEntry';
import { isKind } from './WidgetOperations';
import { useFormDescriptionEditor } from './hooks/useFormDescriptionEditor';

const useGroupEntryStyles = makeStyles<GroupStyleProps, 'verticalSections' | 'adaptableSections'>()(
  (theme, { borderStyle }, classes) => ({
    group: {
      display: 'flex',
      flexDirection: 'column',
      flexGrow: 1,
      borderWidth: borderStyle ? borderStyle.size : 1,
      borderColor: borderStyle ? getCSSColor(borderStyle?.color, theme) || 'transparent' : 'gray',
      borderStyle: borderStyle?.lineStyle || 'solid',
      borderRadius: borderStyle ? borderStyle.radius : 10,
      paddingTop: '1px',
      '&:hover': {
        borderColor: theme.palette.primary.main,
      },
      [`&:has(.${classes.verticalSections}:hover)`]: {
        borderStyle: 'dashed',
      },
      [`&:has(.${classes.adaptableSections}:hover)`]: {
        borderStyle: 'dashed',
      },
    },
    labelAndToolbar: {
      display: 'flex',
      flexDirection: 'row',
      justifyContent: 'space-between',
      paddingLeft: theme.spacing(1),
      overflowX: 'auto',
    },
    verticalSections: {
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'stretch',
      paddingLeft: theme.spacing(1),
      overflowX: 'auto',
    },
    adaptableSections: {
      display: 'grid',
      gridTemplateColumns: 'repeat(auto-fit, minmax(250px, 1fr))',
      gap: theme.spacing(2),
      '& > *': {
        marginBottom: theme.spacing(2),
      },
    },
    placeholder: {
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
      justifyContent: 'center',
      backgroundColor: 'whitesmoke',
      border: '1px solid whitesmoke',
      borderRadius: '5px',
      height: '20px',
      width: 'inherit',
    },
    dragOver: {
      borderWidth: '1px',
      borderStyle: 'dashed',
      borderColor: theme.palette.primary.main,
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
    selected: {
      color: theme.palette.primary.main,
    },
  })
);

const GroupTooltip = withStyles(Tooltip, (theme: Theme) => ({
  tooltip: {
    backgroundColor: theme.palette.primary.main,
    margin: '0px',
    borderRadius: '0px',
  },
}));

const isErrorPayload = (
  payload: GQLAddWidgetPayload | GQLDeleteGroupPayload | GQLMoveGroupPayload | GQLMoveWidgetPayload
): payload is GQLErrorPayload => payload.__typename === 'ErrorPayload';

export const Group = ({ page, group }: GroupProps) => {
  const { editingContextId, representationId, readOnly } = useFormDescriptionEditor();
  const noop = () => {};
  const { classes } = useGroupEntryStyles({
    borderStyle: group.borderStyle,
  });

  const initialState: GroupState = { message: null, selected: false };
  const [state, setState] = useState<GroupState>(initialState);
  const { message, selected } = state;
  const { selection, setSelection } = useSelection();
  const { showDeletionConfirmation } = useDeletionConfirmationDialog();

  const ref = useRef<HTMLInputElement | null>(null);

  useEffect(() => {
    if (ref.current && selection.entries.find((entry) => entry.id === group.id)) {
      ref.current.focus();
      setState((prevState) => {
        return { ...prevState, selected: true };
      });
    } else {
      setState((prevState) => {
        return { ...prevState, selected: false };
      });
    }
  }, [selection, group]);

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

  const [deleteGroup, { loading: deleteGroupLoading, data: deleteGroupData, error: deleteGroupError }] = useMutation<
    GQLDeleteGroupMutationData,
    GQLDeleteGroupMutationVariables
  >(deleteGroupMutation);

  useEffect(() => {
    if (!deleteGroupLoading) {
      if (deleteGroupError) {
        setState((prevState) => {
          return { ...prevState, message: deleteGroupError.message };
        });
      }
      if (deleteGroupData) {
        const { deleteGroup } = deleteGroupData;
        if (isErrorPayload(deleteGroup)) {
          setState((prevState) => {
            return { ...prevState, message: deleteGroup.message };
          });
        }
      }
    }
  }, [deleteGroupLoading, deleteGroupData, deleteGroupError]);

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

  const handleClick: React.MouseEventHandler<HTMLDivElement> = (event) => {
    const newSelection: Selection = {
      entries: [
        {
          id: group.id,
        },
      ],
    };
    setSelection(newSelection);
    event.stopPropagation();
  };

  const handleDelete: React.KeyboardEventHandler<HTMLDivElement> = (event) => {
    event.preventDefault();
    if (event.key === 'Delete') {
      const deleteGroupInput: GQLDeleteGroupInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId,
        groupId: group.id,
      };
      const deleteGroupVariables: GQLDeleteGroupMutationVariables = { input: deleteGroupInput };
      showDeletionConfirmation(() => {
        deleteGroup({ variables: deleteGroupVariables });
      });
      event.stopPropagation();
    }
  };

  const handleDragStart: React.DragEventHandler<HTMLDivElement> = (event: React.DragEvent<HTMLDivElement>) => {
    event.dataTransfer.setData('draggedElementId', group.id);
    event.dataTransfer.setData('draggedElementType', 'Group');
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
  const { data: widgetContributions } = useData(widgetContributionExtensionPoint);
  const handleDrop: React.DragEventHandler<HTMLDivElement> = (event: React.DragEvent<HTMLDivElement>) => {
    event.preventDefault();
    event.currentTarget.classList.remove(classes.dragOver);

    const id: string = event.dataTransfer.getData('draggedElementId');
    const type: string = event.dataTransfer.getData('draggedElementType');

    if (type !== 'Group') {
      return;
    }

    if (id === 'Group') {
      let newGroupIndex: number = page.groups.indexOf(group);
      if (newGroupIndex <= 0) {
        newGroupIndex = 0;
      }
      const addGroupInput: GQLAddGroupInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId,
        pageId: page.id,
        index: newGroupIndex,
      };
      const addGroupVariables: GQLAddGroupMutationVariables = { input: addGroupInput };
      addGroup({ variables: addGroupVariables });
    } else if (page.groups.find((g) => g.id === id)) {
      let groupNewIndex: number = page.groups.indexOf(group);
      if (groupNewIndex <= 0) {
        groupNewIndex = 0;
      }
      const movedGroupIndex = page.groups.findIndex((g) => g.id === id);
      if (movedGroupIndex > -1 && movedGroupIndex < groupNewIndex) {
        groupNewIndex--;
      }
      const moveGroupInput: GQLMoveGroupInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId,
        pageId: page.id,
        groupId: id,
        index: groupNewIndex,
      };
      const moveGroupVariables: GQLMoveGroupMutationVariables = { input: moveGroupInput };
      moveGroup({ variables: moveGroupVariables });
    }
  };
  const handleDropBottom: React.DragEventHandler<HTMLDivElement> = (event: React.DragEvent<HTMLDivElement>) => {
    event.preventDefault();
    event.currentTarget.classList.remove(classes.dragOver);

    const id: string = event.dataTransfer.getData('draggedElementId');
    const type: string = event.dataTransfer.getData('draggedElementType');

    if (type !== 'Widget') {
      return;
    }

    let widgetIndex = group.widgets.length;

    if (isKind(id) || widgetContributions.find((widgetContribution) => widgetContribution.name === id)) {
      const addWidgetInput: GQLAddWidgetInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId,
        containerId: group.id,
        kind: id,
        index: widgetIndex,
      };
      const addWidgetVariables: GQLAddWidgetMutationVariables = { input: addWidgetInput };
      addWidget({ variables: addWidgetVariables });
    } else {
      if (group.widgets.find((w: GQLWidget) => w.id === id)) {
        widgetIndex--;
      }

      const moveWidgetInput: GQLMoveWidgetInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId,
        containerId: group.id,
        widgetId: id,
        index: widgetIndex,
      };
      const moveWidgetVariables: GQLMoveWidgetMutationVariables = { input: moveWidgetInput };
      moveWidget({ variables: moveWidgetVariables });
    }
  };

  const [visibleWidgetIds, setVisibleWidgetIds] = useState<string[]>([]);

  useEffect(() => {
    setVisibleWidgetIds(group.widgets.map((widget: GQLWidget) => widget.id));
  }, [group]);

  let widgetSelector = undefined;
  if (group.displayMode === 'TOGGLEABLE_AREAS') {
    widgetSelector = (
      <ToggleButtonGroup
        size="small"
        value={visibleWidgetIds}
        onChange={(_, newVisibleIds) => setVisibleWidgetIds(newVisibleIds)}>
        {group.widgets.map((widget) => {
          return (
            <ToggleButton value={widget.id} key={widget.id}>
              {widget.label}
            </ToggleButton>
          );
        })}
      </ToggleButtonGroup>
    );
  }

  const toolbar = (
    <ToolbarActions
      data-testid={`Group-ToolbarActions-${group.id}`}
      toolbarActions={group.toolbarActions}
      containerId={group.id}
    />
  );

  return (
    <div>
      <div
        data-testid={`Group-DropArea-${group.id}`}
        className={classes.placeholder}
        onDragEnter={readOnly ? noop : handleDragEnter}
        onDragOver={readOnly ? noop : handleDragOver}
        onDragLeave={readOnly ? noop : handleDragLeave}
        onDrop={readOnly ? noop : handleDrop}
      />
      <GroupTooltip title={group.__typename} placement="top-end">
        <div
          data-testid={group.id}
          className={classes.group}
          onClick={readOnly ? noop : handleClick}
          onKeyDown={readOnly ? noop : handleDelete}
          draggable={!readOnly}
          onDragStart={readOnly ? noop : handleDragStart}>
          <div
            className={`${classes.labelAndToolbar} ${selected ? classes.selected : ''}`}
            onFocus={() =>
              setState((prevState) => {
                return { ...prevState, selected: true };
              })
            }
            onBlur={() =>
              setState((prevState) => {
                return { ...prevState, selected: false };
              })
            }
            ref={ref}
            tabIndex={0}>
            {group.displayMode === 'TOGGLEABLE_AREAS' ? (
              widgetSelector
            ) : (
              <Typography variant="subtitle1">{group.label}</Typography>
            )}
            {toolbar}
          </div>
          <div className={group.displayMode === 'LIST' ? classes.verticalSections : classes.adaptableSections}>
            {group.widgets
              .filter((widget: GQLWidget) => visibleWidgetIds.includes(widget.id))
              .map((widget: GQLWidget) => (
                <WidgetEntry
                  key={widget.id}
                  page={page}
                  container={group}
                  widget={widget}
                  flexDirection={group.displayMode === 'LIST' ? 'column' : 'row'}
                  flexGrow={1}
                />
              ))}
          </div>
          <div
            data-testid={`Group-Widgets-DropArea-${group.id}`}
            className={classes.bottomDropArea}
            onDragEnter={readOnly ? noop : handleDragEnter}
            onDragOver={readOnly ? noop : handleDragOver}
            onDragLeave={readOnly ? noop : handleDragLeave}
            onDrop={readOnly ? noop : handleDropBottom}>
            <Typography variant="body1">{'Drag and drop a widget here'}</Typography>
          </div>
        </div>
      </GroupTooltip>
      <Toast
        message={message}
        open={!!message}
        onClose={() =>
          setState((prevState) => {
            return { ...prevState, message: null };
          })
        }
      />
    </div>
  );
};
