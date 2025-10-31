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

import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { SimpleTreeView } from '@mui/x-tree-view/SimpleTreeView';
import { DataTreeProps } from './DataTree.types';
import { DataTreeItem } from './DataTreeItem';

export const DataTree = ({ dataTree, onNodeClick }: DataTreeProps) => {
  const { nodes } = dataTree;
  const rootNodes = nodes.filter((node) => !node.parentId);
  return (
    <SimpleTreeView
      slots={{ collapseIcon: ExpandMoreIcon, expandIcon: ChevronRightIcon }}
      defaultExpandedItems={[]}
      data-testid="datatree">
      {rootNodes.map((rootNode, index) => {
        const rootItemId = `${index}`;
        return (
          <DataTreeItem
            treeItemId={rootItemId}
            node={rootNode}
            nodes={nodes}
            key={rootItemId}
            onNodeClick={onNodeClick}
          />
        );
      })}
    </SimpleTreeView>
  );
};
