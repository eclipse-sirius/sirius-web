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

import { Body, Card, Divider, Header, Title } from '../Card';

describe('Card', () => {
  it('renders an empty card', () => {
    const card = Renderer.create(<Card />);
    expect(card.toJSON()).toMatchSnapshot();
  });

  it('renders a card with a header and a body', () => {
    const card = Renderer.create(
      <Card>
        <Header>
          <Title>Card Title</Title>
        </Header>
        <Body />
      </Card>
    );
    expect(card.toJSON()).toMatchSnapshot();
  });

  it('renders a card with a divider between the header and body', () => {
    const card = Renderer.create(
      <Card>
        <Header>
          <Title>Card Title</Title>
        </Header>
        <Divider />
        <Body />
      </Card>
    );
    expect(card.toJSON()).toMatchSnapshot();
  });
});
