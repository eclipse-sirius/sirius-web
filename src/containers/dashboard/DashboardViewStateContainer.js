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

/**
 * The DashboardViewStateContainer is the stateful component used to manipulate
 * the state of the dashboard.
 */
export class DashboardViewStateContainer extends Component {
  constructor(props) {
    super(props);
    this.state = {
      projects: []
    };
  }

  async componentDidMount() {
    try {
      const jsonDashboardResponse = await fetch(`/api/dashboard`);
      const jsonDashboard = await jsonDashboardResponse.json();
      this.setState({
        projects: jsonDashboard.projects
      });
    } catch (error) {
      // To be handled later
    }
  }

  render() {
    const { projects } = this.state;

    return <DashboardView projects={projects} />;
  }
}
