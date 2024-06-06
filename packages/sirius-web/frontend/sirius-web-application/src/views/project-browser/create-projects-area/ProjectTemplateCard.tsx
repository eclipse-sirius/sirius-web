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
import { useContext } from 'react';
import { ProjectTemplateCardProps } from './ProjectTemplateCard.types';

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
