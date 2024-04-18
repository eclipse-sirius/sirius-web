/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import { GQLImageMetadata } from './useProjectImages.types';

export interface ImageRowProps {
  image: GQLImageMetadata;
  onImageUpdated: () => void;
}

export interface ImageRowState {
  showEditIcon: boolean;
  modal: ImageRowModal | null;
}

export type ImageRowModal = 'Rename' | 'Delete';
