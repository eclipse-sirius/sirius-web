/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo and others.
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

import ToolsIcon from '@mui/icons-material/Build';
import Button from '@mui/material/Button';
import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { ToolsButtonProps, ToolsButtonState } from './ToolsButton.types';
import { ToolsMenuEntries } from './ToolsMenuEntries';

export const ToolsButton = ({ editingContextId, representationId, table }: ToolsButtonProps) => {
  const { t } = useTranslation('sirius-components-tables', { keyPrefix: 'toolsButton' });
  const [state, setState] = useState<ToolsButtonState>({
    contextMenuAnchorElement: null,
  });

  const handleClick: React.MouseEventHandler<HTMLButtonElement> = (event) => {
    setState((prevState) => ({ ...prevState, contextMenuAnchorElement: event.currentTarget }));
  };

  const handleClose = () => setState((prevState) => ({ ...prevState, contextMenuAnchorElement: null }));

  return (
    <>
      <Button
        aria-haspopup="true"
        aria-expanded={!!state.contextMenuAnchorElement ? 'true' : undefined}
        startIcon={<ToolsIcon />}
        color="inherit"
        onClick={handleClick}>
        {t('tools')}
      </Button>
      {!!state.contextMenuAnchorElement && (
        <ToolsMenuEntries
          editingContextId={editingContextId}
          representationId={representationId}
          table={table}
          contextMenuAnchorElement={state.contextMenuAnchorElement}
          onClose={handleClose}
        />
      )}
    </>
  );
};
