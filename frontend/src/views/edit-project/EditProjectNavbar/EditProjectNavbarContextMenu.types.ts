/*******************************************************************************
 * Copyright (c) 2021 Obeo.
 * This program and the accompanying materials
 * are made available under the erms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/

export interface EditProjectNavbarContextMenuProps {
  x: number;
  y: number;
  projectId: string;
  onCreateDocument: () => void;
  onUploadDocument: () => void;
  onRename: () => void;
  onDelete: () => void;
  onClose: () => void;
}
