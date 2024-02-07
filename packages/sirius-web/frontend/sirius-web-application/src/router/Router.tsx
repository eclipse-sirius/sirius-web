/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
import { useData } from '@eclipse-sirius/sirius-components-core';
import { Redirect, Route, Switch } from 'react-router-dom';
import { withErrorBoundary } from '../errors/ErrorBoundary';
import { EditProjectView } from '../views/edit-project/EditProjectView';
import { NewProjectView } from '../views/new-project/NewProjectView';
import { ProjectBrowser } from '../views/project-browser/ProjectBrowser';
import { ProjectSettingsView } from '../views/project-settings/ProjectSettingsView';
import { UploadProjectView } from '../views/upload-project/UploadProjectView';
import { routerExtensionPoint } from './RouterExtensionPoints';

export const Router = () => {
  const { data: routes } = useData(routerExtensionPoint);
  return (
    <Switch>
      <Route exact path="/new/project" component={withErrorBoundary(NewProjectView)} />
      <Route exact path="/upload/project" component={withErrorBoundary(UploadProjectView)} />
      <Route exact path="/projects" component={withErrorBoundary(ProjectBrowser)} />
      <Route exact path="/projects/:projectId/edit/:representationId?" component={withErrorBoundary(EditProjectView)} />
      <Route exact path="/projects/:projectId/settings" component={withErrorBoundary(ProjectSettingsView)} />
      {routes.map((props, index) => (
        <Route key={index} {...props} />
      ))}
      <Redirect to="/projects" />
    </Switch>
  );
};
