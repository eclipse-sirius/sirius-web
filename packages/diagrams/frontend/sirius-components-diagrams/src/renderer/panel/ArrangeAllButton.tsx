/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import AccountTreeIcon from '@mui/icons-material/AccountTree';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import ViewComfyIcon from '@mui/icons-material/ViewComfy';
import CircularProgress from '@mui/material/CircularProgress';
import IconButton from '@mui/material/IconButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import Tooltip from '@mui/material/Tooltip';
import { useEffect, useRef, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useArrangeAll } from '../layout/useArrangeAll';
import { ArrangeAllButtonProps, ArrangeAllButtonState } from './ArrangeAllButton.types';

export const ArrangeAllButton = ({ reactFlowWrapper, disabled }: ArrangeAllButtonProps) => {
  const [state, setState] = useState<ArrangeAllButtonState>({
    arrangeAllInProgress: false,
    arrangeAllWithRectPackingInProgress: false,
    arrangeAllMenuOpen: false,
  });
  const anchorArrangeMenuRef = useRef<HTMLButtonElement | null>(null);

  const { arrangeAll, arrangeAllWithRectPacking } = useArrangeAll(reactFlowWrapper);

  const { t } = useTranslation('sirius-components-diagrams', { keyPrefix: 'diagramPanel' });

  const handleArrangeAll = () => {
    setState((prevState) => ({
      ...prevState,
      arrangeAllMenuOpen: false,
      arrangeAllInProgress: true,
    }));
    arrangeAll().then(() =>
      setState((prevState) => ({
        ...prevState,
        arrangeAllInProgress: false,
      }))
    );
  };

  const handleArrangeAllWithRectPacking = () => {
    setState((prevState) => ({
      ...prevState,
      arrangeAllMenuOpen: false,
      arrangeAllWithRectPackingInProgress: true,
    }));
    arrangeAllWithRectPacking().then(() =>
      setState((prevState) => ({
        ...prevState,
        arrangeAllWithRectPackingInProgress: false,
      }))
    );
  };

  const handleMenuToggle = () =>
    setState((prevState) => ({
      ...prevState,
      arrangeAllMenuOpen: !prevState.arrangeAllMenuOpen,
    }));
  const onCloseMenu = () =>
    setState((prevState) => ({
      ...prevState,
      arrangeAllMenuOpen: false,
    }));

  useEffect(() => {
    const timeout = setTimeout(() => {
      const urlParams = new URLSearchParams(window.location.search);
      if (urlParams.has('arrangeAll') && urlParams.get('arrangeAll') === 'true') {
        handleArrangeAll();
      }
    }, 500);

    return () => clearTimeout(timeout);
  }, []);

  return (
    <>
      <Tooltip title={t('arrangeMenu')}>
        <span>
          <IconButton
            size="small"
            aria-label={t('arrangeMenu')}
            onClick={handleMenuToggle}
            data-testid="arrange-all-menu"
            ref={anchorArrangeMenuRef}
            disabled={disabled}>
            {state.arrangeAllInProgress || state.arrangeAllWithRectPackingInProgress ? (
              <CircularProgress size="24px" data-testid="arrange-all-circular-loading" />
            ) : (
              <AccountTreeIcon />
            )}
            <KeyboardArrowDownIcon />
          </IconButton>
        </span>
      </Tooltip>
      {state.arrangeAllMenuOpen ? (
        <Menu
          open={state.arrangeAllMenuOpen}
          anchorEl={anchorArrangeMenuRef.current}
          onClose={onCloseMenu}
          data-testid="arrange-all-actions">
          <MenuItem onClick={handleArrangeAll} disabled={disabled} data-testid="arrange-all">
            <ListItemIcon>
              {state.arrangeAllInProgress ? <CircularProgress size="20px" /> : <AccountTreeIcon fontSize="small" />}
            </ListItemIcon>
            <ListItemText primary={t('arrangeAllLayered')} />
          </MenuItem>
          <MenuItem onClick={handleArrangeAllWithRectPacking} disabled={disabled} data-testid="arrange-all-rectpacking">
            <ListItemIcon>
              {state.arrangeAllWithRectPackingInProgress ? (
                <CircularProgress size="20px" />
              ) : (
                <ViewComfyIcon fontSize="small" />
              )}
            </ListItemIcon>
            <ListItemText primary={t('arrangeAllRectPacking')} />
          </MenuItem>
        </Menu>
      ) : null}
    </>
  );
};
