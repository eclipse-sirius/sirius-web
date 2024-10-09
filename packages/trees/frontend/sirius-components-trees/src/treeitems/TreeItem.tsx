/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
import {
  DRAG_SOURCES_TYPE,
  GQLStyledString,
  IconOverlay,
  Selection,
  SelectionEntry,
  StyledLabel,
  useSelection,
} from '@eclipse-sirius/sirius-components-core';
import CropDinIcon from '@mui/icons-material/CropDin';
import React, { useEffect, useRef, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { TreeItemProps, TreeItemState } from './TreeItem.types';
import { TreeItemAction } from './TreeItemAction';
import { TreeItemArrow } from './TreeItemArrow';
import { TreeItemDirectEditInput } from './TreeItemDirectEditInput';
import { isFilterCandidate } from './filterTreeItem';

const useTreeItemStyle = makeStyles()((theme) => ({
  treeItem: {
    display: 'flex',
    flexDirection: 'row',
    height: '24px',
    gap: theme.spacing(0.5),
    alignItems: 'center',
    userSelect: 'none',
    '&:focus-within': {
      borderWidth: '1px',
      borderColor: 'black',
      borderStyle: 'dotted',
    },
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
    marginLeft: theme.spacing(3),
  },
  highlight: {
    backgroundColor: theme.palette.navigation.leftBackground,
  },
}));

export const getString = (styledString: GQLStyledString): string => {
  return styledString.styledStringFragments.map((fragments) => fragments.text).join('');
};

// The list of characters that will enable the direct edit mechanism.
const directEditActivationValidCharacters = /[\w&é§èàùçÔØÁÛÊË"«»’”„´$¥€£\\¿?!=+-,;:%/{}[\]–#@*.]/;

export const TreeItem = ({
  editingContextId,
  treeId,
  item,
  depth,
  onExpand,
  onExpandAll,
  readOnly,
  textToHighlight,
  textToFilter,
  enableMultiSelection,
  markedItemIds,
  treeItemActionRender,
}: TreeItemProps) => {
  const { classes } = useTreeItemStyle();

  const initialState: TreeItemState = {
    editingMode: false,
    editingKey: null,
    isHovered: false,
  };

  const [state, setState] = useState<TreeItemState>(initialState);
  const { editingMode } = state;

  const refDom = useRef() as any;

  const { selection, setSelection } = useSelection();

  const handleMouseEnter = () => {
    setState((prevState) => {
      return { ...prevState, isHovered: true };
    });
  };

  const handleMouseLeave = () => {
    setState((prevState) => {
      return { ...prevState, isHovered: false };
    });
  };

  const onTreeItemAction = () => {
    setState((prevState) => {
      return { ...prevState, isHovered: false };
    });
  };

  const enterEditingMode = () => {
    setState((prevState) => ({
      ...prevState,
      editingMode: true,
      editingKey: null,
    }));
  };

  let content = null;
  if (item.expanded && item.children) {
    content = (
      <ul className={classes.ul}>
        {item.children.map((childItem) => {
          return (
            <li key={childItem.id}>
              <TreeItem
                editingContextId={editingContextId}
                treeId={treeId}
                item={childItem}
                depth={depth + 1}
                onExpand={onExpand}
                onExpandAll={onExpandAll}
                enableMultiSelection={enableMultiSelection}
                readOnly={readOnly}
                textToHighlight={textToHighlight}
                textToFilter={textToFilter}
                markedItemIds={markedItemIds}
                treeItemActionRender={treeItemActionRender}
              />
            </li>
          );
        })}
      </ul>
    );
  }

  let className = classes.treeItem;
  let dataTestid = undefined;

  const selected = selection.entries.find((entry) => entry.id === item.id);
  if (selected) {
    className = `${className} ${classes.selected}`;
    dataTestid = 'selected';
  }
  if (state.isHovered && item.selectable) {
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

  let image = <CropDinIcon />;
  if (item.iconURL?.length > 0) {
    image = <IconOverlay iconURL={item.iconURL} alt={item.kind} />;
  }
  let text: JSX.Element | null = null;
  const onCloseEditingMode = () => {
    setState((prevState) => {
      return { ...prevState, editingMode: false };
    });
    refDom.current.focus();
  };

  const marked: boolean = markedItemIds.some((id) => id === item.id);
  if (editingMode) {
    text = (
      <TreeItemDirectEditInput
        editingContextId={editingContextId}
        treeId={treeId}
        treeItemId={item.id}
        editingKey={state.editingKey}
        onClose={onCloseEditingMode}></TreeItemDirectEditInput>
    );
  } else {
    const styledLabelProps = {
      styledString: item.label,
      selected: false,
      textToHighlight: textToHighlight,
      marked: marked,
    };
    text = <StyledLabel {...styledLabelProps}></StyledLabel>;
  }

  const onClick: React.MouseEventHandler<HTMLDivElement> = (event) => {
    if (!state.editingMode && event.currentTarget.contains(event.target as HTMLElement)) {
      refDom.current.focus();
      if (!item.selectable) {
        return;
      }

      if ((event.ctrlKey || event.metaKey) && enableMultiSelection) {
        event.stopPropagation();
        const isItemInSelection = selection.entries.find((entry) => entry.id === item.id);
        if (isItemInSelection) {
          const newSelection: Selection = { entries: selection.entries.filter((entry) => entry.id !== item.id) };
          setSelection(newSelection);
        } else {
          const { id, label, kind } = item;
          const newEntry = { id, label: getString(label), kind };
          const newSelection: Selection = { entries: [...selection.entries, newEntry] };
          setSelection(newSelection);
        }
      } else {
        const { id, kind } = item;
        setSelection({ entries: [{ id, kind }] });
      }
    }
  };

  const onBeginEditing = (event) => {
    if (!item.editable || editingMode || readOnly || !event.currentTarget.contains(event.target as HTMLElement)) {
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
    const isDraggedItemSelected = selection.entries.map((entry) => entry.id).includes(item.id);
    if (!isDraggedItemSelected) {
      // If we're dragging a non-selected item, drag it alone
      const itemEntry: SelectionEntry = { id: item.id, kind: item.kind };
      event.dataTransfer.setData(DRAG_SOURCES_TYPE, JSON.stringify([itemEntry]));
    } else if (selection.entries.length > 0) {
      // Otherwise drag the whole selection
      event.dataTransfer.setData(DRAG_SOURCES_TYPE, JSON.stringify(selection.entries));
    }
  };

  const dragOver: React.DragEventHandler<HTMLDivElement> = (event) => {
    event.stopPropagation();
  };

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
      tooltipText = params.get('type');
    }
  }

  let currentTreeItem: JSX.Element | null;
  if (textToFilter && isFilterCandidate(item, textToFilter)) {
    currentTreeItem = null;
  } else {
    const label = getString(item.label);
    /* ref, tabindex and onFocus are used to set the React component focusabled and to set the focus to the corresponding DOM part */
    currentTreeItem = (
      <>
        <div className={className} onMouseEnter={handleMouseEnter} onMouseLeave={handleMouseLeave}>
          <TreeItemArrow item={item} depth={depth} onExpand={onExpand} data-testid={`${label}-toggle`} />
          <div
            ref={refDom}
            tabIndex={0}
            onKeyDown={onBeginEditing}
            draggable={true}
            onClick={onClick}
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
                {image}
                {text}
              </div>
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
                    isHovered: state.isHovered,
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
                    isHovered={state.isHovered}
                  />
                )}
              </div>
            </div>
          </div>
        </div>
        {content}
      </>
    );
  }
  return <>{currentTreeItem}</>;
};
