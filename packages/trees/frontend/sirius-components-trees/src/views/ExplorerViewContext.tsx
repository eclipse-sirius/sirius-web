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
import { TreeConverter } from './TreeConverter.types';
import { ExplorerViewContextValue } from './ExplorerViewContext.types';

const converter: TreeConverter = {
  convert: (tree) => tree,
};

const defaultContext: ExplorerViewContextValue = {
  converter,
};

export const ExplorerViewContext = React.createContext(defaultContext);
