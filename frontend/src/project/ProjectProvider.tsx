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
import { useQuery } from 'common/GraphQLHooks';
import gql from 'graphql-tag';
import React, { useContext, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

const getProjectQuery = gql`
  query getProject($projectId: ID!) {
    viewer {
      id
      username
      project(projectId: $projectId) {
        id
        name
        visibility
        accessLevel
        owner {
          username
        }
      }
    }
  }
`.loc.source.body;

const projectEventSubscription = gql`
  subscription projectEvent($input: ProjectEventInput!) {
    projectEvent(input: $input) {
      __typename
      ... on ProjectRenamedEventPayload {
        projectId
        newLabel
      }
    }
  }
`.loc.source.body;

// In order, from the least to the most privileged
const ACCESS_LEVELS = ['READ', 'EDIT', 'ADMIN'];

const initialState = {
  id: undefined,
  name: undefined,
  canRead: false,
  canEdit: false,
  canAdmin: false,
};
export const ProjectContext = React.createContext(initialState);

const ProjectProvider = ({ children }) => {
  const { graphQLWebSocketClient } = useContext(GraphQLClient);
  const [state, setState] = useState(initialState);
  const { projectId } = useParams();
  const { loading, data } = useQuery(getProjectQuery, { projectId }, 'getProject');
  useEffect(() => {
    if (!loading && data) {
      const newState = { ...data.data.viewer.project };
      newState.canRead = ACCESS_LEVELS.indexOf(newState.accessLevel) >= ACCESS_LEVELS.indexOf('READ');
      newState.canEdit = ACCESS_LEVELS.indexOf(newState.accessLevel) >= ACCESS_LEVELS.indexOf('EDIT');
      newState.canAdmin = ACCESS_LEVELS.indexOf(newState.accessLevel) >= ACCESS_LEVELS.indexOf('ADMIN');
      setState(newState);
    }
  }, [loading, data, setState]);

  useEffect(() => {
    if (state.id) {
      const operationId = graphQLWebSocketClient.generateOperationId();
      const subscribe = () => {
        graphQLWebSocketClient.on(operationId, (message) => {
          if (message.type === 'data' && message?.payload?.data?.projectEvent) {
            const { projectEvent } = message.payload.data;
            if (projectEvent.__typename === 'ProjectRenamedEventPayload') {
              setState((prevState) => {
                return { ...prevState, name: projectEvent.newLabel };
              });
            }
          }
        });
        const variables = {
          input: {
            projectId: state.id,
          },
        };

        graphQLWebSocketClient.start(operationId, projectEventSubscription, variables, 'projectEvent');
      };

      const unsubscribe = (id) => {
        graphQLWebSocketClient.remove(id);
        graphQLWebSocketClient.stop(id);
      };

      subscribe();
      return () => unsubscribe(operationId);
    }
  }, [graphQLWebSocketClient, state.id, setState]);

  return <ProjectContext.Provider value={state}>{children}</ProjectContext.Provider>;
};

export const withProject = (Child) => {
  return () => {
    return (
      <ProjectProvider>
        <Child />
      </ProjectProvider>
    );
  };
};

export const useProject = () => {
  return useContext(ProjectContext);
};
