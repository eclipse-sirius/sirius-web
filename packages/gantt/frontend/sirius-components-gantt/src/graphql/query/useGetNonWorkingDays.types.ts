/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

export interface UseGetNonWorkingDaysProps {
  editingContextId: string;
  ganttDescriptionId: string;
}

export interface UseGetNonWorkingDaysValue {
  holidays: string[];
  weekends: string[];
  loading: boolean;
}

export interface UseGetNonWorkingDaysVariables {
  editingContextId: string;
  representationId: string;
}

export interface GetNonWorkingDaysData {
  viewer: GetNonWorkingDaysViewer;
}

export interface GetNonWorkingDaysViewer {
  editingContext: GetNonWorkingDaysEditingContext | null;
}

export interface GetNonWorkingDaysEditingContext {
  getNonWorkingDays: UseGetNonWorkingDaysValue | null;
}
