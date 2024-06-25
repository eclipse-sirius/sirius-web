/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import { DiagramContextProviderProps, DiagramContextValue } from './DiagramContext.types';

const value: DiagramContextValue = {
  editingContextId: '',
  diagramId: '',
  readOnly: false,
  refreshEventPayloadId: '',
  setRefreshEventPayloadId: () => {},
};

export const DiagramContext = React.createContext<DiagramContextValue>(value);

export const DiagramContextProvider = ({
  diagramId,
  editingContextId,
  readOnly,
  children,
}: DiagramContextProviderProps) => {
  const [refreshEventPayloadId, setRefreshEventPayloadId] = useState<string>('');

  return (
    <DiagramContext.Provider
      value={{
        diagramId,
        editingContextId,
        readOnly,
        refreshEventPayloadId,
        setRefreshEventPayloadId,
      }}>
      {children}
    </DiagramContext.Provider>
  );
};
