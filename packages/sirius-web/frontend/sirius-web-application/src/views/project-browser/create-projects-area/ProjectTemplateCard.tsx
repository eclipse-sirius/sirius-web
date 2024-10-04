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
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import CircularProgress from '@mui/material/CircularProgress';
import { useContext } from 'react';
import { makeStyles } from 'tss-react/mui';
import { CreateProjectAreaCard } from './CreateProjectAreaCard';
import { ProjectTemplateCardProps } from './ProjectTemplateCard.types';

const useProjectTemplateStyles = makeStyles()(() => ({
  button: {
    padding: '0px',
    margin: '0px',
  },
  projectCardContent: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
}));

export const ProjectTemplateCard = ({ template, running, disabled, onCreateProject }: ProjectTemplateCardProps) => {
  const { classes } = useProjectTemplateStyles();
  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);

  return (
    <Button
      disabled={disabled}
      onClick={onCreateProject}
      className={classes.button}
      data-testid={`create-template-${template.label}`}>
      <CreateProjectAreaCard title={'+ ' + template.label} description={template.label}>
        {running ? (
          <div className={classes.projectCardContent}>
            <CircularProgress />
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
