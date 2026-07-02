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

import { GQLPalette, GQLTool } from '../Palette.types';

export interface useToolValue {
  getRenderedTool: (
    palette: GQLPalette,
    representationElementIds: string[],
    representationKind: string,
    onToolClick: (tool: GQLTool) => void,
    toolId: string | null
  ) => JSX.Element | null;
  getRenderedGQLTool: (
    palette: GQLPalette,
    tool: GQLTool,
    representationElementIds: string[],
    representationKind: string,
    onToolClick: (tool: GQLTool) => void
  ) => JSX.Element;
}
