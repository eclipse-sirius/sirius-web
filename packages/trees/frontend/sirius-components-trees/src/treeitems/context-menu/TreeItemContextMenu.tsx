/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
import { ComponentExtension, useComponents, useData } from '@eclipse-sirius/sirius-components-core';
import Menu from '@mui/material/Menu';
import { DefaultMenuItem } from './DefaultMenuItem';
import { DeleteMenuItem } from './DeleteMenuItem';
import { RenameMenuItem } from './RenameMenuItem';
import { TreeItemContextMenuProps } from './TreeItemContextMenu.types';
import { TreeItemContextMenuComponentProps } from './TreeItemContextMenuEntry.types';
import {
  treeItemContextMenuEntryExtensionPoint,
  treeItemContextMenuEntryOverrideExtensionPoint,
} from './TreeItemContextMenuEntryExtensionPoints';
import { TreeItemContextMenuOverrideContribution } from './TreeItemContextMenuEntryExtensionPoints.types';
import { useContextMenuEntries } from './useContextMenuEntries';

export const TreeItemContextMenu = ({
  menuAnchor,
  editingContextId,
  treeId,
  item,
  readOnly,
  depth,
  expanded,
  maxDepth,
  onExpandedElementChange,
  enterEditingMode,
  onClose,
}: TreeItemContextMenuProps) => {
  const treeItemMenuContextComponents: ComponentExtension<TreeItemContextMenuComponentProps>[] = useComponents(
    treeItemContextMenuEntryExtensionPoint
  );

  const { data: treeItemContextMenuOverrideContributions } = useData<TreeItemContextMenuOverrideContribution[]>(
    treeItemContextMenuEntryOverrideExtensionPoint
  );

  const expandItem = () => {
    if (!item.expanded && item.hasChildren) {
      onExpandedElementChange([...expanded, item.id], Math.max(depth, maxDepth));
    }
  };

  const { loading, contextMenuEntries } = useContextMenuEntries(editingContextId, treeId, item.id);
  if (loading) {
    return null;
  }

  return (
    <Menu
      id="treeitem-contextmenu"
      anchorEl={menuAnchor}
      keepMounted
      open
      onClose={onClose}
      data-testid="treeitem-contextmenu"
      disableRestoreFocus={true}
      anchorOrigin={{
        vertical: 'bottom',
        horizontal: 'right',
      }}>
      {treeItemMenuContextComponents.map(({ Component: TreeItemMenuContextComponent }, index) => (
        <TreeItemMenuContextComponent
          editingContextId={editingContextId}
          item={item}
          entry={null}
          readOnly={readOnly}
          onClose={onClose}
          onExpandedElementChange={onExpandedElementChange}
          expandItem={expandItem}
          key={index.toString()}
          treeId={treeId}
          expanded={expanded}
          maxDepth={maxDepth}
        />
      ))}
      {contextMenuEntries.map((entry) => {
        const contributedTreeItemMenuContextComponents = treeItemContextMenuOverrideContributions
          .filter((contribution) => contribution.canHandle(entry))
          .map((contribution) => contribution.component);
        if (contributedTreeItemMenuContextComponents.length > 0) {
          return contributedTreeItemMenuContextComponents.map((TreeItemMenuContextComponent, index) => (
            <TreeItemMenuContextComponent
              editingContextId={editingContextId}
              item={item}
              entry={entry}
              readOnly={readOnly}
              onClose={onClose}
              onExpandedElementChange={onExpandedElementChange}
              expandItem={expandItem}
              key={index.toString()}
              treeId={treeId}
              expanded={expanded}
              maxDepth={maxDepth}
            />
          ));
        } else {
          return (
            <DefaultMenuItem
              editingContextId={editingContextId}
              treeId={treeId}
              item={item}
              entry={entry}
              readOnly={readOnly}
              onClick={onClose}
              key={entry.id}
            />
          );
        }
      })}

      <RenameMenuItem item={item} readOnly={readOnly} onClick={enterEditingMode} />
      <DeleteMenuItem
        editingContextId={editingContextId}
        treeId={treeId}
        item={item}
        readOnly={readOnly}
        onClick={onClose}
      />
    </Menu>
  );
};
