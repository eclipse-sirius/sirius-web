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

import Box from '@mui/material/Box';
import ButtonBase from '@mui/material/ButtonBase';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import Radio from '@mui/material/Radio';
import Typography from '@mui/material/Typography';
import { CSSProperties } from 'react';
import { SelectionDialogContentProps, SelectionDialogOptionButtonProps } from './SelectionDialogContent.types';
import { useSelectionDialog } from './useSelectionDialog';

export const SelectionDialogContent = ({ children }: SelectionDialogContentProps) => {
  const {
    selectionDialogDescription: { noSelectionAction, selectionAction, description },
    noSelectionOptionSelected,
    selectionOptionSelected,
    optional,
    updateSelectionOptions,
  } = useSelectionDialog();

  return (
    <DialogContent
      dividers={true}
      sx={(theme) => ({ display: 'flex', flexDirection: 'column', gap: theme.spacing(1), height: '600px' })}>
      <DialogContentText data-testid="selection-dialog-message">{description}</DialogContentText>
      {optional ? (
        <>
          <div data-testid="no-selection-option">
            <SelectionDialogOptionButton
              label={noSelectionAction.label}
              description={noSelectionAction.description}
              selected={noSelectionOptionSelected}
              onButtonClick={() => {
                updateSelectionOptions(true, false);
              }}
            />
          </div>
          <div>
            <SelectionDialogOptionButton
              label={selectionAction.label}
              description={selectionAction.description}
              selected={selectionOptionSelected}
              onButtonClick={() => {
                updateSelectionOptions(false, true);
              }}
            />
          </div>
        </>
      ) : null}
      <Box
        data-testid="selection-section"
        sx={() => {
          let sectionStyle: CSSProperties = { pointerEvents: 'initial', opacity: 1, flexGrow: 1, overflowY: 'auto' };
          if (optional && !selectionOptionSelected) {
            sectionStyle = { ...sectionStyle, pointerEvents: 'none', opacity: 0.5 };
          }
          return sectionStyle;
        }}>
        {children}
      </Box>
    </DialogContent>
  );
};

const SelectionDialogOptionButton = ({
  label,
  description,
  selected,
  onButtonClick,
}: SelectionDialogOptionButtonProps) => {
  return (
    <ButtonBase
      component="div"
      sx={(theme) => ({
        width: '100%',
        borderRadius: theme.spacing(0.5),
        padding: theme.spacing(1),
        justifyContent: 'flex-start',
        border: selected ? `1px solid ${theme.palette.primary.main}` : `1px solid ${theme.palette.divider}`,
      })}
      onClick={onButtonClick}>
      <Radio disableFocusRipple={true} disableRipple={true} disableTouchRipple={true} checked={selected} />
      <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'flex-start' }}>
        <Typography sx={(theme) => ({ color: theme.palette.text.primary })} data-testid="button-no-selection-label">
          {label}
        </Typography>
        <Typography sx={(theme) => ({ color: theme.palette.text.secondary })}>{description}</Typography>
      </Box>
    </ButtonBase>
  );
};
