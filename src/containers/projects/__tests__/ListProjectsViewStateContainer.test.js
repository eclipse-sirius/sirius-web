/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

import React from 'react';
import ReactTestUtils from 'react-dom/test-utils';

import { ListProjectsViewStateContainer } from '../ListProjectsViewStateContainer';

import {
  ERROR__STATE,
  NO_PROJECTS_LOADED__STATE,
  PROJECTS_LOADED__STATE
} from '../../../components/projects/listprojects/ListProjectsViewFiniteStateMachine';

const globalFetch = global.fetch;

/**
 * A mock of the global.fetch function used to receive a valid json response
 * containing an empty project array.
 */
const emptyProjectsFetch = () =>
  new Promise((resolve, reject) => {
    const data = { projects: [] };
    const response = { ok: true, json: () => Promise.resolve(data) };
    resolve(response);
  });

/**
 * A mock of the global.fetch  function used to receive a valid json response
 * containing an array with two projects.
 */
const projectsFetch = () =>
  new Promise((resolve, reject) => {
    const data = {
      projects: [
        {
          name: 'First Project'
        },
        {
          name: 'Second Project'
        }
      ]
    };
    const response = { ok: true, json: () => Promise.resolve(data) };
    resolve(response);
  });

/**
 * A mock of the global.fetch function used to receive an invalid response
 * in which an error 500 has appeared.
 */
const errorFetch = () =>
  new Promise((resolve, reject) => {
    const response = { ok: false, status: 500, statusText: 'An error has occurred' };
    resolve(response);
  });

/**
 * A mock of the global.fetch function used to receive a valid response
 * with an invalid json body which creates an error during the parsing.
 */
const invalidFetch = () =>
  new Promise((resolve, reject) => {
    const response = { ok: true, json: () => Promise.reject('Cannot parse XML data as JSON') };
    resolve(response);
  });

describe('ListProjectsViewStateContainer', () => {
  it('fetches an empty project list', () => {
    global.fetch = emptyProjectsFetch;

    const container = ReactTestUtils.renderIntoDocument(
      <ListProjectsViewStateContainer>
        {(stateId, error, projects) => <p>{stateId}</p>}
      </ListProjectsViewStateContainer>
    );

    setImmediate(() => {
      const { stateId, error, projects } = container.state;
      expect(stateId).toBe(NO_PROJECTS_LOADED__STATE);
      expect(error).toBeNull();
      expect(projects.length).toBe(0);
    });

    global.fetch = globalFetch;
  });

  it('fetches a project list with two projects', () => {
    global.fetch = projectsFetch;

    const container = ReactTestUtils.renderIntoDocument(
      <ListProjectsViewStateContainer>
        {(stateId, error, projects) => <p>{stateId}</p>}
      </ListProjectsViewStateContainer>
    );

    setImmediate(() => {
      const { stateId, error, projects } = container.state;
      expect(stateId).toBe(PROJECTS_LOADED__STATE);
      expect(error).toBeNull();
      expect(projects.length).toBe(2);
      expect(projects[0].name).toBe('First Project');
      expect(projects[1].name).toBe('Second Project');
    });

    global.fetch = globalFetch;
  });

  it('fetches an error from the server', () => {
    global.fetch = errorFetch;

    const container = ReactTestUtils.renderIntoDocument(
      <ListProjectsViewStateContainer>
        {(stateId, error, projects) => <p>{stateId}</p>}
      </ListProjectsViewStateContainer>
    );

    setImmediate(() => {
      const { stateId, error, projects } = container.state;
      expect(stateId).toBe(ERROR__STATE);
      expect(error).toBeDefined();
      expect(projects.length).toBe(0);
    });

    global.fetch = globalFetch;
  });

  it('fetches invalid information from the server', () => {
    global.fetch = invalidFetch;

    const container = ReactTestUtils.renderIntoDocument(
      <ListProjectsViewStateContainer>
        {(stateId, error, projects) => <p>{stateId}</p>}
      </ListProjectsViewStateContainer>
    );

    setImmediate(() => {
      const { stateId, error, projects } = container.state;
      expect(stateId).toBe(ERROR__STATE);
      expect(error).toBeDefined();
      expect(projects.length).toBe(0);
    });

    global.fetch = globalFetch;
  });
});
