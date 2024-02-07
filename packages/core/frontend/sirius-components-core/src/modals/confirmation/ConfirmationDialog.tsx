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

import Button from '@material-ui/core/Button';
import Checkbox from '@material-ui/core/Checkbox';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Typography from '@material-ui/core/Typography';
import { Theme, makeStyles } from '@material-ui/core/styles';
import { WarningOutlined } from '@material-ui/icons';
import CheckBoxIcon from '@material-ui/icons/CheckBox';
import CheckBoxOutlineBlankIcon from '@material-ui/icons/CheckBoxOutlineBlank';
import { ConfirmationDialogProps } from './ConfirmationDialog.types';

const useConfirmationDialogStyles = makeStyles((theme: Theme) => ({
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
  confirmationDisabled,
  onConfirmationDisabledChange,
  onConfirm,
  onCancel,
}: ConfirmationDialogProps) => {
  const classes = useConfirmationDialogStyles();
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
