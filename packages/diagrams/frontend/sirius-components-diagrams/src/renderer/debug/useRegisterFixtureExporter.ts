/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import type { Edge, Node, XYPosition } from '@xyflow/react';
import { Position } from '@xyflow/react';
import parse from 'svg-path-parser';
import { useEffect } from 'react';
import type { EdgeData, NodeData } from '../DiagramRenderer.types';
import type { ConnectionHandle } from '../handles/ConnectionHandles.types';

type FixtureHandlePosition = 'top' | 'right' | 'bottom' | 'left';

type FixtureHandle = {
  id: string;
  type: ConnectionHandle['type'];
  position: FixtureHandlePosition;
  x?: number;
  y?: number;
  isHidden?: boolean;
  isVirtualHandle?: boolean;
};

type FixtureNode = {
  id: string;
  label: string;
  position: XYPosition;
  size: { width: number; height: number };
  handles?: FixtureHandle[];
};

type FixtureEdge = {
  id: string;
  source: string;
  target: string;
  type: string;
  sourcePosition: FixtureHandlePosition;
  targetPosition: FixtureHandlePosition;
  sourceHandleId: string;
  targetHandleId: string;
  sourcePoint?: XYPosition;
  targetPoint?: XYPosition;
};

type Fixture = {
  id: string;
  name: string;
  description?: string;
  categories?: string[];
  nodes: FixtureNode[];
  edges: FixtureEdge[];
};

type ExportOptions = {
  id?: string;
  name?: string;
  description?: string;
  categories?: string[];
};

type ExporterApi = {
  exportFixture: (options?: ExportOptions) => Fixture;
  downloadFixture: (options?: ExportOptions) => Fixture;
  copyFixture: (options?: ExportOptions) => Promise<Fixture>;
};

type NodeWithInternals = Node<NodeData> &
  Partial<{
    positionAbsolute: XYPosition;
    parentNode: string;
    parentId: string;
    internals: {
      positionAbsolute?: XYPosition;
    };
  }>;

type EdgeWithPositions = Edge<EdgeData> &
  Partial<{
    sourcePosition: Position;
    targetPosition: Position;
  }>;

const GLOBAL_EXPORTER_KEY = '__SIRIUS_ROUTING_HARNESS__';

const positionToHandle = (position: Position | undefined, fallback: FixtureHandlePosition): FixtureHandlePosition => {
  switch (position) {
    case Position.Left:
      return 'left';
    case Position.Right:
      return 'right';
    case Position.Top:
      return 'top';
    case Position.Bottom:
      return 'bottom';
    default:
      return fallback;
  }
};

const inferPositionFromHandleId = (handleId: string, fallback: FixtureHandlePosition): FixtureHandlePosition => {
  const segments = handleId.split('-');
  const candidate = segments[segments.length - 1];
  if (candidate === 'top' || candidate === 'bottom' || candidate === 'left' || candidate === 'right') {
    return candidate;
  }
  return fallback;
};

const defaultWidth = 160;
const defaultHeight = 80;

const sanitizeId = (value: string): string => value.replace(/[^a-zA-Z0-9-_]/g, '-');

const parseEdgeEndpoints = (edge: Edge<EdgeData>): { source: XYPosition; target: XYPosition } | undefined => {
  const path = edge.data?.edgePath;
  if (!path) {
    return undefined;
  }

  try {
    const commands = parse(path);
    const points = commands
      .map((command) => ('x' in command && 'y' in command ? ({ x: command.x, y: command.y } as XYPosition) : null))
      .filter((point): point is XYPosition => point !== null);
    if (points.length >= 2) {
      return { source: points[0], target: points[points.length - 1] };
    }
  } catch (error) {
    console.warn('[routing-exporter] Unable to parse edge path for edge', edge.id, error);
  }
  return undefined;
};

const toRelativePosition = (absolute: XYPosition | undefined, nodeTopLeft: XYPosition | undefined): XYPosition | undefined => {
  if (!absolute || !nodeTopLeft) {
    return undefined;
  }
  return {
    x: Math.round(absolute.x - nodeTopLeft.x),
    y: Math.round(absolute.y - nodeTopLeft.y),
  };
};

