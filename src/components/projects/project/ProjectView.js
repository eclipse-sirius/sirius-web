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

import { ProjectHeaderCard } from '../ProjectHeaderCard';
import { ProjectRepresentationsListCard } from '../ProjectRepresentationsListCard';
import { ProjectSemanticResourcesListCard } from '../ProjectSemanticResourcesListCard';

import {
  ERROR__STATE,
  LOADING__STATE,
  PROJECT_LOADED__STATE
} from './ProjectViewFiniteStateMachine';

import './ProjectView.css';

const propTypes = {
  stateId: PropTypes.string
};

/**
 * The ProjectView is used to display and manipulate a project.
 */
export const ProjectView = ({ className, stateId, error, project, ...props }) => {
  switch (stateId) {
    case LOADING__STATE:
      return renderLoadingState(className, props);
    case ERROR__STATE:
      return renderErrorState(className, error, props);
    case PROJECT_LOADED__STATE:
      return renderProjectLoadedState(className, project, props);
    default:
      const undefinedStateError = {
        title: `The project is in an unsupported state: ${stateId}`,
        message: 'Contact your administrator to find a suitable solution',
        code: UNSUPPORTED_STATE
      };
      return renderErrorState(className, undefinedStateError, props);
  }
};
ProjectView.propTypes = propTypes;

/**
 * Renders the loading state of the project.
 * @param {*} className The class name of the project
 * @param {*} props The properties of the component
 */
const renderLoadingState = (className, props) => {
  const projectViewLoadingClassNames = classNames('', className);
  return <Loading className={projectViewLoadingClassNames} {...props} />;
};

/**
 * Renders the error.
 * @param {*} className The class name of the dashboard
 * @param {*} error The error to render
 * @param {*} props The properties of the component
 */
const renderErrorState = (className, error, props) => {
  const projectViewErrorClassNames = classNames('', className);
  return <ErrorCard className={projectViewErrorClassNames} {...error} {...props} />;
};

const PROJECT_VIEW__CLASS_NAMES = 'projectview';
const PROJECT_VIEW_MAIN__CLASS_NAMES = 'projectview-main';
const PROJECT_VIEW_DETAILS__CLASS_NAMES = 'projectview-details';

/**
 * Renders the project loaded.
 * @param {*} className The class name of the project
 * @param {*} project The project to be displayed
 * @param {*} props The properties of the component
 */
const renderProjectLoadedState = (className, project, props) => {
  const projectViewClassNames = classNames(PROJECT_VIEW__CLASS_NAMES, className);
  return (
    <div className={projectViewClassNames}>
      <ProjectHeaderCard name={project.name} />
      <div className={PROJECT_VIEW_MAIN__CLASS_NAMES}>
        <div className={PROJECT_VIEW_DETAILS__CLASS_NAMES}>
          <ProjectSemanticResourcesListCard semanticResources={project.semanticResources} />
          <ProjectRepresentationsListCard representations={project.representations} />
        </div>
      </div>
    </div>
  );
};
