/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import { RepresentationLoadingIndicator } from '@eclipse-sirius/sirius-components-core';
import Typography from '@mui/material/Typography';
import { memo, useEffect, useState } from 'react';
import { DialogContextProvider } from '../dialog/DialogContext';
import {
  GQLDiagramEventPayload,
  GQLDiagramRefreshedEventPayload,
} from '../graphql/subscription/diagramEventSubscription.types';
import { DiagramRenderer } from '../renderer/DiagramRenderer';
import { DiagramSubscriptionProviderProps, DiagramSubscriptionState } from './DiagramSubscriptionProvider.types';
import { StoreContextProvider } from './StoreContext';
import { useDiagramSubscription } from './useDiagramSubscription';
import { DiagramToolExecutorContextProvider } from '../renderer/tools/DiagramToolExecutorContext';

const isDiagramRefreshedEventPayload = (
  payload: GQLDiagramEventPayload | null
): payload is GQLDiagramRefreshedEventPayload => !!payload && payload.__typename === 'DiagramRefreshedEventPayload';

export const DiagramSubscriptionProvider = memo(({ diagramId, editingContextId }: DiagramSubscriptionProviderProps) => {
  const [state, setState] = useState<DiagramSubscriptionState>({
    id: crypto.randomUUID(),
    diagramRefreshedEventPayload: null,
    complete: false,
    message: '',
  });

  const { complete, payload } = useDiagramSubscription(editingContextId, diagramId);

  useEffect(() => {
    if (isDiagramRefreshedEventPayload(payload)) {
      setState((prevState) => ({ ...prevState, diagramRefreshedEventPayload: payload }));
    }
  }, [payload]);

  if (complete) {
    return (
      <div>
        <Typography variant="subtitle2">The representation is not available anymore</Typography>
      </div>
    );
  }

  if (!state.diagramRefreshedEventPayload) {
    return <RepresentationLoadingIndicator />;
  }

  return (
    <StoreContextProvider>
      <DialogContextProvider>
        <DiagramToolExecutorContextProvider>
          <div
            style={{ display: 'inline-block', position: 'relative' }}
            data-representation-kind="diagram"
            data-representation-label={state.diagramRefreshedEventPayload.diagram.metadata.label}>
            <DiagramRenderer
              key={state.diagramRefreshedEventPayload.diagram.id}
              diagramRefreshedEventPayload={state.diagramRefreshedEventPayload}
            />
          </div>
        </DiagramToolExecutorContextProvider>
      </DialogContextProvider>
    </StoreContextProvider>
  );
});
