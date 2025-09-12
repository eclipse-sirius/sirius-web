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
import { LabelAppearancePart } from '../label/LabelAppearancePart';
import { EdgeAppearancePart } from './EdgeAppearancePart';
import { EdgeAppearanceSectionProps } from './EdgeAppearanceSection.types';

export const EdgeAppearanceSection = ({ edgeId, edgeData }: EdgeAppearanceSectionProps) => {
  return (
    <>
      <EdgeAppearancePart
        edgeId={edgeId}
        style={edgeData.edgeAppearanceData.gqlStyle}
        customizedStyleProperties={edgeData.edgeAppearanceData.customizedStyleProperties}
      />
      {edgeData.beginLabel ? (
        <LabelAppearancePart
          diagramElementId={edgeId}
          labelId={edgeData.beginLabel.id}
          position="Begin Label"
          style={edgeData.beginLabel.appearanceData.gqlStyle}
          customizedStyleProperties={edgeData.beginLabel.appearanceData.customizedStyleProperties}
        />
      ) : null}
      {edgeData.label ? (
        <LabelAppearancePart
          diagramElementId={edgeId}
          labelId={edgeData.label.id}
          position="Center Label"
          style={edgeData.label.appearanceData.gqlStyle}
          customizedStyleProperties={edgeData.label.appearanceData.customizedStyleProperties}
        />
      ) : null}
      {edgeData.endLabel ? (
        <LabelAppearancePart
          diagramElementId={edgeId}
          labelId={edgeData.endLabel.id}
          position="End Label"
          style={edgeData.endLabel.appearanceData.gqlStyle}
          customizedStyleProperties={edgeData.endLabel.appearanceData.customizedStyleProperties}
        />
      ) : null}
    </>
  );
};
