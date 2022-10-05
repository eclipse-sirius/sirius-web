/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

import { Command, Event, EventEmitter, ProviderResult, ThemeIcon, TreeDataProvider, TreeItem } from 'vscode';
import { RepresentationData } from '../../data/RepresentationData';
import { ServerData } from '../../data/ServerData';

export class RepresentationsViewProvider implements TreeDataProvider<RepresentationItem> {
  private serversData: ServerData[];
  private serverId: string | undefined;
  private projectId: string | undefined;

  constructor(serversData: ServerData[]) {
    this.serversData = serversData;
  }

  private _onDidChangeTreeData: EventEmitter<void | RepresentationItem | null | undefined> = new EventEmitter<
    void | RepresentationItem | null | undefined
  >();
  readonly onDidChangeTreeData: Event<void | RepresentationItem | null | undefined> = this._onDidChangeTreeData.event;

  refresh(serverId: string, projectId: string): void {
    this.serverId = serverId;
    this.projectId = projectId;
    this._onDidChangeTreeData.fire();
  }

  getTreeItem(element: RepresentationItem): TreeItem | Thenable<TreeItem> {
    return element;
  }

  getChildren(element?: RepresentationItem): ProviderResult<RepresentationItem[]> {
    if (element) {
      return Promise.resolve([]);
    }
    const serverData = this.serversData.find((s) => s.id === this.serverId);
    if (serverData) {
      const projects = serverData.getProjects();
      const projectData = projects?.find((p) => p.id == this.projectId);
      if (projectData) {
        const representations = projectData.fetchRepresentations(serverData.serverAddress, serverData.cookie);
        return representations
          .then((value) => {
            let rootItems: RepresentationItem[] = [];
            value.forEach((representationData: RepresentationData) => {
              const description = `${serverData.name} / ${projectData.name}`;
              const command = {
                command: 'siriusweb.showRepresentationEditor',
                title: 'Display Representation in Editor on Click',
                arguments: [
                  serverData.serverAddress,
                  serverData.username,
                  serverData.password,
                  projectData.id,
                  representationData.id,
                  representationData.label,
                  representationData.kind,
                ],
              };
              const modelItem = new RepresentationItem(
                representationData.id,
                representationData.label,
                description,
                new ThemeIcon('file-text'),
                command
              );
              rootItems.push(modelItem);
            });
            return Promise.resolve(rootItems);
          })
          .catch(() => {
            return Promise.reject([]);
          });
      }
    }
    return Promise.resolve([]);
  }
}

export class RepresentationItem extends TreeItem {
  constructor(
    public readonly id: string,
    public readonly label: string,
    public readonly description: string,
    public readonly iconPath: ThemeIcon,
    public readonly command?: Command
  ) {
    super(label);
  }
}
