/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

import React, { Component } from 'react';

import { RENDERING_ERROR } from '../../common/errors';

import { ErrorCard } from './ErrorCard';

/**
 * The ErrorBoundary is a component used to intercept uncaught exceptions
 * during the rendering.
 */
export class ErrorBoundary extends Component {
  constructor(props) {
    super(props);
    this.state = { error: null };
  }

  componentDidCatch(error) {
    this.setState({ error });
  }

  render() {
    const { error } = this.state;
    const title = 'An unexpected error has appeared during the rendering';
    if (error) {
      return <ErrorCard code={RENDERING_ERROR} title={title} message={error.message} />;
    }
    return this.props.children;
  }
}
