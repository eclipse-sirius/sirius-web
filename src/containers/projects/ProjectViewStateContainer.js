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

import { ProjectView } from '../../components/projects/project/ProjectView';

/**
 * The ProjectViewStateContainerWithoutRouter is the stateful component used to
 * manipulate the state of the ProjectView.
 *
 * It leverages the router API in order to retrieve the name of the project to
 * display from the URL.
 */
class ProjectViewStateContainerWithoutRouter extends Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  async componentDidMount() {
    try {
      const { projectName } = this.props.match.params;
      const jsonProjectResponse = await fetch(`/api/projects/${projectName}`);
      const projectResponse = await jsonProjectResponse.json();

      this.setState({
        project: projectResponse
      });
    } catch (error) {
      // To be handled later
    }
  }

  render() {
    const { project } = this.state;

    if (project === undefined) {
      return <p>Loading</p>;
    }
    return <ProjectView project={project} {...this.props} />;
  }
}
export const ProjectViewStateContainer = withRouter(ProjectViewStateContainerWithoutRouter);
