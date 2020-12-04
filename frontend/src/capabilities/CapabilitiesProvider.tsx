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
import { useQuery } from '@apollo/client';
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

const emptyCapabilities = { pathOverrides: [] };

export const CapabilitiesProvider = ({ children }) => {
  const [capabilites, setCapabilities] = useState(emptyCapabilities);
  const { data, loading, error } = useQuery(getCapablitiliesQuery, {});
  useEffect(() => {
    if (!loading && !error) {
      setCapabilities(data?.viewer?.capabilities);
    }
  }, [data, loading, error]);

  const overrides = (path: string): boolean => {
    return capabilites.pathOverrides.filter((pattern) => path.match(pattern.replace(/\\\\/, '\\'))).length > 0;
  };

  const contextValue = {
    overrides,
  };

  return <CapabilitiesContext.Provider value={contextValue}>{children}</CapabilitiesContext.Provider>;
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
