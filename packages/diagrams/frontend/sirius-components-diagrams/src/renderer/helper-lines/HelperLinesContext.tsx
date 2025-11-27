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
import React, { useState } from 'react';
import { HelperLinesContextValue, HelperLinesContextProviderProps } from './HelperLinesContext.types';

const defaultContextValue: HelperLinesContextValue = {
  isHelperLineEnabled: false,
  setHelperLineEnabled: () => {},
};

export const HelperLinesContext = React.createContext<HelperLinesContextValue>(defaultContextValue);

export const HelperLinesContextProvider = ({ children }: HelperLinesContextProviderProps) => {
  const [helperLine, setHelperLine] = useState<boolean>(true);

  return (
    <HelperLinesContext.Provider
      value={{
        isHelperLineEnabled: helperLine,
        setHelperLineEnabled: setHelperLine,
      }}>
      {children}
    </HelperLinesContext.Provider>
  );
};
