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
import { IconOverlay, useData } from '@eclipse-sirius/sirius-components-core';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import { forwardRef } from 'react';
import { OmniboxCommandListProps } from './OmniboxCommandList.types';
import { omniboxCommandOverrideContributionExtensionPoint } from './OmniboxExtensionPoints';
import { OmniboxCommandOverrideContribution } from './OmniboxExtensionPoints.types';
import { GQLOmniboxCommand } from './useWorkbenchOmniboxCommands.types';

export const OmniboxCommandList = forwardRef(
  (
    { loading, commands, onClose, onModeChanged, onCommandClick }: OmniboxCommandListProps,
    ref: React.ForwardedRef<HTMLUListElement>
  ) => {
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

    const handleOnActionClick = (command: GQLOmniboxCommand) => {
      if (command.id === 'search') {
        onModeChanged('Search');
      }
      onCommandClick(command);
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

    const { data: omniboxCommandOverrideContributions } = useData<OmniboxCommandOverrideContribution[]>(
      omniboxCommandOverrideContributionExtensionPoint
    );

    if (!loading && commands) {
      if (commands.length > 0) {
        listItems = commands.map((command) => {
          const CommandOverride = omniboxCommandOverrideContributions
            .filter((contribution) => contribution.canHandle(command))
            .map((contribution) => contribution.component)[0];
          if (CommandOverride) {
            return (
              <CommandOverride key={command.id} command={command} onClose={onClose} onKeyDown={handleListItemKeyDown} />
            );
          } else {
            return (
              <ListItemButton
                key={command.id}
                data-testid={command.label}
                onClick={() => handleOnActionClick(command)}
                onKeyDown={handleListItemKeyDown}>
                <ListItemIcon>
                  <IconOverlay iconURLs={command.iconURLs} alt={command.label} />
                </ListItemIcon>
                <ListItemText sx={{ whiteSpace: 'nowrap', textOverflow: 'ellipsis' }}>{command.label}</ListItemText>
              </ListItemButton>
            );
          }
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
