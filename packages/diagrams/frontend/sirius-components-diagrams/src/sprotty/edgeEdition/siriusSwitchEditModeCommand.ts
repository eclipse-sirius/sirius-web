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
import {
  CommandExecutionContext,
  SEdge,
  SModelElement,
  SModelRoot,
  SRoutingHandle,
  SwitchEditModeAction,
  SwitchEditModeCommand,
  TYPES,
} from 'sprotty';
import { Point } from 'sprotty-protocol';
import { DiagramServer } from '../DiagramServer';

/**
 * The switch edit mode command used to send new routing point positions to the backend when there are edge to deactivate.
 */
export class SiriusSwitchEditModeCommand extends SwitchEditModeCommand {
  constructor(action: SwitchEditModeAction, private readonly diagramServer: DiagramServer) {
    super(action);
  }

  protected override doExecute(context: CommandExecutionContext): SModelRoot {
    if (this.action.elementsToDeactivate.length > 0) {
      const modelElement = context.root.index.getById(this.action.elementsToDeactivate[0]);
      if (modelElement instanceof SRoutingHandle) {
        if (this.shouldUpdateRoutingPoints(modelElement)) {
          const routingPoints = this.getRoutingPoints(modelElement.parent);
          this.diagramServer.updateRoutingPointsListener(routingPoints, modelElement.parent.id);
        }
      }
    }
    return super.doExecute(context);
  }

  /**
   * We consider to update routing points of an edge when they are created, updated and removed.
   *
   * When a routing point is created, it becomes a 'junction'.
   * When a routing point is updated, it still is a 'junction'.
   * When a routing point is removed, it is contained in the handesToRemove attributes but, it is still a 'junction'.
   *
   * In other words we have to update routing points of an edge every time a 'junction' SRoutingHandle is about to be deactivated.
   *
   * @param routingHandle The routing handle to consider.
   * @returns true whether routing points of the routingHandle parent has to be updated, false otherwise
   */
  private shouldUpdateRoutingPoints(routingHandle: SRoutingHandle): boolean {
    return routingHandle.kind === 'junction';
  }

  private getRoutingPoints(element: SModelElement): Point[] {
    if (element instanceof SEdge) {
      const routingPointsToRemove: Point[] = [];
      this.handlesToRemove.forEach((handleToRemove) => {
        const routingPointIndex = handleToRemove.handle.pointIndex;
        if (routingPointIndex < element.routingPoints.length) {
          routingPointsToRemove.push(element.routingPoints[routingPointIndex]);
        }
      });

      const newRoutingPoints = [...element.routingPoints];
      routingPointsToRemove.forEach((toRemove) => {
        newRoutingPoints.splice(newRoutingPoints.indexOf(toRemove), 1);
      });
      return newRoutingPoints;
    }
    return [];
  }
}

decorate(inject(TYPES.Action) as ParameterDecorator, SiriusSwitchEditModeCommand, 0);
decorate(inject(TYPES.ModelSource) as ParameterDecorator, SiriusSwitchEditModeCommand, 1);
