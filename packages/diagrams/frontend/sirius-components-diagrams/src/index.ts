/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

export { DiagramContext } from './contexts/DiagramContext';
export type { DiagramContextValue } from './contexts/DiagramContext.types';
export { NodeTypeContext } from './contexts/NodeContext';
export type { NodeTypeContextValue, NodeTypeContributionElement } from './contexts/NodeContext.types';
export { convertLineStyle, isListLayoutStrategy } from './converter/convertDiagram';
export { AlignmentMap } from './converter/convertDiagram.types';
export type { IConvertEngine, INodeConverter } from './converter/ConvertEngine.types';
export { convertHandles } from './converter/convertHandles';
export { convertInsideLabel, convertLabelStyle, convertOutsideLabels } from './converter/convertLabel';
export { diagramDialogContributionExtensionPoint } from './dialog/DialogContextExtensionPoints';
export type {
  DiagramDialogComponentProps,
  DiagramDialogContribution,
} from './dialog/DialogContextExtensionPoints.types';
export { useDialog } from './dialog/useDialog';
export type { UseDialogValue } from './dialog/useDialog.types';
export type { GQLNodeDescription } from './graphql/query/nodeDescriptionFragment.types';
export type { GQLDiagram, GQLNodeLayoutData } from './graphql/subscription/diagramFragment.types';
export type { GQLEdge } from './graphql/subscription/edgeFragment.types';
export { GQLViewModifier } from './graphql/subscription/nodeFragment.types';
export type { GQLNode, GQLNodeStyle } from './graphql/subscription/nodeFragment.types';
export { useConnectorNodeStyle } from './renderer/connector/useConnectorNodeStyle';
export { BorderNodePosition as BorderNodePosition } from './renderer/DiagramRenderer.types';
export type { Diagram, EdgeData, NodeData, ReactFlowPropsCustomizer } from './renderer/DiagramRenderer.types';
export { diagramRendererReactFlowPropsCustomizerExtensionPoint } from './renderer/DiagramRendererExtensionPoints';
export { useDrop } from './renderer/drop/useDrop';
export { useDropNode } from './renderer/dropNode/useDropNode';
export { useDropNodeStyle } from './renderer/dropNode/useDropNodeStyle';
export { ConnectionCreationHandles } from './renderer/handles/ConnectionCreationHandles';
export { ConnectionHandles } from './renderer/handles/ConnectionHandles';
export type { ConnectionHandle } from './renderer/handles/ConnectionHandles.types';
export { ConnectionTargetHandle } from './renderer/handles/ConnectionTargetHandle';
export { useRefreshConnectionHandles } from './renderer/handles/useRefreshConnectionHandles';
export { Label } from './renderer/Label';
export { computePreviousPosition, computePreviousSize } from './renderer/layout/bounds';
export type { ForcedDimensions } from './renderer/layout/layout.types';
export * from './renderer/layout/layoutBorderNodes';
export type { ILayoutEngine, INodeLayoutHandler } from './renderer/layout/LayoutEngine.types';
export * from './renderer/layout/layoutNode';
export { defaultHeight, defaultWidth } from './renderer/layout/layoutParams';
export { useLayout } from './renderer/layout/useLayout';
export { NodeContext } from './renderer/node/NodeContext';
export type { NodeContextValue } from './renderer/node/NodeContext.types';
export { NodeTypeContribution } from './renderer/node/NodeTypeContribution';
export type { DiagramNodeType } from './renderer/node/NodeTypes.types';
export { DiagramElementPalette } from './renderer/palette/DiagramElementPalette';
export type {
  DiagramPaletteToolContributionComponentProps,
  DiagramPaletteToolContributionProps,
} from './renderer/palette/DiagramPaletteToolContribution.types';
export type { DiagramPaletteToolComponentProps } from './renderer/palette/tool/DiagramPaletteTool.types';
export { diagramPaletteToolExtensionPoint } from './renderer/palette/tool/DiagramPaletteToolExtensionPoints';
export type { GQLToolVariable, GQLToolVariableType } from './renderer/palette/usePalette.types';
export type { DiagramPanelActionProps } from './renderer/panel/DiagramPanel.types';
export { diagramPanelActionExtensionPoint } from './renderer/panel/DiagramPanelExtensionPoints';
export { DiagramRepresentation } from './representation/DiagramRepresentation';
export type { GQLDiagramDescription } from './representation/DiagramRepresentation.types';
