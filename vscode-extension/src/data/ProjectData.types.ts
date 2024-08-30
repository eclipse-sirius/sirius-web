/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

export interface GQLGetRepresentationMetadataResponse {
  data: GQLGetRepresentationMetadataData;
}

export interface GQLGetRepresentationMetadataData {
  viewer: GQLGetRepresentationMetadataViewer;
}

export interface GQLGetRepresentationMetadataViewer {
  editingContext: GQLGetRepresentationMetadataEditingContext;
}

export interface GQLGetRepresentationMetadataEditingContext {
  representations: GQLGetRepresentationMetadataEditingContextConnexion;
}

export interface GQLGetRepresentationMetadataEditingContextConnexion {
  edges: GQLGetRepresentationMetadataEditingContextEdge[];
}

export interface GQLGetRepresentationMetadataEditingContextEdge {
  node: GQLGetRepresentationMetadataRepresentationMetadata;
}

export interface GQLGetRepresentationMetadataRepresentationMetadata {
  id: string;
  label: string;
  kind: string;
}

export interface GQLStyledString {
  styledStringFragments: GQLStyledStringFragment[];
}
export interface GQLStyledStringFragment {
  text: string;
  styledStringFragmentStyle: GQLStyledStringFragmentStyle;
}
export interface GQLStyledStringFragmentStyle {
  font: string;
  backgroundColor: string;
  foregroundColor: string;
  isStruckOut: boolean;
  strikeoutColor: string;
  underlineColor: string;
  borderColor: string;
  borderStyle: GQLBorderStyle;
  underlineStyle: GQLUnderLineStyle;
}
export type GQLUnderLineStyle = 'NONE' | 'SOLID' | 'DOUBLE' | 'DOTTED' | 'DASHED' | 'WAVY';

export type GQLBorderStyle =
  | 'NONE'
  | 'HIDDEN'
  | 'DOTTED'
  | 'DASHED'
  | 'SOLID'
  | 'DOUBLE'
  | 'GROOVE'
  | 'RIDGE'
  | 'INSET'
  | 'OUTSET';
