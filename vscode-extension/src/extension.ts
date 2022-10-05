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

import * as vscode from 'vscode';
import { ServerData } from './data/ServerData';
import WebViewLoader from './view/WebViewLoader';
import { ExplorerViewProvider } from './viewsContainer/views/explorerView';
import { ProjectsViewProvider } from './viewsContainer/views/projectsView';
import { RepresentationsViewProvider } from './viewsContainer/views/representationsView';
import { ServersViewProvider } from './viewsContainer/views/serversView';

export function activate(context: vscode.ExtensionContext) {
  const serverData: ServerData[] = [];
  const serversViewProvider = new ServersViewProvider(serverData);
  const projectsViewProvider = new ProjectsViewProvider(serverData);
  const explorerViewProvider = new ExplorerViewProvider(serverData);
  const representationsViewProvider = new RepresentationsViewProvider(serverData);

  vscode.window.registerTreeDataProvider('siriusweb.serversView', serversViewProvider);
  vscode.window.registerTreeDataProvider('siriusweb.projectsView', projectsViewProvider);
  vscode.window.registerTreeDataProvider('siriusweb.explorerView', explorerViewProvider);
  vscode.window.registerTreeDataProvider('siriusweb.representationsView', representationsViewProvider);

  vscode.commands.registerCommand('siriusweb.registerServer', () => serversViewProvider.showRegisterServerInputBox());
  vscode.commands.registerCommand('siriusweb.displayProjects', (serverId) => {
    projectsViewProvider.refresh(serverId);
    explorerViewProvider.refresh(serverId, '');
    representationsViewProvider.refresh(serverId, '');
  });
  vscode.commands.registerCommand('siriusweb.displayProjectContents', (serverId, projectId) => {
    explorerViewProvider.refresh(serverId, projectId);
    representationsViewProvider.refresh(serverId, projectId);
  });

  let disposable = vscode.commands.registerCommand(
    'siriusweb.showRepresentationEditor',
    (
      serverAddress,
      username,
      password,
      editingContextId,
      representationId,
      representationLabel,
      representationKind
    ) => {
      WebViewLoader.createOrShow(
        context.extensionPath,
        serverAddress,
        username,
        password,
        editingContextId,
        representationId,
        representationLabel,
        representationKind
      );
    }
  );
  context.subscriptions.push(disposable);
}

export function deactivate() {}
