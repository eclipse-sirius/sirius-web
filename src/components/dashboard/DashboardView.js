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
import { UNSUPPORTED_STATE } from '../../common/errors';

import { ErrorCard } from '../error/ErrorCard';
import { Loading } from '../loading/Loading';
import { Spacing } from '../spacing/Spacing';
import { Text } from '../text/Text';
import { BOLD, EXTRA_LARGE } from '../text/TextConstants';

import { DashboardOverviewSection } from './DashboardOverviewSection';
import {
  ERROR__STATE,
  LOADING__STATE,
  DASHBOARD_LOADED__STATE
} from './DashboardViewFiniteStateMachine';

import './DashboardView.css';
import { DashboardProjectsSection } from './DashboardProjectsSection';

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

/**
 * Renders the dashboard.
 * @param {*} className The class name of the dashboard
 * @param {*} dashboard The dashboard to display
 * @param {*} props The properties of the component
 */
const renderDashboardLoadedState = (className, dashboard, props) => {
  const dashboardViewClassNames = classNames(DASHBOARD_VIEW__CLASS_NAMES, className);

  const { projectsCount, viewpointsCount, metamodelsCount, projects } = dashboard;

  return (
    <div className={dashboardViewClassNames} {...props}>
      <Spacing>
        <Text size={EXTRA_LARGE} weight={BOLD}>
          Dashboard
        </Text>
      </Spacing>
      <DashboardOverviewSection
        projectsCount={projectsCount}
        viewpointsCount={viewpointsCount}
        metamodelsCount={metamodelsCount}
      />
      <DashboardProjectsSection projects={projects} />
    </div>
  );
};
