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
import {
  $convertFromMarkdownString,
  $convertToMarkdownString,
  CHECK_LIST,
  TRANSFORMERS,
  Transformer,
} from "@lexical/markdown";

export const MARKDOWN_TRANSFORMERS: Transformer[] = [
  CHECK_LIST,
  ...TRANSFORMERS,
];

export const convertFromMarkdownString = (markdown: string): void => {
  $convertFromMarkdownString(markdown, MARKDOWN_TRANSFORMERS);
};

export const convertToMarkdownString = (): string =>
  $convertToMarkdownString(MARKDOWN_TRANSFORMERS);
