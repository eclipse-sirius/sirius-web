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

import { useEffect, useState } from 'react';
import { Omnibox } from './Omnibox';
import { OmniboxContext } from './OmniboxContext';
import { OmniboxContextValue } from './OmniboxContext.types';
import { OmniboxProviderProps, OmniboxProviderState } from './OmniboxProvider.types';

export const OmniboxProvider = ({ initialContextEntries = [], children }: OmniboxProviderProps) => {
  const [state, setState] = useState<OmniboxProviderState>({
    open: false,
  });

  const openOmnibox = () => setState((prevState) => ({ ...prevState, open: true }));
  const closeOmnibox = () => setState((prevState) => ({ ...prevState, open: false }));

  useEffect(() => {
    const keyDownEventListener = (event: KeyboardEvent) => {
      if (event.key === 'k' && (event.ctrlKey || event.metaKey)) {
        event.preventDefault();
        setState((prevState) => ({ ...prevState, open: !prevState.open }));
      } else if (event.key === 'Esc') {
        event.preventDefault();
        setState((prevState) => ({ ...prevState, open: false }));
      }
    };

    document.addEventListener('keydown', keyDownEventListener);
    return () => document.removeEventListener('keydown', keyDownEventListener);
  }, []);

  const omniboxContextValue: OmniboxContextValue = {
    openOmnibox,
  };

  return (
    <OmniboxContext.Provider value={omniboxContextValue}>
      {children}
      {state.open ? (
        <Omnibox open={state.open} initialContextEntries={initialContextEntries} onClose={closeOmnibox} />
      ) : null}
    </OmniboxContext.Provider>
  );
};
