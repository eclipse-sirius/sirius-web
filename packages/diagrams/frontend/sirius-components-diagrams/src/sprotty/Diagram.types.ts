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

import { EditableLabel, SEdge, SGraph, SLabel, SNode, SPort, WithEditableLabel } from 'sprotty';

export class Diagram extends SGraph {
  descriptionId: string;
  label: string;
  kind: string;
  targetObjectId: string;
  debug: boolean;
}

export class Node extends SNode implements WithEditableLabel {
  editableLabel?: EditableLabel & Label;
  descriptionId: string;
  kind: string;
  style: INodeStyle;
  targetObjectId: string;
  targetObjectKind: string;
  targetObjectLabel: string;
  state: ViewModifier;
}

export class BorderNode extends SPort implements WithEditableLabel {
  editableLabel?: EditableLabel & Label;
  descriptionId: string;
  kind: string;
  style: INodeStyle;
  targetObjectId: string;
  targetObjectKind: string;
  targetObjectLabel: string;
  state: ViewModifier;
}

export interface INodeStyle {
  opacity: number;
}

export class ImageNodeStyle implements INodeStyle {
  imageURL: string;
  borderColor: string;
  borderRadius: number;
  borderSize: number;
  borderStyle: LineStyle;
  opacity: number;
}

export class ParametricSVGNodeStyle implements INodeStyle {
  svgURL: string;
  borderColor: string;
  borderRadius: number;
  borderSize: number;
  borderStyle: LineStyle;
  backgroundColor: string;
  opacity: number;
}

export class RectangularNodeStyle implements INodeStyle {
  color: string;
  borderColor: string;
  borderRadius: number;
  borderSize: number;
  borderStyle: LineStyle;
  withHeader: boolean;
  opacity: number;
}

export enum LineStyle {
  Dash = 'Dash',
  Dash_Dot = 'Dash_Dot',
  Dot = 'Dot',
  Solid = 'Solid',
}

export class IconLabelNodeStyle implements INodeStyle {
  backgroundColor: string;
  opacity: number;
}

export interface Ratio {
  x: number;
  y: number;
}

export class Edge extends SEdge implements WithEditableLabel {
  editableLabel?: EditableLabel & Label;
  descriptionId: string;
  kind: string;
  style: EdgeStyle;
  targetObjectId: string;
  targetObjectKind: string;
  targetObjectLabel: string;
  sourceAnchorRelativePosition: Ratio;
  targetAnchorRelativePosition: Ratio;
  state: ViewModifier;
}

export class EdgeStyle {
  color: string;
  lineStyle: LineStyle;
  size: number;
  sourceArrow: ArrowStyle;
  targetArrow: ArrowStyle;
  opacity: number;
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
  Circle = 'Circle',
  FillCircle = 'FillCircle',
  CrossedCircle = 'CrossedCircle',
  ClosedArrowWithVerticalBar = 'ClosedArrowWithVerticalBar',
  ClosedArrowWithDots = 'ClosedArrowWithDots',
}

/**
 * Extends Sprotty's SLabel to add support for having the initial text when entering
 * in direct edit mode different from the text's label itself, and makes the
 * pre-selection of the edited text optional.
 */
export class Label extends SLabel {
  isMultiLine: boolean = true;
  style: LabelStyle;
  initialText: string;
  preSelect: boolean = true;
}

export class LabelStyle {
  bold: boolean;
  color: string;
  fontSize: number;
  iconURL: string[];
  italic: boolean;
  strikeThrough: boolean;
  underline: boolean;
  opacity: number;
}

export enum ViewModifier {
  Normal = 'Normal',
  Faded = 'Faded',
  Hidden = 'Hidden',
}
