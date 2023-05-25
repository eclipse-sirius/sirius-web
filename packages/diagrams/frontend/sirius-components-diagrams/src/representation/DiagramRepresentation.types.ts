/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo and others.
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
import { GQLMessage } from '@eclipse-sirius/sirius-components-forms';
import { GQLTool } from '../palette/ContextualPalette.types';
import { Node } from '../sprotty/Diagram.types';

export type CursorValue = 'pointer' | 'copy' | 'not-allowed';

export interface DiagramDescription {
  id: string;
  autoLayout: boolean;
  debug: boolean;
}

export interface GQLDiagramEventSubscription {
  diagramEvent: GQLDiagramEventPayload;
}

export interface GQLDiagramEventPayload {
  __typename: string;
}

export interface GQLSubscribersUpdatedEventPayload extends GQLDiagramEventPayload {
  id: string;
  subscribers: GQLSubscriber[];
}

export interface GQLSubscriber {
  username: string;
}

export interface GQLDiagramRefreshedEventPayload extends GQLDiagramEventPayload {
  id: string;
  diagram: GQLDiagram;
}

export interface Subscriber {
  username: string;
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

export interface Menu {
  canvasBounds: Bounds;
  sourceElement: Node;
  targetElement: Node;
  tools: GQLTool[];
  startPosition: Position | null;
  endPosition: Position | null;
}

export interface ToolSectionWithDefaultTool {
  toolSectionId: string;
  defaultToolId: string;
}

export interface GQLViewer {
  editingContext: GQLEditingContext;
}

export interface GQLEditingContext {
  representation: GQLRepresentationMetadata;
}

export interface GQLRepresentationMetadata {
  id: string;
  label: string;
  kind: string;
}

export enum GQLSynchronizationPolicy {
  SYNCHRONIZED = 'SYNCHRONIZED',
  UNSYNCHRONIZED = 'UNSYNCHRONIZED',
}

export interface GQLRepresentation {
  id: string;
  debug: boolean;
}

export interface GQLDiagram extends GQLRepresentation {
  id: string;
  debug: boolean;
  metadata: GQLRepresentationMetadata;
  targetObjectId: string;
  size: GQLSize;
  position: GQLPosition;
  nodes: GQLNode[];
  edges: GQLEdge[];
}

export interface GQLSize {
  height: number;
  width: number;
}

export interface GQLRatio {
  x: number;
  y: number;
}

export interface GQLPosition {
  __typename: string;
  x: number;
  y: number;
}

export enum GQLViewModifier {
  Normal = 'Normal',
  Faded = 'Faded',
  Hidden = 'Hidden',
}

export interface GQLNode {
  id: string;
  insideLabel: GQLLabel;
  descriptionId: string;
  type: string;
  targetObjectId: string;
  targetObjectKind: string;
  targetObjectLabel: string;
  size: GQLSize;
  userResizable: boolean;
  position: GQLPosition;
  state: GQLViewModifier;
  style: GQLINodeStyle;
  borderNodes: GQLNode[] | undefined;
  childNodes: GQLNode[] | undefined;
}

export interface GQLLabel {
  id: string;
  text: string;
  type: string;
  style: GQLLabelStyle;
  alignment: GQLPosition;
  position: GQLPosition;
  size: GQLSize;
}

export interface GQLLabelStyle {
  bold: boolean;
  color: string;
  fontSize: number;
  iconURL: string;
  italic: boolean;
  strikeThrough: boolean;
  underline: boolean;
}

export interface GQLINodeStyle {
  __typename;
}

export interface GQLImageNodeStyle extends GQLINodeStyle {
  imageURL: string;
  borderColor: string;
  borderRadius: number;
  borderSize: number;
  borderStyle: GQLLineStyle;
}
export interface GQLParametricSVGNodeStyle extends GQLINodeStyle {
  svgURL: string;
  backgroundColor: string;
  borderColor: string;
  borderRadius: number;
  borderSize: number;
  borderStyle: GQLLineStyle;
}
export interface GQLIconLabelNodeStyle extends GQLINodeStyle {
  backgroundColor: string;
}

export interface GQLRectangularNodeStyle extends GQLINodeStyle {
  borderColor: string;
  borderRadius: number;
  borderSize: number;
  borderStyle: GQLLineStyle;
  color: string;
  withHeader: boolean;
}

export enum GQLLineStyle {
  Dash = 'Dash',
  Dash_Dot = 'Dash_Dot',
  Dot = 'Dot',
  Solid = 'Solid',
}

export interface GQLEdge {
  id: string;
  descriptionId: string;
  type: string;
  beginLabel: GQLLabel;
  centerLabel: GQLLabel;
  endLabel: GQLLabel;
  sourceId: string;
  targetId: string;
  targetObjectId: string;
  targetObjectKind: string;
  targetObjectLabel: string;
  state: GQLViewModifier;
  style: GQLEdgeStyle;
  routingPoints: GQLPosition[];
  sourceAnchorRelativePosition: GQLRatio;
  targetAnchorRelativePosition: GQLRatio;
}

export interface GQLEdgeStyle {
  color: string;
  lineStyle: GQLLineStyle;
  size: number;
  sourceArrow: GQLArrowStyle;
  targetArrow: GQLArrowStyle;
}

export enum GQLArrowStyle {
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

export interface GQLDeleteFromDiagramVariables {
  input: GQLDeleteFromDiagramInput;
}

export interface GQLDeleteFromDiagramInput {
  id: string;
  editingContextId: string;
  representationId: string;
  nodeIds: string[];
  edgeIds: string[];
  deletionPolicy: GQLDeletionPolicy;
}

export interface GQLDeleteFromDiagramData {
  deleteFromDiagram: GQLDeleteFromDiagramPayload;
}

export interface GQLDeleteFromDiagramPayload {
  __typename: string;
}

export enum GQLDeletionPolicy {
  SEMANTIC = 'SEMANTIC',
  GRAPHICAL = 'GRAPHICAL',
}

export interface GQLReconnectEdgeVariables {
  input: GQLReconnectEdgeInput;
}

export interface GQLReconnectEdgeInput {
  id: string;
  editingContextId: string;
  representationId: string;
  edgeId: string;
  newEdgeEndId: string;
  reconnectEdgeKind: GQLReconnectKind;
  newEdgeEndPosition: Position;
}

export interface GQLReconnectEdgeData {
  reconnectEdge: GQLReconnectEdgePayload;
}

export interface GQLReconnectEdgePayload {
  __typename: string;
}

export enum GQLReconnectKind {
  SOURCE = 'SOURCE',
  TARGET = 'TARGET',
}

export interface GQLInvokeSingleClickOnDiagramElementToolVariables {
  input: GQLInvokeSingleClickOnDiagramElementToolInput;
}

export interface GQLInvokeSingleClickOnDiagramElementToolInput {
  id: string;
  editingContextId: string;
  representationId: string;
  diagramElementId: string;
  toolId: string;
  startingPositionX: number;
  startingPositionY: number;
  selectedObjectId: string;
}

export interface GQLInvokeSingleClickOnDiagramElementToolData {
  invokeSingleClickOnDiagramElementTool: GQLInvokeSingleClickOnDiagramElementToolPayload;
}

export interface GQLInvokeSingleClickOnDiagramElementToolPayload {
  __typename: string;
}

export interface GQLInvokeSingleClickOnDiagramElementToolSuccessPayload
  extends GQLInvokeSingleClickOnDiagramElementToolPayload {
  id: string;
  newSelection: GQLWorkbenchSelection;
  messages: GQLMessage[];
}

export interface GQLInvokeSingleClickOnTwoDiagramElementsToolVariables {
  input: GQLInvokeSingleClickOnTwoDiagramElementsToolInput;
}

export interface GQLInvokeSingleClickOnTwoDiagramElementsToolInput {
  id: string;
  editingContextId: string;
  representationId: string;
  diagramSourceElementId: string;
  diagramTargetElementId: string;
  sourcePositionX: number;
  sourcePositionY: number;
  targetPositionX: number;
  targetPositionY: number;
  toolId: string;
}

export interface GQLInvokeSingleClickOnTwoDiagramElementsToolData {
  invokeSingleClickOnTwoDiagramElementsTool: GQLInvokeSingleClickOnTwoDiagramElementsToolPayload;
}

export interface GQLInvokeSingleClickOnTwoDiagramElementsToolPayload {
  __typename: string;
}

export interface GQLInvokeSingleClickOnTwoDiagramElementsToolSuccessPayload
  extends GQLInvokeSingleClickOnTwoDiagramElementsToolPayload {
  id: string;
  newSelection: GQLWorkbenchSelection;
  messages: GQLMessage[];
}

export interface GQLUpdateEdgeRoutingPointsVariables {
  input: GQLUpdateEdgeRoutingPointsInput;
}
export interface GQLUpdateEdgeRoutingPointsInput {
  id: string;
  representationId: string;
  editingContextId: string;
  diagramElementId: string;
  routingPoints: Position[];
}
export interface GQLUpdateEdgeRoutingPointsData {
  updateEdgeRoutingPoints: GQLUpdateEdgeRoutingPointsPayload;
}
export interface GQLUpdateEdgeRoutingPointsPayload {
  __typename: string;
}

export interface GQLErrorPayload
  extends GQLDiagramEventPayload,
    GQLInvokeSingleClickOnDiagramElementToolPayload,
    GQLInvokeSingleClickOnTwoDiagramElementsToolPayload,
    GQLUpdateEdgeRoutingPointsPayload,
    GQLReconnectEdgePayload,
    GQLDeleteFromDiagramPayload,
    GQLEditLabelPayload,
    GQLUpdateNodePositionPayload,
    GQLUpdateNodeBoundsPayload,
    GQLArrangeAllPayload {
  message: string;
}
export interface GQLWorkbenchSelection {
  entries: GQLWorkbenchSelectionEntry[];
}

export interface GQLWorkbenchSelectionEntry {
  id: string;
  label: string;
  kind: string;
}

export interface GQLEditLabelVariables {
  input: GQLEditLabelInput;
}

export interface GQLEditLabelInput {
  id: string;
  editingContextId: string;
  representationId: string;
  labelId: string;
  newText: string;
}

export interface GQLEditLabelData {
  editLabel: GQLEditLabelPayload;
}

export interface GQLEditLabelPayload {
  __typename: string;
}

export interface GQLUpdateNodePositionVariables {
  input: GQLUpdateNodePositionInput;
}

export interface GQLUpdateNodePositionInput {
  id: string;
  editingContextId: string;
  representationId: string;
  diagramElementId: string;
  newPositionX: number;
  newPositionY: number;
}

export interface GQLUpdateNodePositionData {
  updateNodePosition: GQLUpdateNodePositionPayload;
}

export interface GQLUpdateNodePositionPayload {
  __typename: string;
}

export interface GQLUpdateNodeBoundsVariables {
  input: GQLUpdateNodeBoundsInput;
}

export interface GQLUpdateNodeBoundsInput {
  id: string;
  editingContextId: string;
  representationId: string;
  diagramElementId: string;
  newHeight: number;
  newPositionX: number;
  newPositionY: number;
  newWidth: number;
}

export interface GQLUpdateNodeBoundsData {
  updateNodeBounds: GQLUpdateNodeBoundsPayload;
}

export interface GQLUpdateNodeBoundsPayload {
  __typename: string;
}

export interface GQLArrangeAllVariables {
  input: GQLArrangeAllInput;
}

export interface GQLArrangeAllInput {
  id: string;
  editingContextId: string;
  representationId: string;
}

export interface GQLArrangeAllData {
  arrangeAll: GQLArrangeAllPayload;
}

export interface GQLArrangeAllPayload {
  __typename: string;
}
