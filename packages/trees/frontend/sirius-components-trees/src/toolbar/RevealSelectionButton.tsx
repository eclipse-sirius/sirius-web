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
import { RevealSelectionButtonProps } from './RevealSelectionButton.types';

export const RevealSelectionButton = ({ editingContextId, onClick }: RevealSelectionButtonProps) => {
  const { fetchObjectLabels, loading, labels } = useObjectsLabels();

  let tooltip = 'Reveal selected elements';
  if (loading) {
    tooltip = 'Reveal selected elements (loading details...)';
  }
  if (labels) {
    const maxLabelsToShow = 5;
    if (labels.length > maxLabelsToShow) {
      tooltip = `Select ${labels.slice(0, maxLabelsToShow).join(', ')} (and ${labels.length - maxLabelsToShow} more)`;
    } else {
      tooltip = `Select ${labels.join(', ')}`;
    }
  }

  const { selection } = useSelection();
  const handleTooltipOpen = () => {
    fetchObjectLabels(
      editingContextId,
      selection.entries.map((entry) => entry.id)
    );
  };

  return (
    <Tooltip title={tooltip} onOpen={handleTooltipOpen}>
      <span>
        <IconButton
          size="small"
          aria-label="reveal selection elements"
          onClick={onClick}
          data-testid="reveal-selection-button">
          <LocationSearchingIcon />
        </IconButton>
      </span>
    </Tooltip>
  );
};
