/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

import React from 'react';
import ReactDOM from 'react-dom';
import ReactTestUtils from 'react-dom/test-utils';
import Renderer from 'react-test-renderer';

import {
  BUTTON_DANGER__KIND,
  BUTTON_DEFAULT__KIND,
  BUTTON_PRIMARY__KIND,
  BUTTON_SUCCESS__KIND,
  BUTTON_WARNING__KIND,
  Button
} from '../Button';

describe('Button', () => {
  it('renders a button without a label', () => {
    const button = Renderer.create(<Button />);
    expect(button.toJSON()).toMatchSnapshot();
  });

  it('renders a button with a label', () => {
    const button = Renderer.create(<Button>OK</Button>);
    expect(button.toJSON()).toMatchSnapshot();
  });

  it('renders a default button', () => {
    const button = Renderer.create(<Button kind={BUTTON_DEFAULT__KIND} />);
    expect(button.toJSON()).toMatchSnapshot();
  });

  it('renders a primary button', () => {
    const button = Renderer.create(<Button kind={BUTTON_PRIMARY__KIND} />);
    expect(button.toJSON()).toMatchSnapshot();
  });

  it('renders a success button', () => {
    const button = Renderer.create(<Button kind={BUTTON_SUCCESS__KIND} />);
    expect(button.toJSON()).toMatchSnapshot();
  });

  it('renders a warning button', () => {
    const button = Renderer.create(<Button kind={BUTTON_WARNING__KIND} />);
    expect(button.toJSON()).toMatchSnapshot();
  });

  it('renders a danger button', () => {
    const button = Renderer.create(<Button kind={BUTTON_DANGER__KIND} />);
    expect(button.toJSON()).toMatchSnapshot();
  });

  describe('Handles DOM events', () => {
    it('can trigger the onClick event handler', () => {
      let hasBeenClicked = false;

      const div = document.createElement('div');
      ReactDOM.render(<Button onClick={() => (hasBeenClicked = true)}>OK</Button>, div);

      const buttonDOMNode = div.childNodes[0];

      ReactTestUtils.Simulate.click(buttonDOMNode);
      expect(hasBeenClicked).toEqual(true);
    });
  });
});