const buildFixture = (
  nodes: Node<NodeData>[],
  edges: Edge<EdgeData>[],
  options: ExportOptions = {},
  getNodeById?: (id: string) => Node<NodeData> | undefined,
  allReactFlowNodes: Node<NodeData>[] = []
): Fixture => {
  const now = new Date();
  const rawId = options.id ?? `fixture-${now.getFullYear()}${String(now.getMonth() + 1).padStart(2, '0')}${String(
    now.getDate()
  ).padStart(2, '0')}-${now.getHours()}${now.getMinutes()}${now.getSeconds()}`;
  const fixtureId = sanitizeId(rawId);
  const fixtureName = options.name ?? fixtureId;

  const typedNodes = nodes as NodeWithInternals[];
  const storeNodesById = new Map<string, NodeWithInternals>();
  typedNodes.forEach((node) => storeNodesById.set(node.id, node));

  const reactFlowNodesById = new Map<string, NodeWithInternals>();
  allReactFlowNodes.forEach((node) => reactFlowNodesById.set(node.id, node as NodeWithInternals));

  if (getNodeById) {
    typedNodes.forEach((node) => {
      if (!reactFlowNodesById.has(node.id)) {
        const rfNode = getNodeById(node.id) as NodeWithInternals | undefined;
        if (rfNode) {
          reactFlowNodesById.set(node.id, rfNode);
        }
      }
    });
  }

  const nodeAbsolutePosition = new Map<string, XYPosition>();
  const nodeConnectionHandles = new Map<string, ConnectionHandle[]>();
  const unresolvedRelativeNodes = new Set<string>();

  const resolveAbsolutePosition = (node: NodeWithInternals): XYPosition => {
    const cached = nodeAbsolutePosition.get(node.id);
    if (cached) {
      return cached;
    }

    const rfNode = reactFlowNodesById.get(node.id);
    const nodeAbsolute = node.positionAbsolute;
    const rfAbsolute = rfNode?.positionAbsolute;
    const internalsAbsolute = node.internals?.positionAbsolute ?? rfNode?.internals?.positionAbsolute;

    if (rfAbsolute && typeof rfAbsolute.x === 'number' && typeof rfAbsolute.y === 'number') {
      const absolute = { x: rfAbsolute.x, y: rfAbsolute.y };
      nodeAbsolutePosition.set(node.id, absolute);
      return absolute;
    }

    if (nodeAbsolute && typeof nodeAbsolute.x === 'number' && typeof nodeAbsolute.y === 'number') {
      const absolute = { x: nodeAbsolute.x, y: nodeAbsolute.y };
      nodeAbsolutePosition.set(node.id, absolute);
      return absolute;
    }

    if (internalsAbsolute && typeof internalsAbsolute.x === 'number' && typeof internalsAbsolute.y === 'number') {
      const absolute = { x: internalsAbsolute.x, y: internalsAbsolute.y };
      nodeAbsolutePosition.set(node.id, absolute);
      return absolute;
    }

    const parentId = node.parentNode ?? node.parentId ?? rfNode?.parentNode ?? (rfNode as NodeWithInternals | undefined)?.parentId;
    if (!parentId) {
      const absolute = { x: node.position.x, y: node.position.y };
      nodeAbsolutePosition.set(node.id, absolute);
      return absolute;
    }

    const parentNode =
      storeNodesById.get(parentId) ??
      reactFlowNodesById.get(parentId) ??
      (getNodeById?.(parentId) as NodeWithInternals | undefined);
    if (!parentNode) {
      unresolvedRelativeNodes.add(node.id);
      const absolute = { x: node.position.x, y: node.position.y };
      nodeAbsolutePosition.set(node.id, absolute);
      return absolute;
    }

    const parentAbsolute = resolveAbsolutePosition(parentNode);
    const absolute = {
      x: parentAbsolute.x + node.position.x,
      y: parentAbsolute.y + node.position.y,
    };
    nodeAbsolutePosition.set(node.id, absolute);
    return absolute;
  };

  typedNodes.forEach((node) => {
    const absolute = resolveAbsolutePosition(node);
    nodeAbsolutePosition.set(node.id, absolute);
    nodeConnectionHandles.set(node.id, node.data?.connectionHandles ?? []);
  });

  const handleOverrides = new Map<string, XYPosition>();
  const registerHandleOverride = (nodeId: string, handleId: string, absolutePoint: XYPosition | undefined) => {
    const nodePosition = nodeAbsolutePosition.get(nodeId);
    const relative = toRelativePosition(absolutePoint, nodePosition);
    if (relative) {
      handleOverrides.set(`${nodeId}::${handleId}`, relative);
    }
  };

  const fixtureEdges: FixtureEdge[] = (edges as EdgeWithPositions[]).map((edge) => {
    const sourcePosition = positionToHandle(edge.sourcePosition, 'right');
    const targetPosition = positionToHandle(edge.targetPosition, 'left');
    const sourceHandleId = edge.sourceHandle ?? `source-${sourcePosition}`;
    const targetHandleId = edge.targetHandle ?? `target-${targetPosition}`;

    const endpoints = parseEdgeEndpoints(edge);
    if (endpoints) {
      registerHandleOverride(edge.source, sourceHandleId, endpoints.source);
      registerHandleOverride(edge.target, targetHandleId, endpoints.target);
    }

    return {
      id: edge.id,
      source: edge.source,
      target: edge.target,
      type: edge.type ?? 'manhattan',
      sourcePosition,
      targetPosition,
      sourceHandleId,
      targetHandleId,
      sourcePoint: endpoints?.source,
      targetPoint: endpoints?.target,
      bendingPoints: edge.data?.bendingPoints ?? undefined,
    };
  });

  const fixtureNodes: FixtureNode[] = (nodes as NodeWithInternals[]).reduce<FixtureNode[]>((acc, node) => {
    if (unresolvedRelativeNodes.has(node.id)) {
      return acc;
    }

    const absolute = nodeAbsolutePosition.get(node.id) ?? { x: node.position.x, y: node.position.y };
    const width = node.width ?? node.data?.defaultWidth ?? defaultWidth;
    const height = node.height ?? node.data?.defaultHeight ?? defaultHeight;
    const label = typeof node.data?.targetObjectLabel === 'string' ? node.data.targetObjectLabel : node.id;

    const handleEntries = new Map<string, FixtureHandle>();
    const existingHandles = nodeConnectionHandles.get(node.id) ?? [];

    existingHandles.forEach((handle) => {
      if (typeof handle.id !== 'string') {
        return;
      }
      const key = `${node.id}::${handle.id}`;
      const override = handleOverrides.get(key);
      const relative =
        override ??
        (handle.XYPosition
          ? { x: Math.round(handle.XYPosition.x), y: Math.round(handle.XYPosition.y) }
          : undefined);
      handleEntries.set(handle.id, {
        id: handle.id,
        type: handle.type,
        position: positionToHandle(handle.position, handle.type === 'source' ? 'right' : 'left'),
        x: relative?.x,
        y: relative?.y,
        isHidden: handle.isHidden,
        isVirtualHandle: handle.isVirtualHandle,
      });
    });

    handleOverrides.forEach((relative, key) => {
      const [nodeId, handleId] = key.split('::') as [string, string];
      if (nodeId !== node.id || handleEntries.has(handleId)) {
        return;
      }
      const inferredType = handleId.startsWith('target-') ? 'target' : 'source';
      const fallbackPosition = inferredType === 'target' ? 'left' : 'right';
      handleEntries.set(handleId, {
        id: handleId,
        type: inferredType as ConnectionHandle['type'],
        position: inferPositionFromHandleId(handleId, fallbackPosition),
        x: relative.x,
        y: relative.y,
        isHidden: false,
        isVirtualHandle: false,
      });
    });

    const handles = Array.from(handleEntries.values());

    acc.push({
      id: node.id,
      label,
      position: {
        x: Math.round(absolute.x),
        y: Math.round(absolute.y),
      },
      size: {
        width: Math.round(width),
        height: Math.round(height),
      },
      handles: handles.length > 0 ? handles : undefined,
    });

    return acc;
  }, []);

  return {
    id: fixtureId,
    name: fixtureName,
    description: options.description,
    ...(options.categories && options.categories.length > 0 ? { categories: [...options.categories] } : {}),
    nodes: fixtureNodes,
    edges: fixtureEdges,
  };
};

