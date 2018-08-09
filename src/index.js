/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

import React from 'react';
import ReactDOM from 'react-dom';

import { BrowserRouter } from 'react-router-dom';

import { App } from './components/app/App';

import './reset.css';
import './app.css';

/**
 * The entry point of the application.
 */
ReactDOM.render(
  <BrowserRouter basename={process.env.PUBLIC_URL || ''}>
    <App />
  </BrowserRouter>,
  document.getElementById('root')
);
