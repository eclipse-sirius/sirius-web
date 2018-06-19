/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

import { Component } from 'react';

import {
  actionCreator,
  dispatcher
} from '../../components/projects/listprojects/ListProjectsViewDispatcher';

/**
 * The ListProjectsViewStateContainer is the stateful component used to manipulate
 * the list of the projects.
 */
export class ListProjectsViewStateContainer extends Component {
  constructor(props) {
    super(props);
    this.state = dispatcher(undefined, props, actionCreator.newInitializeAction());
  }

  async componentDidMount() {
    try {
      const jsonProjectsResponse = await fetch(`/api/projects`);
      let action;
      if (jsonProjectsResponse.ok) {
        const projectsResponse = await jsonProjectsResponse.json();
        action = actionCreator.newHandleProjectsFetchedAction(projectsResponse);
      } else {
        const { statusText, status } = jsonProjectsResponse;
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
    const { stateId, error, projects } = this.state;

    return render(stateId, error, projects);
  }
}
