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
import { GQLRectangularNodeStyle } from '../../../graphql/subscription/nodeFragment.types';
import { NodeData } from '../../DiagramRenderer.types';
import { PaletteAppearanceSectionContributionComponentProps } from './extensions/PaletteAppearanceSectionContribution.types';
import { LabelAppearancePart } from './label/LabelAppearancePart';
import { RectangularNodePart } from './rectangular-node/RectangularNodePart';

export const RectangularNodeAppearanceSection = ({
  diagramElementIds,
}: PaletteAppearanceSectionContributionComponentProps) => {
  const nodeDatas = useNodesData<Node<NodeData>>(diagramElementIds).map((nodeData) => nodeData.data);
  const data = nodeDatas.at(nodeDatas.length - 1);
  if (!data) {
    return null;
  }

  const insideLabelsIds = nodeDatas.flatMap((nodeData) => nodeData.insideLabel?.id || []);
  const outsideLabelsIds = nodeDatas.flatMap((nodeData) => nodeData.outsideLabels.BOTTOM_MIDDLE?.id || []);
  const { t } = useTranslation('sirius-components-diagrams', { keyPrefix: 'rectangularNodeAppearanceSection' });

  return (
    <>
      <RectangularNodePart
        nodeIds={diagramElementIds}
        style={data.nodeAppearanceData.gqlStyle as GQLRectangularNodeStyle}
        customizedStyleProperties={data.nodeAppearanceData.customizedStyleProperties}
      />
      {data.insideLabel ? (
        <LabelAppearancePart
          diagramElementIds={diagramElementIds}
          labelIds={insideLabelsIds}
          position={t('insideLabel')}
          style={data.insideLabel.appearanceData.gqlStyle}
          customizedStyleProperties={data.insideLabel.appearanceData.customizedStyleProperties}
        />
      ) : null}
      {data.outsideLabels.BOTTOM_MIDDLE ? (
        <LabelAppearancePart
          diagramElementIds={diagramElementIds}
          labelIds={outsideLabelsIds}
          position={t('outsideMiddleLabel')}
          style={data.outsideLabels.BOTTOM_MIDDLE.appearanceData.gqlStyle}
          customizedStyleProperties={data.outsideLabels.BOTTOM_MIDDLE.appearanceData.customizedStyleProperties}
        />
      ) : null}
    </>
  );
};
