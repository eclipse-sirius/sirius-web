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
import PropTypes from 'prop-types';
import React from 'react';
import { View } from 'views/View';
import { ModelersViewContainer } from './ModelersViewContainer';

const propTypes = {
  message: PropTypes.string.isRequired,
};
const useStyles = makeStyles((theme) => ({
  errorMessage: {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    justifyItems: 'center',
    paddingTop: theme.spacing(8),
    paddingBottom: theme.spacing(8),
  },
}));
export const ModelersErrorView = ({ message }) => {
  const classes = useStyles();
  return (
    <View>
      <ModelersViewContainer>
        <div className={classes.errorMessage}>
          <Typography>{message}</Typography>
        </div>
      </ModelersViewContainer>
    </View>
  );
};
ModelersErrorView.propTypes = propTypes;