const triggerDownload = (fixture: Fixture) => {
  const blob = new Blob([JSON.stringify(fixture, null, 2)], { type: 'application/json' });
  const url = URL.createObjectURL(blob);
  const anchor = document.createElement('a');
  anchor.href = url;
  anchor.download = `${fixture.id}.json`;
  document.body.appendChild(anchor);
  anchor.click();
  document.body.removeChild(anchor);
  URL.revokeObjectURL(url);
};

const copyToClipboard = async (fixture: Fixture): Promise<void> => {
  const text = JSON.stringify(fixture, null, 2);
  if ('clipboard' in navigator && typeof navigator.clipboard.writeText === 'function') {
    await navigator.clipboard.writeText(text);
    return;
  }
  const textarea = document.createElement('textarea');
  textarea.value = text;
  textarea.style.position = 'fixed';
  textarea.style.top = '0';
  textarea.style.left = '0';
  textarea.style.opacity = '0';
  document.body.appendChild(textarea);
  textarea.focus();
  textarea.select();
  try {
    document.execCommand('copy');
  } finally {
    document.body.removeChild(textarea);
  }
};

declare global {
  // eslint-disable-next-line @typescript-eslint/naming-convention
  interface Window {
    __SIRIUS_ROUTING_HARNESS__?: ExporterApi;
  }
}

