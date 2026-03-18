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
import { Theme, useTheme } from '@mui/material/styles';
import { useEffect, useRef, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useArrangeAll } from '../layout/arrange-all/useArrangeAll';
import { useLayoutConfigurations } from '../layout/arrange-all/useLayoutConfigurations';
import { LayoutConfiguration } from '../layout/arrange-all/useLayoutConfigurations.types';
import { ArrangeAllButtonProps, ArrangeAllButtonState } from './ArrangeAllButton.types';

export const ArrangeAllButton = ({ disabled }: ArrangeAllButtonProps) => {
  const [state, setState] = useState<ArrangeAllButtonState>({
    arrangeAllInProgress: false,
    arrangeAllMenuOpen: false,
    lastUsedLayout: null,
  });
  const anchorArrangeMenuRef = useRef<HTMLButtonElement | null>(null);

  const { arrangeAll } = useArrangeAll();
  const { t } = useTranslation('sirius-components-diagrams', { keyPrefix: 'arrangeAllButton' });
  const { layoutConfigurations } = useLayoutConfigurations();
  const theme: Theme = useTheme();

  const handleArrangeAll = (layoutConfiguration: LayoutConfiguration) => {
    setState((prevState) => ({
      ...prevState,
      arrangeAllMenuOpen: false,
      arrangeAllInProgress: true,
      lastUsedLayout: layoutConfiguration,
    }));
    arrangeAll(layoutConfiguration.layoutOptions).then(() =>
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

  const handleMainButtonClick = () => {
    if (state.lastUsedLayout) {
      handleArrangeAll(state.lastUsedLayout);
    } else if (layoutConfigurations[0]) {
      handleArrangeAll(layoutConfigurations[0]);
    }
  };

  useEffect(() => {
    const timeout = setTimeout(() => {
      const urlParams = new URLSearchParams(window.location.search);
      if (urlParams.has('arrangeAll') && urlParams.get('arrangeAll') === 'true' && layoutConfigurations[0]) {
        handleArrangeAll(layoutConfigurations[0]);
      }
    }, 500);

    return () => clearTimeout(timeout);
  }, []);

  const getMainButtonIcon = () => {
    if (state.arrangeAllInProgress) {
      return <CircularProgress size={theme.spacing(2)} data-testid="arrange-all-circular-loading" />;
    }
    if (state.lastUsedLayout) {
      return state.lastUsedLayout.icon;
    }
    if (layoutConfigurations[0]) {
      return layoutConfigurations[0].icon;
    }
    return <AccountTreeIcon />;
  };

  return (
    <>
      <Tooltip title={t('arrangeMenu')}>
        <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
          <IconButton
            size="small"
            aria-label={t('arrangeMenu')}
            onClick={handleMainButtonClick}
            data-testid="arrange-all-main-button"
            ref={anchorArrangeMenuRef}
            disabled={disabled || state.arrangeAllInProgress}>
            {getMainButtonIcon()}
          </IconButton>
          <IconButton
            size="small"
            aria-label={t('openMenu')}
            onClick={handleMenuToggle}
            data-testid="arrange-all-menu-toggle"
            disabled={disabled || state.arrangeAllInProgress}
            style={{ marginLeft: -theme.spacing(1), padding: theme.spacing(0.5) }}>
            <KeyboardArrowDownIcon />
          </IconButton>
        </div>
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
                onClick={() => handleArrangeAll(layoutConfiguration)}>
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
