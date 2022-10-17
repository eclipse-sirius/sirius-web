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
import {
  CommitModelAction,
  DeleteElementAction,
  edgeInProgressID,
  findChildrenAtPosition,
  findParentByFeature,
  isConnectable,
  isViewport,
  MoveMouseListener,
  ReconnectAction,
  SChildElement,
  SConnectableElement,
  SModelElement,
  SNode,
  SPort,
  SRoutableElement,
  SRoutingHandle,
  SwitchEditModeAction,
  translatePoint,
} from 'sprotty';
import { Action, Dimension, Point } from 'sprotty-protocol';
import { Bounds } from 'sprotty-protocol/';
import { SiriusReconnectAction } from '../edgeEdition/model';
import { snapToRectangle } from '../utils/geometry';
import { RectangleSide } from '../utils/geometry.types';
import { ElementResize, ResizeAction } from './siriusResize';

/**
 * The offset of the port inside its container. It should be the same value as the ELK default value.
 */
const PORT_OFFSET = 8;

/**
 * A common listener for drag and drop actions. This class allows to enter in resize or move mode.
 */
export class SiriusDragAndDropMouseListener extends MoveMouseListener {
  startResizePosition: Point | undefined;
  selector: String;
  intialTarget: SConnectableElement;
  startingPosition: Point;
  startingSize: Dimension;

