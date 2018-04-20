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

import { ProjectView } from '../ProjectView';

import {
  ERROR__STATE,
  INITIAL__STATE,
  LOADING__STATE,
  PROJECT_LOADED__STATE
} from '../ProjectViewFiniteStateMachine';

describe('ProjectView', () => {
  it('renders an error card for an error', () => {
    const error = {
      title: 'An error has occurred',
      message: 'Please contact the administrator to try to find a solution',
      code: 500
    };
    const project = Renderer.create(
      <MemoryRouter initialEntries={['/']}>
        <ProjectView stateId={ERROR__STATE} error={error} project={null} />
      </MemoryRouter>
    );
    expect(project.toJSON()).toMatchSnapshot();
  });

  it('renders an error card for an unsupported state', () => {
    const project = Renderer.create(
      <MemoryRouter initialEntries={['/']}>
        <ProjectView stateId={INITIAL__STATE} error={null} project={null} />
      </MemoryRouter>
    );
    expect(project.toJSON()).toMatchSnapshot();
  });

  it('renders a loading indicator during the loading', () => {
    const project = Renderer.create(
      <MemoryRouter initialEntries={['/']}>
        <ProjectView stateId={LOADING__STATE} error={null} project={null} />
      </MemoryRouter>
    );
    expect(project.toJSON()).toMatchSnapshot();
  });

  it('renders the project', () => {
    const project = {
      name: 'Project',
      representations: [{ name: 'Class Diagram' }, { name: 'Activity Diagram' }],
      semanticResources: [{ name: 'model.ecore', path: '/model/model.ecore' }]
    };
    const projectComponent = Renderer.create(
      <MemoryRouter initialEntries={['/']}>
        <ProjectView stateId={PROJECT_LOADED__STATE} error={null} project={project} />
      </MemoryRouter>
    );
    expect(projectComponent.toJSON()).toMatchSnapshot();
  });
});
