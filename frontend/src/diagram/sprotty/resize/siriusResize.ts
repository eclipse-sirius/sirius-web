/*******************************************************************************
 * Copyright (c) 2021, 2022 THALES GLOBAL SERVICES.
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
import {
  Command,
  CommandExecutionContext,
  CommandReturn,
  SModelElement,
  SNode,
  SPort,
  SShapeElement,
  TYPES,
} from 'sprotty';
import { Action, Bounds, Dimension, Point } from 'sprotty-protocol';
import { snapToRectangle } from '../utils/geometry';
import { RectangleSide } from '../utils/geometry.types';

export class ResizeAction implements Action {
  kind = SiriusResizeCommand.KIND;
  constructor(public readonly resize: ElementResize, public readonly finished: boolean = false) {}
}
export interface ElementResize {
  elementId: string;
  newPosition: Point;
  newSize: Dimension;
}
export class SiriusResizeCommand extends Command {
  static readonly KIND = 'resize';

  constructor(protected readonly action: ResizeAction) {
    super();
  }

  execute(context: CommandExecutionContext): CommandReturn {
    const index = context.root.index;
    const elementResize: ElementResize = this.action.resize;
    const element: SModelElement = index.getById(elementResize.elementId);
    if (this.isNode(element) || this.isPort(element)) {
      const positionDelta: Point = {
        x: elementResize.newPosition.x - element.position.x,
        y: elementResize.newPosition.y - element.position.y,
      };
      const sizeDelta: Point = {
        x: elementResize.newSize.width - element.size.width,
        y: elementResize.newSize.height - element.size.height,
      };
      let validPositionDelta = positionDelta;
      let validSize = elementResize.newSize;
      let validPosition = elementResize.newPosition;

      // TODO this code is just written and has bugs
      if (this.isPort(element)) {
        const parent = element.parent as SShapeElement;
        const parentWidth: number = parent.bounds.width;
        const parentHeight: number = parent.bounds.height;

        const currentSPortCenter: Point = Bounds.center(element.bounds);
        const { side } = snapToRectangle(currentSPortCenter, {
          x: 0, // because newSportPosition has coordinates relative to parent
          y: 0,
          width: parentWidth,
          height: parentHeight,
        });
        if (side === RectangleSide.east || side === RectangleSide.west) {
          if (validPosition.y + validSize.height > parentHeight) {
            validSize = { width: validPosition.x, height: parentHeight - validPosition.y };
          } else if (validPosition.y < 0) {
            validPosition = { x: validPosition.x, y: 0 };
            validSize = { width: validSize.width, height: element.bounds.y - element.bounds.height };
          }
        } else if (side === RectangleSide.north || side === RectangleSide.south) {
          if (validPosition.x + validSize.width > parentWidth) {
            validSize = { width: parentWidth - validPosition.x, height: validSize.height };
          } else if (validPosition.x < 0) {
            validPosition = { x: 0, y: validPosition.y };
            validSize = { width: element.bounds.x - element.bounds.width, height: validSize.height };
          }
        }
      }

      if (this.isNode(element)) {
        //If the element size is decreased, we compute a valid size and position to make sure that all children are still within the new bounds.
        if (
          element.children.filter((child) => this.isNode(child)).length > 0 &&
          this.sizeDecreased(sizeDelta, positionDelta)
        ) {
          const [minTopLeft, maxBottomRight] = this.getChildrenLimits(element);
          //A positive position delta means that the resize from the NW has been done toward the center of the figure (the size is decreased)
          if (positionDelta.x > 0 || positionDelta.y > 0) {
            const previousValidPositionDelta = validPositionDelta;
            validPositionDelta = this.computeValidPositionDelta(validPositionDelta, minTopLeft);
            validPosition = {
              x: element.position.x + validPositionDelta.x,
              y: element.position.y + validPositionDelta.y,
            };
            //If the valid position has been modified, we need to modify the size
            const positionCorrection = {
              x: previousValidPositionDelta.x - validPositionDelta.x,
              y: previousValidPositionDelta.y - validPositionDelta.y,
            };
            validSize = {
              width: validSize.width + positionCorrection.x,
              height: validSize.height + positionCorrection.y,
            };
          }
          if (sizeDelta.x < 0 || sizeDelta.y < 0) {
            validSize = this.computeValidSize(validSize, maxBottomRight);
          }
        }
        this.updateChildrenRelativePosition(element, validPositionDelta);
      }
      element.size = validSize;
      element.position = validPosition;
    }
    return context.root;
  }
  computeValidSize(newSize: Dimension, maxBottomRight: Point): Dimension {
    return {
      width: Math.max(newSize.width, maxBottomRight.x),
      height: Math.max(newSize.height, maxBottomRight.y),
    };
  }
  computeValidPositionDelta(candidateDelta: Point, minTopLeft: Point): Point {
    return {
      x: Math.min(candidateDelta.x, minTopLeft.x),
      y: Math.min(candidateDelta.y, minTopLeft.y),
    };
  }
  private sizeDecreased(sizeDelta: Point, positionDelta: Point): boolean {
    return positionDelta.x > 0 || positionDelta.y > 0 || sizeDelta.x < 0 || sizeDelta.y < 0;
  }
  private updateChildrenRelativePosition(element: SNode, delta: Point) {
    element.children.forEach((child) => {
      if (this.isNode(child)) {
        child.position = {
          x: child.position.x - delta.x,
          y: child.position.y - delta.y,
        };
      }
    });
  }

  private getChildrenLimits(element: SNode): [Point | undefined, Point | undefined] {
    let minTopLeft = undefined;
    let maxBottomRight = undefined;
    element.children.forEach((child) => {
      if (this.isNode(child)) {
        if (!minTopLeft) {
          minTopLeft = {
            x: child.position.x,
            y: child.position.y,
          };
        } else {
          minTopLeft.x = Math.min(minTopLeft.x, child.position.x);
          minTopLeft.y = Math.min(minTopLeft.y, child.position.y);
        }
        const childBottomRight = {
          x: child.position.x + child.size.width,
          y: child.position.y + child.size.height,
        };
        if (!maxBottomRight) {
          maxBottomRight = childBottomRight;
        } else {
          maxBottomRight.x = Math.max(childBottomRight.x, maxBottomRight.x);
          maxBottomRight.y = Math.max(childBottomRight.y, maxBottomRight.y);
        }
      }
    });
    return [minTopLeft, maxBottomRight];
  }

  private isNode(element: SModelElement): element is SNode {
    return element instanceof SNode;
  }
  private isPort(element: SModelElement): element is SPort {
    return element instanceof SPort;
  }
  undo(context: CommandExecutionContext): CommandReturn {
    return context.root;
  }

  redo(context: CommandExecutionContext): CommandReturn {
    return context.root;
  }
}

decorate(inject(TYPES.Action) as ParameterDecorator, SiriusResizeCommand, 0);
