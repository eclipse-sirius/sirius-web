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
import { injectable, inject } from 'inversify';
import { MouseListener as SprottyMouseListener, SModelElement, SChildElement } from 'sprotty';
import { SIRIUS_TYPES } from 'diagram/sprotty/Types';
import { IState } from 'diagram/sprotty/IState';

@injectable()
export class MouseListener extends SprottyMouseListener {
  @inject(SIRIUS_TYPES.STATE) state: IState;

  findElementWithTarget(element: SModelElement) {
    if (element['targetObjectId']) {
      return element;
    } else if (element instanceof SChildElement) {
      return this.findElementWithTarget(element.parent);
    }
    // Otherwise, use the diagram as element with target.
    return element.root;
  }
}
