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
import { ContainerModule } from 'inversify';
import { AnchorComputerRegistry, EdgeRouterRegistry, TYPES } from 'sprotty';
import { SiriusDanglingAnchorComputer } from './siriusDanglingAnchorComputer';
import { SiriusRectangleAnchor } from './siriusPolylineAnchor';
import { SiriusPolylineEdgeRouter } from './siriusPolylineEdgeRouter';

export const siriusRoutingModule = new ContainerModule((bind) => {
  bind(EdgeRouterRegistry).toSelf().inSingletonScope();

  bind(AnchorComputerRegistry).toSelf().inSingletonScope();

  bind(SiriusPolylineEdgeRouter).toSelf().inSingletonScope();
  bind(TYPES.IEdgeRouter).toService(SiriusPolylineEdgeRouter);
  bind(TYPES.IAnchorComputer).to(SiriusRectangleAnchor);
  bind(TYPES.IAnchorComputer).to(SiriusDanglingAnchorComputer);
});
