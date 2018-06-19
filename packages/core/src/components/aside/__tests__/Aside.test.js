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

import { Aside } from '../Aside';

describe('Aside', () => {
  it('renders the aside component', () => {
    const aside = Renderer.create(
      <MemoryRouter initialEntries={['/']}>
        <Aside />
      </MemoryRouter>
    );
    expect(aside.toJSON()).toMatchSnapshot();
  });
});
