/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import { Task } from '@ObeoNetwork/gantt-task-react';
import { GQLMessage } from '@eclipse-sirius/sirius-components-core/src/graphql/GQLTypes.types';

export interface GQLGanttEventSubscription {
  ganttEvent: GQLGanttEventPayload;
}

export interface GQLGanttEventPayload {
  __typename: string;
}

export interface GQLSubscribersUpdatedEventPayload extends GQLGanttEventPayload {
  id: string;
  subscribers: GQLSubscriber[];
}

export interface GQLSubscriber {
  username: string;
}

export interface GQLGanttRefreshedEventPayload extends GQLGanttEventPayload {
  id: string;
  gantt: GQLGantt;
}

export interface Subscriber {
  username: string;
}

export interface GQLErrorPayload extends GQLGanttEventPayload, GQLDeleteTaskPayload {
  messages: GQLMessage[];
}

export interface Bounds {
  x: number;
  y: number;
  width: number;
  height: number;
}

export interface Position {
  x: number;
  y: number;
}

export interface Palette {
  palettePosition: Position;
  canvasBounds: Bounds;
  edgeStartPosition: Position;
  element: any;
  renameable: boolean;
  deletable: boolean;
}

export interface GQLRepresentationMetadata {
  id: string;
  label: string;
  kind: string;
}

export interface GQLGantt {
  id: string;
  metadata: GQLRepresentationMetadata;
  targetObjectId: string;
  displayedDays: GQLDay[];
  backgroundColor: string;
  tasks: GQLTask[];
}

export interface GQLTask {
  id: string;
  descriptionId: string;
  targetObjectId: string;
  targetObjectKind: string;
  targetObjectLabel: string;
  detail: GQLTaskDetail;
  style: GQLTaskStyle;
  subTasks: GQLTask[];
  dependencies: GQLTask[];
}

export interface SelectableTask extends Task {
  targetObjectId: string;
  targetObjectKind: string;
  targetObjectLabel: string;
}

export interface GQLTaskDetail {
  name: string;
  description: string;
  startDate: number;
  endDate: number;
  progress: number;
}
export interface GQLTaskStyle {
  labelColor: string;
  backgroundColor: string;
  progressColor: string;
}

export enum GQLDay {
  MONDAY,
  TUESDAY,
  WEDNESDAY,
  THURSDAY,
  FRIDAY,
  SATURDAY,
  SUNDAY,
}

export interface GQLDeleteTaskVariables {
  input: GQLDeleteTaskInput;
}

export interface GQLDeleteTaskInput {
  id: string;
  editingContextId: string;
  taskIds: string[];
}

export interface GQLDeleteTaskPayload {
  __typename: string;
}
