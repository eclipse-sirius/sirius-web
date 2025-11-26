/*******************************************************************************
 * Copyright (c) 2025 CEA LIST.
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

import MoreHorizIcon from '@mui/icons-material/MoreHoriz';
import IconButton from '@mui/material/IconButton';
import Tooltip from '@mui/material/Tooltip';
import { MouseEvent, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { RowActionProps } from './RowAction.types';
import { RowContextMenuContent } from './RowContextMenuContent';

export const RowAction = ({ editingContextId, representationId, tableId, row, readOnly }: RowActionProps) => {
  const [anchorEl, setAnchorEl] = useState<HTMLElement | null>(null);
  const { t } = useTranslation('sirius-components-tables', { keyPrefix: 'rowAction' });
  const handleOpenRowActionMenu = (event: MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
  };

  return (
    <>
      <Tooltip disableInteractive title={t('rowActions')}>
        <IconButton aria-label={t('rowActions')} onClick={handleOpenRowActionMenu} size="small">
          <MoreHorizIcon />
        </IconButton>
      </Tooltip>
      {anchorEl != null ? (
        <RowContextMenuContent
          editingContextId={editingContextId}
          representationId={representationId}
          tableId={tableId}
          row={row}
          readOnly={readOnly}
          anchorEl={anchorEl}
          onClose={() => setAnchorEl(null)}
        />
      ) : null}
    </>
  );
};
