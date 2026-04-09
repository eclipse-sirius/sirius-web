/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import {
  GQLDiagram,
  GQLDiagramDescription,
  GQLEdge,
  GQLEdgeLayoutData,
  GQLEdgeStyle,
  GQLInsideLabel,
  GQLLabelStyle,
  GQLNode,
  GQLNodeDescription,
  GQLNodeLayoutData,
  GQLNodeStyle,
  GQLPalette,
  GQLRectangularNodeStyle,
  GQLRepresentationMetadata,
  GQLViewModifier,
} from "@eclipse-sirius/sirius-components-diagrams";

export const createDefaultPalette = (): GQLPalette => ({
  id: "default-palette",
  paletteEntries: [],
  quickAccessTools: [],
});

export const createDefaultDescription = (): GQLDiagramDescription => {
  const childNodeDesc: GQLNodeDescription = {
    id: "child-node-desc",
    userResizable: "BOTH",
    keepAspectRatio: false,
    childNodeDescriptionIds: [],
    borderNodeDescriptionIds: [],
  };

  const nodeDesc: GQLNodeDescription = {
    id: "node-desc",
    userResizable: "BOTH",
    keepAspectRatio: false,
    childNodeDescriptionIds: ["child-node-desc"],
    borderNodeDescriptionIds: [],
  };

  const runtimeDescription: GQLDiagramDescription = {
    id: "desc",
    debug: false,
    dropNodeCompatibility: [],
    arrangeLayoutDirection: "DOWN",
    nodeDescriptions: [nodeDesc, childNodeDesc],
    toolbar: {
      expandedByDefault: false,
    },
    autoLayout: false,
  };

  return runtimeDescription;
};

export const buildDiagram = (label: string): GQLDiagram => {
  const id = "diagram-1";
  const metadata: GQLRepresentationMetadata = {
    kind: "siriusComponents://representation?type=Diagram",
    label: label,
  };

  return {
    id,
    targetObjectId: "root",
    metadata,
    nodes: [],
    edges: [],
    layoutData: {
      nodeLayoutData: [],
      edgeLayoutData: [],
      labelLayoutData: [],
    },
    style: { background: "" },
  };
};

interface AddEdgeParams {
  id: string;
  sourceId: string;
  targetId: string;
  label?: string;
}

export const addEdge = (
  diagram: GQLDiagram,
  params: AddEdgeParams,
): GQLDiagram => {
  const edgeStyle: GQLEdgeStyle = {
    lineStyle: "SOLID",
    color: "black",
    size: 1,
    sourceArrow: "None",
    targetArrow: "INPUT_FILL_CLOSED_ARROW",
    edgeType: "",
  };

  const newEdge: GQLEdge = {
    id: params.id,
    targetObjectId: `target-${params.id}`,
    targetObjectKind: "Edge",
    targetObjectLabel: params.label || "",
    descriptionId: "edge-desc",
    type: "",
    sourceId: params.sourceId,
    targetId: params.targetId,
    state: "",
    beginLabel: null,
    centerLabel: null,
    endLabel: null,
    style: edgeStyle,
    routingPoints: [],
    centerLabelEditable: false,
    deletable: false,
    customizedStyleProperties: [],
  };

  const newEdgeLayout: GQLEdgeLayoutData = {
    id: params.id,
    bendingPoints: [],
    edgeAnchorLayoutData: [],
  };

  return {
    ...diagram,
    edges: [...diagram.edges, newEdge],
    layoutData: {
      ...diagram.layoutData,
      edgeLayoutData: [...diagram.layoutData.edgeLayoutData, newEdgeLayout],
    },
  };
};

interface AddNodeParams {
  id: string;
  label: string;
  x: number;
  y: number;
  width: number;
  height: number;
}

