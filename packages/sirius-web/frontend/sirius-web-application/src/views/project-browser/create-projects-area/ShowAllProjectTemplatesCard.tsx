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

import MoreHorizIcon from '@mui/icons-material/MoreHoriz';
import Button from '@mui/material/Button';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Tooltip from '@mui/material/Tooltip';
import Typography from '@mui/material/Typography';
import { useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { ProjectTemplatesModal } from './ProjectTemplatesModal';
import { ShowAllProjectTemplatesCardState } from './ShowAllProjectTemplatesCard.types';

const useShowAllProjectTemplatesCardStyles = makeStyles()((theme) => ({
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
  const { classes } = useShowAllProjectTemplatesCardStyles();
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
