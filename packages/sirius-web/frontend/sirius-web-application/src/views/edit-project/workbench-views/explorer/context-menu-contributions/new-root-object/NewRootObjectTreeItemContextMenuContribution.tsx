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
import { Selection, useSelection } from '@eclipse-sirius/sirius-components-core';
import { TreeItemContextMenuComponentProps } from '@eclipse-sirius/sirius-components-trees';
import AddIcon from '@mui/icons-material/Add';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import { forwardRef, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { NewRootObjectModal } from './NewRootObjectModal';
import { NewRootObjectTreeItemContextMenuContributionState } from './NewRootObjectTreeItemContextMenuContribution.types';

export const NewRootObjectTreeItemContextMenuContribution = forwardRef(
  (
    { editingContextId, item, readOnly, selectTreeItems, expandItem, onClose }: TreeItemContextMenuComponentProps,
    ref: React.ForwardedRef<HTMLLIElement>
  ) => {
    const [state, setState] = useState<NewRootObjectTreeItemContextMenuContributionState>({
      isModalOpen: false,
    });

    const { t } = useTranslation('sirius-web-application', { keyPrefix: 'project.edit' });

    const { setSelection } = useSelection();
    const onObjectCreated = (selection: Selection) => {
      setSelection(selection);
      selectTreeItems(selection.entries.map((entry) => entry.id));
      expandItem();
      onClose();
    };

    return (
      <>
        <MenuItem
          key="new-object"
          data-testid="new-object"
          onClick={() => setState((prevState) => ({ ...prevState, isModalOpen: true }))}
          ref={ref}
          disabled={readOnly}
          aria-disabled>
          <ListItemIcon>
            <AddIcon fontSize="small" />
          </ListItemIcon>
          <ListItemText primary={t('newObject')} />
        </MenuItem>
        {state.isModalOpen ? (
          <NewRootObjectModal
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
