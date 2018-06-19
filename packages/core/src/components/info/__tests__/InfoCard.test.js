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

import { InfoCard } from '../InfoCard';

describe('InfoCard', () => {
  it('renders an info card', () => {
    const infoCard = Renderer.create(<InfoCard title="42" message="Projects" />);
    expect(infoCard.toJSON()).toMatchSnapshot();
  });
});
