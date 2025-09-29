/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import { IconOverlay, Selection, useSelection } from '@eclipse-sirius/sirius-components-core';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import { forwardRef, useContext } from 'react';
import { OmniboxContext } from './OmniboxContext';
import { OmniboxContextValue } from './OmniboxContext.types';
import { ObjectAction, OmniboxObjectListProps } from './OmniboxObjectList.types';

export const OmniboxObjectList = forwardRef(
  ({ loading, data, onClose }: OmniboxObjectListProps, ref: React.ForwardedRef<HTMLUListElement>) => {
    const { setSelection } = useSelection();
    const { applySelection } = useContext<OmniboxContextValue>(OmniboxContext);

    const handleListItemKeyDown: React.KeyboardEventHandler<HTMLDivElement> = (event) => {
      if (event.key === 'ArrowDown') {
        const nextListItemButton = event.currentTarget.nextSibling;
        if (nextListItemButton instanceof HTMLElement) {
          nextListItemButton.focus();
        }
      } else if (event.key === 'ArrowUp') {
        const previousListItemButton = event.currentTarget.previousSibling;
        if (previousListItemButton instanceof HTMLElement) {
          previousListItemButton.focus();
        }
      }
    };

    const handleOnActionClick = (action: ObjectAction) => {
      const newSelection: Selection = { entries: [{ id: action.id }] };
      setSelection(newSelection);
      applySelection(newSelection);
      onClose();
    };

    let listItems: JSX.Element[] = [];
    if (loading) {
      listItems = [
        <ListItem key={'loading-action-key'}>
          <ListItemText sx={{ color: (theme) => theme.palette.text.disabled }} data-testid="fetch-omnibox-result">
            Fetching result...
          </ListItemText>
        </ListItem>,
      ];
    }
    if (!loading && data) {
      const objects = data.viewer.omniboxSearch;
      if (objects.length > 0) {
        listItems = objects
          .map((node) => {
            return {
              id: node.id,
              icon: <IconOverlay iconURL={node.iconURLs} alt={node.kind} />,
              kind: node.kind,
              label: node.label,
            };
          })
          .map((action) => {
            return (
              <ListItemButton
                key={action.id}
                data-testid={action.label}
                onClick={() => handleOnActionClick(action)}
                onKeyDown={handleListItemKeyDown}>
                <ListItemIcon>{action.icon}</ListItemIcon>
                <ListItemText sx={{ whiteSpace: 'nowrap', textOverflow: 'ellipsis' }}>{action.label}</ListItemText>
              </ListItemButton>
            );
          });
      } else {
        listItems = [
          <ListItem key={'no-result-key'}>
            <ListItemText data-testid="omnibox-no-result">No result</ListItemText>
          </ListItem>,
        ];
      }
    }

    return (
      <List ref={ref} dense disablePadding>
        {listItems}
      </List>
    );
  }
);
