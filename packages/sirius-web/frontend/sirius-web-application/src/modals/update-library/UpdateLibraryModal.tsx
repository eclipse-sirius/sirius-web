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
import DialogTitle from '@mui/material/DialogTitle';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import Button from '@mui/material/Button';
import DialogActions from '@mui/material/DialogActions';
import { UpdateLibraryTable } from './UpdateLibraryTable';
import { useState } from 'react';
import { UpdateLibraryModalProps, UpdateLibraryModalState } from './UpdateLibraryModal.types';
import { useUpdateLibrary } from './useUpdateLibrary';
import { useCurrentProject } from '../../views/edit-project/useCurrentProject';

export const UpdateLibraryModal = ({ open, title, namespace, name, version, onClose }: UpdateLibraryModalProps) => {
  const [state, setState] = useState<UpdateLibraryModalState>({
    selectedLibraries: [],
  });

  const { updateLibrary } = useUpdateLibrary();

  const onSelectionChange = (selection) => {
    setState((prevState) => ({
      ...prevState,
      selectedLibraries: selection,
    }));
  };

  const { project } = useCurrentProject();

  const handleUpdateLibrary = () => {
    if (state.selectedLibraries.length > 0) {
      updateLibrary(project.currentEditingContext.id, state.selectedLibraries[0]);
    }
  };

  return (
    <Dialog open={open} onClose={onClose} fullWidth scroll="paper" data-testid="update">
      <DialogTitle>{title}</DialogTitle>
      <DialogContent>
        <UpdateLibraryTable namespace={namespace} name={name} version={version} onSelectionChange={onSelectionChange} />
      </DialogContent>
      <DialogActions>
        <Button
          variant="contained"
          disabled={state.selectedLibraries.length === 0}
          data-testid="update-library"
          color="primary"
          onClick={handleUpdateLibrary}>
          Update
        </Button>
      </DialogActions>
    </Dialog>
  );
};
