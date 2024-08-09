/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
import { IconOverlay, useSelection } from '@eclipse-sirius/sirius-components-core';
import CropDinIcon from '@mui/icons-material/CropDin';
import List from '@mui/material/List';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import { makeStyles } from 'tss-react/mui';

import { ListItemButton } from '@mui/material';
import { SelectionDialogListViewProps } from './SelectionDialogListView.types';

const useSelectionObjectModalStyles = makeStyles()((_theme) => ({
  root: {
    width: '100%',
    position: 'relative',
    overflow: 'auto',
    maxHeight: 300,
  },
}));

export const SelectionDialogListView = ({ selection }: SelectionDialogListViewProps) => {
  const { classes } = useSelectionObjectModalStyles();
  const { setSelection, selection: selectedObjects } = useSelection();

  const handleListItemClick = (selectedObjectId: string) => {
    setSelection({
      entries: [
        {
          id: selectedObjectId,
          kind: '', //Not used in this context.
        },
      ],
    });
  };

  return (
    <List className={classes.root}>
      {selection?.objects.map((selectionObject) => (
        <ListItemButton
          key={`item-${selectionObject.id}`}
          selected={selectedObjects.entries.find((entry) => entry.id === selectionObject.id) !== undefined}
          onClick={() => handleListItemClick(selectionObject.id)}
          data-testid={selectionObject.label}>
          <ListItemIcon>
            {selectionObject.iconURL.length > 0 ? (
              <IconOverlay
                iconURL={selectionObject.iconURL}
                alt={selectionObject.label}
                customIconHeight={24}
                customIconWidth={24}
              />
            ) : (
              <CropDinIcon style={{ fontSize: 24 }} />
            )}
          </ListItemIcon>
          <ListItemText primary={selectionObject.label} />
        </ListItemButton>
      ))}
    </List>
  );
};
