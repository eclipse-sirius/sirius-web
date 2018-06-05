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
import { LoadingProvider } from '../../loading/Loading';
import { WorkflowCard } from '../../workflow/WorkflowCard';

import { ProjectHeaderCard } from '../header/ProjectHeaderCard';
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
export const ProjectView = ({
  className,
  stateId,
  error,
  project,
  pageIdentifier,
  onTabClick,
  onActivityClick,
  onNewDescription,
  ...props
}) => {
  switch (stateId) {
    case LOADING__STATE:
      return renderLoadingState(className, props);
    case ERROR__STATE:
      return renderErrorState(className, error, props);
    case PROJECT_LOADED__STATE:
      return renderProjectLoadedState(
        className,
        project,
        pageIdentifier,
        onTabClick,
        onActivityClick,
        onNewDescription,
        props
      );
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
  const project = {};
  const pageIdentifier = undefined;
  const onTabClick = () => {};
  const onActivityClick = () => {};
  const onNewDescription = () => {};
  return renderProjectState(
    className,
    project,
    pageIdentifier,
    onTabClick,
    onActivityClick,
    onNewDescription,
    true,
    props
  );
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

const renderProjectLoadedState = (
  className,
  project,
  pageIdentifier,
  onTabClick,
  onActivityClick,
  onNewDescription,
  props
) =>
  renderProjectState(
    className,
    project,
    pageIdentifier,
    onTabClick,
    onActivityClick,
    onNewDescription,
    false,
    props
  );

const PROJECT_VIEW__CLASS_NAMES = 'projectview';
const PROJECT_VIEW_MAIN__CLASS_NAMES = 'projectview-main';
const PROJECT_VIEW_DETAILS__CLASS_NAMES = 'projectview-details';
const PROJECT_VIEW_WORKFLOW__CLASS_NAMES = 'projectview-workflow';

/**
 * Renders the project.
 * @param {*} className The class name of the project
 * @param {*} project The project to be displayed
 * @param {*} pageIdentifier The identifier of the page displayed in the workflow
 * @param {*} onTabClick The callback executed when a tab is clicked
 * @param {*} onActivityClick The callback executed when an activity is clicked
 * @param {*} onNewDescription The callback executed when the description is updated
 * @param {*} loading Indicates if the page is being loaded
 * @param {*} props The properties of the component
 */
const renderProjectState = (
  className,
  project,
  pageIdentifier,
  onTabClick,
  onActivityClick,
  onNewDescription,
  loading,
  props
) => {
  const projectViewClassNames = classNames(PROJECT_VIEW__CLASS_NAMES, className);
  return (
    <LoadingProvider loading={loading}>
      <div className={projectViewClassNames}>
        <ProjectHeaderCard
          name={project.name}
          description={project.description}
          onNewDescription={onNewDescription}
        />
        <div className={PROJECT_VIEW_MAIN__CLASS_NAMES}>
          <div className={PROJECT_VIEW_DETAILS__CLASS_NAMES}>
            <ProjectSemanticResourcesListCard semanticResources={project.semanticResources} />
            <ProjectRepresentationsListCard
              projectName={project.name}
              representations={project.representations}
            />
          </div>
          <div className={PROJECT_VIEW_WORKFLOW__CLASS_NAMES}>
            <WorkflowCard
              projectName={project.name}
              pageIdentifier={pageIdentifier}
              pages={project.pages}
              sections={project.currentPageSections}
              onTabClick={onTabClick}
              onActivityClick={onActivityClick}
            />
          </div>
        </div>
      </div>
    </LoadingProvider>
  );
};
