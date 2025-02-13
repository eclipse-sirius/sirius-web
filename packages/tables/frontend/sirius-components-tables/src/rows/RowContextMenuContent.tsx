/*******************************************************************************
 * Copyright (c) 2025 CEA LIST.
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

import { gql, useQuery } from '@apollo/client';
import { IconOverlay, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import { SxProps, Theme } from '@mui/material/styles';
import Typography from '@mui/material/Typography';
import { useEffect } from 'react';
import {
  GQLGetAllRowContextMenuEntriesData,
  GQLGetAllRowContextMenuEntriesVariables,
  GQLRepresentationDescription,
  GQLRowContextMenuEntry,
  GQLTableDescription,
  RowContextMenuContentProps,
} from './RowContextMenuContent.types';
import { useInvokeRowContextMenuEntry } from './useInvokeRowContextMenuEntry';

const getRowContextMenuEntriesQuery = gql`
  query getAllRowContextMenuEntries($editingContextId: ID!, $representationId: ID!, $tableId: ID!, $rowId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $representationId) {
          description {
            __typename
            ... on TableDescription {
              rowContextMenuEntries(tableId: $tableId, rowId: $rowId) {
                __typename
                id
                label
                iconURLs
              }
            }
          }
        }
      }
    }
  }
`;

const isTableDescription = (
  gqlRepresentationDescription: GQLRepresentationDescription
): gqlRepresentationDescription is GQLTableDescription =>
  gqlRepresentationDescription.__typename === 'TableDescription';

export const RowContextMenuContent = ({
  editingContextId,
  representationId,
  tableId,
  row,
  readOnly,
  anchorEl,
  onClose,
}: RowContextMenuContentProps) => {
  const { loading, data, error } = useQuery<
    GQLGetAllRowContextMenuEntriesData,
    GQLGetAllRowContextMenuEntriesVariables
  >(getRowContextMenuEntriesQuery, {
    variables: {
      editingContextId,
      representationId,
      tableId,
      rowId: row.original.id,
    },
  });
  const { invokeRowContextMenuEntry } = useInvokeRowContextMenuEntry(
    editingContextId,
    representationId,
    tableId,
    row.original.id
  );

  const { addErrorMessage } = useMultiToast();

  useEffect(() => {
    if (error) {
      const { message } = error;
      addErrorMessage(message);
    }
  }, [error]);

  const handleClickContextMenuEntry = (menuEntry: GQLRowContextMenuEntry) => {
    invokeRowContextMenuEntry(menuEntry.id);
    onClose();
  };

  const representationDescription: GQLRepresentationDescription | null =
    data?.viewer.editingContext.representation?.description ?? null;

  const rowContextMenuEntries: GQLRowContextMenuEntry[] =
    representationDescription && isTableDescription(representationDescription)
      ? representationDescription.rowContextMenuEntries
      : [];

  if (loading) {
    return null;
  }

  const noEntryFoundStyle: SxProps<Theme> = (theme: Theme) => ({
    paddingX: theme.spacing(1),
  });

  return (
    <Menu
      anchorEl={anchorEl}
      disableScrollLock
      onClick={(event) => event.stopPropagation()}
      onClose={onClose}
      open
      data-testid={`row-context_menu-${row.original.headerLabel}`}>
      {rowContextMenuEntries.length > 0 ? (
        rowContextMenuEntries.map((entry) => (
          <MenuItem
            key={entry.id}
            onClick={(_) => handleClickContextMenuEntry(entry)}
            data-testid={`context-menu-entry-${entry.label}`}
            disabled={readOnly}
            aria-disabled>
            <ListItemIcon>
              {entry.iconURLs.length > 0 ? (
                <IconOverlay iconURL={entry.iconURLs} alt={entry.label} title={entry.label} />
              ) : (
                <div style={{ marginRight: '16px' }} />
              )}
            </ListItemIcon>
            <ListItemText primary={entry.label} />
          </MenuItem>
        ))
      ) : (
        <Typography sx={noEntryFoundStyle}>No entries found</Typography>
      )}
    </Menu>
  );
};
