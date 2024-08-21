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

import { ToolVariable } from './DialogContext.types';

export interface DiagramDialogContribution {
  canHandle: (dialogDescriptionId: string) => boolean;
  component: React.ComponentType<DiagramDialogComponentProps>;
}

export interface DiagramDialogComponentProps {
  editingContextId: string;
  dialogDescriptionId: string;
  variables: DiagramDialogVariable[];
  onClose: () => void;
  onFinish: (variables: ToolVariable[]) => void;
}

export interface DiagramDialogVariable {
  name: string;
  value: string;
}
