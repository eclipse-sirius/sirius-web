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

    this.handleActivityClick = this.handleActivityClick.bind(this);
    this.handleTabClick = this.handleTabClick.bind(this);
    this.handleNewDescription = this.handleNewDescription.bind(this);

    this.state = dispatcher(undefined, props, actionCreator.newInitializeAction());
  }

  async componentDidMount() {
    await this.updateData();
  }

  async updateData() {
    try {
      const { projectName } = this.props.match.params;
      const jsonProjectResponse = await fetch(`/api/projects/${projectName}`);
      let action;
      if (jsonProjectResponse.ok) {
        const projectResponse = await jsonProjectResponse.json();
        action = actionCreator.newHandleProjectFetchedAction(projectResponse);
      } else {
        const { statusText, status } = jsonProjectResponse;
        action = actionCreator.newInvalidResponseAction(statusText, status);
      }
      this.dispatch(action);
    } catch (error) {
      const action = actionCreator.newUnexpectedErrorAction(error);
      this.dispatch(action);
    }
  }

  async handleTabClick(index) {
    const { project: { pages } } = this.state;
    let pageIdentifier = pages[index].identifier;
    const { projectName } = this.props.match.params;
    try {
      const jsonPageResponse = await fetch(`/api/projects/${projectName}/pages/${pageIdentifier}`);
      const pageResponse = await jsonPageResponse.json();
      const action = actionCreator.newHandlePageFetchedAction(pageResponse);
      this.dispatch(action);
    } catch (error) {
      // TO be handled
    }
  }

  async handleActivityClick(projectName, pageIdentifier, sectionIdentifier, activityIdentifier) {
    try {
      const request = new Request(
        `/api/projects/${projectName}/pages/${pageIdentifier}/sections/${sectionIdentifier}/activities/${activityIdentifier}/execute`,
        {
          method: 'POST'
        }
      );
      const jsonResponse = await fetch(request);
      const { ok } = jsonResponse;
      if (!ok) {
        // To be handled
      }
      await this.updateData();
    } catch (error) {
      // To be handled
    }
  }

  async handleNewDescription(description) {
    const { project } = this.state;
    let oldDescription = project.description;
    if (oldDescription === undefined) {
      oldDescription = '';
    }

    if (description !== oldDescription) {
      const { projectName } = this.props.match.params;
      try {
        const body = JSON.stringify({ description });
        const request = new Request(`/api/projects/${projectName}`, { method: 'PUT', body });
        const jsonResponse = await fetch(request);
        const response = await jsonResponse.json();
        const action = actionCreator.newHandleDescriptionUpdatedAction(response);
        this.dispatch(action);
      } catch (error) {
        // To be handled
      }
    }
  }

  dispatch(action) {
    this.setState((prevState, props) => dispatcher(prevState, props, action));
  }

  render() {
    const { children, render = children } = this.props;
    const { stateId, error, project, pageIdentifier } = this.state;

    return render(
      stateId,
      error,
      project,
      pageIdentifier,
      this.handleTabClick,
      this.handleActivityClick,
      this.handleNewDescription
    );
  }
}
export const ProjectViewStateContainer = withRouter(ProjectViewStateContainerWithoutRouter);
