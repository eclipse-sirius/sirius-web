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
import { Spacing } from '../spacing/Spacing';
import { M, L } from '../spacing/SpacingConstants';
import { Text } from '../text/Text';
import { MEDIUM, REGULAR } from '../text/TextConstants';

import { MainLink } from './MainLink';

import './Aside.css';

const ASIDE__CLASS_NAMES = 'aside';
const ASIDE_MAIN_LINKS__CLASS_NAMES = 'aside-mainlinks';

/**
 * The Aside component is used to display the navigation links to move from one
 * page to another.
 *
 * It contains two different kinds of links. One the top, we will have the main
 * links which are used to navigate to the major parts of the application. Under
 * those, we will display the additional links which will mainly be links to
 * external websites for help and support.
 */
export const Aside = ({ className, ...props }) => {
  const asideClassNames = classNames(ASIDE__CLASS_NAMES, className);
  return (
    <aside className={asideClassNames} {...props}>
      <Spacing top={L} right={M} left={M}>
        <div className={ASIDE_MAIN_LINKS__CLASS_NAMES}>
          <ul>
            <li>
              <MainLink to="/">
                <Text size={MEDIUM} weight={REGULAR}>
                  Dashboard
                </Text>
              </MainLink>
            </li>
            <li>
              <MainLink to="/projects">
                <Text size={MEDIUM} weight={REGULAR}>
                  Projects
                </Text>
              </MainLink>
            </li>
          </ul>
        </div>
      </Spacing>
    </aside>
  );
};
