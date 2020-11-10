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
import { SET_CONTEXTUAL_PALETTE_ACTION } from 'diagram/sprotty/Actions';

@injectable()
export class ContextualPaletteKeyListener extends KeyListener {
  keyDown(element, event) {
    if (event.code === 'Escape') {
      return [
        {
          kind: SET_CONTEXTUAL_PALETTE_ACTION,
        },
      ];
    }
    return [];
  }
}
