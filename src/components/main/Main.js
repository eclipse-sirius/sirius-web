/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

import React from 'react';
import { Route, Switch } from 'react-router-dom';

import { classNames } from '../../common/classnames';

import { DashboardViewStateContainer } from '../../containers/dashboard/DashboardViewStateContainer';
import { ListProjectsViewStateContainer } from '../../containers/projects/ListProjectsViewStateContainer';
import { ProjectViewStateContainer } from '../../containers/projects/ProjectViewStateContainer';

import './Main.css';

const MAIN__CLASS_NAMES = 'main';

/**
 * The Main is used to define the content of the main area of the application.
 *
 * This component will define the routing strategy of the application with the
 * list of views and their associated URLs.
 */
export const Main = ({ className, ...props }) => {
  const mainClassNames = classNames(MAIN__CLASS_NAMES, className);
  return (
    <main className={mainClassNames} {...props}>
      <Switch>
        <Route exact path="/" component={DashboardViewStateContainer} />
        <Route exact path="/projects" component={ListProjectsViewStateContainer} />
        <Route exact path="/projects/:projectName" component={ProjectViewStateContainer} />
      </Switch>
    </main>
  );
};
