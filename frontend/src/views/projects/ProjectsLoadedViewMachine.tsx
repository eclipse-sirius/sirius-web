/*******************************************************************************
 * Copyright (c) 2020 Obeo.
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
import { assign, Machine } from 'xstate';

export interface ProjectsLoadedViewStateSchema {
  states: {
    ready: {};
    menuOpened: {};
    dialogOpened: {};
  };
}

export interface ProjectsLoadedViewContext {
  menuAnchor: HTMLElement;
  modalDisplayed: string;
  project: any;
}

export type OpenMenuEvent = { type: 'OPEN_MENU'; menuAnchor: HTMLElement; project: any };
export type OpenDialogEvent = { type: 'OPEN_DIALOG'; modalDisplayed: string };
export type CloseDialogEvent = { type: 'CLOSE_DIALOG' };
export type CloseMenuEvent = { type: 'CLOSE_MENU' };
export type ProjectsLoadedViewEvent = OpenMenuEvent | OpenDialogEvent | CloseDialogEvent | CloseMenuEvent;

export const projectsLoadedViewMachine = Machine<
  ProjectsLoadedViewContext,
  ProjectsLoadedViewStateSchema,
  ProjectsLoadedViewEvent
>(
  {
    initial: 'ready',
    context: {
      modalDisplayed: '',
      menuAnchor: null,
      project: null,
    },
    states: {
      ready: {
        on: {
          OPEN_MENU: [
            {
              target: 'menuOpened',
              actions: 'openMenu',
            },
          ],
        },
      },
      menuOpened: {
        on: {
          CLOSE_MENU: [
            {
              target: 'ready',
              actions: 'closeMenu',
            },
          ],
          OPEN_DIALOG: [
            {
              target: 'dialogOpened',
              actions: 'openDialog',
            },
          ],
        },
      },
      dialogOpened: {
        on: {
          CLOSE_DIALOG: [
            {
              target: 'ready',
              actions: 'closeDialog',
            },
          ],
        },
      },
    },
  },
  {
    actions: {
      openMenu: assign((_, event) => {
        const { menuAnchor, project } = event as OpenMenuEvent;
        return { menuAnchor, project };
      }),
      closeMenu: assign((_, event) => {
        return { menuAnchor: null };
      }),
      openDialog: assign((_, event) => {
        const { modalDisplayed } = event as OpenDialogEvent;
        return { modalDisplayed, menuAnchor: null };
      }),
      closeDialog: assign((_, event) => {
        return { modalDisplayed: '' };
      }),
    },
  }
);
