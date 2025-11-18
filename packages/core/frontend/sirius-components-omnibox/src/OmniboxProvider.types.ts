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

import { RefObject } from 'react';
import { OmniboxCommand, OmniboxMode } from './Omnibox.types';

export interface OmniboxProviderProps {
  open: boolean;
  onOpen: () => void;
  onClose: () => void;
  loading: boolean;
  commands: OmniboxCommand[] | null;
  onQuery: (query: string, mode: OmniboxMode) => void;
  onCommandClick(command: OmniboxCommand, mode: OmniboxMode, input: RefObject<HTMLInputElement>);
  children: React.ReactNode;
}
