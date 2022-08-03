/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
import { gql, useMutation } from '@apollo/client';
import { makeStyles } from '@material-ui/core/styles';
import { DRAG_SOURCES_TYPE } from 'common/dataTransferTypes';
import { httpOrigin } from 'common/URL';
import { IconButton } from 'core/button/Button';
import { Text } from 'core/text/Text';
import { Textfield } from 'core/textfield/Textfield';
import { ArrowCollapsed, ArrowExpanded, More, NoIcon } from 'icons';
import React, { useContext, useEffect, useRef, useState } from 'react';
import { TreeItemProps } from 'tree/TreeItem.types';
import { TreeItemContextMenu, TreeItemContextMenuContext } from 'tree/TreeItemContextMenu';
import { v4 as uuid } from 'uuid';
import { Selection } from 'workbench/Workbench.types';

const renameTreeItemMutation = gql`
  mutation renameTreeItem($input: RenameTreeItemInput!) {
    renameTreeItem(input: $input) {
      __typename
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const useTreeItemStyle = makeStyles((theme) => ({
  treeItem: {
    display: 'grid',
    gridTemplateRows: '20px',
    gridTemplateColumns: '24px min-content',
    alignItems: 'center',
    userSelect: 'none',
    fill: '#66808a',
    '&:hover': {
      stroke: 'var(--daintree)',
      fill: 'var(--daintree)',
      backgroundColor: 'var(--blue-lagoon-lighten-95)',
    },
  },
  selected: {
    fontWeight: 'bold',
    stroke: '#66808a',
    fill: '#66808a',
    backgroundColor: 'var(--blue-lagoon-lighten-90)',
    '&:hover': {
      stroke: '#66808a',
      fill: '#66808a',
      backgroundColor: 'var(--blue-lagoon-lighten-90)',
    },
  },
  arrow: {
    cursor: 'pointer',
  },
  more: {
    hover: {
      stroke: 'var(--daintree)',
      fill: 'var(--daintree)',
      backgroundColor: 'var(--blue-lagoon-lighten-95)',
    },
    focus: {
      stroke: 'var(--daintree)',
      fill: 'var(--daintree)',
      backgroundColor: 'var(--blue-lagoon-lighten-90)',
    },
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
    display: 'grid',
    gridTemplateRows: '1fr',
    gridTemplateColumns: 'min-content 1fr',
    columnGap: '4px',
    alignItems: 'center',
    cursor: 'pointer',
    textOverflow: 'ellipsis',
    whiteSpace: 'nowrap',
  },
  label: {
    color: 'var(--daintree)',
    fontSize: 'var(--font-size-5)',
  },
  ul: {
    marginLeft: '20px',
  },
}));

// The list of characters that will enable the direct edit mechanism.
const directEditActivationValidCharacters = /[\w&é§èàùçÔØÁÛÊË"«»’”„´$¥€£\\¿?!=+-,;:%/{}[\]–#@*.]/;

const ItemCollapseToggle = ({ item, depth, onExpand, dataTestid }) => {
  const classes = useTreeItemStyle();
  if (item.hasChildren) {
    const onClick = () => onExpand(item.id, depth);
    if (item.expanded) {
      return (
        <ArrowExpanded
          title="Collapse"
          className={classes.arrow}
          width="20"
          height="20"
          onClick={onClick}
          data-testid={dataTestid}
        />
      );
    } else {
      return (
        <ArrowCollapsed
          title="Expand"
          className={classes.arrow}
          width="20"
          height="20"
          onClick={onClick}
          data-testid={dataTestid}
        />
      );
    }
  }
  return <div></div>;
};

export const TreeItem = ({
  editingContextId,
  treeId,
  item,
  depth,
  onExpand,
  selection,
  setSelection,
  readOnly,
}: TreeItemProps) => {
  const classes = useTreeItemStyle();

  const treeItemMenuContributionComponents = useContext(TreeItemContextMenuContext)
    .filter((contribution) => contribution.props.canHandle(item))
    .map((contribution) => contribution.props.component);

  const initialState = {
    showContextMenu: false,
    menuAnchor: null,
    editingMode: false,
    label: item.label,
    prevSelectionId: null,
  };
  const [state, setState] = useState(initialState);
  const { showContextMenu, menuAnchor, editingMode, label } = state;

  const refDom = useRef() as any;

  const [renameTreeItem, { loading: renameTreeItemLoading, data: renameTreeItemData, error: renameTreeItemError }] =
    useMutation(renameTreeItemMutation);
  useEffect(() => {
    if (!renameTreeItemLoading && !renameTreeItemError && renameTreeItemData?.renameTreeItem) {
      const { renameTreeItem } = renameTreeItemData;
      if (renameTreeItem.__typename === 'RenameTreeItemSuccessPayload') {
        setState((prevState) => {
          return { ...prevState, editingMode: false };
        });
      }
    }
  }, [renameTreeItemData, renameTreeItemError, renameTreeItemLoading]);

  // custom hook for getting previous value
  const usePrevious = (value) => {
    const ref = useRef();
    useEffect(() => {
      ref.current = value;
    });
    return ref.current;
  };

  const prevEditingMode = usePrevious(editingMode);
  useEffect(() => {
    if (prevEditingMode && !editingMode) {
      refDom.current.focus();
    }
  }, [editingMode, prevEditingMode]);

  // Context menu handling
  const openContextMenu = (event) => {
    if (!showContextMenu) {
      const { currentTarget } = event;
      setState((prevState) => {
        return {
          showContextMenu: true,
          menuAnchor: currentTarget,
          editingMode: false,
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
        selection={selection}
        setSelection={setSelection}
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
                selection={selection}
                setSelection={setSelection}
                readOnly={readOnly}
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
  useEffect(() => {
    if (selected) {
      if (refDom.current.scrollIntoViewIfNeeded) {
        refDom.current.scrollIntoViewIfNeeded(true);
      } else {
        // Fallback for browsers not supporting the non-standard `scrollIntoViewIfNeeded`
        refDom.current.scrollIntoView({ behavior: 'smooth' });
      }
    }
  }, [selected]);

  let image = <NoIcon title={item.kind} />;
  if (item.imageURL) {
    image = <img height="16" width="16" alt={item.kind} src={httpOrigin + item.imageURL}></img>;
  }
  let text;
  if (editingMode) {
    const handleChange = (event) => {
      const newLabel = event.target.value;
      setState((prevState) => {
        return { ...prevState, editingMode: true, label: newLabel };
      });
    };

    const doRename = () => {
      const isNameValid = label.length >= 1;
      if (isNameValid && item) {
        renameTreeItem({
          variables: {
            input: { id: uuid(), editingContextId, representationId: treeId, treeItemId: item.id, newLabel: label },
          },
        });
      } else {
        setState((prevState) => {
          return { ...prevState, editingMode: false, label: item.label };
        });
      }
    };
    const onFinishEditing = (event) => {
      const { key } = event;
      if (key === 'Enter') {
        doRename();
      } else if (key === 'Escape') {
        setState((prevState) => {
          return { ...prevState, editingMode: false, label: item.label };
        });
      }
    };
    const onFocusIn = (event) => {
      event.target.select();
    };
    const onFocusOut = (event: FocusEvent) => {
      doRename();
    };
    text = (
      <Textfield
        kind={'small'}
        name="name"
        placeholder={'Enter the new name'}
        value={label}
        onChange={handleChange}
        onKeyDown={onFinishEditing}
        onFocus={onFocusIn}
        onBlur={onFocusOut}
        autoFocus
        data-testid="name-edit"
      />
    );
  } else {
    text = <Text className={classes.label}>{item.label}</Text>;
  }

  const onClick: React.MouseEventHandler<HTMLDivElement> = (event) => {
    refDom.current.focus();

    if (event.ctrlKey || event.metaKey) {
      event.stopPropagation();
      const isItemInSelection = selection.entries.find((entry) => entry.id === item.id);
      if (isItemInSelection) {
        const newSelection: Selection = { entries: selection.entries.filter((entry) => entry.id !== item.id) };
        setSelection(newSelection);
      } else {
        const { id, label, kind } = item;
        const newEntry = { id, label, kind };
        const newSelection: Selection = { entries: [...selection.entries, newEntry] };
        setSelection(newSelection);
      }
    } else {
      const { id, label, kind } = item;
      setSelection({ entries: [{ id, label, kind }] });
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
        return { ...prevState, editingMode: true, label: key };
      });
    }
  };

  const { kind } = item;
  const draggable = kind.startsWith('siriusComponents://semantic');
  const dragStart: React.DragEventHandler<HTMLDivElement> = (event) => {
    const entries = selection.entries.filter((entry) => entry.kind.startsWith('siriusComponents://semantic'));
    if (entries.length > 0) {
      event.dataTransfer.setData(DRAG_SOURCES_TYPE, JSON.stringify(entries));
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

  /* ref, tabindex and onFocus are used to set the React component focusabled and to set the focus to the corresponding DOM part */
  return (
    <>
      <div className={className}>
        <ItemCollapseToggle item={item} depth={depth} onExpand={onExpand} dataTestid={`${item.label}-toggle`} />
        <div
          ref={refDom}
          tabIndex={0}
          onKeyDown={onBeginEditing}
          draggable={draggable}
          onClick={onClick}
          onDragStart={dragStart}
          onDragOver={dragOver}
          data-treeitemid={item.id}
          data-haschildren={item.hasChildren.toString()}
          data-depth={depth}
          data-expanded={item.expanded.toString()}
          data-testid={dataTestid}
        >
          <div className={classes.content}>
            <div
              className={classes.imageAndLabel}
              onDoubleClick={() => item.hasChildren && onExpand(item.id, depth)}
              title={tooltipText}
              data-testid={item.label}
            >
              {image}
              {text}
            </div>
            {shouldDisplayMoreButton ? (
              <IconButton className={classes.more} onClick={openContextMenu} data-testid={`${item.label}-more`}>
                <More title="More" />
              </IconButton>
            ) : null}
          </div>
        </div>
      </div>
      {children}
      {contextMenu}
    </>
  );
};
