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
import { ReactFlowState, useStore } from '@xyflow/react';
import { useTranslation } from 'react-i18next';
import { MultiLabelEdgeData } from '../../../edge/MultiLabelEdge.types';
import { PaletteAppearanceSectionContributionComponentProps } from '../extensions/PaletteAppearanceSectionContribution.types';
import { LabelAppearancePart } from '../label/LabelAppearancePart';
import { EdgeAppearancePart } from './EdgeAppearancePart';

const edgeDataSelector = (state: ReactFlowState, edgeId: string) =>
  state.edgeLookup.get(edgeId)?.data as MultiLabelEdgeData | undefined;

export const EdgeAppearanceSection = ({ diagramElementId }: PaletteAppearanceSectionContributionComponentProps) => {
  const edgeData = useStore((state) => edgeDataSelector(state, diagramElementId));
  const { t } = useTranslation('sirius-components-diagrams', { keyPrefix: 'edgeAppearanceSection' });

  if (!edgeData) {
    return null;
  }

  return (
    <>
      <EdgeAppearancePart
        edgeId={diagramElementId}
        style={edgeData.edgeAppearanceData.gqlStyle}
        customizedStyleProperties={edgeData.edgeAppearanceData.customizedStyleProperties}
      />
      {edgeData.beginLabel ? (
        <LabelAppearancePart
          diagramElementId={diagramElementId}
          labelId={edgeData.beginLabel.id}
          position={t('beginLabel')}
          style={edgeData.beginLabel.appearanceData.gqlStyle}
          customizedStyleProperties={edgeData.beginLabel.appearanceData.customizedStyleProperties}
        />
      ) : null}
      {edgeData.label ? (
        <LabelAppearancePart
          diagramElementId={diagramElementId}
          labelId={edgeData.label.id}
          position={t('centerLabel')}
          style={edgeData.label.appearanceData.gqlStyle}
          customizedStyleProperties={edgeData.label.appearanceData.customizedStyleProperties}
        />
      ) : null}
      {edgeData.endLabel ? (
        <LabelAppearancePart
          diagramElementId={diagramElementId}
          labelId={edgeData.endLabel.id}
          position={t('endLabel')}
          style={edgeData.endLabel.appearanceData.gqlStyle}
          customizedStyleProperties={edgeData.endLabel.appearanceData.customizedStyleProperties}
        />
      ) : null}
    </>
  );
};
