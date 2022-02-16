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
import { decorate, inject } from 'inversify';
import { CommandExecutionContext, SModelElement, SModelRoot, TYPES } from 'sprotty';
import { generateRequestId, RequestAction, ResponseAction } from 'sprotty-protocol';
import { ModelRequestCommand } from 'sprotty/lib/base/commands/request-command';
import { Edge, Node } from '../Diagram.types';

export interface IsSiriusModelElementAction extends RequestAction<IsSiriusModelElementResult> {
  kind: typeof IsSiriusModelElementAction.KIND;
  elementId: string;
}

export namespace IsSiriusModelElementAction {
  export const KIND = 'isSiriusModelElement';

  export const create = (elementId: string): IsSiriusModelElementAction => {
    return {
      kind: KIND,
      elementId,
      requestId: generateRequestId(),
    };
  };
}

export interface IsSiriusModelElementResult extends ResponseAction {
  kind: typeof IsSiriusModelElementResult.KIND;
  isSiriusModelElement: boolean;
}

export namespace IsSiriusModelElementResult {
  export const KIND = 'isSiriusModelElementResult';

  export const create = (isSiriusModelElement: boolean, requestId: string): IsSiriusModelElementResult => {
    return {
      kind: KIND,
      isSiriusModelElement,
      responseId: requestId,
    };
  };
}

export class IsSiriusModelElementCommand extends ModelRequestCommand {
  static readonly KIND = IsSiriusModelElementAction.KIND;

  constructor(protected readonly action: IsSiriusModelElementAction) {
    super();
  }

  protected retrieveResult(context: CommandExecutionContext): ResponseAction {
    const elem = context.root;
    const isSiriusModelElement = this.isSiriusModelElement(elem, this.action.elementId);
    return IsSiriusModelElementResult.create(isSiriusModelElement, this.action.requestId);
  }

  private isSiriusModelElement(root: SModelRoot, elementId: string): boolean {
    const element = root.index.getById(elementId);
    return isSiriusModelElement(element);
  }
}
decorate(inject(TYPES.Action) as ParameterDecorator, IsSiriusModelElementCommand, 0);

export const isSiriusModelElement = (element: SModelElement): boolean => {
  return element instanceof Node || element instanceof Edge;
};
