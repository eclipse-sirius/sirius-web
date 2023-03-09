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

import { MarginBoxProps } from './MarginBox.types';

export const MarginBox = ({ width, withBorder, children }: MarginBoxProps) => {
  return (
    <div
      id="margin-box"
      style={{
        boxSizing: 'border-box',
        display: 'grid',
        gridTemplateRows: '1fr',
        gridTemplateColumns: '1fr',
        padding: `${width}px`,
        backgroundColor: `white`,
        border: withBorder ? '1px dashed black' : 'none',
      }}>
      {children}
    </div>
  );
};
