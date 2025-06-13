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
import { GQLRectangularNodeStyle } from '../../../graphql/subscription/nodeFragment.types';
import { LabelAppearancePart } from './label/LabelAppearancePart';
import { RectangularNodePart } from './rectangular-node/RectangularNodePart';
import { RectangularNodeAppearanceSectionProps } from './RectangularNodeAppearanceSection.types';

export const RectangularNodeAppearanceSection = ({ nodeId, nodeData }: RectangularNodeAppearanceSectionProps) => {
  return (
    <>
      <RectangularNodePart
        nodeId={nodeId}
        style={nodeData.nodeAppearanceData.gqlStyle as GQLRectangularNodeStyle}
        customizedStyleProperties={nodeData.nodeAppearanceData.customizedStyleProperties}
      />
      {nodeData.insideLabel ? (
        <LabelAppearancePart
          diagramElementId={nodeId}
          labelId={nodeData.insideLabel.id}
          style={nodeData.insideLabel.appearanceData.gqlStyle}
          customizedStyleProperties={nodeData.insideLabel.appearanceData.customizedStyleProperties}
        />
      ) : null}
    </>
  );
};
