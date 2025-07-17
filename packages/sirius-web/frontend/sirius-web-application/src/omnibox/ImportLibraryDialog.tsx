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
import ArrowDropDownIcon from '@mui/icons-material/ArrowDropDown';
import Button from '@mui/material/Button';
import ButtonGroup from '@mui/material/ButtonGroup';
import ClickAwayListener from '@mui/material/ClickAwayListener';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import Grow from '@mui/material/Grow';
import MenuItem from '@mui/material/MenuItem';
import MenuList from '@mui/material/MenuList';
import Paper from '@mui/material/Paper';
import Popper from '@mui/material/Popper';
import Typography from '@mui/material/Typography';
import { useEffect, useRef, useState } from 'react';
import { useCurrentProject } from '../project/useCurrentProject';
import {
  ImportLibraryDialogProps,
  ImportLibraryDialogState,
  ImportStudioSplitButtonProps,
  ImportStudioSplitButtonState,
} from './ImportLibraryDialog.types';
import { LibrariesImportTable } from './LibrariesImportTable';
import { useImportLibraries } from './useImportLibraries';

export const ImportLibraryDialog = ({ open, title, onClose }: ImportLibraryDialogProps) => {
  const [state, setState] = useState<ImportLibraryDialogState>({
    selectedLibraries: [],
    actions: [
      { id: 'import', label: 'Import' },
      { id: 'copy', label: 'Copy' },
    ],
  });

  const onSelectedLibrariesChange = (selectedLibraryIds: string[]) => {
    setState((prevState) => ({
      ...prevState,
      selectedLibraries: selectedLibraryIds,
    }));
  };

  return (
    <Dialog open={open} onClose={onClose} fullWidth maxWidth="lg" scroll="paper" data-testid="import-library">
      <DialogTitle>{title}</DialogTitle>
      <DialogContent>
        <LibrariesImportTable onSelectedLibrariesChange={onSelectedLibrariesChange} />
      </DialogContent>
      <DialogActions>
        {state.actions.length > 0 ? (
          <Typography variant="caption">Apply to {state.selectedLibraries.length} libraries:</Typography>
        ) : (
          <Typography variant="caption" color="error">
            No import action found for the selected libraries
          </Typography>
        )}

        <ImportStudioSplitButton
          selectedLibraries={state.selectedLibraries}
          actions={state.actions}
          onLibraryImported={onClose}
        />
      </DialogActions>
    </Dialog>
  );
};

export const ImportStudioSplitButton = ({
  selectedLibraries,
  actions,
  onLibraryImported,
}: ImportStudioSplitButtonProps) => {
  const [state, setState] = useState<ImportStudioSplitButtonState>({
    selected: false,
    open: false,
    selectedIndex: 0,
    message: '',
  });

  const buttonGroupRef = useRef<HTMLDivElement>(null);
  const widgetRef = useRef<HTMLDivElement>(null);

  const { importLibraries, loading, data } = useImportLibraries();
  useEffect(() => {
    if (data) {
      onLibraryImported();
    }
  }, [data]);

  const { project } = useCurrentProject();

  const handleMenuItemClick = (_event, index) => {
    setState((prevState) => ({ ...prevState, open: false, selectedIndex: index }));
  };

  const handleToggle = () => {
    setState((prevState) => ({ ...prevState, open: !prevState.open }));
  };

  const handleClose = (event) => {
    event.preventDefault();
    setState((prevState) => ({ ...prevState, open: false }));
  };

  const handleClick = () => {
    const selectedAction = actions[state.selectedIndex];
    importLibraries(project.currentEditingContext.id, selectedAction.id, selectedLibraries);
  };

  return (
    <div ref={widgetRef}>
      <ButtonGroup
        variant="contained"
        color="primary"
        ref={buttonGroupRef}
        aria-label="split button"
        disabled={actions.length === 0}
        onFocus={() =>
          setState((prevState) => {
            return {
              ...prevState,
              selected: true,
            };
          })
        }
        onBlur={() =>
          setState((prevState) => {
            return {
              ...prevState,
              selected: false,
            };
          })
        }>
        <Button
          data-testid="import-split-button"
          variant="contained"
          color="primary"
          loading={loading}
          onClick={handleClick}>
          {actions.length > state.selectedIndex ? actions[state.selectedIndex].label : 'No Action'}
        </Button>
        <Button
          color="primary"
          size="small"
          aria-controls={state.open ? 'split-button-menu' : undefined}
          aria-expanded={state.open ? 'true' : undefined}
          aria-label="select button action"
          aria-haspopup="menu"
          role={'show-actions'}
          onClick={handleToggle}>
          <ArrowDropDownIcon />
        </Button>
      </ButtonGroup>
      <Popper
        open={state.open}
        anchorEl={buttonGroupRef.current}
        transition
        placement="bottom"
        style={{ zIndex: 1400 }}>
        {({ TransitionProps, placement }) => (
          <Grow
            {...TransitionProps}
            style={{
              transformOrigin: placement === 'bottom' ? 'center top' : 'center bottom',
            }}>
            <Paper>
              <ClickAwayListener onClickAway={handleClose}>
                <MenuList id="split-button-menu">
                  {actions.map((action, index) => (
                    <MenuItem
                      key={index}
                      selected={index === state.selectedIndex}
                      onClick={(event) => handleMenuItemClick(event, index)}>
                      {action.label}
                    </MenuItem>
                  ))}
                </MenuList>
              </ClickAwayListener>
            </Paper>
          </Grow>
        )}
      </Popper>
    </div>
  );
};
