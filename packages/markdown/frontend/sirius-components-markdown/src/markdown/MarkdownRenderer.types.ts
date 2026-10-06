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

export interface MarkdownRendererProps {
  plainTextByDefault?: boolean;
  value: string;
  placeholder: string;
  readOnly: boolean;
  onBlur: (newValue: string) => void;
}

export interface ContentEditableProps {
  label: string;
  readOnly: boolean;
}

export interface ToolbarPluginProps {
  plainText: boolean;
  onModeChange: () => void;
  readOnly: boolean;
}
