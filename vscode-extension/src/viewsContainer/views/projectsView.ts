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
import { ProjectData } from '../../data/ProjectData';
import { ServerData } from '../../data/ServerData';

export class ProjectsViewProvider implements TreeDataProvider<ProjectItem> {
  private serversData: ServerData[];
  private serverId: string | undefined;

  constructor(serversData: ServerData[]) {
    this.serversData = serversData;
  }

  private _onDidChangeTreeData: EventEmitter<void | ProjectItem | null | undefined> = new EventEmitter<
    void | ProjectItem | null | undefined
  >();
  readonly onDidChangeTreeData: Event<void | ProjectItem | null | undefined> = this._onDidChangeTreeData.event;

  refresh(serverId: string): void {
    this.serverId = serverId;
    this._onDidChangeTreeData.fire();
  }

  getTreeItem(element: ProjectItem): TreeItem | Thenable<TreeItem> {
    return element;
  }

  getChildren(element?: ProjectItem): ProviderResult<ProjectItem[]> {
    if (element) {
      return Promise.resolve([]);
    }
    const serverData = this.serversData.find((s) => s.id === this.serverId);
    if (serverData) {
      const projects = serverData.connectToServerAndFetchProjects();
      return projects
        .then((value) => {
          let rootItems: ProjectItem[] = [];
          value.forEach((projectData: ProjectData) => {
            const description = `${serverData.name} / ${projectData.visibility}`;
            const command = {
              command: 'siriusweb.displayProjectContents',
              title: 'Display Project Semantic Data & Representations on Click',
              arguments: [this.serverId, projectData.id],
            };
            let icon = new ThemeIcon('unlock');
            if (projectData.visibility === 'PRIVATE') {
              icon = new ThemeIcon('lock');
            }
            const projectItem = new ProjectItem(projectData.id, projectData.name, description, icon, command);
            rootItems.push(projectItem);
          });
          return Promise.resolve(rootItems);
        })
        .catch(() => {
          return Promise.reject([]);
        });
    }
    return Promise.resolve([]);
  }
}

export class ProjectItem extends TreeItem {
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
