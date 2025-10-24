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
import { MultiLabelEdgeData } from '../../../edge/MultiLabelEdge.types';
import { LabelAppearancePart } from '../label/LabelAppearancePart';
import { EdgeAppearancePart } from './EdgeAppearancePart';
import { EdgeAppearanceSectionProps } from './EdgeAppearanceSection.types';

const edgeDataSelector = (state: ReactFlowState, edgeId: string) =>
  state.edgeLookup.get(edgeId)?.data as MultiLabelEdgeData | undefined;

export const EdgeAppearanceSection = ({ diagramElementId }: EdgeAppearanceSectionProps) => {
  const edgeData = useStore((state) => edgeDataSelector(state, diagramElementId));

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
          position="Begin Label"
          style={edgeData.beginLabel.appearanceData.gqlStyle}
          customizedStyleProperties={edgeData.beginLabel.appearanceData.customizedStyleProperties}
        />
      ) : null}
      {edgeData.label ? (
        <LabelAppearancePart
          diagramElementId={diagramElementId}
          labelId={edgeData.label.id}
          position="Center Label"
          style={edgeData.label.appearanceData.gqlStyle}
          customizedStyleProperties={edgeData.label.appearanceData.customizedStyleProperties}
        />
      ) : null}
      {edgeData.endLabel ? (
        <LabelAppearancePart
          diagramElementId={diagramElementId}
          labelId={edgeData.endLabel.id}
          position="End Label"
          style={edgeData.endLabel.appearanceData.gqlStyle}
          customizedStyleProperties={edgeData.endLabel.appearanceData.customizedStyleProperties}
        />
      ) : null}
    </>
  );
};
