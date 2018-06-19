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

import { ListProjectsView } from '../ListProjectsView';

import {
  ERROR__STATE,
  INITIAL__STATE,
  LOADING__STATE,
  NO_PROJECTS_LOADED__STATE,
  PROJECTS_LOADED__STATE
} from '../ListProjectsViewFiniteStateMachine';

describe('ListProjectsView', () => {
  it('renders a blank card for an empty list of projects', () => {
    const listProjects = Renderer.create(
      <MemoryRouter initialEntries={['/']}>
        <ListProjectsView stateId={NO_PROJECTS_LOADED__STATE} error={null} projects={[]} />
      </MemoryRouter>
    );
    expect(listProjects.toJSON()).toMatchSnapshot();
  });

  it('renders an error card for an error', () => {
    const error = {
      title: 'An error has occurred',
      message: 'Please contact the administrator to try to find a solution',
      code: 500
    };
    const listProjects = Renderer.create(
      <MemoryRouter initialEntries={['/']}>
        <ListProjectsView stateId={ERROR__STATE} error={error} projects={[]} />
      </MemoryRouter>
    );
    expect(listProjects.toJSON()).toMatchSnapshot();
  });

  it('renders an error card for an unsupported state', () => {
    const listProjects = Renderer.create(
      <MemoryRouter initialEntries={['/']}>
        <ListProjectsView stateId={INITIAL__STATE} error={null} projects={[]} />
      </MemoryRouter>
    );
    expect(listProjects.toJSON()).toMatchSnapshot();
  });

  it('renders a loading indicator during the loading', () => {
    const listProjects = Renderer.create(
      <MemoryRouter initialEntries={['/']}>
        <ListProjectsView stateId={LOADING__STATE} error={null} projects={[]} />
      </MemoryRouter>
    );
    expect(listProjects.toJSON()).toMatchSnapshot();
  });

  it('renders the projects', () => {
    const projects = [{ name: 'First Project' }, { name: 'Second Project' }];
    const listProjects = Renderer.create(
      <MemoryRouter initialEntries={['/']}>
        <ListProjectsView stateId={PROJECTS_LOADED__STATE} error={null} projects={projects} />
      </MemoryRouter>
    );
    expect(listProjects.toJSON()).toMatchSnapshot();
  });
});
