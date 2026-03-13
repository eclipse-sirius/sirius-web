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
import { ComponentExtension, useComponents } from '@eclipse-sirius/sirius-components-core';
import Menu from '@mui/material/Menu';
import CircularProgress from '@mui/material/CircularProgress';
import { ExportDataButton } from './ExportDataButton';
import { DefaultToolMenuEntry } from './DefaultToolMenuEntry';
import { ToolsMenuEntriesProps } from './ToolsMenuEntries.types';
import { useToolMenuEntries } from './useToolMenuEntries';
import { toolsButtonMenuEntryExtensionPoint } from './ToolsButtonExtensionPoints';
import { ToolsButtonMenuEntryProps } from './ToolsButtonExtensionPoints.types';

export const ToolsMenuEntries = ({
  editingContextId,
  representationId,
  table,
  contextMenuAnchorElement,
  onClose,
}: ToolsMenuEntriesProps) => {
  const { loading, toolMenuEntries } = useToolMenuEntries(editingContextId, representationId, table.id);
  const toolsButtonMenuEntries: ComponentExtension<ToolsButtonMenuEntryProps>[] = useComponents(
    toolsButtonMenuEntryExtensionPoint
  );
  return (
    <Menu id="basic-menu" anchorEl={contextMenuAnchorElement} open onClose={onClose}>
      {loading ? (
        <CircularProgress size={24} />
      ) : (
        <>
          <ExportDataButton table={table} />
          {toolMenuEntries.map((entry) => (
            <DefaultToolMenuEntry
              key={entry.id}
              editingContextId={editingContextId}
              representationId={representationId}
              tableId={table.id}
              entry={entry}
              readOnly={false}
            />
          ))}
          {toolsButtonMenuEntries.map(({ Component: ToolsButtonMenuItem }, index) => (
            <ToolsButtonMenuItem
              key={index}
              editingContextId={editingContextId}
              representationId={representationId}
              tableId={table.id}
            />
          ))}
        </>
      )}
    </Menu>
  );
};
