/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
import { TreeItemContextMenuComponentProps } from '@eclipse-sirius/sirius-components-trees';
import { Fragment, forwardRef } from 'react';

export const DiagramTreeItemContextMenuContribution = forwardRef(
  ({}: TreeItemContextMenuComponentProps, _: React.ForwardedRef<HTMLLIElement>) => {
    return <Fragment key="diagram-tree-item-context-menu-contribution"></Fragment>;
  }
);
