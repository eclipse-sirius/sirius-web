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

export const SelectionDialogContent = ({
  selectionDescription,
  selectionDialogOption,
  onSelectionDialogOptionChange,
  children,
}: SelectionDialogContentProps) => {
  const {
    dialog: { noSelectionAction, withSelectionAction, description },
    optional,
  } = selectionDescription;

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
              selected={selectionDialogOption === 'NO_SELECTION'}
              onButtonClick={() => {
                onSelectionDialogOptionChange('NO_SELECTION');
              }}
            />
          </div>
          <div>
            <SelectionDialogOptionButton
              label={withSelectionAction.label}
              description={withSelectionAction.description}
              selected={selectionDialogOption === 'WITH_SELECTION'}
              onButtonClick={() => {
                onSelectionDialogOptionChange('WITH_SELECTION');
              }}
            />
          </div>
        </>
      ) : null}
      <Box
        data-testid="selection-section"
        sx={() => {
          let sectionStyle: CSSProperties = {
            pointerEvents: 'initial',
            opacity: 1,
            flexGrow: 1,
            overflowY: 'auto',
            display: 'grid',
          };
          if (optional && selectionDialogOption !== 'WITH_SELECTION') {
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
