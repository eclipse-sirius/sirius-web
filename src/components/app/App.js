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

import { Aside } from '../aside/Aside';
import { Main } from '../main/Main';
import { Navbar } from '../navbar/Navbar';

import './App.css';

const APP__CLASS_NAMES = 'app';
const APP_NAVBAR__CLASS_NAMES = 'app-navbar';
const APP_CONTAINER__CLASS_NAMES = 'app-container';
const APP_ASIDE__CLASS_NAMES = 'app-aside';
const APP_MAIN__CLASS_NAMES = 'app-main';

/**
 * The App component is the entry point of the user interface of the application.
 *
 * It defines the global layout with a Narvab on top to display the name of the
 * application along with some additional actions. Under the Navbar, two columns
 * are used to display side by side the Aside component and the Main component.
 */
export const App = ({ className, ...props }) => {
  const appClassNames = classNames(APP__CLASS_NAMES, className);
  return (
    <div className={appClassNames} {...props}>
      <Navbar className={APP_NAVBAR__CLASS_NAMES} />
      <div className={APP_CONTAINER__CLASS_NAMES}>
        <Aside className={APP_ASIDE__CLASS_NAMES} />
        <Main className={APP_MAIN__CLASS_NAMES} />
      </div>
    </div>
  );
};
