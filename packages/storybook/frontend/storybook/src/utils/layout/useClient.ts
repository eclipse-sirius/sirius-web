/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { ApolloClient, ApolloLink, FetchResult, InMemoryCache, Observable, Operation } from '@apollo/client';
import { useMemo } from 'react';
import { EnrichedGQLDiagram } from './diagramConstructionUtils';

export const useClient = (diagram: EnrichedGQLDiagram) => {
  return useMemo(() => {
    const cache = new InMemoryCache({ addTypename: true });

    const getObservable = (op: Operation) =>
      new Observable<FetchResult>((o) => {
        const opName = op.operationName;

        const success = (payloadData: any) => ({
          __typename: 'SuccessPayloadWrapper',
          payload: { __typename: 'SuccessPayload', messages: [], ...payloadData },
        });

        const viewer = {
          __typename: 'Viewer',
          editingContext: {
            __typename: 'EditingContext',
            id: 'root',
            representation: diagram,
            getDiagram: success({ diagram }),
            getDiagramDescription: diagram.description,
            getPalette: success({ palette: diagram.description.palette }),
          },
        };

        let responseData: any;

        if (opName === 'diagramEvent') {
          responseData = {
            [opName]: { __typename: 'DiagramRefreshedEventPayload', id: diagram.id, diagram, cause: 'refresh' },
          };
        } else if (['dropNodes', 'layoutDiagram', 'updateNodePosition', 'updateNodeSize'].includes(opName)) {
          responseData = { [opName]: success({ diagram, viewer }) };
        } else {
          responseData = { viewer: viewer };
        }

        o.next({ data: responseData });
        if (opName !== 'diagramEvent') o.complete();
      });

    return new ApolloClient({
      cache,
      link: new ApolloLink(getObservable),
    });
  }, [diagram]);
};
