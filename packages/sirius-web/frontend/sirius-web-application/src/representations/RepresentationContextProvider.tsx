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

import {
  Representation,
  RepresentationComponent,
  RepresentationComponentRegistry,
  RepresentationContext,
  RepresentationContextValue,
} from '@eclipse-sirius/sirius-components-core';
import { DeckRepresentation } from '@eclipse-sirius/sirius-components-deck';
import { DiagramRepresentation } from '@eclipse-sirius/sirius-components-diagrams';
import { FormDescriptionEditorRepresentation } from '@eclipse-sirius/sirius-components-formdescriptioneditors';
import { FormRepresentation } from '@eclipse-sirius/sirius-components-forms';
import { GanttRepresentation } from '@eclipse-sirius/sirius-components-gantt';
import { PortalRepresentation } from '@eclipse-sirius/sirius-components-portals';

import { RepresentationContextProviderProps } from './RepresentationContextProvider.types';

export const RepresentationContextProvider = ({ children }: RepresentationContextProviderProps) => {
  const registry: RepresentationComponentRegistry = {
    getComponent: (representation: Representation): RepresentationComponent | null => {
      const query = representation.kind.substring(representation.kind.indexOf('?') + 1, representation.kind.length);
      const params = new URLSearchParams(query);
      const type = params.get('type');
      if (type === 'Diagram') {
        return DiagramRepresentation;
      } else if (type === 'Form') {
        return FormRepresentation;
      } else if (type === 'FormDescriptionEditor') {
        return FormDescriptionEditorRepresentation;
      } else if (type === 'Gantt') {
        return GanttRepresentation;
      } else if (type === 'Deck') {
        return DeckRepresentation;
      } else if (type === 'Portal') {
        return PortalRepresentation;
      }
      return null;
    },
  };

  const representationContextValue: RepresentationContextValue = {
    registry,
  };

  return <RepresentationContext.Provider value={representationContextValue}>{children}</RepresentationContext.Provider>;
};
