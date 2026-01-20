/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
import AddIcon from '@mui/icons-material/Add';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import { useContext } from 'react';
import { Link as RouterLink } from 'react-router-dom';
import { makeStyles } from 'tss-react/mui';
import { CreateProjectAreaCard } from './CreateProjectAreaCard';
import { NewProjectCardProps } from './NewProjectCard.types';

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

/**
 * Displays a card to trigger the project creation process for a specific project template
 * (which may be the default 'blank' template).
 */
export const NewProjectCard = ({ url, template }: NewProjectCardProps) => {
  const { classes } = useNewProjectCardStyles();
  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);

  return (
    <Button
      to={url}
      component={RouterLink}
      className={classes.button}
      data-testid={`create-project-from-template-${template.label}`}>
      <CreateProjectAreaCard title={'+ ' + template.label} description={template.label}>
        {template.id === 'blank-project' ? (
          <div className={classes.projectCardContent}>
            <AddIcon className={classes.projectCardIcon} htmlColor="white" />
          </div>
        ) : (
          <Box
            sx={{
              backgroundImage: `url('${httpOrigin + template.imageURL}')`,
              backgroundSize: 'cover',
            }}
          />
        )}
      </CreateProjectAreaCard>
    </Button>
  );
};
