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
import { ApolloProvider } from '@apollo/client';
import {
  Representation,
  RepresentationComponent,
  RepresentationContext,
  ServerContext,
  theme,
} from '@eclipse-sirius/sirius-components-core';
import { DiagramRepresentation } from '@eclipse-sirius/sirius-components-diagrams';
import { FormDescriptionEditorRepresentation } from '@eclipse-sirius/sirius-components-formdescriptioneditors';
import { FormRepresentation } from '@eclipse-sirius/sirius-components-forms';
import CssBaseline from '@material-ui/core/CssBaseline';
import { createTheme, ThemeProvider } from '@material-ui/core/styles';
import ReactDOM from 'react-dom';
import { BrowserRouter } from 'react-router-dom';
import { ApolloGraphQLClient } from './ApolloGraphQLClient';
import { httpOrigin } from './core/URL';
import './fonts.css';
import { Main } from './main/Main';
import './reset.css';
import './Sprotty.css';
import './variables.css';

const baseTheme = createTheme({
  ...theme,
  palette: {
    type: 'light',
    primary: {
      main: '#BE1A78',
      dark: '#851254',
      light: '#CB4793',
    },
    secondary: {
      main: '#261E58',
      dark: '#1A153D',
      light: '#514B79',
    },
    text: {
      primary: '#261E58',
      disabled: '#B3BFC5',
      hint: '#B3BFC5',
    },
    error: {
      main: '#DE1000',
      dark: '#9B0B00',
      light: '#E43F33',
    },
    divider: '#B3BFC5',
    navigation: {
      leftBackground: '#BE1A7880',
      rightBackground: '#261E5880',
    },
  },
  props: {
    MuiAppBar: {
      color: 'secondary',
    },
  },
  overrides: {
    MuiSnackbarContent: {
      root: {
        backgroundColor: '#7269A4',
      },
    },
  },
});

const siriusWebTheme = createTheme(
  {
    overrides: {
      MuiAvatar: {
        colorDefault: {
          backgroundColor: baseTheme.palette.primary.main,
        },
      },
    },
  },
  baseTheme
);

const style = {
  display: 'grid',
  gridTemplateColumns: '1fr',
  gridTemplateRows: '1fr',
  minHeight: '100vh',
};

const registry = {
  getComponent: (representation: Representation): RepresentationComponent | null => {
    const query = representation.kind.substring(representation.kind.indexOf('?') + 1, representation.kind.length);
    const params = new URLSearchParams(query);
    const type = params.get('type');
    if (type === 'Diagram') {
      return DiagramRepresentation;
    } else if (type === 'Form') {
      return FormRepresentation;
    } else if (type === 'FormDescriptionEditor') {
      return FormDescriptionEditorRepresentation;
    }
    return null;
  },
};

const representationContextValue = {
  registry,
};

ReactDOM.render(
  <ApolloProvider client={ApolloGraphQLClient}>
    <BrowserRouter>
      <ThemeProvider theme={siriusWebTheme}>
        <CssBaseline />
        <ServerContext.Provider value={{ httpOrigin }}>
          <RepresentationContext.Provider value={representationContextValue}>
            <div style={style}>
              <Main />
            </div>
          </RepresentationContext.Provider>
        </ServerContext.Provider>
      </ThemeProvider>
    </BrowserRouter>
  </ApolloProvider>,
  document.getElementById('root')
);
