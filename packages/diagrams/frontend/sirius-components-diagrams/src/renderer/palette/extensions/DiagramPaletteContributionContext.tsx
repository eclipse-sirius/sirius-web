/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import React from 'react';
import { DiagramPaletteContributionContextValue } from './DiagramPaletteContributionContext.types';

const value: DiagramPaletteContributionContextValue = {
  toolXYPosition: { x: 0, y: 0 },
};

export const DiagramPaletteContributionContext = React.createContext<DiagramPaletteContributionContextValue>(value);
