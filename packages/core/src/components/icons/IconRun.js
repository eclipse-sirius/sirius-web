/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

import React from 'react';

import { classNames } from '../../common/classnames';

import './IconRun.css';

const ICONRUN__CLASS_NAMES = 'iconrun';

/**
 * The run SVG icon.
 */
export const IconRun = ({ className, ...props }) => {
  const iconRunClassNames = classNames(ICONRUN__CLASS_NAMES, className);
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      className={iconRunClassNames}
      height="16"
      width="16"
      {...props}>
      <polygon points="0,0 16,8 0,16" />
    </svg>
  );
};
