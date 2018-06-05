/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

import React, { Component } from 'react';
import PropTypes from 'prop-types';

import { classNames } from '../../common/classnames';

import './Loading.css';

const LOADING__CLASS_NAMES = 'loading';

/**
 * The Login component is used to display that we are waiting for some
 * asynchronous process to finish like a request to a server.
 */
export const Loading = ({ className, ...props }) => {
  const loadingClassNames = classNames(LOADING__CLASS_NAMES, className);
  return (
    <div className={loadingClassNames} {...props}>
      <div />
      <div />
      <div />
    </div>
  );
};

export class LoadingProvider extends Component {
  getChildContext() {
    return {
      LoadingProvider_LOADING: this.props.loading
    };
  }
  render() {
    return this.props.children;
  }
}
LoadingProvider.childContextTypes = {
  LoadingProvider_LOADING: PropTypes.bool.isRequired
};

export const LoadingConsumer = (props, context) => {
  const { render, children = render } = props;
  const loading = context.LoadingProvider_LOADING;
  return children(loading);
};
LoadingConsumer.contextTypes = {
  LoadingProvider_LOADING: PropTypes.bool
};
