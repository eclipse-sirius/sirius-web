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

import { GQLStyleCustomization } from './useProjectStyleCustomizations.types';

export interface ProjectStyleCustomizationsTableProps {
  loading: boolean;
  styleCustomizations: GQLStyleCustomization[];
  rowCount: number;
  hasPreviousPage: boolean;
  hasNextPage: boolean;
  onPreviousPage: () => void;
  onNextPage: () => void;
  pageSize: number;
  onPageSizeChange: (pageSize: number) => void;
}

export interface ProjectStyleCustomization {
  id: string;
  name: string;
  description: string;
  enabled: boolean;
}
