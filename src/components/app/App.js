/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

import React from 'react';
import { withRouter } from 'react-router-dom';

import { classNames } from '../../common/classnames';

import { Aside } from '../aside/Aside';
import { Main } from '../main/Main';
import { Navbar } from '../navbar/Navbar';
import { Spacing } from '../spacing/Spacing';
import { M, L, XL } from '../spacing/SpacingConstants';

import './App.css';
import './ie11.css';

const APP__CLASS_NAMES = 'app';
const APP_NAVBAR__CLASS_NAMES = 'app-navbar';
const APP_CONTAINER__CLASS_NAMES = 'app-container';
const APP_ASIDE__CLASS_NAMES = 'app-aside';
const APP_MAIN__CLASS_NAMES = 'app-main';
const FULLSCREEN__CLASS_NAMES = 'fullscreen';

const FULLSCREEN_PARAMETER_NAME = 'fullscreen';
const IE11__CLASS_NAMES = 'ie11';

/**
 * The App component is the entry point of the user interface of the application.
 *
 * It defines the global layout with a Narvab on top to display the name of the
 * application along with some additional actions. Under the Navbar, two columns
 * are used to display side by side the Aside component and the Main component.
 */
const AppWithoutRouter = ({ className, ...props }) => {
  const { location } = props;

  let query = location.search;
  if (query[0] === '?') {
    query = query.substring(1);
  }
  const segments = query.split('&');
  const parameters = {};
  for (var i = 0; i < segments.length; i++) {
    const entry = segments[i].split('=');
    parameters[decodeURIComponent(entry[0])] = decodeURIComponent(entry[1]);
  }

  const isFullScreen = parameters[FULLSCREEN_PARAMETER_NAME];

  let appClassNames = classNames(APP__CLASS_NAMES, className);
  let container = (
    <div className={APP_CONTAINER__CLASS_NAMES}>
      <Aside className={APP_ASIDE__CLASS_NAMES} />
      <Main className={APP_MAIN__CLASS_NAMES} />
    </div>
  );

  if (isFullScreen) {
    appClassNames = classNames(APP__CLASS_NAMES, FULLSCREEN__CLASS_NAMES, className);
    container = (
      <div className={APP_CONTAINER__CLASS_NAMES}>
        <Main className={APP_MAIN__CLASS_NAMES} />
      </div>
    );
  }

  const isIE11 = !!window.MSInputMethodContext && !!document.documentMode;
  if (isIE11) {
    appClassNames = classNames(appClassNames, IE11__CLASS_NAMES);
  }

  return (
    <div className={appClassNames}>
      <Navbar className={APP_NAVBAR__CLASS_NAMES} />
      <Spacing top={L} right={M} bottom={XL} left={M}>
        {container}
      </Spacing>
    </div>
  );
};
export const App = withRouter(AppWithoutRouter);
