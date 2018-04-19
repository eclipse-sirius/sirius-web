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

import { classNames } from '../../common/classnames';

import { Loading } from '../loading/Loading';
import { ProjectSummaryCard } from '../projects/ProjectSummaryCard';

import { LOADING__STATE, DASHBOARD_LOADED__STATE } from './DashboardViewFiniteStateMachine';

import './DashboardView.css';

const propTypes = {
  stateId: PropTypes.string.isRequired
};

/**
 * The DashboardView component is used as the main component in the dashboard page.
 * It will render a bird eye view of the state of the data of the user starting
 * with the list of the projects available.
 */
export const DashboardView = ({ className, stateId, dashboard, ...props }) => {
  switch (stateId) {
    case LOADING__STATE:
      return renderLoadingState(className, props);
    case DASHBOARD_LOADED__STATE:
      return renderDashboardLoadedState(className, dashboard, props);
    default:
      return renderLoadingState(className, props);
  }
};
DashboardView.propTypes = propTypes;

/**
 * Renders the loading state of the dashboard.
 * @param {*} className The class name of the dashboard.
 * @param {*} props The properties of the component
 */
const renderLoadingState = (className, props) => <Loading className={className} {...props} />;

const DASHBOARD_VIEW__CLASS_NAMES = 'dashboardview';
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
  return (
    <div className={dashboardViewClassNames} {...props}>
      <div className={PROJECTS__CLASS_NAMES}>
        <div className={PROJECTS_BODY__CLASS_NAMES}>
          {dashboard.projects.map(project => (
            <ProjectSummaryCard key={project.name} project={project} />
          ))}
        </div>
      </div>
    </div>
  );
};
