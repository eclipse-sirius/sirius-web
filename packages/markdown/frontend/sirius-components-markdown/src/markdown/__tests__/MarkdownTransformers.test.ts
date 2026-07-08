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
  $createListItemNode,
  $createListNode,
  $isListItemNode,
  $isListNode,
  ListItemNode,
  ListNode,
} from '@lexical/list';
import { $convertFromMarkdownString } from '@lexical/markdown';
import { $createParagraphNode, $createTextNode, $getRoot, $isTextNode, createEditor } from 'lexical';
import { expect, test } from 'vitest';
import { registerMarkdownChecklistTransformer } from '../MarkdownChecklistTransformer';
import { markdownTransformers } from '../MarkdownTransformers';

test('should import markdown checklists without changing bullet lists into checklists', () => {
  const editor = createEditor({ nodes: [ListNode, ListItemNode] });

  editor.update(
    () => {
      $convertFromMarkdownString('- Bullet item\n- [ ] Todo\n- [x] Done', markdownTransformers);
    },
    { discrete: true }
  );

  editor.getEditorState().read(() => {
    const lists = $getRoot().getChildren();
    expect(lists).toHaveLength(2);
    expect($isListNode(lists[0])).toBe(true);
    expect($isListNode(lists[1])).toBe(true);

    if ($isListNode(lists[0]) && $isListNode(lists[1])) {
      expect(lists[0].getListType()).toBe('bullet');
      expect(lists[1].getListType()).toBe('check');

      const checklistItems = lists[1].getChildren();
      expect($isListItemNode(checklistItems[0]) && checklistItems[0].getChecked()).toBe(false);
      expect($isListItemNode(checklistItems[1]) && checklistItems[1].getChecked()).toBe(true);
    }
  });
});

test('should turn a corrected checklist marker into a checklist without remounting the editor', () => {
  const editor = createEditor({ nodes: [ListNode, ListItemNode] });
  const unregisterMarkdownChecklistTransformer = registerMarkdownChecklistTransformer(editor);

  editor.update(
    () => {
      $getRoot().append($createParagraphNode().append($createTextNode('-[]something')));
    },
    { discrete: true }
  );
  editor.update(
    () => {
      const paragraph = $getRoot().getFirstChild();
      const textNode = paragraph?.getFirstChild();
      if ($isTextNode(textNode)) {
        textNode.spliceText(1, 0, ' ', true);
      }
    },
    { discrete: true }
  );
  editor.update(
    () => {
      const paragraph = $getRoot().getFirstChild();
      const textNode = paragraph?.getFirstChild();
      if ($isTextNode(textNode)) {
        textNode.spliceText(3, 0, ' ', true);
      }
    },
    { discrete: true }
  );
  editor.update(
    () => {
      const paragraph = $getRoot().getFirstChild();
      const textNode = paragraph?.getFirstChild();
      if ($isTextNode(textNode)) {
        textNode.spliceText(5, 0, ' ', true);
      }
    },
    { discrete: true }
  );

  editor.getEditorState().read(() => {
    const checklist = $getRoot().getFirstChild();
    expect($isListNode(checklist) && checklist.getListType()).toBe('check');

    if ($isListNode(checklist)) {
      const listItem = checklist.getFirstChild();
      expect($isListItemNode(listItem) && listItem.getChecked()).toBe(false);
      expect(listItem?.getTextContent()).toBe('something');
    }
  });

  unregisterMarkdownChecklistTransformer();
});

test('should turn a checklist marker split across text nodes into a checklist', () => {
  const editor = createEditor({ nodes: [ListNode, ListItemNode] });
  const unregisterMarkdownChecklistTransformer = registerMarkdownChecklistTransformer(editor);

  editor.update(
    () => {
      const marker = $createTextNode('- ');
      const checkedState = $createTextNode('[ ]');
      const content = $createTextNode(' something');
      marker.toggleUnmergeable();
      checkedState.toggleUnmergeable();
      content.toggleUnmergeable();
      $getRoot().append($createParagraphNode().append(marker, checkedState, content));
    },
    { discrete: true }
  );

  editor.getEditorState().read(() => {
    const checklist = $getRoot().getFirstChild();
    expect($isListNode(checklist) && checklist.getListType()).toBe('check');
  });

  unregisterMarkdownChecklistTransformer();
});

test('should turn a corrected checklist marker in a bullet list into a checklist', () => {
  const editor = createEditor({ nodes: [ListNode, ListItemNode] });
  const unregisterMarkdownChecklistTransformer = registerMarkdownChecklistTransformer(editor);

  editor.update(
    () => {
      $getRoot().append($createListNode('bullet').append($createListItemNode().append($createTextNode('[]something'))));
    },
    { discrete: true }
  );
  editor.update(
    () => {
      const list = $getRoot().getFirstChild();
      const textNode = list?.getFirstChild()?.getFirstChild();
      if ($isTextNode(textNode)) {
        textNode.spliceText(2, 0, ' ', true);
      }
    },
    { discrete: true }
  );
  editor.update(
    () => {
      const list = $getRoot().getFirstChild();
      const textNode = list?.getFirstChild()?.getFirstChild();
      if ($isTextNode(textNode)) {
        textNode.spliceText(1, 0, ' ', true);
      }
    },
    { discrete: true }
  );

  editor.getEditorState().read(() => {
    const checklist = $getRoot().getFirstChild();
    expect($isListNode(checklist) && checklist.getListType()).toBe('check');

    if ($isListNode(checklist)) {
      const listItem = checklist.getFirstChild();
      expect($isListItemNode(listItem) && listItem.getChecked()).toBe(false);
      expect(listItem?.getTextContent()).toBe('something');
    }
  });

  unregisterMarkdownChecklistTransformer();
});
