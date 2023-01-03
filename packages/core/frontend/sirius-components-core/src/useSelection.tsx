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

import { useContext } from 'react';
import { SelectionContext } from './contexts/SelectionContext';
import { Selection, SelectionContextValueType } from './contexts/SelectionContext.types';

type UseSelectionType = () => [Selection, (newSelection: Selection) => void];

export const useSelection: UseSelectionType = () => {
  const { selection, setSelection } = useContext<SelectionContextValueType>(SelectionContext);
  return [selection, setSelection];
};
