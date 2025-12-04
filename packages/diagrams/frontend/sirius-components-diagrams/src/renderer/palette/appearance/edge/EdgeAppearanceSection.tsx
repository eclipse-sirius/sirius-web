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
import { Edge, useEdges } from '@xyflow/react';
import { useTranslation } from 'react-i18next';
import { EdgeData } from '../../../DiagramRenderer.types';
import { MultiLabelEdgeData } from '../../../edge/MultiLabelEdge.types';
import { PaletteAppearanceSectionContributionComponentProps } from '../extensions/PaletteAppearanceSectionContribution.types';
import { LabelAppearancePart } from '../label/LabelAppearancePart';
import { EdgeAppearancePart } from './EdgeAppearancePart';

export const EdgeAppearanceSection = ({ diagramElementIds }: PaletteAppearanceSectionContributionComponentProps) => {
  const edgeDatas = useEdges<Edge<EdgeData>>()
    .filter((edge) => diagramElementIds.find((id) => edge.id === id))
    .map((edge) => edge.data as MultiLabelEdgeData);

  const edgeData = edgeDatas.at(edgeDatas.length - 1);

  const { t } = useTranslation('sirius-components-diagrams', { keyPrefix: 'edgeAppearanceSection' });

  if (!edgeData) {
    return null;
  }

  return (
    <>
      <EdgeAppearancePart
        edgeIds={diagramElementIds}
        style={edgeData.edgeAppearanceData.gqlStyle}
        customizedStyleProperties={edgeData.edgeAppearanceData.customizedStyleProperties}
      />
      {edgeData.beginLabel ? (
        <LabelAppearancePart
          diagramElementIds={diagramElementIds}
          labelIds={edgeDatas.map((data) => data.beginLabel?.id || '')}
          position={t('beginLabel')}
          style={edgeData.beginLabel.appearanceData.gqlStyle}
          customizedStyleProperties={edgeData.beginLabel.appearanceData.customizedStyleProperties}
        />
      ) : null}
      {edgeData.label ? (
        <LabelAppearancePart
          diagramElementIds={diagramElementIds}
          labelIds={edgeDatas.map((data) => data.label?.id || '')}
          position={t('centerLabel')}
          style={edgeData.label.appearanceData.gqlStyle}
          customizedStyleProperties={edgeData.label.appearanceData.customizedStyleProperties}
        />
      ) : null}
      {edgeData.endLabel ? (
        <LabelAppearancePart
          diagramElementIds={diagramElementIds}
          labelIds={edgeDatas.map((data) => data.endLabel?.id || '')}
          position={t('endLabel')}
          style={edgeData.endLabel.appearanceData.gqlStyle}
          customizedStyleProperties={edgeData.endLabel.appearanceData.customizedStyleProperties}
        />
      ) : null}
    </>
  );
};
