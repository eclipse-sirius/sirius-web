/*******************************************************************************
 * Copyright (c) 2020 Obeo.
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
import PropTypes from 'prop-types';

const propTypes = {
  title: PropTypes.string.isRequired,
};

export const SiriusIcon = ({ title, ...props }) => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      viewBox="0 -6 15 25"
      aria-labelledby="title"
      aria-describedby="desc"
      role="img"
      {...props}>
      <title>{title}</title>
      <g clipPath="url(#clip0)">
      <path d="M10.06,6.74c-1.26,2-4,3.19-3.67,6,.09.95.87,2.35,1.83,2.55a4.54,4.54,0,0,1-3.43-7.2C5.68,6.61,7.66,5.7,8.37,4.19A2.4,2.4,0,0,0,7,.77,3.57,3.57,0,0,1,10.6,3.08,4.35,4.35,0,0,1,10.06,6.74Zm1.12,3.8a3,3,0,0,0,0-4.24c-.08,2.05-2.27,3-3.22,4.58a2.81,2.81,0,0,0-.45,2,2.73,2.73,0,0,0,3.17,2.17,2.11,2.11,0,0,1-.92-2A4.54,4.54,0,0,1,11.18,10.54Z" transform="translate(-3.94 -0.74)"/>
      </g>
      <defs>
        <clipPath id="clip0">
          <rect width="24.0006" height="25" />
        </clipPath>
      </defs>
    </svg>
  );
};
SiriusIcon.propTypes = propTypes;
