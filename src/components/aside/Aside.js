/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

import React from 'react';
import { Link } from 'react-router-dom';

import { classNames } from '../../common/classnames';
import { Spacing } from '../spacing/Spacing';
import { M, L } from '../spacing/SpacingConstants';

import './Aside.css';

const ASIDE__CLASS_NAMES = 'aside';

const ASIDE_MAIN_LINKS__CLASS_NAMES = 'aside-mainlinks';
const MAIN_LINKS__CLASS_NAMES = 'title-s';

const ASIDE_ADDITIONAL_LINKS__CLASS_NAMES = 'aside-additionallinks';
const ADDITIONAL_LINKS__CLASS_NAMES = 'caption-s';

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
              <Link className={MAIN_LINKS__CLASS_NAMES} to="/">
                Dashboard
              </Link>
            </li>
            <li>
              <Link className={MAIN_LINKS__CLASS_NAMES} to="/projects">
                Projects
              </Link>
            </li>
          </ul>
        </div>
        <div className={ASIDE_ADDITIONAL_LINKS__CLASS_NAMES}>
          <ul>
            <li>
              <a
                className={ADDITIONAL_LINKS__CLASS_NAMES}
                href="https://github.com/eclipse/sirius-components/issues"
                target="_blank"
                rel="noopener noreferrer">
                Report an issue
              </a>
            </li>
            <li>
              <a
                className={ADDITIONAL_LINKS__CLASS_NAMES}
                href="https://stackoverflow.com/questions/tagged/sirius"
                target="_blank"
                rel="noopener noreferrer">
                Help
              </a>
            </li>
          </ul>
        </div>
      </Spacing>
    </aside>
  );
};
