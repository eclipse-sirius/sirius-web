/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import CheckBoxIcon from '@mui/icons-material/CheckBox';
import CheckBoxOutlineBlankIcon from '@mui/icons-material/CheckBoxOutlineBlank';
import WarningOutlined from '@mui/icons-material/WarningOutlined';
import Button from '@mui/material/Button';
import Checkbox from '@mui/material/Checkbox';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import FormControlLabel from '@mui/material/FormControlLabel';
import Typography from '@mui/material/Typography';
import { Theme } from '@mui/material/styles';
import { makeStyles } from 'tss-react/mui';
import { ConfirmationDialogProps } from './ConfirmationDialog.types';

const useConfirmationDialogStyles = makeStyles()((theme: Theme) => ({
  title: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    paddingTop: theme.spacing(2),
  },
  titleIcon: {
    color: theme.palette.error.main,
    fontSize: '50px',
  },
  message: {
    whiteSpace: 'pre-line',
    textAlign: 'center',
  },
  checkbox: {
    '&:hover': {
      color: theme.palette.error.main,
    },
  },
  checkboxChecked: {
    color: theme.palette.error.main,
  },
  checkboxLabel: {
    color: theme.palette.text.secondary,
  },
  confirmButton: {
    backgroundColor: theme.palette.error.main,
    color: 'white',
    '&:hover': {
      backgroundColor: theme.palette.error.dark,
    },
  },
}));

export const ConfirmationDialog = ({
  open,
  title,
  message,
  buttonLabel,
  allowConfirmationDisabled,
  confirmationDisabled,
  onConfirmationDisabledChange,
  onConfirm,
  onCancel,
}: ConfirmationDialogProps) => {
  const { classes } = useConfirmationDialogStyles();

  let confirmationDisabledElement: JSX.Element | null = null;
  if (allowConfirmationDisabled) {
    confirmationDisabledElement = (
      <FormControlLabel
        control={
          <Checkbox
            data-testid="confirmation-dialog-checkbox-disabled"
            className={classes.checkbox}
            checked={confirmationDisabled}
            onChange={(event) => onConfirmationDisabledChange(event.target.checked)}
            icon={<CheckBoxOutlineBlankIcon fontSize="small" />}
            checkedIcon={<CheckBoxIcon className={classes.checkboxChecked} fontSize="small" />}
          />
        }
        label={
          <Typography className={classes.checkboxLabel} variant="body2">
            Disable this confirmation dialog
          </Typography>
        }
      />
    );
  }
  return (
    <div>
      <Dialog open={open} onClose={onCancel} aria-labelledby="confirmation-dialog" data-testid="confirmation-dialog">
        <div className={classes.title}>
          <WarningOutlined className={classes.titleIcon} />
          <DialogTitle data-testid="confirmation-dialog-title">{title}</DialogTitle>
        </div>
        <DialogContent>
          <DialogContentText className={classes.message} data-testid="confirmation-dialog-message">
            {message}
          </DialogContentText>
          {confirmationDisabledElement}
        </DialogContent>
        <DialogActions>
          <Button
            data-testid="confirmation-dialog-button-ok"
            className={classes.confirmButton}
            onClick={onConfirm}
            variant="contained">
            {buttonLabel}
          </Button>
          <Button
            data-testid="confirmation-dialog-button-cancel"
            onClick={onCancel}
            variant="outlined"
            color="secondary"
            autoFocus>
            Cancel
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
};
