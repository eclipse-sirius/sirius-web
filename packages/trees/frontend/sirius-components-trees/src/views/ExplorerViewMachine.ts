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
import { SubscriptionResult } from '@apollo/client';
import { v4 as uuid } from 'uuid';
import { assign, Machine } from 'xstate';
import {
  GQLExplorerEventData,
  GQLGetTreePathData,
  GQLTree,
  GQLTreeEventPayload,
  GQLTreeRefreshedEventPayload,
} from './ExplorerView.types';

export interface ExplorerViewStateSchema {
  states: {
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
    explorerView: {
      states: {
        idle: {};
        ready: {};
        complete: {};
      };
    };
  };
}

export type SchemaValue = {
  toast: 'visible' | 'hidden';
  explorerView: 'idle' | 'ready' | 'complete';
};

export interface ExplorerViewContext {
  id: string;
  tree: GQLTree | null;
  expanded: string[];
  maxDepth: number;
  autoExpandToRevealSelection: boolean;
  synchronizedWithSelection: boolean;
  message: string | null;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type HandleSubscriptionResultEvent = {
  type: 'HANDLE_SUBSCRIPTION_RESULT';
  result: SubscriptionResult<GQLExplorerEventData>;
};
export type HandleCompleteEvent = { type: 'HANDLE_COMPLETE' };
export type AutoExpandToRevealSelectionEvent = {
  type: 'AUTO_EXPAND_TO_REVEAL_SELECTION';
  autoExpandToRevealSelection: boolean;
};
export type SynchronizeWithSelectionEvent = {
  type: 'SYNCHRONIZE_WITH_SELECTION';
  synchronizedWithSelection: boolean;
};
export type HandleExpandedEvent = { type: 'HANDLE_EXPANDED'; id: string; depth: number };
export type HandleTreePathEvent = { type: 'HANDLE_TREE_PATH'; treePathData: GQLGetTreePathData };
export type ExplorerViewEvent =
  | HandleSubscriptionResultEvent
  | HandleCompleteEvent
  | ShowToastEvent
  | HideToastEvent
  | AutoExpandToRevealSelectionEvent
  | HandleExpandedEvent
  | HandleTreePathEvent
  | SynchronizeWithSelectionEvent;

const isTreeRefreshedEventPayload = (payload: GQLTreeEventPayload): payload is GQLTreeRefreshedEventPayload =>
  payload.__typename === 'TreeRefreshedEventPayload';

export const explorerViewMachine = Machine<ExplorerViewContext, ExplorerViewStateSchema, ExplorerViewEvent>(
  {
    type: 'parallel',
    context: {
      id: uuid(),
      tree: null,
      expanded: [],
      maxDepth: 1,
      autoExpandToRevealSelection: true,
      synchronizedWithSelection: true,
      message: null,
    },
    states: {
      toast: {
        initial: 'hidden',
        states: {
          hidden: {
            on: {
              SHOW_TOAST: {
                target: 'visible',
                actions: 'setMessage',
              },
            },
          },
          visible: {
            on: {
              HIDE_TOAST: {
                target: 'hidden',
                actions: 'clearMessage',
              },
            },
          },
        },
      },
      explorerView: {
        initial: 'idle',
        states: {
          idle: {
            on: {
              HANDLE_SUBSCRIPTION_RESULT: [
                {
                  cond: 'isTreeRefreshedEventPayload',
                  target: 'ready',
                  actions: 'handleSubscriptionResult',
                },
                {
                  target: 'idle',
                  actions: 'handleSubscriptionResult',
                },
              ],
            },
          },
          ready: {
            on: {
              HANDLE_SUBSCRIPTION_RESULT: {
                target: 'ready',
                actions: 'handleSubscriptionResult',
              },
              AUTO_EXPAND_TO_REVEAL_SELECTION: {
                actions: 'autoExpandToRevealSelection',
              },
              HANDLE_EXPANDED: {
                actions: 'expand',
              },
              HANDLE_TREE_PATH: {
                actions: 'handleTreePath',
              },
              HANDLE_COMPLETE: {
                target: 'complete',
              },
              SYNCHRONIZE_WITH_SELECTION: {
                actions: 'synchronizeWithSelection',
              },
            },
          },
          complete: {
            type: 'final',
          },
        },
      },
    },
  },
  {
    guards: {
      isTreeRefreshedEventPayload: (_, event) => {
        const { result } = event as HandleSubscriptionResultEvent;
        const { data } = result;
        return isTreeRefreshedEventPayload(data.treeEvent);
      },
    },
    actions: {
      handleSubscriptionResult: assign((_, event) => {
        const { result } = event as HandleSubscriptionResultEvent;
        const { data } = result;
        if (isTreeRefreshedEventPayload(data.treeEvent)) {
          const { tree } = data.treeEvent;

          return { tree };
        }
        return {};
      }),
      synchronizeWithSelection: assign((_, event) => {
        const { synchronizedWithSelection } = event as SynchronizeWithSelectionEvent;
        if (synchronizedWithSelection) {
          return { synchronizedWithSelection, autoExpandToRevealSelection: true };
        }
        return { synchronizedWithSelection, autoExpandToRevealSelection: false };
      }),
      autoExpandToRevealSelection: assign((context, event) => {
        const { autoExpandToRevealSelection } = event as AutoExpandToRevealSelectionEvent;
        const { synchronizedWithSelection } = context;
        if (synchronizedWithSelection) {
          return { autoExpandToRevealSelection };
        }
        return {};
      }),
      expand: assign((context, event) => {
        const { expanded, maxDepth } = context;
        const { id, depth } = event as HandleExpandedEvent;

        if (expanded.includes(id)) {
          const newExpanded = [...expanded];
          newExpanded.splice(newExpanded.indexOf(id), 1);

          // Disable synchronize mode on collapse
          return { expanded: newExpanded, autoExpandToRevealSelection: false, maxDepth: Math.max(maxDepth, depth) };
        }
        return { expanded: [...expanded, id], maxDepth: Math.max(maxDepth, depth) };
      }),
      handleTreePath: assign((context, event) => {
        const { expanded, maxDepth } = context;
        const { treePathData } = event as HandleTreePathEvent;
        if (treePathData.viewer?.editingContext?.treePath) {
          const { treeItemIdsToExpand, maxDepth: expandedMaxDepth } = treePathData.viewer.editingContext.treePath;
          const newExpanded: string[] = [...expanded];

          treeItemIdsToExpand?.forEach((itemToExpand) => {
            if (!expanded.includes(itemToExpand)) {
              newExpanded.push(itemToExpand);
            }
          });

          return { expanded: newExpanded, maxDepth: Math.max(expandedMaxDepth, maxDepth) };
        }
        return {};
      }),
      setMessage: assign((_, event) => {
        const { message } = event as ShowToastEvent;
        return { message };
      }),
      clearMessage: assign((_) => {
        return { message: null };
      }),
    },
  }
);
