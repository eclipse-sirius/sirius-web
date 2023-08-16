/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
import { Component } from 'react';
import { ErrorBoundaryProps, ErrorBoundaryState } from './ErrorBoundary.types';

/**
 * HOC to wrap a component to catch exceptions and react accordingly.
 * See https://reactjs.org/blog/2017/07/26/error-handling-in-react-16.html#introducing-error-boundaries
 *
 * @author sbegaudeau
 * @author pcdavid
 */
export const withErrorBoundary = (Child: React.FunctionComponent<ErrorBoundaryProps>) => {
  return class ErrorBoundary extends Component<ErrorBoundaryProps, ErrorBoundaryState> {
    constructor(props: ErrorBoundaryState) {
      super(props);
      this.state = {
        error: null,
        children: this.props.children,
      };
    }

    /**
     * This is called by React when one of our children component has raised an error.
     * We simply take note of it in the state for the next render() call to decide
     * what to do.
     *
     * @param {*} error the error that was raised.
     */
    static getDerivedStateFromError(error: Error): ErrorBoundaryState {
      console.error('ErrorBoundary caught error', error);
      if (error) {
        return { error };
      }
    }

    /**
     * Some magic spell to make this actually work. Ask @sbegaudeau.
     *
     * @param {*} props
     * @param {*} state
     */
    static getDerivedStateFromProps(props: ErrorBoundaryProps, state: ErrorBoundaryState) {
      if (state.children && state.children !== props.children) {
        return { error: null, children: props.children };
      }
      return state;
    }

    render() {
      if (this.state.error) {
        return <p>An error has occured, please contact your administrator or refresh the page...</p>;
      }
      return <Child />;
    }
  };
};
