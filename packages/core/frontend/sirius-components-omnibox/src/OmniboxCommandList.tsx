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
import { IconOverlay } from '@eclipse-sirius/sirius-components-core';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import { forwardRef } from 'react';
import { OmniboxCommandListProps } from './OmniboxCommandList.types';

export const OmniboxCommandList = forwardRef(
  ({ loading, data, onActionClick }: OmniboxCommandListProps, ref: React.ForwardedRef<HTMLUListElement>) => {
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
      const commands = data.viewer.omniboxCommands.edges.map((edge) => edge.node);
      if (commands.length > 0) {
        listItems = commands
          .map((node) => {
            return {
              id: node.id,
              icon: <IconOverlay iconURL={node.iconURLs} alt={node.label} />,
              label: node.label,
            };
          })
          .map((action) => {
            return (
              <ListItemButton
                key={action.id}
                data-testid={action.label}
                onClick={() => onActionClick(action)}
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
