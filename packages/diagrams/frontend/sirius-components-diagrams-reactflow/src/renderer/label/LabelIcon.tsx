/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import { LabelContext } from './LabelContext';
import { IconOverlay } from '@eclipse-sirius/sirius-components-core';
import { Label } from '../DiagramRenderer.types';

export const LabelIcon = () => {
  const label = React.useContext<Label | undefined>(LabelContext);
  if (!label) {
    return <div />;
  }
  return <IconOverlay iconURL={label.iconURL} alt={label.text} />;
};
