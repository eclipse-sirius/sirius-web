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

import React from 'react';
import { DetailsViewConfigurationProps, UseDetailsViewConfigurationValue } from './DetailsViewConfiguration.types';
import { DetailsViewContext } from './DetailsViewContext';

export const DetailsViewConfiguration = ({ children, converter }: DetailsViewConfigurationProps) => {
  return <DetailsViewContext.Provider value={{ converter }}>{children}</DetailsViewContext.Provider>;
};

export const useDetailsViewConfiguration = (): UseDetailsViewConfigurationValue => {
  const { converter } = React.useContext<UseDetailsViewConfigurationValue>(DetailsViewContext);
  return {
    converter,
  };
};
