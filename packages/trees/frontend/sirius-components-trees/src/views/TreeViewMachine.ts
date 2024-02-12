/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
import { assign, Machine } from 'xstate';
import {
  GQLGetExpandAllTreePathData,
  GQLGetTreePathData,
  GQLTree,
  GQLTreeEventData,
  GQLTreeEventPayload,
  GQLTreeRefreshedEventPayload,
} from './TreeView.types';

export interface TreeViewStateSchema {
  states: {
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
    treeView: {
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
  treeView: 'idle' | 'ready' | 'complete';
};

export interface TreeViewContext {
  id: string;
  tree: GQLTree | null;
  expanded: string[];
  maxDepth: number;
  autoExpandToRevealSelection: boolean;
  treeItemToExpandAll: string | null;
  synchronizedWithSelection: boolean;
  message: string | null;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type HandleSubscriptionResultEvent = {
  type: 'HANDLE_SUBSCRIPTION_RESULT';
  result: SubscriptionResult<GQLTreeEventData>;
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
export type HandleOnExpandAllEvent = {
  type: 'HANDLE_ON_EXPAND_ALL';
  treeItemId: string;
};
export type HandleExpandAllTreePathEvent = {
  type: 'HANDLE_EXPAND_ALL_TREE_PATH';
  expandAllTreePathData: GQLGetExpandAllTreePathData;
};
export type HandleTreePathEvent = { type: 'HANDLE_TREE_PATH'; treePathData: GQLGetTreePathData };
export type TreeViewEvent =
  | HandleSubscriptionResultEvent
  | HandleCompleteEvent
  | ShowToastEvent
  | HideToastEvent
  | AutoExpandToRevealSelectionEvent
  | HandleExpandedEvent
  | HandleOnExpandAllEvent
  | HandleExpandAllTreePathEvent
  | HandleTreePathEvent
  | SynchronizeWithSelectionEvent;

const isTreeRefreshedEventPayload = (payload: GQLTreeEventPayload): payload is GQLTreeRefreshedEventPayload =>
  payload.__typename === 'TreeRefreshedEventPayload';

export const treeViewMachine = Machine<TreeViewContext, TreeViewStateSchema, TreeViewEvent>(
  {
    type: 'parallel',
    context: {
      id: crypto.randomUUID(),
      tree: null,
      expanded: [],
      maxDepth: 1,
      autoExpandToRevealSelection: true,
      treeItemToExpandAll: null,
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
      treeView: {
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
              HANDLE_ON_EXPAND_ALL: {
                actions: 'onExpandAll',
              },
              HANDLE_EXPAND_ALL_TREE_PATH: {
                actions: 'expandAllTreePath',
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
      onExpandAll: assign((_, event) => {
        const { treeItemId } = event as HandleOnExpandAllEvent;
        return { treeItemToExpandAll: treeItemId };
      }),
      expandAllTreePath: assign((context, event) => {
        const { expanded, maxDepth } = context;
        const { expandAllTreePathData } = event as HandleExpandAllTreePathEvent;
        if (expandAllTreePathData.viewer?.editingContext?.expandAllTreePath) {
          const { treeItemIdsToExpand, maxDepth: expandedMaxDepth } =
            expandAllTreePathData.viewer.editingContext.expandAllTreePath;
          const newExpanded: string[] = [...expanded];

          treeItemIdsToExpand?.forEach((itemToExpand) => {
            if (!expanded.includes(itemToExpand)) {
              newExpanded.push(itemToExpand);
            }
          });

          return {
            expanded: newExpanded,
            maxDepth: Math.max(expandedMaxDepth, maxDepth),
            treeItemToExpandAll: null,
          };
        }
        return { treeItemToExpandAll: null };
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
