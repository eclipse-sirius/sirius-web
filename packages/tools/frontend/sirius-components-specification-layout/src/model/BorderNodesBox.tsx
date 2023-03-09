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
import { BorderNode } from './BorderNode';
import { BorderNodeProps } from './BorderNode.types';
import { BorderNodesBoxProps } from './BorderNodesBox.types';

export const BorderNodesBox = ({ borderNodes, stretchChildren = false, children }: BorderNodesBoxProps) => {
  const topBorderNodes = [];
  const leftBorderNodes = [];
  const bottomBorderNodes = [];
  const rightBorderNodes = [];

  React.Children.forEach(borderNodes, (borderNode) => {
    if (React.isValidElement(borderNode) && borderNode.type === BorderNode) {
      const props = borderNode.props as BorderNodeProps;
      if (props.side === 'TOP') {
        topBorderNodes.push(props.component);
      } else if (props.side === 'LEFT') {
        leftBorderNodes.push(props.component);
      } else if (props.side === 'BOTTOM') {
        bottomBorderNodes.push(props.component);
      } else if (props.side === 'RIGHT') {
        rightBorderNodes.push(props.component);
      }
    }
  });

  let leftBorderNodesContainer = (
    <div style={{ gridArea: 'left', display: 'flex', flexDirection: 'column' }}>
      {leftBorderNodes.map((LeftBorderNode, index) => (
        <LeftBorderNode key={index} />
      ))}
    </div>
  );

  let topBorderNodesContainer = (
    <div style={{ gridArea: 'top', display: 'flex', flexDirection: 'row' }}>
      {topBorderNodes.map((TopBorderNode, index) => (
        <TopBorderNode key={index} />
      ))}
    </div>
  );

  let rightBorderNodesContainer = (
    <div style={{ gridArea: 'right', display: 'flex', flexDirection: 'column' }}>
      {rightBorderNodes.map((RightBorderNode, index) => (
        <RightBorderNode key={index} />
      ))}
    </div>
  );

  let bottomBorderNodesContainer = (
    <div style={{ gridArea: 'bottom', display: 'flex', flexDirection: 'row' }}>
      {bottomBorderNodes.map((BottomBorderNode, index) => (
        <BottomBorderNode key={index} />
      ))}
    </div>
  );

  let childrenElement: React.ReactNode = <div style={{ gridArea: 'center' }}>{children}</div>;

  const style: React.CSSProperties = {
    position: 'relative',
    width: '100%',
    height: '100%',
    display: 'grid',
    gridTemplateRows: '1fr min-content 1fr',
    gridTemplateColumns: '1fr min-content 1fr',
    gridTemplateAreas: `
    "top-left       top        top-right"
    "left          center          right"
    "bottom-left   bottom   bottom-right"
    `,
    backgroundColor: '#ba68c8',
  };

  if (
    stretchChildren &&
    topBorderNodes.length === 0 &&
    leftBorderNodes.length === 0 &&
    bottomBorderNodes.length === 0 &&
    rightBorderNodes.length === 0
  ) {
    style.gridTemplateRows = '1fr';
    style.gridTemplateColumns = '1fr';
    style.gridTemplateAreas = 'center';
    style.justifyItems = 'stretch';
    style.alignItems = 'stretch';

    leftBorderNodesContainer = null;
    topBorderNodesContainer = null;
    rightBorderNodesContainer = null;
    bottomBorderNodesContainer = null;
    childrenElement = children;
  }

  return (
    <div id="border-nodes-box" style={style}>
      {leftBorderNodesContainer}
      {topBorderNodesContainer}
      {childrenElement}
      {rightBorderNodesContainer}
      {bottomBorderNodesContainer}
    </div>
  );
};
