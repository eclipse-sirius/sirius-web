/*******************************************************************************
 * Copyright (c) 2021.
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
import { Grid, Typography } from '@material-ui/core';
import Button from '@material-ui/core/Button';
import { Link } from 'core/link/Link';
import React from 'react';
import { useParams } from 'react-router-dom';
import { View } from 'views/View';

export const ModelersEmptyView = () => {
  const { projectId } = useParams();
  return (
    <View>
      <Grid container direction="column" justify="flex-start" alignItems="center" spacing={3}>
        <Grid item>
          <Typography variant="h4">There are no modelers yet</Typography>
        </Grid>
        <Grid item>
          <Typography variant="subtitle1">Start creating your first modeler and it will appear here</Typography>
        </Grid>
        <Grid item>
          <Link to={`/projects/${projectId}/new/modeler`} data-testid="create-link">
            <Button data-testid="create" color="primary" variant="contained">
              New
            </Button>
          </Link>
        </Grid>
      </Grid>
    </View>
  );
};
