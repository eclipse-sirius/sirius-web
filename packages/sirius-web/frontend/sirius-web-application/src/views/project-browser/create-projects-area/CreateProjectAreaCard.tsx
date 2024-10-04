/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Tooltip from '@mui/material/Tooltip';
import Typography from '@mui/material/Typography';
import { makeStyles } from 'tss-react/mui';
import { CreateProjectAreaCardProps } from './CreateProjectAreaCard.types';

const useCreateProjectAreaCardStyles = makeStyles()(() => ({
  card: {
    display: 'grid',
    gridTemplateColumns: '150px',
    gridTemplateRows: '1fr min-content',
    width: '150px',
  },
  content: {
    padding: '0px',
    width: '150px',
    height: '110px',
    display: 'grid',
    gridTemplateColumns: '1fr',
    gridTemplateRows: '1fr',
    alignItems: 'stretch',
    justifyItems: 'stretch',
  },
  label: {
    textTransform: 'none',
    fontWeight: 400,
    textOverflow: 'ellipsis',
    overflow: 'hidden',
    whiteSpace: 'nowrap',
  },
}));

export const CreateProjectAreaCard = ({ title, description, children }: CreateProjectAreaCardProps) => {
  const { classes } = useCreateProjectAreaCardStyles();
  return (
    <Card className={classes.card}>
      <CardContent className={classes.content}>{children}</CardContent>
      <CardActions>
        <Tooltip title={description}>
          <Typography variant="body1" className={classes.label}>
            {title}
          </Typography>
        </Tooltip>
      </CardActions>
    </Card>
  );
};
