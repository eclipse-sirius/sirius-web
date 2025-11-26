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
import { useObjectsLabels, useSelection } from '@eclipse-sirius/sirius-components-core';
import LocationSearchingIcon from '@mui/icons-material/LocationSearchingOutlined';
import IconButton from '@mui/material/IconButton';
import Tooltip from '@mui/material/Tooltip';
import { useApplySelection } from '../selection/useApplySelection';
import { RevealSelectionInDiagramButtonProps } from './RevealSelectionInDiagramButton.types';
import { useTranslation } from 'react-i18next';

export const RevealSelectionInDiagramButton = ({ editingContextId }: RevealSelectionInDiagramButtonProps) => {
  const { fetchObjectLabels, loading, labels } = useObjectsLabels();
  const { t } = useTranslation('sirius-components-diagrams', { keyPrefix: 'revealSelectionInDiagramButton' });
  let tooltip = t('tooltip');
  if (loading) {
    tooltip = t('tooltipLoading');
  }
  if (labels) {
    const maxLabelsToShow = 5;
    if (labels.length > maxLabelsToShow) {
      tooltip = t('tooltipWithMoreLabels', {
        labels: labels.slice(0, maxLabelsToShow).join(', '),
        count: labels.length - maxLabelsToShow,
      });
    } else {
      tooltip = t('tooltipWithLabels', { labels: labels.join(', ') });
    }
  }

  const { selection } = useSelection();
  const handleTooltipOpen = () => {
    fetchObjectLabels(
      editingContextId,
      selection.entries.map((entry) => entry.id)
    );
  };

  const { applySelection } = useApplySelection();
  const revealGlobalSelection = () => applySelection(selection, true);

  return (
    <Tooltip title={tooltip} onOpen={handleTooltipOpen}>
      <span>
        <IconButton
          size="small"
          aria-label={t('tooltip')}
          onClick={revealGlobalSelection}
          data-testid="diagram-reveal-selection">
          <LocationSearchingIcon />
        </IconButton>
      </span>
    </Tooltip>
  );
};
