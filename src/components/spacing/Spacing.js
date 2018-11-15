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
import { css } from 'emotion';

import { XS, S, M, L, XL, XXL } from './SpacingConstants';

const propTypes = {
  top: PropTypes.oneOf([XS, S, M, L, XL, XXL]),
  right: PropTypes.oneOf([XS, S, M, L, XL, XXL]),
  bottom: PropTypes.oneOf([XS, S, M, L, XL, XXL]),
  left: PropTypes.oneOf([XS, S, M, L, XL, XXL])
};

/**
 * The Spacing component is used to help manage the layout of the components.
 *
 * It will compute a padding for the div which will encapsulate the children from
 * the given value of top, right, bottom and left.
 */
export const Spacing = ({ children, top, right, bottom, left }) => {
  const style = css({
    paddingTop: top,
    paddingRight: right,
    paddingBottom: bottom,
    paddingLeft: left
  });
  return <div className={style}>{children}</div>;
};
Spacing.propTypes = propTypes;
