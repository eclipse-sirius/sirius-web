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

import ClickAwayListener from '@material-ui/core/ClickAwayListener';
import IconButton from '@material-ui/core/IconButton';
import Paper from '@material-ui/core/Paper';
import Popper from '@material-ui/core/Popper';
import Tooltip from '@material-ui/core/Tooltip';

import { Selection, useSelection } from '@eclipse-sirius/sirius-components-core';
import {
  DiagramPanelActionProps,
  DiagramContextValue,
  DiagramContext,
} from '@eclipse-sirius/sirius-components-diagrams';
import FilterListIcon from '@material-ui/icons/FilterList';
import { useContext, useRef, useState } from 'react';
import { DiagramFilterForm } from './DiagramFilterForm';

export const DiagramFilter = ({ editingContextId, diagramId }: DiagramPanelActionProps) => {
  const [isOpen, setIsOpen] = useState<boolean>(false);
  const { setSelection } = useSelection();

  const handlePanel = () => {
    const newSelection: Selection = {
      entries: [
        {
          id: diagramId,
          label: '',
          kind: '',
        },
      ],
    };
    setSelection(newSelection);
    setIsOpen(() => true);
  };

  const anchorRef = useRef<HTMLButtonElement | null>(null);
  const { readOnly } = useContext<DiagramContextValue>(DiagramContext);

  return (
    <>
      <Tooltip title="Filter elements" ref={anchorRef}>
        <IconButton
          size="small"
          aria-label="Filter elements"
          onClick={handlePanel}
          data-testid="Filter elements"
          disabled={readOnly}>
          <FilterListIcon />
        </IconButton>
      </Tooltip>
      <Popper
        open={isOpen}
        anchorEl={anchorRef.current}
        placement="bottom-start"
        transition
        disablePortal
        style={{ zIndex: '9999' }}>
        <Paper style={{ maxHeight: '50vh', overflow: 'auto' }}>
          <ClickAwayListener onClickAway={() => setIsOpen(false)}>
            <div>
              <DiagramFilterForm editingContextId={editingContextId} readOnly={false} />
            </div>
          </ClickAwayListener>
        </Paper>
      </Popper>
    </>
  );
};
