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

import { useEffect, useState } from 'react';

export const useDebounce = <T>(value: T, delay: number): T | null => {
  const [state, setState] = useState<T | null>(null);

  useEffect(() => {
    const timeout = setTimeout(() => setState(value), delay);
    return () => clearTimeout(timeout);
  }, [value, delay]);

  return state;
};
