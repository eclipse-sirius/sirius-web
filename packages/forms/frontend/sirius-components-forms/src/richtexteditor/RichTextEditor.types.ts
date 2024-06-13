/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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

import { ReactNode } from 'react';

export interface RichTextEditorProps {
  value: string;
  placeholder: string;
  onBlur: (newValue: string) => void;
  readOnly: boolean;
}

export interface ContentEditableProps {
  readOnly: boolean;
}

export interface ToolbarPluginProps {
  readOnly: boolean;
}

export interface UpdateValuePluginProps {
  markdownText: string;
}

export interface OnBlurPluginProps {
  onBlur: (markdownText: string) => void;
  children: ReactNode;
}
