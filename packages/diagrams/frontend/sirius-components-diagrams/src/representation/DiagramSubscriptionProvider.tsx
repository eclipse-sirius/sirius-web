/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import LinearProgress from '@mui/material/LinearProgress';
import Typography from '@mui/material/Typography';
import { memo, useEffect, useState } from 'react';
import { DiagramContext } from '../contexts/DiagramContext';
import {
  GQLDiagramEventPayload,
  GQLDiagramRefreshedEventPayload,
} from '../graphql/subscription/diagramEventSubscription.types';
import { DiagramRenderer } from '../renderer/DiagramRenderer';
import { DiagramSubscriptionProviderProps, DiagramSubscriptionState } from './DiagramSubscriptionProvider.types';
import { StoreContextProvider } from './StoreContext';
import { useDiagramSubscription } from './useDiagramSubscription';

const isDiagramRefreshedEventPayload = (
  payload: GQLDiagramEventPayload | null
): payload is GQLDiagramRefreshedEventPayload => !!payload && payload.__typename === 'DiagramRefreshedEventPayload';

export const DiagramSubscriptionProvider = memo(
  ({ diagramId, editingContextId, readOnly }: DiagramSubscriptionProviderProps) => {
    const [state, setState] = useState<DiagramSubscriptionState>({
      id: crypto.randomUUID(),
      diagramRefreshedEventPayload: null,
      complete: false,
      message: '',
    });

    const { loading, complete, payload } = useDiagramSubscription(editingContextId, diagramId);

    useEffect(() => {
      if (isDiagramRefreshedEventPayload(payload)) {
        setState((prevState) => ({ ...prevState, diagramRefreshedEventPayload: payload }));
      }
    }, [payload]);

    if (loading || !state.diagramRefreshedEventPayload) {
      return <LinearProgress />;
    }

    if (complete) {
      return (
        <div>
          <Typography variant="subtitle2">The representation is not available anymore</Typography>
        </div>
      );
    }

    return (
      <StoreContextProvider>
        <DiagramContext.Provider
          value={{
            editingContextId,
            diagramId: diagramId,
            refreshEventPayloadId: state.diagramRefreshedEventPayload.id,
            payload: payload,
            readOnly,
          }}>
          <DiagramRenderer diagramRefreshedEventPayload={state.diagramRefreshedEventPayload} />
        </DiagramContext.Provider>
      </StoreContextProvider>
    );
  }
);
