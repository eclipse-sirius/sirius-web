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
import { $createListItemNode, $createListNode, $isListItemNode, $isListNode } from '@lexical/list';
import {
  $createTextNode,
  $getSelection,
  $isParagraphNode,
  $isRangeSelection,
  $isTextNode,
  LexicalEditor,
  TextNode,
} from 'lexical';

const CHECKLIST_MARKDOWN_REGEX = /^[-*+]\s\[( |x)\](?:\s(.*))?$/i;
const CHECKLIST_LIST_ITEM_REGEX = /^\[( |x)\](?:\s(.*))?$/i;

export const registerMarkdownChecklistTransformer = (editor: LexicalEditor): (() => void) => {
  return editor.registerNodeTransform(TextNode, (textNode) => {
    const parent = textNode.getParent();
    const selection = $getSelection();
    if ($isParagraphNode(parent) && parent.getChildren().every($isTextNode)) {
      const match = parent.getTextContent().match(CHECKLIST_MARKDOWN_REGEX);
      if (match === null) {
        return;
      }

      const selectionIsInChecklist =
        $isRangeSelection(selection) && selection.isCollapsed() && selection.anchor.getNode().getParent()?.is(parent);
      const checkedMarker = match[1] ?? ' ';
      const listItem = $createListItemNode(checkedMarker.toLowerCase() === 'x');
      const content = match[2] ?? '';
      if (content.length > 0) {
        listItem.append($createTextNode(content));
      }
      parent.replace($createListNode('check').append(listItem));
      if (selectionIsInChecklist) {
        listItem.selectStart();
      }
      return;
    }

    if ($isListItemNode(parent) && parent.getChildren().every($isTextNode)) {
      const list = parent.getParent();
      const match = parent.getTextContent().match(CHECKLIST_LIST_ITEM_REGEX);
      if (!$isListNode(list) || list.getListType() !== 'bullet' || list.getChildrenSize() !== 1 || match === null) {
        return;
      }

      const selectionIsInChecklist =
        $isRangeSelection(selection) && selection.isCollapsed() && selection.anchor.getNode().getParent()?.is(parent);
      const checkedMarker = match[1] ?? ' ';
      parent.clear();
      parent.setChecked(checkedMarker.toLowerCase() === 'x');
      const content = match[2] ?? '';
      if (content.length > 0) {
        parent.append($createTextNode(content));
      }
      list.setListType('check');
      if (selectionIsInChecklist) {
        parent.selectStart();
      }
    }
  });
};
