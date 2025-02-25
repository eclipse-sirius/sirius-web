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

import { GQLMessage } from '@eclipse-sirius/sirius-components-core';

export interface UseEvaluateExpressionResult {
  evaluateExpression: (editingContextId: string, expression: string) => void;
  loading: boolean;
  result: GQLEvaluateExpressionSuccessPayload | null;
}

export interface GQLEvaluateExpressionMutationData {
  evaluateExpression: GQLEvaluateExpressionPayload;
}

export interface GQLEvaluateExpressionPayload {
  __typename: string;
}

export interface GQLEvaluateExpressionSuccessPayload extends GQLEvaluateExpressionPayload {
  result: GQLExpressionResult;
}

export interface GQLExpressionResult {
  __typename: string;
}

export interface GQLObjectExpressionResult extends GQLExpressionResult {
  objectValue: GQLObject;
}

export interface GQLObject {
  id: string;
  kind: string;
  label: string;
  iconURLs: string[];
}

export interface GQLObjectsExpressionResult extends GQLExpressionResult {
  objectsValue: GQLObject[];
}

export interface GQLBooleanExpressionResult extends GQLExpressionResult {
  booleanValue: boolean;
}

export interface GQLIntExpressionResult extends GQLExpressionResult {
  intValue: number;
}

export interface GQLStringsExpressionResult extends GQLExpressionResult {
  stringsValue: string[];
}

export interface GQLStringExpressionResult extends GQLExpressionResult {
  stringValue: string;
}

export interface GQLVoidExpressionResult extends GQLExpressionResult {}

export interface GQLErrorPayload extends GQLEvaluateExpressionPayload {
  messages: GQLMessage[];
}

export interface GQLEvaluateExpressionMutationVariables {
  input: GQLEvaluateExpressionInput;
}

export interface GQLEvaluateExpressionInput {
  id: string;
  editingContextId: string;
  expression: string;
  selectedObjectIds: string[];
}
