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
import { Selection, SelectionContext } from '@eclipse-sirius/sirius-components-core';
import { TreeView } from '@eclipse-sirius/sirius-components-trees';
import { Checkbox, FormControlLabel } from '@material-ui/core';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import { makeStyles } from '@material-ui/core/styles';
import { useEffect, useState } from 'react';
import { DuplicateObjectModalProps, DuplicateObjectModalState } from './DuplicateObjectModal.types';
import { FilterBar } from './FilterBar';
import { useContainmentFeatureNames } from './useContainmentFeatureNames';
import { useDuplicateObject } from './useDuplicateObject';

const useDuplicateObjectModalStyles = makeStyles((theme) => ({
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
  borderStyle: {
    border: '1px solid',
    borderColor: theme.palette.grey[500],
    height: 300,
    overflow: 'auto',
  },
}));

export const DuplicateObjectModal = ({
  editingContextId,
  item,
  onObjectDuplicated,
  onClose,
}: DuplicateObjectModalProps) => {
  const classes = useDuplicateObjectModalStyles();

  const [state, setState] = useState<DuplicateObjectModalState>({
    containerSelection: { entries: [] },
    containmentFeatureNames: [],
    containmentFeatureName: '',
    duplicateContent: true,
    copyOutgoingReferences: true,
    updateIncomingReferences: false,
    filterBarText: '',
  });

  const { getContainmentFeatureNames, containmentFeatureNames } = useContainmentFeatureNames();
  useEffect(() => {
    if (state.containerSelection.entries[0]) {
      getContainmentFeatureNames({
        variables: {
          editingContextId,
          containerId: state.containerSelection.entries[0].id,
          containedObjectId: item.id,
        },
      });
    }
  }, [state.containerSelection, item]);
  useEffect(() => {
    if (containmentFeatureNames) {
      setState((prevState) => ({
        ...prevState,
        containmentFeatureNames,
        containmentFeatureName: containmentFeatureNames[0] ?? '',
      }));
    }
  }, [containmentFeatureNames]);

  // mutation
  const { duplicateObject, duplicatedObject } = useDuplicateObject();
  const onDuplicate = () => {
    duplicateObject(
      editingContextId,
      item.id,
      state.containerSelection.entries[0]?.id,
      state.containmentFeatureName,
      state.duplicateContent,
      state.copyOutgoingReferences,
      state.updateIncomingReferences
    );
  };
  useEffect(() => {
    if (duplicatedObject) {
      onObjectDuplicated({ entries: [duplicatedObject] });
    }
  }, [duplicatedObject, onObjectDuplicated]);

  const onFeatureNameChange = (event: any) => {
    const value = event.target.value;
    setState((prevState) => ({ ...prevState, containmentFeatureName: value }));
  };

  const onIsDuplicateContentChange = (event) => {
    const value = event.target.checked;
    setState((prevState) => ({ ...prevState, duplicateContent: value }));
  };
  const onIsCopyOutgoingReferencesContentChange = (event) => {
    const value = event.target.checked;
    setState((prevState) => ({ ...prevState, copyOutgoingReferences: value }));
  };
  const onIsUpdateIncomingReferencesChange = (event) => {
    const value = event.target.checked;
    setState((prevState) => ({ ...prevState, updateIncomingReferences: value }));
  };

  const onBrowserSelection = (selection: Selection) => {
    if (selection.entries[0]?.kind !== 'siriusWeb://document')
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
        data-testid="create-new-model">
        <DialogTitle id="dialog-title">Duplicate object</DialogTitle>
        <DialogContent>
          <div className={classes.form}>
            <FilterBar
              onTextChange={(event) => setState((prevState) => ({ ...prevState, filterBarText: event.target.value }))}
              onTextClear={() => setState((prevState) => ({ ...prevState, filterBarText: '' }))}
              text={state.filterBarText}
            />
            <span className={classes.title}>Select the container</span>
            <div className={classes.borderStyle}>
              <TreeView
                editingContextId={editingContextId}
                readOnly={true}
                treeId={`modelBrowser://container?ownerKind=${encodeURIComponent(
                  item.kind
                )}&targetType=${encodeURIComponent(item.kind)}&isContainment=true`}
                enableMultiSelection={false}
                synchronizedWithSelection={true}
                activeFilterIds={[]}
                textToFilter={state.filterBarText}
                textToHighlight={state.filterBarText}
                markedItemIds={[]}
              />
            </div>
            <span className={classes.title}>Select the containment reference name</span>
            <Select
              value={state.containmentFeatureName}
              onChange={onFeatureNameChange}
              disabled={false}
              labelId="DuplicateObjectModalStereotypeLabel"
              fullWidth
              inputProps={{ 'data-testid': 'containmentFeatureName-input' }}
              data-testid="containmentFeatureName">
              {state.containmentFeatureNames.map((featureName) => (
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
                  color="primary"
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
                  color="primary"
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
                  color="primary"
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
            disabled={!state.containerSelection.entries[0] || !state.containerSelection.entries[0].kind}
            data-testid="duplicate-object"
            color="primary"
            onClick={onDuplicate}>
            Duplicate
          </Button>
        </DialogActions>
      </Dialog>
    </SelectionContext.Provider>
  );
};
