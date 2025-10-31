/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import { makeStyles } from 'tss-react/mui';
import { DataTreeItemProps } from './DataTreeItem.types';
import { IconOverlay, StyledLabel } from '@eclipse-sirius/sirius-components-core';
import { TreeItem as MuiTreeItem } from '@mui/x-tree-view/TreeItem';
import { getTextFromStyledString } from '@eclipse-sirius/sirius-components-core';

const useDataTreeItemStyles = makeStyles()((theme) => ({
  label: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    columnGap: theme.spacing(1),
  },
  content: {
    paddingLeft: 0,
    paddingTop: 0,
    paddingRight: 0,
    paddingBottom: 0,
  },
}));

export const DataTreeItem = ({ treeItemId, node, nodes }: DataTreeItemProps) => {
  const { classes } = useDataTreeItemStyles();

  const label = (
    <div className={classes.label}>
      <IconOverlay iconURL={node.iconURL} alt={getTextFromStyledString(node.label)} />
      <StyledLabel styledString={node.label} selected={false} textToHighlight={''} marked={false} />
      {node.endIconsURL.map((iconURL, index) => (
        <IconOverlay iconURL={iconURL} alt={getTextFromStyledString(node.label)} key={index} />
      ))}
    </div>
  );

  const childNodes = nodes.filter((childNode) => childNode.parentId === node.id);
  return (
    <MuiTreeItem itemId={treeItemId} label={label} classes={{ content: classes.content }}>
      {childNodes.map((childNode, index) => {
        const childTreeItemId = `${treeItemId}/${index}`;
        return (
          <DataTreeItem
            treeItemId={childTreeItemId}
            node={childNode}
            nodes={nodes}
            key={childTreeItemId}
            aria-role="treeitem"
          />
        );
      })}
    </MuiTreeItem>
  );
};
