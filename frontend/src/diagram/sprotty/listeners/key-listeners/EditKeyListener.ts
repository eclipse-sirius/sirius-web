/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
import { injectable } from 'inversify';
import { KeyListener } from 'diagram/sprotty/listeners/key-listeners/KeyListener';
import { EditLabelAction } from 'sprotty';
// The list of characters that will enable the direct edit mechanism.
const directEditActivationValidCharacters = /[\w&é§èàùçÔØÁÛÊË"«»’”„´$¥€£\\¿?!=+-,;:%/{}[\]–#@*.]/;

@injectable()
export class EditKeyListener extends KeyListener {
  keyDown(element, event) {
    if (event.code === 'F2') {
      return [{ kind: EditLabelAction.KIND, element }];
    } else {
      /*If a modifier key is hit alone, do nothing*/
      if ((event.altKey || event.shiftKey) && event.getModifierState(event.key)) {
        return [];
      }
      const validFirstInputChar =
        !event.metaKey &&
        !event.ctrlKey &&
        event.key.length === 1 &&
        directEditActivationValidCharacters.test(event.key);
      if (validFirstInputChar) {
        return [{ kind: EditLabelAction.KIND, element }];
      }
    }
    return [];
  }
}
