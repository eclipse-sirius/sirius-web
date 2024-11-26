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
import { MRT_VisibilityState } from 'material-react-table';

export interface UseTableColumnVisibilityValue {
  columnVisibility: MRT_VisibilityState;
  setColumnVisibility: (
    columnVisibility: MRT_VisibilityState | ((prevState: MRT_VisibilityState) => MRT_VisibilityState)
  ) => void;
}

export interface GQLChangeColumnVisibilityInput {
  id: string;
  editingContextId: string;
  representationId: string;
  tableId: string;
  columnsVisibility: GQLColumnVisibility[];
}

export interface GQLColumnVisibility {
  columnId: string;
  visible: boolean;
}

export interface GQLChangeColumnVisibilityVariables {
  input: GQLChangeColumnVisibilityInput;
}

export interface GQLChangeColumnVisibilityData {
  changeTableColumnVisibility: GQLChangeTableColumnVisibilityPayload;
}

export type GQLChangeTableColumnVisibilityPayload = GQLErrorPayload | GQLSuccessPayload;
