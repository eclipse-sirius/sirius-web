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
import { ModelSource, Action, TYPES, IModelFactory } from 'sprotty';
import { inject, injectable } from 'inversify';

/**
 * A diagram model source.
 * This class can be used as a facade over the action-based API of sprotty.
 * @hmarchadour
 */
@injectable()
export class DiagramModelSource extends ModelSource {
  @inject(TYPES.IModelFactory) modelFactory: IModelFactory;

  currentRoot;

  initialize(registry) {}

  handle(action: Action): void {}

  commitModel(newRoot) {
    const previousRoot = this.currentRoot;
    this.currentRoot = newRoot;
    return previousRoot;
  }
}
