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
import { BringToFrontCommand, CommandExecutionContext, SChildElement, SModelRoot, SRoutableElement } from 'sprotty';

export class SiriusBringToFrontCommand extends BringToFrontCommand {
  override execute(context: CommandExecutionContext): SModelRoot {
    const model = context.root;
    this.action.elementIDs.forEach((id) => {
      const element = model.index.getById(id);
      if (element instanceof SRoutableElement) {
        if (element.source) {
          this.addToSelection(element.source);

          var sourceChildren = this.getAllChildren(element.source);
          sourceChildren.forEach((child) => this.includeConnectedEdges(child));
        }

        if (element.target) {
          this.addToSelection(element.target);

          var targetChildren = this.getAllChildren(element.target);
          targetChildren.forEach((child) => this.includeConnectedEdges(child));
        }
      }
      if (element instanceof SChildElement) {
        this.addToSelection(element);
      }
      this.includeConnectedEdges(element);
    });
    return this.redo(context);
  }

  private getAllChildren(element: SChildElement): SChildElement[] {
    let children: SChildElement[] = [element];

    element.children.forEach((child) => {
      children.concat(this.getAllChildren(child));
    });

    return children;
  }
}