  public override mouseDown(target: SModelElement, event: MouseEvent): Action[] {
    const actions: Action[] = super.mouseDown(target, event);

    if (this.startDragPosition) {
      //if the click is perfomed on a resize selector, we switch from the move mode to resize mode.
      const selector = this.isResizeSelector(event);
      if (selector) {
        if (this.isSNode(target) || this.isSPort(target)) {
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

  public override mouseMove(target: SModelElement, event: MouseEvent): Action[] {
    if (this.isResizing()) {
      const result: Action[] = [];
      const action = this.getMouseMoveResizeAction(event, false);
      if (action) {
        result.push(action);
      }
      return result;
    }
    return super.mouseMove(target, event);
  }

  protected isResizing(): Boolean {
    return this.startResizePosition !== undefined;
  }

  public override mouseUp(target: SModelElement, event: MouseEvent): Action[] {
    let result: Action[];
    if (this.isResizing()) {
      result = [];
      const action = this.getMouseMoveResizeAction(event, true);
      if (action) {
        result.push(action);
      }
    } else {
      result = this.onMouseUp(target, event);
    }
    this.reset();
    return result;
  }

  private onMouseUp(target: SModelElement, event: MouseEvent): Action[] {
    const result: Action[] = [];
    let hasReconnected = false;
    if (this.startDragPosition) {
      const moveAction = this.getElementMoves(target, event, true);
      if (moveAction) result.push(moveAction);
      target.root.index.all().forEach((element) => {
        if (element instanceof SRoutingHandle) {
          const parent = element.parent;
          if (parent instanceof SRoutableElement && element.danglingAnchor) {
            const handlePos = this.getHandlePosition(element);
            if (handlePos) {
              const handlePosAbs = translatePoint(handlePos, element.parent, element.root);
              const newEnd = findChildrenAtPosition(target.root, handlePosAbs)
                .reverse()
                .find((e) => isConnectable(e) && e.canConnect(parent, element.kind as 'source' | 'target'));
              if (newEnd && this.hasDragged) {
                if (element.kind === 'source' || element.kind === 'target') {
                  const action: SiriusReconnectAction = {
                    kind: ReconnectAction.KIND,
                    routableId: element.parent.id,
                    newSourceId: element.kind === 'source' ? newEnd.id : parent.sourceId,
                    newTargetId: element.kind === 'target' ? newEnd.id : parent.targetId,
                    newEndPosition: { x: handlePosAbs.x, y: handlePosAbs.y },
                    reconnectKind: element.kind,
                  };
                  result.push(action);
                  hasReconnected = true;
                }
              }
            }
          }
          if (element.editMode) result.push(SwitchEditModeAction.create({ elementsToDeactivate: [element.id] }));
        }
      });
    }
    if (!hasReconnected) {
      const edgeInProgress = target.root.index.getById(edgeInProgressID);
      if (edgeInProgress instanceof SChildElement) {
        const deleteIds: string[] = [];
        deleteIds.push(edgeInProgressID);
        edgeInProgress.children.forEach((c) => {
          if (c instanceof SRoutingHandle && c.danglingAnchor) deleteIds.push(c.danglingAnchor.id);
        });
        result.push(DeleteElementAction.create(deleteIds));
      }
    }
    if (this.hasDragged) result.push(CommitModelAction.create());
    this.hasDragged = false;
    this.startDragPosition = undefined;
    this.elementId2startPos.clear();
    return result;
  }

  /**
   * We override the snap method to prevent moving an element out of the container.
   */
  public override snap(position: Point, element: SModelElement, isSnap: boolean): Point {
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
      const bottomRight: Point = {
        x: position.x + element.size.width,
        y: position.y + element.size.height,
      };
      const inBoundsBottomRight: Point = {
        x: Math.min(bottomRight.x, parent.bounds.width),
        y: Math.min(bottomRight.y, parent.bounds.height),
      };
      const newValidPosition: Point = {
        x: Math.max(0, inBoundsBottomRight.x - element.size.width),
        y: Math.max(0, inBoundsBottomRight.y - element.size.height),
      };
      return newValidPosition;
    }
    return position;
  }

  /**
   * Provides the position on the parent bounding box's border.
   * @param sPort the sPort currently moved.
   * @param position the new candidate position of the SPort upper left corner.
   * @returns the real position of the port.
   */
  private getValidPortPosition(sPort: SPort, newSportPosition: Point): Point {
    const parent = sPort.parent;

    // by default, the SPort is not moved
    let portPosition: Point = { x: sPort.bounds.x, y: sPort.bounds.y };

    if (this.isSNode(parent)) {
      // Determine on which side the port should be associated
      const translationPoint: Point = {
        x: newSportPosition.x - sPort.bounds.x,
        y: newSportPosition.y - sPort.bounds.y,
      };
      const currentSPortCenter: Point = Bounds.center(sPort.bounds);
      const candidateSPortCenter: Point = {
        x: currentSPortCenter.x + translationPoint.x,
        y: currentSPortCenter.y + translationPoint.y,
      };

      const { pointOnRectangleSide, side } = snapToRectangle(candidateSPortCenter, {
        x: 0, // because newSportPosition has coordinates relative to parent
        y: 0,
        width: parent.bounds.width,
        height: parent.bounds.height,
      });

      // Move the port according to the offset and the side position
      if (side === RectangleSide.north) {
        portPosition = {
          x: pointOnRectangleSide.x - sPort.bounds.width / 2,
          y: pointOnRectangleSide.y - sPort.bounds.height + PORT_OFFSET,
        };
      } else if (side === RectangleSide.south) {
        portPosition = {
          x: pointOnRectangleSide.x - sPort.bounds.width / 2,
          y: pointOnRectangleSide.y - PORT_OFFSET,
        };
      } else if (side === RectangleSide.west) {
        portPosition = {
          x: pointOnRectangleSide.x - sPort.bounds.width + PORT_OFFSET,
          y: pointOnRectangleSide.y - sPort.bounds.height / 2,
        };
      } else if (side === RectangleSide.east) {
        portPosition = {
          x: pointOnRectangleSide.x - PORT_OFFSET,
          y: pointOnRectangleSide.y - sPort.bounds.height / 2,
        };
      }
    }

    return portPosition;
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
  protected getMouseMoveResizeAction(event: MouseEvent, finished: boolean): ResizeAction | undefined {
    if (!this.startResizePosition) {
      return undefined;
    }
    const viewport = findParentByFeature(this.intialTarget, isViewport);
    const zoom = viewport ? viewport.zoom : 1;
    const delta: Point = {
      x: (event.pageX - this.startResizePosition.x) / zoom,
      y: (event.pageY - this.startResizePosition.y) / zoom,
    };
    const resizeElement = this.computeElementResize(delta);
    if (resizeElement) {
      return new ResizeAction(resizeElement, finished);
    }
    return undefined;
  }

  private computeElementResize(delta: Point): ElementResize {
    const elementId = this.intialTarget.id;
    const previousPosition: Point = {
      x: this.intialTarget.position.x,
      y: this.intialTarget.position.y,
    };
    const previousSize: Dimension = {
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
    const newSize: Dimension = {
      width: previousSize.width,
      height: this.startingSize.height - delta.y,
    };
    const newPosition: Point = {
      x: previousPosition.x,
      y: this.startingPosition.y + delta.y,
    };
    return [newSize, newPosition];
  }
  private handleSouth(previousSize: Dimension, previousPosition: Point, delta: Point): [Dimension, Point] {
    const newSize: Dimension = {
      width: previousSize.width,
      height: this.startingSize.height + delta.y,
    };
    return [newSize, previousPosition];
  }
  private handleEast(previousSize: Dimension, previousPosition: Point, delta: Point): [Dimension, Point] {
    const newSize: Dimension = {
      width: this.startingSize.width + delta.x,
      height: previousSize.height,
    };
    return [newSize, previousPosition];
  }
  private handleWest(previousSize: Dimension, previousPosition: Point, delta: Point): [Dimension, Point] {
    const newSize: Dimension = {
      width: this.startingSize.width - delta.x,
      height: previousSize.height,
    };
    const newPosition: Point = {
      x: this.startingPosition.x + delta.x,
      y: previousPosition.y,
    };
    return [newSize, newPosition];
  }
}
