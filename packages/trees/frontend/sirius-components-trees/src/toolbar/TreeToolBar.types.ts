/*******************************************************************************
 * Copyright (c) 2023 Obeo and others.
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
import { TreeToolBarContributionComponentProps } from './TreeToolBarContribution.types';

export interface TreeToolBarProps {
  editingContextId: string;
  treeToolBarContributionComponents: ((props: TreeToolBarContributionComponentProps) => JSX.Element)[];
  readOnly: boolean;
}

export interface TreeToolBarState {
  modalOpen: Modal | null;
}

type Modal = 'NewDocument' | 'UploadDocument';
