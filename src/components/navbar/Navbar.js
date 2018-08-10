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

import { IconMenu } from '../icons/IconMenu';
import { Spacing } from '../spacing/Spacing';
import { S, M } from '../spacing/SpacingConstants';
import { Text } from '../text/Text';
import { BOLD, EXTRA_EXTRA_LARGE } from '../text/TextConstants';

import './Navbar.css';

const NAVBAR__CLASS_NAMES = 'navbar';
const NAVBAR_CONTAINER__CLASS_NAMES = 'navbar-container';
const NARBAR_TITLE__CLASS_NAMES = 'navbar-title title-xxl';

const propTypes = {
  onHamburgerMenuClick: PropTypes.func.isRequired
};
const defaultProps = {
  onHamburgerMenuClick: () => {}
};

/**
 * The Navbar is used to display a navigation bar on top of the user interface.
 * It can contain the title of the application and various actions.
 */
export const Navbar = ({ className, onHamburgerMenuClick, ...props }) => {
  const navClassNames = classNames(NAVBAR__CLASS_NAMES, className);
  return (
    <nav className={navClassNames} {...props}>
      <Spacing top={S} right={M} bottom={S} left={M}>
        <div className={NAVBAR_CONTAINER__CLASS_NAMES}>
          <Spacing right={M}>
            <IconMenu onClick={onHamburgerMenuClick} />
          </Spacing>
          <div className={NARBAR_TITLE__CLASS_NAMES}>
            <Text weight={BOLD} size={EXTRA_EXTRA_LARGE}>
              Eclipse Sirius
            </Text>
          </div>
        </div>
      </Spacing>
    </nav>
  );
};
Navbar.propTypes = propTypes;
Navbar.defaultProps = defaultProps;
