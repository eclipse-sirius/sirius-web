/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
import { gql, useSubscription } from '@apollo/client';
import { M, Spacing } from 'core/spacing/Spacing';
import { Text } from 'core/text/Text';
import {
  COMPLETE__STATE,
  ERROR__STATE,
  HANDLE_COMPLETE__ACTION,
  HANDLE_DATA__ACTION,
  HANDLE_ERROR__ACTION,
  HANDLE_EXPANDED__ACTION,
  LOADING__STATE,
} from 'explorer/machine';
import React, { useReducer } from 'react';
import { Explorer } from './Explorer';
import styles from './ExplorerWebSocketContainer.module.css';
import { ExplorerWebSocketContainerProps } from './ExplorerWebSocketContainer.types';
import { getTreeEventSubscription } from './getTreeEventSubscription';
import { initialState, reducer } from './reducer';

export const ExplorerWebSocketContainer = ({
  editingContextId,
  selection,
  setSelection,
  readOnly,
}: ExplorerWebSocketContainerProps) => {
  const [state, dispatch] = useReducer(reducer, initialState);
  const { viewState, id, tree, expanded, maxDepth, message } = state;

  const { error } = useSubscription(gql(getTreeEventSubscription(maxDepth)), {
    variables: {
      input: {
        id,
        editingContextId,
        expanded,
      },
    },
    fetchPolicy: 'no-cache',
    skip: viewState === COMPLETE__STATE,
    onSubscriptionData: ({ subscriptionData }) => {
      dispatch({ type: HANDLE_DATA__ACTION, message: subscriptionData });
    },
    onSubscriptionComplete: () => dispatch({ type: HANDLE_COMPLETE__ACTION }),
  });
  if (error) {
    dispatch({ type: HANDLE_ERROR__ACTION, message: error });
  }

  const onExpand = (id: string, depth: number) => {
    dispatch({ type: HANDLE_EXPANDED__ACTION, id, depth });
  };

  if (viewState === LOADING__STATE) {
    return <div />;
  }
  if (viewState === ERROR__STATE || viewState === COMPLETE__STATE) {
    return (
      <Spacing left={M} right={M} top={M} bottom={M}>
        <Text className={styles.error}>{message}</Text>
      </Spacing>
    );
  }

  return (
    <Explorer
      editingContextId={editingContextId}
      tree={tree}
      onExpand={onExpand}
      selection={selection}
      setSelection={setSelection}
      readOnly={readOnly}
    />
  );
};
