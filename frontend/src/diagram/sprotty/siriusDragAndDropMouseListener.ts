/*******************************************************************************
 * Copyright (c) 2021 THALES GLOBAL SERVICES.
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
  Action,
  Dimension,
  findParentByFeature,
  isViewport,
  MoveMouseListener,
  Point,
  SModelElement,
  SNode,
  SPort,
} from 'sprotty';
import { ElementResize, ResizeAction } from './resize/siriusResize';

/**
 * A common listener for drag and drop actions. This class allows to enter in resize or move mode.
 */
export class SiriusDragAndDropMouseListener extends MoveMouseListener {
  startResizePosition: Point | undefined;
  selector: String;
  intialTarget: SNode;
  startingPosition: Point;
  startingSize: Dimension;

  public mouseDown(target: SModelElement, event: MouseEvent): Action[] {
    const actions: Action[] = super.mouseDown(target, event);

    if (this.startDragPosition) {
      //if the click is perfomed on a resize selector, we switch from the move mode to resize mode.
      const selector = this.isResizeSelector(event);
      if (selector) {
        if (this.isSNode(target)) {
          this.selector = selector;
          this.startResizePosition = this.startDragPosition;
          //Deactivate the move
          this.startDragPosition = undefined;

          //We keep the initial mouse event target since it might change during the resize.
          this.intialTarget = target;
          //We keep the initial target size since it will change during the resize.
          this.startingSize = target.size;
          //We keep the initial position since it might change during the resize.
          this.startingPosition = target.position;
        }
      }
    }
    return actions;
  }

  public mouseMove(target: SModelElement, event: MouseEvent): Action[] {
    if (this.isResizing()) {
      const result: Action[] = [];
      const action = this.getMouseMoveResizeAction(event);
      if (action) {
        result.push(action);
      }
      return result;
    }
    return super.mouseMove(target, event);
  }

  protected isResizing(): Boolean {
    return this.startResizePosition != undefined;
  }

  public mouseUp(target: SModelElement, event: MouseEvent): Action[] {
    let result: Action[];
    if (this.isResizing()) {
      result = [];
      const action = this.getMouseUpResizeAction();
      if (action) {
        result.push(action);
      }
    } else {
      result = super.mouseUp(target, event);
    }
    this.reset();
    return result;
  }

  /**
   * We override the snap method to prevent moving an element out of the container.
   */
  protected snap(position: Point, element: SModelElement, isSnap: boolean): Point {
    let newPosition = super.snap(position, element, isSnap);
    if (this.isSNode(element)) {
      return this.getValidChildPosition(element, newPosition);
    } else if (this.isSPort(element)) {
      return this.getValidPortPosition(element, newPosition);
    }
    return newPosition;
  }

  /**
   * Provides the position within the parent bounding box.
   * @param element the element currently moved.
   * @param position the new candidate position.
   */
  private getValidChildPosition(element: SNode, position: Point): Point {
    const parent = element.parent;
    if (this.isSNode(parent)) {
      const bottomRight = {
        x: position.x + element.size.width,
        y: position.y + element.size.height,
      };
      const inBoundsBottomRight = {
        x: Math.min(bottomRight.x, parent.bounds.width),
        y: Math.min(bottomRight.y, parent.bounds.height),
      };
      const newValidPosition = {
        x: Math.max(0, inBoundsBottomRight.x - element.size.width),
        y: Math.max(0, inBoundsBottomRight.y - element.size.height),
      };
      return newValidPosition;
    }
    return position;
  }

  /**
   * Provides the position on the parent bounding box's border.
   * @param element the element currently moved.
   * @param position the new candidate position.
   */
  private getValidPortPosition(element: SPort, position: Point): Point {
    const parent = element.parent;
    if (this.isSNode(parent)) {
      return { x: -element.bounds.width / 2, y: -element.bounds.height / 2 } as Point;
    }
    return position;
  }

  protected reset() {
    this.intialTarget = undefined;
    this.startingPosition = undefined;
    this.startingSize = undefined;
    this.startResizePosition = undefined;
    this.selector = undefined;
  }

  private isResizeSelector(event: MouseEvent): String | undefined {
    const domTarget = event?.target as Element;
    if (domTarget?.id?.startsWith('selectorGrip_resize')) {
      return domTarget.id;
    }
    return undefined;
  }

  protected isSNode(element: SModelElement): element is SNode {
    return element instanceof SNode;
  }

  protected isSPort(element: SModelElement): element is SPort {
    return element instanceof SPort;
  }

