/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
import { GraphQLClient } from 'common/GraphQLClient';
import { M, Spacing } from 'core/spacing/Spacing';
import { Text } from 'core/text/Text';
import {
  COMPLETE__STATE,
  ERROR__STATE,
  HANDLE_COMPLETE__ACTION,
  HANDLE_CONNECTION_ERROR__ACTION,
  HANDLE_DATA__ACTION,
  HANDLE_ERROR__ACTION,
  HANDLE_EXPANDED__ACTION,
  LOADING__STATE,
} from 'explorer/machine';
import { useProject } from 'project/ProjectProvider';
import PropTypes from 'prop-types';
import React, { useContext, useEffect, useReducer } from 'react';
import { Explorer } from './Explorer';
import styles from './ExplorerWebSocketContainer.module.css';
import { getTreeEventSubscription } from './getTreeEventSubscription';
import { initialState, reducer } from './reducer';

const propTypes = {
  selections: PropTypes.array.isRequired,
  displayedRepresentation: PropTypes.object,
  setSelections: PropTypes.func.isRequired,
};

export const ExplorerWebSocketContainer = ({ selections, displayedRepresentation, setSelections }) => {
  const [state, dispatch] = useReducer(reducer, initialState);
  const { viewState, tree, expanded, maxDepth, message } = state;

  const { graphQLWebSocketClient } = useContext(GraphQLClient);
  const { id } = useProject() as any;

  useEffect(() => {
    let subscribe = (operationId) => {
      graphQLWebSocketClient.on(operationId, (message) => {
        switch (message.type) {
          case 'connection_error':
            dispatch({ type: HANDLE_CONNECTION_ERROR__ACTION, message });
            break;
          case 'error':
            dispatch({ type: HANDLE_ERROR__ACTION, message });
            break;
          case 'data':
            dispatch({ type: HANDLE_DATA__ACTION, message });
            break;
          case 'complete':
            dispatch({ type: HANDLE_COMPLETE__ACTION, message });
            break;
          default:
            break;
        }
      });
      const variables = {
        input: {
          projectId: id,
          expanded,
        },
      };
      const subscription = getTreeEventSubscription(maxDepth);
      return graphQLWebSocketClient.start(operationId, subscription, variables, 'treeEvent');
    };

    if (viewState === COMPLETE__STATE) {
      subscribe = (operationId) => {
        // Let's do nothing since the representation does not exist anymore (the project may have been deleted or the server may have been killed for example)
      };
    }

    const unsubscribe = (operationId) => {
      graphQLWebSocketClient.remove(operationId);
      graphQLWebSocketClient.stop(operationId);
    };

    const operationId = graphQLWebSocketClient.generateOperationId();
    subscribe(operationId);
    return () => unsubscribe(operationId);
  }, [id, viewState, expanded, maxDepth, graphQLWebSocketClient]);

  const onExpand = (id, depth) => {
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
      tree={tree}
      onExpand={onExpand}
      selections={selections}
      // displayedRepresentation={displayedRepresentation} TODO Explorer does not support such a prop
      setSelections={setSelections}
    />
  );
};
ExplorerWebSocketContainer.propTypes = propTypes;
