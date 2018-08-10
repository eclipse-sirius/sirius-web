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

import './IconMenu.css';

const ICONMENU__CLASS_NAMES = 'iconmenu';

/**
 * The menu SVG icon.
 */
export const IconMenu = ({ className, ...props }) => {
  const iconMenuClassNames = classNames(ICONMENU__CLASS_NAMES, className);
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      className={iconMenuClassNames}
      height="20"
      width="20"
      {...props}>
      <rect x="0" y="3" width="20" height="2" />
      <rect x="0" y="9" width="20" height="2" />
      <rect x="0" y="15" width="20" height="2" />
    </svg>
  );
};
