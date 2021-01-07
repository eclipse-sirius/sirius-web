/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
 * This program and the accompanying materials
 * are made available under the erms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
import {
  EditProjectView,
  NewProjectView,
  NewModelerView,
  ProjectsView,
  ModelersView,
  UploadProjectView,
  withErrorBoundary,
  withCapabilities,
  withProject,
} from '@eclipse-sirius/sirius-components';
import React from 'react';
import { Redirect, Route, Switch } from 'react-router-dom';

/**
 * Defines the content of the main part of the user interface.
 *
 * @author sbegaudeau
 */
export const Main = () => {
  return (
    <Switch>
      <Route exact path="/new/project" component={withErrorBoundary(withCapabilities(NewProjectView))} />
      <Route exact path="/upload/project" component={withErrorBoundary(withCapabilities(UploadProjectView))} />
      <Route exact path="/projects" component={withErrorBoundary(withCapabilities(ProjectsView))} />
      <Route
        exact
        path="/projects/:projectId/modelers"
        component={withErrorBoundary(withCapabilities(withProject(ModelersView)))}
      />
      <Route
        exact
        path="/projects/:projectId/new/modeler"
        component={withErrorBoundary(withCapabilities(withProject(NewModelerView)))}
      />
      <Route
        exact
        path="/projects/:projectId/edit/:representationId?"
        component={withErrorBoundary(withCapabilities(withProject(EditProjectView)))}
      />
      <Redirect to="/projects" />
    </Switch>
  );
};
