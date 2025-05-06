/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
import { DRAG_SOURCES_TYPE, GQLStyledString, StyledLabel } from '@eclipse-sirius/sirius-components-core';
import React, { useEffect, useRef, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { GQLTreeItem } from '../views/TreeView.types';
import { PartHovered, TreeItemProps, TreeItemState } from './TreeItem.types';
import { TreeItemAction } from './TreeItemAction';
import { TreeItemArrow } from './TreeItemArrow';
import { TreeItemDirectEditInput } from './TreeItemDirectEditInput';
import { TreeItemIcon } from './TreeItemIcon';
import { isFilterCandidate } from './filterTreeItem';
import { useDropTreeItem } from './useDropTreeItem';

interface TreeItemStyleProps {
  depth: number;
}

const useTreeItemStyle = makeStyles<TreeItemStyleProps>()((theme, { depth }) => ({
  treeItemBefore: {
    height: '2px',
  },
  treeItem: {
    display: 'flex',
    flexDirection: 'row',
    height: '22px',
    gap: theme.spacing(0.5),
    alignItems: 'center',
    userSelect: 'none',
    '&:focus-within': {
      borderWidth: '1px',
      borderColor: 'black',
      borderStyle: 'dotted',
    },
    paddingLeft: `${24 * (depth - 1)}px`,
  },
  treeItemHover: {
    backgroundColor: theme.palette.action.hover,
  },
  selected: {
    backgroundColor: theme.palette.action.selected,
    '&:hover': {
      backgroundColor: theme.palette.action.selected,
    },
  },
  nonSelectable: {
    opacity: 0.6,
  },
  arrow: {
    cursor: 'pointer',
  },
  content: {
    display: 'grid',
    gridTemplateRows: '1fr',
    gridTemplateColumns: '1fr 20px',
    columnGap: '8px',
    alignItems: 'center',
    gridColumnStart: '2',
    gridColumnEnd: '3',
  },
  imageAndLabel: {
    display: 'flex',
    flexDirection: 'row',
    gap: '4px',
    alignItems: 'center',
  },
  imageAndLabelSelectable: {
    cursor: 'pointer',
  },
  label: {
    overflow: 'hidden',
    textOverflow: 'ellipsis',
    whiteSpace: 'nowrap',
    maxWidth: '100ch',
  },
  selectedLabel: {
    fontWeight: 'bold',
  },
  marked: {
    fontWeight: 'bold',
  },
  ul: {
    marginLeft: 0,
  },
}));

export const getString = (styledString: GQLStyledString): string => {
  return styledString.styledStringFragments.map((fragments) => fragments.text).join('');
};

const getTooltipText = (item: GQLTreeItem) => {
  let tooltipText = '';
  if (item.kind.startsWith('siriusComponents://semantic')) {
    const query = item.kind.substring(item.kind.indexOf('?') + 1, item.kind.length);
    const params = new URLSearchParams(query);
    if (params.has('domain') && params.has('entity')) {
      tooltipText = params.get('domain') + '::' + params.get('entity');
    }
  } else if (item.kind.startsWith('siriusComponents://representation')) {
    const query = item.kind.substring(item.kind.indexOf('?') + 1, item.kind.length);
    const params = new URLSearchParams(query);
    if (params.has('type')) {
      tooltipText = params.get('type') ?? 'representation';
    }
  }
  return tooltipText;
};

// The list of characters that will enable the direct edit mechanism.
const directEditActivationValidCharacters = /[\w&é§èàùçÔØÁÛÊË"«»’”„´$¥€£\\¿?!=+-,;:%/{}[\]–#@*.]/;

/**
 * Renders a *single* tree item (excluding its sub-items).
 */
export const TreeItem = ({
  editingContextId,
  treeId,
  item,
  itemIndex,
  depth,
  onExpand,
  onExpandAll,
  readOnly,
  textToHighlight,
  textToFilter,
  markedItemIds,
  treeItemActionRender,
  onTreeItemClick,
  selectedTreeItemIds,
}: TreeItemProps) => {
  const [state, setState] = useState<TreeItemState>({
    editingMode: false,
    editingKey: null,
    partHovered: null,
  });

  const refDom = useRef() as any;

  const { classes } = useTreeItemStyle({ depth });
  const { onDropTreeItem } = useDropTreeItem(editingContextId, treeId);

  const handleMouseEnter = (partHovered: PartHovered) => {
    setState((prevState) => ({ ...prevState, partHovered }));
  };

  const handleMouseLeave = () => {
    setState((prevState) => ({ ...prevState, partHovered: null }));
  };

  const onTreeItemAction = () => {
    setState((prevState) => ({ ...prevState, partHovered: null }));
  };

  const enterEditingMode = () => {
    setState((prevState) => ({
      ...prevState,
      editingMode: true,
      editingKey: null,
    }));
  };

  let className = classes.treeItem;
  let dataTestid: string | undefined = undefined;

  const selected = selectedTreeItemIds.find((id) => id === item.id);
  if (selected) {
    className = `${className} ${classes.selected}`;
    dataTestid = 'selected';
  }
  if (state.partHovered === 'item' && item.selectable) {
    className = `${className} ${classes.treeItemHover}`;
  }
  useEffect(() => {
    if (selected) {
      if (refDom.current?.scrollIntoViewIfNeeded) {
        refDom.current.scrollIntoViewIfNeeded(true);
      } else {
        // Fallback for browsers not supporting the non-standard `scrollIntoViewIfNeeded`
        refDom.current?.scrollIntoView({ behavior: 'smooth' });
      }
    }
  }, [selected]);

  const onCloseEditingMode = () => {
    setState((prevState) => {
      return { ...prevState, editingMode: false };
    });
    refDom.current.focus();
  };

  const onClick: React.MouseEventHandler<HTMLDivElement> = (event: React.MouseEvent<HTMLDivElement, MouseEvent>) => {
    if (!state.editingMode && event.currentTarget.contains(event.target as HTMLElement)) {
      refDom.current.focus();
      if (!item.selectable) {
        return;
      }

      // Don't change the selection if the user clicked on the TreeItemArrow
      const treeItemArrowTestId = `${getString(item.label)}-toggle`;
      if ((event.target as HTMLElement).getAttribute('data-testid') === treeItemArrowTestId) {
        return;
      }

      onTreeItemClick(event, item);
    }
  };

  const onBeginEditing = (event) => {
    if (!item.editable || state.editingMode || readOnly || !event.currentTarget.contains(event.target as HTMLElement)) {
      return;
    }
    const { key } = event;
    /*If a modifier key is hit alone, do nothing*/
    if ((event.altKey || event.shiftKey) && event.getModifierState(key)) {
      return;
    }
    const validFirstInputChar =
      !event.metaKey && !event.ctrlKey && key.length === 1 && directEditActivationValidCharacters.test(key);
    if (validFirstInputChar) {
      setState((prevState) => {
        return { ...prevState, editingMode: true, editingKey: key };
      });
    }
  };

  const dragStart: React.DragEventHandler<HTMLDivElement> = (event) => {
    const isDraggedItemSelected = selectedTreeItemIds.map((id) => id).includes(item.id);
    if (!isDraggedItemSelected) {
      // If we're dragging a non-selected item, drag it alone
      event.dataTransfer.setData(DRAG_SOURCES_TYPE, JSON.stringify([item.id]));
    } else if (selectedTreeItemIds.length > 0) {
      // Otherwise drag the whole selection
      event.dataTransfer.setData(DRAG_SOURCES_TYPE, JSON.stringify(selectedTreeItemIds));
    }
  };

  const dragOver: React.DragEventHandler<HTMLDivElement> = (event) => {
    event.preventDefault();
  };

  const onDropItem: React.DragEventHandler<HTMLDivElement> = (event) => {
    const dragSourcesStringified = event.dataTransfer.getData(DRAG_SOURCES_TYPE);
    const selectedIds = JSON.parse(dragSourcesStringified);
    onDropTreeItem(selectedIds, item.id, -1);
    event.preventDefault();
  };

  const onDropBefore: React.DragEventHandler<HTMLDivElement> = (event) => {
    const dragSourcesStringified = event.dataTransfer.getData(DRAG_SOURCES_TYPE);
    const selectedIds = JSON.parse(dragSourcesStringified);
    onDropTreeItem(selectedIds, item.id, itemIndex);
    event.preventDefault();
  };

  const text: JSX.Element = state.editingMode ? (
    <TreeItemDirectEditInput
      editingContextId={editingContextId}
      treeId={treeId}
      treeItemId={item.id}
      editingKey={state.editingKey}
      onClose={onCloseEditingMode}
    />
  ) : (
    <StyledLabel
      styledString={item.label}
      selected={false}
      textToHighlight={textToHighlight ?? ''}
      marked={markedItemIds.some((id) => id === item.id)}
    />
  );

  const itemAction = (
    <div onClick={onTreeItemAction}>
      {treeItemActionRender ? (
        treeItemActionRender({
          editingContextId: editingContextId,
          treeId: treeId,
          item: item,
          depth: depth,
          onExpand: onExpand,
          onExpandAll: onExpandAll,
          readOnly: readOnly,
          onEnterEditingMode: enterEditingMode,
          isHovered: state.partHovered === 'item',
        })
      ) : (
        <TreeItemAction
          editingContextId={editingContextId}
          treeId={treeId}
          item={item}
          depth={depth}
          onExpand={onExpand}
          onExpandAll={onExpandAll}
          readOnly={readOnly}
          onEnterEditingMode={enterEditingMode}
          isHovered={state.partHovered === 'item'}
        />
      )}
    </div>
  );

  let currentTreeItem: JSX.Element | null;
  if (textToFilter && isFilterCandidate(item, textToFilter)) {
    currentTreeItem = null;
  } else {
    const label = getString(item.label);
    const tooltipText = getTooltipText(item);
    /* ref, tabindex and onFocus are used to set the React component focusabled and to set the focus to the corresponding DOM part */
    currentTreeItem = (
      <>
        <div
          className={`${state.partHovered === 'before' ? classes.treeItemHover : ''} ${classes.treeItemBefore}`}
          onDrop={onDropBefore}
          onDragEnter={() => handleMouseEnter('before')}
          onDragExit={handleMouseLeave}
          onDragOver={dragOver}
          data-testid={`${dataTestid}-drop-before`}
        />
        <div
          className={className}
          onClick={onClick}
          onDragEnter={() => handleMouseEnter('item')}
          onDragExit={handleMouseLeave}
          onDrop={onDropItem}
          onMouseEnter={() => handleMouseEnter('item')}
          onMouseLeave={handleMouseLeave}
          data-testid={`${label}-fullrow`}>
          <TreeItemArrow item={item} depth={depth} onExpand={onExpand} data-testid={`${label}-toggle`} />
          <div
            ref={refDom}
            tabIndex={0}
            onKeyDown={onBeginEditing}
            draggable
            onDragStart={dragStart}
            onDragOver={dragOver}
            data-treeitemid={item.id}
            data-treeitemlabel={label}
            data-treeitemkind={item.kind}
            data-haschildren={item.hasChildren.toString()}
            data-depth={depth}
            data-expanded={item.expanded.toString()}
            data-testid={dataTestid}>
            <div className={`${classes.content} ${item.selectable ? '' : classes.nonSelectable}`}>
              <div
                className={`${classes.imageAndLabel} ${item.selectable ? classes.imageAndLabelSelectable : ''}`}
                onDoubleClick={() => item.hasChildren && onExpand(item.id, depth)}
                title={tooltipText}
                data-testid={label}>
                <TreeItemIcon item={item} />
                {text}
              </div>
              {itemAction}
            </div>
          </div>
        </div>
      </>
    );
  }
  return currentTreeItem;
};
