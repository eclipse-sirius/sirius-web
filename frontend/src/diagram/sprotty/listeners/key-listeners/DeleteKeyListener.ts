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
import { SModelElement, Action, DeleteElementAction } from 'sprotty';
import { KeyListener } from 'diagram/sprotty/listeners/key-listeners/KeyListener';

@injectable()
export class DeleteKeyListener extends KeyListener {
  keyDown(element: SModelElement, event: KeyboardEvent): Action[] {
    if (event.code === 'Delete') {
      const selectedElementIds = element.index
        .all()
        .filter((e) => e['selected'])
        .map((e) => e.id);
      return [new DeleteElementAction([...selectedElementIds])];
    }
    return [];
  }
}
