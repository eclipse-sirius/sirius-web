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
import CircularProgress from '@mui/material/CircularProgress';
import IconButton from '@mui/material/IconButton';
import Tooltip from '@mui/material/Tooltip';
import { useEffect, useState } from 'react';
import { useArrangeAll } from '../layout/useArrangeAll';
import { ArrangeAllButtonProps, ArrangeAllButtonState } from './ArrangeAllButton.types';
import { useTranslation } from 'react-i18next';

export const ArrangeAllButton = ({ reactFlowWrapper, disabled }: ArrangeAllButtonProps) => {
  const [state, setState] = useState<ArrangeAllButtonState>({
    arrangeAllInProgress: false,
  });

  const { arrangeAll } = useArrangeAll(reactFlowWrapper);
  const { t } = useTranslation('sirius-components-diagrams', { keyPrefix: 'arrangeAllButton' });
  const handleClick = () => {
    setState((prevState) => ({
      ...prevState,
      arrangeAllInProgress: true,
    }));
    arrangeAll().then(() =>
      setState((prevState) => ({
        ...prevState,
        arrangeAllDone: true,
        arrangeAllInProgress: false,
      }))
    );
  };

  useEffect(() => {
    const timeout = setTimeout(() => {
      const urlParams = new URLSearchParams(window.location.search);
      if (urlParams.has('arrangeAll') && urlParams.get('arrangeAll') === 'true') {
        handleClick();
      }
    }, 500);

    return () => clearTimeout(timeout);
  }, []);

  return (
    <Tooltip title={t('arrangeAll')}>
      <span>
        <IconButton
          size="small"
          aria-label={t('arrangeAll')}
          onClick={handleClick}
          data-testid={'arrange-all'}
          disabled={disabled}>
          {state.arrangeAllInProgress ? (
            <CircularProgress size="24px" data-testid="arrange-all-circular-loading" />
          ) : (
            <AccountTreeIcon />
          )}
        </IconButton>
      </span>
    </Tooltip>
  );
};
