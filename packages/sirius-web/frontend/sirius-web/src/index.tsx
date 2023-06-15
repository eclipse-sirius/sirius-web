/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
  ToastContext,
} from '@eclipse-sirius/sirius-components-core';
import { DiagramRepresentation } from '@eclipse-sirius/sirius-components-diagrams';
import { DiagramRepresentation as ReactFlowDiagramRepresentation } from '@eclipse-sirius/sirius-components-diagrams-reactflow';
import { FormDescriptionEditorRepresentation } from '@eclipse-sirius/sirius-components-formdescriptioneditors';
import {
  FormRepresentation,
  GQLWidget,
  PropertySectionContext,
  WidgetContribution,
} from '@eclipse-sirius/sirius-components-forms';
import {
  ReferenceIcon,
  ReferencePreview,
  ReferencePropertySection,
} from '@eclipse-sirius/sirius-components-widget-reference';
import CssBaseline from '@material-ui/core/CssBaseline';
import IconButton from '@material-ui/core/IconButton';
import { createTheme, ThemeProvider } from '@material-ui/core/styles';
import CloseIcon from '@material-ui/icons/Close';
import LinearScaleOutlinedIcon from '@material-ui/icons/LinearScaleOutlined';
import { SnackbarProvider, useSnackbar } from 'notistack';
import ReactDOM from 'react-dom';
import { BrowserRouter } from 'react-router-dom';
import { ApolloGraphQLClient } from './ApolloGraphQLClient';
import { httpOrigin } from './core/URL';
import './fonts.css';
import { Main } from './main/Main';
import './reset.css';
import './Sprotty.css';
import './variables.css';
import { SliderPreview } from './widgets/SliderPreview';
import { SliderPropertySection } from './widgets/SliderPropertySection';

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
    if (type === 'Diagram' && representation.label.endsWith('__REACT_FLOW')) {
      return ReactFlowDiagramRepresentation;
    } else if (type === 'Diagram') {
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

const propertySectionsRegistry = {
  getComponent: (widget: GQLWidget) => {
    if (widget.__typename === 'Slider') {
      return SliderPropertySection;
    } else if (widget.__typename === 'ReferenceWidget') {
      return ReferencePropertySection;
    }
  },
  getPreviewComponent: (widget: GQLWidget) => {
    if (widget.__typename === 'Slider') {
      return SliderPreview;
    } else if (widget.__typename === 'ReferenceWidget') {
      return ReferencePreview;
    }
  },
  getWidgetContributions: () => {
    const sliderWidgetContribution: WidgetContribution = {
      name: 'Slider',
      fields: `label iconURL minValue maxValue currentValue`,
      icon: <LinearScaleOutlinedIcon />,
    };
    const referenceWidget: WidgetContribution = {
      name: 'ReferenceWidget',
      fields: 'id label iconURL container manyValued referenceValues { id label kind iconURL }',
      icon: <ReferenceIcon />,
    };
    return [sliderWidgetContribution, referenceWidget];
  },
};

const propertySectionRegistryValue = {
  propertySectionsRegistry,
};

const ToastCloseButton = ({ toastKey }) => {
  const { closeSnackbar } = useSnackbar();

  return (
    <IconButton size="small" aria-label="close" color="inherit" onClick={() => closeSnackbar(toastKey)}>
      <CloseIcon fontSize="small" />
    </IconButton>
  );
};

ReactDOM.render(
  <ApolloProvider client={ApolloGraphQLClient}>
    <BrowserRouter>
      <ThemeProvider theme={siriusWebTheme}>
        <CssBaseline />
        <ServerContext.Provider value={{ httpOrigin }}>
          <SnackbarProvider
            maxSnack={5}
            anchorOrigin={{
              vertical: 'bottom',
              horizontal: 'right',
            }}
            action={(key) => <ToastCloseButton toastKey={key} />}
            autoHideDuration={10000}
            data-testid="toast">
            <ToastContext.Provider
              value={{
                useToast: () => {
                  return useSnackbar();
                },
              }}>
              <RepresentationContext.Provider value={representationContextValue}>
                <PropertySectionContext.Provider value={propertySectionRegistryValue}>
                  <div style={style}>
                    <Main />
                  </div>
                </PropertySectionContext.Provider>
              </RepresentationContext.Provider>
            </ToastContext.Provider>
          </SnackbarProvider>
        </ServerContext.Provider>
      </ThemeProvider>
    </BrowserRouter>
  </ApolloProvider>,
  document.getElementById('root')
);
