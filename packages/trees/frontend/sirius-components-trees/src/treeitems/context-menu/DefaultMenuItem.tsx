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
import { gql, useLazyQuery, useMutation } from '@apollo/client';
import { IconOverlay, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import { useEffect } from 'react';
import { DefaultMenuItemProps } from './DefaultMenuItem.types';
import {
  GQLErrorPayload,
  GQLFetchTreeItemContextEntryDataData,
  GQLGetFetchTreeItemContextMenuEntryDataQueryVariables,
  GQLInvokeSingleClickTreeItemContextMenuEntryData,
  GQLInvokeSingleClickTreeItemContextMenuEntryInput,
  GQLInvokeSingleClickTreeItemContextMenuEntryPayload,
  GQLInvokeSingleClickTreeItemContextMenuEntryVariables,
} from './TreeItemContextMenu.types';
import { GQLTreeItemContextMenuEntry } from './useContextMenuEntries.types';

const invokeSingleClickTreeItemContextMenuEntryMutation = gql`
  mutation invokeSingleClickTreeItemContextMenuEntry($input: InvokeSingleClickTreeItemContextMenuEntryInput!) {
    invokeSingleClickTreeItemContextMenuEntry(input: $input) {
      __typename
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const getFetchTreeItemContextMenuEntryDataQuery = gql`
  query getFetchTreeItemContextMenuEntryDataQuery(
    $editingContextId: ID!
    $representationId: ID!
    $treeItemId: ID!
    $menuEntryId: ID!
  ) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $representationId) {
          description {
            ... on TreeDescription {
              fetchTreeItemContextMenuEntryData(treeItemId: $treeItemId, menuEntryId: $menuEntryId) {
                urlToFetch
                fetchKind
              }
            }
          }
        }
      }
    }
  }
`;

const isErrorPayload = (payload: GQLInvokeSingleClickTreeItemContextMenuEntryPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const DefaultMenuItem = ({ editingContextId, treeId, item, entry, readOnly, onClick }: DefaultMenuItemProps) => {
  const { addErrorMessage } = useMultiToast();

  const [getFetchData, { data, error }] = useLazyQuery<
    GQLFetchTreeItemContextEntryDataData,
    GQLGetFetchTreeItemContextMenuEntryDataQueryVariables
  >(getFetchTreeItemContextMenuEntryDataQuery);
  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }

    if (data) {
      const { urlToFetch, fetchKind } =
        data.viewer.editingContext.representation.description.fetchTreeItemContextMenuEntryData;
      if (fetchKind === 'DOWNLOAD') {
        window.location.href = urlToFetch;
      } else if (fetchKind === 'OPEN') {
        window.open(urlToFetch, '_blank', 'noopener,noreferrer');
      }
      onClick();
    }
  }, [data, error]);

  const invokeFetch = (menuEntryId: string) => {
    const variables: GQLGetFetchTreeItemContextMenuEntryDataQueryVariables = {
      editingContextId,
      representationId: treeId,
      treeItemId: item.id,
      menuEntryId,
    };
    getFetchData({ variables });
  };

  const [invokeSingleClickTreeItemContextMenuEntry, { data: invokeSingleClickData, error: invokeSingleClickError }] =
    useMutation<
      GQLInvokeSingleClickTreeItemContextMenuEntryData,
      GQLInvokeSingleClickTreeItemContextMenuEntryVariables
    >(invokeSingleClickTreeItemContextMenuEntryMutation);

  const invokeSingleClick = (menuEntryId: string) => {
    const input: GQLInvokeSingleClickTreeItemContextMenuEntryInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: treeId,
      treeItemId: item.id,
      menuEntryId,
    };
    invokeSingleClickTreeItemContextMenuEntry({ variables: { input } });
  };

  useEffect(() => {
    if (invokeSingleClickError) {
      addErrorMessage('An error has occurred while executing this action, please contact the server administrator');
    }
    if (invokeSingleClickData) {
      const { invokeSingleClickTreeItemContextMenuEntry } = invokeSingleClickData;
      if (isErrorPayload(invokeSingleClickTreeItemContextMenuEntry)) {
        addErrorMessage(invokeSingleClickTreeItemContextMenuEntry.message);
      }
    }
  }, [invokeSingleClickError, invokeSingleClickData]);

  const invokeContextMenuEntry = (menuEntry: GQLTreeItemContextMenuEntry) => {
    if (menuEntry.__typename === 'FetchTreeItemContextMenuEntry') {
      invokeFetch(menuEntry.id);
    } else if (menuEntry.__typename === 'SingleClickTreeItemContextMenuEntry') {
      invokeSingleClick(menuEntry.id);
      onClick();
    }
  };

  return (
    <MenuItem
      onClick={() => invokeContextMenuEntry(entry)}
      data-testid={`context-menu-entry-${entry.label}`}
      disabled={readOnly}
      aria-disabled>
      <ListItemIcon>
        {entry.iconURL.length > 0 ? (
          <IconOverlay iconURL={entry.iconURL} alt={entry.label} title={entry.label} />
        ) : (
          <div style={{ marginRight: '16px' }} />
        )}
      </ListItemIcon>
      <ListItemText primary={entry.label} />
    </MenuItem>
  );
};
