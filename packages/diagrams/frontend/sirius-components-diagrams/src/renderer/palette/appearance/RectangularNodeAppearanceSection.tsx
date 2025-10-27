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
import { Node, useNodesData } from '@xyflow/react';
import { GQLRectangularNodeStyle } from '../../../graphql/subscription/nodeFragment.types';
import { NodeData } from '../../DiagramRenderer.types';
import { PaletteAppearanceSectionContributionComponentProps } from './extensions/PaletteAppearanceSectionContribution.types';
import { LabelAppearancePart } from './label/LabelAppearancePart';
import { RectangularNodePart } from './rectangular-node/RectangularNodePart';

export const RectangularNodeAppearanceSection = ({ elementId }: PaletteAppearanceSectionContributionComponentProps) => {
  const nodeData = useNodesData<Node<NodeData>>(elementId);

  if (!nodeData) {
    return null;
  }

  return (
    <>
      <RectangularNodePart
        nodeId={elementId}
        style={nodeData.data.nodeAppearanceData.gqlStyle as GQLRectangularNodeStyle}
        customizedStyleProperties={nodeData.data.nodeAppearanceData.customizedStyleProperties}
      />
      {nodeData.data.insideLabel ? (
        <LabelAppearancePart
          diagramElementId={elementId}
          labelId={nodeData.data.insideLabel.id}
          position="Inside Label"
          style={nodeData.data.insideLabel.appearanceData.gqlStyle}
          customizedStyleProperties={nodeData.data.insideLabel.appearanceData.customizedStyleProperties}
        />
      ) : null}
      {nodeData.data.outsideLabels.BOTTOM_MIDDLE ? (
        <LabelAppearancePart
          diagramElementId={elementId}
          labelId={nodeData.data.outsideLabels.BOTTOM_MIDDLE.id}
          position="Outside Middle Label"
          style={nodeData.data.outsideLabels.BOTTOM_MIDDLE.appearanceData.gqlStyle}
          customizedStyleProperties={nodeData.data.outsideLabels.BOTTOM_MIDDLE.appearanceData.customizedStyleProperties}
        />
      ) : null}
    </>
  );
};
