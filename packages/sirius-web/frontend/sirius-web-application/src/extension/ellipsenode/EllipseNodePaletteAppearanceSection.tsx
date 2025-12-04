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

import {
  LabelAppearancePart,
  NodeData,
  PaletteAppearanceSectionContributionComponentProps,
} from '@eclipse-sirius/sirius-components-diagrams';
import { Node, useNodesData } from '@xyflow/react';
import { useTranslation } from 'react-i18next';
import { EllipseNodePart } from './EllipseNodePart';
import { GQLEllipseNodeStyle } from './EllipseNodePart.types';

export const EllipseNodeAppearanceSection = ({
  diagramElementIds,
}: PaletteAppearanceSectionContributionComponentProps) => {
  const nodeDatas = useNodesData<Node<NodeData>>(diagramElementIds).map((nodeData) => nodeData.data);
  const data = nodeDatas.at(nodeDatas.length - 1);
  if (!data) {
    return null;
  }
  const insideLabelsIds = nodeDatas.flatMap((nodeData) => nodeData.insideLabel?.id || []);
  const { t } = useTranslation('sirius-components-diagrams', { keyPrefix: 'ellipseNodeAppearanceSection' });
  return (
    <>
      <EllipseNodePart
        nodeIds={diagramElementIds}
        style={data.nodeAppearanceData.gqlStyle as GQLEllipseNodeStyle}
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
    </>
  );
};
