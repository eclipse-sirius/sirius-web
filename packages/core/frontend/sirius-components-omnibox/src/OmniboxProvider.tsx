/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import { useEffect } from 'react';
import { Omnibox } from './Omnibox';
import { OmniboxContext } from './OmniboxContext';
import { OmniboxContextValue } from './OmniboxContext.types';
import { OmniboxProviderProps } from './OmniboxProvider.types';

export const OmniboxProvider = ({
  open,
  onOpen,
  onClose,
  loading,
  commands,
  onQuery,
  onCommandClick,
  children,
}: OmniboxProviderProps) => {
  useEffect(() => {
    const keyDownEventListener = (event: KeyboardEvent) => {
      if (event.key === 'k' && (event.ctrlKey || event.metaKey)) {
        event.preventDefault();
        if (open) {
          onClose();
        } else {
          onOpen();
        }
      } else if (event.key === 'Esc') {
        event.preventDefault();
        onClose();
      }
    };

    document.addEventListener('keydown', keyDownEventListener);
    return () => document.removeEventListener('keydown', keyDownEventListener);
  }, [open]);

  const omniboxContextValue: OmniboxContextValue = {
    openOmnibox: onOpen,
  };

  return (
    <OmniboxContext.Provider value={omniboxContextValue}>
      {children}
      {open ? (
        <Omnibox
          open={open}
          loading={loading}
          commands={commands}
          onQuery={onQuery}
          onCommandClick={onCommandClick}
          onClose={onClose}
        />
      ) : null}
    </OmniboxContext.Provider>
  );
};
