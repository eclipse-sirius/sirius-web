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

import { ServerContext, ServerContextValue } from '@eclipse-sirius/sirius-components-core';
import { useContext } from 'react';
import { EdgeLabelProps } from './EdgeLabeL.types';
export const EdgeLabel = ({ transform, label: { iconURL, style: labelStyle, text } }: EdgeLabelProps) => {
  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);
  const style: React.CSSProperties = {
    transform,
    ...labelStyle,
  };

  return (
    <div style={style} className="nodrag nopan">
      {iconURL ? <img src={httpOrigin + iconURL} /> : ''}
      {text}
    </div>
  );
};
