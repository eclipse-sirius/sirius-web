/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import { useSynchronizedWithSelection } from '@eclipse-sirius/sirius-components-core';
import { TreeToolBarContributionComponentProps } from '@eclipse-sirius/sirius-components-trees';
import IconButton from '@material-ui/core/IconButton';
import { SwapHoriz as SwapHorizIcon } from '@material-ui/icons';

export const SynchronizedWithSelectionContribution = ({}: TreeToolBarContributionComponentProps) => {
  const { isSynchronized, toggleSynchronizeWithSelection } = useSynchronizedWithSelection();

  const onSynchronizedClick: React.MouseEventHandler<HTMLButtonElement> = (_event) => {
    toggleSynchronizeWithSelection();
  };

  const preferenceButtonSynchroniseTitle = isSynchronized
    ? 'Disable synchronisation with representation'
    : 'Enable synchronisation with representation';
  return (
    <IconButton
      color="inherit"
      size="small"
      aria-label={preferenceButtonSynchroniseTitle}
      title={preferenceButtonSynchroniseTitle}
      onClick={onSynchronizedClick}
      data-testid="tree-synchronise">
      <SwapHorizIcon color={isSynchronized ? 'inherit' : 'disabled'} />
    </IconButton>
  );
};
