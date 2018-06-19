/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/
import React from 'react';
import PropTypes from 'prop-types';

import { classNames } from '../../common/classnames';

import {
  LIGHT,
  REGULAR,
  SEMI_BOLD,
  BOLD,
  EXTRA_BOLD,
  EXTRA_SMALL,
  SMALL,
  MEDIUM,
  LARGE,
  EXTRA_LARGE,
  EXTRA_EXTRA_LARGE
} from './TextConstants';

import './Text.css';

const propTypes = {
  children: PropTypes.string,
  loading: PropTypes.bool,
  weight: PropTypes.oneOf([LIGHT, REGULAR, SEMI_BOLD, BOLD, EXTRA_BOLD]).isRequired,
  size: PropTypes.oneOf([EXTRA_SMALL, SMALL, MEDIUM, LARGE, EXTRA_LARGE, EXTRA_EXTRA_LARGE])
    .isRequired,
  hideOverflow: PropTypes.bool
};
const defaultProps = {
  weight: REGULAR,
  size: SMALL
};

export const Text = ({ children, className, weight, size, hideOverflow, loading, ...props }) => {
  let textClassNames = classNames('text', size, weight);
  if (hideOverflow) {
    textClassNames = classNames(textClassNames, 'hideoverflow');
  }
  if (loading) {
    textClassNames = classNames(textClassNames, 'loading');
  }
  textClassNames = classNames(textClassNames, className);
  return (
    <div className={textClassNames} {...props}>
      {children}
    </div>
  );
};
Text.propTypes = propTypes;
Text.defaultProps = defaultProps;
