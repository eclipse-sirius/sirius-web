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
export const DEFAULT_TOOL_LIST_ITEMS = [
  {
    id: 'semantic-delete',
    label: 'Delete',
    iconURL: ['/api/images/diagram-images/semanticDelete.svg'],
    __typename: 'SingleClickOnDiagramElementTool',
  },
  {
    id: 'rename',
    label: 'Rename',
    iconURL: ['/api/images/diagram-images/edit.svg'],
    __typename: 'SingleClickOnDiagramElementTool',
  },
];
