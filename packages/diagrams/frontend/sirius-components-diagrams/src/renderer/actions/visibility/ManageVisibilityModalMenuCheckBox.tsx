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

import MoreVertIcon from '@mui/icons-material/MoreVertOutlined';
import Box from '@mui/material/Box';
import Checkbox from '@mui/material/Checkbox';
import IconButton from '@mui/material/IconButton';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import Typography from '@mui/material/Typography';
import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { ManageVisibilityMenuCheckBoxProps } from './ManageVisibilityModalMenuCheckBox.types';
import { useManageVisibilityActions } from './hooks/useManageVisibilityActions';
import { GQLManageVisibilityAction } from './hooks/useManageVisibilityActions.types';
import { useManageVisibilityInvokeAction } from './hooks/useManageVisibilityInvokeAction';

export const ManageVisibilityModalMenuCheckBox = ({
  isOneElementChecked,
  onCheckingAllElement,
  diagramElementId,
}: ManageVisibilityMenuCheckBoxProps) => {
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);

  const handleClick = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  const { actions } = useManageVisibilityActions(diagramElementId);
  const { invokeAction } = useManageVisibilityInvokeAction();
  const { t } = useTranslation('sirius-components-diagrams', { keyPrefix: 'manageVisibilityModalMenuCheckBox' });

  return (
    <Box
      sx={{
        display: 'flex',
        justifyContent: 'left',
        alignItems: 'center',
      }}>
      <Checkbox
        disableRipple
        checked={isOneElementChecked}
        onChange={(_event, checked) => onCheckingAllElement(checked)}
        data-testid="manage_visibility_checkbox"
      />
      <Typography>{t('all')}</Typography>
      <IconButton
        aria-label="filter-button"
        color="secondary"
        size="small"
        onClick={handleClick}
        data-testid="manage_visibility_menu">
        <MoreVertIcon />
      </IconButton>

      <Menu id="filter-menu" anchorEl={anchorEl} open={anchorEl !== null} onClose={handleClose}>
        {actions.map((action: GQLManageVisibilityAction) => {
          return (
            <MenuItem
              key={`manage_visibility_menu_action_${action.label}`}
              data-testid={action.id}
              onClick={() => invokeAction(diagramElementId, action)}>
              {action.label}
            </MenuItem>
          );
        })}
      </Menu>
    </Box>
  );
};
