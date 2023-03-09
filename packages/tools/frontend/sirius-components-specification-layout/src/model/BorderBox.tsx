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
import { BorderBoxProps } from './BorderBox.types';

export const BorderBox = ({
  minWidth = 'none',
  width = '150px',
  minHeight = 'auto',
  height = '70px',
  borderWidth = 0,
  children,
}: BorderBoxProps) => {
  const newChildren = React.Children.map(children, (child) => {
    if (React.isValidElement(child)) {
      return React.cloneElement(child, { ...child.props });
    }
    return child;
  });

  return (
    <div
      id="border-box"
      style={{
        boxSizing: 'border-box',
        display: 'flex',
        alignItems: 'stretch',
        justifyContent: 'stretch',
        width: width,
        height: height,
        minWidth: minWidth,
        minHeight: minHeight,
        padding: `${borderWidth}px`,
        backgroundColor: `rgb(228, 191, 124)`,
      }}>
      {newChildren}
    </div>
  );
};
