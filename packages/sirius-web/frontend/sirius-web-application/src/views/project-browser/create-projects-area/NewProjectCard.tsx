/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import AddIcon from '@mui/icons-material/Add';
import Button from '@mui/material/Button';
import { Link as RouterLink } from 'react-router-dom';
import { makeStyles } from 'tss-react/mui';
import { CreateProjectAreaCard } from './CreateProjectAreaCard';

const useNewProjectCardStyles = makeStyles()((theme) => ({
  button: {
    padding: '0px',
    margin: '0px',
  },
  projectCardContent: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: theme.palette.primary.main,
  },
  projectCardIcon: {
    fontSize: theme.spacing(8),
  },
}));

export const NewProjectCard = () => {
  const { classes } = useNewProjectCardStyles();

  return (
    <Button to={`/new/project`} component={RouterLink} className={classes.button} data-testid="create">
      <CreateProjectAreaCard title="+ Blank project" description="Blank project">
        <div className={classes.projectCardContent}>
          <AddIcon className={classes.projectCardIcon} htmlColor="white" />
        </div>
      </CreateProjectAreaCard>
    </Button>
  );
};
