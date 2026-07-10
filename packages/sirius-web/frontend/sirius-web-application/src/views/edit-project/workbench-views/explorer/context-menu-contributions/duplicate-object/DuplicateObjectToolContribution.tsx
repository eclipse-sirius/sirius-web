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
  PaletteToolOverriddenContributionComponentProps,
  ToolListItemText,
} from '@eclipse-sirius/sirius-components-palette';
import { TreePaletteContext, TreePaletteContextValue } from '@eclipse-sirius/sirius-components-trees';
import AddToPhotosIcon from '@mui/icons-material/AddToPhotos';
import ListItemIcon from '@mui/material/ListItemIcon';
import MenuItem from '@mui/material/MenuItem';
import { forwardRef, useContext, useState } from 'react';
import { DuplicateObjectModal } from './DuplicateObjectModal';
import { DuplicateObjectToolContributionState } from './DuplicateObjectToolContribution.types';

export const DuplicateObjectToolContribution = forwardRef(
  ({ onInvoked, tool }: PaletteToolOverriddenContributionComponentProps, ref: React.ForwardedRef<HTMLLIElement>) => {
    const [state, setState] = useState<DuplicateObjectToolContributionState>({
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

    return (
      <>
        <MenuItem
          key="duplicate-object"
          onClick={handleClick}
          ref={ref}
          data-testid="duplicate-object"
          disabled={readOnly}>
          <ListItemIcon>
            <AddToPhotosIcon fontSize="small" />
          </ListItemIcon>
          <ToolListItemText label={tool.label} searchedValue={null} />
        </MenuItem>
        {state.isModalOpen ? (
          <DuplicateObjectModal
            editingContextId={editingContextId}
            objectToDuplicateId={item.id}
            objectToDuplicateKind={item.kind}
            onObjectDuplicated={onObjectCreated}
            onClose={onClose}
          />
        ) : null}
      </>
    );
  }
);
