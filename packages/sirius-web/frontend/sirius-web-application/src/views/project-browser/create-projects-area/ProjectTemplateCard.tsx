/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import { ServerContext, ServerContextValue } from '@eclipse-sirius/sirius-components-core';
import Button from '@material-ui/core/Button';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CircularProgress from '@material-ui/core/CircularProgress';
import Tooltip from '@material-ui/core/Tooltip';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import AddIcon from '@material-ui/icons/Add';
import CloudUploadOutlinedIcon from '@material-ui/icons/CloudUploadOutlined';
import MoreHorizIcon from '@material-ui/icons/MoreHoriz';
import { useContext } from 'react';
import { Link as RouterLink } from 'react-router-dom';
import { ProjectTemplateCardProps, ShowAllTemplatesCardProps } from './ProjectTemplateCard.types';

const useProjectTemplateStyles = makeStyles((theme) => ({
  projectTemplateCard: {
    width: theme.spacing(30),
    height: theme.spacing(18),
    display: 'grid',
    gridTemplateRows: '1fr min-content',
  },
  templateCardContent: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    padding: 0,
  },
  templateCardActions: {
    minWidth: 0,
  },
  projectTemplateLabel: {
    textTransform: 'none',
    fontWeight: 400,
    textOverflow: 'ellipsis',
    overflow: 'hidden',
    whiteSpace: 'nowrap',
  },
}));

export const ProjectTemplateCard = ({ template, running, disabled, onCreateProject }: ProjectTemplateCardProps) => {
  const classes = useProjectTemplateStyles();
  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);
  return (
    <Button disabled={disabled} onClick={onCreateProject} data-testid={`create-template-${template.label}`}>
      <Card className={classes.projectTemplateCard}>
        <CardContent className={classes.templateCardContent}>
          {running ? (
            <CircularProgress />
          ) : (
            <img height="80px" alt={`New ${template.label}`} src={httpOrigin + template.imageURL} />
          )}
        </CardContent>
        <CardActions className={classes.templateCardActions}>
          <Tooltip title={template.label}>
            <Typography variant="body1" className={classes.projectTemplateLabel}>
              + {template.label}
            </Typography>
          </Tooltip>
        </CardActions>
      </Card>
    </Button>
  );
};

const useProjectCardStyles = makeStyles((theme) => ({
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
  blankProjectCard: {
    backgroundColor: theme.palette.primary.main,
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
  uploadProjectCard: {
    backgroundColor: theme.palette.divider,
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
  showAllTemplatesCardContent: {
    backgroundColor: theme.palette.divider,
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
}));

export const NewProjectCard = () => {
  const classes = useProjectCardStyles();
  return (
    <Button to={`/new/project`} component={RouterLink} data-testid="create">
      <Card className={classes.projectCard}>
        <CardContent className={classes.blankProjectCard}>
          <AddIcon className={classes.projectCardIcon} htmlColor="white" />
        </CardContent>
        <CardActions className={classes.projectCardActions}>
          <Tooltip title={'Blank project'}>
            <Typography variant="body1" className={classes.projectCardLabel}>
              + Blank project
            </Typography>
          </Tooltip>
        </CardActions>
      </Card>
    </Button>
  );
};

export const UploadProjectCard = () => {
  const classes = useProjectCardStyles();
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

export const ShowAllTemplatesCard = ({ onClick }: ShowAllTemplatesCardProps) => {
  const classes = useProjectCardStyles();
  return (
    <Button onClick={onClick} data-testid="show-all-templates">
      <Card className={classes.projectCard}>
        <CardContent className={classes.showAllTemplatesCardContent}>
          <MoreHorizIcon className={classes.projectCardIcon} htmlColor="white" />
        </CardContent>
        <CardActions className={classes.projectCardActions}>
          <Tooltip title={'Show all templates'}>
            <Typography variant="body1" className={classes.projectCardLabel}>
              Show all templates
            </Typography>
          </Tooltip>
        </CardActions>
      </Card>
    </Button>
  );
};
