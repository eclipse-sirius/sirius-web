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

import Button from '@material-ui/core/Button';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Tooltip from '@material-ui/core/Tooltip';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import CloudUploadOutlinedIcon from '@material-ui/icons/CloudUploadOutlined';
import { Link as RouterLink } from 'react-router-dom';

const useUploadProjectCardStyles = makeStyles((theme) => ({
  projectCard: {
    width: theme.spacing(30),
    height: theme.spacing(18),
    display: 'grid',
    gridTemplateRows: '1fr min-content',
  },
  projectCardActions: {
    minWidth: 0,
  },
  projectCardLabel: {
    textTransform: 'none',
    fontWeight: 400,
    textOverflow: 'ellipsis',
    overflow: 'hidden',
    whiteSpace: 'nowrap',
  },
  projectCardIcon: {
    fontSize: theme.spacing(8),
  },
  uploadProjectCard: {
    backgroundColor: theme.palette.divider,
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
}));

export const UploadProjectCard = () => {
  const classes = useUploadProjectCardStyles();
  return (
    <Button to={`/upload/project`} component={RouterLink} data-testid="upload">
      <Card className={classes.projectCard}>
        <CardContent className={classes.uploadProjectCard}>
          <CloudUploadOutlinedIcon className={classes.projectCardIcon} htmlColor="white" />
        </CardContent>
        <CardActions className={classes.projectCardActions}>
          <Tooltip title={'Upload project'}>
            <Typography variant="body1" className={classes.projectCardLabel}>
              + Upload project
            </Typography>
          </Tooltip>
        </CardActions>
      </Card>
    </Button>
  );
};
