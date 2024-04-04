/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import { SynchronizeWithSelectionContext } from './SynchronizedWithSelectionContext';
import { SynchronizedWithSelectionContextValue } from './SynchronizedWithSelectionContext.types';
import { UseSynchronizedWithSelectionValue } from './useSynchronizedWithSelection.types';

export const useSynchronizedWithSelection = (): UseSynchronizedWithSelectionValue => {
  const { isSynchronized, toggleSynchronizeWithSelection } = useContext<SynchronizedWithSelectionContextValue>(
    SynchronizeWithSelectionContext
  );

  return { isSynchronized, toggleSynchronizeWithSelection };
};
