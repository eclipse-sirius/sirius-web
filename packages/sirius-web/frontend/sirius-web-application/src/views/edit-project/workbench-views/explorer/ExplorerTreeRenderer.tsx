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
import { RepresentationLoadingIndicator } from '@eclipse-sirius/sirius-components-core';
import { FilterBar, FilterBarContext, FilterBarContextValue, TreeView } from '@eclipse-sirius/sirius-components-trees';
import Box from '@mui/material/Box';
import { Theme } from '@mui/material/styles';
import { useContext, useRef } from 'react';
import { makeStyles } from 'tss-react/mui';
import { DuplicateObjectKeyboardShortcut } from './context-menu-contributions/duplicate-object/DuplicateObjectKeyboardShortcut';
import { ExplorerContext } from './ExplorerContext';
import { ExplorerContextValue } from './ExplorerContext.types';
import { ExplorerSelectionContext } from './ExplorerSelectionContext';
import { ExplorerSelectionContextValue } from './ExplorerSelectionContext.types';
import { ExplorerRendererProps } from './ExplorerTreeRenderer.types';

const useStyles = makeStyles()((theme: Theme) => ({
  treeView: {
    display: 'grid',
    gridTemplateColumns: 'auto',
    gridTemplateRows: 'auto minmax(0, 1fr)',
    justifyItems: 'stretch',
    overflow: 'hidden',
  },
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
  expanded,
  maxDepth,
  onExpandedElementChange,
}: ExplorerRendererProps) => {
  const { classes: styles } = useStyles();
  const {
    isOpen,
    filterBarText,
    filterBarTreeFiltering,
    setFilterBarText,
    setFilterBarTreeFiltering,
    onClose,
    onKeyDown,
  } = useContext<FilterBarContextValue>(FilterBarContext);
  const { selectedTreeItemIds, singleTreeItemSelected, onTreeItemClick, setSelectedTreeItemIds } =
    useContext<ExplorerSelectionContextValue>(ExplorerSelectionContext);

  const { tree } = useContext<ExplorerContextValue>(ExplorerContext);
  const treeElement = useRef<HTMLDivElement>(null);

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

  if (!tree) {
    return <RepresentationLoadingIndicator />;
  }

  return (
    <Box className={styles.treeView} onKeyDown={onKeyDown} ref={treeElement}>
      <DuplicateObjectKeyboardShortcut
        target={treeElement.current}
        editingContextId={editingContextId}
        readOnly={readOnly}
        selectedTreeItem={singleTreeItemSelected}
        selectTreeItems={setSelectedTreeItemIds}>
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
            selectTreeItems={setSelectedTreeItemIds}
            selectedTreeItemIds={selectedTreeItemIds}
            data-testid="explorer://"
            useTreePalette={tree.capabilities.useTreePalette}
          />
        </div>
      </DuplicateObjectKeyboardShortcut>
    </Box>
  );
};
