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

import { GQLToolVariable } from '../renderer/palette/Palette.types';

export interface DiagramDialogContribution {
  canHandle: (dialogDescriptionId: string) => boolean;
  component: React.ComponentType<DiagramDialogComponentProps>;
}

export interface DiagramDialogComponentProps {
  editingContextId: string;
  dialogDescriptionId: string;
  targetObjectId: string;
  onClose: () => void;
  onFinish: (variables: GQLToolVariable[]) => void;
}
