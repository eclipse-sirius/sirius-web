/*******************************************************************************
 * Copyright (c) 2023 Obeo and others.
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

import { memo } from 'react';
import { Handle, NodeProps, NodeResizer, Position } from 'reactflow';
import { ListNodeData } from './ListNode.types';
import { NodePalette } from './palette/NodePalette';

const listNodeStyle = (style: React.CSSProperties, selected: boolean): React.CSSProperties => {
  const listNodeStyle: React.CSSProperties = {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'stretch',
    width: '100%',
    height: '100%',
    ...style,
  };
  if (selected) {
    listNodeStyle.outline = `var(--blue-lagoon) solid 1px`;
  }

  return listNodeStyle;
};

const listNodeHeaderStyle = (style: React.CSSProperties): React.CSSProperties => {
  return {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    gap: '8px',
    padding: '8px 16px',
    ...style,
  };
};

const listItemStyle = (style: React.CSSProperties): React.CSSProperties => {
  return {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'flex-start',
    gap: '8px',
    padding: '4px 8px',
    ...style,
  };
};

export const ListNode = memo(({ data, isConnectable, id, selected }: NodeProps<ListNodeData>) => {
  return (
    <>
      <NodeResizer color="var(--blue-lagoon)" isVisible={selected} />
      <div style={listNodeStyle(data.style, selected)}>
        <div data-id={data.label.id} style={listNodeHeaderStyle(data.label.style)}>
          {data.label.text}
        </div>
        <div>
          {data.listItems.map((listItem) => {
            return (
              <div key={listItem.id} style={listItemStyle(listItem.style)}>
                {listItem.label.text}
              </div>
            );
          })}
        </div>
        {selected ? <NodePalette diagramElementId={id} labelId={data.label?.id} /> : null}
        <Handle type="source" position={Position.Left} isConnectable={isConnectable} />
        <Handle type="target" position={Position.Right} isConnectable={isConnectable} />
      </div>
    </>
  );
});
