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

import { MutableRefObject } from 'react';

export interface FilterBarContextValue {
  isOpen: boolean;
  filterBarText: string | null;
  filterBarTreeFiltering: boolean;
  setFilterBarText: (filterBarText: string) => void;
  setFilterBarTreeFiltering: (enabled: boolean) => void;
  toggleFilter: () => void;
  onClose: () => void;
}

export interface FilterBarContextStates {
  isOpen: boolean;
  filterBarText: string | null;
  filterBarTreeFiltering: boolean;
}

export interface FilterBarContextProps {
  containerRef: MutableRefObject<HTMLDivElement>;
  children: React.ReactNode;
}
