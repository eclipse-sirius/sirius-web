/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import { Collections } from '@material-ui/icons';

import { RepresentationAreaProps } from './RepresentationsArea.types';

const useRepresentationAreaStyles = makeStyles((theme) => ({
  cardContent: {
    overflowY: 'auto',
    maxHeight: theme.spacing(50),
  },
}));

export const RepresentationsArea = ({ representations, setSelection }: RepresentationAreaProps) => {
  const classes = useRepresentationAreaStyles();

  return (
    <Card>
      <CardContent className={classes.cardContent}>
        <Typography variant="h6">Open an existing Representation</Typography>
        <Typography color="textSecondary">Select the representation to open</Typography>
        <List dense={true}>
          {representations.map((representation) => {
            return (
              <ListItem
                disableGutters
                button
                key={representation.id}
                data-testid={`onboard-open-${representation.label}`}
                onClick={() =>
                  setSelection({
                    entries: [{ id: representation.id, label: representation.label, kind: representation.kind }],
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
