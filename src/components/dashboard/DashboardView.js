/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

import React from 'react';

import { classNames } from '../../common/classnames';

import { ProjectSummaryCard } from '../projects/ProjectSummaryCard';

import './DashboardView.css';

const DASHBOARD_VIEW__CLASS_NAMES = 'dashboard-view';
const PROJECTS__CLASS_NAMES = 'projects';
const PROJECTS_BODY__CLASS_NAMES = 'projects-body';

/**
 * The DashboardView component is used as the main component in the dashboard page.
 * It will render a bird eye view of the state of the data of the user starting
 * with the list of the projects available.
 */
export const DashboardView = ({ className }) => {
  const dashboardViewClassNames = classNames(DASHBOARD_VIEW__CLASS_NAMES, className);
  return (
    <div className={dashboardViewClassNames}>
      <div className={PROJECTS__CLASS_NAMES}>
        <div className={PROJECTS_BODY__CLASS_NAMES}>
          <ProjectSummaryCard
            key={'sirius'}
            project={{ name: 'Sirius', semanticResourcesCount: 14 }}
          />
          <ProjectSummaryCard
            key={'acceleo'}
            project={{ name: 'Acceleo', semanticResourcesCount: 5 }}
          />
          <ProjectSummaryCard
            key={'m2doc'}
            project={{ name: 'M2doc', semanticResourcesCount: 9 }}
          />
          <ProjectSummaryCard
            key={'emfcompare'}
            project={{ name: 'EMF Compare', semanticResourcesCount: 11 }}
          />
        </div>
      </div>
    </div>
  );
};
