/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/

import axios from 'axios';
import { window } from 'vscode';
import { ProjectData } from './ProjectData';

export class ServerData {
  public cookie: string;
  public projectsData: ProjectData[];

  constructor(
    public readonly id: string,
    public readonly name: string,
    public readonly serverAddress: string,
    public readonly username: string | undefined,
    public readonly password: string | undefined
  ) {
    this.cookie = '';
    this.projectsData = [];
  }

  connectToServerAndFetchProjects(): Promise<ProjectData[]> {
    if (this.username && this.password) {
      const connectionPromise = this.connectToServer(this.serverAddress, this.username, this.password);
      return connectionPromise
        .then(() => {
          return this.fetchProjects();
        })
        .catch(() => {
          return Promise.reject([]);
        });
    } else {
      return this.fetchProjects();
    }
  }

  getProjects(): ProjectData[] {
    return this.projectsData;
  }

  private fetchProjects(): Promise<ProjectData[]> {
    const graphQLQuery = `
      query getProjects($page: Int!) {
        viewer {
          id
          projects(page: $page) {
            edges {
              node {
                id
                name
              }
            }
          }
        }
      }
    `;
    const queryURL = `${this.serverAddress}/api/graphql`;
    const headers = { headers: { Cookie: this.cookie } };
    return axios
      .post(
        queryURL,
        {
          query: graphQLQuery,
          variables: { page: 0 },
        },
        headers
      )
      .then((response) => {
        if (response.status !== 200) {
          window.showErrorMessage('Error while retrieving projects');
          return Promise.reject([]);
        } else {
          this.projectsData = [];
          const projects = response.data.data.viewer.projects.edges.map((e: { node: any }) => e.node);
          projects.forEach((node: { id: string; name: string; visibility: string }) => {
            const projectData = new ProjectData(node.id, node.name, this.id);
            this.projectsData.push(projectData);
          });
          return Promise.resolve(this.projectsData);
        }
      })
      .catch((error) => {
        window.showErrorMessage('Error while retrieving projects: ' + error.message);
        return Promise.reject([]);
      });
  }

  private connectToServer(serverAddress: string, username: string, password: string): Promise<boolean> {
    const authenticateURL = `${serverAddress}/api/authenticate`;
    const params = new URLSearchParams({ username, password, 'remember-me': 'true' });
    return axios
      .post(authenticateURL, params.toString())
      .then((response) => {
        if (response.status !== 200) {
          window.showErrorMessage(`Invalid credentials`);
          return Promise.reject(false);
        } else {
          const rememberMe = response.headers['set-cookie'][0];
          const jsession = response.headers['set-cookie'][1];
          let setCookie = rememberMe.toString().substring(0, rememberMe.toString().indexOf(';'));
          this.cookie = setCookie
            .concat(';')
            .concat(jsession.toString().substring(0, jsession.toString().indexOf(';')));
          return Promise.resolve(true);
        }
      })
      .catch((error) => {
        window.showErrorMessage('Error while trying to authenticate: ' + error.message);
        return Promise.reject(false);
      });
  }
}
