/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
import { DiagramWebSocketContainer } from 'diagram/DiagramWebSocketContainer';
import { FormWebSocketContainer } from 'form/FormWebSocketContainer';
import React from 'react';
import { RepresentationComponentRegistry } from 'workbench/RepresentationContext.types';
import { RepresentationComponentProps } from './Workbench.types';

const registry: RepresentationComponentRegistry = {
  getComponent: (representation) => {
    if (representation.kind === 'Diagram') {
      return DiagramWebSocketContainer;
    } else if (representation.kind === 'Form') {
      return FormWebSocketContainer;
    }
    return (props: RepresentationComponentProps) => null;
  },
};

const value = {
  registry,
};
export const RepresentationContext = React.createContext(value);
