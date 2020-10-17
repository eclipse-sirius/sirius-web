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
import React, { useReducer, useEffect, useContext } from 'react';
import gql from 'graphql-tag';

import { GraphQLClient } from 'common/GraphQLClient';

import { Text } from 'core/text/Text';
import { useProject } from 'project/ProjectProvider';
import {
  LOADING__STATE,
  READY__STATE,
  HANDLE_CONNECTION_ERROR__ACTION,
  HANDLE_ERROR__ACTION,
  HANDLE_DATA__ACTION,
  HANDLE_COMPLETE__ACTION,
  SWITCH_FORM__ACTION,
  INITIALIZE__ACTION,
} from 'properties/machine';
import { Properties } from 'properties/Properties';
import { initialState, reducer } from 'properties/reducer';

import styles from './PropertiesWebSocketContainer.module.css';

const propertiesEventSubscription = gql`
  subscription formEvent($input: FormEventInput!) {
    formEvent(input: $input) {
      __typename
      ... on PreDestroyPayload {
        id
      }
      ... on SubscribersUpdatedEventPayload {
        subscribers {
          username
        }
      }
      ... on WidgetSubscriptionsUpdatedEventPayload {
        widgetSubscriptions {
          widgetId
          subscribers {
            username
          }
        }
      }
      ... on FormRefreshedEventPayload {
        form {
          label
          id
          pages {
            label
            id
            groups {
              label
              id
              widgets {
                id
                __typename
                ... on Textfield {
                  label
                  stringValue: value
                }
                ... on Textarea {
                  label
                  stringValue: value
                }
                ... on Checkbox {
                  label
                  booleanValue: value
                }
                ... on Select {
                  label
                  value
                  options {
                    id
                    label
                  }
                }
                ... on Radio {
                  label
                  options {
                    id
                    label
                    selected
                  }
                }
                ... on List {
                  label
                  items {
                    id
                    label
                    imageURL
                  }
                }
              }
            }
          }
        }
      }
    }
  }
`.loc.source.body;
const propertiesEventOperationName = 'formEvent';

/**
 * Connect the Properties component to the GraphQL API over Web Socket.
 */
export const PropertiesWebSocketContainer = ({ objectId }) => {
  const [state, dispatch] = useReducer(reducer, initialState);
  const { viewState, form, displayedObjectId, subscribers, widgetSubscriptions, message } = state;

  const { graphQLWebSocketClient } = useContext(GraphQLClient);
  const { id } = useProject() as any;

  /**
   * Displays an other form if the selection indicates that we should display another properties view.
   */
  useEffect(() => {
    if (displayedObjectId !== objectId) {
      dispatch({ type: SWITCH_FORM__ACTION, objectId });
    }
  }, [objectId, displayedObjectId]);

  useEffect(() => {
    if (viewState === LOADING__STATE) {
      dispatch({ type: INITIALIZE__ACTION });
    }
  }, [viewState]);

  useEffect(() => {
    if (viewState !== READY__STATE) {
      return () => {};
    }

    const operationId = graphQLWebSocketClient.generateOperationId();
    const subscribe = () => {
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
          objectId,
        },
      };
      graphQLWebSocketClient.start(operationId, propertiesEventSubscription, variables, propertiesEventOperationName);
    };

    const unsubscribe = (id) => {
      graphQLWebSocketClient.remove(id);
      graphQLWebSocketClient.stop(id);
    };

    subscribe();
    return () => unsubscribe(operationId);
  }, [id, objectId, graphQLWebSocketClient, viewState]);

  let view = <div />;
  if (!form) {
    view = (
      <div className={styles.empty}>
        <Text className={styles.label}>{message}</Text>
      </div>
    );
  } else {
    view = (
      <Properties projectId={id} form={form} subscribers={subscribers} widgetSubscriptions={widgetSubscriptions} />
    );
  }
  return view;
};
