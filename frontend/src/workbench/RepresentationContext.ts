/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
import { RepresentationComponentRegistry } from 'workbench/RepresentationContext.types';
import { RepresentationComponentProps } from './Workbench.types';

const registry: RepresentationComponentRegistry = {
  getComponent: (representation) => {
    return (props: RepresentationComponentProps) => null;
  },
};

const value = {
  registry,
};
export const RepresentationContext = React.createContext(value);
