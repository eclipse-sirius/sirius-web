/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

import React, { Component } from 'react';

import { ListProjectsView } from '../../components/projects/listprojects/ListProjectsView';

/**
 * The ListProjectsViewStateContainer is the stateful component used to manipulate
 * the list of the projects.
 */
export class ListProjectsViewStateContainer extends Component {
  constructor(props) {
    super(props);
    this.state = { projects: [] };
  }

  async componentDidMount() {
    try {
      const jsonProjectsResponse = await fetch(`/api/projects`);
      const jsonProjects = await jsonProjectsResponse.json();
      this.setState({ projects: jsonProjects.projects });
    } catch (error) {
      // To be handled later
    }
  }

  render() {
    const { projects } = this.state;

    return <ListProjectsView projects={projects} />;
  }
}
