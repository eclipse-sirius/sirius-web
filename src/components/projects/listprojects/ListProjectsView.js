/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

import React from 'react';
import PropTypes from 'prop-types';

import { classNames } from '../../../common/classnames';
import { UNSUPPORTED_STATE } from '../../../common/errors';

import { ErrorCard } from '../../error/ErrorCard';
import { Loading } from '../../loading/Loading';

import { ProjectsListCard } from '../ProjectsListCard';

import {
  ERROR__STATE,
  LOADING__STATE,
  PROJECTS_LOADED__STATE
} from './ListProjectsViewFiniteStateMachine';

const LIST_PROJECTS_VIEW__CLASS_NAMES = 'listprojectsview';

const propTypes = {
  stateId: PropTypes.string.isRequired
};

/**
 * The ListProjectsView component is used to display the list of all the projects.
 */
export const ListProjectsView = ({ className, stateId, error, projects, ...props }) => {
  switch (stateId) {
    case LOADING__STATE:
      return renderLoadingState(className, props);
    case ERROR__STATE:
      return renderErrorState(className, error, props);
    case PROJECTS_LOADED__STATE:
      return renderProjectsLoadedState(className, projects, props);
    default:
      const undefinedStateError = {
        title: `The projects list is in an unsupported state: ${stateId}`,
        message: 'Contact your administrator to find a suitable solution',
        code: UNSUPPORTED_STATE
      };
      return renderErrorState(className, undefinedStateError, props);
  }
};
ListProjectsView.propTypes = propTypes;

/**
 * Renders the loading state of the projects list.
 * @param {*} className The class name of the projects list
 * @param {*} props The properties of the component
 */
const renderLoadingState = (className, props) => <Loading className={className} {...props} />;

/**
 * Renders the error.
 * @param {*} className The class name of the dashboard
 * @param {*} error The error to render
 * @param {*} props The properties of the component
 */
const renderErrorState = (className, error, props) => (
  <ErrorCard className={className} {...error} {...props} />
);

/**
 * Renders the projects loaded.
 * @param {*} className The class name of the projects list
 * @param {*} projects The projects to be displayed
 * @param {*} props The properties of the component
 */
const renderProjectsLoadedState = (className, projects, props) => {
  const listProjectsViewClassNames = classNames(LIST_PROJECTS_VIEW__CLASS_NAMES, className);
  return (
    <div className={listProjectsViewClassNames}>
      <ProjectsListCard projects={projects} {...props} />
    </div>
  );
};
