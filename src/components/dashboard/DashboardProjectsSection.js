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

import { Link } from 'react-router-dom';

import { InfoCard } from '../info/InfoCard';
import { ProjectSummaryCard } from '../projects/ProjectSummaryCard';
import { Spacing } from '../spacing/Spacing';
import { S, M } from '../spacing/SpacingConstants';
import { Text } from '../text/Text';
import { MEDIUM, SEMI_BOLD } from '../text/TextConstants';

import { classNames } from '../../common/classnames';

import './DashboardProjectsSection.css';

const DASHBORDPROJECTSSECTION__CLASS_NAMES = 'dashboardprojectssection';
const DASHBORDPROJECTSSECTION_CONTAINER__CLASS_NAMES = 'dashboardprojectssection-container';
const DASHBORDPROJECTSSECTION_NEWCARD__CLASS_NAMES = 'dashboardprojectssection-newcard';

const propTypes = {
  projects: PropTypes.array.isRequired
};
const defaultProps = {
  projects: []
};

/**
 * The DashboardProjectsSection displays a list of projects in the Dashboard.
 */
export const DashboardProjectsSection = ({ className, projects, ...props }) => {
  const dashboardProjectsSectionClassNames = classNames(
    DASHBORDPROJECTSSECTION__CLASS_NAMES,
    className
  );
  return (
    <div className={dashboardProjectsSectionClassNames}>
      <Spacing top={M} bottom={M}>
        <Text size={MEDIUM} weight={SEMI_BOLD}>
          <Link to="/projects">Projects</Link>
        </Text>
        <Spacing top={S}>
          <div className={DASHBORDPROJECTSSECTION_CONTAINER__CLASS_NAMES}>
            <Link to="/newproject">
              <InfoCard
                className={DASHBORDPROJECTSSECTION_NEWCARD__CLASS_NAMES}
                title="+"
                message="New Project"
                to=""
              />
            </Link>
            {projects.map(project => <ProjectSummaryCard key={project.name} project={project} />)}
          </div>
        </Spacing>
      </Spacing>
    </div>
  );
};
DashboardProjectsSection.propTypes = propTypes;
DashboardProjectsSection.defaultProps = defaultProps;
