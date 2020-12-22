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
import { ProjectsEmptyView } from './ProjectsEmptyView';
import { ProjectsErrorView } from './ProjectsErrorView';
import { ProjectsLoadedView } from './ProjectsLoadedView';
import { ProjectsLoadingView } from './ProjectsLoadingView';
import {
  ErrorFetchingEvent,
  FetchedProjectsEvent,
  ProjectstUpdatedEvent,
  ProjectsViewContext,
  ProjectsViewEvent,
  projectsViewMachine,
} from './ProjectsViewMachine';

const getProjectsQuery = gql`
  query getProjects {
    viewer {
      projects {
        id
        name
        accessLevel
      }
    }
  }
`;

/**
 * Displays a list of all the projects available to the user and a link to create a new one.
 *
 * @author hmarchadour
 * @author pcdavid
 * @author fbarbin
 * @author sbegaudeau
 */
export const ProjectsView = () => {
  const [{ value, context }, dispatch] = useMachine<ProjectsViewContext, ProjectsViewEvent>(projectsViewMachine);
  const { projects, message } = context;

  // Load current project list
  const { loading: projectsLoading, data: projectsData, error: projectsError } = useQuery(getProjectsQuery);
  useEffect(() => {
    if (!projectsLoading) {
      if (projectsError) {
        const errorFetching = { type: 'ERROR_FETCHING', message: projectsError.message } as ErrorFetchingEvent;
        dispatch(errorFetching);
      } else if (projectsData) {
        let { projects } = projectsData?.viewer;
        const fetchedProjects = { type: 'FETCHED_PROJECTS', projects } as FetchedProjectsEvent;
        dispatch(fetchedProjects);
      }
    }
  }, [projectsLoading, projectsData, projectsError, dispatch]);

  // Setup callback to update project list when invoked
  const [getProjects, { loading, error, data }] = useLazyQuery(getProjectsQuery, { fetchPolicy: 'no-cache' });
  useEffect(() => {
    if (!loading) {
      if (error) {
        const errorFetching = { type: 'ERROR_FETCHING', message: error.message } as ErrorFetchingEvent;
        dispatch(errorFetching);
      } else if (data) {
        let { projects } = data?.viewer;
        const projectsUpdatedEvent = { type: 'PROJECTS_UPDATED', projects } as ProjectstUpdatedEvent;
        dispatch(projectsUpdatedEvent);
      }
    }
  }, [loading, error, data, dispatch]);

  let view = null;
  switch (value) {
    case 'loading':
      view = <ProjectsLoadingView />;
      break;
    case 'empty':
      view = <ProjectsEmptyView />;
      break;
    case 'error':
      view = <ProjectsErrorView message={message} />;
      break;
    case 'loaded':
      view = <ProjectsLoadedView projects={projects} onProjectUpdated={getProjects} />;
      break;
    default:
      view = <ProjectsErrorView message={message} />;
  }
  return <div>{view}</div>;
};
