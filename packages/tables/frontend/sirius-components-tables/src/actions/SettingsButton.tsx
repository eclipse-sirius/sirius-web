/*******************************************************************************
 * Copyright (c) 2024 Obeo and others.
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
import SettingsIcon from '@mui/icons-material/Settings';
import Button from '@mui/material/Button';
import Menu from '@mui/material/Menu';
import { useState } from 'react';
import { ExportAllDataButton } from './ExportAllDataButton';
import { SettingsButtonProps, SettingsButtonState } from './SettingsButton.types';
import { settingButtonMenuEntryExtensionPoint } from './SettingsButtonExtensionPoints';
import { SettingsButtonMenuEntryProps } from './SettingsButtonExtensionPoints.types';

export const SettingsButton = ({ editingContextId, representationId, table }: SettingsButtonProps) => {
  const [state, setState] = useState<SettingsButtonState>({
    contextMenuAnchorElement: null,
  });

  const settingsButtonMenuEntries: ComponentExtension<SettingsButtonMenuEntryProps>[] = useComponents(
    settingButtonMenuEntryExtensionPoint
  );

  const handleClick: React.MouseEventHandler<HTMLButtonElement> = (event) =>
    setState((prevState) => ({ ...prevState, contextMenuAnchorElement: event.currentTarget }));

  const handleClose = () => setState((prevState) => ({ ...prevState, contextMenuAnchorElement: null }));

  return (
    <>
      <Button
        aria-haspopup="true"
        aria-expanded={!!state.contextMenuAnchorElement ? 'true' : undefined}
        startIcon={<SettingsIcon />}
        color="inherit"
        onClick={handleClick}>
        Settings
      </Button>
      <Menu
        id="basic-menu"
        anchorEl={state.contextMenuAnchorElement}
        open={!!state.contextMenuAnchorElement}
        onClose={handleClose}>
        <ExportAllDataButton table={table} />
        {settingsButtonMenuEntries.map(({ Component: SettingsButtonMenuItem }, index) => (
          <SettingsButtonMenuItem
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
