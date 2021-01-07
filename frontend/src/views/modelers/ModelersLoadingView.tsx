/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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

import { makeStyles } from '@material-ui/core';
import Typography from '@material-ui/core/Typography';
import React from 'react';
import { View } from 'views/View';
import { ModelersViewContainer } from './ModelersViewContainer';

const useStyles = makeStyles((theme) => ({
  message: {
    display: 'grid',
    gridTemplateColumns: '1fr',
    gridTemplateRows: '1fr',
    justifyItems: 'center',
    paddingTop: theme.spacing(8),
    paddingBottom: theme.spacing(8),
  },
}));
export const ModelersLoadingView = () => {
  const classes = useStyles();
  return (
    <View>
      <ModelersViewContainer>
        <div className={classes.message}>
          <Typography>Loading</Typography>
        </div>
      </ModelersViewContainer>
    </View>
  );
};