  /**
   * Computes the potential new position and new size of the element being resized.
   * It is only "potential" because the ResizeAction can prevent the resize.
   */
  protected getMouseMoveResizeAction(event: MouseEvent): ResizeAction | undefined {
    if (!this.startResizePosition) return undefined;
    const viewport = findParentByFeature(this.intialTarget, isViewport);
    const zoom = viewport ? viewport.zoom : 1;
    const delta = {
      x: (event.pageX - this.startResizePosition.x) / zoom,
      y: (event.pageY - this.startResizePosition.y) / zoom,
    };
    const resizeElement = this.computeElementResize(delta);
    if (resizeElement) {
      return new ResizeAction(resizeElement, false);
    }
    return undefined;
  }

  /**
   * When the mouse up comes, the size and the position of the element being resize have already
   * been calculated by the last ResizeAction during the mouseMove, and thus, it only returns a
   * new ResizeAction with the size and position of the element being resize and mark the ResizeAction as finished.
   */
  protected getMouseUpResizeAction(): ResizeAction | undefined {
    if (!this.startResizePosition) return undefined;
    const resizeElement = {
      newSize: this.intialTarget.size,
      newPosition: this.intialTarget.position,
      elementId: this.intialTarget.id,
    };
    return new ResizeAction(resizeElement, true);
  }

  private computeElementResize(delta: Point): ElementResize {
    const elementId = this.intialTarget.id;
    const previousPosition = {
      x: this.intialTarget.position.x,
      y: this.intialTarget.position.y,
    };
    const previousSize = {
      width: this.intialTarget.size.width,
      height: this.intialTarget.size.height,
    };
    let newPosition = previousPosition;
    let newSize = previousSize;
    if (this.selector === 'selectorGrip_resize_s') {
      [newSize, newPosition] = this.handleSouth(previousSize, previousPosition, delta);
    } else if (this.selector === 'selectorGrip_resize_e') {
      [newSize, newPosition] = this.handleEast(previousSize, previousPosition, delta);
    } else if (this.selector === 'selectorGrip_resize_w') {
      [newSize, newPosition] = this.handleWest(previousSize, previousPosition, delta);
    } else if (this.selector === 'selectorGrip_resize_n') {
      [newSize, newPosition] = this.handleNorth(previousSize, previousPosition, delta);
    } else if (this.selector === 'selectorGrip_resize_nw') {
      [newSize, newPosition] = this.handleNorth(previousSize, previousPosition, delta);
      [newSize, newPosition] = this.handleWest(newSize, newPosition, delta);
    } else if (this.selector === 'selectorGrip_resize_ne') {
      [newSize, newPosition] = this.handleNorth(previousSize, previousPosition, delta);
      [newSize, newPosition] = this.handleEast(newSize, newPosition, delta);
    } else if (this.selector === 'selectorGrip_resize_se') {
      [newSize, newPosition] = this.handleSouth(previousSize, previousPosition, delta);
      [newSize, newPosition] = this.handleEast(newSize, newPosition, delta);
    } else if (this.selector === 'selectorGrip_resize_sw') {
      [newSize, newPosition] = this.handleSouth(previousSize, previousPosition, delta);
      [newSize, newPosition] = this.handleWest(newSize, newPosition, delta);
    }
    return {
      elementId,
      newPosition,
      newSize,
    };
  }

  private handleNorth(previousSize: Dimension, previousPosition: Point, delta: Point): [Dimension, Point] {
    const newSize = {
      width: previousSize.width,
      height: this.startingSize.height - delta.y,
    };
    const newPosition = {
      x: previousPosition.x,
      y: this.startingPosition.y + delta.y,
    };
    return [newSize, newPosition];
  }
  private handleSouth(previousSize: Dimension, previousPosition: Point, delta: Point): [Dimension, Point] {
    const newSize = {
      width: previousSize.width,
      height: this.startingSize.height + delta.y,
    };
    return [newSize, previousPosition];
  }
  private handleEast(previousSize: Dimension, previousPosition: Point, delta: Point): [Dimension, Point] {
    const newSize = {
      width: this.startingSize.width + delta.x,
      height: previousSize.height,
    };
    return [newSize, previousPosition];
  }
  private handleWest(previousSize: Dimension, previousPosition: Point, delta: Point): [Dimension, Point] {
    const newSize = {
      width: this.startingSize.width - delta.x,
      height: previousSize.height,
    };
    const newPosition = {
      x: this.startingPosition.x + delta.x,
      y: previousPosition.y,
    };
    return [newSize, newPosition];
  }
}