export const useRegisterFixtureExporter = (
  getNodes: () => Node<NodeData>[],
  getEdges: () => Edge<EdgeData>[],
  getNodeById?: (id: string) => Node<NodeData> | undefined,
  getAllNodes?: () => Node<NodeData>[]
): void => {
  useEffect(() => {
    if (typeof window === 'undefined') {
      return;
    }

    const exportFixture = (options: ExportOptions = {}) => {
      const nodes = getNodes();
      const edges = getEdges();
      const reactFlowNodes = getAllNodes ? getAllNodes() : [];
      const fixture = buildFixture(nodes, edges, options, getNodeById, reactFlowNodes);
      console.info('[routing-exporter] Generated fixture:', fixture);
      if (fixture.categories?.length) {
        console.info(`[routing-exporter] Categories: ${fixture.categories.join(', ')}`);
      }
      console.info('[routing-exporter] Move the downloaded JSON into packages/dev/frontend/routing-harness/src/fixtures/.');
      return fixture;
    };

    const downloadFixture = (options: ExportOptions = {}) => {
      const fixture = exportFixture(options);
      triggerDownload(fixture);
      console.info(`[routing-exporter] Downloaded ${fixture.id}.json.`);
      return fixture;
    };

    const copyFixture = async (options: ExportOptions = {}) => {
      const fixture = exportFixture(options);
      await copyToClipboard(fixture);
      console.info('[routing-exporter] Fixture JSON copied to clipboard.');
      return fixture;
    };

    const boundApi: ExporterApi = {
      exportFixture,
      downloadFixture,
      copyFixture,
    };

    const existing = window[GLOBAL_EXPORTER_KEY];
    const apiForWindow: ExporterApi = {
      ...(existing ?? {}),
      ...boundApi,
    };
    window[GLOBAL_EXPORTER_KEY] = apiForWindow;

    console.info(
      `[routing-exporter] Ready. Use window.${GLOBAL_EXPORTER_KEY}.downloadFixture({ id: 'my-fixture', name: 'My Fixture' }).`
    );

    return () => {
      const current = window[GLOBAL_EXPORTER_KEY];
      if (current === apiForWindow) {
        delete window[GLOBAL_EXPORTER_KEY];
      }
    };
  }, [getNodes, getEdges]);
};
