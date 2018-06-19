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
} from '../../components/projects/newproject/NewProjectViewDispatcher';

/**
 * The NewProjectViewStateContainer is the stateful component used to manipulate
 * the state of the NewProjectView.
 */
export class NewProjectViewStateContainer extends Component {
  constructor(props) {
    super(props);
    this.handleNameChange = this.handleNameChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);

    this.state = dispatcher(undefined, props, actionCreator.newInitializeAction());
  }

  handleNameChange(event) {
    const name = event.target.value;
    this.dispatch(actionCreator.newModifyNameAction(name));
  }

  async handleSubmit(event) {
    const { name } = this.state;

    event.preventDefault();

    try {
      const body = { name };
      const request = new Request('/api/projects', {
        method: 'POST',
        body: JSON.stringify(body)
      });
      const jsonResponse = await fetch(request);
      const { ok, statusText } = jsonResponse;
      if (ok) {
        this.dispatch(actionCreator.newCreatedProjectAction());
      } else {
        let message = statusText;
        const response = await jsonResponse.json();
        if (response) {
          message = response.message;
        }
        this.dispatch(actionCreator.newInvalidResponseAction(message));
      }
    } catch (error) {
      this.dispatch(actionCreator.newUnexpectedErrorAction(error.message));
    }
  }

  dispatch(action) {
    this.setState((prevState, props) => dispatcher(prevState, props, action));
  }

  render() {
    const { children, render = children } = this.props;
    const { stateId, errors, isValid, name, nameIsValid, nameErrors } = this.state;

    return render(
      stateId,
      errors,
      isValid,
      this.handleSubmit,
      name,
      nameIsValid,
      nameErrors,
      this.handleNameChange
    );
  }
}
