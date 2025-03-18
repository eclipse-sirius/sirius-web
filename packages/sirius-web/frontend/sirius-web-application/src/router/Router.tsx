/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
import { Navigate, Route, Routes, useRouteError } from 'react-router-dom';
import { ErrorView } from '../views/error/ErrorView';
import { routerExtensionPoint } from './RouterExtensionPoints';

const ErrorBoundary = () => {
  let error = useRouteError();
  console.error(error);
  return <p>An error has occurred, please contact your administrator or refresh the page...</p>;
};

export const Router = () => {
  const { data: routes } = useData(routerExtensionPoint);
  return (
    <Routes>
      <Route path="/errors/:code" element={<ErrorView />} />
      {routes.map((props, index) => (
        <Route key={index} {...props} ErrorBoundary={ErrorBoundary} />
      ))}
      <Route path="*" element={<Navigate to="/errors/404" />} />
    </Routes>
  );
};
