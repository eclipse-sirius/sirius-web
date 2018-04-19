/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

import { Component } from 'react';
import { withRouter } from 'react-router-dom';

import { actionCreator, dispatcher } from '../../components/projects/project/ProjectViewDispatcher';

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
    this.state = dispatcher(undefined, props, actionCreator.newInitializeAction());
  }

  async componentDidMount() {
    try {
      const { projectName } = this.props.match.params;
      const jsonProjectResponse = await fetch(`/api/projects/${projectName}`);
      const projectResponse = await jsonProjectResponse.json();

      const action = actionCreator.newHandleProjectFetchedAction(projectResponse);
      this.dispatch(action);
    } catch (error) {
      // To be handled later
    }
  }

  dispatch(action) {
    this.setState((prevState, props) => dispatcher(prevState, props, action));
  }

  render() {
    const { children, render = children } = this.props;
    const { stateId, project } = this.state;

    return render(stateId, project);
  }
}
export const ProjectViewStateContainer = withRouter(ProjectViewStateContainerWithoutRouter);
