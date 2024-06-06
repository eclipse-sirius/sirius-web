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
import MoreHorizIcon from '@material-ui/icons/MoreHoriz';
import { useState } from 'react';
import { ProjectTemplatesModal } from './ProjectTemplatesModal';
import { ShowAllProjectTemplatesCardState } from './ShowAllProjectTemplatesCard.types';

const useShowAllProjectTemplatesCardStyles = makeStyles((theme) => ({
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

export const ShowAllProjectTemplatesCard = () => {
  const classes = useShowAllProjectTemplatesCardStyles();
  const [state, setState] = useState<ShowAllProjectTemplatesCardState>({
    modalDisplayed: null,
  });
  const showAllTemplatesModal = () => setState((prevState) => ({ ...prevState, modalDisplayed: 'SHOW_ALL_TEMPLATES' }));
  const closeModal = () => setState((prevState) => ({ ...prevState, modalDisplayed: null }));

  return (
    <>
      <Button onClick={showAllTemplatesModal} data-testid="show-all-templates">
        <Card className={classes.projectCard}>
          <CardContent className={classes.showAllTemplatesCardContent}>
            <MoreHorizIcon className={classes.projectCardIcon} htmlColor="white" />
          </CardContent>
          <CardActions className={classes.projectCardActions}>
            <Tooltip title="Show all templates">
              <Typography variant="body1" className={classes.projectCardLabel}>
                Show all templates
              </Typography>
            </Tooltip>
          </CardActions>
        </Card>
      </Button>

      {state.modalDisplayed === 'SHOW_ALL_TEMPLATES' ? <ProjectTemplatesModal onClose={closeModal} /> : null}
    </>
  );
};
