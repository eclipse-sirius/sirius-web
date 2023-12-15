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
import { GQLInsideLabel, GQLOutsideLabel } from './labelFragment.types';

export interface GQLNode<T extends GQLNodeStyle> {
  id: string;
  type: string;
  targetObjectId: string;
  targetObjectKind: string;
  targetObjectLabel: string;
  descriptionId: string;
  state: GQLViewModifier;
  insideLabel: GQLInsideLabel | undefined;
  outsideLabels: GQLOutsideLabel[];
  style: T;
  childrenLayoutStrategy?: ILayoutStrategy;
  borderNodes: GQLNode<GQLNodeStyle>[] | undefined;
  childNodes: GQLNode<GQLNodeStyle>[] | undefined;
  position: GQLPosition;
  size: GQLSize;
  defaultWidth: number | null;
  defaultHeight: number | null;
  labelEditable: boolean;
}

export interface ILayoutStrategy {
  __typename: string;
  kind: string;
}

export interface ListLayoutStrategy extends ILayoutStrategy {
  kind: 'List';
}

export interface FreeFormLayoutStrategy extends ILayoutStrategy {
  kind: 'FreeForm';
}

export enum GQLViewModifier {
  Normal = 'Normal',
  Faded = 'Faded',
  Hidden = 'Hidden',
}

export interface GQLPosition {
  x: number;
  y: number;
}

export interface GQLSize {
  width: number;
  height: number;
}

export interface GQLNodeStyle {
  __typename: string;
}

export interface GQLRectangularNodeStyle extends GQLNodeStyle {
  color: string;
  borderColor: string;
  borderStyle: string;
  borderSize: string;
  borderRadius: number;
}

export interface GQLImageNodeStyle extends GQLNodeStyle {
  imageURL: string;
  positionDependentRotation: boolean;
}

export interface GQLIconLabelNodeStyle extends GQLNodeStyle {
  backgroundColor: string;
}

export interface GraphQLNodeStyleFragment {
  type: string;
  fields: string;
}
