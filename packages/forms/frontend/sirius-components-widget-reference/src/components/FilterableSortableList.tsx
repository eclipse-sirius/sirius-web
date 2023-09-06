/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import { ServerContext, ServerContextValue } from '@eclipse-sirius/sirius-components-core';
import { splitText } from '@eclipse-sirius/sirius-components-trees';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import Typography from '@material-ui/core/Typography';
import { Theme, makeStyles } from '@material-ui/core/styles';
import DragHandleIcon from '@material-ui/icons/DragHandle';
import React, { useContext, useState } from 'react';
import {
  FilterableSortableListProps,
  FilterableSortableListState,
  HighlightedLabelProps,
} from './FilterableSortableList.types';
import { ModelBrowserFilterBar } from './ModelBrowserFilterBar';

const useStyles = makeStyles((theme: Theme) => ({
  selectable: {
    cursor: 'pointer',
    '&:hover': {
      backgroundColor: theme.palette.action.hover,
    },
  },
  selected: {
    cursor: 'pointer',
    backgroundColor: theme.palette.action.selected,
  },
  selectedLabel: {
    fontWeight: 'bold',
  },
  title: {
    opacity: 0.6,
    fontSize: theme.typography.caption.fontSize,
  },
  borderStyle: {
    border: '1px solid',
    borderColor: theme.palette.grey[500],
    height: 300,
    overflow: 'auto',
  },
  dragIcon: {
    display: 'block',
  },
  noDragIcon: {
    display: 'none',
  },
}));
const useLabelStyles = makeStyles((theme: Theme) => ({
  highlight: {
    backgroundColor: theme.palette.navigation.leftBackground,
  },
}));

const HighlightedLabel = ({ label, textToHighlight }: HighlightedLabelProps) => {
  const classes = useLabelStyles();
  let itemLabel: JSX.Element;
  const splitLabelWithTextToHighlight: string[] = splitText(label, textToHighlight);
  if (
    textToHighlight === null ||
    textToHighlight === '' ||
    (splitLabelWithTextToHighlight.length === 1 &&
      splitLabelWithTextToHighlight[0].toLocaleLowerCase() !== label.toLocaleLowerCase())
  ) {
    itemLabel = <>{label}</>;
  } else {
    const languages: string[] = Array.from(navigator.languages);
    itemLabel = (
      <>
        {splitLabelWithTextToHighlight.map((value, index) => {
          const shouldHighlight = value.localeCompare(textToHighlight, languages, { sensitivity: 'base' }) === 0;
          return (
            <span
              key={value + index}
              data-testid={`${label}-${value}-${index}`}
              className={shouldHighlight ? classes.highlight : ''}>
              {value}
            </span>
          );
        })}
      </>
    );
  }
  return <Typography>{itemLabel}</Typography>;
};

export const FilterableSortableList = ({
  items,
  options,
  setItems,
  handleDragItemStart,
  handleDragItemEnd,
  handleDropNewItem,
  onClick,
  selectedItems,
  moveElement,
}: FilterableSortableListProps) => {
  const classes = useStyles();
  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);

  const [state, setState] = useState<FilterableSortableListState>({
    filterBarText: '',
    hoveringItemId: undefined,
    draggingItemId: undefined,
    draggingStartIndex: -1,
    draggingIndex: -1,
  });

  const handleMouseEnter = (id: string) => {
    setState((prevState) => {
      return {
        ...prevState,
        hoveringItemId: id,
      };
    });
  };

  const handleMouseLeave = () => {
    setState((prevState) => {
      return {
        ...prevState,
        hoveringItemId: undefined,
      };
    });
  };

  const handleDragOverNewItem = (event: React.DragEvent) => {
    event.preventDefault();
  };

  const handleDragStartOrder = (id: string, index: number) => {
    setState((prevState) => {
      return {
        ...prevState,
        draggingItemId: id,
        draggingStartIndex: index,
      };
    });
  };

  const handleDragEndOrder = () => {
    if (state.draggingItemId) {
      moveElement(state.draggingItemId, state.draggingStartIndex, state.draggingIndex);
    }
    setState((prevState) => {
      return {
        ...prevState,
        draggingItemId: undefined,
      };
    });
  };

  const handleDragOverOrder = (event: React.DragEvent, index: number) => {
    event.preventDefault();
    if (state.draggingItemId) {
      const newList = Array.from(items);
      const draggedItem = newList.find((item) => item.id === state.draggingItemId);
      const draggedItemIndex = newList.indexOf(draggedItem);
      newList.splice(draggedItemIndex, 1);
      newList.splice(index, 0, draggedItem);
      setItems(newList);
      setState((prevState) => {
        return {
          ...prevState,
          draggingIndex: index,
        };
      });
    }
  };

  return (
    <>
      <ModelBrowserFilterBar
        onTextChange={(event) =>
          setState((prevState) => {
            return {
              ...prevState,
              filterBarText: event.target.value,
            };
          })
        }
        onTextClear={() =>
          setState((prevState) => {
            return {
              ...prevState,
              filterBarText: '',
            };
          })
        }
        text={state.filterBarText}
      />
      <span className={classes.title}>Selected</span>
      <div className={classes.borderStyle} onDrop={handleDropNewItem} onDragOver={handleDragOverNewItem}>
        <List dense component="div" role="list" data-testid="selected-items-list">
          {items
            .filter(({ label }) => {
              if (state.filterBarText === null || state.filterBarText === '') {
                return true;
              }
              const splitLabelWithTextToHighlight: string[] = splitText(label, state.filterBarText);
              return (
                splitLabelWithTextToHighlight.length > 1 ||
                (splitLabelWithTextToHighlight.length === 1 &&
                  splitLabelWithTextToHighlight[0].toLocaleLowerCase() === state.filterBarText.toLocaleLowerCase())
              );
            })
            .map(({ id }, index) => {
              const { kind, label, iconURL } = options.find((option) => option.id === id);
              const labelId = `transfer-list-item-${id}-label`;
              const selected = selectedItems.some((entry) => entry.id === id);
              const hover = state.hoveringItemId === id;
              return (
                <ListItem
                  key={id}
                  role="listitem"
                  className={selected ? classes.selected : classes.selectable}
                  onDragOver={(event) => handleDragOverOrder(event, index)}
                  onDragEnd={handleDragItemEnd}
                  onClick={(event) => onClick(event, { id, kind, label })}
                  onMouseEnter={() => handleMouseEnter(id)}
                  onMouseLeave={handleMouseLeave}
                  data-testid={label}>
                  <ListItemIcon
                    className={hover ? classes.dragIcon : classes.noDragIcon}
                    draggable
                    onDragStart={() => handleDragStartOrder(id, index)}
                    onDragEnd={handleDragEndOrder}>
                    <DragHandleIcon />
                  </ListItemIcon>
                  <ListItemIcon draggable onDragStart={() => handleDragItemStart(id)}>
                    {iconURL ? <img width="16" height="16" alt={''} src={httpOrigin + iconURL} /> : null}
                  </ListItemIcon>
                  <ListItemText
                    id={labelId}
                    draggable
                    onDragStart={() => handleDragItemStart(id)}
                    primary={<HighlightedLabel label={label} textToHighlight={state.filterBarText} />}
                    classes={{ primary: selected ? classes.selectedLabel : '' }}
                  />
                </ListItem>
              );
            })}
        </List>
      </div>
    </>
  );
};
