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
import { GQLLabel } from './labelFragment.types';

export interface GQLNode {
  id: string;
  type: string;
  targetObjectId: string;
  targetObjectKind: string;
  targetObjectLabel: string;
  descriptionId: string;
  state: GQLViewModifier;
  label: GQLLabel;
  style: GQLNodeStyle;
  borderNodes: GQLNode[] | undefined;
  childNodes: GQLNode[] | undefined;
  position: GQLPosition;
  size: GQLSize;
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
  withHeader: boolean;
}

export interface GQLImageNodeStyle extends GQLNodeStyle {
  imageURL: string;
}

export interface GQLIconLabelNodeStyle extends GQLNodeStyle {}
