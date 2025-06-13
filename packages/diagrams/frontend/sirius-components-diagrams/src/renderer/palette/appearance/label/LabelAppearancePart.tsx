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

import FormatColorResetIcon from '@mui/icons-material/FormatColorReset';
import Checkbox from '@mui/material/Checkbox';
import IconButton from '@mui/material/IconButton';
import ListItem from '@mui/material/ListItem';
import Typography from '@mui/material/Typography';
import { useContext } from 'react';
import { DiagramContext } from '../../../../contexts/DiagramContext';
import { DiagramContextValue } from '../../../../contexts/DiagramContext.types';
import { LabelAppearancePartProps } from './LabelAppearancePart.types';
import { useEditLabelAppearance } from './useEditLabelAppearance';
import { useResetLabelAppearance } from './useResetLabelAppearance';

export const LabelAppearancePart = ({
  diagramElementId,
  labelId,
  style,
  customizedStyleProperties,
}: LabelAppearancePartProps) => {
  const { editingContextId, diagramId } = useContext<DiagramContextValue>(DiagramContext);

  const { updateBold } = useEditLabelAppearance();
  const { resetLabelStyleProperties } = useResetLabelAppearance();

  return (
    <ListItem>
      <Typography variant="subtitle2">Label bold</Typography>
      <Checkbox
        checked={style.bold}
        onChange={(_, checked) => updateBold(editingContextId, diagramId, diagramElementId, labelId, checked)}
      />
      {customizedStyleProperties.some((property) => property === 'BOLD') ? (
        <IconButton
          aria-label="reset"
          onClick={() => resetLabelStyleProperties(editingContextId, diagramId, diagramElementId, labelId, ['BOLD'])}>
          <FormatColorResetIcon />
        </IconButton>
      ) : null}
    </ListItem>
  );
};
