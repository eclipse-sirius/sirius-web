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

import {
  GQLPalette,
  GQLTool,
  Palette,
  PaletteExtensionSection,
  usePalette,
} from '@eclipse-sirius/sirius-components-palette';
import { useTranslation } from 'react-i18next';
import { useContextMenuEntries } from '../context-menu/useContextMenuEntries';
import { GQLTreeItemContextMenuEntry } from '../context-menu/useContextMenuEntries.types';
import { useInvokeContextMenuEntry } from '../context-menu/useInvokeContextMenuEntry';
import { ShowInSection } from './ShowInSection';
import { TreeItemPaletteProps } from './TreeItemPalette.types';
import { TreePaletteContext } from './contexts/TreePaletteContext';

const isTreeItemContextMenuEntry = (tool: GQLTool): tool is GQLTreeItemContextMenuEntry =>
  tool.__typename === 'TreeItemContextMenuEntry';

export const TreeItemPalette = ({
  editingContextId,
  treeId,
  treeItem,
  readOnly,
  selectedTreeItems,
  expanded,
  onDirectEditClick,
  onExpandedElementChange,
  selectTreeItems,
  expandItem,
  onClose,
}: TreeItemPaletteProps) => {
  const { loading, contextMenuEntries } = useContextMenuEntries(editingContextId, treeId, treeItem.id, false);
  const { invokeContextMenuEntry } = useInvokeContextMenuEntry();
  const { hidePalette } = usePalette();
  const { t } = useTranslation('sirius-components-diagrams', { keyPrefix: 'diagramPalette' });

  if (loading) {
    return null;
  }

  const handleToolClick = (tool: GQLTool) => {
    if (isTreeItemContextMenuEntry(tool)) {
      invokeContextMenuEntry(editingContextId, treeId, treeItem.id, tool, () => {});
    }
  };

  const handleOnClose = () => {
    hidePalette();
    onClose();
  };

  const palette: GQLPalette = {
    id: treeItem.id,
    quickAccessTools: [],
    paletteEntries: contextMenuEntries,
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
        palette={palette}
        onToolClick={handleToolClick}
        onClose={handleOnClose}
        representationElementIds={[treeItem.id]}
        representationDescriptionId={'explorer_'}
        x={0}
        y={0}
        paletteToolListExtensions={paletteToolListExtensions}
      />
    </TreePaletteContext.Provider>
  );
};
