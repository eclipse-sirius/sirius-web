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
import { DiagramRepresentation } from "@eclipse-sirius/sirius-components-diagrams";
import type { LayoutOptions } from "elkjs";
import type { ComponentProps } from "react";

type StoryCustomArgs = {
  autoLayout?: boolean;
  algorithm?: string;
  nodeNode?: number;
  contentAlign?: string;
  direction?: string;
  directionL?: string;
  nodeNodeBetweenLayers?: number;
  componentComponent?: number;
  edgeNodeBetweenLayers?: number;
  layStrat?: string;
  nodePlacStrat?: string;
  contentAlignLayered?: string;
  directionT?: string;
  edgeNode?: number;
  edgeRoutingMode?: string;
  wheighting?: string;
  whiteSpaceStart?: string;
  approStrat?: string;
  targetWidth?: number;
  contentAlignRect?: string;
  approOptiGoal?: string;
  wedgeCrit?: string;
  compactor?: string;
  optiCrit?: string;
  spanningTree?: string;
  treeConstrution?: string;
  model?: string;
  iterations?: number;
  maxIterations?: number;
  desiredEdgeLength?: number;
  dimension?: string;
};
export type DiagramStoryArgs = ComponentProps<typeof DiagramRepresentation> &
  StoryCustomArgs;

export const getElkForceOptions = (
  args: Partial<DiagramStoryArgs>,
): LayoutOptions => {
  const def = defaultForceAlgorithmConfiguration;
  return {
    "elk.algorithm": "force",
    "elk.spacing.nodeNode": String(args.nodeNode ?? def.nodeNode),
    "elk.force.model": args.model ?? def.model,
    "elk.force.iterations": String(args.iterations ?? def.iterations),
  };
};

export const defaultForceAlgorithmConfiguration = {
  autoLayout: true,
  nodeNode: 80,
  iterations: 300,
  model: "FRUCHTERMAN_REINGOLD",
};

export const getElkLayeredOptions = (
  args: Partial<DiagramStoryArgs>,
): LayoutOptions => {
  const def = defaultLayeredAlgorithmConfiguration;
  return {
    "elk.algorithm": "layered",
    "elk.layered.spacing.nodeNodeBetweenLayers": String(
      args.nodeNodeBetweenLayers ?? def.nodeNodeBetweenLayers,
    ),
    "elk.layered.spacing.edgeNodeBetweenLayers": String(
      args.edgeNodeBetweenLayers ?? def.edgeNodeBetweenLayers,
    ),
    "elk.spacing.componentComponent": String(
      args.componentComponent ?? def.componentComponent,
    ),
    "elk.spacing.nodeNode": String(args.nodeNode ?? def.nodeNode),
    "elk.direction": args.direction ?? def.direction,
    "elk.layering.strategy": args.layStrat ?? def.layStrat,
    "elk.layered.nodePlacement.strategy":
      args.nodePlacStrat ?? def.nodePlacStrat,
    "elk.contentAlignment": args.contentAlign ?? def.contentAlign,
  };
};

export const defaultLayeredAlgorithmConfiguration = {
  autoLayout: true,
  nodeNode: 80,
  direction: "DOWN",
  nodeNodeBetweenLayers: 80,
  componentComponent: 60,
  edgeNodeBetweenLayers: 80,
  layStrat: "NETWORK_SIMPLEX",
  nodePlacStrat: "NETWORK_SIMPLEX",
  contentAlign: "V_TOP H_CENTER",
};

export const getElkMrTreeOptions = (
  args: Partial<DiagramStoryArgs>,
): LayoutOptions => {
  const def = defaultMrTreeAlgorithmConfiguration;
  return {
    "elk.algorithm": "mrtree",
    "elk.spacing.nodeNode": String(args.nodeNode ?? def.nodeNode),
    "elk.spacing.edgeNode": String(args.edgeNode ?? def.edgeNode),
    "elk.direction": args.direction ?? def.direction,
    "elk.mrtree.weighting": args.wheighting ?? def.wheighting,
    "elk.mrtree.edgeRoutingMode": args.edgeRoutingMode ?? def.edgeRoutingMode,
  };
};

export const defaultMrTreeAlgorithmConfiguration = {
  autoLayout: true,
  direction: "DOWN",
  nodeNode: 80,
  edgeNode: 10,
  edgeRoutingMode: "AVOID_OVERLAP",
  wheighting: "MODEL_ORDER",
};

export const getElkRadialOptions = (
  args: Partial<DiagramStoryArgs>,
): LayoutOptions => {
  const def = defaultRadialAlgorithmConfiguration;
  return {
    "elk.algorithm": "radial",
    "elk.spacing.nodeNode": String(args.nodeNode ?? def.nodeNode),
    "elk.radial.wedgeCriteria": args.wedgeCrit ?? def.wedgeCrit,
    "elk.radial.compactor": args.compactor ?? def.compactor,
    "elk.radial.optimizationCriteria": args.optiCrit ?? def.optiCrit,
  };
};

export const defaultRadialAlgorithmConfiguration = {
  autoLayout: true,
  nodeNode: 80,
  wedgeCrit: "NODE_SIZE",
  compactor: "NONE",
  optiCrit: "NONE",
};

