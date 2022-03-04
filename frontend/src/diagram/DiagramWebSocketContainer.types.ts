/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo and others.
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
  sourceElement: NodeDescription;
  targetElement: NodeDescription;
  tools: Tool[];
  startPosition: Position | null;
  endPosition: Position | null;
}

export interface ToolSection {
  id: string;
  label: string;
  imageURL: string;
  tools: Tool[];
  defaultTool: Tool | null;
}

export interface Tool {
  id: string;
  label: string;
  imageURL: string;
  __typename: string;
}

export interface SingleClickOnDiagramElementTool extends Tool {
  appliesToDiagramRoot: boolean;
  selectionDescriptionId: string;
  targetDescriptions: NodeDescription[];
}

export interface SingleClickOnTwoDiagramElementsTool extends Tool {
  candidates: SingleClickOnTwoDiagramElementsCandidate[];
}

export interface SingleClickOnTwoDiagramElementsCandidate {
  sources: NodeDescription[];
  targets: NodeDescription[];
}

export interface NodeDescription {
  id: string;
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
  description: GQLRepresentationDescription;
}

export interface GQLRepresentationDescription {
  id: string;
}

export enum GQLSynchronizationPolicy {
  SYNCHRONIZED = 'SYNCHRONIZED',
  UNSYNCHRONIZED = 'UNSYNCHRONIZED',
}

export interface GQLRepresentation {
  id: string;
}

export interface GQLDiagram extends GQLRepresentation {
  id: string;
  metadata: GQLRepresentationMetadata;
  targetObjectId: string;
  autoLayout: boolean;
  size: GQLSize;
  position: GQLPosition;
  nodes: GQLNode[];
  edges: GQLEdge[];
}

export interface GQLSize {
  height: number;
  width: number;
}

export interface GQLPosition {
  x: number;
  y: number;
}

export interface GQLNode {
  id: string;
  label: GQLLabel;
  descriptionId: string;
  type: string;
  targetObjectId: string;
  targetObjectKind: string;
  targetObjectLabel: string;
  size: GQLSize;
  position: GQLPosition;
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
}

export interface GQLListNodeStyle extends GQLINodeStyle {
  borderColor: string;
  borderRadius: number;
  borderSize: number;
  borderStyle: GQLLineStyle;
  color: string;
}

export interface GQLListItemNodeStyle extends GQLINodeStyle {
  backgroundColor: string;
}

export interface GQLRectangularNodeStyle extends GQLINodeStyle {
  borderColor: string;
  borderRadius: number;
  borderSize: number;
  borderStyle: GQLLineStyle;
  color: string;
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
  style: GQLEdgeStyle;
  routingPoints: GQLPosition[];
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
}

export enum GQLDeletionPolicy {
  SEMANTIC = 'SEMANTIC',
  GRAPHICAL = 'GRAPHICAL',
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
}

export interface GQLErrorPayload
  extends GQLDiagramEventPayload,
    GQLInvokeSingleClickOnDiagramElementToolPayload,
    GQLInvokeSingleClickOnTwoDiagramElementsToolPayload {
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
