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
import { Selection, useSelection } from '@eclipse-sirius/sirius-components-core';
import {
  fuzzyMatch,
  PaletteToolOverriddenContributionComponentProps,
  ToolListItemText,
} from '@eclipse-sirius/sirius-components-palette';
import { TreePaletteContext, TreePaletteContextValue } from '@eclipse-sirius/sirius-components-trees';
import AddIcon from '@mui/icons-material/Add';
import ListItemIcon from '@mui/material/ListItemIcon';
import MenuItem from '@mui/material/MenuItem';
import { forwardRef, useContext, useState } from 'react';
import { NewObjectModal } from './NewObjectModal';
import { NewObjectToolContributionState } from './NewObjectToolContribution.types';

export const NewObjectToolContribution = forwardRef(
  (
    { onInvoked, searchedValue, tool }: PaletteToolOverriddenContributionComponentProps,
    ref: React.ForwardedRef<HTMLLIElement>
  ) => {
    const [state, setState] = useState<NewObjectToolContributionState>({
      isModalOpen: false,
    });
    const { editingContextId, item, readOnly, selectTreeItems, expandItem, onClose } =
      useContext<TreePaletteContextValue>(TreePaletteContext);

    const { setSelection } = useSelection();
    const onObjectCreated = (selection: Selection) => {
      setSelection(selection);
      selectTreeItems(selection.entries.map((entry) => entry.id));
      expandItem();
      onClose();
    };

    const handleClick = () => {
      onInvoked();
      setState((prevState) => ({ ...prevState, isModalOpen: true }));
    };

    const matchResult = searchedValue ? fuzzyMatch(tool.label, searchedValue) : null;
    if (!!searchedValue && !matchResult?.matches) {
      return null;
    }

    return (
      <>
        <MenuItem key="new-object" data-testid="new-object" onClick={handleClick} ref={ref} disabled={readOnly}>
          <ListItemIcon>
            <AddIcon fontSize="small" />
          </ListItemIcon>
          <ToolListItemText label={tool.label} searchedValue={searchedValue} />
        </MenuItem>
        {state.isModalOpen ? (
          <NewObjectModal
            editingContextId={editingContextId}
            item={item}
            onObjectCreated={onObjectCreated}
            onClose={onClose}
          />
        ) : null}
      </>
    );
  }
);
