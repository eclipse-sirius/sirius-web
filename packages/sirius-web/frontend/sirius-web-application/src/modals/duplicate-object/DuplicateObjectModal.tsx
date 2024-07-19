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
import { ModelBrowserTreeView } from '@eclipse-sirius/sirius-components-browser';
import { GQLTreeItem } from '@eclipse-sirius/sirius-components-trees';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Checkbox from '@mui/material/Checkbox';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import FormControlLabel from '@mui/material/FormControlLabel';
import MenuItem from '@mui/material/MenuItem';
import Select, { SelectChangeEvent } from '@mui/material/Select';
import Typography from '@mui/material/Typography';
import { useEffect, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { DuplicateObjectModalProps, DuplicateObjectModalState } from './DuplicateObjectModal.types';
import { useContainmentFeatureNames } from './useContainmentFeatureNames';
import { useDuplicateObject } from './useDuplicateObject';

const useDuplicateObjectModalStyles = makeStyles()((theme) => ({
  form: {
    display: 'flex',
    flexDirection: 'column',
    gap: theme.spacing(1),
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
    containerSelectedId: null,
    containmentFeatureName: '',
    duplicateContent: true,
    copyOutgoingReferences: true,
    updateIncomingReferences: false,
  });

  const { containmentFeatureNames } = useContainmentFeatureNames(
    editingContextId,
    state.containerSelectedId,
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
      state.containerSelectedId,
      state.containmentFeatureName,
      state.duplicateContent,
      state.copyOutgoingReferences,
      state.updateIncomingReferences
    );
  };
  useEffect(() => {
    if (duplicatedObject) {
      onObjectDuplicated({ entries: [{ id: duplicatedObject.id }] });
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

  const onTreeItemClick = (_event: React.MouseEvent<HTMLDivElement, MouseEvent>, item: GQLTreeItem) => {
    setState((prevState) => ({ ...prevState, containerSelectedId: item.id }));
  };

  return (
    <Dialog
      open={true}
      onClose={onClose}
      aria-labelledby="dialog-title"
      maxWidth="md"
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
            title="Select the container"
            leafType="container"
            ownerKind={objectToDuplicateKind}
            onTreeItemClick={onTreeItemClick}
            selectedTreeItemIds={state.containerSelectedId ? [state.containerSelectedId] : []}
          />

          <div>
            <Typography variant="subtitle1" gutterBottom>
              Select the containment reference name
            </Typography>
            <Select
              value={state.containmentFeatureName}
              onChange={onFeatureNameChange}
              disabled={containmentFeatureNames.length === 0}
              fullWidth
              data-testid="containment-feature-name">
              {containmentFeatureNames.map((featureName) => (
                <MenuItem value={featureName} key={featureName}>
                  {featureName}
                </MenuItem>
              ))}
            </Select>
          </div>

          <Box
            sx={(theme) => ({
              display: 'flex',
              flexDirection: 'column',
              gap: theme.spacing(0.5),
            })}>
            <FormControlLabel
              control={
                <Checkbox
                  checked={state.duplicateContent}
                  onChange={onIsDuplicateContentChange}
                  name="duplicateContent"
                  sx={{ padding: 0 }}
                  data-testid="duplicateContent"
                />
              }
              label="Duplicate the content"
              sx={{
                marginLeft: '0',
                marginRight: '0',
              }}
            />
            <FormControlLabel
              control={
                <Checkbox
                  checked={state.copyOutgoingReferences}
                  onChange={onIsCopyOutgoingReferencesContentChange}
                  name="copyOutgoingReferences"
                  sx={{ padding: 0 }}
                  data-testid="copyOutgoingReferences"
                />
              }
              label="Copy outgoing references"
              sx={{
                marginLeft: '0',
                marginRight: '0',
              }}
            />
            <FormControlLabel
              control={
                <Checkbox
                  checked={state.updateIncomingReferences}
                  onChange={onIsUpdateIncomingReferencesChange}
                  name="updateIncomingReferences"
                  sx={{ padding: 0 }}
                  data-testid="updateIncomingReferences"
                />
              }
              label="Update incoming references"
              sx={{
                marginLeft: '0',
                marginRight: '0',
              }}
            />
          </Box>
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
  );
};
