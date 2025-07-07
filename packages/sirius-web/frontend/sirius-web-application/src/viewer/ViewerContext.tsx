/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import React from 'react';
import { ViewerContextProviderProps, ViewerContextValue } from './ViewerContext.types';
import { useViewer } from './useViewer';

const defaultValue: ViewerContextValue = {
  viewer: {
    capabilities: {
      projects: {
        canCreate: false,
        canUpload: false,
      },
      libraries: {
        canView: false,
      },
    },
  },
};

export const ViewerContext = React.createContext<ViewerContextValue>(defaultValue);

export const ViewerContextProvider = ({ children }: ViewerContextProviderProps) => {
  const { data, loading } = useViewer();

  if (loading || !data) {
    return null;
  }

  return <ViewerContext.Provider value={data}>{children}</ViewerContext.Provider>;
};
