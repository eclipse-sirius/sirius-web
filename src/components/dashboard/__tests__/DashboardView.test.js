/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

import React from 'react';
import { MemoryRouter } from 'react-router-dom';
import Renderer from 'react-test-renderer';

import { DashboardView } from '../DashboardView';
import {
  ERROR__STATE,
  INITIAL__STATE,
  LOADING__STATE,
  DASHBOARD_LOADED__STATE
} from '../DashboardViewFiniteStateMachine';

describe('DashboardView', () => {
  it('renders an error card for an error', () => {
    const error = {
      title: 'An error has occurred',
      message: 'Please contact the administrator to try to find a solution',
      code: 500
    };
    const dashboard = {
      projectsCount: 0,
      viewpointsCount: 27,
      metamodelsCount: 132,
      projects: []
    };
    const dashboardComponent = Renderer.create(
      <MemoryRouter initialEntries={['/']}>
        <DashboardView stateId={ERROR__STATE} error={error} dashboard={dashboard} />
      </MemoryRouter>
    );
    expect(dashboardComponent.toJSON()).toMatchSnapshot();
  });

  it('renders an error card for an unsupported state', () => {
    const dashboard = {
      projectsCount: 0,
      viewpointsCount: 27,
      metamodelsCount: 132,
      projects: []
    };
    const dashboardComponent = Renderer.create(
      <MemoryRouter initialEntries={['/']}>
        <DashboardView stateId={INITIAL__STATE} error={null} dashboard={dashboard} />
      </MemoryRouter>
    );
    expect(dashboardComponent.toJSON()).toMatchSnapshot();
  });

  it('renders a loading indicator during the loading', () => {
    const dashboard = {
      projectsCount: 0,
      viewpointsCount: 27,
      metamodelsCount: 132,
      projects: []
    };
    const dashboardComponent = Renderer.create(
      <MemoryRouter initialEntries={['/']}>
        <DashboardView stateId={LOADING__STATE} error={null} dashboard={dashboard} />
      </MemoryRouter>
    );
    expect(dashboardComponent.toJSON()).toMatchSnapshot();
  });

  it('renders the projects', () => {
    const projects = [{ name: 'First Project' }, { name: 'Second Project' }];
    const dashboard = {
      projectsCount: 0,
      viewpointsCount: 27,
      metamodelsCount: 132,
      projects
    };
    const dashboardComponent = Renderer.create(
      <MemoryRouter initialEntries={['/']}>
        <DashboardView stateId={DASHBOARD_LOADED__STATE} error={null} dashboard={dashboard} />
      </MemoryRouter>
    );
    expect(dashboardComponent.toJSON()).toMatchSnapshot();
  });
});
