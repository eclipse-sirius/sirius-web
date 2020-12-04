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
import { useLazyQuery } from 'common/GraphQLHooks';
import gql from 'graphql-tag';
import { useProject } from 'project/ProjectProvider';
import React, { useCallback, useContext, useEffect, useReducer } from 'react';
import { generatePath, useHistory, useParams, useRouteMatch } from 'react-router-dom';
import { EditProjectLoadedView } from 'views/edit-project/EditProjectLoadedView';
import {
  HANDLE_FETCHED_PROJECT__ACTION,
  HANDLE_REPRESENTATION_RENAMED__ACTION,
  HANDLE_SELECTION__ACTION,
  HANDLE_SUBSCRIBERS_UPDATED__ACTION,
  LOADING__STATE,
  PROJECT_FETCHING_ERROR__STATE,
  PROJECT_NOT_FOUND__STATE,
} from 'views/edit-project/machine';
import { initialState, reducer } from 'views/edit-project/reducer';
import { ErrorView } from 'views/ErrorView';

const getRepresentationQuery = gql`
  query getRepresentation($projectId: ID!, $representationId: ID!) {
    viewer {
      project(projectId: $projectId) {
        representation(representationId: $representationId) {
          __typename
          id
          label
        }
      }
    }
  }
`.loc.source.body;

const projectEventSubscriptionFile = gql`
  subscription projectEvent($input: ProjectEventInput!) {
    projectEvent(input: $input) {
      __typename
      ... on RepresentationRenamedEventPayload {
        representationId
        newLabel
      }
    }
  }
`;

export const EditProjectView = () => {
  const { graphQLWebSocketClient } = useContext(GraphQLClient);
  const history = useHistory();
  const routeMatch = useRouteMatch();
  const { projectId, representationId } = useParams();
  const [state, dispatch] = useReducer(reducer, initialState);
  const { viewState, project, representations, displayedRepresentation, selection, subscribers, message } = state;

  const context = useProject() as any;
  let contextId;
  if (context) {
    contextId = context.id;
  }

  useEffect(() => {
    if (context && context.id && viewState === LOADING__STATE) {
      const action = {
        type: HANDLE_FETCHED_PROJECT__ACTION,
        response: {
          data: {
            viewer: {
              project: {
                id: context.id,
                name: context.name,
                visibility: context.visibility,
                accessLevel: context.accessLevel,
              },
            },
          },
        },
      };
      dispatch(action);
    }
  }, [context, viewState]);

  const [getRepresentation, result] = useLazyQuery(getRepresentationQuery, {}, 'getRepresentation');
  useEffect(() => {
    if (representationId && project && !displayedRepresentation) {
      getRepresentation({ projectId: project.id, representationId });
    }
  }, [project, representationId, displayedRepresentation, getRepresentation]);
  useEffect(() => {
    if (!result.loading && result?.data?.data?.viewer?.project?.representation) {
      const { id, label, __typename } = result.data.data.viewer.project.representation;
      const representation = { id, label, kind: __typename };
      const action = { type: HANDLE_SELECTION__ACTION, selection: representation };
      dispatch(action);
    }
  }, [result]);

  useEffect(() => {
    if (displayedRepresentation && displayedRepresentation.id !== representationId) {
      const pathname = generatePath(routeMatch.path, { projectId, representationId: displayedRepresentation.id });
      history.push({ pathname });
    }
  }, [projectId, routeMatch, history, displayedRepresentation, representationId]);

  const setSelection = useCallback(
    (newSelectedObject) => {
      const action = { type: HANDLE_SELECTION__ACTION, selection: newSelectedObject };
      dispatch(action);
    },
    [dispatch]
  );

  /**
   * Connect to the WebSocket server to retrieve updates when the component is ready. This will only be
   * performed once the project has been loaded with useProject() and its ID (contextId) has been retrieved.
   * Indeed, this useEffect() hook is called each time contextId is updated.
   * The first time useProject() is called, contextId is set to 'undefined', and this useEffect() is then called. -> We don't want to subscribe.
   * The second time useProject() is called, contextId is set with the project ID, and this useEffect() is then called. -> We want to subscribe.
   */
  useEffect(() => {
    if (!contextId) {
      return () => {};
    }
    const operationId = graphQLWebSocketClient.generateOperationId();

    const subscribe = () => {
      graphQLWebSocketClient.on(operationId, (message) => {
        if (message.type === 'data' && message?.payload?.data?.projectEvent) {
          const { projectEvent } = message.payload.data;
          if (projectEvent.__typename === 'RepresentationRenamedEventPayload') {
            const action = { type: HANDLE_REPRESENTATION_RENAMED__ACTION, projectEvent };
            dispatch(action);
          }
        }
      });

      const variables = {
        input: {
          projectId: contextId,
        },
      };

      graphQLWebSocketClient.start(
        operationId,
        projectEventSubscriptionFile.loc.source.body,
        variables,
        'projectEvent'
      );
    };

    const unsubscribe = (id) => {
      graphQLWebSocketClient.remove(id);
      graphQLWebSocketClient.stop(id);
    };

    subscribe();
    return () => unsubscribe(operationId);
  }, [graphQLWebSocketClient, contextId]);

  const setSubscribers = useCallback(
    (subscribers) => {
      const action = { type: HANDLE_SUBSCRIBERS_UPDATED__ACTION, subscribers };
      dispatch(action);
    },
    [dispatch]
  );

  if (!context) {
    return <ErrorView message="The requested project does not exist" />;
  }
  if (viewState === LOADING__STATE) {
    return <div></div>;
  }
  if (viewState === PROJECT_FETCHING_ERROR__STATE || viewState === PROJECT_NOT_FOUND__STATE) {
    return <ErrorView message={message} />;
  }
  return (
    <EditProjectLoadedView
      subscribers={subscribers}
      representations={representations}
      displayedRepresentation={displayedRepresentation}
      selection={selection}
      setSelection={setSelection}
      setSubscribers={setSubscribers}
    />
  );
};
