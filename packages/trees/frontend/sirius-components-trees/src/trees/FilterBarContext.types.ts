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

export interface FilterBarContextValue {
  isOpen: boolean;
  filterBarText: string | null;
  filterBarTreeFiltering: boolean;
  setFilterBarText: (filterBarText: string) => void;
  setFilterBarTreeFiltering: (enabled: boolean) => void;
  onKeyDown: (event: React.KeyboardEvent<Element>) => void;
  toggleFilter: () => void;
  onClose: () => void;
}

export interface FilterBarContextStates {
  isOpen: boolean;
  filterBarText: string | null;
  filterBarTreeFiltering: boolean;
}

export interface FilterBarContextProps {
  children: React.ReactNode;
}
