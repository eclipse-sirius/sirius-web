/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo and others.
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
import React from 'react';

export interface DropAreaProps {
  editingContextId: string;
  representationId: string;
  invokeHover: (id: string, mouseIsHover: boolean) => void;
  convertInSprottyCoordinate: (x: number, y: number) => Promise<{ x: number; y: number }>;
  children: React.ReactNode;
  readOnly: boolean;
}
