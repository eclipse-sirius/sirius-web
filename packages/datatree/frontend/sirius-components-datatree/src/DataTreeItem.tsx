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

import { getTextFromStyledString, IconOverlay, StyledLabel } from '@eclipse-sirius/sirius-components-core';
import { TreeItem as MuiTreeItem } from '@mui/x-tree-view/TreeItem';
import { makeStyles } from 'tss-react/mui';
import { DataTreeItemProps } from './DataTreeItem.types';

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

  const labelText = getTextFromStyledString(node.label);

  const label = (
    <div className={classes.label}>
      <IconOverlay iconURLs={node.iconURLs} alt={labelText} />
      <StyledLabel styledString={node.label} selected={false} textToHighlight={''} marked={false} />
      {node.endIconsURLs.map((iconURLs, index) => (
        <IconOverlay iconURLs={iconURLs} alt={labelText} key={index} />
      ))}
    </div>
  );

  const childNodes = nodes.filter((childNode) => childNode.parentId === node.id);
  return (
    <MuiTreeItem
      itemId={treeItemId}
      label={label}
      classes={{ content: classes.content }}
      data-datatreeitemlabel={labelText}
      data-datatreeitemid={treeItemId}>
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