export const getElkRectPackingOptions = (
  args: Partial<DiagramStoryArgs>,
): LayoutOptions => {
  const def = defaultRectPackingAlgorithmConfiguration;
  return {
    "elk.algorithm": "rectpacking",
    "elk.spacing.nodeNode": String(args.nodeNode ?? def.nodeNode),
    "elk.rectpacking.trybox": "true",
    "elk.rectpacking.whiteSpaceElimination.strategy":
      args.whiteSpaceStart ?? def.whiteSpaceStart,
    "elk.rectpacking.widthApproximation.strategy":
      args.approStrat ?? def.approStrat,
    "elk.rectpacking.widthApproximation.targetWidth": String(
      args.targetWidth ?? def.targetWidth,
    ),
    "elk.contentAlignment": args.contentAlign ?? def.contentAlign,
    "elk.rectpacking.widthApproximation.optimizationGoal":
      args.approOptiGoal ?? def.approOptiGoal,
  };
};

export const defaultRectPackingAlgorithmConfiguration = {
  autoLayout: true,
  nodeNode: 80,
  whiteSpaceStart: "NONE",
  approStrat: "GREEDY",
  targetWidth: 1,
  contentAlign: "V_TOP H_CENTER",
  approOptiGoal: "MAX_SCALE_DRIVEN",
};

export const getElkSporeCompactionOptions = (
  args: Partial<DiagramStoryArgs>,
): LayoutOptions => {
  const def = defaultSporeCompactionAlgorithmConfiguration;
  return {
    "elk.algorithm": "sporeCompaction",
    "elk.spacing.nodeNode": String(args.nodeNode ?? def.nodeNode),
    "elk.processingOrder.spanningTreeCostFunction":
      args.spanningTree ?? def.spanningTree,
    "elk.processingOrder.treeConstruction":
      args.treeConstrution ?? def.treeConstrution,
  };
};

export const defaultSporeCompactionAlgorithmConfiguration = {
  autoLayout: true,
  nodeNode: 80,
  spanningTree: "CIRCLE_UNDERLAP",
  treeConstrution: "MINIMUM_SPANNING_TREE",
};

export const getElkSporeOverlapOptions = (
  args: Partial<DiagramStoryArgs>,
): LayoutOptions => {
  const def = defaultStressAlgorithmConfiguration;
  return {
    "elk.algorithm": "stress",
    "elk.stress.desiredEdgeLength": String(
      args.desiredEdgeLength ?? def.desiredEdgeLength,
    ),
    "elk.stress.dimension": args.dimension ?? def.dimension,
  };
};

export const defaultSporeOverlapAlgorithmConfiguration = {
  autoLayout: true,
  nodeNode: 80,
  maxIterations: 64,
};

export const getElkStressOptions = (
  args: Partial<DiagramStoryArgs>,
): LayoutOptions => {
  const def = defaultStressAlgorithmConfiguration;
  return {
    "elk.algorithm": "stress",
    "elk.stress.desiredEdgeLength": String(
      args.desiredEdgeLength ?? def.desiredEdgeLength,
    ),
    "elk.stress.dimension": args.dimension ?? def.dimension,
  };
};

export const defaultStressAlgorithmConfiguration = {
  autoLayout: true,
  desiredEdgeLength: 100.0,
  dimension: "XY",
};

export const getElkAllAlgorithmsOptions = (
  args: Partial<DiagramStoryArgs>,
): LayoutOptions => {
  switch (args.algorithm) {
    case "rectpacking":
      return getElkRectPackingOptions({
        ...args,
        contentAlign: args.contentAlignRect,
      });
    case "stress":
      return getElkStressOptions(args);
    case "mrtree":
      return getElkMrTreeOptions({ ...args, direction: args.directionT });
    case "radial":
      return getElkRadialOptions(args);
    case "force":
      return getElkForceOptions(args);
    case "sporeOverlap":
      return getElkSporeOverlapOptions(args);
    case "sporeCompaction":
      return getElkSporeCompactionOptions(args);
    default:
      return getElkLayeredOptions({
        ...args,
        direction: args.directionL,
        contentAlign: args.contentAlignLayered,
      });
  }
};

export const defaultAllAlgorithmsConfiguration = {
  autoLayout: true,
  algorithm: "layered",
  nodeNode: 80,

  directionL: "DOWN",
  nodeNodeBetweenLayers: 80,
  componentComponent: 60,
  edgeNodeBetweenLayers: 20,
  layStrat: "NETWORK_SIMPLEX",
  nodePlacStrat: "NETWORK_SIMPLEX",
  contentAlignLayered: "V_TOP H_CENTER",

  directionT: "DOWN",
  edgeNode: 10,
  edgeRoutingMode: "AVOID_OVERLAP",
  wheighting: "MODEL_ORDER",

  whiteSpaceStart: "NONE",
  approStrat: "GREEDY",
  targetWidth: 1,
  contentAlignRect: "V_TOP H_CENTER",
  approOptiGoal: "MAX_SCALE_DRIVEN",

  wedgeCrit: "NODE_SIZE",
  compactor: "NONE",
  optiCrit: "NONE",

  spanningTree: "CIRCLE_UNDERLAP",
  treeConstrution: "MINIMUM_SPANNING_TREE",

  model: "FRUCHTERMAN_REINGOLD",
  iterations: 300,

  maxIterations: 64,

  desiredEdgeLength: 100.0,
  dimension: "XY",
};
