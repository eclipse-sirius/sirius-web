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
import { useTranslation } from 'react-i18next';
import { GQLImageNodeStyle } from '../../../graphql/subscription/nodeFragment.types';
import { NodeData } from '../../DiagramRenderer.types';
import { PaletteAppearanceSectionContributionComponentProps } from './extensions/PaletteAppearanceSectionContribution.types';
import { ImageNodePart } from './image-node/ImageNodePart';
import { LabelAppearancePart } from './label/LabelAppearancePart';

export const ImageNodeAppearanceSection = ({
  diagramElementId,
}: PaletteAppearanceSectionContributionComponentProps) => {
  const nodeData = useNodesData<Node<NodeData>>(diagramElementId);

  if (!nodeData) {
    return null;
  }
  const { t } = useTranslation('sirius-components-diagrams', { keyPrefix: 'imageNodeAppearanceSection' });
  return (
    <>
      <ImageNodePart
        nodeId={diagramElementId}
        style={nodeData.data.nodeAppearanceData.gqlStyle as GQLImageNodeStyle}
        customizedStyleProperties={nodeData.data.nodeAppearanceData.customizedStyleProperties}
      />
      {nodeData.data.insideLabel ? (
        <LabelAppearancePart
          diagramElementId={diagramElementId}
          labelId={nodeData.data.insideLabel.id}
          position={t('insideLabel')}
          style={nodeData.data.insideLabel.appearanceData.gqlStyle}
          customizedStyleProperties={nodeData.data.insideLabel.appearanceData.customizedStyleProperties}
        />
      ) : null}
      {nodeData.data.outsideLabels.BOTTOM_MIDDLE ? (
        <LabelAppearancePart
          diagramElementId={diagramElementId}
          labelId={nodeData.data.outsideLabels.BOTTOM_MIDDLE.id}
          position={t('outsideMiddleLabel')}
          style={nodeData.data.outsideLabels.BOTTOM_MIDDLE.appearanceData.gqlStyle}
          customizedStyleProperties={nodeData.data.outsideLabels.BOTTOM_MIDDLE.appearanceData.customizedStyleProperties}
        />
      ) : null}
    </>
  );
};
