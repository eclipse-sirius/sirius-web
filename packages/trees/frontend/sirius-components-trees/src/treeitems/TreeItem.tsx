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
import IconButton from '@material-ui/core/IconButton';
import { makeStyles } from '@material-ui/core/styles';
import CropDinIcon from '@material-ui/icons/CropDin';
import MoreVertIcon from '@material-ui/icons/MoreVert';
import UnfoldMoreIcon from '@material-ui/icons/UnfoldMore';
import React, { useContext, useEffect, useRef, useState } from 'react';
import { TreeItemProps } from './TreeItem.types';
import { TreeItemArrow } from './TreeItemArrow';
import { TreeItemContextMenu, TreeItemContextMenuContext } from './TreeItemContextMenu';
import { TreeItemContextMenuContextValue } from './TreeItemContextMenu.types';
import { TreeItemDirectEditInput } from './TreeItemDirectEditInput';
import { isFilterCandidate } from './filterTreeItem';

const useTreeItemStyle = makeStyles((theme) => ({
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
  more: {
    hover: {
      backgroundColor: theme.palette.action.hover,
    },
    focus: {
      backgroundColor: theme.palette.action.selected,
    },
  },
  expandIcon: {
    marginLeft: 'auto',
    marginRight: theme.spacing(1),
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
  ul: {
    marginLeft: theme.spacing(3),
  },
  highlight: {
    backgroundColor: theme.palette.navigation.leftBackground,
  },
}));

const getString = (styledString: GQLStyledString): string => {
  return styledString.styledStringFragments.map((fragments) => fragments.text).join();
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
}: TreeItemProps) => {
  const classes = useTreeItemStyle();

  const treeItemMenuContributionComponents = useContext<TreeItemContextMenuContextValue>(TreeItemContextMenuContext)
    .filter((contribution) => contribution.props.canHandle(treeId, item))
    .map((contribution) => contribution.props.component);

  const initialState = {
    showContextMenu: false,
    menuAnchor: null,
    editingMode: false,
    label: item.label,
    prevSelectionId: null,
    editingkey: '',
  };

  const [state, setState] = useState(initialState);
  const { showContextMenu, menuAnchor, editingMode } = state;

  const refDom = useRef() as any;

  const { selection, setSelection } = useSelection();

  const [isHovered, setIsHovered] = useState(false);

  const handleMouseEnter = () => {
    setIsHovered(true);
  };

  const handleMouseLeave = () => {
    setIsHovered(false);
  };
  // Context menu handling
  const openContextMenu = (event) => {
    if (!showContextMenu) {
      const { currentTarget } = event;
      setState((prevState) => {
        return {
          showContextMenu: true,
          menuAnchor: currentTarget,
          editingMode: false,
          editingkey: prevState.editingkey,
          label: item.label,
          prevSelectionId: prevState.prevSelectionId,
        };
      });
    }
  };

  let contextMenu = null;
  if (showContextMenu) {
    const closeContextMenu = () => {
      setState((prevState) => {
        return {
          modalDisplayed: null,
          showContextMenu: false,
          menuAnchor: null,
          editingMode: false,
          editingkey: prevState.editingkey,
          label: item.label,
          prevSelectionId: prevState.prevSelectionId,
        };
      });
    };
    const enterEditingMode = () => {
      setState((prevState) => {
        return {
          modalDisplayed: null,
          showContextMenu: false,
          menuAnchor: null,
          editingMode: true,
          editingkey: prevState.editingkey,
          label: item.label,
          prevSelectionId: prevState.prevSelectionId,
        };
      });
    };

    contextMenu = (
      <TreeItemContextMenu
        menuAnchor={menuAnchor}
        editingContextId={editingContextId}
        treeId={treeId}
        item={item}
        readOnly={readOnly}
        treeItemMenuContributionComponents={treeItemMenuContributionComponents}
        depth={depth}
        onExpand={onExpand}
        onExpandAll={onExpandAll}
        enterEditingMode={enterEditingMode}
        onClose={closeContextMenu}
      />
    );
  }

  let children = null;
  if (item.expanded && item.children) {
    children = (
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
  if (isHovered && item.selectable) {
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
  let text;
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
        editingkey={state.editingkey}
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
    if (!state.editingMode) {
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
        const { id, label, kind } = item;
        setSelection({ entries: [{ id, label: getString(label), kind }] });
      }
    }
  };

  const onBeginEditing = (event) => {
    if (!item.editable || editingMode || readOnly) {
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
        return { ...prevState, editingMode: true, editingkey: key };
      });
    }
  };

  const dragStart: React.DragEventHandler<HTMLDivElement> = (event) => {
    const isDraggedItemSelected = selection.entries.map((entry) => entry.id).includes(item.id);
    if (!isDraggedItemSelected) {
      // If we're dragging a non-selected item, drag it alone
      const itemEntry: SelectionEntry = { id: item.id, label: getString(item.label), kind: item.kind };
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

  const shouldDisplayMoreButton = item.deletable || item.editable || treeItemMenuContributionComponents.length > 0;

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
              {shouldDisplayMoreButton ? (
                <IconButton
                  className={classes.more}
                  size="small"
                  onClick={openContextMenu}
                  data-testid={`${label}-more`}>
                  <MoreVertIcon style={{ fontSize: 12 }} />
                </IconButton>
              ) : null}
            </div>
          </div>
          {!shouldDisplayMoreButton && isHovered && item.hasChildren && (
            <IconButton
              className={classes.expandIcon}
              size="small"
              data-testid="expand-all"
              title="expand all"
              onClick={() => {
                onExpandAll(item);
              }}>
              <UnfoldMoreIcon style={{ fontSize: 12 }} />
            </IconButton>
          )}
        </div>
        {children}
        {contextMenu}
      </>
    );
  }
  return <>{currentTreeItem}</>;
};
