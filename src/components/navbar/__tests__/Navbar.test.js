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

import { Navbar } from '../Navbar';

describe('Navbar', () => {
  it('renders the navbar component', () => {
    const navbar = Renderer.create(
      <MemoryRouter initialEntries={['/']}>
        <Navbar />
      </MemoryRouter>
    );
    expect(navbar.toJSON()).toMatchSnapshot();
  });
});
