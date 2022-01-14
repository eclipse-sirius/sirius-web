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

import { EditableLabel, SEdge, SGraph, SLabel, SNode, SPort, WithEditableLabel } from 'sprotty';

export class Diagram extends SGraph {
  descriptionId: string;
  label: string;
  kind: string;
  targetObjectId: string;
}

export class Node extends SNode implements WithEditableLabel {
  editableLabel?: EditableLabel & Label;
  descriptionId: string;
  kind: string;
  style: INodeStyle;
  targetObjectId: string;
  targetObjectKind: string;
  targetObjectLabel: string;
}

export interface INodeStyle {}

export class ImageNodeStyle implements INodeStyle {
  imageURL: string;
}

export class RectangularNodeStyle implements INodeStyle {
  color: string;
  borderColor: string;
  borderRadius: number;
  borderSize: number;
  borderStyle: LineStyle;
}

export enum LineStyle {
  Dash = 'Dash',
  Dash_Dot = 'Dash_Dot',
  Dot = 'Dot',
  Solid = 'Solid',
}

export class ListNodeStyle implements INodeStyle {
  color: string;
  borderColor: string;
  borderRadius: number;
  borderSize: number;
  borderStyle: LineStyle;
}

export class ListItemNodeStyle implements INodeStyle {
  backgroundColor: string;
}

export class Edge extends SEdge implements WithEditableLabel {
  editableLabel?: EditableLabel & Label;
  descriptionId: string;
  kind: string;
  style: EdgeStyle;
  targetObjectId: string;
  targetObjectKind: string;
  targetObjectLabel: string;
}

export class EdgeStyle {
  color: string;
  lineStyle: LineStyle;
  size: number;
  sourceArrow: ArrowStyle;
  targetArrow: ArrowStyle;
}

export enum ArrowStyle {
  Diamond = 'Diamond',
  FillDiamond = 'FillDiamond',
  InputArrow = 'InputArrow',
  InputArrowWithDiamond = 'InputArrowWithDiamond',
  InputArrowWithFillDiamond = 'InputArrowWithFillDiamond',
  InputClosedArrow = 'InputClosedArrow',
  InputFillClosedArrow = 'InputFillClosedArrow',
  None = 'None',
  OutputArrow = 'OutputArrow',
  OutputClosedArrow = 'OutputClosedArrow',
  OutputFillClosedArrow = 'OutputFillClosedArrow',
}

export class Label extends SLabel {
  style: LabelStyle;
}

export class LabelStyle {
  bold: boolean;
  color: string;
  fontSize: number;
  iconURL: string;
  italic: boolean;
  strikeThrough: boolean;
  underline: boolean;
}

export class Port extends SPort {}
