/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

import { classNames } from '../classnames';

describe('classnames', () => {
  it('concatenates multiple class names', () => {
    expect(classNames('first', 'second', 'third')).toBe('first second third');
  });

  it('trims empty class names', () => {
    expect(classNames('first', 'second', '')).toBe('first second');
  });

  it('trims undefined class names', () => {
    expect(classNames('first', 'second', undefined)).toBe('first second');
  });

  it('trims null class names', () => {
    expect(classNames('first', 'second', null)).toBe('first second');
  });
});
