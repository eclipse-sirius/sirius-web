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
import { GQLImageNodeStyle } from '../../../graphql/subscription/nodeFragment.types';
import { ImageNodePart } from './image-node/ImageNodePart';
import { ImageNodeAppearanceSectionProps } from './ImageNodeAppearanceSection.types';

export const ImageNodeAppearanceSection = ({ nodeId, nodeData }: ImageNodeAppearanceSectionProps) => {
  return (
    <ImageNodePart
      nodeId={nodeId}
      style={nodeData.nodeAppearanceData.gqlStyle as GQLImageNodeStyle}
      customizedStyleProperties={nodeData.nodeAppearanceData.customizedStyleProperties}
    />
  );
};
