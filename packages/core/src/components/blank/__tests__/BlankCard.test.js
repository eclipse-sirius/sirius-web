/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

import React from 'react';
import Renderer from 'react-test-renderer';

import { BlankCard } from '../BlankCard';

describe('BlankCard', () => {
  it('renders a blank card', () => {
    const blankCard = Renderer.create(
      <BlankCard
        title="Nothing to see here"
        message="You should create new elements to see them here"
      />
    );
    expect(blankCard.toJSON()).toMatchSnapshot();
  });
});
