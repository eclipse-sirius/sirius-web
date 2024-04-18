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

export interface UseProjectImagesValue {
  data: GQLGetProjectImagesQueryData | null;
  loading: boolean;
  refreshImages: () => void;
}

export interface GQLGetProjectImagesQueryVariables {
  projectId: string;
}

export interface GQLGetProjectImagesQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  project: GQLProject | null;
}

export interface GQLProject {
  images: GQLImageMetadata[];
}

export interface GQLImageMetadata {
  id: string;
  label: string;
  url: string;
}
