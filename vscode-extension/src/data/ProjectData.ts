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
import { v4 as uuid } from 'uuid';
import { commands, window } from 'vscode';
import { w3cwebsocket as W3CWebSocket } from 'websocket';
import { ModelData } from './ModelData';
import { GQLGetRepresentationMetadataResponse } from './ProjectData.types';
import { RepresentationData } from './RepresentationData';
import { getTreeEventSubscription } from './getTreeEventSubscription';

export class ProjectData {
  private modelsData: ModelData[];
  private representationsData: RepresentationData[];
  private subscriptionTreeEventId: string | undefined;

  constructor(public readonly id: string, public readonly name: string, public readonly serverId: string) {
    this.modelsData = [];
    this.representationsData = [];
  }

  getModels(): ModelData[] {
    return this.modelsData;
  }

  getRepresentations(): RepresentationData[] {
    return this.representationsData;
  }

  fetchModels(serverAddress: string, cookie: string, expandedItems: string[]): ModelData[] {
    const graphQLSubscription = getTreeEventSubscription(8);
    const headers = {
      Cookie: cookie,
    };

    const protocol = serverAddress.startsWith('https') ? 'wss' : 'ws';
    const address = serverAddress.substring(serverAddress.indexOf('://'), serverAddress.length);
    const subscriptionURL = `${protocol}${address}/subscriptions`;

    const client = new W3CWebSocket(subscriptionURL, 'graphql-ws', '', headers);
    client.onerror = (error) => {
      window.showErrorMessage('Error while retrieving model contents: ' + error.message);
    };
    client.onclose = () => {};
    client.onopen = () => {
      if (this.subscriptionTreeEventId) {
        // stop the previous subscription
        const stopMessage = { id: this.subscriptionTreeEventId, type: 'stop' };
        const stopMessageJSON = JSON.stringify(stopMessage);
        client.send(stopMessageJSON);
      }
      const payload = {
        query: graphQLSubscription,
        variables: { input: { id: uuid(), treeId: 'explorer://', editingContextId: this.id, expanded: expandedItems } },
      };
      const startMessage = { id: this.genSubscriptionTreeEventId(), type: 'start', payload };
      const startMessageJSON = JSON.stringify(startMessage);
      client.send(startMessageJSON);
    };
    client.onmessage = (message) => {
      if (message?.data) {
        const response = JSON.parse(message.data as string);
        const documents = response.payload?.data?.treeEvent?.tree?.children;
        if (response.id === this.subscriptionTreeEventId && documents) {
          this.modelsData = this.buildModelData(documents);
          commands.executeCommand('siriusweb.displayProjectContents', this.serverId, this.id);
        }
      }
    };
    return this.modelsData;
  }

  private genSubscriptionTreeEventId() {
    this.subscriptionTreeEventId = uuid();
    return this.subscriptionTreeEventId;
  }

  private isHandledRepresentation(element: { kind: string }): boolean {
    return (
      element.kind.startsWith('siriusComponents://representation?type=Diagram') ||
      element.kind.startsWith('siriusComponents://representation?type=Form')
    );
  }

  private buildModelData(elements: []): ModelData[] {
    const modelsData: ModelData[] = [];
    elements.forEach(
      (element: { id: string; label: string; kind: string; imageURL: string; hasChildren: boolean; children: [] }) => {
        if (!this.isHandledRepresentation(element)) {
          let elementLabel = element.label;
          if (elementLabel === undefined || elementLabel.length === 0) {
            elementLabel = String(element.kind.split('::').pop());
          }
          const modelData = new ModelData(
            element.id,
            elementLabel,
            element.kind,
            element.imageURL,
            this.serverId,
            this.id,
            element.hasChildren
          );
          modelsData.push(modelData);
          if (element.children?.length > 0) {
            const children = this.buildModelData(element.children);
            children.forEach((child) => modelData.children.push(child));
          }
        }
      }
    );
    return modelsData;
  }

  fetchRepresentations(address: string, cookie: string): Promise<RepresentationData[]> {
    const graphQLQuery = `
      query getRepresentations($editingContextId: ID!) {
        viewer {
          id
          editingContext(editingContextId: $editingContextId) {
            id
            representations {
              edges {
                node {
                  id
                  kind
                  label
                }
              }
            }
          }
        }
      }
    `;
    const queryURL = `${address}/api/graphql`;
    const headers = { headers: { Cookie: cookie } };
    return axios
      .post<GQLGetRepresentationMetadataResponse>(
        queryURL,
        {
          query: graphQLQuery,
          variables: { editingContextId: this.id },
        },
        headers
      )
      .then((response) => {
        if (response.status !== 200) {
          window.showErrorMessage('Error while retrieving representations');
          return Promise.reject([]);
        } else {
          this.representationsData = [];
          const representations = response.data.data.viewer.editingContext?.representations?.edges
            .map((e) => e.node)
            .filter(this.isHandledRepresentation);
          representations.forEach((diagram) => {
            const representationData = new RepresentationData(diagram.id, diagram.label, diagram.kind, this.id);
            this.representationsData.push(representationData);
          });
          return Promise.resolve(this.representationsData);
        }
      })
      .catch((error) => {
        window.showErrorMessage('Error while retrieving representations: ' + error.message);
        return Promise.reject([]);
      });
  }
}
