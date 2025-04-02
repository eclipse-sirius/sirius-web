/*******************************************************************************
 * Copyright (c) 2024, 2025 CEA LIST.
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
import { ApolloError, gql, OnDataOptions, useSubscription } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useState } from 'react';
import { flushSync } from 'react-dom';
import {
  GQLTableColumnFilterPayload,
  GQLTableEventData,
  GQLTableEventInput,
  GQLTableEventPayload,
  GQLTableEventVariables,
  GQLTableGlobalFilterValuePayload,
  GQLTableRefreshedEventPayload,
  UseTableSubscriptionState,
  UseTableSubscriptionValue,
  GQLTableColumnSortPayload,
} from './useTableSubscription.types';

export const getTableEventSubscription = `
  subscription tableEvent($input: TableEventInput!) {
    tableEvent(input: $input) {
      __typename
       ... on TableGlobalFilterValuePayload {
        globalFilterValue
      }
      ... on TableColumnFilterPayload {
        columnFilters {
          id
          value
        }
      }
      ... on TableColumnSortPayload {
        columnSort {
          id
          desc
        }
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
          enableSubRows
          pageSizeOptions
          defaultPageSize
          globalFilter
          columnFilters {
            id
            value
          }
          columnSort {
            id
            desc
          }
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
            filterVariant
            isSortable
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
            depthLevel
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
              ... on TextareaCell {
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

const isTableColumnFilterPayload = (payload: GQLTableEventPayload): payload is GQLTableColumnFilterPayload =>
  payload.__typename === 'TableColumnFilterPayload';

const isTableColumnSortPayload = (payload: GQLTableEventPayload): payload is GQLTableColumnSortPayload =>
  payload.__typename === 'TableColumnSortPayload';

export const useTableSubscription = (editingContextId: string, representationId: string): UseTableSubscriptionValue => {
  const [state, setState] = useState<UseTableSubscriptionState>({
    id: crypto.randomUUID(),
    table: null,
    complete: false,
  });

  const input: GQLTableEventInput = {
    id: state.id,
    editingContextId,
    representationId,
  };

  const variables: GQLTableEventVariables = { input };

  const onData = ({ data }: OnDataOptions<GQLTableEventData>) => {
    flushSync(() => {
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
        } else if (isTableColumnFilterPayload(payload)) {
          const { columnFilters } = payload;
          setState((prevState) => {
            if (prevState.table) {
              return {
                ...prevState,
                table: { ...prevState.table, columnFilters: columnFilters },
              };
            } else {
              return prevState;
            }
          });
        } else if (isTableColumnSortPayload(payload)) {
          const { columnSort } = payload;
          setState((prevState) => {
            if (prevState.table) {
              return {
                ...prevState,
                table: { ...prevState.table, columnSort: columnSort },
              };
            } else {
              return prevState;
            }
          });
        }
      }
    });
  };

  const onComplete = () => setState((prevState) => ({ ...prevState, complete: true }));

  const { addErrorMessage } = useMultiToast();
  const onError = ({ message }: ApolloError) => {
    addErrorMessage(message);
  };

  const { loading } = useSubscription<GQLTableEventData, GQLTableEventVariables>(gql(getTableEventSubscription), {
    variables,
    fetchPolicy: 'no-cache',
    onData,
    onComplete,
    onError,
  });

  return {
    loading,
    table: state.table,
    complete: state.complete,
  };
};
