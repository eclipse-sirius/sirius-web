/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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
import { GQLErrorPayload, GQLSuccessPayload } from '@eclipse-sirius/sirius-components-core';

export type GlobalFilterSetter = (columnVisibility: string | ((prevState: string) => string)) => void;

export interface UseGlobalFilterValue {
  globalFilter: string | undefined;
  setGlobalFilter: GlobalFilterSetter | undefined;
}

export interface GQLChangeGlobalFilterValueInput {
  id: string;
  editingContextId: string;
  representationId: string;
  tableId: string;
  globalFilterValue: string;
}

export interface GQLChangeGlobalFilterValueVariables {
  input: GQLChangeGlobalFilterValueInput;
}

export interface GQLChangeGlobalFilterValueData {
  changeGlobalFilterValue: GQLChangeGlobalFilterValuePayload;
}

export type GQLChangeGlobalFilterValuePayload = GQLErrorPayload | GQLSuccessPayload;
