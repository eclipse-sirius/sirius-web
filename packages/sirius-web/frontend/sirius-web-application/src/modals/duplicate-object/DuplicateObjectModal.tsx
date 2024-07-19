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
import { TreeView, TreeItemActionProps } from '@eclipse-sirius/sirius-components-trees';
import UnfoldMoreIcon from '@mui/icons-material/UnfoldMore';
import Checkbox from '@mui/material/Checkbox';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import FormControlLabel from '@mui/material/FormControlLabel';
import IconButton from '@mui/material/IconButton';
import MenuItem from '@mui/material/MenuItem';
import Select, { SelectChangeEvent } from '@mui/material/Select';
import { useEffect, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { DuplicateObjectModalProps, DuplicateObjectModalState } from './DuplicateObjectModal.types';
import { FilterBar } from './FilterBar';
import { useContainmentFeatureNames } from './useContainmentFeatureNames';
import { useDuplicateObject } from './useDuplicateObject';
import { useDuplicateDialogTreeSubscription } from './useDuplicateDialogTreeSubscription';

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
  const { classes } = useDuplicateObjectModalStyles();

  const [state, setState] = useState<DuplicateObjectModalState>({
    containerSelection: { entries: [] },
    containmentFeatureNames: [],
    containmentFeatureName: '',
    duplicateContent: true,
    copyOutgoingReferences: true,
    updateIncomingReferences: false,
    filterBarText: '',
    expanded: [],
    maxDepth: 1,
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
    if (selection.entries[0]?.kind !== 'siriusWeb://document')
      setState((prevState) => ({ ...prevState, containerSelection: selection }));
  };

  const onExpandedElementChange = (expanded: string[], maxDepth: number) => {
    setState((prevState) => ({ ...prevState, expanded, maxDepth }));
  };

  const treeId: string = `modelBrowser://container?ownerKind=${encodeURIComponent(
    item.kind
  )}&targetType=${encodeURIComponent(item.kind)}&isContainment=true`;
  const { tree } = useDuplicateDialogTreeSubscription(editingContextId, treeId, state.expanded, state.maxDepth);

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
            <FilterBar
              onTextChange={(event) =>
                setState((prevState) => ({
                  ...prevState,
                  filterBarText: event.target.value,
                }))
              }
              onTextClear={() => setState((prevState) => ({ ...prevState, filterBarText: '' }))}
              text={state.filterBarText}
            />
            <span className={classes.title}>Select the container</span>
            <div className={classes.borderStyle} data-testid="duplicate-object-tree">
              {tree !== null ? (
                <TreeView
                  editingContextId={editingContextId}
                  readOnly={true}
                  treeId={treeId}
                  tree={tree}
                  enableMultiSelection={false}
                  synchronizedWithSelection={true}
                  textToFilter={state.filterBarText}
                  textToHighlight={state.filterBarText}
                  treeItemActionRender={(props) => <ExpandAllTreeItemAction {...props} />}
                  onExpandedElementChange={onExpandedElementChange}
                  expanded={state.expanded}
                  maxDepth={state.maxDepth}
                />
              ) : null}
            </div>
            <span className={classes.title}>Select the containment reference name</span>
            <Select
              value={state.containmentFeatureName}
              onChange={onFeatureNameChange}
              fullWidth
              data-testid="containment-feature-name">
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
            disabled={!state.containmentFeatureName}
            data-testid="duplicate-object-button"
            onClick={onDuplicate}>
            Duplicate
          </Button>
        </DialogActions>
      </Dialog>
    </SelectionContext.Provider>
  );
};

const ExpandAllTreeItemAction = ({ onExpandAll, item, isHovered }: TreeItemActionProps) => {
  if (!onExpandAll || !item || !item.hasChildren || !isHovered) {
    return null;
  }
  return (
    <IconButton
      size="small"
      data-testid="expand-all"
      title="expand all"
      onClick={() => {
        onExpandAll(item);
      }}>
      <UnfoldMoreIcon style={{ fontSize: 12 }} />
    </IconButton>
  );
};
