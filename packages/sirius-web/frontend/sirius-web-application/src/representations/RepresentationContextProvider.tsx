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

import {
  Representation,
  RepresentationComponent,
  RepresentationComponentRegistry,
  RepresentationContext,
  RepresentationContextValue,
} from '@eclipse-sirius/sirius-components-core';
import { DiagramRepresentation } from '@eclipse-sirius/sirius-components-diagrams';
import { DiagramRepresentation as ReactFlowDiagramRepresentation } from '@eclipse-sirius/sirius-components-diagrams-reactflow';
import { FormDescriptionEditorRepresentation } from '@eclipse-sirius/sirius-components-formdescriptioneditors';
import { FormRepresentation } from '@eclipse-sirius/sirius-components-forms';
import { RepresentationContextProviderProps } from './RepresentationContextProvider.types';

export const RepresentationContextProvider = ({ children }: RepresentationContextProviderProps) => {
  const registry: RepresentationComponentRegistry = {
    getComponent: (representation: Representation): RepresentationComponent | null => {
      const query = representation.kind.substring(representation.kind.indexOf('?') + 1, representation.kind.length);
      const params = new URLSearchParams(query);
      const type = params.get('type');
      if (type === 'Diagram' && representation.label.endsWith('__REACT_FLOW')) {
        return ReactFlowDiagramRepresentation;
      } else if (type === 'Diagram') {
        return DiagramRepresentation;
      } else if (type === 'Form') {
        return FormRepresentation;
      } else if (type === 'FormDescriptionEditor') {
        return FormDescriptionEditorRepresentation;
      }
      return null;
    },
  };

  const representationContextValue: RepresentationContextValue = {
    registry,
  };

  return <RepresentationContext.Provider value={representationContextValue}>{children}</RepresentationContext.Provider>;
};
