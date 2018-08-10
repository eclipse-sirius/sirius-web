/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';

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

const SMALL_DEVICE_WIDTH = 1024;

/**
 * The App component is the entry point of the user interface of the application.
 *
 * It defines the global layout with a Narvab on top to display the name of the
 * application along with some additional actions. Under the Navbar, two columns
 * are used to display side by side the Aside component and the Main component.
 */
class AppWithoutRouter extends Component {
  constructor(props) {
    super(props);

    this.state = {
      asideHidden: document.documentElement.clientWidth < SMALL_DEVICE_WIDTH
    };

    this.handleHamburgerClick = this.handleHamburgerClick.bind(this);
  }

  handleHamburgerClick() {
    this.setState(previousState => {
      return {
        asideHidden: !previousState.asideHidden
      };
    });
  }

  render() {
    const { className } = this.props;
    const { asideHidden } = this.state;

    let appClassNames = classNames(APP__CLASS_NAMES, className);

    let aside = <Aside className={APP_ASIDE__CLASS_NAMES} />;
    if (asideHidden) {
      aside = null;
    }

    return (
      <div className={appClassNames}>
        <Navbar
          className={APP_NAVBAR__CLASS_NAMES}
          onHamburgerMenuClick={this.handleHamburgerClick}
        />
        <div className={APP_CONTAINER__CLASS_NAMES}>
          {aside}
          <Main className={APP_MAIN__CLASS_NAMES} />
        </div>
      </div>
    );
  }
}
export const App = withRouter(AppWithoutRouter);
