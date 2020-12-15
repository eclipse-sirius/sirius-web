/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
import Container from '@material-ui/core/Container';
import { makeStyles } from '@material-ui/core/styles';
import { useBranding } from 'common/BrandingContext';
import { Navbar } from 'navbar/Navbar';
import PropTypes from 'prop-types';
import React from 'react';

const useViewStyles = makeStyles((theme) => ({
  view: {
    display: 'grid',
    gridTemplateColumns: '1fr',
    gridTemplateRows: 'min-content 1fr min-content',
    minHeight: '100vh',
  },
  main: {
    paddingTop: theme.spacing(3),
    paddingBottom: theme.spacing(3),
  },
}));

const propTypes = {
  children: PropTypes.node,
  condensed: PropTypes.bool,
};
export const View = ({ children, condensed }) => {
  const classes = useViewStyles();

  const { footer } = useBranding();
  const maxWidth = condensed ? 'sm' : 'xl';
  return (
    <div className={classes.view}>
      <Navbar />
      <main className={classes.main}>
        <Container maxWidth={maxWidth}>{children}</Container>
      </main>
      {footer}
    </div>
  );
};
View.propTypes = propTypes;
