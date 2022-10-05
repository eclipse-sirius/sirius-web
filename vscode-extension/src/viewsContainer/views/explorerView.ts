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

import {
  Command,
  Event,
  EventEmitter,
  ProviderResult,
  TreeDataProvider,
  TreeItem,
  TreeItemCollapsibleState
} from 'vscode';
import { ModelData } from '../../data/ModelData';
import { ProjectData } from '../../data/ProjectData';
import { ServerData } from '../../data/ServerData';

export class ExplorerViewProvider implements TreeDataProvider<ModelItem> {
  private serversData: ServerData[];
  private serverId: string | undefined;
  private projectId: string | undefined;
  private tree: ModelItem[];
  private expandedItems: string[];

  constructor(serversData: ServerData[]) {
    this.serversData = serversData;
    this.tree = [];
    this.expandedItems = [];
  }
  private _onDidChangeTreeData: EventEmitter<void | ModelItem | null | undefined> = new EventEmitter<
    void | ModelItem | null | undefined
  >();
  readonly onDidChangeTreeData: Event<void | ModelItem | null | undefined> = this._onDidChangeTreeData.event;

  refresh(serverId: string, projectId: string): void {
    if (this.serverId !== serverId || this.projectId !== projectId) {
      this.expandedItems = [];
    }
    this.serverId = serverId;
    this.projectId = projectId;
    this._onDidChangeTreeData.fire();
  }

  getTreeItem(element: ModelItem): TreeItem | Thenable<TreeItem> {
    return element;
  }

  getChildren(element?: ModelItem): ProviderResult<ModelItem[]> {
    if (element?.hasChildren) {
      const children = element.children;
      if (children === undefined || children.length === 0) {
        const serverData = this.serversData.find((s) => s.id === this.serverId);
        if (serverData) {
          const projects = serverData.getProjects();
          const projectData = projects?.find((p) => p.id == this.projectId);
          if (projectData) {
            this.expandedItems.push(element.id);
            projectData.fetchModels(serverData.serverAddress, serverData.cookie, this.expandedItems);
          }
        }
      }
      return Promise.resolve(element.children);
    }
    const serverData = this.serversData.find((s) => s.id === this.serverId);
    if (serverData) {
      const projects = serverData.getProjects();
      const projectData = projects?.find((p) => p.id == this.projectId);
      if (projectData) {
        let models = projectData.getModels();
        if (models === undefined || models.length === 0) {
          models = projectData.fetchModels(serverData.serverAddress, serverData.cookie, this.expandedItems);
        }
        this.tree = this.buildTree(models, serverData, projectData);
        return Promise.resolve(this.tree);
      }
    }
    return Promise.resolve([]);
  }

  private buildTree(elements: ModelData[], serverData: ServerData, projectData: ProjectData): ModelItem[] {
    const items: ModelItem[] = [];
    elements.forEach((element) => {
      let modelItem = this.findExistingItem(this.tree, element.id);
      if (!modelItem) {
        modelItem = this.createModelItem(element, serverData, projectData);
      } else {
        modelItem.label = element.label;
        modelItem.hasChildren = element.hasChildren;
      }
      items.push(modelItem);
      if (element.children?.length > 0) {
        const children = this.buildTree(element.children, serverData, projectData);
        // add new children
        children.forEach((child) => {
          if (modelItem?.children.indexOf(child) === -1) {
            modelItem?.children.push(child);
          }
        });
        // remove deleted ones
        const filteredChildren = modelItem?.children.filter(
          (child) => element.children.find((elt) => elt.id === child.id) !== undefined
        );
        modelItem.children = filteredChildren;
        if (modelItem.collapsibleState === TreeItemCollapsibleState.None) {
          modelItem.collapsibleState = TreeItemCollapsibleState.Collapsed;
        }
      } else if (element.hasChildren) {
        modelItem.collapsibleState = TreeItemCollapsibleState.Collapsed;
      } else if (!element.hasChildren) {
        modelItem.collapsibleState = TreeItemCollapsibleState.None;
      }
    });
    return items;
  }

  private findExistingItem(items: ModelItem[], itemId: string): ModelItem | undefined {
    let modelItem = items.find((item) => item.id === itemId);
    if (!modelItem) {
      for (let index = 0; index < items.length; index++) {
        const item = items[index];
        modelItem = this.findExistingItem(item.children, itemId);
        if (modelItem) {
          return modelItem;
        }
      }
    }
    return modelItem;
  }

  private createModelItem(element: ModelData, serverData: ServerData, projectData: ProjectData): ModelItem {
    let command = undefined;
    if (element.kind.startsWith('siriusComponents://semantic')) {
      command = {
        command: 'siriusweb.showRepresentationEditor',
        title: 'Display Representation in Editor on Click',
        arguments: [
          serverData.serverAddress,
          serverData.username,
          serverData.password,
          projectData.id,
          element.id,
          element.label,
          element.kind,
        ],
      };
    }
    return new ModelItem(element.id, element.label, '', element.hasChildren, command);
  }
}

export class ModelItem extends TreeItem {
  public children: ModelItem[];
  constructor(
    public readonly id: string,
    public label: string,
    public description: string,
    public hasChildren: boolean,
    public readonly command?: Command
  ) {
    super(label);
    this.children = [];
  }
}
