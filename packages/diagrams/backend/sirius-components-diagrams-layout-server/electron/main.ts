/*******************************************************************************
 * Copyright (c) 2023 Obeo and others.
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
import { app, BrowserWindow } from 'electron';
import { startHttpServer } from './httpServer';

app.whenReady().then(() => {
  const browserWindow = new BrowserWindow({
    title: 'Sirius Components - Diagrams Layout Server',
    show: false,
  });

  // You can use `process.env.VITE_DEV_SERVER_URL` when the vite command is called `serve`
  if (process.env.VITE_DEV_SERVER_URL) {
    browserWindow.loadURL(process.env.VITE_DEV_SERVER_URL);
  } else {
    // Load your file
    browserWindow.loadFile('dist/index.html');
  }

  const onceFinishLoaded = () => {
    startHttpServer();
  };

  browserWindow.webContents.once('did-finish-load', onceFinishLoaded);
  browserWindow.webContents.openDevTools();
  browserWindow.maximize();
  browserWindow.show();
});
