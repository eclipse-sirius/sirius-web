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

import { useComponent, useData } from '@eclipse-sirius/sirius-components-core';
import ErrorOutlineIcon from '@mui/icons-material/ErrorOutline';
import Container from '@mui/material/Container';
import Link from '@mui/material/Link';
import Paper from '@mui/material/Paper';
import Typography from '@mui/material/Typography';
import { Link as RouterLink, useParams } from 'react-router-dom';
import { makeStyles } from 'tss-react/mui';
import { footerExtensionPoint } from '../../footer/FooterExtensionPoints';
import { NavigationBar } from '../../navigationBar/NavigationBar';
import { ErrorMessageProvider, ErrorViewParams } from './ErrorView.types';
import { errorMessageProvidersExtensionPoint } from './ErrorViewExtensionPoints';

const useErrorViewStyles = makeStyles()((theme) => ({
  errorView: {
    display: 'grid',
    gridTemplateColumns: '1fr',
    gridTemplateRows: 'min-content 1fr min-content',
    minHeight: '100vh',
  },
  main: {
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    gap: theme.spacing(2),
    height: '100%',
  },
  error: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    gap: theme.spacing(4),
    paddingTop: theme.spacing(3),
    paddingLeft: theme.spacing(2),
    paddingRight: theme.spacing(2),
    paddingBottom: theme.spacing(3),
    minWidth: '500px',
  },
  title: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    gap: theme.spacing(1),
  },
}));

const messageProvider = (code: string, errorMessageProviders: ErrorMessageProvider[]): string => {
  let message: string | null = null;

  let index: number = 0;
  while (message === null && index < errorMessageProviders.length) {
    const errorMessageProvider = errorMessageProviders[index];
    message = errorMessageProvider(code);

    index = index + 1;
  }

  // Fallback for the default messages
  if (message === null) {
    message = 'An unexpected error has occurred, please contact the server administrator for additional information';

    if (code === '404') {
      message = 'The content that you are looking for has not been found';
    }
  }

  return message;
};

export const ErrorView = () => {
  const { code } = useParams<ErrorViewParams>();
  const { classes } = useErrorViewStyles();

  const { data: errorMessageProviders } = useData(errorMessageProvidersExtensionPoint);
  const message: string = messageProvider(code, errorMessageProviders);

  const { Component: Footer } = useComponent(footerExtensionPoint);

  return (
    <div className={classes.errorView}>
      <NavigationBar />
      <Container maxWidth="sm">
        <div className={classes.main}>
          <Paper className={classes.error}>
            <div className={classes.title}>
              <ErrorOutlineIcon sx={{ fontSize: 40 }} />
              <Typography variant="h2" align="center">
                Error {code}
              </Typography>
            </div>
            <Typography variant="body1" sx={{ fontSize: '1rem', textAlign: 'justify' }}>
              {message}
            </Typography>
          </Paper>
          <Link variant="body1" component={RouterLink} to="/projects">
            Back to the homepage
          </Link>
        </div>
      </Container>
      <Footer />
    </div>
  );
};
