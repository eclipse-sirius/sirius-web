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
import SearchIcon from '@mui/icons-material/Search';
import List from '@mui/material/List';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import { forwardRef } from 'react';
import { OmniboxSearchListProps } from './OmniboxSearchList.types';

export const OmniboxSearchList = forwardRef(
  ({ previousSearches, onItemSelected }: OmniboxSearchListProps, ref: React.ForwardedRef<HTMLUListElement>) => {
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

    let listItems: JSX.Element[] = [];
    if (previousSearches) {
      if (previousSearches.length > 0) {
        listItems = previousSearches
          .map((node) => {
            return {
              id: node + 'PreviousSearchedItem',
              label: `Search '${node}'`,
              search: node,
              testId: `previous_search_${node}`,
            };
          })
          .map((node) => {
            return (
              <ListItemButton
                key={node.id}
                data-testid={node.testId}
                onClick={() => onItemSelected(node.search)}
                onKeyDown={handleListItemKeyDown}>
                <ListItemIcon>
                  <SearchIcon />
                </ListItemIcon>
                <ListItemText sx={{ whiteSpace: 'nowrap', textOverflow: 'ellipsis' }}>{node.label}</ListItemText>
              </ListItemButton>
            );
          });
      }
    }

    return (
      <List ref={ref} dense disablePadding data-testid="search-history-result">
        {listItems}
      </List>
    );
  }
);
