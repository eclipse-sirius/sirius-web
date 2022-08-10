/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

import { SelectionEntry, ServerContext } from '@eclipse-sirius/sirius-components-core';
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import ChevronRightIcon from '@material-ui/icons/ChevronRight';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import { TreeItem as MuiTreeItem } from '@material-ui/lab';
import TreeView from '@material-ui/lab/TreeView';
import React, { useContext } from 'react';
import { PropertySectionLabel } from './PropertySectionLabel';
import { TreeItemProps, TreePropertySectionProps } from './TreePropertySection.types';

const useTreeItemWidgetStyles = makeStyles((theme) => ({
  label: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    columnGap: theme.spacing(1),
  },
}));

const TreeItem = ({ node, nodes, setSelection }: TreeItemProps) => {
  const styles = useTreeItemWidgetStyles();
  const { httpOrigin } = useContext(ServerContext);

  const handleClick: React.MouseEventHandler<HTMLDivElement> = () => {
    if (node.selectable) {
      const newSelection: SelectionEntry = {
        id: node.id,
        label: node.label,
        kind: node.kind,
      };
      setSelection({ entries: [newSelection] });
    }
  };
  const label = (
    <div className={styles.label} onClick={handleClick}>
      {node.imageURL ? (
        <img height="16" width="16" title={node.label} alt={node.label} src={httpOrigin + node.imageURL} />
      ) : null}
      <Typography>{node.label}</Typography>
    </div>
  );

  const childNodes = nodes.filter((childNode) => childNode.parentId === node.id);
  return (
    <MuiTreeItem nodeId={node.id} label={label}>
      {childNodes.map((childNode) => (
        <TreeItem node={childNode} nodes={nodes} setSelection={setSelection} key={childNode.id} />
      ))}
    </MuiTreeItem>
  );
};

export const TreePropertySection = ({ widget, setSelection, subscribers }: TreePropertySectionProps) => {
  let { nodes, expandedNodesIds } = widget;

  if (widget.nodes.length === 0) {
    expandedNodesIds = [];
    nodes = [
      {
        id: 'none',
        parentId: null,
        label: 'None',
        kind: 'siriusComponents://unknown',
        imageURL: null,
        selectable: false,
      },
    ];
  }

  const rootNodes = nodes.filter((node) => node.parentId === null);
  return (
    <div>
      <PropertySectionLabel label={widget.label} subscribers={subscribers} />
      <TreeView
        defaultCollapseIcon={<ExpandMoreIcon />}
        defaultExpanded={expandedNodesIds}
        defaultExpandIcon={<ChevronRightIcon />}
      >
        {rootNodes.map((rootNode) => (
          <TreeItem node={rootNode} nodes={nodes} setSelection={setSelection} key={rootNode.id} />
        ))}
      </TreeView>
    </div>
  );
};
