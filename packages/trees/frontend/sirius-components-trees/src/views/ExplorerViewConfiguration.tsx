/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import { useContext } from 'react';
import { ExplorerViewConfigurationProps, UseExplorerViewConfigurationValue } from './ExplorerViewConfiguration.types';
import { ExplorerViewContext } from './ExplorerViewContext';

export const ExplorerViewConfiguration = ({ children, converter }: ExplorerViewConfigurationProps) => {
  return <ExplorerViewContext.Provider value={{ converter }}>{children}</ExplorerViewContext.Provider>;
};

export const useExplorerViewConfiguration = (): UseExplorerViewConfigurationValue => {
  const { converter } = useContext<UseExplorerViewConfigurationValue>(ExplorerViewContext);
  return {
    converter,
  };
};
