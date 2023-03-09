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

import { InsideLabelBoxProps } from './InsideLabelBox.types';

export const InsideLabelBox = ({ label, position = 'TOP_CENTER', children }: InsideLabelBoxProps) => {
  const labelElement = (
    <div
      style={{
        boxSizing: 'border-box',
        display: 'flex',
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'center',
        whiteSpace: 'nowrap',
      }}>
      {label}
    </div>
  );

  return (
    <div
      id="inside-label-box"
      style={{
        position: 'relative',
        width: '100%',
        height: '100%',
        display: 'grid',
        gridTemplateRows: 'min-content 1fr min-content',
        gridTemplateColumns: 'min-content 1fr min-content',
        backgroundColor: `rgb(176, 190, 121)`,
      }}>
      <div id="top-left"></div>
      <div id="top-center">{position === 'TOP_CENTER' ? labelElement : null}</div>
      <div id="top-right"></div>
      <div id="left-center"></div>
      {children}
      <div id="right-center"></div>
      <div id="bottom-left"></div>
      <div id="bottom-center">{position === 'BOTTOM_CENTER' ? labelElement : null}</div>
      <div id="bottom-right"></div>
    </div>
  );
};
