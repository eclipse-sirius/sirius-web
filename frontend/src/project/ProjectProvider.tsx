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
import { useQuery, useSubscription } from '@apollo/client';
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
`;

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
`;

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
  const [state, setState] = useState(initialState);
  const { projectId } = useParams();
  const { loading, error, data } = useQuery(getProjectQuery, { variables: { projectId } });
  useEffect(() => {
    if (!loading && !error && data) {
      const newState = { ...data.viewer.project };
      newState.canRead = ACCESS_LEVELS.indexOf(newState.accessLevel) >= ACCESS_LEVELS.indexOf('READ');
      newState.canEdit = ACCESS_LEVELS.indexOf(newState.accessLevel) >= ACCESS_LEVELS.indexOf('EDIT');
      newState.canAdmin = ACCESS_LEVELS.indexOf(newState.accessLevel) >= ACCESS_LEVELS.indexOf('ADMIN');
      setState(newState);
    }
  }, [loading, data, error, setState]);

  useSubscription(projectEventSubscription, {
    variables: {
      input: {
        projectId: state.id,
      },
    },
    fetchPolicy: 'no-cache',
    skip: !state.id,
    onSubscriptionData: ({ subscriptionData }) => {
      if (subscriptionData.data.projectEvent) {
        const { projectEvent } = subscriptionData.data;
        if (projectEvent.__typename === 'ProjectRenamedEventPayload') {
          setState((prevState) => {
            return { ...prevState, name: projectEvent.newLabel };
          });
        }
      }
    },
    shouldResubscribe: ({ variables: { input } }) => input.projectId !== state.id,
  });

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
