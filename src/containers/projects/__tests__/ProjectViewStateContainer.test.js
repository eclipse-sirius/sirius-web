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

import { ProjectViewStateContainer } from '../ProjectViewStateContainer';

import {
  ERROR__STATE,
  PROJECT_LOADED__STATE
} from '../../../components/projects/project/ProjectViewFiniteStateMachine';

const globalFetch = global.fetch;

/**
 * A mock of the global.fetch function used to receive a valid json response
 * containing a project.
 */
const projectFetch = () =>
  new Promise((resolve, reject) => {
    const data = { name: 'Project', pages: [] };
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

describe('ProjectViewStateContainer', () => {
  it('fetches a project', () => {
    global.fetch = projectFetch;

    const container = ReactTestUtils.renderIntoDocument(
      <MemoryRouter initialEntries={['/projects/firstProject']}>
        <ProjectViewStateContainer>{stateId => <p>{stateId}</p>}</ProjectViewStateContainer>
      </MemoryRouter>
    );

    const projectStateContainer = ReactTestUtils.findAllInRenderedTree(container, component => {
      return component && component.constructor.name === 'ProjectViewStateContainerWithoutRouter';
    })[0];

    setImmediate(() => {
      const { stateId, error, project } = projectStateContainer.state;
      expect(stateId).toBe(PROJECT_LOADED__STATE);
      expect(error).toBeNull();
      expect(project.name).toBe('Project');
      expect(project.pages.length).toBe(0);
    });

    global.fetch = globalFetch;
  });

  it('fetches an error from the server', () => {
    global.fetch = errorFetch;

    const container = ReactTestUtils.renderIntoDocument(
      <MemoryRouter initialEntries={['/projects/firstProject']}>
        <ProjectViewStateContainer>
          {(stateId, error, projects) => <p>{stateId}</p>}
        </ProjectViewStateContainer>
      </MemoryRouter>
    );

    const projectStateContainer = ReactTestUtils.findAllInRenderedTree(container, component => {
      return component && component.constructor.name === 'ProjectViewStateContainerWithoutRouter';
    })[0];

    setImmediate(() => {
      const { stateId, error, project } = projectStateContainer.state;
      expect(stateId).toBe(ERROR__STATE);
      expect(error).toBeDefined();
      expect(project).toBeNull();
    });

    global.fetch = globalFetch;
  });

  it('fetches invalid information from the server', () => {
    global.fetch = invalidFetch;

    const container = ReactTestUtils.renderIntoDocument(
      <MemoryRouter initialEntries={['/projects/firstProject']}>
        <ProjectViewStateContainer>
          {(stateId, error, projects) => <p>{stateId}</p>}
        </ProjectViewStateContainer>
      </MemoryRouter>
    );

    const projectStateContainer = ReactTestUtils.findAllInRenderedTree(container, component => {
      return component && component.constructor.name === 'ProjectViewStateContainerWithoutRouter';
    })[0];

    setImmediate(() => {
      const { stateId, error, project } = projectStateContainer.state;
      expect(stateId).toBe(ERROR__STATE);
      expect(error).toBeDefined();
      expect(project).toBeNull();
    });

    global.fetch = globalFetch;
  });
});
