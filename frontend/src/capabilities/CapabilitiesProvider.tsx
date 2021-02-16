/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
import { useQuery } from '@apollo/client';
import IconButton from '@material-ui/core/IconButton';
import Snackbar from '@material-ui/core/Snackbar';
import CloseIcon from '@material-ui/icons/Close';
import { Capabilities, GQLQueryData } from 'capabilities/CapabilitiesProvider.types';
import gql from 'graphql-tag';
import React, { useEffect, useState } from 'react';

const getCapablitiliesQuery = gql`
  query getCapabilities {
    viewer {
      capabilities {
        pathOverrides
      }
    }
  }
`;

const defaultContextValue = { overrides: (path: string) => false };

export const CapabilitiesContext = React.createContext(defaultContextValue);

const emptyCapabilities: Capabilities = { pathOverrides: [] };

export const CapabilitiesProvider = ({ children }) => {
  const [message, setMessage] = useState<string | null>(null);
  const [capabilites, setCapabilities] = useState<Capabilities>(emptyCapabilities);
  const { data, loading, error } = useQuery<GQLQueryData>(getCapablitiliesQuery, {});
  useEffect(() => {
    if (!loading) {
      if (error) {
        setMessage('An unexpected error has occurred, please refresh the page');
      }
      if (data) {
        setCapabilities(data.viewer.capabilities);
      }
    }
  }, [data, loading, error]);

  const overrides = (path: string): boolean => {
    return capabilites.pathOverrides.filter((pattern) => path.match(pattern.replace(/\\\\/, '\\'))).length > 0;
  };

  const contextValue = {
    overrides,
  };

  return (
    <>
      <CapabilitiesContext.Provider value={contextValue}>{children}</CapabilitiesContext.Provider>
      <Snackbar
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'right',
        }}
        open={!!message}
        autoHideDuration={3000}
        onClose={() => setMessage(null)}
        message={message}
        action={
          <IconButton size="small" aria-label="close" color="inherit" onClick={() => setMessage(null)}>
            <CloseIcon fontSize="small" />
          </IconButton>
        }
        data-testid="error"
      />
    </>
  );
};

export const withCapabilities = (Child) => {
  return () => {
    return (
      <CapabilitiesProvider>
        <Child />
      </CapabilitiesProvider>
    );
  };
};
