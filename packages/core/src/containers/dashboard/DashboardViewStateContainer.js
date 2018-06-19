/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

import { Component } from 'react';

import { actionCreator, dispatcher } from '../../components/dashboard/DashboardViewDispatcher';

/**
 * The DashboardViewStateContainer is the stateful component used to manipulate
 * the state of the dashboard.
 */
export class DashboardViewStateContainer extends Component {
  constructor(props) {
    super(props);
    this.state = dispatcher(undefined, props, actionCreator.newInitializeAction());
  }

  async componentDidMount() {
    try {
      const jsonDashboardResponse = await fetch(`/api/dashboard`);
      let action;
      if (jsonDashboardResponse.ok) {
        const dashboardResponse = await jsonDashboardResponse.json();
        action = actionCreator.newHandleDashboardFetchedAction(dashboardResponse);
      } else {
        const { statusText, status } = jsonDashboardResponse;
        action = actionCreator.newInvalidResponseAction(statusText, status);
      }
      this.dispatch(action);
    } catch (error) {
      const action = actionCreator.newUnexpectedErrorAction(error);
      this.dispatch(action);
    }
  }

  dispatch(action) {
    this.setState((prevState, props) => dispatcher(prevState, props, action));
  }

  render() {
    const { children, render = children } = this.props;
    const { stateId, error, dashboard } = this.state;

    return render(stateId, error, dashboard);
  }
}
