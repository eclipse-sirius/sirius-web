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

import { GQLEvaluateExpressionSuccessPayload, GQLExpressionResult } from './useEvaluateExpression.types';

export interface ExpressionAreaProps {
  editingContextId: string;
  onEvaluateExpression: (expression: string) => void;
  disabled: boolean;
}

export interface ResultAreaProps {
  loading: boolean;
  payload: GQLEvaluateExpressionSuccessPayload | null;
  height: number | null;
  width: number | null;
}

export interface ExpressionResultViewerProps {
  result: GQLExpressionResult;
  height: number | null;
  width: number | null;
}

export interface ExportResultButtonProps {
  objectIds: string[];
}
