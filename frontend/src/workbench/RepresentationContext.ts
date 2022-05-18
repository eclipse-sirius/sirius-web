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
import { DiagramWebSocketContainer } from 'diagram/DiagramWebSocketContainer';
import { FormWebSocketContainer } from 'form/FormWebSocketContainer';
import { FormDescriptionEditorWebSocketContainer } from 'formdescriptioneditor/FormDescriptionEditorWebSocketContainer';
import React from 'react';
import { RepresentationComponentRegistry } from 'workbench/RepresentationContext.types';
import { RepresentationComponentProps } from './Workbench.types';

const registry: RepresentationComponentRegistry = {
  getComponent: (representation) => {
    const query = representation.kind.substring(representation.kind.indexOf('?') + 1, representation.kind.length);
    const params = new URLSearchParams(query);
    const type = params.get('type');

    if (type === 'Diagram') {
      return DiagramWebSocketContainer;
    } else if (type === 'Form') {
      return FormWebSocketContainer;
    } else if (type === 'FormDescriptionEditor') {
      return FormDescriptionEditorWebSocketContainer;
    }
    return (props: RepresentationComponentProps) => null;
  },
};

const value = {
  registry,
};
export const RepresentationContext = React.createContext(value);
