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

import { GQLTool, Palette, PaletteExtensionSection, usePalette } from '@eclipse-sirius/sirius-components-palette';
import React, { useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { ShowInSection } from './ShowInSection';
import { TreeItemPaletteProps } from './TreeItemPalette.types';
import { TreePaletteContext } from './contexts/TreePaletteContext';
import { useInvokeTreeItemTool } from './useInvokeTreeItemTool';
import { useTreeItemPaletteContents } from './useTreeItemPaletteContents';
import { GQLFetchTreeItemTool, GQLSingleClickTreeItemTool } from './useTreeItemPaletteContents.types';

export const isFetchTreeItemTool = (tool: GQLTool): tool is GQLFetchTreeItemTool =>
  tool.__typename === 'FetchTreeItemTool';

export const isSingleClickTreeItemTool = (tool: GQLTool): tool is GQLSingleClickTreeItemTool =>
  tool.__typename === 'SingleClickTreeItemTool';

export const TREE_REPRESENTATION_KIND = 'tree';

export const TreeItemPalette = ({
  editingContextId,
  treeId,
  treeItem,
  readOnly,
  selectedTreeItems,
  expanded,
  popperInstanceRef,
  onDirectEditClick,
  onExpandedElementChange,
  selectTreeItems,
  expandItem,
  onClose,
}: TreeItemPaletteProps) => {
  const { t } = useTranslation('sirius-components-trees', { keyPrefix: 'treePalette' });
  const { loading, palette } = useTreeItemPaletteContents(editingContextId, treeId, treeItem.id);
  const { invokeTreeItemTool } = useInvokeTreeItemTool();
  const { hidePalette } = usePalette();

  const [paletteEl, setPaletteEl] = React.useState<HTMLDivElement | null>(null);

  const paletteRefCallback = React.useCallback((node: HTMLDivElement | null) => {
    setPaletteEl(node);
  }, []);

  useEffect(() => {
    if (!paletteEl || !popperInstanceRef?.current) {
      return;
    }

    const resizeObserver = new ResizeObserver(() => popperInstanceRef.current?.update());
    resizeObserver.observe(paletteEl);

    return () => {
      resizeObserver.disconnect();
    };
  }, [paletteEl, popperInstanceRef]);

  if (loading) {
    return null;
  }

  const handleToolClick = (tool: GQLTool) => invokeTreeItemTool(editingContextId, treeId, treeItem.id, tool, () => {});

  const handleOnClose = () => {
    hidePalette();
    onClose();
  };

  const paletteToolListExtensions = [
    <PaletteExtensionSection
      id="show_in"
      key="show_in"
      title={t('showIn')}
      component={ShowInSection}
      onClose={hidePalette}
    />,
  ];

  if (!palette) {
    return null;
  }

  return (
    <TreePaletteContext.Provider
      value={{
        editingContextId: editingContextId,
        item: treeItem,
        treeId: treeId,
        readOnly: readOnly,
        selectedTreeItems: selectedTreeItems,
        expanded: expanded,
        onDirectEditClick: onDirectEditClick,
        selectTreeItems: selectTreeItems,
        onExpandedElementChange: onExpandedElementChange,
        expandItem: expandItem,
        onClose: onClose,
      }}>
      <Palette
        ref={paletteRefCallback}
        palette={palette}
        onToolClick={handleToolClick}
        onClose={handleOnClose}
        representationElementIds={[treeItem.id]}
        representationKind={TREE_REPRESENTATION_KIND}
        paletteToolListExtensions={paletteToolListExtensions}
        slotProps={{
          paper: () => ({ sx: () => ({ position: 'relative' }) }),
        }}
      />
    </TreePaletteContext.Provider>
  );
};
