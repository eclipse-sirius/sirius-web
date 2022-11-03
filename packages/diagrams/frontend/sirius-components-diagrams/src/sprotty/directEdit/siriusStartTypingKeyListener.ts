/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

import { EditLabelAction, isSelectable, KeyListener, SModelElement } from 'sprotty';
import { Action } from 'sprotty-protocol';
import { matchesKeystroke } from 'sprotty/lib/utils/keyboard';
import { Label } from '../Diagram.types';

export class StartTypingKeyListener extends KeyListener {
  // The list of characters that will enable the direct edit mechanism.
  private readonly directEditActivationValidCharacters = /[\w&é§èàùçÔØÁÛÊË"«»’”„´$¥€£\\¿?!=+-,;:%/{}[\]–#@*.]/;
  override keyDown(element: SModelElement, event: KeyboardEvent): Action[] {
    if (
      !!event.getModifierState(event.key) &&
      !matchesKeystroke(event, 'F2') && // Handled by EditLabelKeyListener
      !matchesKeystroke(event, 'Delete') &&
      !matchesKeystroke(event, 'Escape')
    ) {
      return [];
    }
    const validFirstInputChar = event.key.length === 1 && this.directEditActivationValidCharacters.test(event.key);
    if (validFirstInputChar) {
      const actions: Action[] = [];
      const selectedItems = element.index.all().filter((e) => isSelectable(e) && e.selected);
      selectedItems.forEach((item: any) => {
        /**
         * We need to update the graph index of the item label but it appears the graph index of the item.editableLabel and
         * the graph index of item.children[0] are different (item.children[0] graph index is the good one). We have that
         * behavior because when the sprotty diagram is created in `DiagramServer`, we convert the diagram from graphql that
         * initializes an index. Then, we create the sprotty model that recreate an index for all chilren but all editable
         * label kept the old index.
         */
        const label = item.editableLabel;
        if (label) {
          const editableLabel = item.children.find((c) => c instanceof Label);
          editableLabel.initialText = event.key;
          editableLabel.preSelect = false;
        }
        actions.push(EditLabelAction.create(label.id));
      });
      return actions;
    }

    return [];
  }
}
