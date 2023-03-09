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

import { OutsideLabelBoxProps } from './OutsideLabelBox.types';

export const OutsideLabelBox = ({
  label,
  position = 'BOTTOM_CENTER',
  alignChildren = 'center',
  children,
}: OutsideLabelBoxProps) => {
  const style: React.CSSProperties = {
    boxSizing: 'border-box',
    display: 'flex',
    backgroundColor: `#80cbc4`,
  };

  if (position === 'TOP_CENTER') {
    style.flexDirection = 'column';
    style.alignItems = alignChildren;
    style.justifyContent = 'flex-start';
  } else if (position === 'BOTTOM_CENTER') {
    style.flexDirection = 'column-reverse';
    style.alignItems = alignChildren;
    style.justifyContent = 'flex-end';
  } else if (position === 'LEFT_CENTER') {
    style.flexDirection = 'row';
    style.alignItems = alignChildren;
    style.justifyContent = 'flex-start';
  } else if (position === 'RIGHT_CENTER') {
    style.flexDirection = 'row-reverse';
    style.alignItems = alignChildren;
    style.justifyContent = 'flex-end';
  }

  const labelStyle: React.CSSProperties = {
    boxSizing: 'border-box',
    whiteSpace: 'nowrap',
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
  };
  const childStyle: React.CSSProperties = {};
  return (
    <div id="outside-label-box" style={style}>
      <div style={labelStyle}>{label}</div>
      <div style={childStyle}>{children}</div>
    </div>
  );
};
