/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

import React from 'react';
import { NavLink } from 'react-router-dom';

import { classNames } from '../../common/classnames';

import { Spacing } from '../spacing/Spacing';
import { S, M } from '../spacing/SpacingConstants';

import './MainLink.css';

const MAINLINK__CLASS_NAMES = 'mainlink';
const MAINLINK_ACTIVE__CLASS_NAMES = 'mainlink--active';

/**
 * The MainLink component is used to create link in the Aside component.
 */
export const MainLink = ({ className, children, ...props }) => {
  const mainLinkClassNames = classNames(MAINLINK__CLASS_NAMES, className);
  return (
    <NavLink
      className={mainLinkClassNames}
      activeClassName={MAINLINK_ACTIVE__CLASS_NAMES}
      exact
      {...props}>
      <Spacing top={S} right={M} bottom={S} left={M}>
        {children}
      </Spacing>
    </NavLink>
  );
};
