/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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
import { gql, OnDataOptions, useSubscription } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';

import { useEffect, useState } from 'react';
import {
  GQLTableEventData,
  GQLTableEventInput,
  GQLTableEventPayload,
  GQLTableEventVariables,
  GQLTableRefreshedEventPayload,
  UseTableSubscriptionState,
  UseTableSubscriptionValue,
  GQLTableGlobalFilterValuePayload,
} from './useTableSubscription.types';

export const getTableEventSubscription = `
  subscription tableEvent($input: TableEventInput!) {
    tableEvent(input: $input) {
      __typename
       ... on TableGlobalFilterValuePayload {
        globalFilterValue
      }
      ... on TableRefreshedEventPayload {
        table {
          id
          paginationData {
            hasPreviousPage
            hasNextPage
            totalRowCount
          }
          stripeRow
          globalFilter
          columns {
            id
            headerLabel
            headerIconURLs
            headerIndexLabel
            targetObjectId
            targetObjectKind
            width
            isResizable 
            hidden
          }
          lines {
            id
            targetObjectId
            targetObjectKind
            headerLabel
            headerIconURLs
            headerIndexLabel
            height
            isResizable
            cells {
              __typename
              id
              targetObjectId
              targetObjectKind
              columnId
              ... on CheckboxCell {
                booleanValue: value
              }
              ... on SelectCell {
                value
                options {
                  id
                  label
                }
              }
              ... on MultiSelectCell {
                values
                options {
                  id
                  label
                }
              }
              ... on TextfieldCell {
                stringValue: value
              }
              ... on IconLabelCell {
                label: value
                iconURLs
              }
            }
          }
        }
      }
    }
  }
  `;

const isTableRefreshedEventPayload = (payload: GQLTableEventPayload): payload is GQLTableRefreshedEventPayload =>
  payload.__typename === 'TableRefreshedEventPayload';

const isTableGlobalFilterValuePayload = (payload: GQLTableEventPayload): payload is GQLTableGlobalFilterValuePayload =>
  payload.__typename === 'TableGlobalFilterValuePayload';

export const useTableSubscription = (
  editingContextId: string,
  tableId: string,
  cursor: string | null,
  direction: 'PREV' | 'NEXT' | null,
  size: number,
  globalFilter: string | null
): UseTableSubscriptionValue => {
  const [state, setState] = useState<UseTableSubscriptionState>({
    id: crypto.randomUUID(),
    table: null,
    complete: false,
  });

  const globalFilterParam: string = globalFilter !== null ? `&globalFilter=${encodeURIComponent(globalFilter)}` : '';

  const input: GQLTableEventInput = {
    id: state.id,
    editingContextId,
    representationId: `${tableId}?cursor=${cursor}&direction=${direction}&size=${size}${globalFilterParam}`,
  };

  const variables: GQLTableEventVariables = { input };

  const onData = ({ data }: OnDataOptions<GQLTableEventData>) => {
    const { data: gqlTableData } = data;
    if (gqlTableData) {
      const { tableEvent: payload } = gqlTableData;
      if (isTableRefreshedEventPayload(payload)) {
        const { table } = payload;
        setState((prevState) => ({ ...prevState, table }));
      } else if (isTableGlobalFilterValuePayload(payload)) {
        const { globalFilterValue } = payload;
        setState((prevState) => {
          if (prevState.table) {
            return {
              ...prevState,
              table: { ...prevState.table, globalFilter: globalFilterValue },
            };
          } else {
            return prevState;
          }
        });
      }
    }
  };

  const onComplete = () => setState((prevState) => ({ ...prevState, complete: true }));

  const { error, loading } = useSubscription<GQLTableEventData, GQLTableEventVariables>(
    gql(getTableEventSubscription),
    {
      variables,
      fetchPolicy: 'no-cache',
      onData,
      onComplete,
    }
  );

  const { addErrorMessage } = useMultiToast();
  useEffect(() => {
    if (error) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
  }, [error]);

  return {
    loading,
    table: state.table,
    complete: state.complete,
  };
};
