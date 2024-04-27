/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import ClickAwayListener from '@mui/material/ClickAwayListener';
import IconButton from '@mui/material/IconButton';
import Paper from '@mui/material/Paper';
import Popper from '@mui/material/Popper';
import Tooltip from '@mui/material/Tooltip';

import {
  DiagramContext,
  DiagramContextValue,
  DiagramPanelActionProps,
} from '@eclipse-sirius/sirius-components-diagrams';
import FilterListIcon from '@mui/icons-material/FilterList';
import { useContext, useRef, useState } from 'react';
import { DiagramFilterForm } from './DiagramFilterForm';

export const DiagramFilter = ({ editingContextId, diagramId }: DiagramPanelActionProps) => {
  const [isOpen, setIsOpen] = useState<boolean>(false);

  const handlePanel = () => setIsOpen(() => true);

  const anchorRef = useRef<HTMLButtonElement | null>(null);
  const { readOnly } = useContext<DiagramContextValue>(DiagramContext);

  return (
    <>
      <Tooltip title="Filter elements" ref={anchorRef}>
        <span>
          <IconButton
            size="small"
            aria-label="Filter elements"
            onClick={handlePanel}
            data-testid="Filter elements"
            disabled={readOnly}>
            <FilterListIcon />
          </IconButton>
        </span>
      </Tooltip>
      <Popper
        open={isOpen}
        anchorEl={anchorRef.current}
        placement="bottom-start"
        disablePortal
        style={{ zIndex: '9999' }}>
        <Paper style={{ maxHeight: '50vh', overflow: 'auto' }}>
          <ClickAwayListener onClickAway={() => setIsOpen(false)}>
            <div>
              <DiagramFilterForm editingContextId={editingContextId} diagramId={diagramId} readOnly={false} />
            </div>
          </ClickAwayListener>
        </Paper>
      </Popper>
    </>
  );
};
