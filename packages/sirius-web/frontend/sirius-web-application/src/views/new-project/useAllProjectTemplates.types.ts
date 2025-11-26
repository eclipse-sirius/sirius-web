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

export interface GQLGetAllProjectTemplatesQueryVariables {}

export interface UseAllProjectTemplatesValue {
  data: GQLAllGetProjectTemplatesQueryData | null;
  loading: boolean;
}

export interface GQLAllGetProjectTemplatesQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  allProjectTemplates: GQLProjectTemplate[];
}

export interface GQLProjectTemplate {
  id: string;
  label: string;
  imageURL: string;
}
