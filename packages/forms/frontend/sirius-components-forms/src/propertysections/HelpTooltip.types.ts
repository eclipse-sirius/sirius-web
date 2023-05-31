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

export interface HelpTooltipProps {
  editingContextId: string;
  formId: string;
  widgetId: string;
  children?: React.ReactElement<any, any>;
}

export interface HelpTooltipState {
  open: boolean;
  content: JSX.Element;
}

export interface GQLHelpTextQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  editingContext: GQLEditingContext;
}
export interface GQLEditingContext {
  representation: GQLRepresentation;
}

export interface GQLRepresentation {
  description: GQLRepresentationDescription;
}

export interface GQLRepresentationDescription {
  __typename: string;
  helpText: string;
}

export interface GQLHelpTextQueryVariables {}
