/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo and others.
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

import Box from '@mui/material/Box';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';
import Typography from '@mui/material/Typography';
import { alpha } from '@mui/material/styles';
import { makeStyles } from 'tss-react/mui';
import { NodePanelInfosProps } from './NodePanelInfos.types';

const useStyles = makeStyles()((theme) => ({
  list: {
    marginTop: 0,
    marginBottom: 0,
    paddingTop: 0,
    paddingBottom: 0,
    paddingLeft: 0,
    paddingRight: 0,
  },
  listItem: {
    paddingTop: 0,
    paddingBottom: 0,
  },
  listItemText: {
    marginTop: 0,
    marginBottom: 0,
    wordWrap: 'break-word',
  },
  box: {
    minWidth: theme.spacing(32),
    maxWidth: theme.spacing(32),
    border: '1px solid black',
    borderRadius: theme.shape.borderRadius,
    backgroundColor: alpha(theme.palette.background.paper, 0.8),
  },
}));

export const NodePanelInfos = ({ title, node }: NodePanelInfosProps) => {
  const { classes } = useStyles();

  return (
    <Box className={classes.box} data-testid={'nodePanelInfos'}>
      <List dense={true} classes={{ root: classes.list }}>
        <ListItem classes={{ root: classes.listItem }}>
          <ListItemText classes={{ root: classes.listItemText }}>
            <Typography variant="h6">{title}</Typography>
          </ListItemText>
        </ListItem>
        <ListItem classes={{ root: classes.listItem }}>
          <ListItemText classes={{ root: classes.listItemText }} primary={`Type : Node`} />
        </ListItem>
        <ListItem classes={{ root: classes.listItem }}>
          <ListItemText classes={{ root: classes.listItemText }} primary={`Label : ${node?.data.insideLabel?.text}`} />
        </ListItem>
        <ListItem classes={{ root: classes.listItem }}>
          <ListItemText classes={{ root: classes.listItemText }} primary={`Node id : ${node?.id}`} />
        </ListItem>
        <ListItem classes={{ root: classes.listItem }}>
          <ListItemText classes={{ root: classes.listItemText }} primary={`Height : ${node?.height}`} />
        </ListItem>
        <ListItem classes={{ root: classes.listItem }}>
          <ListItemText classes={{ root: classes.listItemText }} primary={`Width : ${node?.width}`} />
        </ListItem>
        <ListItem classes={{ root: classes.listItem }}>
          <ListItemText classes={{ root: classes.listItemText }} primary={`Extent : ${node?.extent}`} />
        </ListItem>
        <ListItem classes={{ root: classes.listItem }}>
          <ListItemText classes={{ root: classes.listItemText }} primary={`x : ${node?.position.x}`} />
        </ListItem>
        <ListItem classes={{ root: classes.listItem }}>
          <ListItemText classes={{ root: classes.listItemText }} primary={`y : ${node?.position.y}`} />
        </ListItem>
        <ListItem classes={{ root: classes.listItem }}>
          <ListItemText
            classes={{ root: classes.listItemText }}
            primary={`TargetObjectId : ${node?.data.targetObjectId}`}
          />
        </ListItem>
        <ListItem classes={{ root: classes.listItem }}>
          <ListItemText
            classes={{ root: classes.listItemText }}
            primary={`TargetObjectKind : ${node?.data.targetObjectKind}`}
          />
        </ListItem>
      </List>
    </Box>
  );
};
