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

export interface FilterBarProps {
  onTextChange: React.ChangeEventHandler<HTMLTextAreaElement | HTMLInputElement>;
  onTextClear: () => void;
  onFilterButtonClick: (enabled: boolean) => void;
  onClose: () => void;
  text: string | null;
  options: FilterBarOptions | undefined;
}

export interface FilterBarState {
  filterEnabled: boolean;
}

export interface FilterBarOptions {
  textFieldVariant: 'outlined' | 'standard';
  searchIcon: boolean;
  clearTextButton: boolean;
  filterButton: boolean;
  closeButton: boolean;
  filterEnabled: boolean;
  filterBarDisplayByDefault: boolean;
}
