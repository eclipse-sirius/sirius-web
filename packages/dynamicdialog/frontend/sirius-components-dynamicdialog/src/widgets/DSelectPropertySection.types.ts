/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
export interface DSelectStyleProps {
  backgroundColor: string | null;
  foregroundColor: string | null;
  fontSize: number | null;
  italic: boolean | null;
  bold: boolean | null;
  underline: boolean | null;
  strikeThrough: boolean | null;
}

export interface DSelectPropertySectionProps {
  dialogDescriptionId: string;
  widgetDescriptionId: string;
  outputVariableName: string;
  label: string;
  editingContextId: string;
  initialValue: string;
  setValue: any;
  inputVariables: any[] | null;
}

export interface GQLDSelectWidgetQueryVariables {
  editingContextId: string;
  dialogDescriptionId: string;
  widgetDescriptionId: string;
  variables: any[];
}

export interface GQLDSelectWidgetQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  editingContext: GQLEditingContext;
}

export interface GQLEditingContext {
  dynamicDialogQueryObjects: GQLObject[];
}

export interface GQLObject {
  id: string;
  label: string;
}
