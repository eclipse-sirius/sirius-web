/*******************************************************************************
 * Copyright (c) 2022, 2026 Obeo.
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
  $handleListInsertParagraph,
  $insertList,
  $isListItemNode,
  $isListNode,
  $removeList,
  INSERT_CHECK_LIST_COMMAND,
  INSERT_ORDERED_LIST_COMMAND,
  INSERT_UNORDERED_LIST_COMMAND,
  REMOVE_LIST_COMMAND,
} from "@lexical/list";
import { useLexicalComposerContext } from "@lexical/react/LexicalComposerContext";
import { mergeRegister } from "@lexical/utils";
import {
  LexicalNode,
  $getSelection,
  COMMAND_PRIORITY_EDITOR,
  COMMAND_PRIORITY_LOW,
  $isRangeSelection,
  INSERT_PARAGRAPH_COMMAND,
  KEY_SPACE_COMMAND,
  createCommand,
  LexicalCommand,
} from "lexical";
import { useEffect } from "react";

const CHECKLIST_MARKER_REGEX = /^\[( |x)\]$/i;

export const SET_CHECK_LIST_ITEM_CHECKED_COMMAND: LexicalCommand<boolean> =
  createCommand("SET_CHECK_LIST_ITEM_CHECKED_COMMAND");

const getListItem = (node: LexicalNode) => {
  if ($isListItemNode(node)) {
    return node;
  }
  const parent = node.getParent();
  if ($isListItemNode(parent)) {
    return parent;
  }
  const grandParent = parent?.getParent();
  return $isListItemNode(grandParent) ? grandParent : null;
};

export function ListPlugin(): null {
  const [editor] = useLexicalComposerContext();

  useEffect(() => {
    return mergeRegister(
      editor.registerCommand(
        KEY_SPACE_COMMAND,
        (event) => {
          const selection = $getSelection();
          if (!$isRangeSelection(selection) || !selection.isCollapsed()) {
            return false;
          }
          const listItem = getListItem(selection.anchor.getNode());
          if (listItem === null) {
            return false;
          }
          const listNode = listItem?.getParent();
          if (
            !$isListNode(listNode) ||
            listNode.getListType() !== "bullet" ||
            listNode.getChildrenSize() !== 1
          ) {
            return false;
          }
          const match = listItem.getTextContent().match(CHECKLIST_MARKER_REGEX);
          if (!match) {
            return false;
          }
          event.preventDefault();
          const checkedMarker = match[1] ?? " ";
          listNode.setListType("check");
          listItem.setChecked(checkedMarker.toLowerCase() === "x");
          listItem.clear();
          listItem.selectStart();
          return true;
        },
        COMMAND_PRIORITY_EDITOR
      ),
      editor.registerCommand(
        SET_CHECK_LIST_ITEM_CHECKED_COMMAND,
        (checked) => {
          const selection = $getSelection();
          if (!$isRangeSelection(selection)) {
            return false;
          }
          const selectedListItem = getListItem(selection.anchor.getNode());
          const listItems = selection
            .getNodes()
            .map(getListItem)
            .filter($isListItemNode);
          if (
            $isListItemNode(selectedListItem) &&
            !listItems.includes(selectedListItem)
          ) {
            listItems.push(selectedListItem);
          }
          if (listItems.length === 0) {
            return false;
          }
          listItems.forEach((listItem) => {
            const listNode = listItem.getParent();
            if ($isListNode(listNode) && listNode.getListType() === "check") {
              listItem.setChecked(checked);
            }
          });
          return true;
        },
        COMMAND_PRIORITY_LOW
      ),
      editor.registerCommand(
        INSERT_CHECK_LIST_COMMAND,
        () => {
          $insertList("check");
          return true;
        },
        COMMAND_PRIORITY_LOW
      ),
      editor.registerCommand(
        INSERT_ORDERED_LIST_COMMAND,
        () => {
          $insertList("number");
          return true;
        },
        COMMAND_PRIORITY_LOW
      ),
      editor.registerCommand(
        INSERT_UNORDERED_LIST_COMMAND,
        () => {
          $insertList("bullet");
          return true;
        },
        COMMAND_PRIORITY_LOW
      ),
      editor.registerCommand(
        REMOVE_LIST_COMMAND,
        () => {
          $removeList();
          return true;
        },
        COMMAND_PRIORITY_LOW
      ),
      editor.registerCommand(
        INSERT_PARAGRAPH_COMMAND,
        () => {
          const hasHandledInsertParagraph = $handleListInsertParagraph();
          return hasHandledInsertParagraph;
        },
        COMMAND_PRIORITY_LOW
      )
    );
  }, [editor]);

  return null;
}
