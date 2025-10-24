/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo and others.
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
import ToolsIcon from '@mui/icons-material/Build';
import Button from '@mui/material/Button';
import Menu from '@mui/material/Menu';
import { useState } from 'react';
import { ExportDataButton } from './ExportDataButton';
import { ToolsButtonProps, ToolsButtonState } from './ToolsButton.types';
import { toolsButtonMenuEntryExtensionPoint } from './ToolsButtonExtensionPoints';
import { ToolsButtonMenuEntryProps } from './ToolsButtonExtensionPoints.types';

export const ToolsButton = ({ editingContextId, representationId, table }: ToolsButtonProps) => {
  const [state, setState] = useState<ToolsButtonState>({
    contextMenuAnchorElement: null,
  });

  const toolsButtonMenuEntries: ComponentExtension<ToolsButtonMenuEntryProps>[] = useComponents(
    toolsButtonMenuEntryExtensionPoint
  );

  const handleClick: React.MouseEventHandler<HTMLButtonElement> = (event) =>
    setState((prevState) => ({ ...prevState, contextMenuAnchorElement: event.currentTarget }));

  const handleClose = () => setState((prevState) => ({ ...prevState, contextMenuAnchorElement: null }));

  return (
    <>
      <Button
        aria-haspopup="true"
        aria-expanded={!!state.contextMenuAnchorElement ? 'true' : undefined}
        startIcon={<ToolsIcon />}
        color="inherit"
        onClick={handleClick}>
        Tools
      </Button>
      <Menu
        id="basic-menu"
        anchorEl={state.contextMenuAnchorElement}
        open={!!state.contextMenuAnchorElement}
        onClose={handleClose}>
        <ExportDataButton table={table} />
        {toolsButtonMenuEntries.map(({ Component: ToolsButtonMenuItem }, index) => (
          <ToolsButtonMenuItem
            key={index}
            editingContextId={editingContextId}
            representationId={representationId}
            tableId={table.id}
          />
        ))}
      </Menu>
    </>
  );
};
