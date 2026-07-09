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
 *     tbezierslafosse - support GFM markdown table rendering
 *******************************************************************************/
import {
  $convertFromMarkdownString,
  $convertToMarkdownString,
  TRANSFORMERS,
  Transformer,
} from "@lexical/markdown";
import { GFM_TABLE } from "./MarkdownTableTransformer";

export const MARKDOWN_TRANSFORMERS: Transformer[] = [
  GFM_TABLE,
  ...TRANSFORMERS,
];

export const convertFromMarkdownString = (markdown: string): void => {
  $convertFromMarkdownString(markdown, MARKDOWN_TRANSFORMERS);
};

export const convertToMarkdownString = (): string =>
  $convertToMarkdownString(MARKDOWN_TRANSFORMERS);
