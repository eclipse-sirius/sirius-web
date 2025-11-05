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
import { IconOverlay } from '@eclipse-sirius/sirius-components-core';
import CropDinIcon from '@mui/icons-material/CropDin';
import { TreeItemIconProps } from './TreeItemIcon.types';

export const TreeItemIcon = ({ item }: TreeItemIconProps) => {
  if (item.iconURL?.length > 0) {
    return <IconOverlay iconURLs={item.iconURL} alt={item.kind} />;
  } else {
    return <CropDinIcon />;
  }
};
