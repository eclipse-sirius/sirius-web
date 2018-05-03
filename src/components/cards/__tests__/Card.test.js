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

import { Card, Divider, PrimaryTitle } from '../Card';

describe('Card', () => {
  it('renders an empty card', () => {
    const card = Renderer.create(<Card />);
    expect(card.toJSON()).toMatchSnapshot();
  });

  it('renders a card with a primary title', () => {
    const card = Renderer.create(
      <Card>
        <PrimaryTitle label="Card Title" />
      </Card>
    );
    expect(card.toJSON()).toMatchSnapshot();
  });

  it('renders a card with a primary title and a divider', () => {
    const card = Renderer.create(
      <Card>
        <PrimaryTitle label="Card Title" />
        <Divider />
      </Card>
    );
    expect(card.toJSON()).toMatchSnapshot();
  });
});
