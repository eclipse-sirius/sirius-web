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
import { Selection, SelectionContext } from '@eclipse-sirius/sirius-components-core';
import Checkbox from '@mui/material/Checkbox';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import FormControlLabel from '@mui/material/FormControlLabel';
import MenuItem from '@mui/material/MenuItem';
import Select, { SelectChangeEvent } from '@mui/material/Select';
import { useEffect, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { DuplicateObjectModalProps, DuplicateObjectModalState } from './DuplicateObjectModal.types';
import { useContainmentFeatureNames } from './useContainmentFeatureNames';
import { useDuplicateObject } from './useDuplicateObject';
import { ModelBrowserTreeView } from '@eclipse-sirius/sirius-components-browser';

const useDuplicateObjectModalStyles = makeStyles()((theme) => ({
  form: {
    display: 'flex',
    flexDirection: 'column',
    '& > *': {
      marginBottom: theme.spacing(1),
    },
  },
  title: {
    opacity: 0.6,
    fontSize: theme.typography.caption.fontSize,
  },
}));

export const DuplicateObjectModal = ({
  editingContextId,
  objectToDuplicateId,
  objectToDuplicateKind,
  onObjectDuplicated,
  onClose,
}: DuplicateObjectModalProps) => {
  const { classes } = useDuplicateObjectModalStyles();

  const [state, setState] = useState<DuplicateObjectModalState>({
    containerSelection: { entries: [] },
    containmentFeatureName: '',
    duplicateContent: true,
    copyOutgoingReferences: true,
    updateIncomingReferences: false,
  });

  const { containmentFeatureNames } = useContainmentFeatureNames(
    editingContextId,
    state.containerSelection.entries[0]?.id ?? null,
    objectToDuplicateId
  );

  useEffect(() => {
    if (containmentFeatureNames.length > 0) {
      setState((prevState) => ({
        ...prevState,
        containmentFeatureName: containmentFeatureNames[0] ?? '',
      }));
    }
  }, [containmentFeatureNames]);

  const { duplicateObject, duplicatedObject, loading: duplicateObjectMutationLoading } = useDuplicateObject();
  const onDuplicate = () => {
    duplicateObject(
      editingContextId,
      objectToDuplicateId,
      state.containerSelection.entries[0]?.id,
      state.containmentFeatureName,
      state.duplicateContent,
      state.copyOutgoingReferences,
      state.updateIncomingReferences
    );
  };
  useEffect(() => {
    if (duplicatedObject) {
      onObjectDuplicated({ entries: [{ id: duplicatedObject.id, kind: duplicatedObject.kind }] });
    }
  }, [duplicatedObject, onObjectDuplicated]);

  const onFeatureNameChange = (event: SelectChangeEvent<string>) => {
    const value = event.target.value;
    setState((prevState) => ({ ...prevState, containmentFeatureName: value }));
  };

  const onIsDuplicateContentChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const value = event.target.checked;
    setState((prevState) => ({ ...prevState, duplicateContent: value }));
  };
  const onIsCopyOutgoingReferencesContentChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const value = event.target.checked;
    setState((prevState) => ({ ...prevState, copyOutgoingReferences: value }));
  };
  const onIsUpdateIncomingReferencesChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const value = event.target.checked;
    setState((prevState) => ({ ...prevState, updateIncomingReferences: value }));
  };

  const onBrowserSelection = (selection: Selection) => {
    setState((prevState) => ({ ...prevState, containerSelection: selection }));
  };

  return (
    <SelectionContext.Provider
      value={{
        selection: state.containerSelection,
        setSelection: onBrowserSelection,
      }}>
      <Dialog
        open={true}
        onClose={onClose}
        aria-labelledby="dialog-title"
        maxWidth="xs"
        fullWidth
        data-testid="duplicate-object-dialog">
        <DialogTitle id="dialog-title">Duplicate object</DialogTitle>
        <DialogContent>
          <div className={classes.form}>
            <ModelBrowserTreeView
              editingContextId={editingContextId}
              referenceKind={objectToDuplicateKind}
              ownerId=""
              descriptionId=""
              isContainment={false}
              markedItemIds={[]}
              enableMultiSelection={false}
              title="Select the container"
              leafType="container"
              ownerKind={objectToDuplicateKind}
            />
            <span className={classes.title}>Select the containment reference name</span>
            <Select
              value={state.containmentFeatureName}
              onChange={onFeatureNameChange}
              fullWidth
              data-testid="containment-feature-name">
              {containmentFeatureNames.map((featureName) => (
                <MenuItem value={featureName} key={featureName}>
                  {featureName}
                </MenuItem>
              ))}
            </Select>
            <FormControlLabel
              control={
                <Checkbox
                  checked={state.duplicateContent}
                  onChange={onIsDuplicateContentChange}
                  name="duplicateContent"
                  data-testid="duplicateContent"
                />
              }
              label="Duplicate the content"
            />
            <FormControlLabel
              control={
                <Checkbox
                  checked={state.copyOutgoingReferences}
                  onChange={onIsCopyOutgoingReferencesContentChange}
                  name="copyOutgoingReferences"
                  data-testid="copyOutgoingReferences"
                />
              }
              label="Copy outgoing references"
            />
            <FormControlLabel
              control={
                <Checkbox
                  checked={state.updateIncomingReferences}
                  onChange={onIsUpdateIncomingReferencesChange}
                  name="updateIncomingReferences"
                  data-testid="updateIncomingReferences"
                />
              }
              label="Update incoming references"
            />
          </div>
        </DialogContent>
        <DialogActions>
          <Button
            variant="contained"
            disabled={!state.containmentFeatureName || duplicateObjectMutationLoading}
            data-testid="duplicate-object-button"
            onClick={onDuplicate}>
            Duplicate
          </Button>
        </DialogActions>
      </Dialog>
    </SelectionContext.Provider>
  );
};
