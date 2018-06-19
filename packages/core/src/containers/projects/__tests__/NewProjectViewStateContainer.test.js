/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

import React from 'react';
import { MemoryRouter } from 'react-router-dom';
import ReactTestUtils from 'react-dom/test-utils';

import { NewProjectViewStateContainer } from '../NewProjectViewStateContainer';

import {
  MODIFIED__STATE,
  PRISTINE__STATE,
  REDIRECT__STATE
} from '../../../components/projects/newproject/NewProjectViewFiniteStateMachine';

const globalFetch = global.fetch;

/**
 * A mock of the global.fetch function used to receive a valid json response
 * when simulating the creation of a new project.
 */
const successfullyCreateNewProjectFetch = () =>
  new Promise((resolve, reject) => {
    const data = {};
    const response = { ok: true, json: () => Promise.resolve(data) };
    resolve(response);
  });

/**
 * A mock of the global.fetch function used to receive an invalid json response
 * when simulating an error during the creation of a new project.
 */
const failedCreateNewProjectFetch = () =>
  new Promise((resolve, reject) => {
    const data = { message: 'Error' };
    const response = { ok: false, statusText: 'Error', json: () => Promise.resolve(data) };
    reject(response);
  });

describe('NewProjectViewStateContainer', () => {
  it('initializes the new project page', () => {
    const container = ReactTestUtils.renderIntoDocument(
      <MemoryRouter initialEntries={['/newproject']}>
        <NewProjectViewStateContainer>{stateId => <p>{stateId}</p>}</NewProjectViewStateContainer>
      </MemoryRouter>
    );

    const newProjectStateContainer = ReactTestUtils.findAllInRenderedTree(container, component => {
      return component && component.constructor.name === 'NewProjectViewStateContainer';
    })[0];

    setImmediate(() => {
      const {
        stateId,
        errors,
        isValid,
        name,
        nameIsValid,
        nameErrors
      } = newProjectStateContainer.state;
      expect(stateId).toBe(PRISTINE__STATE);
      expect(errors.length).toBe(0);
      expect(isValid).toBe(false);
      expect(name).toBe('');
      expect(nameIsValid).toBe(false);
      expect(nameErrors.length).toBe(0);
    });
  });

  it('is ready to submit when the name is valid', () => {
    const container = ReactTestUtils.renderIntoDocument(
      <MemoryRouter initialEntries={['/newproject']}>
        <NewProjectViewStateContainer>{stateId => <p>{stateId}</p>}</NewProjectViewStateContainer>
      </MemoryRouter>
    );

    const newProjectStateContainer = ReactTestUtils.findAllInRenderedTree(container, component => {
      return component && component.constructor.name === 'NewProjectViewStateContainer';
    })[0];

    const event = {
      target: {
        value: 'NewName'
      }
    };
    newProjectStateContainer.handleNameChange(event);

    setImmediate(() => {
      const {
        stateId,
        errors,
        isValid,
        name,
        nameIsValid,
        nameErrors
      } = newProjectStateContainer.state;
      expect(stateId).toBe(MODIFIED__STATE);
      expect(errors.length).toBe(0);
      expect(isValid).toBe(true);
      expect(name).toBe('NewName');
      expect(nameIsValid).toBe(true);
      expect(nameErrors.length).toBe(0);
    });
  });

  it('shows an error when the name is invalid', () => {
    const container = ReactTestUtils.renderIntoDocument(
      <MemoryRouter initialEntries={['/newproject']}>
        <NewProjectViewStateContainer>{stateId => <p>{stateId}</p>}</NewProjectViewStateContainer>
      </MemoryRouter>
    );

    const newProjectStateContainer = ReactTestUtils.findAllInRenderedTree(container, component => {
      return component && component.constructor.name === 'NewProjectViewStateContainer';
    })[0];

    const event = { target: { value: '' } };
    newProjectStateContainer.handleNameChange(event);

    setImmediate(() => {
      const {
        stateId,
        errors,
        isValid,
        name,
        nameIsValid,
        nameErrors
      } = newProjectStateContainer.state;
      expect(stateId).toBe(MODIFIED__STATE);
      expect(errors.length).toBe(0);
      expect(isValid).toBe(false);
      expect(name).toBe('');
      expect(nameIsValid).toBe(false);
      expect(nameErrors.length).toBe(1);
    });
  });

  it('redirects the user interface after the creation of the project', () => {
    global.fetch = successfullyCreateNewProjectFetch;

    const container = ReactTestUtils.renderIntoDocument(
      <MemoryRouter initialEntries={['/newproject']}>
        <NewProjectViewStateContainer>{stateId => <p>{stateId}</p>}</NewProjectViewStateContainer>
      </MemoryRouter>
    );

    const newProjectStateContainer = ReactTestUtils.findAllInRenderedTree(container, component => {
      return component && component.constructor.name === 'NewProjectViewStateContainer';
    })[0];

    const nameChangedEvent = { target: { value: 'test' } };
    newProjectStateContainer.handleNameChange(nameChangedEvent);
    const submitEvent = {
      preventDefault: () => {}
    };
    newProjectStateContainer.handleSubmit(submitEvent);

    setImmediate(() => {
      const {
        stateId,
        errors,
        isValid,
        name,
        nameIsValid,
        nameErrors
      } = newProjectStateContainer.state;
      expect(stateId).toBe(REDIRECT__STATE);
      expect(errors.length).toBe(0);
      expect(isValid).toBe(true);
      expect(name).toBe('test');
      expect(nameIsValid).toBe(true);
      expect(nameErrors.length).toBe(0);
    });

    global.fetch = globalFetch;
  });

  it('shows an error if the project creation fails', () => {
    global.fetch = failedCreateNewProjectFetch;

    const container = ReactTestUtils.renderIntoDocument(
      <MemoryRouter initialEntries={['/newproject']}>
        <NewProjectViewStateContainer>{stateId => <p>{stateId}</p>}</NewProjectViewStateContainer>
      </MemoryRouter>
    );

    const newProjectStateContainer = ReactTestUtils.findAllInRenderedTree(container, component => {
      return component && component.constructor.name === 'NewProjectViewStateContainer';
    })[0];

    const nameChangedEvent = { target: { value: 'test' } };
    newProjectStateContainer.handleNameChange(nameChangedEvent);
    const submitEvent = { preventDefault: () => {} };
    newProjectStateContainer.handleSubmit(submitEvent);

    setImmediate(() => {
      const {
        stateId,
        errors,
        isValid,
        name,
        nameIsValid,
        nameErrors
      } = newProjectStateContainer.state;
      expect(stateId).toBe(MODIFIED__STATE);
      expect(errors.length).toBe(1);
      expect(isValid).toBe(false);
      expect(name).toBe('test');
      expect(nameIsValid).toBe(true);
      expect(nameErrors.length).toBe(0);
    });

    global.fetch = globalFetch;
  });
});
