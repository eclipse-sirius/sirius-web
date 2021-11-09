/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
import { Selection } from 'workbench/Workbench.types';

export interface TreeItemContextMenuContributionProps {
  canHandle: (item: any) => boolean;
  component: (props: TreeItemContextMenuComponentProps) => JSX.Element;
}

export interface TreeItemContextMenuComponentProps {
  editingContextId: string;
  item: any;
  readOnly: boolean;
  selection: Selection;
  setSelection: (selection: Selection) => void;
  expandItem: () => void;
  onClose: () => void;
  key: string;
}
