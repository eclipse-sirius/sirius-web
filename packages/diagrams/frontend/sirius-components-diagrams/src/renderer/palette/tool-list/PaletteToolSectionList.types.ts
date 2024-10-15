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
import { GQLTool, GQLToolSection } from '../Palette.types';

export interface PaletteToolSectionListProps {
  onToolClick: (event: React.MouseEvent<HTMLDivElement, MouseEvent>, tool: GQLTool) => void;
  onBackToMainList: (event: React.MouseEvent<HTMLDivElement, MouseEvent>) => void;
  toolSection: GQLToolSection;
  tooltipDelay?: number;
  tooltipPlacement?:
    | 'bottom-end'
    | 'bottom-start'
    | 'bottom'
    | 'left-end'
    | 'left-start'
    | 'left'
    | 'right-end'
    | 'right-start'
    | 'right'
    | 'top-end'
    | 'top-start'
    | 'top';
}
