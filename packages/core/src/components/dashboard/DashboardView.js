/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

import React from 'react';
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';

import { classNames } from '../../common/classnames';
import { UNSUPPORTED_STATE } from '../../common/errors';

import { ErrorCard } from '../error/ErrorCard';
import { InfoCard } from '../info/InfoCard';
import { Loading } from '../loading/Loading';
import { ProjectSummaryCard } from '../projects/ProjectSummaryCard';

import {
  ERROR__STATE,
  LOADING__STATE,
  DASHBOARD_LOADED__STATE
} from './DashboardViewFiniteStateMachine';

import './DashboardView.css';

const propTypes = {
  stateId: PropTypes.string.isRequired
};

/**
 * The DashboardView component is used as the main component in the dashboard page.
 * It will render a bird eye view of the state of the data of the user starting
 * with the list of the projects available.
 */
export const DashboardView = ({ className, stateId, error, dashboard, ...props }) => {
  switch (stateId) {
    case LOADING__STATE:
      return renderLoadingState(className, props);
    case ERROR__STATE:
      return renderErrorState(className, error, props);
    case DASHBOARD_LOADED__STATE:
      return renderDashboardLoadedState(className, dashboard, props);
    default:
      const undefinedStateError = {
        title: `The dashboard is in an unsupported state: ${stateId}`,
        message: 'Contact your administrator to find a suitable solution',
        code: UNSUPPORTED_STATE
      };
      return renderErrorState(className, undefinedStateError, props);
  }
};
DashboardView.propTypes = propTypes;

/**
 * Renders the loading state of the dashboard.
 * @param {*} className The class name of the dashboard.
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

const DASHBOARD_VIEW__CLASS_NAMES = 'dashboardview';
const INFO__CLASS_NAMES = 'info';
const PROJECTS_INFO__CLASS_NAMES = 'projectsinfo';
const VIEWPOINTS_INFO__CLASS_NAMES = 'viewpointsinfo';
const METAMODELS_INFO__CLASS_NAMES = 'metamodelsinfo';
const NEWPROJECT_INFO__CLASS_NAMES = 'newprojectinfo';
const PROJECTS__CLASS_NAMES = 'projects';
const PROJECTS_BODY__CLASS_NAMES = 'projects-body';

/**
 * Renders the dashboard.
 * @param {*} className The class name of the dashboard
 * @param {*} dashboard The dashboard to display
 * @param {*} props The properties of the component
 */
const renderDashboardLoadedState = (className, dashboard, props) => {
  const dashboardViewClassNames = classNames(DASHBOARD_VIEW__CLASS_NAMES, className);

  const { projectsCount, viewpointsCount, metamodelsCount } = dashboard;

  return (
    <div className={dashboardViewClassNames} {...props}>
      <div className={INFO__CLASS_NAMES}>
        <InfoCard
          className={PROJECTS_INFO__CLASS_NAMES}
          title={projectsCount.toString()}
          message={'Projects'}
        />
        <InfoCard
          className={VIEWPOINTS_INFO__CLASS_NAMES}
          title={viewpointsCount.toString()}
          message={'Viewpoints'}
        />
        <InfoCard
          className={METAMODELS_INFO__CLASS_NAMES}
          title={metamodelsCount.toString()}
          message={'Metamodels'}
        />
      </div>
      <div className={PROJECTS__CLASS_NAMES}>
        <div className={PROJECTS_BODY__CLASS_NAMES}>
          <Link to="/newproject">
            <InfoCard
              className={NEWPROJECT_INFO__CLASS_NAMES}
              title="+"
              message="New Project"
              to=""
            />
          </Link>
          {dashboard.projects.map(project => (
            <ProjectSummaryCard key={project.name} project={project} />
          ))}
        </div>
      </div>
    </div>
  );
};
