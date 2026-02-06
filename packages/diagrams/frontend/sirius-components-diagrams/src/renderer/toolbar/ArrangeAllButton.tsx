/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
import CircularProgress from '@mui/material/CircularProgress';
import IconButton from '@mui/material/IconButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import Tooltip from '@mui/material/Tooltip';
import { LayoutOptions } from 'elkjs/lib/elk-api';
import { useEffect, useRef, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useArrangeAll } from '../layout/arrange-all/useArrangeAll';
import { useLayoutConfigurations } from '../layout/arrange-all/useLayoutConfigurations';
import { LayoutConfiguration } from '../layout/arrange-all/useLayoutConfigurations.types';
import { ArrangeAllButtonProps, ArrangeAllButtonState } from './ArrangeAllButton.types';

export const ArrangeAllButton = ({ reactFlowWrapper, disabled }: ArrangeAllButtonProps) => {
  const [state, setState] = useState<ArrangeAllButtonState>({
    arrangeAllInProgress: false,
    arrangeAllMenuOpen: false,
  });
  const anchorArrangeMenuRef = useRef<HTMLButtonElement | null>(null);

  const { arrangeAll } = useArrangeAll(reactFlowWrapper);
  const { t } = useTranslation('sirius-components-diagrams', { keyPrefix: 'arrangeAllButton' });
  const { layoutConfigurations } = useLayoutConfigurations();

  const handleArrangeAll = (layoutOptions: LayoutOptions) => {
    setState((prevState) => ({
      ...prevState,
      arrangeAllMenuOpen: false,
      arrangeAllInProgress: true,
    }));
    arrangeAll(layoutOptions).then(() =>
      setState((prevState) => ({
        ...prevState,
        arrangeAllInProgress: false,
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
      if (urlParams.has('arrangeAll') && urlParams.get('arrangeAll') === 'true' && layoutConfigurations[0]) {
        handleArrangeAll(layoutConfigurations[0].layoutOptions);
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
            disabled={disabled || state.arrangeAllInProgress}>
            {state.arrangeAllInProgress ? (
              <CircularProgress size="24px" data-testid="arrange-all-circular-loading" />
            ) : (
              <AccountTreeIcon />
            )}
            <KeyboardArrowDownIcon />
          </IconButton>
        </span>
      </Tooltip>
      {state.arrangeAllMenuOpen && !state.arrangeAllInProgress ? (
        <Menu
          open={state.arrangeAllMenuOpen}
          anchorEl={anchorArrangeMenuRef.current}
          onClose={onCloseMenu}
          data-testid="arrange-all-actions">
          {layoutConfigurations.map((layoutConfiguration: LayoutConfiguration) => {
            return (
              <MenuItem
                key={layoutConfiguration.id}
                disabled={disabled}
                data-testid={`arrange-all-${layoutConfiguration.id}`}
                onClick={() => handleArrangeAll(layoutConfiguration.layoutOptions)}>
                <ListItemIcon>{layoutConfiguration.icon}</ListItemIcon>
                <ListItemText primary={layoutConfiguration.label} />
              </MenuItem>
            );
          })}
        </Menu>
      ) : null}
    </>
  );
};
