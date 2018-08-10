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

import { InfoCard } from '../info/InfoCard';
import { Spacing } from '../spacing/Spacing';
import { S, M } from '../spacing/SpacingConstants';
import { Text } from '../text/Text';
import { MEDIUM, SEMI_BOLD } from '../text/TextConstants';

import './DashboardOverviewSection.css';

const DASHBOARDOVERVIEWSECTION__CLASSNAMES = 'dashboardoverviewsection';
const DASHBOARDOVERVIEWSECTION_CONTAINER__CLASSNAMES = 'dashboardoverviewsection-container';
const PROJECTS_INFOCARD__CLASS_NAMES = 'projectsinfocard';
const VIEWPOINTS_INFOCARD__CLASS_NAMES = 'viewpointsinfocard';
const METAMODELS_INFOCARD__CLASS_NAMES = 'metamodelsinfocard';

const propTypes = {
  projectsCount: PropTypes.number.isRequired,
  viewpointsCount: PropTypes.number.isRequired,
  metamodelsCount: PropTypes.number.isRequired
};
const defaultProps = {
  projectsCount: 0,
  viewpointsCount: 0,
  metamodelsCount: 0
};

/**
 * The DashboardOverviewSection defines the Overview part of the dashboard view.
 */
export const DashboardOverviewSection = ({
  className,
  projectsCount,
  viewpointsCount,
  metamodelsCount,
  ...props
}) => {
  const dashboardOverviewSectionClassNames = classNames(
    DASHBOARDOVERVIEWSECTION__CLASSNAMES,
    className
  );
  return (
    <div className={dashboardOverviewSectionClassNames} {...props}>
      <Spacing top={M} bottom={M}>
        <Text size={MEDIUM} weight={SEMI_BOLD}>
          Overview
        </Text>
        <Spacing top={S}>
          <div className={DASHBOARDOVERVIEWSECTION_CONTAINER__CLASSNAMES}>
            <InfoCard
              className={PROJECTS_INFOCARD__CLASS_NAMES}
              title={projectsCount.toString()}
              message="Projects"
            />
            <InfoCard
              className={VIEWPOINTS_INFOCARD__CLASS_NAMES}
              title={viewpointsCount.toString()}
              message="Viewpoints"
            />
            <InfoCard
              className={METAMODELS_INFOCARD__CLASS_NAMES}
              title={metamodelsCount.toString()}
              message="Metamodels"
            />
          </div>
        </Spacing>
      </Spacing>
    </div>
  );
};
DashboardOverviewSection.propTypes = propTypes;
DashboardOverviewSection.defaultProps = defaultProps;
