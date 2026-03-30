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
import { createContext } from 'react';
import { PanelCollapseContextValue, PanelCollapseProviderProps } from './PanelCollapseContext.types';

const defaultValue: PanelCollapseContextValue = {
  onCollapseChange: () => {},
};

export const PanelCollapseContext = createContext<PanelCollapseContextValue>(defaultValue);

export const PanelCollapseContextProvider = ({ children, onCollapseChange }: PanelCollapseProviderProps) => {
  return <PanelCollapseContext.Provider value={{ onCollapseChange }}>{children}</PanelCollapseContext.Provider>;
};
