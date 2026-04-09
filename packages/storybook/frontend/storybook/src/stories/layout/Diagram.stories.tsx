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
import { Meta, StoryObj } from '@storybook/react';
import '@xyflow/react/dist/style.css';
import { DiagramStoryWrapper } from '../../utils/layout/DiagramStoryWrapper';
import '../../utils/layout/global.css';
import { defaultAllAlgorithmsConfiguration, DiagramStoryArgs, getElkAllAlgorithmsOptions } from '../../utils/layout/layoutConfigurations';
import { AllScenarioDiagram, BasiqueSiriusDiagram, FiveNodeDiagram, ThreeNodeGroupDiagram, TwoNodeGroupDiagram } from '../../utils/layout/scenario';

const meta = {
  title: 'Layout/All algorithms',
  tags: ['autodocs'],
  argTypes: {
    autoLayout: { name: 'Auto-layout', control: 'boolean' },
    algorithm: { name: 'Algorithm', control: 'select', options: ['force', 'layered', 'mrtree', 'radial', 'rectpacking', 'sporeCompaction', 'sporeOverlap','stress'] },
    nodeNode: { name: 'Node spacing', control: 'number', if: { arg: 'algorithm', neq: 'stress' } },

    directionL: { name: 'Layout direction', control: 'select', if: { arg: 'algorithm', eq: 'layered' }, options: ['DOWN', 'RIGHT', 'LEFT', 'UP'] },
    nodeNodeBetweenLayers: { name: 'Node spacing between layers', control: 'number', if: { arg: 'algorithm', eq: 'layered' } },
    componentComponent: { name: 'Component spacing', control: 'number', if: { arg: 'algorithm', eq: 'layered' } },
    edgeNodeBetweenLayers: { name: 'Edge spacing between layers', control: 'number', if: { arg: 'algorithm', eq: 'layered' } },
    layStrat: { name: 'Layering strategy', control: 'select', if: { arg: 'algorithm', eq: 'layered' }, options: ['NETWORK_SIMPLEX', 'LONGEST_PATH', 'LONGEST_PATH_SOURCE'] },
    nodePlacStrat: { name: 'Node placement strategy', control: 'select', if: { arg: 'algorithm', eq: 'layered' }, options: ['NETWORK_SIMPLEX', 'LINEAR_SEGMENTS', 'SIMPLE', 'BRANDES_KOEPF'] },
    contentAlignLayered: { name: 'Content alignment', control: 'select', if: { arg: 'algorithm', eq: 'layered' }, options: ['V_TOP H_LEFT', 'V_TOP H_CENTER', 'V_TOP H_RIGHT', 'V_CENTER H_LEFT', 'V_CENTER H_CENTER', 'V_CENTER H_RIGHT', 'V_BOTTOM H_LEFT', 'V_BOTTOM H_CENTER', 'V_BOTTOM H_RIGHT'] },
    
    directionT: { name: 'Layout direction', control: 'select', if: { arg: 'algorithm', eq: 'mrtree' }, options: ['DOWN', 'RIGHT', 'LEFT', 'UP'] },
    edgeNode: { name: 'Edge spacing', control: 'number', if: { arg: 'algorithm', eq: 'mrtree' } },
    edgeRoutingMode: { name: 'Edge routing mode', control: 'select', if: { arg: 'algorithm', eq: 'mrtree' }, options: ['AVOID_OVERLAP', 'MIDDLE_TO_MIDDLE', 'NONE'] },
    wheighting: { name: 'Weighting method', control: 'select', if: { arg: 'algorithm', eq: 'mrtree' }, options: ['MODEL_ORDER', 'DESCENDANTS', 'FAN', 'CONSTRAINT'] },

    whiteSpaceStart: { name: 'White space distribution', control: 'select', if: { arg: 'algorithm', eq: 'rectpacking' }, options: ['EQUAL_BETWEEN_STRUCTURES', 'TO_ASPECT_RATIO', 'NONE'] },
    approStrat: { name: 'Packing strategy', control: 'select', if: { arg: 'algorithm', eq: 'rectpacking' }, options: ['GREEDY', 'TARGET_WIDTH'] },
    targetWidth: { name: 'Target width', control: 'number', if: { arg: 'algorithm', eq: 'rectpacking' } },
    contentAlignRect: { name: 'Content alignment', control: 'select', if: { arg: 'algorithm', eq: 'rectpacking' }, options: ['V_TOP H_LEFT', 'V_TOP H_CENTER', 'V_TOP H_RIGHT', 'V_CENTER H_LEFT', 'V_CENTER H_CENTER', 'V_CENTER H_RIGHT', 'V_BOTTOM H_LEFT', 'V_BOTTOM H_CENTER', 'V_BOTTOM H_RIGHT'] },
    approOptiGoal: { name: 'Optimization goal', control: 'select', if: { arg: 'algorithm', eq: 'rectpacking' }, options: ['ASPECT_RATIO_DRIVEN', 'MAX_SCALE_DRIVEN', 'AREA_DRIVEN'] },

    wedgeCrit: { name: 'Wedge criterion', control: 'select', if: { arg: 'algorithm', eq: 'radial' }, options: ['LEAF_NUMBER', 'NODE_SIZE'] },
    compactor: { name: 'Compaction method', control: 'select', if: { arg: 'algorithm', eq: 'radial' }, options: ['NONE', 'RADIAL_COMPACTION', 'WEDGE_COMPACTION'] },
    optiCrit: { name: 'Optimization criterion', control: 'select', if: { arg: 'algorithm', eq: 'radial' }, options: ['NONE', 'EDGE_LENGTH', 'EDGE_LENGTH_BY_POSITION ', 'CROSSING_MINIMIZATION_BY_POSITION'] },
 
    spanningTree: { name: 'Spanning tree metric', control: 'select', if: { arg: 'algorithm', eq: 'sporeCompaction' }, options: ['CENTER_DISTANCE', 'CIRCLE_UNDERLAP', 'RECTANGLE_UNDERLAP', 'INVERTED_OVERLAP', 'MINIMUM_ROOT_DISTANCE'] },
    treeConstrution: { name: 'Construction method', control: 'select', if: { arg: 'algorithm', eq: 'sporeCompaction' }, options: ['MINIMUM_SPANNING_TREE', 'MAXIMUM_SPANNING_TREE'] },

    model: { name: 'Force model', control: 'select', if: { arg: 'algorithm', eq: 'force' }, options: ['EADES', 'FRUCHTERMAN_REINGOLD'] },
    iterations: { name: 'Iterations', control: 'number', if: { arg: 'algorithm', eq: 'force' } },

    maxIterations: { name: 'Maximum iterations', control: 'number', if: { arg: 'algorithm', eq: 'sporeOverlap' } },

    desiredEdgeLength: { name: 'Edge length', control: 'number', if: { arg: 'algorithm', eq: 'stress' } },
    dimension: { name: 'Layout Direction', control: 'select', if: { arg: 'algorithm', eq: 'stress' }, options: ['XY', 'X', 'Y'] },
  },
} satisfies Meta<DiagramStoryArgs>;

