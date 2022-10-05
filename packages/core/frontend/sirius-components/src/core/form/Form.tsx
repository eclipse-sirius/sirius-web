/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
import { makeStyles } from '@material-ui/core/styles';
import PropTypes from 'prop-types';
import React from 'react';

const useFormStyles = makeStyles((theme) => ({
  form: {
    display: 'flex',
    flexDirection: 'column',
    paddingTop: theme.spacing(1),
    paddingLeft: theme.spacing(2),
    paddingRight: theme.spacing(2),
    '& > *': {
      marginBottom: theme.spacing(2),
    },
  },
}));

const propTypes = {
  children: PropTypes.node.isRequired,
};
export const Form = ({ children, ...props }) => {
  const classes = useFormStyles();
  return (
    <form {...props} className={classes.form}>
      {children}
    </form>
  );
};
Form.propTypes = propTypes;
