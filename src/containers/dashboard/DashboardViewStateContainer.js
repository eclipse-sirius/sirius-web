/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

import React, { Component } from 'react';

import { DashboardView } from '../../components/dashboard/DashboardView';

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
      const dashboardResponse = await jsonDashboardResponse.json();
      const action = actionCreator.newHandleDashboardFetchedAction(dashboardResponse);
      this.dispatch(action);
    } catch (error) {
      // To be handled later
    }
  }

  dispatch(action) {
    this.setState((prevState, props) => dispatcher(prevState, props, action));
  }

  render() {
    const { dashboard } = this.state;

    return <DashboardView dashboard={dashboard} />;
  }
}
