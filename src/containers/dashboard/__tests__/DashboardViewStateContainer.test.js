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

import { DashboardViewStateContainer } from '../DashboardViewStateContainer';

import {
  ERROR__STATE,
  DASHBOARD_LOADED__STATE
} from '../../../components/dashboard/DashboardViewFiniteStateMachine';

const globalFetch = global.fetch;

/**
 * A mock of the global.fetch function used to receive a valid json response
 * containing a dashboard without projects.
 */
const dashboardWithoutProjectsFetch = () =>
  new Promise((resolve, reject) => {
    const dashboard = { projects: [] };
    const response = { ok: true, json: () => Promise.resolve(dashboard) };
    resolve(response);
  });

/**
 * A mock of the global.fetch  function used to receive a valid json response
 * containing a dashboard with two projects.
 */
const dashboardWithProjectsFetch = () =>
  new Promise((resolve, reject) => {
    const dashboard = {
      projects: [
        {
          name: 'First Project'
        },
        {
          name: 'Second Project'
        }
      ]
    };
    const response = { ok: true, json: () => Promise.resolve(dashboard) };
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

describe('DashboardViewStateContainer', () => {
  it('fetches a dashboard without projects', () => {
    global.fetch = dashboardWithoutProjectsFetch;

    const container = ReactTestUtils.renderIntoDocument(
      <DashboardViewStateContainer>
        {(stateId, error, dashboard) => <p>{stateId}</p>}
      </DashboardViewStateContainer>
    );

    setImmediate(() => {
      const { stateId, error, dashboard } = container.state;
      expect(stateId).toBe(DASHBOARD_LOADED__STATE);
      expect(error).toBeNull();
      expect(dashboard.projects.length).toBe(0);
    });

    global.fetch = globalFetch;
  });

  it('fetches a dashboard with two projects', () => {
    global.fetch = dashboardWithProjectsFetch;

    const container = ReactTestUtils.renderIntoDocument(
      <DashboardViewStateContainer>
        {(stateId, error, dashboard) => <p>{stateId}</p>}
      </DashboardViewStateContainer>
    );

    setImmediate(() => {
      const { stateId, error, dashboard } = container.state;
      expect(stateId).toBe(DASHBOARD_LOADED__STATE);
      expect(error).toBeNull();
      expect(dashboard.projects.length).toBe(2);
      expect(dashboard.projects[0].name).toBe('First Project');
      expect(dashboard.projects[1].name).toBe('Second Project');
    });

    global.fetch = globalFetch;
  });

  it('fetches an error from the server', () => {
    global.fetch = errorFetch;

    const container = ReactTestUtils.renderIntoDocument(
      <DashboardViewStateContainer>
        {(stateId, error, dashboard) => <p>{stateId}</p>}
      </DashboardViewStateContainer>
    );

    setImmediate(() => {
      const { stateId, error, dashboard } = container.state;
      expect(stateId).toBe(ERROR__STATE);
      expect(error).toBeDefined();
      expect(dashboard.projects.length).toBe(0);
    });

    global.fetch = globalFetch;
  });

  it('fetches invalid information from the server', () => {
    global.fetch = invalidFetch;

    const container = ReactTestUtils.renderIntoDocument(
      <DashboardViewStateContainer>
        {(stateId, error, dashboard) => <p>{stateId}</p>}
      </DashboardViewStateContainer>
    );

    setImmediate(() => {
      const { stateId, error, dashboard } = container.state;
      expect(stateId).toBe(ERROR__STATE);
      expect(error).toBeDefined();
      expect(dashboard.projects.length).toBe(0);
    });

    global.fetch = globalFetch;
  });
});
