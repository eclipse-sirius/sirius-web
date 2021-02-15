/*******************************************************************************
 * Copyright (c) 2021 THALES GLOBAL SERVICES.
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
import PropTypes from 'prop-types';
import React from 'react';

const propTypes = {
  title: PropTypes.string.isRequired,
};

export const ArrangeAll = ({ title, ...props }) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 0 24 24"
      aria-labelledby="title"
      aria-describedby="desc"
      role="img"
      {...props}>
      <title>{title}</title>
      <g fill="#fff" stroke="#000" stroke-linejoin="round">
        <rect x="1" y="1" width="22" height="9" rx="0" ry="3" stroke-width="2" />
        <rect x="1" y="13" width="6" height="10" rx="0" ry="3" stroke-width="2" />
        <rect x="10" y="13" width="13" height="10" rx="0" ry="3" stroke-width="2" />
      </g>
    </svg>
  );
};
ArrangeAll.propTypes = propTypes;