export default meta;

const listDiagram = AllScenarioDiagram();

export const AllScenarios: StoryObj<DiagramStoryArgs> = {
  name: 'All scenarios in one story',
  args:defaultAllAlgorithmsConfiguration,
  render: (args) => (
    <div className="conteneur">
      {listDiagram.map((generator, index) => (
          <DiagramStoryWrapper 
            key={index}
            args={args} 
            diagram={generator} 
            layoutOptions={getElkAllAlgorithmsOptions(args)}
          />
      ))}
    </div>
  )
};

export const Basic: StoryObj<DiagramStoryArgs> = {
  name: 'Scenario sirius studio example',
  args:defaultAllAlgorithmsConfiguration,
  render: (args) => (
    <div style={{width: '100vw', height: '100vh'}}>
      <DiagramStoryWrapper 
          args={args} 
          diagram={BasiqueSiriusDiagram} 
          layoutOptions={getElkAllAlgorithmsOptions(args)} 
      />
    </div>
  )
};

export const FiveNodes: StoryObj<DiagramStoryArgs> = {
  name:'Scenario with five nodes',
  args:defaultAllAlgorithmsConfiguration,
  render: (args) => (
    <div style={{width: '100vw', height: '100vh'}}>
      <DiagramStoryWrapper 
          args={args} 
          diagram={FiveNodeDiagram} 
          layoutOptions={getElkAllAlgorithmsOptions(args)} 
      />
    </div>
  )
};

export const TwoNodesGroups: StoryObj<DiagramStoryArgs> = {
  name:'Scenario with two groups of nodes',
  args:defaultAllAlgorithmsConfiguration,
  render: (args) => (
    <div style={{width: '100vw', height: '100vh'}}>
      <DiagramStoryWrapper 
          args={args} 
          diagram={TwoNodeGroupDiagram} 
          layoutOptions={getElkAllAlgorithmsOptions(args)} 
      />
    </div>
  )
};

export const ThreeNodesGroups: StoryObj<DiagramStoryArgs> = {
  name:'Scenario with three groups of nodes',
  args:defaultAllAlgorithmsConfiguration,
  render: (args) => (
    <div style={{width: '100vw', height: '100vh'}}>
      <DiagramStoryWrapper
          args={args}
          diagram={ThreeNodeGroupDiagram}
          layoutOptions={getElkAllAlgorithmsOptions(args)}
      />
    </div>
  )
};