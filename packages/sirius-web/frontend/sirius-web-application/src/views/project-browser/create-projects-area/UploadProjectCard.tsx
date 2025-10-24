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

import CloudUploadOutlinedIcon from '@mui/icons-material/CloudUploadOutlined';
import Button from '@mui/material/Button';
import { Link as RouterLink } from 'react-router-dom';
import { makeStyles } from 'tss-react/mui';
import { CreateProjectAreaCard } from './CreateProjectAreaCard';

const useUploadProjectCardStyles = makeStyles()((theme) => ({
  button: {
    padding: '0px',
    margin: '0px',
  },
  projectCardContent: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: theme.palette.divider,
  },
  projectCardIcon: {
    fontSize: theme.spacing(8),
  },
}));

export const UploadProjectCard = () => {
  const { classes } = useUploadProjectCardStyles();

  return (
    <Button to={`/upload/project`} component={RouterLink} className={classes.button} data-testid="upload">
      <CreateProjectAreaCard title="+ Upload project" description="Upload project">
        <div className={classes.projectCardContent}>
          <CloudUploadOutlinedIcon className={classes.projectCardIcon} htmlColor="white" />
        </div>
      </CreateProjectAreaCard>
    </Button>
  );
};
