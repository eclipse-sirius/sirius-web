/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

import React from 'react';
import { MemoryRouter, Route } from 'react-router-dom';
import Renderer from 'react-test-renderer';

import { NewProjectView } from '../NewProjectView';

import {
  INITIAL__STATE,
  PRISTINE__STATE,
  MODIFIED__STATE,
  REDIRECT__STATE
} from '../NewProjectViewFiniteStateMachine';

describe('NewProjectView', () => {
  it('renders an error card for an unsupported state', () => {
    const newProject = Renderer.create(
      <MemoryRouter initialEntries={['/newproject']}>
        <NewProjectView stateId={INITIAL__STATE} />
      </MemoryRouter>
    );
    expect(newProject.toJSON()).toMatchSnapshot();
  });

  it('renders a card with a unsubmittable form to create a new project', () => {
    const newProject = Renderer.create(
      <MemoryRouter initialEntries={['/newproject']}>
        <NewProjectView stateId={PRISTINE__STATE} />
      </MemoryRouter>
    );
    expect(newProject.toJSON()).toMatchSnapshot();
  });

  it('renders a card with a form ready to be submitted to create a new project', () => {
    const newProject = Renderer.create(
      <MemoryRouter initialEntries={['/newproject']}>
        <NewProjectView stateId={MODIFIED__STATE} name="sirius" nameIsValid={true} isValid={true} />
      </MemoryRouter>
    );
    expect(newProject.toJSON()).toMatchSnapshot();
  });

  it('renders errors message', () => {
    const newProject = Renderer.create(
      <MemoryRouter initialEntries={['/newproject']}>
        <NewProjectView
          stateId={MODIFIED__STATE}
          name=":\/."
          nameErrors={['Invalid name']}
          nameIsValid={false}
          isValid={false}
          errors={['Project already exists']}
        />
      </MemoryRouter>
    );
    expect(newProject.toJSON()).toMatchSnapshot();
  });

  it('renders a redirect component once the project has been created', () => {
    const newProject = Renderer.create(
      <MemoryRouter initialEntries={['/newproject']}>
        <Route
          exact
          path="/newproject"
          render={() => <NewProjectView stateId={REDIRECT__STATE} name="test" />}
        />
      </MemoryRouter>
    );
    expect(newProject.toJSON()).toMatchSnapshot();
  });
});
