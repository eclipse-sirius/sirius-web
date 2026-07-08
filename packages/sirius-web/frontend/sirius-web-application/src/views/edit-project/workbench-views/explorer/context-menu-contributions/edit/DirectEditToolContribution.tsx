/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import {
  fuzzyMatch,
  HighlightedLabel,
  PaletteToolContributionComponentProps,
} from '@eclipse-sirius/sirius-components-palette';
import { TreePaletteContext, TreePaletteContextValue } from '@eclipse-sirius/sirius-components-trees';
import EditIcon from '@mui/icons-material/Edit';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import { useContext } from 'react';
import { useTranslation } from 'react-i18next';

export const DirectEditToolContribution = ({ onInvoked, searchedValue }: PaletteToolContributionComponentProps) => {
  const { t } = useTranslation('sirius-components-trees', { keyPrefix: 'renameMenuItem' });
  const { item, readOnly, onDirectEditClick } = useContext<TreePaletteContextValue>(TreePaletteContext);

  if (!item.editable) {
    return null;
  }

  const handleClick = () => {
    onInvoked();
    onDirectEditClick();
  };

  const matchResult = searchedValue ? fuzzyMatch(t('rename'), searchedValue) : null;
  if (!!searchedValue && !matchResult?.matches) {
    return null;
  }

  return (
    <MenuItem key="rename" onClick={handleClick} data-testid="rename-tree-item" disabled={readOnly}>
      <ListItemIcon>
        <EditIcon fontSize="small" />
      </ListItemIcon>
      {matchResult ? (
        <HighlightedLabel label={t('rename')} textIndicesToHighlight={matchResult.matchingIndices} />
      ) : (
        <ListItemText primary={t('rename')} />
      )}
    </MenuItem>
  );
};
