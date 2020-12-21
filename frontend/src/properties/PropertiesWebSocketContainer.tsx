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
import { useSubscription } from '@apollo/client';
import { Text } from 'core/text/Text';
import gql from 'graphql-tag';
import {
  HANDLE_COMPLETE__ACTION,
  HANDLE_DATA__ACTION,
  HANDLE_ERROR__ACTION,
  INITIALIZE__ACTION,
  LOADING__STATE,
  READY__STATE,
  SWITCH_FORM__ACTION,
} from 'properties/machine';
import { Properties } from 'properties/Properties';
import { initialState, reducer } from 'properties/reducer';
import React, { useEffect, useReducer } from 'react';
import styles from './PropertiesWebSocketContainer.module.css';

const propertiesEventSubscription = gql`
  subscription propertiesEvent($input: PropertiesEventInput!) {
    propertiesEvent(input: $input) {
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
`;

/**
 * Connect the Properties component to the GraphQL API over Web Socket.
 */
export const PropertiesWebSocketContainer = ({ projectId, objectId }) => {
  const [state, dispatch] = useReducer(reducer, initialState);
  const { viewState, form, displayedObjectId, subscribers, widgetSubscriptions, message } = state;

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

  const { error } = useSubscription(propertiesEventSubscription, {
    variables: {
      input: {
        projectId,
        objectId,
      },
    },
    skip: viewState !== READY__STATE,
    onSubscriptionData: ({ subscriptionData }) => {
      dispatch({ type: HANDLE_DATA__ACTION, message: subscriptionData });
    },
    onSubscriptionComplete: () => dispatch({ type: HANDLE_COMPLETE__ACTION }),
  });
  if (error) {
    dispatch({ type: HANDLE_ERROR__ACTION, message: error });
  }

  let view = <div />;
  if (!form) {
    view = (
      <div className={styles.empty}>
        <Text className={styles.label}>{message}</Text>
      </div>
    );
  } else {
    view = (
      <Properties
        projectId={projectId}
        form={form}
        subscribers={subscribers}
        widgetSubscriptions={widgetSubscriptions}
      />
    );
  }
  return view;
};
