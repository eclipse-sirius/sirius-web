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
import { useSelection } from '@eclipse-sirius/sirius-components-core';
import Collections from '@mui/icons-material/Collections';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Typography from '@mui/material/Typography';
import { makeStyles } from 'tss-react/mui';
import { RepresentationAreaProps } from './RepresentationsArea.types';

const useRepresentationAreaStyles = makeStyles()((theme) => ({
  cardContent: {
    overflowY: 'auto',
    maxHeight: theme.spacing(50),
  },
  item: {
    padding: 0,
  },
}));

export const RepresentationsArea = ({ representations }: RepresentationAreaProps) => {
  const { setSelection } = useSelection();

  const { classes } = useRepresentationAreaStyles();

  return (
    <Card>
      <CardContent className={classes.cardContent}>
        <Typography variant="h6">Open an existing Representation</Typography>
        <Typography color="textSecondary">Select the representation to open</Typography>
        <List dense={true}>
          {representations
            .sort((a, b) => a.label.localeCompare(b.label))
            .map((representation) => {
              return (
                <ListItem
                  className={classes.item}
                  dense
                  disableGutters
                  button
                  key={representation.id}
                  data-testid={`onboard-open-${representation.label}`}
                  onClick={() =>
                    setSelection({
                      entries: [{ id: representation.id, kind: representation.kind }],
                    })
                  }>
                  <ListItemIcon>
                    <Collections htmlColor="primary" fontSize="small" />
                  </ListItemIcon>
                  <ListItemText primary={representation.label} />
                </ListItem>
              );
            })}
        </List>
      </CardContent>
    </Card>
  );
};
