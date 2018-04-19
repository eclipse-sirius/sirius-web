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

import { ErrorCard } from '../ErrorCard';

describe('ErrorCard', () => {
  it('renders an error card', () => {
    const errorCard = Renderer.create(
      <ErrorCard
        title="An error has occurred"
        message="Please contact your administrator to find out more about this error"
        code={500}
      />
    );
    expect(errorCard.toJSON()).toMatchSnapshot();
  });
});