export const addNode = (
  diagram: GQLDiagram,
  params: AddNodeParams,
): GQLDiagram => {
  const labelStyle: GQLLabelStyle = {
    fontSize: 12,
    bold: false,
    italic: false,
    underline: false,
    strikeThrough: false,
    color: "black",
    background: "transparent",
    borderColor: "transparent",
    borderSize: 0,
    borderRadius: 0,
    borderStyle: "SOLID",
    iconURL: [],
    maxWidth: "",
    visibility: "visible",
  };

  const insideLabel: GQLInsideLabel = {
    id: `label-${params.id}`,
    text: params.label,
    insideLabelLocation: "TOP_CENTER",
    headerSeparatorDisplayMode: "IF_CHILDREN",
    overflowStrategy: "ELLIPSIS",
    textAlign: "CENTER",
    customizedStyleProperties: [],
    style: labelStyle,
    isHeader: true,
  };

  const nodeStyle: GQLRectangularNodeStyle = {
    __typename: "RectangularNodeStyle",
    background: "white",
    borderColor: "orange",
    borderStyle: "SOLID",
    borderSize: 2,
    borderRadius: 0,
    childrenLayoutStrategy: {
      __typename: "FreeFormLayoutStrategy",
      kind: "FreeForm",
    },
  };

  const newNode: GQLNode<GQLRectangularNodeStyle> = {
    id: params.id,
    type: "rectangle",
    targetObjectId: `target-${params.id}`,
    targetObjectKind: "Node",
    targetObjectLabel: params.label,
    descriptionId: "node-desc",
    pinned: false,
    position: { x: params.x, y: params.y },
    size: { width: params.width, height: params.height },
    defaultWidth: params.width,
    defaultHeight: params.height,
    labelEditable: true,
    deletable: true,
    customizedStyleProperties: [],
    initialBorderNodePosition: "WEST",
    insideLabel: insideLabel,
    outsideLabels: [],
    borderNodes: [],
    childNodes: [],
    style: nodeStyle,
    state: GQLViewModifier.Normal,
  };

  const newLayoutData: GQLNodeLayoutData = {
    id: params.id,
    position: { x: params.x, y: params.y },
    size: { width: params.width, height: params.height },
    minComputedSize: { width: params.width, height: params.height },
    resizedByUser: false,
    movedByUser: false,
    handleLayoutData: [],
  };

  return {
    ...diagram,
    nodes: [...diagram.nodes, newNode],
    layoutData: {
      ...diagram.layoutData,
      nodeLayoutData: [...diagram.layoutData.nodeLayoutData, newLayoutData],
    },
  };
};

export interface AddChildNodeParams {
  parentId: string;
  id: string;
  label: string;
  x: number;
  y: number;
  width: number;
  height: number;
}

export const addChildNode = (
  diagram: GQLDiagram,
  params: AddChildNodeParams,
): GQLDiagram => {
  const labelStyle: GQLLabelStyle = {
    fontSize: 12,
    bold: false,
    italic: false,
    underline: false,
    strikeThrough: false,
    color: "black",
    background: "transparent",
    borderColor: "transparent",
    borderSize: 0,
    borderRadius: 0,
    borderStyle: "SOLID",
    iconURL: [],
    maxWidth: "",
    visibility: "visible",
  };

  const insideLabel: GQLInsideLabel = {
    id: `label-${params.id}`,
    text: params.label,
    insideLabelLocation: "TOP_CENTER",
    headerSeparatorDisplayMode: "NEVER",
    overflowStrategy: "ELLIPSIS",
    textAlign: "CENTER",
    customizedStyleProperties: [],
    style: labelStyle,
    isHeader: false,
  };

  const nodeStyle: GQLRectangularNodeStyle = {
    __typename: "RectangularNodeStyle",
    background: "transparent",
    borderColor: "orange",
    borderStyle: "SOLID",
    borderSize: 1,
    borderRadius: 0,
    childrenLayoutStrategy: {
      __typename: "FreeFormLayoutStrategy",
      kind: "FreeForm",
    },
  };

  const newChildNode: GQLNode<GQLRectangularNodeStyle> = {
    id: params.id,
    type: "rectangle",
    targetObjectId: `target-${params.id}`,
    targetObjectKind: "Node",
    targetObjectLabel: params.label,
    descriptionId: "child-node-desc",
    pinned: false,
    position: { x: params.x, y: params.y },
    size: { width: params.width, height: params.height },
    defaultWidth: params.width,
    defaultHeight: params.height,
    labelEditable: true,
    deletable: true,
    customizedStyleProperties: [],
    initialBorderNodePosition: "WEST",
    insideLabel: insideLabel,
    outsideLabels: [],
    borderNodes: [],
    childNodes: [],
    style: nodeStyle,
    state: GQLViewModifier.Normal,
  };

  const newLayoutData: GQLNodeLayoutData = {
    id: params.id,
    position: { x: params.x, y: params.y },
    size: { width: params.width, height: params.height },
    minComputedSize: { width: params.width, height: params.height },
    resizedByUser: false,
    movedByUser: false,
    handleLayoutData: [],
  };

  const appendChildToParent = (
    nodes: GQLNode<GQLNodeStyle>[],
  ): GQLNode<GQLNodeStyle>[] => {
    return nodes.map((node) => {
      if (node.id === params.parentId) {
        return {
          ...node,
          childNodes: [...(node.childNodes || []), newChildNode],
        };
      }
      if (node.childNodes && node.childNodes.length > 0) {
        return {
          ...node,
          childNodes: appendChildToParent(node.childNodes),
        };
      }
      return node;
    });
  };

  return {
    ...diagram,
    nodes: appendChildToParent(diagram.nodes),
    layoutData: {
      ...diagram.layoutData,
      nodeLayoutData: [...diagram.layoutData.nodeLayoutData, newLayoutData],
    },
  };
};
