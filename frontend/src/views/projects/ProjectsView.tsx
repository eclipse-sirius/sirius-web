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
import gql from 'graphql-tag';
import React, { useEffect, useReducer } from 'react';
import {
  EMPTY__STATE,
  ERROR__STATE,
  HANDLE_ERROR_FETCHING_PROJECTS__ACTION,
  HANDLE_FETCHED_PROJECTS__ACTION,
  HANDLE_PROJECTS_UPDATED__ACTION,
  LOADED__STATE,
  LOADING__STATE,
} from './machine';
import { ProjectsEmptyView } from './ProjectsEmptyView';
import { ProjectsErrorView } from './ProjectsErrorView';
import { ProjectsLoadedView } from './ProjectsLoadedView';
import { ProjectsLoadingView } from './ProjectsLoadingView';
import { initialState, reducer } from './reducer';

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
  const [state, dispatch] = useReducer(reducer, initialState);
  const { viewState, projects, message } = state;

  // Load current project list
  const { loading: projectsLoading, data: projectsData, error: projectsError } = useQuery(getProjectsQuery);
  useEffect(() => {
    if (!projectsLoading) {
      if (projectsError) {
        dispatch({ type: HANDLE_ERROR_FETCHING_PROJECTS__ACTION, error: projectsError });
      } else if (projectsData) {
        dispatch({ type: HANDLE_FETCHED_PROJECTS__ACTION, response: projectsData });
      }
    }
  }, [projectsLoading, projectsData, projectsError]);

  // Setup callback to update project list when invoked
  const [getProjects, { loading, error, data }] = useLazyQuery(getProjectsQuery);
  useEffect(() => {
    if (!loading) {
      if (error) {
        dispatch({ type: HANDLE_ERROR_FETCHING_PROJECTS__ACTION, error });
      } else if (data) {
        dispatch({ type: HANDLE_PROJECTS_UPDATED__ACTION, response: data });
      }
    }
  }, [loading, error, data]);

  let view = null;
  switch (viewState) {
    case LOADING__STATE:
      view = <ProjectsLoadingView />;
      break;
    case EMPTY__STATE:
      view = <ProjectsEmptyView />;
      break;
    case ERROR__STATE:
      view = <ProjectsErrorView message={message} />;
      break;
    case LOADED__STATE:
      view = <ProjectsLoadedView projects={projects} onProjectUpdated={getProjects} />;
      break;
    default:
      view = <ProjectsErrorView message={message} />;
  }
  return <div>{view}</div>;
};
