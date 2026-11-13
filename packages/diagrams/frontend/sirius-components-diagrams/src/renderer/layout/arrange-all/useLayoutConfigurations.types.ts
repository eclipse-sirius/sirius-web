/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
export interface UseLayoutConfigurationsValue {
  layoutConfigurations: GQLLayoutConfiguration[] | null;
  loading: boolean;
}

export interface GQLGetLayoutConfigurationsVariables {
  editingContextId: string;
  representationId: string;
}

export interface GQLGetLayoutConfigurationsData {
  viewer: GQLViewer;
}
export interface GQLViewer {
  editingContext: GQLEditingContext | null;
}

export interface GQLEditingContext {
  representation: GQLRepresentationMetadata | null;
}

export interface GQLRepresentationMetadata {
  description: GQLRepresentationDescription;
}

export interface GQLRepresentationDescription {
  id: string;
  __typename: string;
}

export interface GQLDiagramDescription extends GQLRepresentationDescription {
  layoutConfigurations: GQLLayoutConfiguration[];
}

export interface GQLLayoutConfiguration {
  id: string;
  label: string;
  iconURL: string[];
  layoutOptions: { [key: string]: string };
}
