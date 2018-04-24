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

import { DashboardView } from '../dashboard/DashboardView';
import { ErrorBoundary } from '../error/ErrorBoundary';
import { ListProjectsView } from '../projects/listprojects/ListProjectsView';
import { NewProjectView } from '../projects/newproject/NewProjectView';
import { ProjectView } from '../projects/project/ProjectView';

import { DashboardViewStateContainer } from '../../containers/dashboard/DashboardViewStateContainer';
import { ListProjectsViewStateContainer } from '../../containers/projects/ListProjectsViewStateContainer';
import { NewProjectViewStateContainer } from '../../containers/projects/NewProjectViewStateContainer';
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
      <ErrorBoundary>
        <Switch>
          <Route exact path="/" render={renderDashboardViewStateContainer} />
          <Route exact path="/projects" render={renderListProjectsViewStateContainer} />
          <Route exact path="/projects/:projectName" render={renderProjectViewStateContainer} />
          <Route exact path="/newproject" render={renderNewProjectViewStateContainer} />
        </Switch>
      </ErrorBoundary>
    </main>
  );
};

const renderDashboardViewStateContainer = () => (
  <DashboardViewStateContainer>
    {(stateId, error, dashboard) => (
      <DashboardView stateId={stateId} error={error} dashboard={dashboard} />
    )}
  </DashboardViewStateContainer>
);

const renderListProjectsViewStateContainer = () => (
  <ListProjectsViewStateContainer>
    {(stateId, error, projects) => (
      <ListProjectsView stateId={stateId} error={error} projects={projects} />
    )}
  </ListProjectsViewStateContainer>
);

const renderProjectViewStateContainer = () => (
  <ProjectViewStateContainer>
    {(stateId, error, project) => <ProjectView stateId={stateId} error={error} project={project} />}
  </ProjectViewStateContainer>
);

const renderNewProjectViewStateContainer = () => (
  <NewProjectViewStateContainer>
    {(stateId, errors, isValid, onSubmit, name, nameIsValid, nameErrors, onNameChange) => (
      <NewProjectView
        stateId={stateId}
        errors={errors}
        isValid={isValid}
        onSubmit={onSubmit}
        name={name}
        nameIsValid={nameIsValid}
        nameErrors={nameErrors}
        onNameChange={onNameChange}
      />
    )}
  </NewProjectViewStateContainer>
);
