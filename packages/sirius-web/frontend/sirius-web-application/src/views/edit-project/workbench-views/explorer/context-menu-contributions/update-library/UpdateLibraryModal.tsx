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
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import { useEffect, useState } from 'react';
import { useCurrentProject } from '../../../../useCurrentProject';
import { UpdateLibraryModalProps, UpdateLibraryModalState } from './UpdateLibraryModal.types';
import { UpdateLibraryTable } from './UpdateLibraryTable';
import { useUpdateLibrary } from './useUpdateLibrary';

export const UpdateLibraryModal = ({ open, title, namespace, name, version, onClose }: UpdateLibraryModalProps) => {
  const [state, setState] = useState<UpdateLibraryModalState>({
    selectedLibraryId: null,
  });

  const { updateLibrary, loading, data } = useUpdateLibrary();
  useEffect(() => {
    if (data) {
      onClose();
    }
  }, [data]);

  const handleLibrarySelected = (selectedLibraryId) => {
    setState((prevState) => ({
      ...prevState,
      selectedLibraryId,
    }));
  };

  const { project } = useCurrentProject();

  const handleUpdateLibrary = () => {
    if (project.currentEditingContext && state.selectedLibraryId) {
      updateLibrary(project.currentEditingContext.id, state.selectedLibraryId);
    }
  };

  return (
    <Dialog open={open} onClose={onClose} fullWidth maxWidth="lg" scroll="paper" data-testid="update-library">
      <DialogTitle>{title}</DialogTitle>
      <DialogContent>
        <UpdateLibraryTable
          namespace={namespace}
          name={name}
          version={version}
          onLibrarySelected={handleLibrarySelected}
        />
      </DialogContent>
      <DialogActions>
        <Button
          variant="contained"
          disabled={state.selectedLibraryId === null}
          loading={loading}
          data-testid="update-library"
          color="primary"
          onClick={handleUpdateLibrary}>
          Update
        </Button>
      </DialogActions>
    </Dialog>
  );
};
