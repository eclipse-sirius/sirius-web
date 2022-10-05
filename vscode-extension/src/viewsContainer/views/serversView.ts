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

import { v4 as uuid } from 'uuid';
import { Command, Event, EventEmitter, ProviderResult, ThemeIcon, TreeDataProvider, TreeItem, window } from 'vscode';
import { ServerData } from '../../data/ServerData';

export class ServersViewProvider implements TreeDataProvider<ServerItem> {
  private registeredServersItems: ServerItem[];
  private serversData: ServerData[];

  constructor(serversData: ServerData[]) {
    this.registeredServersItems = [];
    this.serversData = serversData;
  }

  private _onDidChangeTreeData: EventEmitter<void | ServerItem | null | undefined> = new EventEmitter<
    void | ServerItem | null | undefined
  >();
  readonly onDidChangeTreeData: Event<void | ServerItem | null | undefined> = this._onDidChangeTreeData.event;

  refresh(): void {
    this._onDidChangeTreeData.fire();
  }

  getTreeItem(element: ServerItem): TreeItem | Thenable<TreeItem> {
    return element;
  }
  getChildren(element?: ServerItem): ProviderResult<ServerItem[]> {
    if (element) {
      return Promise.resolve([]);
    }
    let rootItems: ServerItem[] = [];
    const connectItem = new RegisterNewServerItem({
      command: 'siriusweb.registerServer',
      title: '',
      arguments: [],
    });
    rootItems.push(connectItem);
    rootItems = rootItems.concat(this.registeredServersItems);
    return Promise.resolve(rootItems);
  }

  public async showRegisterServerInputBox() {
    const serverAddressResult = await window.showInputBox({
      value: 'http://127.0.0.1:8080',
      valueSelection: [0, 21],
      placeHolder: 'For example: http://127.0.0.1:8080',
      prompt: 'Please enter the server URL',
      validateInput: (text) => {
        if (this.registeredServersItems.map((server) => server.description).includes(text)) {
          return 'A server with this URL has already been registered';
        } else if (text === undefined || text === '') {
          return 'The server URL cannot be empty';
        }
        return null;
      },
    });
    let serverNameResult;
    if (serverAddressResult) {
      serverNameResult = await window.showInputBox({
        placeHolder: 'For example: My server',
        prompt: 'Please enter the server name',
        validateInput: (text) => {
          if (this.registeredServersItems.map((server) => server.label).includes(text)) {
            return 'A server with this name has already been registered';
          } else if (text === undefined || text === '') {
            return 'The server name cannot be empty';
          }
          return null;
        },
      });
    }
    let usernameResult;
    if (serverNameResult) {
      usernameResult = await window.showInputBox({
        prompt: 'Please enter your username (or type Enter if there are no credentials)',
      });
    }
    let passwordResult;
    if (serverNameResult) {
      passwordResult = await window.showInputBox({
        prompt: 'Please enter your password (or type Enter if there are no credentials)',
        password: true,
      });
    }
    if (serverAddressResult && serverNameResult) {
      const newServerId = uuid();
      const command = {
        command: 'siriusweb.displayProjects',
        title: 'Display Server Projects on Click',
        arguments: [newServerId],
      };
      const newServer = new ServerItem(
        newServerId,
        serverNameResult,
        serverAddressResult,
        usernameResult,
        passwordResult,
        new ThemeIcon('run'),
        command
      );
      this.registeredServersItems.push(newServer);
      this.serversData.push(
        new ServerData(newServerId, serverNameResult, serverAddressResult, usernameResult, passwordResult)
      );
      window.showInformationMessage(`Server has been successfully registered`);
      this.refresh();
    }
  }
}

export class ServerItem extends TreeItem {
  constructor(
    public readonly id: string,
    public readonly label: string,
    public readonly description: string,
    public readonly username: string | undefined,
    public readonly password: string | undefined,
    public readonly iconPath: ThemeIcon,
    public readonly command?: Command
  ) {
    super(label);
  }
}

export class RegisterNewServerItem extends ServerItem {
  constructor(public readonly command: Command) {
    super('RegisterNewServerId', 'Register New Server', '', '', '', new ThemeIcon('add'), command);
  }
}
