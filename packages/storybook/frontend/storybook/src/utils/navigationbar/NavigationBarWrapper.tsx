/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { DisplayLibraryNavbar, NavigationBar, siriusWebTheme } from '@eclipse-sirius/sirius-web-application';
import { ThemeProvider } from '@mui/material/styles';
import Typography from '@mui/material/Typography';
import i18n from 'i18next';
import { ReactNode } from 'react';
import { I18nextProvider, initReactI18next } from 'react-i18next';
import { MemoryRouter } from 'react-router-dom';
import { makeStyles } from 'tss-react/mui';

if (!i18n.isInitialized) {
  i18n.use(initReactI18next).init({});
}

const useNavbarStyles = makeStyles()((theme) => ({
  titleLabel: {
    marginRight: theme.spacing(2),
    overflow: 'hidden',
    textOverflow: 'ellipsis',
    whiteSpace: 'nowrap',
    maxWidth: '100ch',
  },
}));

export const NavigationBarWrapper = ({ children }: { children?: ReactNode }) => {
  const { classes } = useNavbarStyles();
  return (
    <MemoryRouter>
      <I18nextProvider i18n={i18n}>
        <ThemeProvider theme={siriusWebTheme}>
          <NavigationBar>
            <Typography variant="h6" noWrap className={classes.titleLabel} data-testid={`navbar-title`}>
              {children}
            </Typography>
          </NavigationBar>
        </ThemeProvider>
      </I18nextProvider>
    </MemoryRouter>
  );
};

export const DisplayLibraryNavbarWrapper = ({}: {}) => {
  return (
    <MemoryRouter>
      <I18nextProvider i18n={i18n}>
        <ThemeProvider theme={siriusWebTheme}>
          <DisplayLibraryNavbar />
        </ThemeProvider>
      </I18nextProvider>
    </MemoryRouter>
  );
};
