/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { FilterBar, FilterBarContext, FilterBarContextValue, TreeView } from '@eclipse-sirius/sirius-components-trees';
import { Theme } from '@mui/material/styles';
import { useContext } from 'react';
import { makeStyles } from 'tss-react/mui';
import { DuplicateObjectKeyboardShortcut } from './context-menu-contributions/duplicate-object/DuplicateObjectKeyboardShortcut';
import { ExplorerRenderedProps } from './ExplorerTreeRenderer.types';

const useStyles = makeStyles()((theme: Theme) => ({
  treeFilter: {
    paddingTop: theme.spacing(1),
  },
  treeContent: {
    overflow: 'auto',
  },
}));

export const ExplorerTreeRenderer = ({
  editingContextId,
  readOnly,
  tree,
  selectedTreeItem,
  selectedTreeItemIds,
  target,
  expanded,
  maxDepth,
  onTreeItemClick,
  selectTreeItems,
  onExpandedElementChange,
}: ExplorerRenderedProps) => {
  const { classes: styles } = useStyles();
  const { isOpen, filterBarText, filterBarTreeFiltering, setFilterBarText, setFilterBarTreeFiltering, onClose } =
    useContext<FilterBarContextValue>(FilterBarContext);

  let filterBar: JSX.Element = <div />;
  if (isOpen) {
    filterBar = (
      <div className={styles.treeFilter}>
        <FilterBar
          onTextChange={(event) => {
            const {
              target: { value },
            } = event;
            setFilterBarText(value);
          }}
          onFilterButtonClick={setFilterBarTreeFiltering}
          onClose={onClose}
        />
      </div>
    );
  }

  return (
    <DuplicateObjectKeyboardShortcut
      target={target}
      editingContextId={editingContextId}
      readOnly={readOnly}
      selectedTreeItem={selectedTreeItem}
      selectTreeItems={selectTreeItems}>
      {filterBar}
      <div className={styles.treeContent}>
        <TreeView
          editingContextId={editingContextId}
          readOnly={readOnly}
          tree={tree}
          textToHighlight={filterBarText}
          textToFilter={filterBarTreeFiltering ? filterBarText : null}
          onExpandedElementChange={onExpandedElementChange}
          expanded={expanded}
          maxDepth={maxDepth}
          onTreeItemClick={onTreeItemClick}
          selectTreeItems={selectTreeItems}
          selectedTreeItemIds={selectedTreeItemIds}
          data-testid="explorer://"
          useTreePalette={tree.capabilities.useTreePalette}
        />
      </div>
    </DuplicateObjectKeyboardShortcut>
  );
};
