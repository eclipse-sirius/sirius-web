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
import { useLazyQuery, useQuery } from '@apollo/client';
import { useMachine } from '@xstate/react';
import gql from 'graphql-tag';
import React, { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { ModelersEmptyView } from './ModelersEmptyView';
import { ModelersErrorView } from './ModelersErrorView';
import { ModelersLoadedView } from './ModelersLoadedView';
import { ModelersLoadingView } from './ModelersLoadingView';
import {
  ErrorFetchingEvent,
  FetchedModelersEvent,
  ModelersUpdatedEvent,
  ModelersViewContext,
  ModelersViewEvent,
  modelersViewMachine,
} from './ModelersViewMachine';

const getModelersQuery = gql`
  query getModelers($projectId: ID!) {
    viewer {
      project(projectId: $projectId) {
        modelers {
          id
          name
          status
        }
      }
    }
  }
`;

export const ModelersView = () => {
  const { projectId } = useParams();
  const [{ value, context }, dispatch] = useMachine<ModelersViewContext, ModelersViewEvent>(modelersViewMachine);
  const { modelers, message } = context;

  // Load current project list
  const { loading: modelersLoading, data: modelersData, error: modelersError } = useQuery(getModelersQuery, {
    variables: { projectId },
  });
  useEffect(() => {
    if (!modelersLoading) {
      if (modelersError) {
        const errorFetching = { type: 'ERROR_FETCHING', message: modelersError.message } as ErrorFetchingEvent;
        dispatch(errorFetching);
      } else if (modelersData) {
        let { modelers } = modelersData.viewer.project;
        const fetchedProjects = { type: 'FETCHED_MODELERS', modelers } as FetchedModelersEvent;
        dispatch(fetchedProjects);
      }
    }
  }, [modelersLoading, modelersData, modelersError, dispatch]);

  // Setup callback to update project list when invoked
  const [getModelers, { loading, error, data }] = useLazyQuery(getModelersQuery);
  useEffect(() => {
    if (!loading) {
      if (error) {
        const errorFetching = { type: 'ERROR_FETCHING', message: error.message } as ErrorFetchingEvent;
        dispatch(errorFetching);
      } else if (data) {
        let { modelers } = data.viewer.project;
        const modelersUpdatedEvent = { type: 'MODELERS_UPDATED', modelers } as ModelersUpdatedEvent;
        dispatch(modelersUpdatedEvent);
      }
    }
  }, [loading, error, data, dispatch]);

  let view = null;
  switch (value) {
    case 'loading':
      view = <ModelersLoadingView />;
      break;
    case 'empty':
      view = <ModelersEmptyView />;
      break;
    case 'error':
      view = <ModelersErrorView message={message} />;
      break;
    case 'loaded':
      view = (
        <ModelersLoadedView
          modelers={modelers}
          onModelerUpdated={() => {
            getModelers({ variables: { projectId } });
          }}
        />
      );
      break;
    default:
      view = <ModelersErrorView message={message} />;
  }
  return <div>{view}</div>;
};
