/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
import React from 'react';
import '@testing-library/jest-dom';
import { render } from '@testing-library/react';
import { M, Spacing } from 'core/spacing/Spacing';

describe('Spacing', () => {
  it('renders top spacing', () => {
    const content = 'Top Spacing';
    const topSpacing = (
      // tag::topSpacing[]
      <Spacing top={M}>{content}</Spacing>
      // end::topSpacing[]
    );
    const { getByText } = render(topSpacing);
    expect(getByText(content)).toHaveAttribute('style', `padding-top: ${M};`);
  });

  it('renders right spacing', () => {
    const content = 'Right Spacing';
    const rightSpacing = (
      // tag::rightSpacing[]
      <Spacing right={M}>{content}</Spacing>
      // end::rightSpacing[]
    );
    const { getByText } = render(rightSpacing);
    expect(getByText(content)).toHaveAttribute('style', `padding-right: ${M};`);
  });

  it('renders bottom spacing', () => {
    const content = 'Bottom Spacing';
    const bottomSpacing = (
      // tag::bottomSpacing[]
      <Spacing bottom={M}>{content}</Spacing>
      // end::bottomSpacing[]
    );
    const { getByText } = render(bottomSpacing);
    expect(getByText(content)).toHaveAttribute('style', `padding-bottom: ${M};`);
  });

  it('renders left spacing', () => {
    const content = 'Left Spacing';
    const leftSpacing = (
      // tag::leftSpacing[]
      <Spacing left={M}>{content}</Spacing>
      // end::leftSpacing[]
    );
    const { getByText } = render(leftSpacing);
    expect(getByText(content)).toHaveAttribute('style', `padding-left: ${M};`);
  });
});
