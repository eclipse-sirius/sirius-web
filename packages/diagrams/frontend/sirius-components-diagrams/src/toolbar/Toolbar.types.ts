/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo and others.
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
import { Subscriber } from '../representation/DiagramRepresentation.types';

export interface ToolbarProps {
  onZoomIn: () => void;
  onZoomOut: () => void;
  onFitToScreen: () => void;
  onArrangeAll: () => void;
  onUnhideAll: () => void;
  onUnfadeAll: () => void;
  setZoomLevel: (zoomLevel: string) => void;
  autoLayout: boolean;
  zoomLevel: string;
  readOnly: boolean;
  subscribers: Subscriber[];
}

export interface ToolbarState {
  modal: string | null;
  currentZoomLevel: string;
}
