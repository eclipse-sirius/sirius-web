/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
 * This program and the accompanying materials
 * are made available under the erms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
import { ApolloProvider } from '@apollo/client';
import CssBaseline from '@material-ui/core/CssBaseline';
import { createMuiTheme, ThemeProvider } from '@material-ui/core/styles';
import { ApolloGraphQLClient } from 'ApolloGraphQLClient';
import { Main } from 'main/Main';
import ReactDOM from 'react-dom';
import { theme } from '@eclipse-sirius/sirius-components';
import { BrowserRouter } from 'react-router-dom';
import './fonts.css';
import './reset.css';
import './Sprotty.css';
import './variables.css';

const baseTheme = createMuiTheme({
  ...theme,
  palette: {
    type: 'light',
    primary: {
      main: '#BE1A78',
      dark: '#851254',
      light: '#CB4793'
    },
    secondary: {
      main: '#261E58',
      dark: '#1A153D',
      light: '#514B79'
    },
    text: {
      primary: '#261E58',
      disabled: '#B3BFC5',
      hint: '#B3BFC5'
    },
    error: {
      main: '#DE1000',
      dark: '#9B0B00',
      light: '#E43F33'
    },
    divider: '#B3BFC5'
  },
  props: {
    MuiAppBar: {
      color: 'secondary'
    }
  },
  overrides: {
    MuiSnackbarContent: {
      root: {
        backgroundColor: '#7269A4'
      }
    }
  }
});

const siriusWebTheme = createMuiTheme(
  {
    overrides: {
      MuiAvatar: {
        colorDefault: {
          backgroundColor: baseTheme.palette.primary.main
        }
      }
    }
  },
  baseTheme
);

const style = {
  display: 'grid',
  gridTemplateColumns: '1fr',
  gridTemplateRows: '1fr',
  minHeight: '100vh'
};

ReactDOM.render(
  <ApolloProvider client={ApolloGraphQLClient}>
    <BrowserRouter>
      <ThemeProvider theme={siriusWebTheme}>
        <CssBaseline />
        <div style={style}>
          <Main />
        </div>
      </ThemeProvider>
    </BrowserRouter>
  </ApolloProvider>,
  document.getElementById('root')
);
