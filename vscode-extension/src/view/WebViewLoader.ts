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

import * as path from 'path';
import * as vscode from 'vscode';

export default class WebViewLoader {
  public static openedPanels: { [key: string]: WebViewLoader | undefined } = {};

  private readonly panel: vscode.WebviewPanel;

  public static createOrShow(
    extensionPath: string,
    serverAddress: string,
    username: string,
    password: string,
    editingContextId: string,
    representationId: string,
    representationLabel: string,
    representationKind: string
  ) {
    const column = vscode.window.activeTextEditor ? vscode.window.activeTextEditor.viewColumn : undefined;
    const webViewContext = {
      extensionPath,
      serverAddress,
      username,
      password,
      editingContextId,
      representationId,
      representationLabel,
      representationKind,
    };
    // If we already have a panel, show it.
    const openedPanel = WebViewLoader.openedPanels[representationId];
    if (openedPanel) {
      openedPanel.panel.reveal(column);
      return;
    }
    // Otherwise, create a new panel.
    const panel = vscode.window.createWebviewPanel('siriusweb', representationLabel, column || vscode.ViewColumn.One, {
      enableScripts: true,
      localResourceRoots: [vscode.Uri.file(path.join(extensionPath, 'siriusweb'))],
    });
    WebViewLoader.openedPanels[representationId] = new WebViewLoader(panel, webViewContext);
  }

  constructor(panel: vscode.WebviewPanel, webViewContext: WebViewContext) {
    this.panel = panel;
    this.panel.title = webViewContext.representationLabel;
    this.panel.webview.html = WebViewLoader.getWebviewContent(this.panel.webview, webViewContext);
    this.panel.onDidDispose(
      () => {
        WebViewLoader.openedPanels[webViewContext.representationId] = undefined;
      },
      null,
      []
    );
  }

  public static getWebviewContent(webView: vscode.Webview, webViewContext: WebViewContext): string {
    // Local path to main script run in the webview
    const reactAppPathOnDisk = vscode.Uri.file(path.join(webViewContext.extensionPath, 'siriusweb', 'siriusweb.js'));
    const reactAppUri = webView.asWebviewUri(reactAppPathOnDisk);
    return `<!DOCTYPE html>
      <html lang="en">
      <head>
          <meta charset="UTF-8">
          <meta name="viewport" content="width=device-width, initial-scale=1.0">
          <title>${webViewContext.representationLabel}</title>
          <script>
            window.acquireVsCodeApi = acquireVsCodeApi;
            window.serverAddress = '${webViewContext.serverAddress}';
            window.username = '${webViewContext.username}';
            window.password = '${webViewContext.password}';
            window.editingContextId = '${webViewContext.editingContextId}';
            window.representationId = '${webViewContext.representationId}';
            window.representationLabel = '${webViewContext.representationLabel}';
            window.representationKind = '${webViewContext.representationKind}';
          </script>
      </head>
      <body>
          <div id="root"></div>

          <script src="${reactAppUri}"></script>
      </body>
      </html>`;
  }
}

interface WebViewContext {
  extensionPath: string;
  serverAddress: string;
  username: string;
  password: string;
  editingContextId: string;
  representationId: string;
  representationLabel: string;
  representationKind: string;
}
